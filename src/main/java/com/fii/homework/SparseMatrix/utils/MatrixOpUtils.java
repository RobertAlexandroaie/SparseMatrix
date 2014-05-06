/**
 * 
 */

package com.fii.homework.SparseMatrix.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix;

/**
 * @author Robert
 */
public class MatrixOpUtils {
    
    private static double epsilon = 1.0 * Math.pow(10, -10);
    
    public static <T extends Number> ArrayList<ArrayList<T>> mulScalarWithMatrix(T scalar, ArrayList<ArrayList<T>> a, Class<T> type) {
	ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
	try {
	    if (a == null) {
		throw new NullPointerException();
	    }
	    int rows = a.size();
	    int columns = a.get(0).size();
	    
	    for (int i = 0; i < rows; i++) {
		ArrayList<T> row = new ArrayList<>();
		for (int j = 0; j < columns; j++) {
		    T elem = ElementOpUtils.mul(scalar, a.get(i).get(j), type);
		    row.add(elem);
		}
		result.add(row);
	    }
	    return result;
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot multiply null matrixes");
	    e.printStackTrace();
	    return null;
	} catch (IndexOutOfBoundsException e) {
	    Logger.getGlobal().log(Level.SEVERE, "check your indexes when multiplying matrixes");
	    e.printStackTrace();
	    return null;
	}
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> mul(ArrayList<ArrayList<T>> a, ArrayList<ArrayList<T>> b, Class<T> type) {
	ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
	try {
	    if (a == null || b == null) {
		throw new NullPointerException();
	    }
	    int rows = a.size();
	    int columns = b.get(0).size();
	    int common = a.get(0).size();
	    
	    for (int i = 0; i < rows; i++) {
		ArrayList<T> row = new ArrayList<>();
		for (int j = 0; j < columns; j++) {
		    T elem = ElementOpUtils.getNeutralSumElem(type);
		    for (int k = 0; k < common; k++) {
			elem = ElementOpUtils.sum(elem, ElementOpUtils.mul(a.get(i).get(k), b.get(k).get(j), type), type);
		    }
		    row.add(elem);
		}
		result.add(row);
	    }
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot multiply null matrixes");
	    e.printStackTrace();
	    return null;
	} catch (IndexOutOfBoundsException e) {
	    Logger.getGlobal().log(Level.SEVERE, "check your indexes when multiplying matrixes");
	    e.printStackTrace();
	    return null;
	}
	return result;
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> sub(ArrayList<ArrayList<T>> a, ArrayList<ArrayList<T>> b, Class<T> type) {
	try {
	    ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
	    if (a.size() != b.size() && a.get(0).size() != b.get(0).size()) {
		throw new IllegalArgumentException();
	    }
	    
	    for (int i = 0; i < a.size(); i++) {
		ArrayList<T> resultRow = new ArrayList<T>();
		ArrayList<T> aRow = a.get(i);
		ArrayList<T> bRow = b.get(i);
		for (int j = 0; j < a.get(0).size(); j++) {
		    resultRow.add(ElementOpUtils.sub(aRow.get(j), bRow.get(j), type));
		}
		result.add(resultRow);
	    }
	    return result;
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot extract from null matrix!");
	    e.printStackTrace();
	    return null;
	} catch (IllegalArgumentException e) {
	    Logger.getGlobal().log(Level.SEVERE, "matrixes have different sizes!");
	    e.printStackTrace();
	    return null;
	} catch (IndexOutOfBoundsException e) {
	    Logger.getGlobal().log(Level.SEVERE, "check indexes!");
	    e.printStackTrace();
	    return null;
	}
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Number> ArrayList<ArrayList<T>> mulSparseMatrixWithArrayMatrix(SparseMatrix a, ArrayList<ArrayList<Double>> b,
	    Class<T> type) {
	try {
	    
	    ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
	    if (a == null || b == null) {
		throw new NullPointerException();
	    }
	    
	    ArrayList<ArrayList<Integer>> rowIndexes = getClone(a.getRowIndexes(), Integer.class);
	    ArrayList<Double> values = VectorOpUtils.getClone(a.getValues(), Double.class);
	    Collections.fill(values, 0.0);
	    
	    int rows = a.getSize();
	    int cols = b.get(0).size();
	    Logger.getLogger(MatrixOpUtils.class.getSimpleName()).info("sorted indexes");
	    
	    for (int i = 0; i < rows; i++) {
		ArrayList<Integer> row = rowIndexes.get(i);
		if (row != null) {
		    ArrayList<T> rowToAdd = new ArrayList<>();
		    for (int col = 0; col < cols; col++) {
			T elem = ElementOpUtils.getNeutralSumElem(type);
			elem = ElementOpUtils.sum(elem, ElementOpUtils.mul((T) a.getElement(i, i), (T) b.get(i).get(col), type), type);
			for (Integer k : row) {
			    elem = ElementOpUtils.sum(elem, ElementOpUtils.mul((T) a.getElement(i, k), (T) b.get(k).get(col), type), type);
			}
			rowToAdd.add(elem);
		    }
		    result.add(rowToAdd);
		}
	    }
	    return result;
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot multiply null matrixes");
	    e.printStackTrace();
	    return null;
	} catch (IndexOutOfBoundsException e) {
	    Logger.getGlobal().log(Level.SEVERE, "check your indexes when multiplying matrixes");
	    e.printStackTrace();
	    return null;
	}
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Number> SparseMatrix mulSparseMatrixWithSparseMatrix(SparseMatrix a, SparseMatrix b, Class<T> type) {
	SparseMatrix result = null;
	try {
	    if (a == null || b == null) {
		throw new NullPointerException();
	    }
	    
	    int size = a.getSize();
	    
	    result = MatrixBuildUtils.buildEmptySparseMatrix(size);
	    
	    ArrayList<ArrayList<Integer>> aRowIndexes = getClone(a.getRowIndexes(), Integer.class);
	    
	    for (int row = 0; row < size; row++) {
		ArrayList<Integer> rowInRowIndexes = aRowIndexes.get(row);
		
		for (int colOfElemInB = 0; colOfElemInB < size; colOfElemInB++) {
		    T elem = ElementOpUtils.getNeutralSumElem(type);
		    T elOfA = (T) a.getElement(row, row);
		    T elOfB = (T) b.getElement(row, colOfElemInB);
		    elem = ElementOpUtils.sum(elem, ElementOpUtils.mul(elOfA, elOfB, type), type);
		    
		    for (Integer col : rowInRowIndexes) {
			elOfA = (T) a.getElement(row, col);
			elOfB = (T) b.getElement(col, colOfElemInB);
			if (((Double) elOfB).doubleValue() != 0.0) {
			    elem = ElementOpUtils.sum(elem, ElementOpUtils.mul(elOfA, elOfB, type), type);
			}
		    }
		    result.setElement(row, colOfElemInB, (Double) elem);
		    elem = ElementOpUtils.getNeutralSumElem(type);
		}
	    }
	    return result;
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot multiply null matrixes");
	    e.printStackTrace();
	    return null;
	} catch (IndexOutOfBoundsException e) {
	    Logger.getGlobal().log(Level.SEVERE, "check your indexes when multiplying matrixes");
	    e.printStackTrace();
	    return null;
	}
    }
    
    public static boolean addInToSparseMatrix(SparseMatrix a) {
	int size = a.getSize();
	ArrayList<Double> diag = a.getDiag();
	for (int i = 0; i < size; i++) {
	    diag.set(i, diag.get(i) + 1.0);
	}
	return false;
    }
    
    /**
     * @return the epsilon
     */
    public static double getEpsilon() {
	return epsilon;
    }
    
    /**
     * @param epsilon
     *            the epsilon to set
     */
    public static void setEpsilon(double epsilon) {
	MatrixOpUtils.epsilon = epsilon;
    }
    
    public static <T> ArrayList<ArrayList<T>> getClone(ArrayList<ArrayList<T>> matrix, Class<T> type) {
	ArrayList<ArrayList<T>> clone = new ArrayList<ArrayList<T>>();
	
	if (matrix == null || matrix.get(0) == null) {
	    Logger.getLogger(MatrixOpUtils.class.getSimpleName()).warning("matrix null or contains null" + matrix);
	} else {
	    
	    for (ArrayList<T> row : matrix) {
		clone.add(new ArrayList<T>(row));
	    }
	}
	return clone;
    }
    
    public static double getNorm1(ArrayList<ArrayList<Double>> matrix) {
	int rows = matrix.size();
	int cols = matrix.get(0).size();
	double sum = 0.0;
	double max = Double.MIN_VALUE;
	
	for (int j = 0; j < cols; j++) {
	    sum = 0.0;
	    for (int i = 0; i < rows; i++) {
		sum += Math.abs(matrix.get(i).get(j));
	    }
	    if (sum > max) {
		max = sum;
	    }
	}
	return max;
    }
}
