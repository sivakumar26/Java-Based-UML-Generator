import java.util.List;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;


public class ClassFinder extends VoidVisitorAdapter {


    
	 @Override
	 public void visit(ClassOrInterfaceDeclaration decl, Object arg)
	   {
	      // Make class extend Blah.
		 
		 List<ClassOrInterfaceType> list = decl.getExtends();
		 if(list==null)
			 return;
		 for (ClassOrInterfaceType k : list) {
				String n = k.toString();
			
				Umlgen.s = Umlgen.s + n + " " + "<|--" + " " + Umlgen.classname + "\n";
			}
	      
	      
	   }
       

}
