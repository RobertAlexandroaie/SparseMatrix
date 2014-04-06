/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fii.homework.SparseMatrix.utils;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 *
 * @author Claudiu
 */
public class SingularValueDecompositionHelper {

    private static SingularValueDecompositionHelper sharedInstance;

    private SingularValueDecompositionHelper() {
    }

    public static SingularValueDecompositionHelper getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new SingularValueDecompositionHelper();
        }
        return sharedInstance;
    }

    public double[] getSingularValues(SingularValueDecomposition sngValDecompMatrix) {
        return sngValDecompMatrix.getSingularValues();
    }
    
    public int getMatrixRank(SingularValueDecomposition sngValDecompMatrix) {
        return sngValDecompMatrix.rank();
    }
    
    public double getMatrixConditionNumber(SingularValueDecomposition sngValDecompMatrix) {
        return sngValDecompMatrix.cond();
    }
    
    public double getMatrixNorm(SingularValueDecomposition sngValDecompMatrix) {
        return sngValDecompMatrix.norm2();
    }
    
}
