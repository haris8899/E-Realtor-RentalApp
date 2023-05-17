package com.example.erealtorapp.ContractManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erealtorapp.AddManagement.MyAddAdapter;
import com.example.erealtorapp.AddManagement.ViewSingleAd;
import com.example.erealtorapp.ErealtorSmartContract.ContractJavaFunctions;
import com.example.erealtorapp.ErealtorSmartContract.Project_sol_ERealtor_Property;
import com.example.erealtorapp.ErealtorSmartContract.RentalSmartContract;
import com.example.erealtorapp.R;
import com.example.erealtorapp.WalletManagement.ViewWalletActivity;
import com.example.erealtorapp.WalletManagement.Wallet;
import com.example.erealtorapp.databinding.ActivityViewContractBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.abi.datatypes.Int;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.Tuple;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ViewContractSingle extends AppCompatActivity {

    ActivityViewContractBinding bind;
    FirebaseAuth auth;
    ChatAdapter adapter;
    String CID;
    String ContractAddress;
    Credentials credentials;
    ContractJavaFunctions ContractFunc;
    String Password;
    Project_sol_ERealtor_Property contract;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    FirebaseDatabase database;
    DatabaseReference myref;
    ArrayList<MessagesClass> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewContractBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        Password = "";
        Intent intent = getIntent();
        CID = intent.getStringExtra("A1");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Data is Loading");
        dialog.setTitle("Loading");
        messageList = new ArrayList<MessagesClass>();
        adapter = new ChatAdapter(messageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bind.messageRecyclerView.setLayoutManager(layoutManager);
        bind.messageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bind.messageRecyclerView.setAdapter(adapter);
        bind.messageRecyclerView.setVisibility(View.GONE);
        bind.PayrentButton.setVisibility(View.GONE);
        bind.l3.setVisibility(View.GONE);



        bind.ShowHidebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bind.ShowHidebtn.getText().toString().equals("Show Chat"))
                {
                    bind.l1.setVisibility(View.GONE);
                    bind.messageRecyclerView.setVisibility(View.VISIBLE);
                    bind.l3.setVisibility(View.VISIBLE);
                    bind.ShowHidebtn.setText("Hide Chat");
                }
                else{
                    bind.l1.setVisibility(View.VISIBLE);
                    bind.messageRecyclerView.setVisibility(View.GONE);
                    bind.l3.setVisibility(View.GONE);
                    bind.ShowHidebtn.setText("Show Chat");
                }
            }
        });
        dialog.show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Landlordid = snapshot.child("Contracts").child(CID)
                        .child("landlordID").getValue().toString();
                String Landlordname = snapshot.child("Users").child(Landlordid)
                        .child("username").getValue().toString();
                String Tenantid = snapshot.child("Contracts").child(CID)
                        .child("tenantID").getValue().toString();
                String Tenantname = snapshot.child("Users").child(Tenantid)
                        .child("username").getValue().toString();
                String Tenantemail = snapshot.child("Users").child(Tenantid)
                        .child("email").getValue().toString();
                String rent = snapshot.child("Contracts").child(CID)
                        .child("rentAmount").getValue().toString();
                String PropertyID = snapshot.child("Contracts").child(CID).
                        child("propertyID").getValue().toString();
                String Status = snapshot.child("Contracts").child(CID)
                        .child("status").getValue().toString();
                String propertyid = snapshot.child("Contracts").child(CID)
                        .child("propertyID").getValue().toString();
                String PropertyName = snapshot.child("Properties").child(propertyid)
                        .child("title").getValue().toString();
                bind.PropertyNameText.setText(PropertyName);
                bind.LandlordNameText.setText(Landlordname);
                bind.TenantNameText.setText(Tenantname);
                bind.StatusText.setText(Status);
                ContractAddress ="";
                if(Status.equals("Request"))
                {
                    bind.TransactionHistoryBtn.setEnabled(false);
                }
                else
                {
                    //ContractFunc = new ContractJavaFunctions(ViewContractSingle.this,)
                    bind.TransactionHistoryBtn.setEnabled(true);
                }
                if(snapshot.child("Contracts").child(CID)
                        .child("ContractAddress").getValue()!=null)
                {
                    ContractAddress = snapshot.child("Contracts").child(CID)
                            .child("ContractAddress").getValue().toString();
                }

                if(Status.equals("Negotiating"))
                {
                    Toast.makeText(ViewContractSingle.this,
                            "Please make liberal use of Chat to negotiate rent aggreement",Toast.LENGTH_SHORT).show();
                    ContractAddress = snapshot.child("Contracts").child(CID)
                            .child("ContractAddress").getValue().toString();
                    bind.messageRecyclerView.setVisibility(View.VISIBLE);
                    bind.l3.setVisibility(View.VISIBLE);
                    bind.ShowHidebtn.setText("Hide Chat");
                    //contract = new RentalSmartContract().LoadContract(ViewContractSingle.this,ContractAddress,);
                }
                if(!rent.equals("Not Set"))
                {
                    bind.RentText.setText(rent);
                    bind.Rentedit.setText(rent);
                }
                bind.PropertyNameText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewContractSingle.this, ViewSingleAd.class);
                        intent.putExtra("A1",propertyid);
                        startActivity(intent);
                        //finish();
                    }
                });
                bind.AcceptContractButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Status.equals("Request") && auth.getCurrentUser().getUid().equals(Landlordid))
                        {
                            DeployAlertDialog();
                        }
                        if(Status.equals("Negotiating") && auth.getCurrentUser().getUid().equals(Landlordid))
                        {
                            //AddPropertyAlertDialog();
                            if(bind.Rentedit.getText().toString().equals("") ||bind.Rentedit.getText().toString().equals("Not Set"))
                            {
                                Toast.makeText(ViewContractSingle.this,
                                        "Please Set Rent Amount",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                myref.child("Contracts").child(CID)
                                        .child("rentAmount").setValue(bind.Rentedit.getText().toString());
                                myref.child("Contracts").child(CID)
                                        .child("status").setValue("Accepted");
                                String rent = bind.Rentedit.getText().toString();
                                BigDecimal brent = Convert.toWei(rent, Convert.Unit.ETHER);
                                Log.d("Tag",brent.toString());
                                AddPropertyAlertDialog(CID,ContractAddress,"1",PropertyID,
                                        brent.toBigInteger(), brent.toBigInteger());

                                String message = "Contract Has been Accepted";
                                SendMessage("System",message);
                            }
                        }
                        if(Status.equals("Accepted") && auth.getCurrentUser().getUid().equals(Tenantid))
                        {
                            String rent = bind.Rentedit.getText().toString();
                            BigDecimal brent = Convert.toWei(rent, Convert.Unit.ETHER);
                            SignAgreementAlertDialog(ContractAddress,brent.toBigInteger().multiply(BigInteger.valueOf(2)));
                            myref.child("Contracts").child(CID)
                                    .child("status").setValue("Active");
                            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                            Log.d("Tag",date);
                            String message = "Contract Has been Accepted by Tenant";
                            SendMessage("System",message);
                        }
                    }
                });
                bind.RejectContractButtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Status.equals("Request") && auth.getCurrentUser().getUid().equals(Landlordid))
                        {
                            myref.child("Contracts").child(CID).removeValue();
                        } else if(Status.equals("Accepted") && auth.getCurrentUser().getUid().equals(Tenantid))
                        {
                            myref.child("Contracts").child(CID)
                                    .child("status").setValue("Request");
                            String message = "Contract Has been Rejected by Tenant";
                            SendMessage("System",message);
                        }
                    }
                });
                bind.LandlordLayout.setVisibility(View.GONE);
                bind.l2.setVisibility(View.GONE);
                if(Tenantid.equals(auth.getCurrentUser().getUid()) && Status.equals("Active"))
                {
                    bind.PayrentButton.setVisibility(View.VISIBLE);
                    bind.PayrentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String rent = bind.Rentedit.getText().toString();
                            BigDecimal brent = Convert.toWei(rent, Convert.Unit.ETHER);
                            PayRentAlertDialog(ContractAddress,brent.toBigInteger());
                        }
                    });
                }
                if(Landlordid.equals(auth.getCurrentUser().getUid()) && Status.equals("Active"))
                {
                    bind.PayrentButton.setVisibility(View.VISIBLE);
                    bind.PayrentButton.setText("Terminate Contract");
                    BigDecimal brent = Convert.toWei(rent, Convert.Unit.ETHER);
                    bind.PayrentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TerminateAlertDialog(myref,Tenantemail,ContractAddress,brent.toBigInteger());
                        }
                    });
                }
                if(Landlordid.equals(auth.getCurrentUser().getUid()))
                {
                    bind.TenantLayout.setVisibility(View.GONE);
                    bind.LandlordLayout.setVisibility(View.VISIBLE);
                    if(Status.equals("Accepted") || Status.equals("Active"))
                    {
                        bind.TenantLayout.setVisibility(View.VISIBLE);
                        bind.LandlordLayout.setVisibility(View.GONE);
                        bind.l2.setVisibility(View.GONE);
                    } else {
                        bind.l2.setVisibility(View.VISIBLE);
                    }
                }
                else if (Tenantid.equals(auth.getCurrentUser().getUid()) && Status.equals("Accepted"))
                {
                    bind.l2.setVisibility(View.VISIBLE);
                    if(Status.equals("Accepted"))
                    {
                        bind.l2.setVisibility(View.VISIBLE);
                    } else {
                        bind.l2.setVisibility(View.GONE);
                    }
                }
                dialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference myref2 = database.getReference("Contracts").child(CID).child("messages").getRef();
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren())
                {
                    String Sender = postsnapshot.child("sender").getValue().toString();
                    String message = postsnapshot.child("message").getValue().toString();
                    messageList.add(new MessagesClass(Sender,message));
                    adapter.notifyDataSetChanged();
                    bind.messageRecyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
                bind.SendMessageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = bind.EnterMessageText.getText().toString();
                        SendMessage(auth.getCurrentUser().getUid(),message);
                        bind.EnterMessageText.setText("");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bind.TransactionHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContractSingle.this,TransactionHistoryActivity.class);
                intent.putExtra("A1",CID);
                startActivity(intent);
            }
        });

    }
    public void SendMessage(String Sender, String Message)
    {
        MessagesClass newmessage =
                new MessagesClass(Sender,Message);
        int index = bind.messageRecyclerView.getAdapter().getItemCount();
        myref.child("Contracts").child(CID).child("messages")
                .child(Integer.toString(index)).setValue(newmessage);
    }

    public void viewContractProperty(String Password)
    {
        String array = null;
        try {
            contract = new RentalSmartContract().LoadContract(ViewContractSingle.this,ContractAddress,Password);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                array = contract.property_no(BigInteger.valueOf(1)).sendAsync().get().toString();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Tag",array);
    }

    public void DeployAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String address = new RentalSmartContract().deployContract(ViewContractSingle.this,input.getText().toString());
                    myref.child("Contracts").child(CID).child("ContractAddress").setValue(address);
                    Log.d("Tag","Contract Address: "+address);
                    myref.child("Contracts").child(CID)
                            .child("status").setValue("Negotiating");
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(ViewContractSingle.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
    public void AddPropertyAlertDialog(String CID,String finalContractAddress,
                                       String Title, String Address, BigInteger rent, BigInteger advance)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Password = input.getText().toString();
                    try {
                        contract = new RentalSmartContract().LoadContract(ViewContractSingle.this, finalContractAddress, Password);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            TransactionReceipt receipt = contract.addProperty(Title,Address,rent,advance).sendAsync().get();
                            Log.d("Tag", "Hash: "+receipt.getTransactionHash());
                            Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String> block = contract.property_no(BigInteger.valueOf(1)).sendAsync().get();
                            BigInteger timestamp = block.component5();
                            StoreTransactionHistory(CID,"Property Added to Blockchain",receipt.getTransactionHash(), timestamp.toString());
                        }
                    } catch (CipherException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(ViewContractSingle.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag","Test: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
    public void SignAgreementAlertDialog(String finalContractAddress, BigInteger Amount)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Password = input.getText().toString();
                try {
                    //Log.d("Tag","Final: "+finalContractAddress);
                    contract = new RentalSmartContract().LoadContract(ViewContractSingle.this, finalContractAddress, Password);
                    //Log.d("Tag","Address: "+contract.getContractAddress());
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        TransactionReceipt receipt= contract.signAgreement(new BigInteger("1"),Amount).sendAsync().get();
                        Log.d("Tag","Hash: "+receipt.getTransactionHash());
                        Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String> block = contract.property_no(BigInteger.valueOf(1)).sendAsync().get();
                        BigInteger timestamp = block.component5();
                        StoreTransactionHistory(CID,"Agreement Signed",receipt.getTransactionHash(),timestamp.toString());
                    }
                    //Log.d("Tag",contract.getTransactionReceipt().toString());
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(ViewContractSingle.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
    public void PayRentAlertDialog(String finalContractAddress, BigInteger Amount)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Password = input.getText().toString();
                try {
                    //Log.d("Tag","Final: "+finalContractAddress);
                    contract = new RentalSmartContract().LoadContract(ViewContractSingle.this, finalContractAddress, Password);
                    //Log.d("Tag","Address: "+contract.getContractAddress());
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        TransactionReceipt receipt= contract.payRent(new BigInteger("1"),Amount).sendAsync().get();
                        Log.d("Tag","Hash: "+receipt.getTransactionHash());
                        Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String> block = contract.property_no(BigInteger.valueOf(1)).sendAsync().get();
                        BigInteger timestamp = block.component5();
                        StoreTransactionHistory(CID,"Rent Payment",receipt.getTransactionHash(),timestamp.toString());
                    }
                    //Log.d("Tag",contract.getTransactionReceipt().toString());
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(ViewContractSingle.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
    public void TerminateAlertDialog(DatabaseReference myref,String tenantemail,String finalContractAddress, BigInteger Amount)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Password = input.getText().toString();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewContractSingle.this);
                final TextView output = new TextView(ViewContractSingle.this);
                output.setText("Are you sure you want to Terminate contract \nAll security deposit will be lost");
                builder1.setView(output);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message = "Dear Tenant\n\n" +"I hope this letter finds you well. I am writing to inform you that due to unforeseen circumstances, it has become necessary for me to regain possession of the property. This letter serves as a formal notice of eviction, in accordance with the applicable laws and regulations of Pakistan.\n" +
                                "\n" +
                                "It is important to emphasize that this decision has not been made lightly, and I understand the impact it may have on you. I would like to express my sincere regret for any inconvenience caused by this situation. Please be aware that my intention is to handle this matter as smoothly and fairly as possible, with respect for both parties involved.\n" +
                                "\n" +
                                "In compliance with the law, you are hereby granted a period of 30 days to vacate the premises. During this time, I urge you to start making the necessary arrangements to secure alternative housing. Should you require any assistance or information regarding local resources, please do not hesitate to reach out to me, and I will be glad to help in any way I can";
                        try {
                            //Log.d("Tag","Final: "+finalContractAddress);
                            contract = new RentalSmartContract().LoadContract(ViewContractSingle.this, finalContractAddress, Password);
                            //Log.d("Tag","Address: "+contract.getContractAddress());
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                TransactionReceipt receipt= contract.agreementTerminated(new BigInteger("1"),Amount).sendAsync().get();
                                Log.d("Tag","Hash: "+receipt.getTransactionHash());
                                Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String> block = contract.property_no(BigInteger.valueOf(1)).sendAsync().get();
                                BigInteger timestamp = block.component5();
                                StoreTransactionHistory(CID,"Contract terminated",receipt.getTransactionHash(),timestamp.toString());
                            }
                            //Log.d("Tag",contract.getTransactionReceipt().toString());
                            dialog.cancel();
                        } catch (Exception e) {
                            Toast.makeText(ViewContractSingle.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.d("Tag",e.getMessage());
                            e.printStackTrace();
                        }
                        myref.child("Contracts").child(CID).removeValue();
                        SendEmail(tenantemail,"Eviction Notice",message);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });
        builder.show();
    }

    private void SendEmail(String to, String subject, String message)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
    public void StoreTransactionHistory(String CID,String TransactionMessage, String TransactionHash, String BlockHash)
    {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myref = database1.getReference("Contracts").child(CID).getRef();
        int index = adapter.getItemCount();
        myref.child("Transactions").child(String.valueOf(index)).child("Message").setValue(TransactionMessage);
        myref.child("Transactions").child(String.valueOf(index)).child("Hash").setValue(TransactionHash);
        myref.child("Transactions").child(String.valueOf(index)).child("block").setValue(BlockHash);
    }
}