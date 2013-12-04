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

class Campo extends JPanel { // Campo representa o mapa da arena, e cuida do output gráfico
    int Larg, Alt, Dx, Dy; // largura do terreno, altura do terreno, incremento em x e incremento em y
    BufferedImage grama, terra, agua, ponte, baseA, baseB, roboA, roboB, crystalN, crystalW, crystalS, crystalE; // texturas a serem carregadas para o terreno
    BufferedImage bombaX, bomba0, bomba1, bomba2, bomba3, bomba4, bomba5, bomba6, bomba7, bomba8, bomba9;
    int[][] Terreno;
    
    int m; //Dimensões do mapa
    int n;
    
    Celula[][] cel; // define a matriz de células do terreno
    
    public CelExtra[][] robos;
    public CelExtra[][] cristais;
    public CelExtra[][] minesField;
    
    public BufferedImage[] Textura;
    public BufferedImage[] ExplosivesTexture;
    
    private void initTexturas(){
        
        // cada try..catch que segue carregará uma textura, ou levantará uma exceção que encerrará a aplicação com erro
        try {
            grama = ImageIO.read(this.getClass().getResource("grama2.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            terra = ImageIO.read(this.getClass().getResource("terra9.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            agua = ImageIO.read(this.getClass().getResource("/img/agua3.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            ponte = ImageIO.read(this.getClass().getResource("ponte4.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            baseA = ImageIO.read(this.getClass().getResource("base5.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            baseB = ImageIO.read(this.getClass().getResource("base3.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            roboA = ImageIO.read(this.getClass().getResource("/img/roboA3.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            roboB = ImageIO.read(this.getClass().getResource("/img/roboB2.png"));
        }
        catch (Exception e) {
            System.exit(1);
        }
        
        try {
            crystalN = ImageIO.read(this.getClass().getResource("/img/crystal2N.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        try {
            crystalW = ImageIO.read(this.getClass().getResource("/img/crystal2W.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        try {
            crystalS = ImageIO.read(this.getClass().getResource("/img/crystal2S.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        try {
            crystalE = ImageIO.read(this.getClass().getResource("/img/crystal2E.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        BufferedImage[] imageArray = {agua, terra, grama, baseA, baseB, roboA, roboB, ponte, crystalN, crystalW, crystalS, crystalE};
        this.Textura = imageArray;
        initExplosives();
    }

    public void initExplosives(){

        //X
        try {
            bombaX = ImageIO.read(this.getClass().getResource("/img/bombaX.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //9
        try {
            bomba9 = ImageIO.read(this.getClass().getResource("/img/bomba9.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //8
        try {
            bomba8 = ImageIO.read(this.getClass().getResource("/img/bomba8.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //7
        try {
            bomba7 = ImageIO.read(this.getClass().getResource("/img/bomba7.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //6
        try {
            bomba6 = ImageIO.read(this.getClass().getResource("/img/bomba6.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //5
        try {
            bomba5 = ImageIO.read(this.getClass().getResource("/img/bomba5.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //4
        try {
            bomba4 = ImageIO.read(this.getClass().getResource("/img/bomba4.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //3
        try {
            bomba3 = ImageIO.read(this.getClass().getResource("/img/bomba3.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //2
        try {
            bomba2 = ImageIO.read(this.getClass().getResource("/img/bomba2.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //1
try {
            bomba1 = ImageIO.read(this.getClass().getResource("/img/bomba1.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        BufferedImage[] bombArray = {bombaX, bomba1, bomba2, bomba3, bomba4, bomba5, bomba6, bomba7, bomba8, bomba9};
        this.ExplosivesTexture = bombArray;
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
                robos[posX][posY] = new CelExtra( (int)((posX + psi)*Dx), posY*Dy, L, Textura[5]);
            else
                robos[posX][posY] = new CelExtra( (int)((posX + psi)*Dx), posY*Dy, L, Textura[6]);
        }
    }
    
    public void setCrystals(Vector<Crystal> crystalVector, int Dx, int Dy, int L){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cristais[i][j] = null;
            }
        }
        
        Iterator itr = crystalVector.iterator();
        while(itr.hasNext()){
            Crystal cristal = (Crystal) itr.next();
            int posX = cristal.getX();
            int posY = cristal.getY();
            
            double psi = 0;
            if (posY%2 == 1)
                psi = 0.6;
                        
            cristais[posX][posY] = new CelExtra( (int)((posX + psi)*Dx), posY*Dy, L, Textura[8+((posX*posY)%4)]);
            
        }
    }
    public Stack<Bomb> setMines(Stack<Bomb> bombStack, int Dx, int Dy, int L){
        Stack<Bomb> mineExploded = new Stack<Bomb>();
        Stack<Bomb> mineActived = new Stack<Bomb>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                minesField[i][j] = null;
            }
        }
        
        while(!bombStack.empty()){
            Bomb b = bombStack.pop();
            int posX = b.getX();
            int posY = b.getY();
            int countdown;
            boolean timer;
            double psi = 0; 
            
            timer = b.updateBombTimer();
            countdown = b.getTimer();

            if (posY%2 == 0){
                psi = 0.2; 
                minesField[posX][posY] = new CelExtra( (int)((posX+psi)*Dx), 
                                                        (int)((posY+psi)*Dy), L, 
                                                        ExplosivesTexture[countdown]);
            }
            else{
                psi = 0.3; 
                minesField[posX][posY] = new CelExtra( (int)((posX+2.2*psi)*Dx), 
                                                        (int)((posY+psi/2)*Dy), L, 
                                                        ExplosivesTexture[countdown]); 
            }

            if (timer){
                mineExploded.push(b);
            }
            else
                mineActived.push(b);
        }
        Battlefield.updateBombStack(mineActived);
        return mineExploded;
    }
    
    public int getDx(){
    	return this.Dx;
    }
    public int getDy(){
        return this.Dy;
    }

    public void removeLastExplosions(Stack<Bomb> mineExploded){
        while (!mineExploded.empty()){
            Bomb b = mineExploded.pop();
            int x = b.getX();
            int y = b.getY();
            minesField [x][y] = null;
        }
    }
    
    Campo(int L, int W, int H, int[][] Terreno, Vector<BattleRobot> army, Vector<Crystal> crystalsVector, Stack<Bomb> bombStack) {
	this.setBackground(Color.black);
        this.Terreno = Terreno;
        this.m = Terreno[0].length;
        this.n = Terreno.length;
        this.cel = new Celula[m][n];
        
        this.robos = new CelExtra[m][n];
        this.cristais = new CelExtra[m][n];
        this.minesField = new CelExtra[m][n];

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
        setCrystals(crystalsVector,Dx,Dy, L);
        Stack<Bomb> temp = setMines(bombStack, Dx, Dy, L);
    }
    
    public void paintComponent(Graphics g) { // Função chamada automaticamente pelo java
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cel[i][j].draw(g); // pinta as células no contexto gráfico
        
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (cristais[i][j] != null) {
                    cristais[i][j].draw(g); // pinta as células no contexto gráfico
                }
        
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (robos[i][j] != null) {
                    robos[i][j].draw(g); // pinta as células no contexto gráfico
                }
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (minesField[i][j] != null) {
                    minesField[i][j].draw(g); // pinta as células no contexto gráfico
                }        
    }
}
