// Código para a impressão gráfica adaptado dos exemplos fornecidos pelo monitor Rafael de Assunção Sampaio.

import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.util.Random;
import java.lang.*;
import java.util.*;
import java.io.*;


public class Battlefield extends JFrame{
    
    //    DEFINES:
    private static final char CRYSTAL = '*';
    private static final char ROBOT = 'r';
    private static final char EMPTY = ' ';
    
    private static final int NUM_ROBOTS = 8;
    private static final int NUM_CRYSTALS = 10;
    
    private static final int HEXAGON_SIZE = 20;
    
    private enum SysCallOperations{
        WLK,
    	FIRE,
    	BOMB,
    	TAKE,
    	LOOK,
    	ASK,
    	NONE,
    	EXC
    }
    
    private enum DirMov{
        E,
    	W,
        SW,
    	SE,
    	NE,
    	NW
    }
    
    private enum AskOptions {
        MY_HEALTH,
        HAS_ENEMY,
        HAS_CRYSTAL,
        NUMBER_OF_CRYSTAL,
        ENEMY_BASE_DISTANCE,
        TEAM_BASE_DISTANCE,
        ITS_ENEMY,
        THEIR_HEALTH,
        WHY
    }
    static int[][] Terreno = { // O mapa
    {0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    {0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    {2, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 2},
    {2, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 2},
    {2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2},
    {2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 1, 1, 2},
    {2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
    {2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
    {2, 2, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2},
    {2, 2, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2},
    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0},
    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0}
    };
    
    static int Height = Terreno.length;
    static int Width = Terreno[0].length;
    
    
    //Virtual Machines Atributtes
    private static Vector<BattleRobot> army = new Vector<BattleRobot>(NUM_ROBOTS);
    private static Vector<Crystal> crystals = new Vector<Crystal>(NUM_CRYSTALS);
    
    private static int serialMachine;
    private static Vector<SystemRequest> requestList = new Vector<SystemRequest>(NUM_ROBOTS);
    public static final String codeName = "sourceCode";
    public static Campo thisIsMadness;
    
    //Transforma a classe em Singleton:
    
    
    private static Battlefield thisIsSparta = new Battlefield();
    //Trocar esse nome depois...   
    
    public static Battlefield getInstanceOfBattlefield(){
        return thisIsSparta;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    
    public static void main (String argv[]) throws IOException{
        // codeNameA = argv[0];
        // codeNameB = argv[1];
        
        initArena(Height, Width);
        rearrangeAll();
        //tellMeAboutTheWar();
        
        runtheGame();
        
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    		    Battlefield bf = Battlefield.getInstanceOfBattlefield();
    		    bf.setVisible(true);
    		}
        });
    	thisIsSparta.repaint();
    }
    
    /********************************************************************************/
    /**ROTINES FOR EXECUTE THE MAIN ACTIONS */
    public static void runtheGame(){
        int condition = 0;
        while (true){
            condition = rollTheDice();
            reloadArena();
            if(condition == NUM_ROBOTS){ break; }
            else{ sleepForaWhile(100);}
        }
        System.out.println("Execution ended");
    }
    
    public static int rollTheDice(){
        int cont = 0;
        for (int x = 0; x < NUM_ROBOTS; x++ ) {
            if (army.get(x).returnState() == 1){ army.get(x).runVM(); }
            else { cont++;}
        }
        return cont;
    }
    public static void reloadArena(){
        shuffleList();
        Iterator it = requestList.iterator();
        while(it.hasNext()){
            executeCall((SystemRequest)it.next());
        }
        requestList.clear();
        changeTheWorld();
    }
    
    private static void changeTheWorld() {
    	rearrangeAll();
        thisIsSparta.repaint();
    }
    
    private static void shuffleList(){
        long seed = System.nanoTime();
        Collections.shuffle(requestList, new Random(seed));
    }
    
    
    
    /********************************************************************************/
    /*SYSTEM CALLS*/
    public static void executeCall (SystemRequest request) {
        SysCallOperations op = SysCallOperations.valueOf( request.getInstructionRequest() );
        BattleRobot sony = getRobotBySerial(request.getSerialNumberRequester());
        double sucess = 0;
        //System.out.println("Request :" + request.getInstructionRequest() + " Peso : " + request.getWeight() + "Requester : "  + request.getSerialNumberRequester()); 
        switch(op) {
            case WLK:
                sucess = moveCall(request.getInstructionArgument(),request.getSerialNumberRequester());
                sony.returnAnswer(sucess);
                //sony.showCoordinates();
                //fazer push se a acao ocorreu ok!
                break;
            case FIRE:
                break;
            case BOMB:
                break;
            case TAKE:
                break;
            case LOOK:
                break;
            case ASK:
                sucess = askCall(request.getInstructionArgument(),request.getSerialNumberRequester());
                sony.returnAnswer(sucess);	    
                break;
            default:
                break;
        }
    }
    /********************************************************************************/
    /*ASK ACTION*/
    public static int askCall(String asked, int robotSerial){
        AskOptions question = AskOptions.valueOf(asked);
        BattleRobot sony = getRobotBySerial(robotSerial);
        switch(question){
            case MY_HEALTH:
                break;
            case HAS_ENEMY:
                break;
            case HAS_CRYSTAL:
                break;
            case NUMBER_OF_CRYSTAL:
                break;
            case ENEMY_BASE_DISTANCE:
                break;
            case TEAM_BASE_DISTANCE:
                break;
            case ITS_ENEMY:
                break;
            case THEIR_HEALTH:
                break;
            default:
                break;
        }
        return 0;
        
    }
    
    /********************************************************************************/
    /*MOVE ACTION*/
    
    public static boolean canMoveTo(int i, int j){
        Iterator it = army.iterator();
        BattleRobot hal;
        while(it.hasNext()){
            hal = (BattleRobot)it.next();
            if(i == hal.getX() && j == hal.getY())
                return false;
        }
        return true;
    }
    
    public static int moveCall(String direction, int robotSerial){
        BattleRobot sony = getRobotBySerial(robotSerial);
        DirMov dir = DirMov.valueOf( direction);
        int x = 0;
        int y = 0;
        switch(dir) {
            case E:
                if(canMoveTo((sony.getX() + 0)%Width, (sony.getY() + 1)%Height)){
                    sony.moveRobot((sony.getX() + 0)%Width, (sony.getY() + 1)%Height);
                    return 1;
                }
                break;
            case W:
                if(canMoveTo((sony.getX() + 0)%Width, (sony.getY() -1)%Height)){
                    sony.moveRobot((sony.getX() + 0)%Width, (sony.getY() + Height - 1)%Height);
                    
                    return 1;
                }
                break;
            case SE:
                if(canMoveTo((sony.getX() + 1)%Width, (sony.getY() + 0)%Height)){
                    sony.moveRobot((sony.getX() + 1)%Width, (sony.getY() + 0)%Height);
                    
                    return 1;
                }
                break;
            case NE:
                if(canMoveTo((sony.getX() - 1)%Width, (sony.getY() + 0)%Height)){
                    sony.moveRobot((sony.getX() - 1)%Width, (sony.getY() + 0)%Height);
                    
                    return 1;
                }
                break;
            case SW:
                if(canMoveTo((sony.getX() + 1)%Width, (sony.getY() - 1)%Height)){
                    sony.moveRobot((sony.getX() + Width - 1)%Width, (sony.getY() + Height - 1)%Height);
                    
                    return 1;
                }
                break;
            case NW:
                if(canMoveTo((sony.getX() - 1)%Width, (sony.getY() - 1)%Height)){
                    sony.moveRobot((sony.getX() + Width - 1)%Width, (sony.getY() + Height - 1)%Height);
                    return 1;
                }
                break;
            default:
                break;
        }
        
        return 0;
    }
    
    public static BattleRobot getRobotBySerial(int robotSerial){
        Iterator it = army.iterator();
        BattleRobot wally = null;
        while(it.hasNext()){
            wally = (BattleRobot)it.next();
            if (wally.saySerialNumber() == robotSerial){
                break;
            }
        }
        return wally;
    }
    /********************************************************************************/
    /*DEBUG FUNCTIONS*/
    
    public static void tellMeAboutTheWar(){
        System.out.println("\tInformacao sobre os robos\n" );
        for (int x = 0; x < NUM_ROBOTS ; x++) {
            System.out.println("Name : " + army.get(x).sayName()
                               +"\nSerial Number : " +army.get(x).saySerialNumber());
            System.out.println("Team : " + army.get(x).getTeam());
            System.out.print("Position : ");
            army.get(x).showCoordinates();
            System.out.println();
            // sleepForaWhile(1000);
        }
    }
    
    public static void sleepForaWhile(int time) {
        try{
            Thread.sleep(time);
            // System.out.println("\n Sai!!!! \n");   
        }
        catch(InterruptedException e){
            System.out.println("Deu merda!");
        }
        
    }
    /********************************************************************************/
    
    public static void systemCall(SystemRequest request){
        sleepForaWhile(100);
        //request.showRequest();
        requestList.add(request);
    }
    
    /********************************************************************************/
    public static void rearrangeAll(){
       	thisIsMadness.setRobots(army, thisIsMadness.getDx(), thisIsMadness.getDy(), HEXAGON_SIZE);
        thisIsMadness.setCrystals(crystals, thisIsMadness.getDx(), thisIsMadness.getDy(), HEXAGON_SIZE);
        
    }
    /********************************************************************************/
    /*INITIALIZE ARENA AND HIS ELEMENTS*/
    
    public static void insertArmy(String sourceCode,int index, String team, String robotModel, int x, int y, int serialNumber) throws IOException{
        army.add(index, new BattleRobot(robotModel+"-" + serialNumber, serialNumber,sourceCode));
        army.get(index).setTeam("Team "+team);
        army.get(index).moveRobot(x,y);
    }
    
    static void initArena(int mapHeight, int mapWidth) throws IOException{
        
        Random gen = new Random();
        //gen.setSeed(3);
        
        int i;
        int j;
        
        //        Inserts robots at totally random locations
        for (int k = 0; k < NUM_ROBOTS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            if (k < NUM_ROBOTS/2){
                insertArmy(codeName + "-" + k,k,"A","TX",j,i,gen.nextInt(1000));
	        }
            else{
                insertArmy(codeName + "-" + k,k,"B","ZT",j,i,gen.nextInt(1000));
            }
        }
        //        Inserts crystals at totally random locations
        for (int k = 0; k < NUM_CRYSTALS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            
            crystals.add(k, new Crystal(j,i,"FREE"));
        }
    }
    
    
    /********************************************************************************/
    /*CONSTRUCTOR*/
    
    public Battlefield(){
        setTitle("BATTLEFIELD");
        
        int m = 1200;
        int n = 720;
        
        setSize(m, n);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
	    });
        
        
        thisIsMadness = new Campo(HEXAGON_SIZE, m, n, Terreno, army, crystals);
        add(thisIsMadness);
        setVisible(true);
    }
    
}
