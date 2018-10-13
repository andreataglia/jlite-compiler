import concrete_nodes.Program;
import jnodes.JProgram;
import nodes3.Program3;
import utils.IRGeneratorVisitor;
import utils.StaticCheckingVisitor;
import utils.SymbolTable;
import utils.TypeException;

import java.io.FileReader;

public class Main {

    static boolean DEBUG = true;

    static public void main(String argv[]) {
        boolean allGood = true;
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            JProgram ast = (JProgram) p.parse().value;
            //System.out.println(ast.toString());
            Program tree = ast.genConcreteTree();
            //PrettyPrintVisitor visitor = new PrettyPrintVisitor();
            utils.SymbolTable symbolTable = new SymbolTable();
            StaticCheckingVisitor staticCheckingVisitor = new StaticCheckingVisitor(symbolTable);
            try {
                tree.accept(staticCheckingVisitor);
            } catch (TypeException e) {
                System.out.println();
                System.err.println("ERROR: Static Checking Failed - "+ e);
                if (DEBUG) e.printStackTrace();
                System.err.println("cat output.txt to see where the program failed\n\n");
                if (!DEBUG) System.exit(-1);
                allGood = false;
            }
            IRGeneratorVisitor irGeneratorVisitor = new IRGeneratorVisitor(symbolTable);
            Program3 ir3Tree = (Program3) tree.accept(irGeneratorVisitor);

            if (allGood) System.out.println("\n\nSuccess!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


