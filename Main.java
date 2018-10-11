import concrete_nodes.Program;
import jnodes.JProgram;
import utils.StaticCheckingVisitor;
import utils.TypeException;

import java.io.*;

public class Main {
    static public void main(String argv[]) {
        boolean allGood = true;
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            JProgram ast = (JProgram) p.parse().value;
            //System.out.println(ast.toString());
            Program tree = ast.genConcreteTree();
            //PrettyPrintVisitor visitor = new PrettyPrintVisitor();
            StaticCheckingVisitor visitor = new StaticCheckingVisitor();
            try {
                tree.accept(visitor);
            } catch (TypeException e) {
                e.printStackTrace();
                System.out.println("\n\nERROR: Static Checking Failed");
                allGood = false;
            }
            if (allGood) System.out.println("\n\nSuccess!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


