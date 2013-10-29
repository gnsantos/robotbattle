import java.io.*;
import java.util.*;
import java.lang.*;

public class SystemRequest{
	private String instructionRequest;
	private String instructionArgument;
	private int serialNumberRequester;
	public SystemRequest(String opCode, String arg,int serialNumber){
		this.instructionRequest = opCode;
		this.instructionArgument = arg;
		this.serialNumberRequester = serialNumber;
	}

	public void showRequest(){
		System.out.println("Opcode : " +this.instructionRequest);
		System.out.println("Argument : " +this.instructionArgument);
		System.out.println("Robot Serial Number : " +this.serialNumberRequester);
	}
}