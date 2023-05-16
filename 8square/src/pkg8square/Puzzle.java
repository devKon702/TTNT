package pkg8square;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Puzzle implements Comparator<Puzzle> {

    private int[][] positions; //vị trí các số
    private int f;  //giá trị f(n)
    private int g;  //số bước đi để đến hiện tại
    private int h;  //số nút khác biệt với goal
    private int index;  //ID của bước đi hiện tại
    private int trace;  //index trước đó
    private int rowIndex, colIndex;    //vị trí của x trong puzzle

    public Puzzle() {

    }

    public Puzzle(int[][] pos, int[][] goal, int g, int index, int trace) {
        this.positions = pos.clone();
        this.g = g;
        this.h = this.checkDiffer(goal);
        this.f = g + h;
        this.index = index;
        this.trace = trace;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (positions[i][j] == 0) {
                    this.rowIndex = i;
                    this.colIndex = j;
                    return;
                }
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Puzzle) {
            Puzzle node = (Puzzle) obj;
            // Kiểm tra nếu cùng position và cùng số bước g thì là trùng
            if (this.checkDiffer(node.positions) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Arrays.deepHashCode(this.positions);
        return hash;
    }

    

    @Override
    public int compare(Puzzle o1, Puzzle o2) {
        if (o1.f > o2.f) {
            return 1;
        }
        if (o1.f < o2.f) {
            return -1;
        }
        if (o1.h > o2.h){
            return 1;
        }
        if (o1.h < o2.h){
            return -1;
        }
        return 0;
    }

//    public void showPuzzle() {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (positions[i][j] == 0) {
//                    System.out.print("x ");
//                } else {
//                    System.out.print(positions[i][j] + " ");
//                }
//            }
//            if (i == 0) {
//                System.out.println("| g(n)= " + this.g);
//            }
//            if (i == 1) {
//                System.out.println("| h(n)= " + this.h);
//            }
//            if (i == 2) {
//                System.out.println("| f(n)= " + this.f);
//            }
//        }
//    }

    public void writeFile(FileWriter file) throws IOException {            
        for(int i=0; i<3; i++){
            for(int x:positions[i]){
                file.write(x + " ");
            }
        }
        
        file.write("\n"+g);
        file.write("\n"+index);
        file.write("\n"+trace);
    }

    public int checkDiffer(int[][] a) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (positions[i][j] != a[i][j] && positions[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean upAble() {
        return this.rowIndex > 0;
    }

    public boolean downAble() {
        return this.rowIndex < 2;
    }

    public boolean leftAble() {
        return this.colIndex > 0;
    }

    public boolean rightAble() {
        return this.colIndex < 2;
    }

    public Puzzle moveUp(int index, int[][] goal) {
        int[][] a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = this.positions[i][j];
            }
        }

        a[rowIndex][colIndex] = a[rowIndex - 1][colIndex];
        a[rowIndex - 1][colIndex] = 0;
        Puzzle puzz = new Puzzle(a, goal, this.g + 1, index, this.index);
        return puzz;
    }

    public Puzzle moveDown(int index, int[][] goal) {
        int[][] a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = this.positions[i][j];
            }
        }
        a[rowIndex][colIndex] = a[rowIndex + 1][colIndex];
        a[rowIndex + 1][colIndex] = 0;
        Puzzle puzz = new Puzzle(a, goal, this.g + 1, index, this.index);
        return puzz;
    }

    public Puzzle moveLeft(int index, int[][] goal) {
        int[][] a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = this.positions[i][j];
            }
        }
        a[rowIndex][colIndex] = a[rowIndex][colIndex - 1];
        a[rowIndex][colIndex - 1] = 0;
        Puzzle puzz = new Puzzle(a, goal, this.g + 1, index, this.index);
        return puzz;
    }

    public Puzzle moveRight(int index, int[][] goal) {
        int[][] a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = this.positions[i][j];
            }
        }
        a[rowIndex][colIndex] = a[rowIndex][colIndex + 1];
        a[rowIndex][colIndex + 1] = 0;
        Puzzle puzz = new Puzzle(a, goal, this.g + 1, index, this.index);
        return puzz;
    }

    public boolean isGoal() {
        return this.h == 0;
    }

    public int[][] getPositions() {
        return positions;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getIndex() {
        return index;
    }
    
    public int getTrace() {
        return this.trace;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }
    
    

}
