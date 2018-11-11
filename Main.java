import asm.ASMCode;
import asm.ASMGeneratorVisitor;
import concrete_nodes.Program;
import jnodes.JProgram;
import nodes3.Program3;
import utils.IRGeneratorVisitor;
import utils.StaticCheckingVisitor;
import utils.SymbolTable;
import utils.TypeException;

import java.io.*;

public class Main {

    private static boolean DEBUG = true;

    static public void main(String argv[]) {
        boolean allGood = true;
        try {
            //Parsing
            System.out.println("\n------------- Parsing Output ------------------");
            parser p = null;
            try {
                p = new parser(new Lexer(new FileReader(argv[0])));
            } catch (FileNotFoundException e) {
                System.err.println("\nFATAL ERROR: Input file not found");
                System.exit(-1);
            }
            JProgram ast = (JProgram) p.parse().value;
            Program tree = ast.genConcreteTree();
            //PrettyPrintVisitor visitor = new PrettyPrintVisitor();
            System.out.println("\n------------- End Parsing Output ------------------");
            System.out.println("-----------------------------------------------------");


            //Static Checking
            utils.SymbolTable symbolTable = new SymbolTable();
            StaticCheckingVisitor staticCheckingVisitor = new StaticCheckingVisitor(symbolTable);
            try {
                tree.accept(staticCheckingVisitor);
            } catch (TypeException e) {
                allGood = false;
                System.err.println("\nFATAL ERROR: Static Checking Failed - " + e);
                if (DEBUG) e.printStackTrace();
                System.err.println("cat output.txt to see where the program failed\n\n");
                if (!DEBUG) System.exit(-1);
            }

            //IR Generation
            IRGeneratorVisitor irGeneratorVisitor = new IRGeneratorVisitor(symbolTable);
            //will be used as input for the backend compiling process
            Program3 ir3Tree = (Program3) tree.accept(irGeneratorVisitor);

            //ASM Generation
            ASMGeneratorVisitor asmGeneratorVisitor = new ASMGeneratorVisitor();
            ir3Tree.accept(asmGeneratorVisitor);


            if (allGood) System.err.println("\nSuccess!");
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            else System.err.println("\nFATAL ERROR: Couldn't compile");
            System.exit(-1);
        }
    }
}


