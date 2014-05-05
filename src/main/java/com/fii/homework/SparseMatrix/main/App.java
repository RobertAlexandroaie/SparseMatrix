
package com.fii.homework.SparseMatrix.main;


import com.fii.homework.SparseMatrix.models.PowerMethod;
import com.fii.homework.SparseMatrix.utils.VectorOpUtils;

/**
 * @author Robert
 */
public class App {
    public static void main(String[] args) {
        
        PowerMethod pmHelper = new PowerMethod();
        pmHelper.generateSquareSymmetricAndSparseMatrix(10);
        if(pmHelper.getMatrix().isSymmetric()) {
            System.out.println("Matricea este simetrica");
        }
        System.out.println(VectorOpUtils.getEuclidianNorm(pmHelper.getRandomVector(), Double.class));
        
    }
}
