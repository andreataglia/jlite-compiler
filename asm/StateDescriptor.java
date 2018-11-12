package asm;

import nodes3.Id3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class StateDescriptor {
    private MemoryMap memoryMap;
    private ASMCode asmCode;
    private int varN; //must be updated every function call
    static final int A1 = 0;
    static final int A2 = 1;
    static final int LR = 14;
    static final int V1 = 4;
    static final int V2 = 5;
    static final int V3 = 6;
    static final int V4 = 7;
    static final int V5 = 8;
    static final int FP = 11; //reg 11 = fp, reg 13 = sp
    static final int SP = 13;
    static final int PC = 15;
    VarLocationTracker varTracker;

    StateDescriptor(ASMCode asmCode) {
        this.asmCode = asmCode;
        varTracker = new VarLocationTracker();
        memoryMap = new MemoryMap(varTracker);
        varN = 0;
    }

    int getReg(Id3 var) {
        String varName = getVarName(var);
        int regnum;
        if (varTracker.isInReg(varName)) regnum = varTracker.getVarRegnum(varName);
            //need to put that var in a reg
        else if (memoryMap.thereIsAvailReg()) {
            regnum = memoryMap.getFirstAvailReg();
            //var must be on stack
            emitLoadReg(regnum, FP, memoryMap.calculateFPOffset(varTracker.getStackPosition(varName)));
        } else {
            regnum = spillReg();
            emitLoadReg(regnum, FP, memoryMap.calculateFPOffset(varTracker.getStackPosition(varName)));
        }
        return regnum;
    }

    void placeVarInReg(int destreg, Id3 var) {
        String varName = getVarName(var);
        if (varTracker.isInReg(varName)) emitMov(destreg, varTracker.getVarRegnum(varName), true);
        else if (varTracker.isOnStack(varName)) {
            emitLoadReg(destreg, FP, memoryMap.calculateFPOffset(varTracker.getStackPosition(varName)));
        } else {
            //TODO it's on the heap? but double check otherwise error
            ASMGeneratorVisitor.error("var " + varName + " is not placed anywhere");
        }
    }

    // this gives the variable a unique name, needed as it is the identifier inside a function frame
    private String getVarName(Id3 var) {
        return var.id + varN;
    }

    //returns register which is now free for use
    private int spillReg() {
        //TODO implement good policy for reg spilling, for now just pick r4
        int regToSpill = 4;

        //make reg safe to reuse
        emitPush(new int[]{4});

        return regToSpill;
    }

    void reserveStackWordForVar(String var) {
        memoryMap.push(new Word(var));
    }

    // push {fp, lr, v1, v2, v3, v4, v5}
    void emitPush(int[] regs) {
        Word w = memoryMap.getRegContent(regs[0]);
        memoryMap.push(w);
        StringBuilder r = new StringBuilder("r" + regs[0]);
        for (int i = 1; i < regs.length; i++) {
            memoryMap.push(memoryMap.getRegContent(regs[i]));
            r.append(", r").append(regs[i]);
        }
        asmCode.addToText("push {" + r + "}");
    }

    void emitPop(int[] regs) {
        memoryMap.pop();
        StringBuilder r = new StringBuilder("r" + regs[0]);
        for (int i = 1; i < regs.length; i++) {
            r.append(", r").append(regs[i]);
            memoryMap.pop();
        }
        asmCode.addToText("pop {" + r + "}");
    }

    // ldr r0, [fp, #offset]
    void emitLoadReg(int destreg, int base_reg, int offset) {
        int calculatedAddrs = memoryMap.getFP() + offset / 4;
        Word wordBeingLoaded = memoryMap.getWordFromStack(calculatedAddrs);
        memoryMap.updateRegValue(destreg, wordBeingLoaded);
        asmCode.addToText("ldr r" + destreg + ", [r" + base_reg + ", #" + offset + "]");
    }

    //ldr r0, =L1
    //ldr r0, #5
    //if(isDataLabel) then it's the data label, otherwise is an int constant
    void emitLoadReg(int destreg, int n, boolean isDataLabel) {
        memoryMap.updateRegValue(destreg, new Word(n));
        if (isDataLabel) {
            asmCode.addToText("ldr r" + destreg + ", =L" + n);
        } else {
            asmCode.addToText("ldr r" + destreg + ", #" + n);
        }
    }

    //mov r0, r2
    //mov r0, #5
    void emitMov(int destreg, int sourcereg, boolean isOp2Reg) {
        memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg));
        if (isOp2Reg) {
            asmCode.addToText("mov r" + destreg + ", r" + sourcereg);
        } else {
            asmCode.addToText("mov r" + destreg + ", #" + sourcereg);
        }
    }

    //add r1, r4, #5
    //add r1, r4, r5
    void emitAdd(int destreg, int sourcereg, int op2, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.updateRegValue(destreg, new Word(memoryMap.getRegContent(sourcereg).val + memoryMap.getRegContent(op2).val));
            asmCode.addToText("add r" + destreg + ", r" + sourcereg + ", r" + op2);
        } else {
            memoryMap.updateRegValue(destreg, new Word(memoryMap.getRegContent(sourcereg).val + op2));
            asmCode.addToText("add r" + destreg + ", r" + sourcereg + ", #" + op2);
        }
    }

    //sub r1, r4, #5
    //sub r1, r4, r5
    void emitSub(int destreg, int sourcereg, int op2, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.updateRegValue(destreg, new Word(memoryMap.getRegContent(sourcereg).val - memoryMap.getRegContent(op2).val));
            asmCode.addToText("sub r" + destreg + ", r" + sourcereg + ", r" + op2);
        } else {
            memoryMap.updateRegValue(destreg, new Word(memoryMap.getRegContent(sourcereg).val - op2));
            asmCode.addToText("sub r" + destreg + ", r" + sourcereg + ", #" + op2);
        }
    }

}

/////////////////////////////////////////////////////////////////////
//////////////////////////Descriptors////////////////////////////////
class VarLocationTracker{
    //the variable unique names is given by the id + function number to distinguish between functions
    private HashMap<String, Location> locations;

    VarLocationTracker() {
        locations = new HashMap<>();
    }

    boolean isInReg(String var) {
        return locations.get(var).isInReg();
    }

    boolean isOnStack(String var) {
        return locations.get(var).isInStack();
    }

    int getVarRegnum(String var) {
        return locations.get(var).getRegnum();
    }

    //returns offset from fp
    int getStackPosition(String var) {
        return locations.get(var).getStackaddress();
    }

    void removeVarFromStack(String var) {
        locations.get(var).noMoreOnStack();
    }

    void removeVarFromReg(String var) {
        locations.get(var).noMoreInReg();
    }

    void addVarToStack(String var, int stackPosition){
        if (locations.get(var) != null) locations.get(var).setStackaddress(stackPosition);
        else locations.put(var, new Location(stackPosition, false));
    }

    void addVarToReg(String var, int regnum){
        if (locations.get(var) != null) locations.get(var).setRegnum(regnum);
        else locations.put(var, new Location(regnum, false));
    }
}


class MemoryMap {
    private ArrayList<Word> registers;
    private Stack<Word> stack; //all the stack operations works with unary vals
    private int fp;
    private VarLocationTracker varTracker;


    MemoryMap(VarLocationTracker varTracker) {
        this.registers = new ArrayList<>();
        int regs = 15;
        for (int i = 0; i <= regs; i++) {
            registers.add(new Word());
        }
        stack = new Stack<>();
        fp = 0;
        this.varTracker = varTracker;
    }

    ////////////////////////// Registers Operations /////////////////////////////

    Word getRegContent(int regnum) {
        return registers.get(regnum);
    }

    void updateRegValue(int regnum, Word newWord) {
        //dereference previous
        if (getRegContent(regnum).isVar()) varTracker.removeVarFromReg(getRegContent(regnum).varName);
        //add new word
        if (newWord.isVar()) {
           varTracker.addVarToReg(newWord.varName, regnum);
        }
        registers.add(regnum, newWord);
    }

    boolean thereIsAvailReg() {
        return getFirstAvailReg() >= 0;
    }

    int getFirstAvailReg() {
        int usefulregs = 8;
        for (int i = 0; i <= usefulregs; i++) {
            if (registers.get(i) == null) return i;
        }
        return -1;
    }

    ////////////////// Stack Operations //////////////////

    void push(Word w) {
        if (w.isVar()) varTracker.addVarToStack(w.varName, getStackSize());
        stack.add(w);
    }

    void pop() {
        if (stack.peek().isVar()) varTracker.removeVarFromStack(stack.peek().varName);
        stack.pop();
    }

    int getStackSize() {
        return stack.size();
    }

    Word getWordFromStack(int position) {
        if (position > getStackSize()) return new Word();
        return stack.get(position);
    }

    int calculateFPOffset(int addr) {
        return (addr - getFP());
    }

    void setFP(int fp) {
        this.fp = fp;
    }

    int getFP() {
        return fp;
    }

    ////////////////////// Addresses Operations ////////////////////////////

    void updateStackWord(Word newWord, int stackPosition) {
        //dereference previous
        if (getWordFromStack(stackPosition).isVar()) varTracker.removeVarFromStack(newWord.varName);
        //add new word
        if (newWord.isVar()) {
            varTracker.addVarToStack(newWord.varName, stackPosition);
        }
        stack.add(stackPosition, newWord);
    }
}

/////////////////////////////////////////////////////////////////////
////////////////////////Utils////////////////////////////////////////

class Location {
    private int regnum;
    private int stackaddress; //absolute stack position (counting 1 each entry)

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

    boolean isInStack() {
        return stackaddress >= 0;
    }
}

class Word {
    //reg content can be a variable or an int constant or an int datalabel
    String varName; //word content referenced by a variable
    int val; //word int content (can also be pointer, an int constant, or a data section label id)
    boolean isEmpty = false;

    Word(String varName) {
        this.varName = varName;
    }

    Word(int val) {
        this.val = val;
    }

    Word() {
        isEmpty = true;
    }

    boolean isVar() {
        return varName != null;
    }

    boolean isEmpty() {
        return isEmpty;
    }
}