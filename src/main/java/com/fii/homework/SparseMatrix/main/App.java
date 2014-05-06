
package com.fii.homework.SparseMatrix.main;


import com.fii.homework.SparseMatrix.models.PowerMethod;

/**
 * @author Robert
 */
public class App {
    public static void main(String[] args) {
	
	PowerMethod powerMethod = null;
	powerMethod = new PowerMethod("test.txt");
	
//	powerMethod = new PowerMethod();
//	powerMethod.createSquareSymmetricSparseMatrix(500);
	powerMethod.solve();
	
    }
}
