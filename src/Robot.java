import java.lang.*;
import java.util.*;

interface Robot{
    public void runVM();
    public String sayName();
    public int saySerialNumber();
    public boolean positionScanner(int x, int y);
    public int getX();
    public int getY();
    public int incrementCrystalCount();
    public Double getCrystalQuantity();
    public void setTeam(String team);
    public String getTeam();
    public void updateHealth(double x);
    public double getHealth();
    public void moveRobot(int x, int y);
    public void showCoordinates();
}