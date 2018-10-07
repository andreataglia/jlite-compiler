package concrete_nodes;

import java.util.List;

public class IfStmt extends Stmt {
    public Expr condition;
    public List<Stmt> trueBranch;
    public List<Stmt> falseBranch;
}
