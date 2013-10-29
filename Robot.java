import java.lang.*;
import java.util.*;

interface Robot{
	public void setTeam(String t);
	public String getTeam();
	//---------------------------------------------------------	
	public void reloadHealth(double x);
	public double getHealth();
	//---------------------------------------------------------
	//public void setNewRoutine(String sourceCode);
	//public void destroyRoutine();
	//---------------------------------------------------------
	public void moveRobot(int x, int y);
	public void showCoordinates();
	//---------------------------------------------------------
	//public void newExecutionCicle();
	//public SystemRequest getSystemCall();
	//public void resetRobot();
	//---------------------------------------------------------
	//public void setRobotNumber(int serialNumber);
	//public int getRobotNumber();
}