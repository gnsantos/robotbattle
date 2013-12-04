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

class Bomb {
    // Posição
    int x;
    int y;
    int countdown = 0;
    private final Integer TIMER = 9;
    
    // Com o robô, no chão, etc.
    String status;
    
    // Contrutor
    public Bomb(int x, int y, String status) {
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

    public void plantTheBomb(int x, int y){
        this.x = x;
        this.y = y;
        this.status = "Armed";
        this.countdown = TIMER;
    }

    public boolean updateBombTimer(){
        if (this.getStatus().equals("Armed")){
            this.countdown--;   
            if(this.countdown == 0){
                this.setStatus("exploded");
                return true;
            }
            else return false;         
        }
        return false;
    }
    public int getTimer(){
        return this.countdown;
    }
}