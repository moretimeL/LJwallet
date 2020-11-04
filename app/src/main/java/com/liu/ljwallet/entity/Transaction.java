package com.liu.ljwallet.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.web3j.protocol.core.methods.response.Log;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Transaction {

    @Id(autoincrement = true)
    private Long id;
    /**
     * 交易区块hash
     */
    private String transactionHash;
    /**
     * 累计使用手续费
     */
    private String cumulativeGasUsed;
    /**
     * 转账金额
     */
    private String num;

    private String gasUsed;
    /**
     * 发送钱包地址
     */
    private String from;
    /**
     * 接受钱包地址
     */
    private String to;
    @Generated(hash = 363678825)
    public Transaction(Long id, String transactionHash, String cumulativeGasUsed,
            String num, String gasUsed, String from, String to) {
        this.id = id;
        this.transactionHash = transactionHash;
        this.cumulativeGasUsed = cumulativeGasUsed;
        this.num = num;
        this.gasUsed = gasUsed;
        this.from = from;
        this.to = to;
    }
    @Generated(hash = 750986268)
    public Transaction() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTransactionHash() {
        return this.transactionHash;
    }
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    public String getCumulativeGasUsed() {
        return this.cumulativeGasUsed;
    }
    public void setCumulativeGasUsed(String cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }
    public String getNum() {
        return this.num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getGasUsed() {
        return this.gasUsed;
    }
    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }
    public String getFrom() {
        return this.from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return this.to;
    }
    public void setTo(String to) {
        this.to = to;
    }


}
