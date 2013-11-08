import java.lang.*;
import java.util.*;
import java.io.*;

public class BattleRobot implements Robot{
    
    //Robot Attributes
	private String robotName;
	private  String robotTeam;
	private double robotHealth = 100.0;
	private Vector<Integer> coordinates = new Vector<Integer>(2);
	private int serialNumber;
	private VirtualMachine vm;
	private String robotState;
    
	public BattleRobot(String name, int serialNumber, String sourceCode) throws IOException{
		this.robotName = name;
		this.serialNumber = serialNumber;
		this.vm = new VirtualMachine(sourceCode, this.serialNumber);
		this.robotState = "On";
	}
    
	public void runVM(){
		int state = this.vm.runCode();
		if(state == -1){ this.robotState = "Off"; }
	}

	// public void newInstructionCycle(){
	// 	novoVetorIns
	// }
	// public void turnOn(){}
	
	public int returnState(){ 
		if (this.robotState.equals("On")){ return 1;}
        return 0;
	}

	public String sayName(){
		return this.robotName;
	}
	public int saySerialNumber(){
		return this.serialNumber;
	}
	public boolean positionScanner(int x, int y){
		return (this.coordinates.get(0) == x && this.coordinates.get(1) == y);
	}
	public int getX(){return this.coordinates.get(0);}
	public int getY(){return this.coordinates.get(1);}
	//---------------------------------------------------------
	public void setTeam(String team){
		this.robotTeam = team;
	}
	public String getTeam(){
		return this.robotTeam;
	}
	//---------------------------------------------------------
	//---------------------------------------------------------
	public void reloadHealth(double x){
		this.robotHealth = this.robotHealth + x;
	}
	public double getHealth(){
		return this.robotHealth;
	}
	//---------------------------------------------------------
	//---------------------------------------------------------
	public void moveRobot(int x, int y){
		this.coordinates.add(0,x);
		this.coordinates.add(1,y);
	}
	public void showCoordinates(){
		System.out.println("X : " + this.coordinates.get(0) + "\nY : " + this.coordinates.get(1));
	}
	//---------------------------------------------------------
}