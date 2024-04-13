package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public AdminDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    public Admin getAdmin() {
        JSONObject jsonData = dataWrapperDao.loadJsonFile();
        return jsonData.getObject("admin", Admin.class);
    }

    public void saveAdmin(Admin admin) {
        JSONObject jsonData = dataWrapperDao.loadJsonFile();
        jsonData.put("admin", JSON.parseObject(JSON.toJSONString(admin)));
        dataWrapperDao.saveJsonFile(jsonData);
    }
}
