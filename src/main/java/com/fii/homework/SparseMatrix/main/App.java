
package com.fii.homework.SparseMatrix.main;


import com.fii.homework.SparseMatrix.models.SngDecompMatrix;

/**
 * @author Robert
 */
public class App {
    public static void main(String[] args) {
//	int rowTest = 15418;
//	int colTest = 21508;
	String filename = "test.txt";
	
	filename = "m_rar_2014_3.txt";
	int n = 6, p = 5;
	
	SngDecompMatrix matrix = new SngDecompMatrix(n, p);
	matrix.generateSystem();
	matrix.computeXI();
    }
}
