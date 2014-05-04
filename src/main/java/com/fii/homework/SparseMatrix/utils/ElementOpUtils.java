/**
 * 
 */
package com.fii.homework.SparseMatrix.utils;

/**
 * @author Robert
 */
public class ElementOpUtils {
    
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
    
    @SuppressWarnings("unchecked")
    public static <T extends Number> T sum(T e1, T e2, Class<T> type) {
	T result = ElementOpUtils.getNeutralMulElem(type);
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
	T result = ElementOpUtils.getNeutralMulElem(type);
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
	T result = ElementOpUtils.getNeutralMulElem(type);
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
	    T result = ElementOpUtils.getNeutralMulElem(type);
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
	    return ElementOpUtils.getNeutralMulElem(type);
	}
    }

}
