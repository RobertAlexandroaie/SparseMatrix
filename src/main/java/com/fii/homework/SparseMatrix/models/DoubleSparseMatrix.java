/**
 *
 */

package com.fii.homework.SparseMatrix.models;


import java.util.ArrayList;

import com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix;
import com.fii.homework.SparseMatrix.utils.MatrixBuildUtil;
import com.fii.homework.SparseMatrix.utils.MatrixOpUtils;
import com.fii.homework.SparseMatrix.utils.VectorOpUtils;

/**
 * @author Robert
 */
public class DoubleSparseMatrix implements SparseMatrix {
    
    private ArrayList<ArrayList<String>> colIndexes;
    private ArrayList<Double> diag;
    private ArrayList<ArrayList<Integer>> rowIndexes;
    private int size;
    private ArrayList<Double> values;
    
    public DoubleSparseMatrix(ArrayList<ArrayList<Integer>> rowIndexes, ArrayList<ArrayList<String>> colIndexes, ArrayList<Double> values,
	    ArrayList<Double> diag, int size) {
	this.colIndexes = colIndexes;
	this.rowIndexes = rowIndexes;
	this.values = values;
	this.diag = diag;
	this.size = size;
    }
    
    public DoubleSparseMatrix(SparseMatrix b) {
	colIndexes = MatrixOpUtils.getClone(b.getColIndexes(), String.class);
	rowIndexes = MatrixOpUtils.getClone(b.getRowIndexes(), Integer.class);
	size = b.getSize();
	values = VectorOpUtils.getClone(b.getValues(), Double.class);
	diag = VectorOpUtils.getClone(b.getDiag(), Double.class);
    }
    
    /**
     * @return the colIndexes
     */
    @Override
    public ArrayList<ArrayList<String>> getColIndexes() {
	return colIndexes;
    }
    
    /**
     * @return the diag
     */
    @Override
    public ArrayList<Double> getDiag() {
	return diag;
    }
    
    /**
     * @return the rowIndexes
     */
    @Override
    public ArrayList<ArrayList<Integer>> getRowIndexes() {
	return rowIndexes;
    }
    
    /**
     * @return the size
     */
    @Override
    public int getSize() {
	return size;
    }
    
    /**
     * @return the values
     */
    @Override
    public ArrayList<Double> getValues() {
	return values;
    }
    
    /**
     * @param colIndexes
     *            the colIndexes to set
     */
    public void setColIndexes(ArrayList<ArrayList<String>> colIndexes) {
	this.colIndexes = colIndexes;
    }
    
    /**
     * @param diag
     *            the diag to set
     */
    public void setDiag(ArrayList<Double> diag) {
	this.diag = diag;
    }
    
    /**
     * @param rowIndexes
     *            the rowIndexes to set
     */
    public void setRowIndexes(ArrayList<ArrayList<Integer>> rowIndexes) {
	this.rowIndexes = rowIndexes;
    }
    
    /**
     * @param size
     *            the size to set
     */
    public void setSize(int size) {
	this.size = size;
    }
    
    /**
     * @param values
     *            the values to set
     */
    public void setValues(ArrayList<Double> values) {
	this.values = values;
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix#getBMatrix()
     */
    @Override
    public SparseMatrix BMatrix() {
	return MatrixBuildUtil.BMatrix(this);
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix#getCMatrix
     * (com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix)
     */
    @Override
    public SparseMatrix CMatrix() {
	return MatrixBuildUtil.CMatrix(this);
    }
    
    @Override
    public SparseMatrix mul(SparseMatrix sparseMatrix) {
	return MatrixOpUtils.mulSparseMatrixWithSparseMatrix(this, sparseMatrix, Double.class);
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix#getElement
     * (int, int)
     */
    @Override
    public Double getElement(int row, int column) {
	int index = 0;
	boolean elementIsZero = true;
	if (row == column) {
	    return diag.get(row);
	} else {
	    for (String line : colIndexes.get(column)) {
		if (line.endsWith("|" + row)) {
		    index = Integer.parseInt(line.split("\\|")[0]);
		    elementIsZero = false;
		    break;
		}
	    }
	    
	    if (!elementIsZero) {
		return values.get(index);
	    } else {
		return 0.0;
	    }
	}
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder stringToReturn = new StringBuilder();
	stringToReturn.append(size);
	stringToReturn.append("\n");
	for (Double value : values) {
	    stringToReturn.append(value + "\n");
	}
	return stringToReturn.toString();
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix#setElement
     * (int, int, double)
     */
    @Override
    public boolean setElement(int row, int column, double value) {
	boolean added = false;
	if (row == column) {
	    diag.set(row, value);
	    return true;
	} else {
	    ArrayList<Integer> rowInRowIndexes = rowIndexes.get(row);
	    ArrayList<String> rowInColIndexes = colIndexes.get(column);
	    if (rowInRowIndexes == null) {
		rowInRowIndexes = new ArrayList<Integer>();
		rowInRowIndexes.add(row);
		rowIndexes.set(row, rowInRowIndexes);
		if (rowInColIndexes == null) {
		    rowInColIndexes = new ArrayList<String>();
		    rowInColIndexes.add(values.size() + "|" + row);
		    colIndexes.set(column, rowInColIndexes);
		    values.add(value);
		    return true;
		} else {
		    for (String line : rowInColIndexes) {
			if (line.endsWith("|" + row)) {
			    int indexOfVal = Integer.parseInt(line.split("\\|")[0]);
			    values.set(indexOfVal, value);
			    added = true;
			    return true;
			}
		    }
		    if (!added) {
			rowInColIndexes.add(values.size() + "|" + row);
			values.add(value);
			added = true;
		    }
		}
	    } else {
		if (!rowInRowIndexes.contains(column)) {
		    rowInRowIndexes.add(column);
		}
		if (rowInColIndexes == null) {
		    rowInColIndexes = new ArrayList<String>();
		    rowInColIndexes.add(values.size() + "|" + row);
		    colIndexes.set(column, rowInColIndexes);
		    values.add(value);
		    return true;
		} else {
		    for (String line : rowInColIndexes) {
			if (line.endsWith("|" + row)) {
			    int indexOfVal = Integer.parseInt(line.split("\\|")[0]);
			    values.set(indexOfVal, value);
			    added = true;
			    return true;
			}
		    }
		    if (!added) {
			rowInColIndexes.add(values.size() + "|" + row);
			values.add(value);
			added = true;
		    }
		}
	    }
	    return false;
	}
    }
    
    /*
     * (non-Javadoc)
     * @see com.fii.homework.SparseMatrix.models.interfaces.SparseMatrix#asArray()
     */
    @Override
    public double[][] asArray() {
	double[][] result = new double[size][size];
	
	for (int col = 0; col < size; col++) {
	    result[col][col] = diag.get(col);
	    ArrayList<String> rowInColIndexes = colIndexes.get(col);
	    for (String line : rowInColIndexes) {
		String vals[] = line.split("\\|");
		int index = Integer.parseInt(vals[0]);
		int row = Integer.parseInt(vals[1]);
		result[row][col] = values.get(index);
	    }
	    
	}
	
	return result;
    }
    
    /**
     *
     */
    public boolean isSymmetric() {
	for (int i = 0; i < size; i++) {
	    for (int j = 0; j < size; j++) {
		Double elem1 = getElement(i, j);
		Double elem2 = getElement(j, i);
		if (!elem1.equals(elem2)) {
		    System.out.println(i + " " + j + " = " + getElement(i, j));
		    System.out.println(j + " " + i + " = " + getElement(j, i));
		    return false;
		}
	    }
	}
	return true;
    }
}
