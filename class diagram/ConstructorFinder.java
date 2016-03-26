import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.visitor.VoidVisitorAdapter;


public class ConstructorFinder extends VoidVisitorAdapter {


    
    @Override
    public void visit(ConstructorDeclaration n, Object arg) {
    	String param =null;
    	if(n.getParameters()!=null)
    	{
    	for(Parameter x : n.getParameters())
    	{
    		if(param != null)
    		param = param + "," + x.toString();
    		else 
    			param = x.toString();
    	String check =  x.getType().toString();
    	if(Umlgen.list.contains(check))
    	{
    		if(!Umlgen.s.contains(check + "<.. "  + Umlgen.classname + ":uses")&& Umlgen.interfacelist.contains(check)&& !Umlgen.interfacelist.contains(Umlgen.classname))
    			Umlgen.s = Umlgen.s + check + "<.. "  + Umlgen.classname + ":uses" + "\n";
    	}
    	}	
    	}
    	Umlgen.s = Umlgen.s + Umlgen.classname + " : "+ "+" + n.getName() + "("+ param +")" 
        		;
    	Umlgen.s = Umlgen.s + "\n";
    }
    
    


}
