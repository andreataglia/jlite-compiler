import concrete_nodes.Program;
import jnodes.JProgram;
import utils.PrettyPrintVisitor;

import java.io.*;
   
public class Main {
  static public void main(String argv[]) {    
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      JProgram ast = (JProgram) p.parse().value;
      //System.out.println(ast.toString());
      Program tree = ast.genConcreteTree();
      PrettyPrintVisitor visitor = new PrettyPrintVisitor();
      tree.accept(visitor);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


