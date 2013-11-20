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
            p.addPoint(10 + x + (int) ((r+1) * Math.sin(i * 2 * Math.PI / 6)),
                       12 + y + (int) ((r+1) * Math.cos(i * 2 * Math.PI / 6)));
        
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