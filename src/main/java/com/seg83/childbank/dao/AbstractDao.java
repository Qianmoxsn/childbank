package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;


public abstract class AbstractDao {
    /**
     * Load the JSONObject from the local storage json file or from upper JSONObject
     *
     * <p>Example: return loadJsonFile(); //Load the whole Json Object from a file</p>
     * <p>Example: return dataWrapperDao.load().getJSONObject("admin"); //from upper JSONObject</p>
     * @return a JSONObject that contains all attributes of the object
     *
     */
    abstract JSONObject load();

    abstract void save(JSONObject jsobj);


    /**
     * Set the value of an attribute
     *
     * @param attrname the name of the attribute
     * @param value    the value of the attribute, should be cast to the correct type in Java(String, int, Admin, etc.)
     *                 DO NOT pass JSONObject
     */
    abstract void setAttribute(String attrname, Object value);

    /**
     * Get the value of an attribute
     *
     * @param attrname the name of the attribute
     * @return the value of the attribute, should be cast to the correct type in Java(String, int, Admin, etc.)
     * DO NOT return JSONObject
     */
    abstract Object getAttribute(String attrname);

    /**
     * Get all attributes of the object
     *
     * @return a list of all attributes of the object, each element is a return value of 'getAttribute()'
     */
    abstract List<Object> getAllAttributes();
}
