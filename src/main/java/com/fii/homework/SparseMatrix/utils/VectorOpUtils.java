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

    public static <T extends Number> Double getEuclidianNorm(ArrayList<T> vector, Class<T> type) {
        Double euclidianNorm = 0.0;
        T sum = ElementOpUtils.getNeutralSumElem(type);
        for (int i = 0; i < vector.size(); i++) {
            ElementOpUtils.sum(sum,ElementOpUtils.mul(vector.get(i), vector.get(i), type), type);
        }
        euclidianNorm = Math.sqrt((Double)sum);
        return euclidianNorm;
    }
    
     public static <T extends Number> Double getEuclidianNorm(T[] vector, Class<T> type) {
        Double euclidianNorm = 0.0;
        T sum = ElementOpUtils.getNeutralSumElem(type);
        for (int i = 0; i < vector.length; i++) {
           sum = ElementOpUtils.sum(sum,ElementOpUtils.mul(vector[i], vector[i], type), type);
        }
        euclidianNorm = Math.sqrt((Double)sum);
        return euclidianNorm;
    }
}
