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

class CelExtra { // Cada célula da matriz representa um hexágono mostrado na tela
    BufferedImage ime;
    Graphics2D Gime;
    Point origin;
    
    CelExtra(int x, int y, int r, BufferedImage t) {
        ime = t;
        
        origin = new Point(12+x, 12+y);
        
        Gime = ime.createGraphics();
    }
    
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ime, (int)origin.getX(), (int)origin.getY(), null);
    }
}