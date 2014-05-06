
package com.fii.homework.SparseMatrix.main;


import com.fii.homework.SparseMatrix.models.PowerMethod;

/**
 * @author Robert
 */
public class App {
    public static void main(String[] args) {
	
	PowerMethod powerMethod = new PowerMethod();
	powerMethod.createSquareSymmetricSparseMatrix(10);
	powerMethod.solve();
	
    }
}
