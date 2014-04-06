/**
 * 
 */
package com.fii.homework.SparseMatrix.utils;

import java.util.ArrayList;

/**
 * @author Robert
 */
public class VectorOpUtils {
    public static <T extends Number> ArrayList<T> subtract(ArrayList<T> v1, ArrayList<T> v2, Class<T> type) {

	int size = v1.size();

	ArrayList<T> v = new ArrayList<T>(size);

	for (int i = 0; i < size; i++) {
	    v.add(ElementOpUtils.sub(v1.get(i), v2.get(i), type));
	}
	return v;
    }

    public static <T> ArrayList<T> getClone(ArrayList<T> vector, Class<T> type) {
	ArrayList<T> clone = new ArrayList<T>();
	for (T elem : vector) {
	    clone.add(elem);
	}
	return clone;
    }

}
