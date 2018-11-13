package asm;

import java.util.HashMap;
import java.util.Map;

class VarLocationTracker {
    //the variable unique names is given by the id + function number to distinguish between functions
    private HashMap<String, Location> locations;

    VarLocationTracker() {
        locations = new HashMap<>();
    }


    ////////////////////// GETTERS /////////////////////////
    boolean isInReg(String var) {
        return locations.get(var).isInReg();
    }

    boolean isOnStack(String var) {
        return locations.get(var).isOnStack();
    }

    int getVarRegnum(String var) {
        return locations.get(var).getRegnum();
    }

    //returns offset from fp
    int getStackPosition(String var) {
        return locations.get(var).getStackaddress();
    }

    ////////////////////// SETTERS /////////////////////////

    void removeVarFromStack(String var) {
        locations.get(var).noMoreOnStack();
    }

    void removeVarFromReg(String var) {
        locations.get(var).noMoreInReg();
    }

    void addVarToStack(String var, int stackPosition) {
        if (locations.get(var) != null) locations.get(var).setStackaddress(stackPosition);
        else locations.put(var, new Location(stackPosition, true));
    }

    void addVarToReg(String var, int regnum) {
        if (locations.get(var) != null) locations.get(var).setRegnum(regnum);
        else locations.put(var, new Location(regnum, false));
    }

    void printState() {
        System.out.println("-----------var tracker-------------");
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue().isInReg() ? "reg:" + entry.getValue().getRegnum() : "") + (entry.getValue().isOnStack() ? " stackPosition:" + entry.getValue().getStackaddress() : ""));
        }
    }


}

class Location {
    private int regnum = -1;
    private int stackaddress = -1; //absolute stack position (counting 1 each entry)

    Location(int n, boolean onStack) {
        if (onStack) stackaddress = n;
        else regnum = n;
    }

    int getRegnum() {
        return regnum;
    }

    int getStackaddress() {
        return stackaddress;
    }

    void noMoreInReg() {
        regnum = -1;
    }

    void noMoreOnStack() {
        stackaddress = -1;
    }

    void setRegnum(int regnum) {
        this.regnum = regnum;
    }

    void setStackaddress(int stackaddress) {
        this.stackaddress = stackaddress;
    }

    boolean isInReg() {
        return regnum >= 0;
    }

    boolean isOnStack() {
        return stackaddress >= 0;
    }
}