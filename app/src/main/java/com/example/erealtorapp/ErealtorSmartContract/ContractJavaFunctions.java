package com.example.erealtorapp.ErealtorSmartContract;

import android.app.Activity;
import android.content.Context;

import com.example.erealtorapp.ContractManagement.ViewContractSingle;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class ContractJavaFunctions {
    String Address;
    String Password;
    Project_sol_ERealtor_Property Contract;

    public Project_sol_ERealtor_Property getContract() {
        return Contract;
    }

    public void setContract(Project_sol_ERealtor_Property contract) {
        Contract = contract;
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

    public TransactionReceipt AddProperty(String CID,String Title, String Paddress, BigInteger rent, BigInteger advance) throws ExecutionException, InterruptedException {
        TransactionReceipt receipt= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            receipt = Contract.addProperty(Title,Address,rent,advance).sendAsync().get();
        }
        return receipt;
    }

    public ContractJavaFunctions(Activity context, String address, String password) throws CipherException, IOException {
        Address = address;
        Password = password;
        Contract = new RentalSmartContract().LoadContract(context, Address, Password);
    }
}
