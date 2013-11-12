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

class Celula { // Cada célula da matriz representa um hexágono mostrado na tela
    Polygon p = new Polygon();
    BufferedImage ime;
    Graphics2D Gime;
    
    Celula(int x, int y, int r, BufferedImage t) {
        ime = t;
        
        for (int i = 0; i < 6; i++)
            p.addPoint(x + (int) (r * Math.sin(i * 2 * Math.PI / 6)),
                       y + (int) (r * Math.cos(i * 2 * Math.PI / 6)));
		
        Gime = ime.createGraphics();
    }
    
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle r = new Rectangle(0,0,100,100);
        g2d.setPaint(new TexturePaint(ime, r));
        g2d.fill(p);
    }
    
    void trans(int dx, int dy) {
        p.translate(dx, dy);
    }
}

class CelRobo { // Cada célula da matriz representa um hexágono mostrado na tela
    BufferedImage ime;
    Graphics2D Gime;
    Point origin;
    
    CelRobo(int x, int y, int r, BufferedImage t) {
        ime = t;
        
        origin = new Point(x, y);
        
        Gime = ime.createGraphics();
    }
    
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ime, (int)origin.getX(), (int)origin.getY(), null);
    }
}

class Campo extends JPanel { // Campo representa o mapa da arena, e cuida do output gráfico
    int Larg, Alt, Dx, Dy; // largura do terreno, altura do terreno, incremento em x e incremento em y
    BufferedImage grama, terra, agua, baseA, baseB, roboA, roboB; // texturas a serem carregadas para o terreno
    
    int[][] Terreno;
    
    int m; //Dimensões do mapa
    int n;
    
    Celula[][] cel; // define a matriz de células do terreno
    
    public CelRobo[][] robos;
    
    public BufferedImage[] Textura;

    private void initTexturas(){
	
        // cada try..catch que segue carregará uma textura, ou levantará uma exceção que encerrará a aplicação com erro
        try {
            grama = ImageIO.read(this.getClass().getResource("grama.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            terra = ImageIO.read(this.getClass().getResource("terra.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            agua = ImageIO.read(this.getClass().getResource("agua.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            baseA = ImageIO.read(this.getClass().getResource("baseA.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            baseB = ImageIO.read(this.getClass().getResource("baseB.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            roboA = ImageIO.read(this.getClass().getResource("roboA.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            roboB = ImageIO.read(this.getClass().getResource("roboB.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        BufferedImage[] imageArray = {agua, terra, grama, baseA, baseB, roboA, roboB};
	this.Textura = imageArray;
    }

    public void setRobots(Vector<BattleRobot> army, int Dx, int Dy, int L){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                robos[i][j] = null;
            }
        }
	
        Iterator itr = army.iterator();
        while(itr.hasNext()){
            BattleRobot robot = (BattleRobot) itr.next();
            int posX = robot.getX();
            int posY = robot.getY();
            
            String team = robot.getTeam();
            
            double psi = 0;
            if (posY%2 == 1)
                psi = 0.6;
            
            if( team.equals("Team A"))
                robos[posX][posY] = new CelRobo( (int)((posX + psi)*Dx), posY*Dy, L, Textura[5]);
            else
                robos[posX][posY] = new CelRobo( (int)((posX + psi)*Dx), posY*Dy, L, Textura[6]);
        }
    }

    public int getDx(){
	return this.Dx;
    }
    public int getDy(){
	return this.Dy;
    }
    
    Campo(int L, int W, int H, int[][] Terreno, Vector<BattleRobot> army) {
        this.Terreno = Terreno;
        this.m = Terreno[0].length;
        this.n = Terreno.length;
        this.cel = new Celula[m][n];
        
        this.robos = new CelRobo[m][n];
        
        Dx = (int) (2 * L * Math.sin(2 * Math.PI / 6)); // incremento em x para desenhar os hexágonos
        Dy = 3* L/2; // idem para y
        Larg = W; Alt = H;
        
	initTexturas();

        int DELTA = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // instância das células hexagonais, com as texturas adequadas, e atribuição destas ao mapa (a ser renderizado em paintComponent)
                cel[i][j] = new Celula(DELTA + L + i*Dx, L + j*Dy, L, Textura[Terreno[j][i]]);
                DELTA = DELTA == 0 ? Dx/2 : 0;
            }
        }

	setRobots(army,Dx, Dy, L);
        
    }
    
    public void paintComponent(Graphics g) { // Função chamada automaticamente pelo java
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cel[i][j].draw(g); // pinta as células no contexto gráfico
        
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (robos[i][j] != null) {
                    robos[i][j].draw(g); // pinta as células no contexto gráfico
                }
    }
}


public class Battlefield extends JFrame{
    
    //    DEFINES:
    private static final char CRYSTAL = '*';
    private static final char ROBOT = 'r';
    private static final char EMPTY = ' ';
    
    private static final int NUM_ROBOTS = 8;
    private static final int NUM_CRYSTALS = 10;
    
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
    static int[][] Terreno = { // O mapa
	{0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
	{2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 2},
	{2, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2},
	{2, 1, 2, 2, 0, 0, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2},
	{2, 1, 2, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 2, 1, 2},
	{2, 1, 2, 2, 2, 1, 1, 0, 0, 2, 2, 2, 2, 2, 1, 2},
	{2, 1, 2, 1, 1, 1, 2, 2, 2, 0, 0, 2, 2, 2, 1, 2},
	{2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 1, 2},
	{2, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 2},
	{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0}
    };
    
    //    Attributes
    static Entity[][] arena;
    
    //Virtual Machines Atributtes
    private static Vector<BattleRobot> army = new Vector<BattleRobot>(NUM_ROBOTS);
    private static int serialMachine;
    private static Vector<SystemRequest> requestList = new Vector<SystemRequest>(NUM_ROBOTS);
    public static final String codeName = "sourceCode";
    public static Campo thisIsMadness;
    
    //Transforma a classe em Singleton:
    

    private static Battlefield thisIsSparta = new Battlefield();
   

    public static Battlefield getInstanceOfBattlefield(){
	return thisIsSparta;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    
    public static void main (String argv[]) throws IOException{
        // codeNameA = argv[0];
        // codeNameB = argv[1];
        initArena(Terreno.length, Terreno[0].length);
        rearrangeRobots();
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
            else{ sleepForaWhile(300);}
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
	rearrangeRobots();
        thisIsSparta.repaint();
    }
    
    private static void shuffleList(){
        long seed = System.nanoTime();
        Collections.shuffle(requestList, new Random(seed));
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
    
    public static void executeCall (SystemRequest request) {
        SysCallOperations op = SysCallOperations.valueOf( request.getInstructionRequest() );
        //System.out.println("Request :" + request.getInstructionRequest() + " Peso : " + request.getWeight() + "Requester : "  + request.getSerialNumberRequester()); 
        switch(op) {
	case WLK:
	    BattleRobot sony = getRobotBySerial(request.getSerialNumberRequester());
	    //sony.showCoordinates();
	    int k = moveCall(request.getInstructionArgument(),request.getSerialNumberRequester());
	    //sony.showCoordinates();
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
	    break;
	default:
	    break;
        }
    }

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
        //System.out.println("Request :" + request.getInstructionRequest() + " Peso : " + request.getWeight() + "Requester : "  + request.getSerialNumberRequester()); 
        switch(dir) {
	case E:
	    if(canMoveTo((sony.getX() + 0)%16, (sony.getY() + 1)%10)){
		sony.moveRobot((sony.getX() + 0)%16, (sony.getY() + 1)%10);
		//thisIsSparta.repaint();
		return 1;
	    }
	    break;
	case W:
	    if(canMoveTo((sony.getX() + 0)%16, (sony.getY() -1)%10)){
		sony.moveRobot((sony.getX() + 0)%16, (sony.getY() +9)%10);
		//thisIsSparta.repaint();
		
		return 1;
	    }
	    break;
	case SE:
	    if(canMoveTo((sony.getX() + 1)%16, (sony.getY() + 0)%10)){
		sony.moveRobot((sony.getX() + 1)%16, (sony.getY() + 0)%10);
		//thisIsSparta.repaint();
		
		return 1;
	    }
	    break;
	case NE:
	    if(canMoveTo((sony.getX() - 1)%16, (sony.getY() + 0)%10)){
		sony.moveRobot((sony.getX() +15)%16, (sony.getY() + 0)%10);
		//thisIsSparta.repaint();
		
		return 1;
	    }
	    break;
	case SW:
	    if(canMoveTo((sony.getX() + 1)%16, (sony.getY() - 1)%10)){
		sony.moveRobot((sony.getX() + 1)%16, (sony.getY() +9)%10);
		//thisIsSparta.repaint();
		
		return 1;
	    }
	    break;
	case NW:
	    if(canMoveTo((sony.getX() - 1)%16, (sony.getY() - 1)%10)){
		sony.moveRobot((sony.getX() +15 )%16, (sony.getY() +9)%10);
		//thisIsSparta.repaint();
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
    
    /********************************************************************************/
    
    public static void systemCall(SystemRequest request){
        sleepForaWhile(500);
        request.showRequest();
        requestList.add(request);
    }
    
    /********************************************************************************/
    public static void rearrangeRobots(){
       	thisIsMadness.setRobots(army, thisIsMadness.getDx(), thisIsMadness.getDy(), 40);
    }
    /********************************************************************************/
    
    public static void insertArmy(String sourceCode,int index, String team, String robotModel, int x, int y, int serialNumber) throws IOException{
        army.add(index, new BattleRobot(robotModel+"-" + serialNumber, serialNumber,sourceCode));
        army.get(index).setTeam("Team "+team);
        army.get(index).moveRobot(x,y);
    }
    
    /********************************************************************************/
    
    static void initArena(int mapHeight, int mapWidth) throws IOException{
        arena = new Entity[mapHeight][mapWidth];
        
        Random gen = new Random();
        //gen.setSeed(3);
        
        int i;
        int j;
        
        //        Initializes the matriz with spaces
        for (i = 0; i < mapHeight; i++) {
            for (j = 0; j < mapWidth; j++) {
                arena[i][j] = null;
            }
        }
        
        //        Inserts robots at totally random locations
        for (int k = 0; k < NUM_ROBOTS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            arena[i][j] = new Entity(ROBOT, i, j);
            if (k < NUM_ROBOTS/2){
                insertArmy(codeName + "-" + k,k,"A","TX",j,i,gen.nextInt(1000));
	        }
            else{
                insertArmy(codeName + "-" + k,k,"B","ZT",j,i,gen.nextInt(1000));
            }
            // System.out.println("Robo #"+k + "\n("+i+","+j+")");
            // System.out.println();
        }
        
        //        Inserts crystals at totally random locations
        for (int k = 0; k < NUM_CRYSTALS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            arena[i][j] = new Entity(CRYSTAL, i, j);
        }
    }
    
    /********************************************************************************/
    
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
	
	/*try{
	    initArena(Terreno.length, Terreno[0].length);
	}catch(IOException e){
	    System.out.println("Arena unvailable.");
	    System.exit(1);
	    }*/
	thisIsMadness = new Campo(40, m, n, Terreno, army);
        add(thisIsMadness);
        setVisible(true);
    }
    
}
