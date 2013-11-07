import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VirtualMachine{
  
  private LinkedList<String[]> programArray = new LinkedList<String[]>(); /*Lista ligada (com Random Acess) que guarda instrues. Vetor-programa.*/
  private Hashtable<String, Integer> labelsHash = new Hashtable<String, Integer>();
  private static Parser filter;
  private StackElement myStack = new StackElement();
  private int pc;
  private int instructionCounter;
  private MachineStates myState;
  private static final int MAX_IC = 10;
  private int serialNumber;

  private enum SysCallOperations{
    WLK,
    FIRE,
    BOMB,
    TAKE,
    LOOK,
    ASK,
    EXC
  }
  private enum SetOperation{ 
    PUSH, 
    POP, 
    DUP, 
    PRN,
    SHW,
    RCL,
    STO,
    END,
    ADD,
    SUB,
    MUL,
    DIV,
    EQ,
    GT,
    GE,
    LT,
    LE,
    NE,
    JIT,
    JIF,
    JMP
  }
  private enum MachineStates{
    WAITING,
    RUNNING,
    CALLING
  }
  public VirtualMachine(String sourceCode,int serialNumber) throws IOException{
    this.startSourceCode(sourceCode);
    this.myState = MachineStates.valueOf("WAITING");
    this.serialNumber = serialNumber;
  }

  private void startSourceCode(String sourceCode) throws IOException{
    this.programArray.clear();
    this.labelsHash.clear();
    filter.parseToMe(this.programArray, this.labelsHash, sourceCode);
    this.instructionCounter = 0;
    this.pc = 0;
  }

  private LinkedList<String[]> program(){
    return this.programArray;
  }

  private Hashtable<String, Integer> hashlables(){
    return this.labelsHash;
  }

  void showProgram(){
   for(int i = 0; i < programArray.size(); i++)
     System.out.println(programArray.get(i)[0]+" "+programArray.get(i)[1]);
  }

  void showHash(){
    Enumeration<String> enumKey = labelsHash.keys();
    while(enumKey.hasMoreElements()) {
      String key = enumKey.nextElement();
      Integer val = labelsHash.get(key);
      System.out.println("Chave : "+key +"\nValor : " +val);
    }
  }

  private void makeOperation(int index){
    boolean decision;
    String opCode = programArray.get(index)[0];
    try{
      SetOperation myOperation = SetOperation.valueOf(opCode);
      switch (myOperation) {
        case PUSH:
          try{
            myStack.pile(Double.parseDouble(programArray.get(index)[1]));

          }catch(NumberFormatException e1){
            myStack.pile(programArray.get(index)[1]);
          }
          break;
        case POP:
          myStack.discartTop();
          break;
        case DUP:
          myStack.dupTop();
          break;
        case PRN:
          myStack.printTop();
          break;
        case SHW:
          myStack.printStack();
          break;
        case RCL:
          try{
            myStack.retriveMem(Integer.parseInt(programArray.get(index)[1]));
          }catch(NumberFormatException e1){
            System.out.println("Ocorreu um Erro na RCL Operation");
          }
            break;
        case STO:
          try{
            myStack.salveMem(Integer.parseInt(programArray.get(index)[1]));
          }catch(NumberFormatException e1){
            System.out.println("Ocorreu um Erro na STO Operation");
          }
          break;
        case END:
          this.pc = programArray.size() +1;
          break;
        case ADD:
          myStack.operation(0);
          break;
        case SUB:
          myStack.operation(1);
          break;
        case MUL:
          myStack.operation(2);
          break;
        case DIV:
          myStack.operation(3);
          break;
        case EQ:
          myStack.operation(4);
          break;
        case GT:
          myStack.operation(5);
          break;
        case GE:
          myStack.operation(6);
          break;
        case LT:
          myStack.operation(7);
          break;
        case LE:
          myStack.operation(8);
          break;
        case NE:
          myStack.operation(9);
          break;
        case JIT:
          myStack.operation(10);
          decision = myStack.jumpTrue();
          if(decision){
            jumpPC(index);
          }
          break;
        case JIF:
          myStack.operation(11);
          decision = myStack.jumpFalse();
          if(!decision){
            jumpPC(index);
          }
          break;
        case JMP:
          jumpPC(index);
          break;
        default:
          System.out.println("Code : " +programArray.get(index)[0] +"\nArgs : "  +programArray.get(index)[1]);
          break;
      }

    }catch(IllegalArgumentException enumErro1){
      try{
        SysCallOperations mySysCall = SysCallOperations.valueOf(opCode);
        switch(mySysCall){
          case WLK:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          case BOMB:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          case FIRE:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          case TAKE:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          case LOOK:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          case ASK:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          case EXC:
            makeSysCall(programArray.get(index)[0],programArray.get(index)[1]);
            break;
          default:
            System.out.println("ERRO!");
            break;
          }
      }catch(IllegalArgumentException enumErro2){ 
        System.out.println("Code : " +programArray.get(index)[0] +"\nArgs : "  +programArray.get(index)[1] +"\nIt isn't a Valid Option!");    
        }
      }
  }
/*Se o programa contem uma instrução inválida a própria VM tem a obrigação de se colocar em Off*/
  private void jumpPC(int position){
    String key = programArray.get(position)[1] + ':';
    Integer val = labelsHash.get(key);
    this.pc = val -1;
  }

  private void setPC(int value){
    this.pc = value;
  }
  private int getPC(){
    return this.pc;
  }
  private void nextPc(){
    this.pc++;
  }
  private void setInstructionCounter(int x){
    this.instructionCounter = x;
  }
  private int getInstructionCounter(){
    return this.instructionCounter;
  }
  private void addInstructionCount(){
    this.instructionCounter++;
  }
  private void makeSysCall(String sysCallCode, String sysCallArg){
    setInstructionCounter(0);
    this.myState = MachineStates.valueOf("CALLING");
    SystemRequest newReq = new SystemRequest(sysCallCode,sysCallArg,serialNumber);
    Battlefield.systemCall(newReq);
    this.myState = MachineStates.valueOf("WAITING");
  }

  public int runCode(){
    this.myState = MachineStates.valueOf("RUNNING");
    while(this.myState.toString().compareTo("RUNNING") == 0){
      makeOperation(getPC());
      nextPc();
      addInstructionCount();
      if(getInstructionCounter() == MAX_IC){
        makeSysCall("None","None");
      }
      if (programArray.get(getPC())[0].compareTo("END") == 0){return -1;}
    }
    return 1;
  }

  public changeSourceCode(String newSource){
    myStack.eraseData();
    this.startSourceCode(newSource);
  }
}
