import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Toolbar{
    private int width;
    private int height;

    private boolean open;
    
    private String[] tools = {
        "File",
        "Edit",
        "View",
        "Help",
    };
    private boolean[] hov = new boolean[tools.length];
    
    private int mouseX, mouseY;
    private Drop[] drop = new Drop[tools.length];
            
    //Constructor
    public Toolbar(int w){
        width = w;
        height = 20;

        open = false;
        
        String[] drop0 = {
            "New File",
            "New Window",
            "/break",
            "Open File...",
            "Open Folder...",
            "Open Recent",
            "/break",
            "Save",
            "Save As...",
            "Save All",
            "/break",
            "Auto Save",
            "Preferences",
            "/break",
            "Close Editor",
            "Close Window",
            "/break",
            "Exit",
        };
        drop[0] = new Drop(drop0, 200);
        
        String[] drop1 = {
            "Undo",
            "Redo",
            "/break",
            "Cut",
            "Copy",
            "Paste",
            "/break",
            "Find",
            "Replace",
        };
        drop[1] = new Drop(drop1, 150);
        
        String[] drop2 = {
            "Appearance",
            "Editor Layout",
            "/break",
            "Search",
            "Run",
            "Extensions",
            "/break",
            "Output",
            "Terminal",
        };
        drop[2] = new Drop(drop2, 150);
        
        String[] drop3 = {
            "Get Started",
            "Show All Commands",
            "Documentation",
            "Release Notes",
            "/break",
            "Join Us",
            "Report Issue",
            "/break",
            "View License",
            "Privacy Statement",
            "/break",
            "Check for Updates...",
            "/break",
            "About",
        };
        drop[3] = new Drop(drop3, 180);
    }
    public int getHeight(){
        return height;
    }
    public boolean isOpen(){
        return open;
    }
    public void pressed(MouseEvent e){
        open = false;
        for(int i=0;i<hov.length;i++){
            if(hov[i] && !drop[i].getDisplay()){
                drop[i].setDisplay(true);
                open = true;
            }else{
                drop[i].setDisplay(false);
                drop[i].pressed(e);
            }
        }
    }
    public void hover(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void draw(Graphics g){
        //Bar
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        
        //Font
        Font font = new Font("Times", Font.PLAIN, 12);
        g.setFont(font);
        
        //Taskbar
        int space = 0;
        int wordLen;
        for(int i=0;i<tools.length;i++){
            wordLen = tools[i].length()*9;
            
            //Drawing Hover
            if(mouseX > space && mouseX < space + tools[i].length()*8 && mouseY < height*2.5){
                g.setColor(Color.cyan);
                g.fillRect(space, 0, wordLen, height);
                hov[i] = true;
            }else{
                hov[i] = false;
            }
            
            //Remove drop-down menu
            if(hov[i] != drop[i].getDisplay() && mouseY < height*2.5){
                drop[i].setDisplay(false);
            }
            
            //Drawing drop-down menus
            if(drop[i].getDisplay()){
                drop[i].setMouse(mouseX, mouseY);
                drop[i].setCoordinates(space, height);
                drop[i].draw(g);
            }
            
            //Drawing Words
            g.setColor(Color.black);
            g.drawString(tools[i], space+4, height-6);
            space+=wordLen;
        }
    }
}