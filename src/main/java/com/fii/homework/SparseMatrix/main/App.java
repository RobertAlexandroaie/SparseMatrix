
package com.fii.homework.SparseMatrix.main;


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
