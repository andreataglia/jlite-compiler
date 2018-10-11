package concrete_nodes;

import utils.Visitor;

public class ReadlnStmt extends Stmt{
    public String id;

    public ReadlnStmt(String id) {
        this.id = id;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
