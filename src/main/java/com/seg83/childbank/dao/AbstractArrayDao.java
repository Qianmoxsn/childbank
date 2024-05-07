package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;

import java.util.List;


public abstract class AbstractArrayDao {
    AbstractArrayDao parentDao;

    long ElementCount;

    /**
     * Load the JSONObject from the local storage json file or from upper JSONObject
     *
     * <p>Example: return historyDao.load().getJSONArray("historyActions"); //from upper JSONObject</p>
     *
     * @return a JSONArray that contains all attributes of the object
     */
    abstract JSONArray load();

    /**
     * Get the number of elements in the list
     */
    abstract void getElementCount();

    /**
     * Get the element (in list) by id
     *
     * @param Id the id of the object
     * @return the object with the id, which should be the same type as the object in list
     */
    abstract Object getElementById(long Id);

    /**
     * Set the value of an attribute
     *
     * @param attrname the name of the attribute
     * @param value    the value of the attribute, should be cast to the correct type in Java(String, int, Admin, etc.)
     *                 DO NOT pass JSONObject
     * @param Id       the id of the element
     */
    abstract void setAttribute(String attrname, Object value, long Id);

    /**
     * Get the value of an attribute
     *
     * @param attrname the name of the attribute
     * @param Id       the id of the element
     * @return the value of the attribute, should be cast to the correct type in Java(String, int, Admin, etc.)
     * DO NOT return JSONObject
     */
    abstract Object getAttribute(String attrname, long Id);

    /**
     * Get all attributes of the object
     *
     * @return a list of all attributes of the object, each element is a return value of 'getAttribute()'
     */
    abstract List<Object> getAllAttributes();
}
