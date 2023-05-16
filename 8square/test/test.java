
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import pkg8square.Puzzle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class test {

   
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<Puzzle> puzz = new ArrayList<>();
        int [][] a = {{1,2,3},{1,2,3}, {1,2,3}};
        int [][] b = {{1,2,2},{1,2,3}, {1,2,3}};
        puzz.add(new Puzzle(a,a,1,0,-1));
        if(puzz.contains(new Puzzle(b,a,10,10,2)))
        {
            System.out.println("Contains");
        }        
    }
}
