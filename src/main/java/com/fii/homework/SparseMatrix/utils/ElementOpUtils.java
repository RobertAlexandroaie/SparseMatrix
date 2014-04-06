/**
 * 
 */
package com.fii.homework.SparseMatrix.utils;

/**
 * @author Robert
 */
public class ElementOpUtils {
    @SuppressWarnings("unchecked")
    public static <T extends Number> T sum(T e1, T e2, Class<T> type) {
	T result = MatrixBuildUtil.getNeutralMulElem(type);
	switch (type.getSimpleName()) {
	case "Byte":
	    return (T) (Byte) (byte) ((Byte) e1 + (Byte) e2);
	case "Short":
	    return (T) (Short) (short) ((Short) e1 + (Short) e2);
	case "Integer":
	    return (T) (Integer) ((Integer) e1 + (Integer) e2);
	case "Long":
	    return (T) (Long) ((Long) e1 + (Long) e2);
	case "Float":
	    return (T) (Float) ((Float) e1 + (Float) e2);
	case "Double":
	    return (T) (Double) ((Double) e1 + (Double) e2);
	default:
	    return result;
	}
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T sub(T e1, T e2, Class<T> type) {
	T result = MatrixBuildUtil.getNeutralMulElem(type);
	switch (type.getSimpleName()) {
	case "Byte":
	    return (T) (Byte) (byte) ((Byte) e1 - (Byte) e2);
	case "Short":
	    return (T) (Short) (short) ((Short) e1 - (Short) e2);
	case "Integer":
	    return (T) (Integer) ((Integer) e1 - (Integer) e2);
	case "Long":
	    return (T) (Long) ((Long) e1 - (Long) e2);
	case "Float":
	    return (T) (Float) ((Float) e1 - (Float) e2);
	case "Double":
	    return (T) (Double) ((Double) e1 - (Double) e2);
	default:
	    return result;
	}
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T mul(T e1, T e2, Class<T> type) {
	T result = MatrixBuildUtil.getNeutralMulElem(type);
	switch (type.getSimpleName()) {
	case "Byte":
	    return (T) (Byte) (byte) ((Byte) e1 * (Byte) e2);
	case "Short":
	    return (T) (Short) (short) ((Short) e1 * (Short) e2);
	case "Integer":
	    return (T) (Integer) ((Integer) e1 * (Integer) e2);
	case "Long":
	    return (T) (Long) ((Long) e1 * (Long) e2);
	case "Float":
	    return (T) (Float) ((Float) e1 * (Float) e2);
	case "Double":
	    return (T) (Double) ((Double) e1 * (Double) e2);
	default:
	    return result;
	}
    }

    @SuppressWarnings({ "unchecked" })
    public static <T extends Number> T div(T e1, T e2, Class<T> type, double eps) {
	if (Math.abs((Double) e2) > eps) {
	    T result = MatrixBuildUtil.getNeutralMulElem(type);
	    switch (type.getSimpleName()) {
	    case "Byte":
		return (T) (Byte) (byte) ((Byte) e1 / (Byte) e2);
	    case "Short":
		return (T) (Short) (short) ((Short) e1 / (Short) e2);
	    case "Integer":
		return (T) (Integer) ((Integer) e1 / (Integer) e2);
	    case "Long":
		return (T) (Long) ((Long) e1 / (Long) e2);
	    case "Float":
		return (T) (Float) ((Float) e1 / (Float) e2);
	    case "Double":
		return (T) (Double) ((Double) e1 / (Double) e2);
	    default:
		return result;
	    }
	} else {
	    return MatrixBuildUtil.getNeutralMulElem(type);
	}
    }

}
