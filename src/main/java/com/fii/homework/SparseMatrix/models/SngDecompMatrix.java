/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fii.homework.SparseMatrix.models;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;
import java.util.Random;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

/**
 *
 * @author Claudiu
 */
public class SngDecompMatrix {

    private SimpleMatrix matrix;
    private SimpleMatrix b;
    private int nrLines;
    private int nrColumns;
    private SimpleSVD svd;
    private double[] singularValues;
    private int rank = Integer.MIN_VALUE;
    private double cond = Double.MIN_VALUE;
    private double norm = Double.MIN_VALUE;

    public SngDecompMatrix(int n, int p) {
        matrix = new SimpleMatrix(n, p);
        b = new SimpleMatrix(n, 1);
        nrLines = n;
        nrColumns = p;

    }

    public double getCond() {
        if (cond == Double.MIN_VALUE) {
            Double max = Double.MIN_VALUE;
            Double min = Double.MAX_VALUE;
            double[] sngValues = getSingularValues();
            for (int idx = 0; idx < sngValues.length; idx++) {
                if (sngValues[idx] > max) {
                    max = sngValues[idx];
                }
                if (sngValues[idx] < min && sngValues[idx] > 0) {
                    min = sngValues[idx];
                }
            }
            cond = max / min;
        }
        return cond;
    }

    public void generateSystem() {
        generateMatrix();
        generateB();
    }

    private void generateMatrix() {
        Random random = new Random();
        int range = 5 - (-5) + 1;
        for (int idx = 0; idx < nrLines; idx++) {
            for (int jdx = 0; jdx < nrColumns; jdx++) {
                Double value = (double) random.nextInt(range) + (-5);
                matrix.set(idx, jdx, value);
            }
        }
    }

    private void generateB() {
        Random random = new Random();
        int range = 5 - (-5) + 1;
        for (int idx = 0; idx < nrLines; idx++) {
            Double value = (double) random.nextInt(range) + (-5);
            b.set(idx, 0, value);
        }
    }

    public double[] getSingularValues() {
        if (singularValues == null) {
            return getSvd().getSVD().getSingularValues();
        }
        return singularValues;
    }

    private SimpleSVD getSvd() {
        if (svd == null) {
            svd = matrix.svd();
        }
        return svd;
    }

    public int getRank() {
        if (rank == Integer.MIN_VALUE) {
            rank = getSvd().rank();
        }
        return rank;
    }

    public double getNorm() {
        if (norm == Double.MIN_VALUE) {
            SimpleMatrix x = getSvd().getU().mult(getSvd().getW()).mult(getSvd().getV().transpose());
            SimpleMatrix diff = matrix.minus(x);
            double maxSum = 0.0;
            for (int i = 0; i < diff.numRows(); i++) {
                double sum = 0.0;
                for (int j = 0; j < diff.numCols(); j++) {
                    sum += diff.get(i, j);
                }
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
            norm = maxSum;
        }
        return norm;
    }

    public SimpleMatrix getB() {
        return b;
    }

    public void computeXI() {
        double condTemp = getCond();
        System.out.println("Numarul de conditionare: [" + condTemp + "]");
        double normTemp = getNorm();
        System.out.println("Norma: [" + normTemp + "]");
        SimpleMatrix SI = computeSI();
        SimpleSVD svdTemp = getSvd();
        SimpleMatrix U = svdTemp.getU();
        SimpleMatrix S = svdTemp.getW();
        SimpleMatrix V = svdTemp.getV();
        SimpleMatrix XI = V.mult(SI).mult(U.transpose()).mult(b);
        for (int i = 0; i < XI.getMatrix().numRows; i++) {
            System.out.println("");
            for (int j = 0; j < XI.getMatrix().numCols; j++) {
                System.out.print(XI.get(i, j) + " ");
            }
        }
        System.out.println("");
        if (solvesSystem(XI)) {
            System.out.println("Rezultat corect.");
        } else {
            System.out.println("Rezultat incorect.");
        }
    }

    public SimpleMatrix computeSI() {
        SimpleMatrix siTemp = new SimpleMatrix(nrColumns, nrLines);
        int rankTemp = getRank();
        double sngValues[] = getSingularValues();
        for (int idx = 0; idx < rankTemp; idx++) {
            siTemp.set(idx, idx, 1 / sngValues[idx]);
        }
        return siTemp;
    }

    public boolean solvesSystem(SimpleMatrix XI) {
        SimpleMatrix result = matrix.mult(XI);
        SimpleMatrix difMatrix = b.minus(result);
        System.out.println("A * Xi");
        System.out.println(result);
        System.out.println("B");
        System.out.println(b);
        double sum = 0.0;
        for(int idx = 0 ; idx < difMatrix.numRows(); idx++) {
            sum += Math.abs(difMatrix.get(idx, 0)*difMatrix.get(idx, 0));
        }
        System.out.println("Norma euclidiana: [" + Math.sqrt(sum) + "]");
        if (Math.abs(Math.sqrt(sum)) <= MatrixOpUtils.getEpsilon()) {
            return true;
        } else {
            return false;
        }
    }
}
