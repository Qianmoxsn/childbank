package com.seg83.childbank.service;

import com.seg83.childbank.dao.AccountDao;
import com.seg83.childbank.dao.AdminDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetupService {
    private final AdminDao adminDao;
    private final AccountDao accountDao;

    @Autowired
    public SetupService(AdminDao adminDao, AccountDao accountDao) {
        this.adminDao = adminDao;
        this.accountDao = accountDao;
    }

    public int checkPass(String pass1, String pass2) {
        // Check the length of the password == 6
        if (pass1.length() != 6) {
            return -1;
        }
        // Check if two passwords are the same
        if (!pass1.equals(pass2)) {
            return -2;
        }
        return 1;
    }

    public void setPassAdmin(String pass) {
        log.info("Setting admin password");
        adminDao.setAttribute("adminPassword", pass);
    }

    public void setPassAccount(String pass) {
        log.info("Setting account password");
        accountDao.setAttribute("accountPassword", pass);
    }
}
