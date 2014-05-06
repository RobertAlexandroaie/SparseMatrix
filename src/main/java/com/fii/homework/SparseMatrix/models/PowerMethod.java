/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fii.homework.SparseMatrix.models;


import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import com.fii.homework.SparseMatrix.utils.MatrixBuildUtils;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;
import com.fii.homework.SparseMatrix.utils.VectorBuildUtils;
import com.fii.homework.SparseMatrix.utils.VectorOpUtils;

/**
 * @author Claudiu
 */
public class PowerMethod {
    private DoubleSparseMatrix A;
    private Double[] v;
    private final short kMax = 500;
    private final double eps = Math.pow(10, -8);
    
    public PowerMethod() {
	
    }
    
    public PowerMethod(String filename) {
	GaussSeidel gs = new GaussSeidel(filename);
	A = (DoubleSparseMatrix) gs.getMatrixA();
    }
    
    public void createSquareSymmetricSparseMatrix(int size) {
	
	A = (DoubleSparseMatrix) MatrixBuildUtils.buildEmptySparseMatrix(size);
	
//	for (int i = 0; i < size; i++) {
//	    for (int j = 0; j < size; j++) {
//		A.setElement(i, j, 0.0);
//	    }
//	}
	
	// compute maximum number of not null  elements that can exist in matrix
	double a = (Math.pow(new Double(size), 2) * 50 / 100) / 2;
	int maxNumberElements = ((int) a);
	int numberOfElements = maxNumberElements / 2 + (int) (Math.random() * (maxNumberElements - maxNumberElements / 2) + 1);
	
	// generate elements
	Random generator = new Random();
	
	int numberOfDiagElementsThatCanBeFilled = (numberOfElements > size) ? size : numberOfElements;
	
	//generate elements on main diagonal
	for (int i = 0; i < size; i++) {
	    A.setElement(i, i, generator.nextDouble() * 10);
	}
	
	numberOfElements -= numberOfDiagElementsThatCanBeFilled;
	if (numberOfElements > 0) {
	    if (numberOfElements < size) {
		for (int i = 0; i < numberOfElements; i++) {
		    int column = generator.nextInt(size);
		    double element = generator.nextDouble() * 10;
		    A.setElement(i, column, element);
		    A.setElement(column, i, element);
		}
	    } else {
		int maxNumberOfElementsPerLine = ((int) (Math.ceil(numberOfElements / size)));
		int line = new Integer(0);
		while (line < size) {
		    for (int i = 0; i < maxNumberOfElementsPerLine; i++) {
			boolean insert = false;
			while (!insert) {
			    int column = generator.nextInt(size);
			    if (line != column) {
				if (A.getElement(line, column) == 0.0) {
				    insert = true;
				    double element = generator.nextDouble() * 10;
				    A.setElement(line, column, element);
				    A.setElement(column, line, element);
				    insert = true;
				}
			    }
			}
		    }
		    line++;
		}
	    }
	}
    }
    
    public void createRandomVector(int matrixSize) {
	v = new Double[matrixSize];
	Random random = new Random();
	for (int i = 0; i < matrixSize; i++) {
//	    v[i] = (double) random.nextInt(100);
	    v[i] = 0.0;
	}
	v[0] = 1.0;
	
//	Double euclidianNorm = VectorOpUtils.getEuclidianNorm(v, Double.class);
//	for (int i = 0; i < matrixSize; i++) {
//	    v[i] *= (1.0 / euclidianNorm);
//	}
    }
    
    /**
     * @return the matrix
     */
    public DoubleSparseMatrix getA() {
	return A;
    }
    
    /**
     * @return the randomVector
     */
    public Double[] getV() {
	return v;
    }
    
    public void solve() {
	Class<Double> type = Double.class;
	try {
	    if (A != null) {
		int size = A.getSize();
		createRandomVector(size);
		ArrayList<ArrayList<Double>> vAsRowMatrix = VectorBuildUtils.getRowMatrixFromArrayVector(v, type);
		ArrayList<ArrayList<Double>> vAsColMatrix = MatrixBuildUtils.getTranspose(vAsRowMatrix, type);
		ArrayList<ArrayList<Double>> w = MatrixOpUtils.mulSparseMatrixWithArrayMatrix(A, vAsColMatrix, type);
		ArrayList<ArrayList<Double>> wAsRow = MatrixBuildUtils.getTranspose(w, type);
		
		Double lambda = MatrixOpUtils.mul(w, vAsRowMatrix, type).get(0).get(0);
		int k = 0;
		
		boolean cond1, cond2;
		Double testNorm = 0.0;
		do {
		    Double norm = VectorOpUtils.getEuclidianNorm(MatrixBuildUtils.getTranspose(w, type).get(0), type);
		    vAsColMatrix = MatrixOpUtils.mulScalarWithMatrix(1.0 / norm, w, type);
		    
		    w = MatrixOpUtils.mulSparseMatrixWithArrayMatrix(A, vAsColMatrix, type);
		    wAsRow = MatrixBuildUtils.getTranspose(w, type);
		    
		    lambda = MatrixOpUtils.mul(wAsRow, vAsColMatrix, type).get(0).get(0);
		    k++;
		    
		    ArrayList<ArrayList<Double>> lambdaMulV = MatrixOpUtils.mulScalarWithMatrix(lambda, vAsColMatrix, type);
		    ArrayList<ArrayList<Double>> wSubsLambdaMulV = MatrixOpUtils.sub(w, lambdaMulV, type);
		    ArrayList<Double> substracted = MatrixBuildUtils.getTranspose(wSubsLambdaMulV, type).get(0);
		    testNorm = VectorOpUtils.getEuclidianNorm(substracted, type);
		    
		    cond1 = (testNorm > (size * eps));
		    cond2 = (k <= kMax);
		} while (cond1 && cond2);
		
		if (!cond1) {
		    Logger.getLogger(PowerMethod.class.getSimpleName()).info("eigenvector computed: \n" + vAsColMatrix + "\n" + lambda);
		} else if (!cond2) {
		    Logger.getLogger(PowerMethod.class.getSimpleName()).info(
			    "cannot compute value: \n" + testNorm + "\n" + lambda + "\n" + vAsColMatrix);
		}
	    } else {
		throw new NullPointerException();
	    }
	} catch (NullPointerException e) {
	    Logger.getLogger(PowerMethod.class.getSimpleName()).warning("matrix is null");
	}
    }
}
