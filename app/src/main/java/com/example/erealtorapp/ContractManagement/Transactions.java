package com.example.erealtorapp.ContractManagement;

public class Transactions {
    String Message;
    String Hash;
    String BlockHash;

    public String getBlockHash() {
        return BlockHash;
    }

    public void setBlockHash(String blockHash) {
        BlockHash = blockHash;
    }

    public Transactions(String message, String hash, String block) {
        Message = message;
        Hash = hash;
        BlockHash = block;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }
}
