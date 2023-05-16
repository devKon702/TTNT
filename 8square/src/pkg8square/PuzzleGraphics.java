package pkg8square;

import  java.awt.*;
import java.awt.Graphics;

public class PuzzleGraphics {

    public static int squareWidth = 20;
    public static int puzzleWidth = 100;
    public static int puzzleHeight = 60;
    private int x;
    private int y;
    private Color color;
    private Puzzle puzz;

    public PuzzleGraphics(int x, int y, Puzzle puzz) {
        this.x = x;
        this.y = y;
        this.puzz = puzz;
        color = Color.black;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PuzzleGraphics other = (PuzzleGraphics) obj;
        return this.puzz.equals(other.getPuzz());
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        int indexX = this.x;
        int indexY = this.y;
        int width = PuzzleGraphics.squareWidth;
        int[][] a = puzz.getPositions();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                g.drawRect(indexX, indexY, width, width);
                if(a[i][j] != 0) 
                    g.drawString(Integer.toString(a[i][j]), (indexX * 2 + width) / 2 - 5, (indexY * 2 + width) / 2 + 5);
                indexX += width;
            }
            
            if(i == 0) 
                g.drawString("g=" + puzz.getG(),indexX + 5, indexY + width - 5);
            else if(i == 1) 
                g.drawString("h=" + puzz.getH(),indexX + 5, (indexY * 2 + width) / 2 + 5);
            else if(i == 2) 
                g.drawString("f=" + puzz.getF(),indexX + 5, indexY + 12);
            indexX = this.x;
            indexY += width;
        }
    }
    
    public void changeColor(Graphics g, Color color){
        g.setColor(color);
        draw(g);
    }
    
    public void putNextTo(Graphics g, PuzzleGraphics root){
        x = root.getX()+PuzzleGraphics.puzzleWidth + GraphcisSupporter.marginRight;
        y = root.getY();
        
        draw(g);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Puzzle getPuzz() {
        return puzz;
    }
    
    public Color getColor(){
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPuzz(Puzzle puzz) {
        this.puzz = puzz;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
}
