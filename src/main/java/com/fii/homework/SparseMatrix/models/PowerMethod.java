/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fii.homework.SparseMatrix.models;


import java.util.Random;
import java.util.logging.Logger;

import com.fii.homework.SparseMatrix.utils.MatrixBuildUtil;
import com.fii.homework.SparseMatrix.utils.VectorOpUtils;

/**
 * @author Claudiu
 */
public class PowerMethod {
    private DoubleSparseMatrix A;
    private Double[] v;
    
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
	try {
	    if (A != null) {
		int size = A.getSize();
		generateVector(size);
		//as
		//asdas
	    } else {
		throw new NullPointerException();
	    }
	} catch (NullPointerException e) {
	    Logger.getLogger(PowerMethod.class.getSimpleName()).warning("matrix is null");
	}
    }
}
