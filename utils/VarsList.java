package utils;

import concrete_nodes.VarDecl;

import java.util.ArrayList;
import java.util.List;

public class VarsList {
    List<VarDecl> list;

    public VarsList(List<VarDecl> list) {
        this.list = new ArrayList<>();
        if (list == null) this.list = new ArrayList<>();
        else {
            this.list.addAll(list);
        }
    }

    public VarsList() {
        list = new ArrayList<>();
    }

    void add(VarDecl varDecl) {
        list.add(varDecl);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VarsList) {
            VarsList element = (VarsList) obj;
            if (this.list.size() == element.list.size()) {
                for (VarDecl var : list) {
                    for (VarDecl otherVar : element.list) {
                        if (!var.equals(otherVar)) return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    boolean atLeastOneVarIn(VarsList other) {
        for (int i = 0; i < this.list.size(); i++) {
            for (int j = 0; j < other.list.size(); j++) {
                if (list.get(i).equals(other.list.get(j))) return true;
            }
        }
        return false;
    }

    boolean allVarsHaveDifferentIds() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (VarDecl v : list) {
            s += " " + v;
        }
        return s;
    }
}
