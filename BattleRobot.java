import java.lang.*;
import java.util.*;
import java.io.*;

public class BattleRobot implements Robot{
    
    //Robot Attributes
	private String robotName;
	private String robotTeam;
	private double robotHealth = 100.0;
	private Vector<Integer> coordinates = new Vector<Integer>(2);
	private int serialNumber;
	private int crystal;
	private VirtualMachine vm;
	private String robotState;
	private final Integer MAX_CRYSTAL = 3;
	private final Integer MAX_BOMB = 10;
	private Vector<Bomb> groundMine = new Vector<Bomb>(MAX_BOMB);
    
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

	public void returnAnswer(Double answer){
		vm.pushAnswer(answer);
	}
	public void returnAnswer(String answer){
		vm.pushAnswer(answer);
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
    public int incrementCrystalCount(){
    	if (getCrystalQuantity() < MAX_CRYSTAL){
    		this.crystal++;
  			return 1;
    	}
    	else{
    		return 0;
    	}
    }
    public Double getCrystalQuantity(){
    	return 1.0*this.crystal;
    }
	//---------------------------------------------------------
	public void updateHealth(double x){
		this.robotHealth = this.robotHealth + x;
	}
	public double getHealth(){
		return this.robotHealth;
	}
	public void turnOff(){
		this.robotState = "Off";
	}
	//---------------------------------------------------------
	//---------------------------------------------------------
	public void moveRobot(int x, int y){
		this.coordinates.add(0,x);
		this.coordinates.add(1,y);
	}
	public void showCoordinates(){
		//System.out.println("X : " + this.coordinates.get(0) + "\nY : " + this.coordinates.get(1));
		System.out.println("(" + this.coordinates.get(0) + "," +this.coordinates.get(1) + ")");
	}
	//---------------------------------------------------------

	public void initBomb(){
		for (int x = 0; x < MAX_BOMB; x++){
			groundMine.add(x,new Bomb(getX(),getY(),"Holding"));
		}
	}

	public Bomb placeTheBomb(int x, int y){
		int size = groundMine.size();
		if (size > 0){
			Bomb b = groundMine.remove(size-1);
			b.plantTheBomb(x, y);
			return b;
		}
		else return null;
	}
}
