import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class ChessBoard{
    // private int WIDTH, HEIGHT;
    private int x,y;
    private int mX,mY;

    private int offset;
    private int scale;
    
    private int tiles;

    private boolean move;
    private Piece toMove;
    private ArrayList<Piece> pieces;
    
    public ChessBoard(int WIDTH, int HEIGHT){
        // this.WIDTH = WIDTH;//JavaGraphicsError
        // this.HEIGHT = HEIGHT;
        x = WIDTH/2-8;//JavaGraphicsError
        y = HEIGHT/2-4;//JavaGraphicsError
        offset = 0;

        tiles = 8;
        scale = 80;

        toMove = new Piece("w_pawn",'A',0);
        pieces = new ArrayList<>();

        offset = (scale/2) * tiles;
        // offset*=tiles;
        move = false;
        chessInit();
    }
    public void chessInit(){
        // for(int i=0;i<tiles;i++){
            // pieces.add(new Piece("b_pawn",i*scale + x-offset,y-offset + (scale),scale));
            // pieces.add(new Piece("w_pawn",i*scale + x-offset,y-offset + (scale*6),scale));
        // }
        // pieces.add(new Piece("pawn",scale + x-offset,y-offset + (scale),scale));
        
        for(int i=0;i<8;i++){
            pieces.add(new Piece("b_pawn",(char)(i + 97),7));
            pieces.add(new Piece("w_pawn",(char)(i + 97),2));
        }
        pieces.add(new Piece("w_rook",'A',1));
        pieces.add(new Piece("w_rook",'H',1));
        pieces.add(new Piece("w_knight",'B',1));
        pieces.add(new Piece("w_knight",'G',1));
        pieces.add(new Piece("w_bishop",'C',1));
        pieces.add(new Piece("w_bishop",'F',1));
        pieces.add(new Piece("w_queen",'D',1));
        pieces.add(new Piece("w_king",'E',1));
        
        pieces.add(new Piece("b_rook",'A',8));
        pieces.add(new Piece("b_rook",'H',8));
        pieces.add(new Piece("b_knight",'B',8));
        pieces.add(new Piece("b_knight",'G',8));
        pieces.add(new Piece("b_bishop",'C',8));
        pieces.add(new Piece("b_bishop",'F',8));
        pieces.add(new Piece("b_queen",'D',8));
        pieces.add(new Piece("b_king",'E',8));
    }
    public void pressed(MouseEvent e){
        // System.out.println((x-offset) + ", " + (y-offset));
        // System.out.println(e.getX() + ", " + e.getY());
        // System.out.println(x%scale);
        System.out.println("===");
        
        int pX, pY;
        pX = 0;
        pY = 0;
        if((mX > x-offset && mX < x + scale*tiles) && (mY > y-offset && mY < y + scale*tiles)){

            pX = (mX-(x-offset)) / scale + 1;
            pY = tiles - (mY-(y-offset)) / scale;
            
            System.out.println(e.getButton());
            if(e.getButton() == 1){
                // System.out.println(tiles/2 + (mX-x) / scale);
                // System.out.println(tiles/2 + (mY-y) / scale);
                // pX = tiles/2 + (mX-x) / scale;
                // pY = tiles/2 - (mY-y) / scale;

                // System.out.println(mX + ", x:" + x + "offset:" + offset + " scale:" + scale);

                // System.out.println(pX + ", " + pY);
                System.out.println("move: " + move);

                Piece p;
                if(move){
                    boolean spaceOccupied;
                    spaceOccupied = false;
                    for(int i=0;i<pieces.size();i++){
                        if(pieces.get(i).getPosX() == pX && pieces.get(i).getPosY() == pY){
                            spaceOccupied = true;
                            
                            /*
                                * Move this if statement directly outside and directly below
                                * the containing if statment for more conventional
                                * single-piece at a time chess movement
                            */
                            if(pieces.get(i).getMovement()){
                                pieces.get(i).setMovement(false);
                                move = false;
                            }
                        }
                    }
                    System.out.println("spaceOccupied: " + spaceOccupied);

                    if(!spaceOccupied){
                        toMove.setPosX(pX);
                        toMove.setPosY(pY);
                        
                        p = new Piece("");
                        p.setAll(toMove);
                        pieces.add(p);
                        // move = false;
                    }
                }else{
                    for(int i=0;i<pieces.size();i++){
                        // System.out.println(pieces.get(i).getName() + ": " + pieces.get(i).getPosX() + "," + pieces.get(i).getPosY());
                        if(pieces.get(i).getPosX() == pX && pieces.get(i).getPosY() == pY){
                            System.out.println(pieces.get(i).getName() + " is here");
                            toMove.setAll(pieces.get(i));
                            move = true;
                            pieces.get(i).displayPosition();
                            pieces.get(i).setMovement(true);
                        }
                    }
                }
                System.out.println("pieces size: " + pieces.size());
            }else if(e.getButton() == 3){
                for(int i=0;i<pieces.size();i++){
                    pieces.get(i).displayPosition();
                    System.out.println(pX + ", " + pY);
                    if(pieces.get(i).getPosX() == pX && pieces.get(i).getPosY() == pY){
                        pieces.remove(i);
                        i--;
                        System.out.println("remove");
                    }
                }
            }
        }
        // if(mY > y-offset && mY < y + scale*tiles){
        //     System.out.println(tiles/2 + (mY-y) / scale);
        // }
    }
    public void hover(MouseEvent e){
        mX = e.getX()-8;//Java Graphics Error
        mY = e.getY()-32;

        // System.out.println(mX + " " + (x-offset));
    }
    public void loop(){
        offset = (scale/2) * tiles;

        for(int i=0;i<pieces.size();i++){
            // pieces.get(i).set(i*scale + x-offset,y-offset,scale);
            // System.out.println(pieces.get(i).getPosition());
            pieces.get(i).loop(x,y,offset,scale,tiles);
        }

        // if(Math.random() < 0.025){
        //     scale-=2;
        //     x+=10;
        // }
    }
    public void draw(Graphics g){
        boolean checkerShade;
        checkerShade = false;
        
        g.setColor(Color.lightGray);
        for(int i=0;i<tiles;i++){
            for(int j=0;j<tiles;j++){
                if(j != 0){
                    if(checkerShade){
                        g.setColor(Color.lightGray);
                        checkerShade = false;
                    }else{
                        g.setColor(Color.gray);
                        checkerShade = true;
                    }
                }
                g.fillRect(i*scale + x-offset,j*scale + y-offset,scale,scale);
            }
        }
        
        //Hover Tiles
        g.setColor(new Color(255,255,255,100));
        // if(mX-7 < (x + scale*tiles))
        // g.drawRect(((mX-7 - scale/2) / scale) * scale,((mY-30 - scale/2) / scale ) * scale + y-offset,scale,scale);
        // g.drawRect((mX / scale) * scale - (x%scale) + scale/2,0,scale,scale);
        g.fillRect(((mX-(x%scale))/ scale) * scale + x%scale,((mY-(y%scale))/ scale) * scale + y%scale,scale,scale);
        g.drawRect(mX,mY,scale,scale);
        
        for(int i=0;i<pieces.size();i++){
            pieces.get(i).draw(g);
        }
    }
}