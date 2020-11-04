package com.liu.ljwallet.util;

import android.app.AlertDialog;
import android.content.Intent;
import android.provider.Settings;

import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;

public class ETHWalletUtil {

    public static final ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);
    private static final int MOD = 97;
    private static final long MAX = 999999999;
    public static WalletFile WALLET_FILE;
    public static String ADDRESS;

    public static String mnemonics() {
        // 1. 生成一组随机的助记词
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE)
                .createMnemonic(entropy, sb::append);
        return sb.toString();
    }


    public static void createWalletBySeed(String seedCode){
        List<String> list = Arrays.asList(seedCode.split("\\ "));
        byte[] seed = MnemonicCode.toSeed(list, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
        // m / 44' / 60' / 0' / 0 / 0
        DeterministicKey deterministicKey = deterministicHierarchy
                .deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
        byte[] bytes = deterministicKey.getPrivKeyBytes();
        ECKeyPair keyPair = ECKeyPair.create(bytes);
        try {
            WALLET_FILE = Wallet.createLight("", keyPair);
            String address = "0x" + WALLET_FILE.getAddress();
            ADDRESS = address;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BigDecimal getBalance(String address){
        //获取余额
        Web3j mWeb3j = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/f725a1cacbf94934be7491d925a62b37"));
        BigInteger balance = null;
        try {
            balance = mWeb3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Convert.fromWei(balance.toString(), Convert.Unit.ETHER).setScale(4, BigDecimal.ROUND_DOWN);
    }

    public static TransactionReceipt sendCoin(String toAddress, String tranNum){
        TransactionReceipt transactionReceipt = null;
        try {
            Web3j mWeb3j = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/f725a1cacbf94934be7491d925a62b37"));
            BigInteger gasPrice = mWeb3j.ethGasPrice().send().getGasPrice();
            BigInteger balance = mWeb3j.ethGetBalance(ETHWalletUtil.ADDRESS, DefaultBlockParameterName.LATEST).send().getBalance();
            BigDecimal value = new BigDecimal(tranNum);
            String to = toAddress.trim();
            BigDecimal money = Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
            BigDecimal gas = Convert.fromWei(gasPrice.toString(), Convert.Unit.ETHER);
            if (money.compareTo(gas.add(value)) < 0) {
               // TODO 余额不足
                Credentials credentials = Credentials.create(Wallet.decrypt("", ETHWalletUtil.WALLET_FILE));
                transactionReceipt = Transfer.sendFunds(
                        mWeb3j, credentials, to, value, Convert.Unit.ETHER)
                        .send();
            } else {
                Credentials credentials = Credentials.create(Wallet.decrypt("", ETHWalletUtil.WALLET_FILE));
                transactionReceipt = Transfer.sendFunds(
                        mWeb3j, credentials, to, value, Convert.Unit.ETHER)
                        .send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionReceipt;
    }
}



