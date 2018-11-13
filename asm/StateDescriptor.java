package asm;

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
    private VarLocationTracker varTracker;
    private int roundRobinSpill = V1;

    StateDescriptor(ASMCode asmCode) {
        this.asmCode = asmCode;
        varTracker = new VarLocationTracker();
        memoryMap = new MemoryMap(varTracker);
        varN = 0;
    }

    int getReg(String var) {
        String varName = getVarName(var);
        int regnum;
        if (varTracker.isInReg(varName)) {
            regnum = varTracker.getVarRegnum(varName);
        }
        //need to put that var in a reg
        else if (memoryMap.thereIsAvailReg()) {
            regnum = memoryMap.getFirstAvailReg();
            //var must be on stack
            emitLoadReg(regnum, FP, -memoryMap.calculateFPOffset(varName));
        } else {
            regnum = spillReg();
            emitLoadReg(regnum, FP, -memoryMap.calculateFPOffset(varName));
        }
        return regnum;
    }

    //get Reg for any constant
    int getReg(int var, boolean isDataLabel) {
        int regnum;
        if (memoryMap.thereIsAvailReg()) {
            regnum = memoryMap.getFirstAvailReg();
        } else {
            regnum = spillReg();
        }
        if (isDataLabel) {
            emitLoadReg(regnum, var, true);
        } else {
            emitMov(regnum, var, false);
        }
        return regnum;
    }

    void placeVarValueInReg(int destreg, String var) {
        String varName = getVarName(var);
        if (varTracker.isInReg(varName)) emitMov(destreg, varTracker.getVarRegnum(varName), true);
        else if (varTracker.isOnStack(varName)) {
            emitLoadReg(destreg, FP, -memoryMap.calculateFPOffset(varName));
        } else {
            //TODO it's on the heap? but double check otherwise error
            ASMGeneratorVisitor.error("var " + varName + " is not placed anywhere");
        }
    }

    // this gives the variable a unique name, needed as it is the identifier inside a function frame
    private String getVarName(String var) {
        return var + varN;
    }

    //returns register which is now free for use
    private int spillReg() {
        //spill register from one of the v1-5 doing round robin
        if (roundRobinSpill == StateDescriptor.V5) roundRobinSpill = V1;
        else roundRobinSpill++;

        //make reg safe to reuse
        Word wordToSpill = memoryMap.getRegContent(roundRobinSpill);
        if (wordToSpill.isVar() && varTracker.isOnStack(wordToSpill.varName)) {
            emitStoreReg(roundRobinSpill, FP, -memoryMap.calculateFPOffset(wordToSpill.varName));
        } else {
            emitPush(new int[]{roundRobinSpill});
        }

        return roundRobinSpill;
    }

    void reserveStackWordForVar(String var) {
        memoryMap.push(new Word(getVarName(var)));
    }

    // push {fp, lr, v1, v2, v3, v4, v5}
    void emitPush(int[] regs) {
        Word w = memoryMap.getRegContent(regs[0]);
        memoryMap.push(w);
        memoryMap.getRegContent(StateDescriptor.SP).val -= 4 * regs.length;
        StringBuilder r = new StringBuilder("r" + regs[0]);
        for (int i = 1; i < regs.length; i++) {
            memoryMap.push(memoryMap.getRegContent(regs[i]));
            r.append(", r").append(regs[i]);
        }
        asmCode.addToText("push {" + r + "}");
    }

    void emitPop(int[] regs) {
        memoryMap.pop();
        memoryMap.getRegContent(StateDescriptor.SP).val += 4 * regs.length;
        StringBuilder r = new StringBuilder("r" + regs[0]);
        for (int i = 1; i < regs.length; i++) {
            r.append(", r").append(regs[i]);
            memoryMap.pop();
        }
        asmCode.addToText("pop {" + r + "}");
    }

    // ldr r0, [sp, #offset]
    void emitLoadReg(int destreg, int base_reg, int offset) {
        Word wordBeingLoaded = memoryMap.getWordFromStack(memoryMap.getRegContent(base_reg).val + offset);
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

    //str r0, [sp, #8]
    void emitStoreReg(int sourcereg, int base_reg, int offset) {
        memoryMap.updateStackWord(memoryMap.getRegContent(sourcereg), memoryMap.getRegContent(base_reg).val + offset);
        asmCode.addToText("str r" + sourcereg + ", [r" + base_reg + ", #" + offset + "]");
    }

    //mov r0, r2
    //mov r0, #5
    void emitMov(int destreg, int sourcereg, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg));
            asmCode.addToText("mov r" + destreg + ", r" + sourcereg);
        } else {
            memoryMap.updateRegValue(destreg, new Word(sourcereg));
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

    //push {fp,lr,v1,v2,v3,v4,v5}
    //add fp, sp, #24
    void funcPrologue() {
        int[] toPush = new int[]{StateDescriptor.FP, StateDescriptor.LR, StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5};
        emitPush(toPush);
        emitAdd(StateDescriptor.FP, StateDescriptor.SP, 24, false);
    }

    //sub sp, fp, #24
    //pop {fp,lr,v1,v2,v3,v4,v5}
    void funcEpilogue() {
        emitSub(StateDescriptor.SP, StateDescriptor.FP, 24, false);
        emitPop(new int[]{StateDescriptor.FP, StateDescriptor.PC, StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5});
        varN--;
    }

    void emitBranch() {
        varN++;
    }

    void printState() {
        memoryMap.printState();
    }
}

/////////////////////////////////////////////////////////////////////////////////
////////////////////////// Memory Map ///////////////////////////////////////////

class MemoryMap {
    private Word[] registers;
    private Stack<Word> stack; //when accessing the stack, an address translation is needed
    private VarLocationTracker varTracker;


    MemoryMap(VarLocationTracker varTracker) {
        final int regs = 16;
        registers = new Word[regs];
        for (int i = 0; i < regs; i++) {
            registers[i] = new Word();
        }
        registers[StateDescriptor.FP] = new Word(4);
        registers[StateDescriptor.SP] = new Word(4);
        stack = new Stack<>();
        this.varTracker = varTracker;
    }

    ////////////////////////// Registers Operations /////////////////////////////

    Word getRegContent(int regnum) {
        return registers[regnum];
    }

    void updateRegValue(int regnum, Word newWord) {
        //dereference previous
        if (getRegContent(regnum).isVar()) varTracker.removeVarFromReg(getRegContent(regnum).varName);
        //add new word
        if (newWord.isVar()) {
            varTracker.addVarToReg(newWord.varName, regnum);
        }
        registers[regnum] = newWord;
        //make sure SP and stack are always in synch
        //TODO not working
        if (regnum == StateDescriptor.SP) {
            int diff = stack.size() - mapToVirtual(getRegContent(StateDescriptor.SP).val);
            for (int i = 0; i < diff; i++) {
                stack.pop();
            }
        }
    }

    boolean thereIsAvailReg() {
        return getFirstAvailReg() >= 0;
    }

    int getFirstAvailReg() {
        int usefulregs = 8;
        for (int i = 0; i <= usefulregs; i++) {
            if (registers[i].isEmpty()) return i;
        }
        return -1;
    }

    ////////////////// Stack Operations //////////////////

    void push(Word w) {
        if (w.isVar()) varTracker.addVarToStack(w.varName, stack.size());
        stack.add(w);
    }

    void pop() {
        if (stack.peek().isVar()) varTracker.removeVarFromStack(stack.peek().varName);
        stack.pop();
    }

    //note that position will always be a multiple of 4
    Word getWordFromStack(int position) {
        return stack.get(mapToVirtual(position));
    }

    //returns positive distance from FP (multiple of 4)
    int calculateFPOffset(String var) {
        return -(mapToPhysical(varTracker.getStackPosition(var)) + getRegContent(StateDescriptor.FP).val);
    }

    //note that position will always be a multiple of 4
    void updateStackWord(Word newWord, int stackPosition) {
        //dereference previous
        if (getWordFromStack(stackPosition).isVar()) varTracker.removeVarFromStack(newWord.varName);
        //add new word
        if (newWord.isVar()) {
            varTracker.addVarToStack(newWord.varName, mapToVirtual(stackPosition));
        }
        stack.add(mapToVirtual(stackPosition), newWord);
    }

    void printState() {
        System.out.println("-----------regs-------------");
        for (int i = 0; i < registers.length; i++) {
            System.out.println("r" + i + " val: " + getRegContent(i).val + "; varName: " + getRegContent(i).varName);
        }
        System.out.println("-----------stack-------------");
        int i = 0;
        for (Word w : stack) {
            System.out.println(i + ":: val: " + w.val + "; varName: " + w.varName);
            i++;
        }
        varTracker.printState();

    }

    private int mapToVirtual(int address) {
        return (address / 4) * -1;
    }

    private int mapToPhysical(int address) {
        return (address * 4) * -1;
    }
}


/////////////////////////////////////////////////////////////////////
////////////////////////Utils////////////////////////////////////////

class Word {
    //reg content can be a variable or an int constant or an int datalabel
    String varName; //word content referenced by a variable
    int val = 0; //word int content (can also be pointer, an int constant, or a data section label id)
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