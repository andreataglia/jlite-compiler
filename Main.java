import concrete_nodes.Program;
import jnodes.JProgram;
import utils.StaticCheckingVisitor;

import java.io.*;

public class Main {
    static public void main(String argv[]) {
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            JProgram ast = (JProgram) p.parse().value;
            //System.out.println(ast.toString());
            Program tree = ast.genConcreteTree();
            //PrettyPrintVisitor visitor = new PrettyPrintVisitor();
            StaticCheckingVisitor visitor = new StaticCheckingVisitor();
            tree.accept(visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


