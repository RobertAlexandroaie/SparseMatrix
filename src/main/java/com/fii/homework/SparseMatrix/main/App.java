
package com.fii.homework.SparseMatrix.main;


import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.util.ArrayList;

import com.fii.homework.SparseMatrix.models.GaussSeidel;
import com.fii.homework.SparseMatrix.utils.SingularValueDecompositionHelper;
import java.util.Arrays;
import java.util.Collections;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//	int rowTest = 15418;
//	int colTest = 21508;
	String filename = "test.txt";
	filename = "m_rar_2014_3.txt";
	
	GaussSeidel system = new GaussSeidel(filename);
//	long start = System.currentTimeMillis();
//	ArrayList<Double> sol = system.solveSystem();
//	long stop = System.currentTimeMillis();
//	
//	System.out.println(1.0 * (stop - start) / 1000);
//	//System.out.println(sol.get(2));
//	System.out.println(system.verifySolution());
	
	double[][] vals = system.getMatrixA().asArray();
        Matrix matrix = Matrix.random(5, 5);
        SingularValueDecomposition sngValDecompMatrix = new SingularValueDecomposition(matrix);
        double []singularValues = SingularValueDecompositionHelper.getSharedInstance().getSingularValues(sngValDecompMatrix);
        double condNumber = SingularValueDecompositionHelper.getSharedInstance().getMatrixConditionNumber(sngValDecompMatrix);
        int rank = SingularValueDecompositionHelper.getSharedInstance().getMatrixRank(sngValDecompMatrix);
        double norm = SingularValueDecompositionHelper.getSharedInstance().getMatrixNorm(sngValDecompMatrix);
        System.out.println(singularValues);
        System.out.println(condNumber);
        System.out.println(rank);
        System.out.println(norm);
    }
}
