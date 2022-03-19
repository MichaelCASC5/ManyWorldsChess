import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Drop{
    private boolean display;
    private int x,y;
    
    private int mouseX;
    private int mouseY;
    
    private String[] options;
    private boolean[] hov;
    
    private int width, height;
    
    public Drop(String[] strs, int w){
        options = strs;
        display = false;
        
        width = w;
        
        height = 10 + options.length*20;
        hov = new boolean[options.length];
    }
    public void setCoordinates(int a, int b){
        x = a;
        y = b;
    }
    public void setMouse(int a, int b){
        mouseX = a;
        mouseY = b;
    }
    public void setDisplay(boolean b){
        display = b;
    }
    public boolean getDisplay(){
        return display;
    }
    public void pressed(MouseEvent e){
        for(int i=0;i<hov.length;i++){
            if(hov[i]){
                if(options[i].equals("Exit")){
                    System.exit(0);
                }
            }
            hov[i] = false;
        }
    }
    public void draw(Graphics g){
        //Font
        Font font = new Font("Times", Font.PLAIN, 12);
        g.setFont(font);
        
        //Draws Menu
        g.setColor(Color.white);
        g.fillRect(x, y, width, 10 + options.length*20);
        g.setColor(Color.gray);
        g.drawRect(x, y, width, 10 + options.length*20);
        
        int space = 20;
        for(int i=0;i<options.length;i++){
            //Draws hovers
            if(mouseX > x && mouseX < x + width && mouseY > space + 35 && mouseY < space + 55 && !options[i].equals("/break")){
                g.setColor(Color.cyan);
                g.fillRect(x, space + 5, width, 20);
                hov[i] = true;
            }else{
                hov[i] = false;
            }
            
            //Draws text
            if(!options[i].equals("/break")){
                g.setColor(Color.black);
                g.drawString(options[i], x + 20, y + space);
            }else{
                g.setColor(Color.gray);
                g.drawLine(x+5,y+space-5,x+width-5,y+space-5);
            }
            space+=20;
        }
    }
}