import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;


public class FieldFinder extends VoidVisitorAdapter {


    @Override
    public void visit(FieldDeclaration n, Object arg) {
    	int i;
    	
    	String x = n.getType().toString();
    	boolean flag =false;
    	System.out.println(Umlgen.classname);
    	System.out.println(Umlgen.list);
    	if(Umlgen.list.contains(x))
        	{
    		if(x.contains("Collection"))
    		{
    		   i= Umlgen.list.indexOf(x)-1;
    		 flag =true;
    		}
    		else 
    			i = Umlgen.list.indexOf(x);
    	
    		
    		if(Umlgen.s.contains(Umlgen.list.get(i) + " -- "  + Umlgen.classname ))
    			System.out.println("already there");
    		else if(Umlgen.s.contains(" " + Umlgen.list.get(i) + " - \"1\" " + Umlgen.classname ) && flag == false)
    		{
    	
    			Umlgen.s.replace(Umlgen.list.get(i) + " - \"1\" " + Umlgen.classname, Umlgen.list.get(i) + "\"1\" - \"1\" " + Umlgen.classname);
    		}
    		
    		else if(Umlgen.s.contains(" "+Umlgen.list.get(i) + " - \"1\" " + Umlgen.classname ) && flag == true)
    		{
    			
    			Umlgen.s.replace(Umlgen.list.get(i) + " - \"1\" " + Umlgen.classname, Umlgen.list.get(i) + "\"*\" - \"1\" " + Umlgen.classname);
    		}
    		
    		else if(Umlgen.s.contains(Umlgen.list.get(i) + " - \"*\" " + Umlgen.classname ) && flag == false)
    		{
    			
    			Umlgen.s.replace(Umlgen.list.get(i) + " - \"*\" " + Umlgen.classname, Umlgen.list.get(i) + "\"1\" - \"*\" " + Umlgen.classname);
    		}
    	
    		else if(Umlgen.s.contains( Umlgen.list.get(i) + " - \"*\" " + Umlgen.classname ) && flag == true)
    		{
    			
    			Umlgen.s.replace(Umlgen.list.get(i) + " - \"*\" " + Umlgen.classname, Umlgen.list.get(i) + "\"*\" - \"*\" " + Umlgen.classname);
    		}
    		else
    		{
    			
    			if(flag==false)
    				Umlgen.s = Umlgen.s + Umlgen.classname + " - \"1\" "  + Umlgen.list.get(i) + "\n";
    			else
    				Umlgen.s = Umlgen.s + Umlgen.classname + " - \"*\" "  + Umlgen.list.get(i) + "\n";
    		}
    		
    		
    		
        	}
    			


    		
        	
    	
    	String k =n.toString();
         k = k.replaceAll("[;]", "");
         String[] strs = k.split("\\s+");
        
         if(strs[0].equals("public"))
        	 strs[0]="+" ;
         if(strs[0].equals("private"))
         {    
        	 if(Umlgen.methodlist.contains("set"+strs[2]) && Umlgen.methodlist.contains("get"+strs[2]))
        	 strs[0]="+" ;
        	 else
        		 strs[0]="-" ;
        	 
         }
         if(strs[0].equals("protected"))
        	 strs[0]="#" ;
         if(strs.length >2 && (strs[0] == "+" || strs[0] == "-"))
         {
        	 Umlgen.s = Umlgen.s + Umlgen.classname + " : " + strs[0] + " " + strs[2] + " : " + strs[1];
        	 Umlgen.s = Umlgen.s + "\n";
         strs[2] = Character.toUpperCase(strs[2].charAt(0)) + strs[2].substring(1);
         String rep1 = "get" + strs[2];
         String rep2 = "set" + strs[2];
         Umlgen.s = Umlgen.s.replaceAll( ".*"+rep1+".*(\r?\n|\r)?", "" );
         Umlgen.s = Umlgen.s.replaceAll( ".*"+rep2+".*(\r?\n|\r)?", "" );
         }
         super.visit(n, arg);
    }
}
