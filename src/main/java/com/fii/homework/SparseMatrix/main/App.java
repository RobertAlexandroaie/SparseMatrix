
package com.fii.homework.SparseMatrix.main;


import com.fii.homework.SparseMatrix.models.GaussSeidel;
import com.fii.homework.SparseMatrix.models.SngDecompMatrix;
import com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;
import com.fii.homework.SparseMatrix.utils.PowerMethodHelper;
import com.fii.homework.SparseMatrix.utils.VectorOpUtils;

/**
 * @author Robert
 */
public class App {
    public static void main(String[] args) {
        
        PowerMethodHelper pmHelper = new PowerMethodHelper();
        pmHelper.generateSquareSymmetricAndSparseMatrix(10);
        if(pmHelper.getMatrix().isSymmetric()) {
            System.out.println("Matricea este simetrica");
        }
        System.out.println(VectorOpUtils.getEuclidianNorm(pmHelper.getRandomVector(), Double.class));
        
    }
}
