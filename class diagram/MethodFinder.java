import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;


public class MethodFinder extends VoidVisitorAdapter {


    
    @Override
    public void visit(MethodDeclaration n, Object arg) {
    
    	if(n.getName()!=null)
    		Umlgen.methodlist.add(n.getName().toLowerCase());
    	String param= "";
    
    	if (n.getBody() !=null && n.getBody().getStmts()!=null) {
    	
    		for(Statement x : n.getBody().getStmts())
    		{
    			if(x!=null)
    			{
    			String k = x.toString();
    			String delims = "[ .,?!]+";
		        String[] tokens = k.split(delims);
		        if(tokens[0]!=null)
		        {
		        if(Umlgen.list.contains(tokens[0]))
		        	Umlgen.s = Umlgen.s + tokens[0] +"<.. " + Umlgen.classname + "\n";
		        }
    			}
    		}
    		
    	}
    
    	if(n.getParameters()!=null)
    	{
    	for(Parameter x : n.getParameters())
    	{
    		if(param != "")
        		param = param + "," + x.toString();
        		else 
        			param = x.toString();
    	String check =  x.getType().toString();
    	if(Umlgen.list.contains(check))
    	{
    		if(!Umlgen.s.contains(check + "<.. "  + Umlgen.classname + ":uses") && Umlgen.interfacelist.contains(check) && !Umlgen.interfacelist.contains(Umlgen.classname))
    			Umlgen.s = Umlgen.s + check + "<.. "  + Umlgen.classname + ":uses" + "\n";
    	}
    	}	
    	}
    	if(n.getModifiers()==1)
    	{
    		Umlgen.s = Umlgen.s + Umlgen.classname + " : "+ "+" + n.getName() + "("+ param +")" + ":" + n.getType();
    		Umlgen.s = Umlgen.s + "\n";
    	}
    }
    
    


}
