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

    boolean isOnStack(String var) {
        return locations.get(var).isOnStack();
    }

    //returns offset from fp
    int getStackPosition(String var) {
        return locations.get(var).getStackaddress();
    }

    ////////////////////// SETTERS /////////////////////////

    void removeVarFromStack(String var) {
        locations.get(var).noMoreOnStack();
    }

    void addVarToStack(String var, int stackPosition) {
        if (locations.get(var) != null) locations.get(var).setStackaddress(stackPosition);
        else locations.put(var, new Location(stackPosition, true));
    }

    void addObjectToStack(String var, String cname){
        locations.get(var).setClassName(cname);
    }

    String getVarObject(String var){
        return locations.get(var).getClassName();
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
    private String className;

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

    void setClassName(String className) {
        this.className = className;
    }

    String getClassName(){
        return className;
    }

    boolean isInReg() {
        return regnum >= 0;
    }

    boolean isOnStack() {
        return stackaddress >= 0;
    }
}