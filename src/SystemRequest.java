import java.io.*;
import java.util.*;
import java.lang.*;

public class SystemRequest{
	private String instructionRequest;
	private String instructionArgument;
	private int serialNumberRequester;
	private int requestWeight;
        
    private enum SysCallOperations{
        WLK,
        FIRE,
        BOMB,
        TAKE,
        DROP,
        LOOK,
        ASK,
        NONE,
        EXC
    }

	public SystemRequest(String opCode, String arg,int serialNumber){
		this.instructionRequest = opCode;
		this.instructionArgument = arg;
		this.serialNumberRequester = serialNumber;
		this.requestWeight = setWeight(opCode);
	}
	private int setWeight(String opCode){
	    SysCallOperations mySysCall = SysCallOperations.valueOf(opCode);
        switch(mySysCall){
            case WLK:
                return 1;
            case BOMB:
                return 2;
            case FIRE:
                return 3;
            case TAKE:
                return 4;
            case DROP:
                return 8;
            case LOOK:
                return 5;
            case ASK:
                return 6;
            case EXC:
                return 7;
            default:
                return 0;
        }
	}
	public int getWeight(){ 
		return this.requestWeight; 
	}
	public void showRequest(){
		System.out.println("Opcode : " +this.instructionRequest);
		System.out.println("Argument : " +this.instructionArgument);
		System.out.println("Robot Serial Number : " +this.serialNumberRequester + "\n");
	}
    public String getInstructionRequest () {
        return instructionRequest; 
    }
   	public int getSerialNumberRequester(){ 
   		return serialNumberRequester;
   	}
   	public String getInstructionArgument(){
   		return instructionArgument;
   	}
}