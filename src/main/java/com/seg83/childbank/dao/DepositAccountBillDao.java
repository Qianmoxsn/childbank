package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.DepositAccountBill;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class DepositAccountBillDao extends AbstractArrayDao {
    private final AccountDao accountDao;
    private final StringDateConvert convert;
    long ElementCount;

    @Autowired
    public DepositAccountBillDao(AccountDao accountDao, StringDateConvert convert) {
        this.accountDao = accountDao;
        this.convert = convert;
        this.getElementCount();
    }

    @Override
    JSONArray load() {
        log.info("Request deposit account data in JSON format");
        JSONArray depositAccount = accountDao.load().getJSONArray("depositAccount");
        log.debug("Get deposit account data {}", depositAccount);
        return depositAccount;
    }

    @Override
    void getElementCount() {
        log.info("Request deposit account count");
        this.ElementCount = this.load().size();
        log.debug("Get deposit account count {}", this.ElementCount);
    }

    @Override
    DepositAccountBill getElementById(long depositAccountBillId) {
        log.info("Request DepositAccountBill with id {}", depositAccountBillId);
        List<DepositAccountBill> depositAccountBill = this.load().toList(DepositAccountBill.class);
        for (DepositAccountBill bill : depositAccountBill) {
            if (bill.getDepositAccountBillId() == depositAccountBillId) {
                log.debug("Get bill {}", bill);
                return bill;
            }
        }
        throw new RuntimeException("Invalid Id");
    }

    @Override
    void setAttribute(String attrname, Object value, long depositAccountBillId) {
        switch (attrname) {
            case "depositAccountBillAmount" -> {
                log.info("Set depositAccountBillAmount to {}", value);
                this.setDepositAccountBillAmount(depositAccountBillId, (double) value);
            }
            case "depositAccountBillRate" -> {
                log.info("Set depositAccountBillRate to {}", value);
                this.setDepositAccountBillRate(depositAccountBillId, (double) value);
            }
            case "depositAccountBillExpireDate" -> {
                log.info("Set depositAccountBillExpireDate to {}", value);
                this.setDepositAccountBillExpireDate(depositAccountBillId, (String) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    //TODO
    private void setDepositAccountBillAmount(long depositAccountBillId, double value) {

    }

    //TODO
    private void setDepositAccountBillRate(long depositAccountBillId, double value) {

    }

    //TODO
    private void setDepositAccountBillExpireDate(long depositAccountBillId, String value) {
    }


    public void createDepositAccountBill(double amount, double rate, String expireDate) {
        log.info("Create DepositAccountBill with date amount {}, rate {}, expireDate {}", amount, rate, expireDate);
        DepositAccountBill newDepositAccountBill = new DepositAccountBill(this.ElementCount + 1, amount, rate, expireDate);
        this.ElementCount++;
        log.debug("DepositAccountBill created {}", newDepositAccountBill);

        List<DepositAccountBill> depositAccountBill = this.load().toJavaList(DepositAccountBill.class);
        depositAccountBill.add(newDepositAccountBill);
        log.debug("Set DepositAccountBill Array {}", depositAccountBill);
        this.accountDao.setAttribute("depositAccount", depositAccountBill);
    }

    @Override
    public Object getAttribute(String attrname, long depositAccountBillId) {
        if (depositAccountBillId < 0 || depositAccountBillId > this.ElementCount) {
            throw new RuntimeException("Invalid Id range");
        }
        return switch (attrname) {
            case "depositAccountBillAmount" -> this.getDepositAccountBillAmount(depositAccountBillId);
            case "depositAccountBillRate" -> this.getDepositAccountBillRate(depositAccountBillId);
            case "depositAccountBillExpireDate" -> this.getDepositAccountBillExpireDate(depositAccountBillId);
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    //TODO
    private Object getDepositAccountBillAmount(long depositAccountBillId) {
        double depositAccountBillAmount = 0;
        return depositAccountBillAmount;
    }

    //TODO
    private Object getDepositAccountBillRate(long depositAccountBillId) {
        double depositAccountBillRate = 0;
        return depositAccountBillRate;
    }

    //TODO
    private Object getDepositAccountBillExpireDate(long depositAccountBillId) {
        String depositAccountBillExpireDate = "";
        return depositAccountBillExpireDate;
    }

    //TODO: Implement this method
    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
