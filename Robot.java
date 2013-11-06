import java.lang.*;
import java.util.*;

interface Robot{
    public int runVM();
    public String sayName();
    public int saySerialNumber();
    public boolean positionScanner(int x, int y);
    public int getX();
    public int getY();
    public void setTeam(String team);
    public String getTeam();
    public void reloadHealth(double x);
    public double getHealth();
    public void moveRobot(int x, int y);
    public void showCoordinates();
}