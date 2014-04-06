/**
 * 
 */

package com.fii.homework.SparseMatrix.models.interfaces;


import java.util.ArrayList;

/**
 * @author Robert
 */
public interface SparseMatrix {
    public abstract SparseMatrix mul(SparseMatrix sparseMatrix);
    
    public Double getElement(int row, int column);
    
    public boolean setElement(int row, int column, double value);
    
    public SparseMatrix BMatrix();
    
    public SparseMatrix CMatrix();
    
    @Override
    public String toString();
    
    public ArrayList<ArrayList<Integer>> getRowIndexes();
    
    public ArrayList<ArrayList<String>> getColIndexes();
    
    public ArrayList<Double> getValues();
    
    public ArrayList<Double> getDiag();
    
    public int getSize();
    
    public double[][] asArray();
}
