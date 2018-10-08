package concrete_nodes;

import utils.Visitor;

import java.util.List;

public class Program extends Node {
    public MainClass mainClass;
    public List<ClassDecl> classDeclList;

    public Program(MainClass mainClass, List<ClassDecl> classDeclList) {
        this.mainClass = mainClass;
        this.classDeclList = classDeclList;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
