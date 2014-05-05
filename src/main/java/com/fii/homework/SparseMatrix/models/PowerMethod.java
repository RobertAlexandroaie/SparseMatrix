/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fii.homework.SparseMatrix.models;


import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import com.fii.homework.SparseMatrix.utils.MatrixBuildUtil;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;
import com.fii.homework.SparseMatrix.utils.VectorBuildUtils;
import com.fii.homework.SparseMatrix.utils.VectorOpUtils;

/**
 * @author Claudiu
 */
public class PowerMethod {
    private DoubleSparseMatrix A;
    private Double[] v;
    private final short kMax = 100;
    private final double eps = Math.pow(1, -10);
    
    public PowerMethod() {
    }
    
    public void generateSquareSymmetricAndSparseMatrix(Integer matrixSize) {
	
	A = (DoubleSparseMatrix) MatrixBuildUtil.buildEmptySparseMatrix(matrixSize);
	for (int i = 0; i < matrixSize; i++) {
	    for (int j = 0; j < matrixSize; j++) {
		getMatrix().setElement(i, j, 0.0);
	    }
	}
	// compute maximum number of not null  elements that can exist in matrix
	Double a = (Math.pow(new Double(matrixSize), 2) * 50 / 100) / 2;
	Integer maxNumberElements = ((int) a.doubleValue());
	Integer numberOfElements = maxNumberElements / 2 + (int) (Math.random() * (maxNumberElements - maxNumberElements / 2) + 1);
	
	// generate elements
	Random generator = new Random();
	
	int numberOfDiagElementsThatCanBeFilled = (numberOfElements > matrixSize) ? matrixSize : numberOfElements;
	//generate elements on main diagonal
	for (int i = 0; i < numberOfDiagElementsThatCanBeFilled; i++) {
	    getMatrix().setElement(i, i, generator.nextDouble() * 10);
	}
	
	numberOfElements -= numberOfDiagElementsThatCanBeFilled;
	if (numberOfElements > 0) {
	    
	    if (numberOfElements < matrixSize) {
		for (int i = 0; i < numberOfElements; i++) {
		    int column = generator.nextInt(matrixSize);
		    double element = generator.nextDouble() * 10;
		    getMatrix().setElement(i, column, element);
		    getMatrix().setElement(column, i, element);
		}
	    } else {
		Integer maxNumberOfElementsPerLine = ((int) new Double(Math.ceil(numberOfElements / matrixSize)).doubleValue());
		Integer line = new Integer(0);
		while (line < matrixSize) {
		    for (int i = 0; i < maxNumberOfElementsPerLine; i++) {
			boolean insert = false;
			while (!insert) {
			    Integer column = generator.nextInt(matrixSize);
			    if (line != column) {
				if (getMatrix().getElement(line, column) == 0.0) {
				    insert = true;
				    Double element = generator.nextDouble() * 10;
				    getMatrix().setElement(line, column, element);
				    getMatrix().setElement(column, line, element);
				    insert = true;
				}
			    }
			}
		    }
		    line++;
		}
	    }
	}
	generateVector(matrixSize);
    }
    
    private void generateVector(int matrixSize) {
	v = new Double[matrixSize];
	Random random = new Random();
	for (int i = 0; i < matrixSize; i++) {
	    v[i] = (double) random.nextInt(100);
	}
	Double euclidianNorm = VectorOpUtils.getEuclidianNorm(getRandomVector(), Double.class);
	for (int i = 0; i < matrixSize; i++) {
	    v[i] = (1 / euclidianNorm) * getRandomVector()[i];
	}
    }
    
    /**
     * @return the matrix
     */
    public DoubleSparseMatrix getMatrix() {
	return A;
    }
    
    /**
     * @return the randomVector
     */
    public Double[] getRandomVector() {
	return v;
    }
    
    public void solve() {
	Class<Double> type = Double.class;
	try {
	    if (A != null) {
		int size = A.getSize();
		generateVector(size);
		
		ArrayList<ArrayList<Double>> vAsRowMatrix = VectorBuildUtils.getRowMatrixFromArrayVector(v, type);
		ArrayList<ArrayList<Double>> vAsColMatrix = MatrixBuildUtil.getTranspose(vAsRowMatrix, type);
		ArrayList<ArrayList<Double>> w = MatrixOpUtils.mulSparseMatrixWithArrayMatrix(A, vAsColMatrix, type);
		
		Double lambda = MatrixOpUtils.mul(w, vAsRowMatrix, type).get(0).get(0);
		int k = 0;
		
		boolean cond1, cond2;
		
		do {
		    ArrayList<ArrayList<Double>> lambdaMulV = MatrixOpUtils.mulScalarWithMatrix(lambda, vAsColMatrix, type);
		    ArrayList<ArrayList<Double>> wSubsLambdaMulV = MatrixOpUtils.sub(w, lambdaMulV, type);
		    ArrayList<Double> substracted = MatrixBuildUtil.getTranspose(wSubsLambdaMulV, type).get(0);
		    Double testNorm = VectorOpUtils.getEuclidianNorm(substracted, type);
		    
		    Double norm = VectorOpUtils.getEuclidianNorm(MatrixBuildUtil.getTranspose(w, type).get(0), type);
		    vAsColMatrix = MatrixOpUtils.mulScalarWithMatrix(norm, w, type);
		    vAsRowMatrix = MatrixBuildUtil.getTranspose(vAsColMatrix, type);
		    
		    w = MatrixOpUtils.mulSparseMatrixWithArrayMatrix(A, vAsColMatrix, type);
		    lambda = MatrixOpUtils.mul(w, vAsRowMatrix, type).get(0).get(0);
		    k++;
		    cond1 = testNorm > size * eps;
		    cond2 = k <= kMax;
		} while (cond1 && cond2);
		
		if (!cond1) {
		    Logger.getLogger(PowerMethod.class.getSimpleName()).info("eigenvector computed: " + vAsRowMatrix);
		}
		if (!cond2) {
		    Logger.getLogger(PowerMethod.class.getSimpleName()).info("cannot compute value");
		}
	    } else {
		throw new NullPointerException();
	    }
	} catch (NullPointerException e) {
	    Logger.getLogger(PowerMethod.class.getSimpleName()).warning("matrix is null");
	}
    }
}
