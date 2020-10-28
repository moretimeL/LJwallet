package com.liu.ljwallet.util;

import android.content.Context;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

public class BTCWalletUtil {
    /**
     * 通过Wallet 获取 助记词
     * @param context
     * @return
     */
    public static List<String> generateMnemonic(Context context) {
        MnemonicCode mnemonicCode;
        List<String> wd = null;
        try {
            mnemonicCode = new MnemonicCode(context.getAssets().open("english.txt"), null);
            SecureRandom secureRandom = new SecureRandom();
            byte[] initialEntropy = new byte[16];//算法需要，必须是被4整除
            secureRandom.nextBytes(initialEntropy);
            wd = mnemonicCode.toMnemonic(initialEntropy);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MnemonicException.MnemonicLengthException e) {
            e.printStackTrace();
        }
        return wd;
    }

    //通过speed 获取钱包
    public static Wallet getFromSpeed(String seedCode){
        NetworkParameters params = TestNet3Params.get();
        DeterministicSeed seed;
        try {
            seed = new DeterministicSeed(seedCode, null, "", Utils.currentTimeSeconds() );

            Wallet restoredWallet = Wallet.fromSeed(params, seed);
            return  restoredWallet;
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
        return  null;
    }


}
