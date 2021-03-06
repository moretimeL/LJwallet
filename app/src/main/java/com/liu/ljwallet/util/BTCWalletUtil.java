package com.liu.ljwallet.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.CheckpointManager;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.MultiplexingDiscovery;
import org.bitcoinj.net.discovery.PeerDiscovery;
import org.bitcoinj.net.discovery.PeerDiscoveryException;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BTCWalletUtil {


    private static final String TAG = "BlockChainService";

    public static final String USER_AGENT = "Bitcoin Wallet";

    public static final String ACTION_BROADCAST_TRANSACTION = "action_broadcast_transaction";
    public static final String ACTION_BROADCAST_TRANSACTION_HASH = "action_broadcast_transaction_hash";

    private static SPVBlockStore blockStore;
    private static PeerGroup peerGroup;
    private static BlockChain blockChain;
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

    //发送交易
    public static void send(Wallet wallet,String recipientAddress, String amount){
        NetworkParameters params = TestNet3Params.get();
        Address targetAddress  = Address.fromBase58(params, recipientAddress);
        // Do the send of 1 BTC in the background. This could throw InsufficientMoneyException.
        SPVBlockStore blockStore = null;
        try {
            blockStore = new SPVBlockStore(params, getBLockFile());
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        BlockChain chain;
        try {
            chain = new BlockChain(params, wallet,blockStore);
            PeerGroup peerGroup = new PeerGroup(params, chain);
            try {
                Wallet.SendResult result = wallet.sendCoins(peerGroup, targetAddress, Coin.parseCoin(amount));
                // Save the wallet to disk, optional if using auto saving (see below).
                //wallet.saveToFile(....);
                // Wait for the transaction to propagate across the P2P network, indicating acceptance.
                Transaction transaction = result.tx;
                return;
            } catch (InsufficientMoneyException e) {
                e.printStackTrace();
            }
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }

    }

    public static  String send(WalletAppKit walletAppKit, String recipientAddress, String amount){
        NetworkParameters params = TestNet3Params.get();
        String err = "";
        if(TextUtils.isEmpty(recipientAddress) || recipientAddress.equals("Scan recipient QR")) {
            err = "Select recipient";
            return err;
        }
        if(TextUtils.isEmpty(amount) | Double.parseDouble(amount) <= 0) {
            err = "Select valid amount";
            return err;

        }
        /*if(walletAppKit.wallet().getBalance().isLessThan(Coin.parseCoin(amount))) {
            err = "You got not enough coins";
            return err;
        }*/
        SendRequest request = SendRequest.to(Address.fromBase58(params, recipientAddress), Coin.parseCoin(amount));
        try {
            walletAppKit.wallet().completeTx(request);
            walletAppKit.wallet().commitTx(request.tx);
            walletAppKit.peerGroup().broadcastTransaction(request.tx).broadcast();
            return "";
        } catch (InsufficientMoneyException e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }



    public static File getBLockFile(){
        File file = new File("/tmp/bitcoin-blocks");
        if(!file.exists()){
            try {
                boolean newFile = file.createNewFile();
                if(newFile){
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    //通过助记词
    public static WalletAppKit getWalletKit(Context context,String seedcode){
        WalletAppKit walletAppKit = new WalletAppKit(TestNet3Params.get(), context.getCacheDir(), "aa");


        walletAppKit.setAutoSave(true);
        walletAppKit.setBlockingStartup(false);
        if(seedcode!=null && seedcode != ""){
            try {
                DeterministicSeed seed = new DeterministicSeed(seedcode, null, "",Utils.currentTimeSeconds());
                walletAppKit.restoreWalletFromSeed(seed);
            } catch (UnreadableWalletException e) {
                e.printStackTrace();
            }
        }
        walletAppKit.startAsync();
        walletAppKit.awaitRunning();
        System.out.println(walletAppKit.wallet().toString());
        return  walletAppKit;
    }

    public static void refreshWallet(Context context,Wallet wallet){
        Log.d(TAG, "createBlockChain: ");
        File blockChainFile = new File(context.getDir("blockstore", Context.MODE_PRIVATE), "blockchain");
        boolean blockChainFileExists = blockChainFile.exists();
        if (!blockChainFileExists) {
            wallet.reset();
        }

        try {
            blockStore = new SPVBlockStore(TestNet3Params.get(), blockChainFile);
            blockStore.getChainHead(); // detect corruptions as early as possible

            final long earliestKeyCreationTime = wallet.getEarliestKeyCreationTime();

            if (!blockChainFileExists && earliestKeyCreationTime > 0) {
                try {
                    final InputStream checkpointsInputStream = context.getAssets()
                            .open("checkpoints-testnet.txt");
                    CheckpointManager.checkpoint(TestNet3Params.get(), checkpointsInputStream,
                            blockStore, earliestKeyCreationTime);
                } catch (final IOException x) {
                    x.printStackTrace();
                }
            }
        } catch (final BlockStoreException x) {
            blockChainFile.delete();
            x.printStackTrace();
        }
        try {
            blockChain = new BlockChain(TestNet3Params.get(), wallet, blockStore);
        } catch (final BlockStoreException x) {
            throw new Error("blockchain cannot be created", x);
        }
        startup(context,wallet);
    }


    private static void startup(Context context,Wallet wallet) {
        PeerGroup peerGroup = new PeerGroup(TestNet3Params.get(), blockChain);
        peerGroup.setDownloadTxDependencies(0); // recursive implementation causes StackOverflowError
        peerGroup.addWallet(wallet);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            peerGroup.setUserAgent(USER_AGENT, packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        peerGroup.setMaxConnections(8);
        int connectTimeout = (int) (15 * DateUtils.SECOND_IN_MILLIS);
        peerGroup.setConnectTimeoutMillis(connectTimeout);
        int discoveryTimeout = (int) (10 * DateUtils.SECOND_IN_MILLIS);
        peerGroup.setPeerDiscoveryTimeoutMillis(discoveryTimeout);
        peerGroup.addPeerDiscovery(new PeerDiscovery() {
            private final PeerDiscovery normalPeerDiscovery = MultiplexingDiscovery
                    .forServices(TestNet3Params.get(), 0);

            @Override
            public InetSocketAddress[] getPeers(final long services, final long timeoutValue,
                                                final TimeUnit timeoutUnit) throws PeerDiscoveryException {
                return normalPeerDiscovery.getPeers(services, timeoutValue, timeoutUnit);
            }

            @Override
            public void shutdown() {
                normalPeerDiscovery.shutdown();
            }
        });
        peerGroup.startAsync();
        peerGroup.startBlockChainDownload(null);
    }


}
