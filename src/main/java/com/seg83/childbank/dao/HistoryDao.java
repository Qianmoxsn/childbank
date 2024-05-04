package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class HistoryDao extends AbstractDao {
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public HistoryDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    @Override
    JSONObject load() {
        return null;
    }

    @Override
    void setAttribute(String attrname, Object value) {

    }

    @Override
    Object getAttribute(String attrname) {
        return null;
    }

    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
