/**
 * 
 */

package com.fii.homework.SparseMatrix.utils;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fii.homework.SparseMatrix.models.DoubleSparseMatrix;
import com.fii.homework.SparseMatrix.models.GaussSeidel;
import com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix;

/**
 * @author Robert
 */
public class MatrixBuildUtil {
    
    private static void buildB(ArrayList<Double> b, BufferedReader buffReader, int size) throws NumberFormatException, IOException {
	for (int i = 0; i < size; i++) {
	    b.add(Double.parseDouble(buffReader.readLine()));
	}
    }
    
    private static void addElement(ArrayList<ArrayList<Integer>> rowIndexes, ArrayList<ArrayList<String>> colIndexes, ArrayList<Double> values,
	    ArrayList<Double> diag, int row, int col, double value) throws NumberFormatException {
	boolean positionOcupied = false;
	if (row == col) {
	    Double diagElem = diag.get(col);
	    if (diagElem != 0.0) {
		diagElem += value;
	    } else {
		diagElem = value;
	    }
	    diag.set(row, diagElem);
	} else {
	    ArrayList<Integer> rowInRowIndexes = rowIndexes.get(row);
	    if (rowInRowIndexes.contains(col)) {
		positionOcupied = true;
	    } else {
		rowInRowIndexes.add(col);
	    }
	    if (!positionOcupied) {
		colIndexes.get(col).add(values.size() + "|" + row);
		values.add(value);
	    } else {
		ArrayList<String> rowInColIndexes = colIndexes.get(col);
		for (String line : rowInColIndexes) {
		    if (line.endsWith("|" + row)) {
			String[] splitLine = line.split("\\|");
			int indexOfElemInValues = Integer.parseInt(splitLine[0]);
			double newValue = values.get(indexOfElemInValues);
			newValue += value;
			values.set(indexOfElemInValues, newValue);
		    }
		}
	    }
	}
    }
    
    private static void buildMatrix(ArrayList<ArrayList<Integer>> rowIndexes, ArrayList<ArrayList<String>> colIndexes, ArrayList<Double> values,
	    ArrayList<Double> diag, BufferedReader buffReader, int size) throws IOException, NumberFormatException {
	String line = null;
	
	for (int i = 0; i < size; i++) {
	    rowIndexes.add(new ArrayList<Integer>());
	    colIndexes.add(new ArrayList<String>());
	    diag.add(new Double(0.0));
	}
	
	while ((line = buffReader.readLine()) != null) {
	    String[] splitLine = line.split("(, )");
	    Double value = Double.parseDouble(splitLine[0]);
	    int row = Integer.parseInt(splitLine[1]);
	    int col = Integer.parseInt(splitLine[2]);
	    addElement(rowIndexes, colIndexes, values, diag, row, col, value);
	}
    }
    
    public static SparseMatrix buildEmptySparseMatrix(int size) {
	SparseMatrix result = null;
	
	ArrayList<ArrayList<Integer>> resultRowIndexes = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<String>> resultColIndexes = new ArrayList<ArrayList<String>>();
	ArrayList<Double> resultValues = new ArrayList<Double>();
	ArrayList<Double> resultDiag = new ArrayList<Double>();
	
	for (int i = 0; i < size; i++) {
	    resultRowIndexes.add(null);
	    resultColIndexes.add(null);
	    resultDiag.add(0.0);
	}
	result = new DoubleSparseMatrix(resultRowIndexes, resultColIndexes, resultValues, resultDiag, size);
	
	return result;
    }
    
    public static void buildSystemFromFile(String filename, GaussSeidel system) {
	ArrayList<ArrayList<Integer>> rowIndexes = null;
	ArrayList<ArrayList<String>> colIndexes = null;
	ArrayList<Double> values = null;
	ArrayList<Double> diag = null;
	ArrayList<Double> b = null;
	int size = 0;
	
	BufferedReader buffReader = null;
	
	try {
	    buffReader = new BufferedReader(new FileReader(filename));
	    
	    size = Integer.parseInt(buffReader.readLine());
	    buffReader.readLine();
	    
	    b = new ArrayList<Double>(size);
	    buildB(b, buffReader, size);
	    
	    buffReader.readLine();
	    rowIndexes = new ArrayList<ArrayList<Integer>>(size);
	    colIndexes = new ArrayList<ArrayList<String>>(size);
	    values = new ArrayList<Double>();
	    diag = new ArrayList<Double>();
	    buildMatrix(rowIndexes, colIndexes, values, diag, buffReader, size);
	    
	    SparseMatrix matrixA = new DoubleSparseMatrix(rowIndexes, colIndexes, values, diag, size);
	    SparseMatrix matrixB = matrixA.BMatrix();
	    SparseMatrix matrixC = matrixA.CMatrix();
	    system.setB(b);
	    system.setMatrixA(matrixA);
	    system.setMatrixB(matrixB);
	    system.setMatrixC(matrixC);
	    
	} catch (FileNotFoundException e) {
	    Logger.getLogger(MatrixBuildUtil.class.getSimpleName()).warning("the file doesn't exist");
	    e.printStackTrace();
	} catch (NumberFormatException e) {
	    Logger.getLogger(MatrixBuildUtil.class.getSimpleName()).warning("cannot parse string to number");
	    e.printStackTrace();
	} catch (IOException e) {
	    Logger.getLogger(MatrixBuildUtil.class.getSimpleName()).warning("cannot read line");
	    e.printStackTrace();
	} finally {
	    if (buffReader != null) {
		try {
		    buffReader.close();
		} catch (IOException e) {
		    Logger.getLogger(MatrixBuildUtil.class.getSimpleName()).warning("cannot close buff reader");
		    e.printStackTrace();
		}
	    }
	}
	
    }
    
    public static SparseMatrix BMatrix(SparseMatrix AMatrix) {
	SparseMatrix BMatrix = null;
	
	ArrayList<ArrayList<Integer>> rowIndexes = null;
	ArrayList<ArrayList<String>> colIndexes = null;
	ArrayList<Double> values = null;
	ArrayList<Double> diag = null;
	int size = AMatrix.getSize();
	
	rowIndexes = new ArrayList<ArrayList<Integer>>(size);
	colIndexes = new ArrayList<ArrayList<String>>(size);
	values = new ArrayList<Double>();
	diag = new ArrayList<Double>();
	
	for (int i = 0; i < size; i++) {
	    rowIndexes.add(new ArrayList<Integer>());
	    colIndexes.add(new ArrayList<String>());
	    diag.add(new Double(0.0));
	}
	
	ArrayList<ArrayList<Integer>> rowIndexesOfA = AMatrix.getRowIndexes();
	for (int row = 0; row < size; row++) {
	    ArrayList<Integer> rowInRowIndexesOfA = rowIndexesOfA.get(row);
	    double value = AMatrix.getElement(row, row);
	    addElement(rowIndexes, colIndexes, values, diag, row, row, value);
	    
	    for (int i = 0; i < rowInRowIndexesOfA.size(); i++) {
		int col = rowInRowIndexesOfA.get(i);
		if (col < row) {
		    value = AMatrix.getElement(row, col);
		    addElement(rowIndexes, colIndexes, values, diag, row, col, value);
		}
		
	    }
	}
	
	BMatrix = new DoubleSparseMatrix(rowIndexes, colIndexes, values, diag, size);
	return BMatrix;
    }
    
    public static SparseMatrix CMatrix(SparseMatrix AMatrix) {
	SparseMatrix CMatrix = null;
	
	ArrayList<ArrayList<Integer>> rowIndexes = null;
	ArrayList<ArrayList<String>> colIndexes = null;
	ArrayList<Double> values = null;
	ArrayList<Double> diag = null;
	int size = AMatrix.getSize();
	
	rowIndexes = new ArrayList<ArrayList<Integer>>(size);
	colIndexes = new ArrayList<ArrayList<String>>(size);
	values = new ArrayList<Double>();
	diag = new ArrayList<Double>();
	
	for (int i = 0; i < size; i++) {
	    rowIndexes.add(new ArrayList<Integer>());
	    colIndexes.add(new ArrayList<String>());
	    diag.add(new Double(0.0));
	}
	
	ArrayList<ArrayList<Integer>> rowIndexesOfA = AMatrix.getRowIndexes();
	for (int row = 0; row < size; row++) {
	    ArrayList<Integer> rowInRowIndexesOfA = rowIndexesOfA.get(row);
	    for (int i = 0; i < rowInRowIndexesOfA.size(); i++) {
		int col = rowInRowIndexesOfA.get(i);
		if (col > row) {
		    double value = AMatrix.getElement(row, col);
		    addElement(rowIndexes, colIndexes, values, diag, row, col, -value);
		}
	    }
	}
	
	CMatrix = new DoubleSparseMatrix(rowIndexes, colIndexes, values, diag, size);
	return CMatrix;
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Number> T getNeutralSumElem(Class<T> type) {
	switch (type.getSimpleName()) {
	    case "Double":
		return (T) (new Double(0.0));
	    case "Float":
		return (T) (new Float(0.0));
	    case "Byte":
		return (T) (new Byte((byte) 0));
	    case "Short":
		return (T) (new Short((short) 0));
	    case "Integer":
		return (T) (new Integer(0));
	    case "Long":
		return (T) (new Long(0));
	}
	return null;
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Number> T getNeutralMulElem(Class<T> type) {
	switch (type.getSimpleName()) {
	    case "Double":
		return (T) (new Double(1.0));
	    case "Float":
		return (T) (new Float(1.0));
	    case "Byte":
		return (T) (new Byte((byte) 1));
	    case "Short":
		return (T) (new Short((short) 1));
	    case "Integer":
		return (T) (new Integer(1));
	    case "Long":
		return (T) (new Long(1));
	}
	return null;
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> getIn(int n, Class<T> type) {
	ArrayList<ArrayList<T>> In = new ArrayList<ArrayList<T>>();
	for (int i = 0; i < n; i++) {
	    ArrayList<T> row = new ArrayList<T>();
	    for (int j = 0; j < n; j++) {
		if (i != j) {
		    row.add(getNeutralSumElem(type));
		} else {
		    row.add(getNeutralMulElem(type));
		}
	    }
	    In.add(row);
	}
	return In;
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> getClone(ArrayList<ArrayList<T>> matrix, Class<T> type) {
	ArrayList<ArrayList<T>> clone = new ArrayList<ArrayList<T>>();
	for (ArrayList<T> row : matrix) {
	    clone.add(new ArrayList<T>(row));
	}
	return clone;
    }
    
    public static <T extends Number> ArrayList<ArrayList<T>> getTranspose(ArrayList<ArrayList<T>> a, Class<T> type) {
	try {
	    int rows = a.size();
	    int columns = a.get(0).size();
	    
	    ArrayList<ArrayList<T>> transpose = new ArrayList<ArrayList<T>>();
	    for (int i = 0; i < columns; i++) {
		ArrayList<T> row = new ArrayList<>();
		for (int j = 0; j < rows; j++) {
		    row.add(a.get(j).get(i));
		}
		transpose.add(row);
	    }
	    return transpose;
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot transpose null matrix!");
	    e.printStackTrace();
	    return null;
	}
    }
}
