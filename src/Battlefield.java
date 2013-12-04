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
import java.math.*;


public class Battlefield extends JFrame{
    
    /*
     * Defines 
     */
    private static final char CRYSTAL = '*';
    private static final char ROBOT = 'r';
    private static final char EMPTY = ' ';
    
    private static final int HEXAGON_SIZE = 20;
    
    // Coordenadas no mapa da base A
    private static final int BASE_A_X = 32;
    private static final int BASE_A_Y = 3;
    
    // Coordenadas no mapa da base B
    private static final int BASE_B_X = 4;
    private static final int BASE_B_Y = 19;
    
    // Valor default de dano que cada fonte pode causar
    private static final double FIRE_DAMAGE = -20;
    private static final double BOMB_DAMAGE = -50;
    private static final double WATER_DAMAGE = -3;
    
    // Possíveis chamadas do robô ao sistema
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
    
    // Possíveis direções de movimento (em um mapa hexagonal)
    private enum DirMov{
        E,
	    W,
	    SW,
	    SE,
	    NE,
	    NW
    }
    
    // Possíveis perguntas a serem feitas pelo robô ao sistema
    private enum AskOptions {
        MY_HEALTH,
	    HAS_ENEMY,
	    HAS_CRYSTAL,
	    NUMBER_OF_CRYSTAL,
        IS_IN_ENEMY_BASE,
	    ENEMY_BASE_DISTANCE,
	    TEAM_BASE_DISTANCE,
    }
    
    // Configuração do mapa
    static int[][] Terreno = {
    {0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
     {0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,4,4,2,2},
    {2,0,0,0,0,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,4,4,2},
     {2,2,0,0,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,4,2,2},
    {2,2,2,0,7,7,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,2,2},
     {2,2,1,1,7,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,2,2,1,1,1,2,2},
    {2,2,1,1,1,2,0,0,0,0,0,0,2,2,2,2,2,2,2,2,1,1,1,1,1,2,2,2,2,2,1,1,2,2},
     {2,2,1,1,2,2,2,2,0,0,0,0,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,2,2,2,1,1,2,2},
    {2,2,1,1,2,2,2,2,2,2,0,0,0,0,0,0,1,1,1,1,1,2,2,2,2,2,2,2,2,2,1,1,2,2},
     {2,2,1,1,2,2,2,2,2,2,0,0,0,0,0,0,0,1,1,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2},
    {2,2,1,1,2,2,2,2,2,2,1,1,1,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2},
     {2,2,1,1,2,2,2,2,1,1,1,1,1,1,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,1,1,2,2},
    {2,2,1,1,2,2,1,1,1,1,1,1,2,2,2,2,2,0,0,0,0,2,2,2,2,2,2,2,2,2,1,1,2,2},
     {2,2,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,0,0,0,0,0,2,2,2,2,2,2,2,1,1,2,2},
    {2,2,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,2,2,2,2,1,1,2,2},
     {2,2,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,2,2,1,1,2,2},
    {2,2,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,7,7,2,2},
     {2,2,3,3,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,7,7,0,0,0},
    {2,2,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,0,0,0},
     {2,2,3,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
    {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0},
     {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0}
    
    };
    
    
    
    // Dimensões do mapa
    static int Height = Terreno.length;
    static int Width = Terreno[0].length;
    
    private static int numRobots = 4;       // Número de robôs ativos
    private static int NUM_CRYSTALS = 15;   //Número de cristais ativos
    private static int NUM_DROPPED_CRYSTALS = 0;    // Número de cristais que já estão na base
    
    // Estruturas com as entidades do sistema
    private static Vector<BattleRobot> army = new Vector<BattleRobot>();            // Vetor com os robôs
    private static Vector<Crystal> crystals = new Vector<Crystal>(NUM_CRYSTALS);    // Vetor com os cristais
    private static Stack<Bomb> groundMine = new Stack<Bomb>();                      // Pilhas das bombas
    private static Stack<Bomb> mineExploded = new Stack<Bomb>();
    
    private static Vector<SystemRequest> requestList = new Vector<SystemRequest>(); // Vetor com as chamadas ao sistema
    
    public static final String codeName = "sourceCode";     // Nome do arquivo default de código fonte dos robôs
    
    public static Campo visualComponent;        // O controlador da parte gmovecráfica
    
    
    
    // Transforma a classe em Singleton:
    private static Battlefield controlador = new Battlefield();
    
    public static Battlefield getInstanceOfBattlefield(){
        return controlador;
    }
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // Main
    
    public static void main (String argv[]) throws IOException{
        // Inicializa o vetor de robôs e o de cristais
        initArena(Height, Width);
        
        // Atualiza as posições e dos cristais
        rearrangeAll();
        
        // Começa a execução do jogo
        runtheGame();
        
        // Paralelamente, atualiza o componente gráfico
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
		    public void run() {
    		    Battlefield bf = Battlefield.getInstanceOfBattlefield();
    		    bf.setVisible(true);
    		}
	    });
    	controlador.repaint();
    }
    
    // Construtor
    public Battlefield(){
        // Seta atributos da janela
        setTitle("BATTLEFIELD");
        
        int m = 1200;
        int n = 720;
        
        setSize(m, n);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
	    });
        
        // Cria o gerenciador gráfico
        visualComponent = new Campo(HEXAGON_SIZE, m, n, Terreno, army, crystals,groundMine);
        add(visualComponent);
        setVisible(true);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // Métodos principais
    
    public static void runtheGame(){
        int condition = 0;
        
        // Rodar enquanto houver robôs executando ações
        while (true){
            condition = runRobotStateCycle();
            processRequestList();
            
            if(condition == numRobots) {
                System.out.print("YOU LOSERS\n");
                break;
            }
            else if(NUM_DROPPED_CRYSTALS == 3) {
                System.out.print("GANHEI GG\n");
                break;
            }
            else
                pauseSystem(80);
        }
        endTheGame();
    }
    
    public static int runRobotStateCycle(){
        int cont = 0;
        
        // Para cada robô do vetor, roda algumas instruções dele (runVM)
        for (int x = 0; x < numRobots; x++ ) {
            if (army.get(x).returnState() == 1)
                army.get(x).runVM();
            else 
                cont++;
        }
        
        // Retorna o número de robôs inativos
        return cont;
    }
    
    public static void endTheGame() {
        System.out.print("Game ended.\n");
    }
    
    public static void processRequestList(){
        shuffleList();
        Iterator it = requestList.iterator();
        
        // Itera pela lista de chamadas e executa cada uma delas
        while(it.hasNext()){
            executeCall((SystemRequest)it.next());
        }
        
        requestList.clear();
        updateArena();
    }
    
    private static void updateArena() {
        // Atualiza as estruturas de dados conforme apropriado
        // e então atualiza a parte gráfica
    	rearrangeAll();
        controlador.repaint();
    }
    
    private static void shuffleList(){
        // Aleatoriza a lista de chamadas ao sistema
        // para não priorizar nenhum robô
        long seed = System.nanoTime();
        Collections.shuffle(requestList, new Random(seed));
    }
    
    public static void rearrangeAll(){
       	visualComponent.setRobots(army, visualComponent.getDx(), visualComponent.getDy(), HEXAGON_SIZE);
        visualComponent.setCrystals(crystals, visualComponent.getDx(), visualComponent.getDy(), HEXAGON_SIZE);
        
        // Atualiza as estruturas de dados dos robôs, dos cristais e das bombas
        if (!mineExploded.empty())
            visualComponent.removeLastExplosions(mineExploded);
        if (!groundMine.empty()){
            
            mineExploded =  visualComponent.setMines(groundMine, visualComponent.getDx(), visualComponent.getDy(), HEXAGON_SIZE);
            if(!mineExploded.empty()){
                processTheDamage();
            }
        }
    }
    
    public static BattleRobot getRobotBySerial(int robotSerial){
        Iterator it = army.iterator();
        BattleRobot wally = null;
        
        // Retorna o robô baseado no serial
        while(it.hasNext()){
            wally = (BattleRobot)it.next();
            if (wally.saySerialNumber() == robotSerial){
                break;
            }
        }
        return wally;
    }
    
    public static void insertArmy(String sourceCode,int index, String team, String robotModel, int x, int y, int serialNumber) throws IOException{
        // Insere um robô novo no vetor de robôs
        army.add(index, new BattleRobot(robotModel+"-" + serialNumber, serialNumber,sourceCode));
        army.get(index).setTeam("Team "+team);
        army.get(index).moveRobot(x,y);
        army.get(index).initBomb();
    }
    
    static void initArena(int mapHeight, int mapWidth) throws IOException{
        Random gen = new Random();
        int i;
        int j;
        
        // Inicializa o vetore de robôs e o de cristais
        for (int k = 0; k < numRobots; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            if (k < numRobots/2){
                insertArmy(codeName + "-" + k,k,"A","TX",j,i,gen.nextInt(1000));
            }
            else{
                insertArmy(codeName + "-" + k,k,"B","ZT",j,i,gen.nextInt(1000));
            }
        }
        
        for (int k = 0; k < NUM_CRYSTALS; k++) {
            i = gen.nextInt(mapHeight);
            j = gen.nextInt(mapWidth);
            
            // Crstais não podem aparecer na água nem nas bases
            if (Terreno[i][j] != 0 && Terreno[i][j] != 3 && Terreno[i][j] != 4)
                crystals.add(k, new Crystal(j,i,"FREE"));
            else
                k--;
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // Chamadas sistema
    
    public static void executeCall (SystemRequest request) {
        //Recebe uma chamada e a executa
        SysCallOperations op = SysCallOperations.valueOf( request.getInstructionRequest() );
        BattleRobot sony = getRobotBySerial(request.getSerialNumberRequester());
        double sucessNum = 0;
        String sucessStr;
        
        switch(op) {
            case WLK: /*Anda pelo mapa*/
                sucessNum = moveCall(request.getInstructionArgument(),request.getSerialNumberRequester());
                sony.returnAnswer(sucessNum);
                break;
            case FIRE: /*Atira no oponente*/
                sucessNum = fireCall(request.getInstructionArgument(),request.getSerialNumberRequester());
                sony.returnAnswer(sucessNum);
                break;
            case BOMB: /*Planta uma bomba*/ 
                sucessNum = bombCall(request.getInstructionArgument(),request.getSerialNumberRequester());
                sony.returnAnswer(sucessNum);
                break;
            case TAKE: /*Pega um Cristal*/
                takeCall(request.getInstructionArgument(), request.getSerialNumberRequester());
                break;
            case DROP: /*Solta um Cristal*/
                dropCall(request.getInstructionArgument(), request.getSerialNumberRequester());
                break;
            case LOOK: /*Olha para uma direcao e retorna-se o que existe la*/
                sucessStr = lookCall(request.getInstructionArgument(), request.getSerialNumberRequester());
                sony.returnAnswer(sucessStr);
                break;
            case ASK: /*Faz perguntas ao sistema */
                askCall(request.getInstructionArgument(),request.getSerialNumberRequester());
                break;
            default:
                break;
        }
    }
    
    // Pega um cristal na direcao
    public static void takeCall(String dir, int robotSerial) {
        BattleRobot sony = getRobotBySerial(robotSerial);
        if(!sony.canTakeCrystal()) return ;
        
        int x = xMove(sony.getX(), sony.getY(), dir);
        int y = yMove(sony.getX(), sony.getY(), dir);
        
        String what = lookAt(x,y,sony.getTeam());
        Crystal taken = null;
        if(what.equals("HAS_CRYSTAL")){
            Iterator cs = crystals.iterator();
            while(cs.hasNext()){
                taken = (Crystal) cs.next();
                if(taken.getX() == x && taken.getY() == y)
                    break;
            }
        }
        
        if(taken != null){
            crystals.removeElement(taken);
            sony.incrementCrystalCount();
        }
    }
    
    // Solta um cristal na direção
    public static void dropCall(String dir, int robotSerial) {
        BattleRobot sony = getRobotBySerial(robotSerial);
        if(!sony.canDropCrystal()) return ;
        
        int x = xMove(sony.getX(), sony.getY(), dir);
        int y = yMove(sony.getX(), sony.getY(), dir);
        
        String what = lookAt(x,y,sony.getTeam());
        Crystal dropped = new Crystal(x, y, "FREE");
        
        crystals.add(dropped);
        sony.decreaseCrystalCount();
        
        if (Terreno[y][x] == 3 || Terreno[y][x] == 4) {     // Se o cristal está na base
            NUM_DROPPED_CRYSTALS++;
        }
    }
    
    
    // Olha para uma direção
    public static String lookCall(String dir, int robotSerial){
        BattleRobot sony = getRobotBySerial(robotSerial);
        return lookAt(xMove(sony.getX(), sony.getY(), dir),yMove(sony.getX(), sony.getY(), dir), sony.getTeam());
    }
    
    // Varre as estruturas para ver se algo está onde se está olhado
    public static String lookAt(int i, int j, String robotTeam){
        Iterator it = army.iterator();
        BattleRobot hal;
        
        // Olha os robôs
        while(it.hasNext()){
            hal = (BattleRobot)it.next();
            if(i == hal.getX() && j == hal.getY()){
                if(hal.getTeam().equals(robotTeam))
                    return "HAS_ALLY"; 
                else return "HAS_ENEMY";
            }
        }
        
        // Olha os cristais
        Iterator cs = crystals.iterator();
        Crystal c;
        while(cs.hasNext()){
            c = (Crystal) cs.next();
            if(c.getX() == i && j == c.getY())
                return "HAS_CRYSTAL";
        }
        
        // Olha as bombas
        Iterator bm = groundMine.iterator();
        Bomb b;
        while(bm.hasNext()){
            b = (Bomb) cs.next();
            if(b.getX() == i && j == b.getY())
                return "HAS_BOMB";
        }
        
        // A ordem de retorno das listas prioriza o que deve ser
        // mais importante para o usuário e permite, por exemplo,
        // plantar armadilhas.
        
        return "NONE";
    }
    
    // Planta uma bomba em uma célula
    public static double bombCall(String dir, int robotSerial){
        BattleRobot sony = getRobotBySerial(robotSerial);
        Bomb plantedBomb = sony.placeTheBomb(xMove(sony.getX(), sony.getY(), dir),yMove(sony.getX(), sony.getY(), dir));
        groundMine.push(plantedBomb);
        return 1;
    }
    
    public static void processTheDamage(){
        BattleRobot hal = null;
        Stack<Bomb> auxMines = new Stack<Bomb>();
        // Olha para o vetor de "bombas que acabaram de explodir"
        // e danifica o robô, caso necessário
        
        while (!mineExploded.empty()){
            Iterator it = army.iterator();
            Bomb b = mineExploded.pop();
            int i = b.getX();
            int j = b.getY();
            while(it.hasNext()){
                hal = (BattleRobot)it.next();
                if(i == hal.getX() && j == hal.getY()){
                    damageRobot(hal, BOMB_DAMAGE);
                }
            }
            auxMines.push(b);            
        }
        mineExploded = auxMines;
    }
    
    // Atira no adversário
    public static double fireCall(String dir, int robotSerial){
        BattleRobot sony = getRobotBySerial(robotSerial);
        String type = lookAt(xMove(sony.getX(), sony.getY(), dir), yMove(sony.getX(), sony.getY(), dir), sony.getTeam());
        int x = xMove(sony.getX(), sony.getY(), dir);
        int y = yMove(sony.getX(), sony.getY(), dir);
        
        // Olha para o vetore de robôs; se houver algum
        // na posição escolhida, atira.
        
        if(type.equals("HAS_ENEMY")){
            Iterator it = army.iterator();
            BattleRobot hal = null;
            
            while(it.hasNext()){
                hal = (BattleRobot)it.next();
                if(x == hal.getX() && y == hal.getY())
                    break;
            }
            
            damageRobot(hal, FIRE_DAMAGE);
            
            return 1;
        }
        else{
            return 0;
        }
        
    }
    
    public static void damageRobot(BattleRobot hal, double x) {
        if(hal.getHealth() > 0)
            hal.updateHealth(x);
        else
            robotDied(hal);
    }
    
    // Desliga o robo se seu HP chegar a 0
    public static void robotDied(BattleRobot hal){
        army.remove(hal);
        numRobots--;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // Pergunta coisas para o sistema
    
    public static void  askCall(String asked, int robotSerial){
        // "Executa" a pergunta
        AskOptions question = AskOptions.valueOf(asked);
        BattleRobot sony = getRobotBySerial(robotSerial);
        double x,y;

        switch(question){
                // Devolve a quantidade de vida do robô
            case MY_HEALTH:
                sony.returnAnswer(sony.getHealth());
                break;
                // Devolve a quantidade de cristais do robô
            case NUMBER_OF_CRYSTAL:
                sony.returnAnswer(sony.getCrystalQuantity());
                break;
            case IS_IN_ENEMY_BASE:
                if (sony.getTeam().equals("Team A"))
                    x = 3;
                else
                    x = 4;
                if (Terreno[sony.getY()][sony.getX()] == x) {
                    sony.returnAnswer(1.0);
                }
                else {
                    sony.returnAnswer(0.0);
                }
                // Calcula a distância de ponto a ponto do robô a ponta da base inimiga ou sua própria base
            case ENEMY_BASE_DISTANCE:
                if (sony.getTeam().equals("Team A")){
                    x = calculeDistance("Team B",sony);
                    sony.returnAnswer(x);
                }
                else{
                    x = calculeDistance("Team A",sony);
                    sony.returnAnswer(x);    
                }
                break;
            case TEAM_BASE_DISTANCE:
                sony.returnAnswer(calculeDistance(sony.getTeam(),sony));
                break;
            default:
                break;
        }
    }
    
    // Calcula a distância do robô à base especificada
    public static double calculeDistance(String team,BattleRobot sony){
        return Math.sqrt( Math.pow(2,sony.getX() - getBaseX(team)) + Math.pow(2,sony.getY() - getBaseY(team)));
    }
    
    // Retorna a coordenada x da base especificada
    public static double getBaseX(String team){
        if (team.equals("Team A")){
            return 1.0*BASE_A_X;
        }
        else{
            return 1.0*BASE_B_X;
        }
    }
    
    // Retorna a coordenada y da base especificada
    public static double getBaseY(String team){
        if (team.equals("Team A")){
            return 1.0*BASE_A_Y;
        }
        else{
            return 1.0*BASE_B_Y;
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // Movimento
    
    public static int moveCall(String direction, int robotSerial){
        BattleRobot sony = getRobotBySerial(robotSerial);
        
        int x = xMove(sony.getX(), sony.getY(), direction);
        int y = yMove(sony.getX(), sony.getY(), direction);
        
        // Checa se o robô pode se mover; se puder, move.
        if(canMoveTo(x, y)){
            sony.moveRobot(x, y);
                        
            if (Terreno[y][x] == 0) {   // Se o robo tá na água
                damageRobot(sony, WATER_DAMAGE);
            }
            
            return 1;
        }
        else
            return 0;
    }
    
    public static boolean canMoveTo(int i, int j){
        // Checa se o robô pode se mover
        Iterator it = army.iterator();
        BattleRobot hal;
        
        while(it.hasNext()){
            hal = (BattleRobot)it.next();
            if(i == hal.getX() && j == hal.getY())
                return false;
        }
        return true;
    }
    
    // Metodos Helper para execução de ações
    public static int xMove(int x, int y, String direction){
        DirMov dir = DirMov.valueOf( direction);
        switch(dir) {
            case E:
                return (x + 1 + Width)%Width;
            case W:
                return (x - 1 + Width)%Width;
            case NW:
            case SW:
                if (y%2 == 0)
                    return (x - 1 + Width)%Width;
                else 
                    return (x + Width)%Width;
            case NE:
            case SE:
                if (y%2 != 0)
                    return (x + 1 + Width)%Width;
                else 
                    return (x + Width)%Width;
            default:
                break;
        }
        return x;
    }
    
    public static int yMove(int x, int y, String direction){
        DirMov dir = DirMov.valueOf(direction);
        switch(dir) {
            case E:
            case W:
                return y;
            case NW:
            case NE:
                return (y - 1 + Height)%Height;
            case SW:
            case SE:
                return (y + 1 + Height)%Height;
            default:
                break;
        }
        return y;   
    }
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // Funções disponibilizadas às outras entidades para requisicoes a arena
    public static void systemCall(SystemRequest request){
        // pauseSystem(100);
        requestList.add(request);
    }
    
    public static void updateBombStack(Stack<Bomb> bombStack){
        groundMine = bombStack;
    }
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    // DEBUG FUNCTIONS
    
    public static void pauseSystem(int time) {
        try{
            Thread.sleep(time);
        }
        catch(InterruptedException e){
            System.out.println("Pausa do sistema deu errado!");
        }
        
    }
    
}
