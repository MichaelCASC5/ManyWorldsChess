import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Driver extends JComponent implements KeyListener, MouseListener, MouseMotionListener{
    private int WIDTH;
    private int HEIGHT;

    private Toolbar tb;
    private ChessBoard cb;
    
    public Driver(){
        WIDTH = 720;
        HEIGHT = 720;

        tb = new Toolbar(WIDTH);
        cb = new ChessBoard(WIDTH,HEIGHT);
        
        //Setting up the GUI
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Many Worlds Chess");
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        cb.draw(g);
        tb.draw(g);
    }
    public void loop(){
        try{
            cb.loop();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        repaint();
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        System.out.println(key);
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
    }
    public void mousePressed(MouseEvent e){
        if(!tb.isOpen())
            cb.pressed(e);
        tb.pressed(e);
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
    public void mouseMoved(MouseEvent e){
        cb.hover(e);
        tb.hover(e);
    }
    public void mouseDragged(MouseEvent e){
    }
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };	
        gameThread.start();
    }
    public static void main(String[] args){
        Driver g = new Driver();
        g.start(60);
    }
}