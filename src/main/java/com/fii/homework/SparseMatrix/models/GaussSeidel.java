/**
 * 
 */

package com.fii.homework.SparseMatrix.models;


import java.util.ArrayList;
import java.util.Collections;

import com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix;
import com.fii.homework.SparseMatrix.utils.MatrixBuildUtil;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;

/**
 * @author Robert
 */
public class GaussSeidel {
    private SparseMatrix matrixA;
    private SparseMatrix matrixB;
    private SparseMatrix matrixC;
    //asda
    
    private ArrayList<Double> b;
    private final ArrayList<Double> x;
    
    final int K_MAX = 800;
    private final double epsilon = 1.0 * Math.pow(10, -12);
    
    public GaussSeidel(String filename) {
	MatrixBuildUtil.buildSystemFromFile(filename, this);
	int size = matrixA.getSize();
	x = new ArrayList<Double>(size);
	for (int i = 0; i < size; i++) {
	    x.add(0.0);
	}
    }
    
    /**
     * @return the matrixA
     */
    public SparseMatrix getMatrixA() {
	return matrixA;
    }
    
    /**
     * @return the matrixB
     */
    public SparseMatrix getMatrixB() {
	return matrixB;
    }
    
    /**
     * @return the matrixC
     */
    public SparseMatrix getMatrixC() {
	return matrixC;
    }
    
    /**
     * @return the b
     */
    public ArrayList<Double> getB() {
	return b;
    }
    
    /**
     * @param b
     *            the b to set
     */
    public void setB(ArrayList<Double> b) {
	this.b = b;
    }
    
    /**
     * @return the k_MAX
     */
    public int getK_MAX() {
	return K_MAX;
    }
    
    /**
     * @return the epsilon
     */
    public double getEpsilon() {
	return epsilon;
    }
    
    /**
     * @param matrixA
     *            the matrixA to set
     */
    public void setMatrixA(SparseMatrix matrixA) {
	this.matrixA = matrixA;
    }
    
    /**
     * @param matrixB
     *            the matrixB to set
     */
    public void setMatrixB(SparseMatrix matrixB) {
	this.matrixB = matrixB;
    }
    
    /**
     * @param matrixC
     *            the matrixC to set
     */
    public void setMatrixC(SparseMatrix matrixC) {
	this.matrixC = matrixC;
    }
    
    public ArrayList<Double> solveSystem() {
	Collections.fill(x, 0.0);
	int k = 0;
	int size = x.size();
	double delta = 0.0;
	ArrayList<ArrayList<Integer>> rows = matrixA.getRowIndexes();
	do {
	    double t1 = 0.0;
	    double t2 = 0.0;
	    delta = 0.0;
	    for (int i = 0; i < size; i++) {
		t1 = b.get(i);
		t2 = 0.0;
		ArrayList<Integer> row = rows.get(i);
		for (Integer j : row) {
		    double element = matrixA.getElement(i, j);
		    t2 += element * x.get(j);
		}
		
		double value = (t1 - t2) / matrixA.getElement(i, i);
		double nI = value - x.get(i);
		nI = Math.abs(nI);
		delta += nI * nI;
		
		x.set(i, value);
	    }
	    delta = Math.sqrt(delta);
	} while (delta >= epsilon && k <= K_MAX && delta <= 100000000);
	
	if (delta < epsilon) {
	    return x;
	} else {
	    return null;
	}
    }
    
    public boolean verifySolution() {
	boolean correct = false;
	ArrayList<ArrayList<Double>> solutionAsMatrix = new ArrayList<ArrayList<Double>>();
	solutionAsMatrix.add(x);
	
	ArrayList<ArrayList<Double>> solutionT = MatrixBuildUtil.getTranspose(solutionAsMatrix, Double.class);
	ArrayList<ArrayList<Double>> Axsol = MatrixOpUtils.mulSparseMatrixWithArrayMatrix(matrixA, solutionT, Double.class);
	
	ArrayList<ArrayList<Double>> bT = new ArrayList<ArrayList<Double>>();
	bT.add(b);
	bT = MatrixBuildUtil.getTranspose(bT, Double.class);
	
	ArrayList<ArrayList<Double>> diff = MatrixOpUtils.sub(Axsol, bT, Double.class);
	double norm = MatrixOpUtils.getNorm1(diff);
	if (norm <= matrixA.getSize() * epsilon) {
	    correct = true;
	}
	return correct;
    }
}
