import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.Font;

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

    private int player;

    private int boardType;
    
    public ChessBoard(int WIDTH, int HEIGHT, int n){
        // this.WIDTH = WIDTH;//JavaGraphicsError
        // this.HEIGHT = HEIGHT;
        x = WIDTH/2-8;//JavaGraphicsError
        y = HEIGHT/2-4;//JavaGraphicsError
        offset = 0;

        tiles = 8;
        scale = 76;

        toMove = new Piece("w_pawn",'A',0);

        offset = (scale/2) * tiles;
        // offset*=tiles;
        move = false;
        player = 0;

        boardType = n;
        init();
    }
    public void init(){
        if(boardType == 0){
            chessInit();
        }else if(boardType == 1){
            checkersInit();
        }
    }
    public void chessInit(){
        pieces = new ArrayList<>();
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
    public void checkersInit(){
        pieces = new ArrayList<>();
        
        for(int i=0;i<8;i+=2){
            pieces.add(new Piece("b_c_checker",(char)(i + 97),7));
            pieces.add(new Piece("w_c_checker",(char)(i + 97),1));
            pieces.add(new Piece("w_c_checker",(char)(i + 97),3));
        }

        for(int i=1;i<8;i+=2){
            pieces.add(new Piece("b_c_checker",(char)(i + 97),6));
            pieces.add(new Piece("w_c_checker",(char)(i + 97),2));
            pieces.add(new Piece("b_c_checker",(char)(i + 97),8));
        }
    }
    public boolean mouseOverBoard(){
        return ((mX > x-offset && mX < x + offset) && (mY > y-offset && mY < y + offset));
    }
    public void flipPlayer(){
        player = (player + 1) % 2;

        Piece p;
        for(int i=0;i<pieces.size();i++){
            p = pieces.get(i);

            if(p.getToRemove() || p.isCapture()){
                pieces.remove(i);
                i--;
                System.out.println("removed a piece on flip");
            }

            if(p.isChild()){
                p.setChild(false);
            }
            if(p.getMovement()){
                p.setMovement(false);
            }
            p.setChildren(0);
            p.setParent(new Piece(""));

            move = false;
        }
    }
    // public void reset(){
    //     init();
    // }
    public void pressed(MouseEvent e){
        Piece p;
        // System.out.println((x-offset) + ", " + (y-offset));
        // System.out.println(e.getX() + ", " + e.getY());
        // System.out.println(x%scale);
        System.out.println("===");
        
        int pX, pY;
        pX = 0;
        pY = 0;
        if(mouseOverBoard()){
            pX = (mX-(x-offset)) / scale + 1;
            pY = tiles - (mY-(y-offset)) / scale;
            
            // System.out.println(e.getButton());
            if(e.getButton() == 1){
                // System.out.println(tiles/2 + (mX-x) / scale);
                // System.out.println(tiles/2 + (mY-y) / scale);
                // pX = tiles/2 + (mX-x) / scale;
                // pY = tiles/2 - (mY-y) / scale;

                // System.out.println(mX + ", x:" + x + "offset:" + offset + " scale:" + scale);

                // System.out.println(pX + ", " + pY);
                System.out.println("move: " + move);

                if(move){
                    boolean spaceOccupied;
                    spaceOccupied = false;

                    boolean spaceOccupied_OPP;
                    spaceOccupied_OPP = false;

                    //Deselecting Pieces
                    for(int i=0;i<pieces.size();i++){
                        p = pieces.get(i);

                        if(p.getPosX() == pX && p.getPosY() == pY){// && p.getPlayer() == player
                            if(p.getPlayer() == player){
                                System.out.println("here: " + p.getPlayer() + " " + player);
                                spaceOccupied = true;
                            }else{
                                spaceOccupied_OPP = true;
                            }
                            
                            /*
                                * Move this if statement directly outside and directly below
                                * the containing if statment for more conventional
                                * single-piece at a time chess movement
                            */
                            // if(p.getMovement()){
                            //     p.setMovement(false);
                            //     move = false;
                            // }
                        }
                        if(p.getMovement()){
                            p.setMovement(false);
                            move = false;
                        }
                    }
                    System.out.println("spaceOccupied: " + spaceOccupied);

                    //Placing Children
                    if(!spaceOccupied){
                        toMove.setPosX(pX);
                        toMove.setPosY(pY);
                        
                        Piece copyPiece = new Piece("");
                        copyPiece.setAll(toMove);
                        copyPiece.getParent().setChildren(copyPiece.getParent().getChildren() + 1);
                        // copyPiece.setChild(true);
                        pieces.add(copyPiece);
                        // move = false;
                        if(spaceOccupied_OPP){
                            for(int i=0;i<pieces.size();i++){
                                p = pieces.get(i);

                                if(p.getPosX() == pX && p.getPosY() == pY && p.getPlayer() != player){
                                    p.setCapture(true);
                                    System.out.println("hello there " + p.isCapture() + " " + p.getPlayer() + " " + p.getName());
                                }
                            }
                        }
                    }
                }else{
                    //Selecting Pieces
                    for(int i=0;i<pieces.size();i++){
                        p = pieces.get(i);
                        // System.out.println(pieces.get(i).getName() + ": " + pieces.get(i).getPosX() + "," + pieces.get(i).getPosY());
                        if(p.getPosX() == pX && p.getPosY() == pY && !p.isChild() && p.getPlayer() == player){
                            // System.out.println(p.getName() + " is here");
                            toMove.setAll(p);
                            // toMove.setClone(true);
                            toMove.setParent(p);
                            move = true;
                            // p.displayPosition();
                            p.setMovement(true);
                            // p.setToRemove(true);
                            // System.out.println(p);
                        }
                    }
                }
                System.out.println("pieces size: " + pieces.size());
            }else if(e.getButton() == 3){
                for(int i=0;i<pieces.size();i++){
                    p = pieces.get(i);

                    // p.displayPosition();
                    // System.out.println(pX + ", " + pY);
                    // if(p.getPosX() == pX && p.getPosY() == pY && !p.getMovement()){
                    if(p.getPosX() == pX && p.getPosY() == pY){
                        if(p.isChild() && p.getPlayer() == player){
                            p.getParent().setChildren(p.getParent().getChildren() - 1);
        
                            pieces.remove(i);
                            i--;
                        }else if(p.getPlayer() != player){
                            p.setCapture(false);
                        }
                        // System.out.println("remove");
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

        Piece p;
        for(int i=0;i<pieces.size();i++){
            p = pieces.get(i);
            // pieces.get(i).set(i*scale + x-offset,y-offset,scale);
            // System.out.println(pieces.get(i).getPosition());
            p.loop(x,y,offset,scale,tiles);

            // if(p.isClone()){
            //     p.getParent().setToRemove(true);
            // }
            if(p.getChildren() > 0){
                p.setToRemove(true);
                // System.out.println("children " + p.getChildren());
            }else{
                p.setToRemove(false);
            }
        }

        // if(Math.random() < 0.025){
        //     scale-=2;
        //     x+=10;
        // }
    }
    public void draw(Graphics g){
        //Board edge
        // g.setColor(new Color(218,138,52));
        g.setColor(new Color(159,73,2));
        g.fillRect(x-offset-(scale/(3)),y-offset-(scale/3),(tiles*scale)+(scale/3)*2,(tiles*scale)+(scale/3)*2);

        boolean checkerShade;
        checkerShade = false;
        
        g.setColor(new Color(228,148,62));//light
        for(int i=0;i<tiles;i++){
            for(int j=0;j<tiles;j++){
                if(j != 0){
                    if(checkerShade){
                        g.setColor(new Color(228,148,62));//light
                        checkerShade = false;
                    }else{
                        g.setColor(new Color(189,103,22));//dark
                        checkerShade = true;
                    }
                }
                g.fillRect(i*scale + x-offset,j*scale + y-offset,scale,scale);
            }
        }
        
        //Hover Tiles
        if(mouseOverBoard()){
            g.setColor(new Color(255,255,255,100));
            g.fillRect(((mX-(x%scale))/ scale) * scale + x%scale,((mY-(y%scale))/ scale) * scale + y%scale,scale,scale);
            // g.drawRect(mX,mY,scale,scale);
        }
        
        // if(mX-7 < (x + scale*tiles))
        // g.drawRect(((mX-7 - scale/2) / scale) * scale,((mY-30 - scale/2) / scale ) * scale + y-offset,scale,scale);
        // g.drawRect((mX / scale) * scale - (x%scale) + scale/2,0,scale,scale);
        
        for(int i=0;i<pieces.size();i++){
            pieces.get(i).draw(g);
        }

        //Drawing Text
        Font font = new Font("Times", Font.BOLD, scale/4);
        g.setFont(font);
        String text;
        text = " to move";
        if(player == 0){
            text = "White" + text;
            g.setColor(Color.white);
        }else{
            text = "Black" + text;
            g.setColor(Color.black);
        }
        g.drawString(text,x-offset-(scale/15),y-offset-(scale/15));
    }
}