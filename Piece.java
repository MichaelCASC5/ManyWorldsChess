import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class Piece{
    private String name;
    private int scale;
    private int x,y;

    private int posX,posY;
    private int player;

    private boolean movement;
    private ImageIcon sprite;

    private int[] corner = new int[3];

    public Piece(String str){
        name = str;
    }
    public Piece(String str, char a, int b){
        name = str;
        // x = a + s/8;
        // y = b + s/8;
        x = 0;
        y = 0;
        
        // let = a;
        // num = b;

        posX = Character.getNumericValue(a)-9;
        posY = b;
        
        // scale = s - (s/8)*2;
        scale = 0;
        player = 0;

        movement = false;
        sprite = new ImageIcon(Driver.class.getResource("\\images\\" + name + ".png"));
    }
    public void loop(int bX, int bY, int bOffset, int bScale, int tiles){
        int addX = posX * bScale - bScale;
        int addY = (tiles*bScale) - posY * bScale;

        corner[0] = bX - bOffset + addX;
        corner[1] = bY - bOffset + addY;
        corner[2] = bScale;

        x = bX - bOffset + (bScale/tiles) + addX;
        y = bY - bOffset + (bScale/tiles) + addY;

        scale = bScale - (bScale/tiles)*2;
    }
    public void setPosX(int n){
        posX = n;
    }
    public int getPosX(){
        return posX;
    }
    public void setX(int n){
        x = n;
    }
    public int getX(){
        return x;
    }
    public void setY(int n){
        y = n;
    }
    public int getY(){
        return y;
    }
    public void setPosY(int n){
        posY = n;
    }
    public int getPosY(){
        return posY;
    }
    public void setName(String str){
        name = str;
    }
    public String getName(){
        return name;
    }
    public void setScale(int n){
        scale = n;
    }
    public int getScale(){
        return scale;
    }
    public void setPlayer(int n){
        player = n;
    }
    public int getPlayer(){
        return player;
    }
    public void setMovement(boolean b){
        movement = b;
    }
    public boolean getMovement(){
        return movement;
    }
    // public void set(int a, int b, int s){
    //     x = a + s/8;
    //     y = b + s/8;

    //     scale = s - (s/8)*2;
    // }
    public ImageIcon getSprite(){
        return sprite;
    }
    public void setCorner(int[] a){
        corner = a;
    }
    public int[] getCorner(){
        return corner;
    }
    public void displayPosition(){
        System.out.println("" + posX + ", " + posY);
    }
    public void setAll(Piece p){
        name = p.getName();
        scale = p.getScale();
        x = p.getX();
        y = p.getY();
        posX = p.getPosX();
        posY = p.getPosY();
        movement = p.getMovement();
        sprite = p.getSprite();
    }
    public void draw(Graphics g){
        Color col;
        col = new Color(0,0,0,0);
        // if(name.equals("pawn")){
        //     col = new Color(255,255,255);
        // }else{
        //     col = new Color(0,0,255);
        // }

        if(movement){
            col = new Color(100,100,100);
            g.setColor(Color.green);
            g.fillRect(corner[0],corner[1],corner[2],corner[2]);
            // g.fillRect(x,y,scale,scale);
            // g.fillRect(trueX,trueY,trueScale,trueScale);
        }

        g.setColor(Color.green);
        // g.fillRect(trueX,trueY,trueScale,trueScale);

        //AffineTransforms and scale
        // AffineTransform at;
        // at = AffineTransform.getTranslateInstance(x,y);
        // at.scale(1,1);

        //Drawing Image
        Image image = sprite.getImage();
        g.drawImage(image, x, y, scale, scale, null);
    }
}