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

class Crystal {
    // Posição
    int x;
    int y;
    
    // Com o robô, no chão, etc.
    String status;
    
    // Contrutor
    public Crystal(int x, int y, String status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }
    
    
    // Getters e setters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String s) {
        status = s;
    }
}