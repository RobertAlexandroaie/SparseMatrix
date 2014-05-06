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
    
    public void createSquareSymmetricSparseMatrix(int size) {
	
	A = (DoubleSparseMatrix) MatrixBuildUtil.buildEmptySparseMatrix(size);
	
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
	    v[i] = (double) random.nextInt(100);
	}
	Double euclidianNorm = VectorOpUtils.getEuclidianNorm(getV(), Double.class);
	for (int i = 0; i < matrixSize; i++) {
	    v[i] = (1 / euclidianNorm) * getV()[i];
	}
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
