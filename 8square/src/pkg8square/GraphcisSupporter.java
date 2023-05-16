package pkg8square;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Scanner;

public class GraphcisSupporter extends JFrame {

    private int count = 0;
    private int drawStep = -1;
    private ArrayList<PuzzleGraphics> myPuzzleGraphics;
    private ArrayList<Puzzle> path;
    public JPanel panel;
    public static int marginBottom = 40;
    public static int marginRight = 20;
    public static int rootX = 400;
    public static int rootY = 10;

    public GraphcisSupporter(ArrayList<PuzzleGraphics> myPuzzles, ArrayList<Puzzle> path) {
        this.myPuzzleGraphics = myPuzzles;
        this.path = path;
        panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                display(g);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JButton btnNext = new JButton("Next step");
        btnNext.addActionListener((ActionEvent e) -> {
            drawStep++;
            if(drawStep == myPuzzles.size()){
                drawPath(panel.getGraphics());
            }
            else if(drawStep > myPuzzles.size() ){
                panel.getGraphics().clearRect(0, 0, panel.getWidth() - 350, panel.getHeight());
                for(PuzzleGraphics pg: myPuzzleGraphics){
                    if(path.contains(pg.getPuzz())){
                        pg.setColor(Color.black);
                    }
                }
                drawStep = -1;
            }
            else{
                drawSinglePuzzle(panel.getGraphics(), drawStep);
            }
            
        });
        
        JButton btnUp = new JButton("Up");
        btnUp.addActionListener((ActionEvent e) -> {
            panel.getGraphics().clearRect(0, 0, panel.getWidth() - 350, panel.getHeight());
            for (PuzzleGraphics pg : myPuzzles) {
                pg.setY(pg.getY() + 20);
            }
            refresh(panel.getGraphics());
        });

        JButton btnDown = new JButton("Down");
        btnDown.addActionListener((ActionEvent e) -> {
            panel.getGraphics().clearRect(0, 0, panel.getWidth() - 350, panel.getHeight());
            for (PuzzleGraphics pg : myPuzzles) {
                pg.setY(pg.getY() - 20);
            }
            refresh(panel.getGraphics());
        });
        
        JButton btnShowPath = new JButton("Show path only");
        btnShowPath.addActionListener((ActionEvent e) -> {
            panel.getGraphics().clearRect(0, 0, panel.getWidth() - 350, panel.getHeight());
            drawPath(panel.getGraphics());
        });
        
        JButton btnShowAll = new JButton("Show all");
        btnShowAll.addActionListener((ActionEvent e) -> {
            panel.getGraphics().clearRect(0, 0, panel.getWidth() - 350, panel.getHeight());
            drawAll(panel.getGraphics());
        });
        gbc.insets = new Insets(-200, 700, 0, 0);
        panel.add(btnNext, gbc);
        gbc.insets = new Insets(-100, 700, 0, 0);
        panel.add(btnUp, gbc);
        gbc.insets = new Insets(0, 700, 0, 0);
        panel.add(btnDown, gbc);
        gbc.insets = new Insets(100, 700, 0, 0);
        panel.add(btnShowPath, gbc);
        gbc.insets = new Insets(200, 700, 0, 0);
        panel.add(btnShowAll, gbc);

        add(panel);
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setUp() {
        if (myPuzzleGraphics.isEmpty()) {
            System.out.println("GraphicsPuzz is null");
            return;
        }

        myPuzzleGraphics.get(0).setX(rootX);
        myPuzzleGraphics.get(0).setY(rootY);
        for (PuzzleGraphics puzz : myPuzzleGraphics) {
            switch (getQuantityOfChild(puzz.getPuzz())) {
                case 1:
                    setOneChild(puzz);
                    break;
                case 2:
                    setTwoChild(puzz);
                    break;
                case 3:
                    setThreeChild(puzz);
                    break;
                case 4:
                    setFourChild(puzz);
                    break;
                default:
            }
        }

    }

    public void drawAll(Graphics g) {
        for (PuzzleGraphics pg : myPuzzleGraphics) {
            if (path.contains(pg.getPuzz())) {
                pg.setColor(Color.blue);
            } else {
                pg.setColor(Color.black);
            }
            pg.draw(g);
            if (pg.getPuzz().getTrace() != -1) {
                drawConnectLine(g, myPuzzleGraphics.get(pg.getPuzz().getTrace()), pg);
            }
        }
        drawStep = myPuzzleGraphics.size();
    }
    
    public void refresh(Graphics g){
        int cnt = 0;
        for(PuzzleGraphics pg: myPuzzleGraphics){
            if(cnt > drawStep) break;
            pg.draw(g);
            if (pg.getPuzz().getTrace() != -1) {
                drawConnectLine(g, myPuzzleGraphics.get(pg.getPuzz().getTrace()), pg);
            }
            cnt++;
            
        }
    }

    public void drawPath(Graphics g) {
        for (PuzzleGraphics pg : myPuzzleGraphics) {
            if (path.contains(pg.getPuzz())) {
                pg.setColor(Color.blue);
                pg.draw(g);
                if (pg.getPuzz().getTrace() != -1) {
                    drawConnectLine(g, myPuzzleGraphics.get(pg.getPuzz().getTrace()), pg);
                }
            }
        }
        drawStep = myPuzzleGraphics.size();
    }
    
    public void drawSinglePuzzle(Graphics g, int step){
        PuzzleGraphics pg = myPuzzleGraphics.get(step);
        pg.draw(g);
        if(pg.getPuzz().getTrace() != -1){
            drawConnectLine(g, myPuzzleGraphics.get(pg.getPuzz().getTrace()), pg);
        }
    }

    public void display(Graphics g) {
        // Set up only when first time paintComponent()
        if (this.count == 0) {
            setUp();
            count++;
        }
        //drawAll(g);
    }

    public void drawConnectLine(Graphics g, PuzzleGraphics start, PuzzleGraphics end) {
        g.setColor(end.getColor());
        
        int startPointX = (int) (start.getX() + PuzzleGraphics.squareWidth * 1.5);
        int startPointY = (int) (start.getY() + PuzzleGraphics.squareWidth * 3);

        int endPointX = (int) (end.getX() + PuzzleGraphics.squareWidth * 1.5);
        int endPointY = end.getY();

        g.drawLine(startPointX, startPointY, endPointX, endPointY);
    }

    public void setCenterPuzz(int width, int x, int y, PuzzleGraphics pg) {
        int indexX = (x + x + width) / 2;
        indexX -= PuzzleGraphics.puzzleWidth / 2;
        pg.setX(indexX);
        pg.setY(y);
    }

    public int getQuantityOfChild(Puzzle parent) {
        int cnt = 0;
        for (PuzzleGraphics pg : myPuzzleGraphics) {
            if (pg.getPuzz().getTrace() == parent.getIndex()) {
                cnt++;
            }
        }
        return cnt;
    }

    public void setOneChild(PuzzleGraphics parentPuzz) {
        for (PuzzleGraphics puzz : myPuzzleGraphics) {
            if (puzz.getPuzz().getTrace() == parentPuzz.getPuzz().getIndex()) {
                puzz.setX(parentPuzz.getX());
                puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                return;
            }
        }
    }

    public void setTwoChild(PuzzleGraphics parentPuzz) {
        int nth = 1;
        for (PuzzleGraphics puzz : myPuzzleGraphics) {
            if (puzz.getPuzz().getTrace() == parentPuzz.getPuzz().getIndex()) {
                switch (nth) {
                    case 1:
                        puzz.setX(parentPuzz.getX() - PuzzleGraphics.puzzleWidth / 2 - marginRight);
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    case 2:
                        puzz.setX(parentPuzz.getX() + PuzzleGraphics.puzzleWidth / 2 + marginRight);
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    default:
                        System.out.println("Error at setTwoChild");
                        break;
                }
            }
        }
    }

    public void setThreeChild(PuzzleGraphics parentPuzz) {
        int nth = 1;
        for (PuzzleGraphics puzz : myPuzzleGraphics) {
            if (puzz.getPuzz().getTrace() == parentPuzz.getPuzz().getIndex()) {
                switch (nth) {
                    case 1:
                        puzz.setX(parentPuzz.getX());
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    case 2:
                        puzz.setX(parentPuzz.getX() - PuzzleGraphics.puzzleWidth - marginRight);
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    case 3:
                        puzz.setX(parentPuzz.getX() + PuzzleGraphics.puzzleWidth + marginRight);
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    default:
                        System.out.println("Error at setThreeChild");
                        break;
                }
            }
        }
    }

    public void setFourChild(PuzzleGraphics parentPuzz) {
        int nth = 1;
        for (PuzzleGraphics puzz : myPuzzleGraphics) {
            if (puzz.getPuzz().getTrace() == parentPuzz.getPuzz().getIndex()) {
                switch (nth) {
                    case 1:
                        puzz.setX(parentPuzz.getX() - PuzzleGraphics.puzzleWidth / 2 - marginRight);
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    case 2:
                        puzz.setX(parentPuzz.getX() + PuzzleGraphics.puzzleWidth / 2 + marginRight);
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    case 3:
                        puzz.setX((int) (parentPuzz.getX() - PuzzleGraphics.puzzleWidth * 1.5 - marginRight * 2));
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    case 4:
                        puzz.setX((int) (parentPuzz.getX() + PuzzleGraphics.puzzleWidth * 1.5 + marginRight * 2));
                        puzz.setY(parentPuzz.getY() + PuzzleGraphics.puzzleHeight + marginBottom);
                        nth++;
                        break;
                    default:
                        System.out.println("Error at setFourChild");
                        break;
                }

            }
        }
    }
}
