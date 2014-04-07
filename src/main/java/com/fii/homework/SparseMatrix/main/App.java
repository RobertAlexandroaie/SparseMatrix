
package com.fii.homework.SparseMatrix.main;


import com.fii.homework.SparseMatrix.models.GaussSeidel;
import com.fii.homework.SparseMatrix.models.SngDecompMatrix;
import com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;

/**
 * @author Robert
 */
public class App {
    public static void main(String[] args) {
//	int rowTest = 15418;
//	int colTest = 21508;
	String filename = "test.txt";
	
	filename = "m_rar_2014_5.txt";
	GaussSeidel system = new GaussSeidel(filename);
        SparseMatrix A = system.getMatrixA();
        SparseMatrix B = system.getMatrixB();
        MatrixOpUtils.addInToSparseMatrix(B);
        
        SparseMatrix C = MatrixOpUtils.mulSparseMatrixWithSparseMatrix(A, B, Double.class);
        System.out.println(C);
        int n = 5, p = 5;
	
	SngDecompMatrix matrix = new SngDecompMatrix(n, p);
	matrix.generateSystem();
	matrix.computeXI();
    }
}
