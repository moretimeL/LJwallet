package com.liu.ljwallet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MyWallet {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 种子助记词
     */
    private String seedCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 密钥路径
     */
    private String keystorePath;

    /**
     * 地址
     */
    public String address;

    /**
     * 是否当前钱包
     */
    private boolean isCurrent;

    /**
     * 是否备份
     */
    private boolean isBackup;

    @Generated(hash = 971956825)
    public MyWallet(Long id, String seedCode, String userName, String password,
            String keystorePath, String address, boolean isCurrent,
            boolean isBackup) {
        this.id = id;
        this.seedCode = seedCode;
        this.userName = userName;
        this.password = password;
        this.keystorePath = keystorePath;
        this.address = address;
        this.isCurrent = isCurrent;
        this.isBackup = isBackup;
    }

    @Generated(hash = 301376668)
    public MyWallet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeedCode() {
        return this.seedCode;
    }

    public void setSeedCode(String seedCode) {
        this.seedCode = seedCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeystorePath() {
        return this.keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsCurrent() {
        return this.isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public boolean getIsBackup() {
        return this.isBackup;
    }

    public void setIsBackup(boolean isBackup) {
        this.isBackup = isBackup;
    }


}
