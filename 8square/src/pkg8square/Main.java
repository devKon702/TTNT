package pkg8square;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void taciAstar(int[][] start, int[][] goal) throws FileNotFoundException, IOException {
        ArrayList<Puzzle> myPuzzles = new ArrayList<>(); // Mảng chứa các phần tử puzzle đã xét duyệt
        PriorityQueue<Puzzle> fringe = new PriorityQueue<>(new Puzzle());

        //Puzzle(startArr, goalArr, g, index, trace)
        Puzzle puzz = new Puzzle(start.clone(), goal, 0, 0, -1);
        fringe.add(puzz);
        myPuzzles.add(puzz);
        Puzzle tmp;
        while (!fringe.isEmpty()) {
            puzz = fringe.remove();
            if (puzz.isGoal()) {
                showResult(myPuzzles, puzz);
                return;
            }

            if (puzz.upAble()) {
                tmp = puzz.moveUp(myPuzzles.size(), goal);
                if (!myPuzzles.contains(tmp)) {
                    fringe.add(tmp);
                    myPuzzles.add(tmp);
                }
            }
            if (puzz.downAble()) {
                tmp = puzz.moveDown(myPuzzles.size(), goal);
                if (!myPuzzles.contains(tmp)) {
                    fringe.add(tmp);
                    myPuzzles.add(tmp);
                }
            }
            if (puzz.leftAble()) {
                tmp = puzz.moveLeft(myPuzzles.size(), goal);
                if (!myPuzzles.contains(tmp)) {
                    fringe.add(tmp);
                    myPuzzles.add(tmp);
                }
            }
            if (puzz.rightAble()) {
                tmp = puzz.moveRight(myPuzzles.size(), goal);
                if (!myPuzzles.contains(tmp)) {
                    fringe.add(tmp);
                    myPuzzles.add(tmp);
                }
            }
        }
        System.out.println("Cannot find the path");
    }

    public static void showResult(ArrayList<Puzzle> myPuzzles, Puzzle goal) throws FileNotFoundException, IOException {
        ArrayList<PuzzleGraphics> myPG = new ArrayList<>();
        for(Puzzle x : myPuzzles){
            myPG.add(new PuzzleGraphics(0, 0, x));
        }
        ArrayList<Puzzle> path = new ArrayList<>();
        
        // Xây dựng đường đi
        path.add(goal);
        int pos = goal.getTrace();
        while (pos != -1) {
            path.add(myPuzzles.get(pos));
            pos = myPuzzles.get(pos).getTrace();
        }
        
        // Hiển thị hình ảnh
        GraphcisSupporter sp = new GraphcisSupporter(myPG, path);
        
        // Xuất file
        FileWriter fileOut = new FileWriter("output.txt");
        for (int i = path.size() - 1; i >= 0; i--) {
//            path.get(i).showPuzzle();
            path.get(i).writeFile(fileOut);
            fileOut.write("\n");

        }

        fileOut.close();
    }

    public static int[][] getPuzz(Scanner scan) {
        scan.nextLine();
        int[][] a = new int[3][3];
        String x;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                x = scan.next();
                if (x.equals("x")) {
                    a[i][j] = 0;
                } else {
                    a[i][j] = Integer.parseInt(x);
                }
            }
        }
        if (scan.hasNext()) {
            scan.nextLine();
        }
        return a;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("taci.txt");
        Scanner scan = new Scanner(file);
        taciAstar(getPuzz(scan), getPuzz(scan));
    }

}
