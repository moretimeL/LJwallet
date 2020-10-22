package com.liu.ljwallet.util;

import android.content.Context;

import org.bitcoinj.crypto.MnemonicCode;

import java.security.SecureRandom;
import java.util.List;

public class BTCWalletUtil {
    /**
     * 通过Wallet 获取 助记词
     * @param context
     * @return
     */
    public static void generateMnemonic(Context context) throws Exception {
        MnemonicCode mnemonicCode = new MnemonicCode(context.getAssets().open("english.txt"), null);

        SecureRandom secureRandom = new SecureRandom();
        byte[] initialEntropy = new byte[16];//算法需要，必须是被4整除
        secureRandom.nextBytes(initialEntropy);
        List<String> wd = mnemonicCode.toMnemonic(initialEntropy);

    }


}
