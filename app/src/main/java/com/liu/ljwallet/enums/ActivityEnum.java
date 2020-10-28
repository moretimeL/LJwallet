package com.liu.ljwallet.enums;

public enum  ActivityEnum {
    CREATE_WALLET("CreateWalletActivity","创建钱包"),
    IMPORT_WALLET("ImportWalletActivity","导入钱包"),
    BACK_UP_SEED ("BackUpSeedActivity","备份助记词");

    ActivityEnum(String className, String title) {
        this.className = className;
        this.title = title;
    }
    private String className;
    private String title;

    public String getClassName() {
        return className;
    }

    public String getTitle() {
        return title;
    }

}
