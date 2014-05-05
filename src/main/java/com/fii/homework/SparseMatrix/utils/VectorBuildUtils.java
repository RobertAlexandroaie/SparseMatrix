/**
 * 
 */

package com.fii.homework.SparseMatrix.utils;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Robert
 */
public class VectorBuildUtils {
    public static <T extends Number> ArrayList<ArrayList<T>> getColMatrixFromArrayVector(T[] vector, Class<T> type) {
	ArrayList<T> vAsList = (ArrayList<T>) Arrays.asList(vector);
	ArrayList<ArrayList<T>> vAsMatrix = new ArrayList<ArrayList<T>>();
	vAsMatrix.add(vAsList);
	ArrayList<ArrayList<T>> vAsColMatrix = MatrixBuildUtil.getTranspose(vAsMatrix, type);
	
	return vAsColMatrix;
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> getRowMatrixFromArrayVector(T[] vector, Class<T> type) {
	ArrayList<T> vAsList = (ArrayList<T>) Arrays.asList(vector);
	ArrayList<ArrayList<T>> vAsMatrix = new ArrayList<ArrayList<T>>();
	vAsMatrix.add(vAsList);
	
	return vAsMatrix;
    }
    
}
