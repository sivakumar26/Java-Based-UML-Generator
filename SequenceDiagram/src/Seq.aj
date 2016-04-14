import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Stack;

public aspect Seq {

	pointcut methods() : !within(Invoker) && !within(Seq) && execution(public * *.*(..))  && !execution(public static void setUpBeforeClass() )
	&& !execution(public static void tearDownAfterClass())
	 && !execution(*.new(..));

	pointcut constructors() : !within(Invoker) && !within(Seq) && execution(*.new(..));
	private Stack<String> execStack = new Stack<String>();
	private String seqOutput = "";
	private int d = 0;
	private int cons = 0;


	before() : constructors() {
		cons++;
	}

	after() :  constructors() {
		cons--;
	}

	before()  : methods() {
		if (cons == 0) {
			String currClassName = thisJoinPoint.getTarget().getClass().getName();
			String class1 = null;
			
			if(execStack.isEmpty())
			{
				class1 = "Main";
			}
			else
			{
				String c = (String) execStack.peek();
				class1 = c;
			}
			
			String class2 = currClassName;
			String message = null;
			String currExec = thisJoinPoint.toString();

			int endIndex = currExec.lastIndexOf(')');
			String methodSignature = currExec.substring(10, endIndex );
			String components[] = methodSignature.split(" ");
			String p = components[1];
			int dot = p.indexOf('.');
			String ret = components[0];
			String exec = components[1].substring(dot + 1);

			message = exec + " : " + ret;
			
			String result = class1 + " ->" + class2 + ":" +   " " + message + "\n";
			seqOutput += result;
			d++;
			execStack.push(class2);
		}
	}

	after() : methods(){
		if (cons == 0) {
			d--;
			if (!execStack.isEmpty()) {
				execStack.pop();
			}
			if (d == 0) {
				getSequenceDiagram(seqOutput, "sq.png", "modern-blue");
			}
		}
	}
	

	private static void getSequenceDiagram(String text, String outFile, String style) {

		try {
			String data = "style=" + style + "&message=" + URLEncoder.encode(text, "UTF-8") + "&apiVersion=1";

			
			URL url = new URL("http://www.websequencediagrams.com");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

			writer.write(data);
			writer.flush();

			
			StringBuffer answer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				answer.append(line);
			}
			writer.close();
			reader.close();

			String json = answer.toString();
			int start = json.indexOf("?png=");
			int end = json.indexOf("\"", start);

			url = new URL("http://www.websequencediagrams.com/" + json.substring(start, end));

			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
			InputStream in = url.openConnection().getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}

			in.close();
			out.close();

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
