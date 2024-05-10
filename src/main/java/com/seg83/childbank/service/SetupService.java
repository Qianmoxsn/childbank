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

    /**
     * Constructs a new SetupService object with the provided AdminDao and AccountDao.
     *
     * @param adminDao   The data access object for admin-related operations.
     * @param accountDao The data access object for account-related operations.
     */

    @Autowired
    public SetupService(AdminDao adminDao, AccountDao accountDao) {
        this.adminDao = adminDao;
        this.accountDao = accountDao;
    }

    /**
     * Checks the validity of the provided passwords.
     *
     * @param pass1 The first password to be checked.
     * @param pass2 The second password to be checked.
     * @return An integer indicating the result of the password checks:
     *         -1 if the password length is not equal to 6,
     *         -2 if the passwords do not match,
     *         1 if the passwords pass the validation checks.
     */

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

    /**
     * Sets the password for the admin account.
     *
     * @param pass The new password for the admin account.
     */

    public void setPassAdmin(String pass) {
        log.info("Setting admin password");
        adminDao.setAttribute("adminPassword", pass);
    }
    /**
     * Sets the password for the account.
     *
     * @param pass The new password for the account.
     */

    public void setPassAccount(String pass) {
        log.info("Setting account password");
        accountDao.setAttribute("accountPassword", pass);
    }
    /**
     * Checks if it is the first login of the admin.
     *
     * @return true if it is the first login, false otherwise.
     */

    public boolean checkFirstLogin() {
        log.info("Checking if first login");
        return (boolean) adminDao.getAttribute("firstLogin");
    }

    /**
     * Sets the first login status of the admin
     */

    public void setFirstLogin() {
        log.info("Setting first login");
        adminDao.setAttribute("firstLogin", false);
    }
}
