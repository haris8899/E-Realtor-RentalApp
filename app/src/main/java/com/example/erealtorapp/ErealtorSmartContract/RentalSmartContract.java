package com.example.erealtorapp.ErealtorSmartContract;

import android.app.Activity;

import com.example.erealtorapp.BuildConfig;
import com.example.erealtorapp.WalletManagement.Wallet;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;

public class RentalSmartContract {
    String web3api;
    String Address;

    public String deployContract(Activity Context, String Password) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(web3api));
        Project_sol_ERealtor_Property contract = Project_sol_ERealtor_Property.
                deploy(web3j,new Wallet().LoadWalletCrediantials(Context,Password),new DefaultGasProvider()).sendAsync().get();
        String contractaddress = contract.getContractAddress();
        return contractaddress;
    }
    public Project_sol_ERealtor_Property LoadContract(Activity Context,String ContractAddress, String Password) throws CipherException, IOException {
        Web3j web3j = Web3j.build(new HttpService(web3api));
        Project_sol_ERealtor_Property contract = Project_sol_ERealtor_Property.load(ContractAddress,web3j,
                new Wallet().LoadWalletCrediantials(Context, Password),new DefaultGasProvider());
        return contract;
    }

    public String getWeb3api() {
        return web3api;
    }

    public void setWeb3api(String web3api) {
        this.web3api = web3api;
    }

    public RentalSmartContract() {
        this.web3api = BuildConfig.WEB3_API;

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
