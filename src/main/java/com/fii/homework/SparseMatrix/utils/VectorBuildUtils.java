/**
 * 
 */

package com.fii.homework.SparseMatrix.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Robert
 */
public class VectorBuildUtils {
    public static <T extends Number> ArrayList<ArrayList<T>> getColMatrixFromArrayVector(T[] vector, Class<T> type) {
	List<T> vAsList = Arrays.asList(vector);
	ArrayList<ArrayList<T>> vAsMatrix = new ArrayList<ArrayList<T>>();
	vAsMatrix.add(new ArrayList<T>(vAsList));
	ArrayList<ArrayList<T>> vAsColMatrix = MatrixBuildUtils.getTranspose(vAsMatrix, type);
	
	return vAsColMatrix;
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> getRowMatrixFromArrayVector(T[] vector, Class<T> type) {
	List<T> vAsList = Arrays.asList(vector);
	ArrayList<ArrayList<T>> vAsMatrix = new ArrayList<ArrayList<T>>();
	vAsMatrix.add(new ArrayList<T>(vAsList));
	
	return vAsMatrix;
    }
    
}
