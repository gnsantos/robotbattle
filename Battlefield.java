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
    
    CelRobo[][] robos;
    
    Campo(int L, int W, int H, int[][] Terreno, Vector<BattleRobot> army) {
        this.Terreno = Terreno;
        this.m = Terreno[0].length;
        this.n = Terreno.length;
        this.cel = new Celula[m][n];
        
        this.robos = new CelRobo[m][n];
        
	Dx = (int) (2 * L * Math.sin(2 * Math.PI / 6)); // incremento em x para desenhar os hexágonos
	Dy = 3* L/2; // idem para y
	Larg = W; Alt = H;
        
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
        
	BufferedImage[] Textura = {agua, terra, grama, baseA, baseB, roboA, roboB}; // array de texturas (valores de enumeração: 0, 1, 2)
        
	int DELTA = 0;
	for (int i = 0; i < m; i++) {
	    for (int j = 0; j < n; j++) {
		// instância das células hexagonais, com as texturas adequadas, e atribuição destas ao mapa (a ser renderizado em paintComponent)
		cel[i][j] = new Celula(DELTA + L + i*Dx, L + j*Dy, L, Textura[Terreno[j][i]]);
		DELTA = DELTA == 0 ? Dx/2 : 0;
	    }
	}
        
	for (int i = 0; i < m; i++) {
	    for (int j = 0; j < n; j++) {
		// instância das células hexagonais, com as texturas adequadas, e atribuição destas ao mapa (a ser renderizado em paintComponent)
		robos[i][j] = null;
	    }
	}
        
	Iterator itr = army.iterator();
	while(itr.hasNext()){
	    BattleRobot robot = (BattleRobot) itr.next();
	    int posX = robot.getX();
	    int posY = robot.getY();
	    String team = robot.getTeam();
	    if( team.equals("Team A") )
		robos[posX][posY] = new CelRobo( posX*Dx, posY*Dy, L, Textura[5]);
	    else
		robos[posX][posY] = new CelRobo( posX*Dx, posY*Dy, L, Textura[6]);
	}
        
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


public class Battlefield extends Frame{
    
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
        ASK
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
    private static Queue<SystemRequest> requestQueue = new LinkedList<SystemRequest>();
    public static String codeNameA;
    public static String codeNameB;
    
    
    public static void main (String argv[]) throws IOException {
	codeNameA = argv[0];
	codeNameB = argv[1];
	        
	initArena(Terreno.length, Terreno[0].length);
	        
	tellMeAboutTheWar();
	runtheGame();
        
        SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		    Battlefield bf = new Battlefield();
		    bf.setVisible(true);
		}
	    });
    }
    
    /********************************************************************************/
    
    public static void runtheGame(){
        for(int x = 0; x < NUM_ROBOTS;){
            if(requestQueue.size() == 4 ){  queueProcessing(); }
            if(army.get(x).runVM() !=  1){x++;}
        }
        if(requestQueue.size() > 0){ queueProcessing();}
    }
    
    /********************************************************************************/
    
    public void executeCall (SystemRequest request) {
        SysCallOperations op = SysCallOperations.valueOf( request.getInstructionRequest() );
        
        switch(op) {
	case WLK:
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
    
    /********************************************************************************/
    
    public static void queueProcessing(){
        System.out.println("\n-------------------------------------\n");
        System.out.println("New Queue Processing\n" );
        while(requestQueue.size() != 0){
            requestQueue.poll().showRequest();
            System.out.println();
        }
        System.out.println("\n-------------------------------------\n");
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
        }
    }
    
    /********************************************************************************/
    
    public static void systemCall(SystemRequest request){
        requestQueue.add(request);
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
                insertArmy(codeNameA,k,"A","TX",j,i,gen.nextInt(1000));
            }
            else{
                insertArmy(codeNameB,k,"B","ZT",j,i,gen.nextInt(1000));
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
    
    public Battlefield() {
        setTitle("DOOOOOOOOOOOOOOM.");
        
        int m = 1200;
        int n = 720;
        
        setSize(m, n);
        addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		    System.exit(0);
		}
	    });
        add(new Campo(40, m, n, Terreno, army));
        setVisible(true);
    }
    
}
