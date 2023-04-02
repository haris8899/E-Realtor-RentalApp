package com.example.erealtorapp.WalletManagement;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.erealtorapp.BuildConfig;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.concurrent.ExecutionException;

public class Wallet {
    String Address;
    String Password;
    String Filename;
    String Filepath;

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public String CreateNewWallet(Activity context, String Password)
    {
        String testpath  = context.getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        File file = new File(testpath+"/wallet");
        if(!file.exists()){
            file.mkdir();
        }
        setupBouncyCastle();
        try {
            String fileName = WalletUtils.generateNewWalletFile(
                    Password,
                    new File(file.getAbsolutePath()));
            SharedPreferences sharedpreference =
                    context.getSharedPreferences("Walletfiles", 0);
            SharedPreferences.Editor editor = sharedpreference.edit();
            editor.commit();
            editor.putString("A3",fileName);
            editor.putString("A4",file.getAbsolutePath());
            editor.commit();
            return  fileName;
        } catch (CipherException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            e.printStackTrace();
            //Log.d("Tag",e.getMessage());
            return e.getMessage();
        }
    }

    public Credentials LoadWalletCrediantials(Activity context, String Password) throws CipherException, IOException {
        if(WalletExists(context))
        {
            SharedPreferences sharedpreference =
                    context.getSharedPreferences("Walletfiles", 0);
            String name = sharedpreference.getString("A3","");
            String Path = sharedpreference.getString("A4","");
            String filepath = Path +"/"+name;
            Credentials credentials = WalletUtils.loadCredentials(
                    Password,
                    filepath);
            //Log.d("Tag","Balance: "+credentials.getEcKeyPair());
            Log.d("Tag","Cred: "+credentials.getAddress());
            return credentials;
        }
        else
        {
            Toast.makeText(context,"Wallet does not Exist",Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public BigDecimal getbalance(Credentials credentials) throws ExecutionException, InterruptedException {
        Web3j web3j = Web3j.build(new HttpService(BuildConfig.WEB3_API));
        EthGetBalance balance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger wei = balance.getBalance();

        return Convert.fromWei(wei.toString(),Convert.Unit.ETHER);
    }

    public void sendEth(Credentials credentials,String Address,double amount) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(BuildConfig.WEB3_API));
        TransactionReceipt transactionReceipt = Transfer.sendFunds(
                web3j, credentials, Address,
                BigDecimal.valueOf(amount), Convert.Unit.ETHER).sendAsync().get();
    }

    public boolean WalletExists(Activity context)
    {
        SharedPreferences sharedpreference =
                context.getSharedPreferences("Walletfiles", 0);
        String name = sharedpreference.getString("A3","");
        String Path = sharedpreference.getString("A4","");
        File file = new File(Path +"/"+name);
        if(file.isFile())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFilepath() {
        return Filepath;
    }

    public void setFilepath(String filepath) {
        Filepath = filepath;
    }

    public Wallet()
    {

    }
    public Wallet(String address, String password, String filename, String filepath) {
        Address = address;
        Password = password;
        Filename = filename;
        Filepath = filepath;
    }
}
