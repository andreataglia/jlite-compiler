import jnodes.JProgram;

import java.io.*;
   
public class Main {
  static public void main(String argv[]) {    
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      JProgram result = (JProgram) p.parse().value;
      //System.out.println(result); for now we just stick to the printing made by the Lexer
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


