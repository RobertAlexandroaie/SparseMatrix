
package com.fii.homework.SparseMatrix.main;


import java.util.ArrayList;

import com.fii.homework.SparseMatrix.models.GaussSeidel;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//	int rowTest = 15418;
//	int colTest = 21508;
	String filename = "test.txt";
	filename = "m_rar_2014_2.txt";
	
	GaussSeidel system = new GaussSeidel(filename);
	long start = System.currentTimeMillis();
	ArrayList<Double> sol = system.solveSystem();
	long stop = System.currentTimeMillis();
	
	System.out.println(1.0 * (stop - start) / 1000);
	System.out.println(sol.get(2));
	System.out.println(system.verifySolution());
    }
}
