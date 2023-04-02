package com.example.erealtorapp.ErealtorSmartContract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class Project_sol_ERealtor_Property extends Contract {
    public static final String BINARY = "6080604052600080556000600155600060025534801561001e57600080fd5b50611f5a8061002e6000396000f3fe6080604052600436106100865760003560e01c806355ecd14c1161005957806355ecd14c146101195780636aafa2ba146101395780636beb64141461014c578063d9e8843f14610182578063e5424f511461019557600080fd5b806303742de01461008b57806316f4408f146100a05780634e6873c7146100de5780634f5e442814610106575b600080fd5b61009e610099366004611915565b6101cb565b005b3480156100ac57600080fd5b506100c06100bb366004611915565b610862565b6040516100d599989796959493929190611974565b60405180910390f35b3480156100ea57600080fd5b506100f36109c7565b6040516100d59796959493929190611a56565b61009e610114366004611915565b610e4c565b34801561012557600080fd5b5061009e610134366004611b90565b610f84565b61009e610147366004611915565b6110b2565b34801561015857600080fd5b5061016c610167366004611915565b61124c565b6040516100d59a99989796959493929190611c04565b61009e610190366004611915565b6113bc565b3480156101a157600080fd5b506101b56101b0366004611915565b6117a9565b6040516100d59a99989796959493929190611c79565b6000818152600560205260409020600901548190336001600160a01b03909116036102115760405162461bcd60e51b815260040161020890611cee565b60405180910390fd5b60008281526005602052604090206004810154600390910154839161023591611d4b565b34101561027e5760405162461bcd60e51b815260206004820152601760248201527610985b185b98d9481a5cc8125b9cdd59999a58da595b9d604a1b6044820152606401610208565b600083815260056020526040902060060154839060ff1615156001146102f25760405162461bcd60e51b815260206004820152602360248201527f50726f70657274792063757272656e746c79206e6f7420617661696c61626c6560448201526217171760e91b6064820152608401610208565b336102fc57600080fd5b6000848152600560205260408120600981015460048201546003909201546001600160a01b03909116929161033091611d4b565b6040519091506001600160a01b0383169082156108fc029083906000818181858888f19350505050158015610369573d6000803e3d6000fd5b506001805490600061037a83611d64565b909155505060008681526005602081815260408084206008810180546001600160a01b0319163317905560068101805460ff191690554281850155600180546007830155825161014081019093528b8352948b9052928252929091018054918301916103e590611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461041190611d7d565b801561045e5780601f106104335761010080835404028352916020019161045e565b820191906000526020600020905b81548152906001019060200180831161044157829003601f168201915b5050505050815260200160056000898152602001908152602001600020600201805461048990611d7d565b80601f01602080910402602001604051908101604052809291908181526020018280546104b590611d7d565b80156105025780601f106104d757610100808354040283529160200191610502565b820191906000526020600020905b8154815290600101906020018083116104e557829003601f168201915b5050509183525050600088815260056020908152604080832060038101548386015260040154818501524260608501526301e1338060808501526001805460a086018190523360c08701526001600160a01b03891660e09096019590955293835260068252909120835181559083015190918201906105819082611e06565b50604082015160028201906105969082611e06565b50606082015160038201556080820151600482015560a0820151600582015560c0820151600682015560e082015160078201556101008201516008820180546001600160a01b03199081166001600160a01b0393841617909155610120909301516009909201805490931691161790556002805490600061061683611d64565b9190505550604051806101200160405280600254815260200187815260200160056000898152602001908152602001600020600101805461065690611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461068290611d7d565b80156106cf5780601f106106a4576101008083540402835291602001916106cf565b820191906000526020600020905b8154815290600101906020018083116106b257829003601f168201915b505050505081526020016005600089815260200190815260200160002060020180546106fa90611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461072690611d7d565b80156107735780601f1061074857610100808354040283529160200191610773565b820191906000526020600020905b81548152906001019060200180831161075657829003601f168201915b50505091835250506000888152600560209081526040808320600301548285015242818501526001805460608601523360808601526001600160a01b03881660a090950194909452600280548452600783529281902085518155918501519382019390935591830151908201906107ea9082611e06565b50606082015160038201906107ff9082611e06565b506080820151600482015560a0820151600582015560c0820151600682015560e08201516007820180546001600160a01b03199081166001600160a01b039384161790915561010090930151600890920180549093169116179055505050505050565b6007602052600090815260409020805460018201546002830180549293919261088a90611d7d565b80601f01602080910402602001604051908101604052809291908181526020018280546108b690611d7d565b80156109035780601f106108d857610100808354040283529160200191610903565b820191906000526020600020905b8154815290600101906020018083116108e657829003601f168201915b50505050509080600301805461091890611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461094490611d7d565b80156109915780601f1061096657610100808354040283529160200191610991565b820191906000526020600020905b81548152906001019060200180831161097457829003601f168201915b50505060048401546005850154600686015460078701546008909701549596929591945092506001600160a01b03918216911689565b6060806060806060806060600060015467ffffffffffffffff8111156109ef576109ef611aed565b604051908082528060200260200182016040528015610a18578160200160208202803683370190505b509050600060015467ffffffffffffffff811115610a3857610a38611aed565b604051908082528060200260200182016040528015610a61578160200160208202803683370190505b509050600060015467ffffffffffffffff811115610a8157610a81611aed565b604051908082528060200260200182016040528015610aaa578160200160208202803683370190505b509050600060015467ffffffffffffffff811115610aca57610aca611aed565b604051908082528060200260200182016040528015610af3578160200160208202803683370190505b509050600060015467ffffffffffffffff811115610b1357610b13611aed565b604051908082528060200260200182016040528015610b3c578160200160208202803683370190505b509050600060015467ffffffffffffffff811115610b5c57610b5c611aed565b604051908082528060200260200182016040528015610b85578160200160208202803683370190505b509050600060015467ffffffffffffffff811115610ba557610ba5611aed565b604051908082528060200260200182016040528015610bce578160200160208202803683370190505b5090506000805b610100811015610e355760008181526006602052604090205415801590610c0c575060008181526006602052604090206003015415155b8015610c28575060008181526006602052604090206004015415155b8015610c44575060008181526006602052604090206005015415155b8015610c6157506000818152600660208190526040909120015415155b8015610c7d575060008181526006602052604090206007015415155b15610e235760008181526006602052604090205489518a9084908110610ca557610ca5611ec6565b6020026020010181815250506006600082815260200190815260200160002060030154888381518110610cda57610cda611ec6565b6020026020010181815250506006600082815260200190815260200160002060040154878381518110610d0f57610d0f611ec6565b6020026020010181815250506006600082815260200190815260200160002060060154868381518110610d4457610d44611ec6565b6020026020010181815250506006600082815260200190815260200160002060070154858381518110610d7957610d79611ec6565b60209081029190910181019190915260008281526006909152604090206008015484516001600160a01b0390911690859084908110610dba57610dba611ec6565b6001600160a01b039283166020918202929092018101919091526000838152600690915260409020600901548451911690849084908110610dfd57610dfd611ec6565b6001600160a01b039092166020928302919091019091015281610e1f81611d64565b9250505b80610e2d81611d64565b915050610bd5565b50969e959d50939b50919950975095509350915050565b60008181526005602052604090206009015481906001600160a01b03163314610e875760405162461bcd60e51b815260040161020890611edc565b600082815260056020818152604080842060070154808552600692839052908420918201549190920154859391610ebd91611d4b565b9050804210610f0e5760405162461bcd60e51b815260206004820152601b60248201527f436f6e747261637420416c7265616479205465726d696e6174656400000000006044820152606401610208565b33610f1857600080fd5b60008581526005602052604080822060068101805460ff191660011790556004810154600890910154915190926001600160a01b0390921691829184156108fc0291859190818181858888f19350505050158015610f7a573d6000803e3d6000fd5b5050505050505050565b33610f8e57600080fd5b600080549080610f9d83611d64565b90915550506040805161014081018252600080548083526020808401898152848601899052606085018890526080850187905260a08501849052600160c0860181905260e08601859052610100860185905233610120870152928452600590915293909120825181559251919291908201906110199082611e06565b506040820151600282019061102e9082611e06565b50606082015160038201556080820151600482015560a0820151600582015560c082015160068201805460ff191691151591909117905560e082015160078201556101008201516008820180546001600160a01b03199081166001600160a01b03938416179091556101209093015160099092018054909316911617905550505050565b60008181526005602052604090206009015481906001600160a01b031633146110ed5760405162461bcd60e51b815260040161020890611edc565b60008281526005602081815260408084206007015480855260069283905290842091820154919092015485939161112391611d4b565b90508042116111745760405162461bcd60e51b815260206004820181905260248201527f54696d65206973206c65667420666f7220636f6e747261637420746f20656e646044820152606401610208565b3361117e57600080fd5b60008581526005602052604090206006015460ff16156111ec5760405162461bcd60e51b8152602060048201526024808201527f50726f70657274792069732063757272656e746c79206e6f7420617661696c616044820152633136329760e11b6064820152608401610208565b60008581526005602052604080822060068101805460ff19166001179055600881015460049091015491516001600160a01b0390911692839183156108fc0291849190818181858888f19350505050158015610f7a573d6000803e3d6000fd5b6005602052600090815260409020805460018201805491929161126e90611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461129a90611d7d565b80156112e75780601f106112bc576101008083540402835291602001916112e7565b820191906000526020600020905b8154815290600101906020018083116112ca57829003601f168201915b5050505050908060020180546112fc90611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461132890611d7d565b80156113755780601f1061134a57610100808354040283529160200191611375565b820191906000526020600020905b81548152906001019060200180831161135857829003601f168201915b5050506003840154600485015460058601546006870154600788015460088901546009909901549798949793965091945060ff169290916001600160a01b0391821691168a565b6000818152600560205260409020600901548190336001600160a01b03909116036113f95760405162461bcd60e51b815260040161020890611cee565b60008281526005602081905260408220015483919061141b9062278d00611d4b565b9050804210156114655760405162461bcd60e51b8152602060048201526015602482015274151a5b59481b19599d081d1bc81c185e4814995b9d605a1b6044820152606401610208565b60008481526005602052604090206003015484903410156114c25760405162461bcd60e51b815260206004820152601760248201527610985b185b98d9481a5cc8125b9cdd59999a58da595b9d604a1b6044820152606401610208565b336114cc57600080fd5b600085815260056020526040808220600981015460039091015491516001600160a01b0390911692839183156108fc0291849190818181858888f1935050505015801561151d573d6000803e3d6000fd5b5060008781526005602052604081206008810180546001600160a01b03191633179055600601805460ff19169055600280549161155983611d64565b91905055506040518061012001604052806002548152602001888152602001600560008a8152602001908152602001600020600101805461159990611d7d565b80601f01602080910402602001604051908101604052809291908181526020018280546115c590611d7d565b80156116125780601f106115e757610100808354040283529160200191611612565b820191906000526020600020905b8154815290600101906020018083116115f557829003601f168201915b50505050508152602001600560008a8152602001908152602001600020600201805461163d90611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461166990611d7d565b80156116b65780601f1061168b576101008083540402835291602001916116b6565b820191906000526020600020905b81548152906001019060200180831161169957829003601f168201915b505050918352505060208082018490524260408084019190915260008b81526005835281812060078082015460608701523360808701526009909101546001600160a01b031660a0909501949094526002805482529383528190208451815591840151600183015583015190918201906117309082611e06565b50606082015160038201906117459082611e06565b506080820151600482015560a0820151600582015560c0820151600682015560e08201516007820180546001600160a01b03199081166001600160a01b03938416179091556101009093015160089092018054909316911617905550505050505050565b600660205260009081526040902080546001820180549192916117cb90611d7d565b80601f01602080910402602001604051908101604052809291908181526020018280546117f790611d7d565b80156118445780601f1061181957610100808354040283529160200191611844565b820191906000526020600020905b81548152906001019060200180831161182757829003601f168201915b50505050509080600201805461185990611d7d565b80601f016020809104026020016040519081016040528092919081815260200182805461188590611d7d565b80156118d25780601f106118a7576101008083540402835291602001916118d2565b820191906000526020600020905b8154815290600101906020018083116118b557829003601f168201915b5050505060038301546004840154600585015460068601546007870154600888015460099098015496979496939550919390926001600160a01b0391821691168a565b60006020828403121561192757600080fd5b5035919050565b6000815180845260005b8181101561195457602081850181015186830182015201611938565b506000602082860101526020601f19601f83011685010191505092915050565b60006101208b83528a60208401528060408401526119948184018b61192e565b905082810360608401526119a8818a61192e565b6080840198909852505060a081019490945260c08401929092526001600160a01b0390811660e08401521661010090910152949350505050565b600081518084526020808501945080840160005b83811015611a12578151875295820195908201906001016119f6565b509495945050505050565b600081518084526020808501945080840160005b83811015611a125781516001600160a01b031687529582019590820190600101611a31565b60e081526000611a6960e083018a6119e2565b8281036020840152611a7b818a6119e2565b90508281036040840152611a8f81896119e2565b90508281036060840152611aa381886119e2565b90508281036080840152611ab781876119e2565b905082810360a0840152611acb8186611a1d565b905082810360c0840152611adf8185611a1d565b9a9950505050505050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f830112611b1457600080fd5b813567ffffffffffffffff80821115611b2f57611b2f611aed565b604051601f8301601f19908116603f01168101908282118183101715611b5757611b57611aed565b81604052838152866020858801011115611b7057600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060008060808587031215611ba657600080fd5b843567ffffffffffffffff80821115611bbe57600080fd5b611bca88838901611b03565b95506020870135915080821115611be057600080fd5b50611bed87828801611b03565b949794965050505060408301359260600135919050565b60006101408c8352806020840152611c1e8184018d61192e565b90508281036040840152611c32818c61192e565b606084019a909a525050608081019690965260a086019490945291151560c085015260e08401526001600160a01b0390811661010084015216610120909101529392505050565b60006101408c8352806020840152611c938184018d61192e565b90508281036040840152611ca7818c61192e565b606084019a909a525050608081019690965260a086019490945260c085019290925260e08401526001600160a01b0390811661010084015216610120909101529392505050565b60208082526027908201527f4163636573732064656e696564206f6e6c792074656e616e742063616e20616360408201526618d95cdcc81a5d60ca1b606082015260800190565b634e487b7160e01b600052601160045260246000fd5b80820180821115611d5e57611d5e611d35565b92915050565b600060018201611d7657611d76611d35565b5060010190565b600181811c90821680611d9157607f821691505b602082108103611db157634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115611e0157600081815260208120601f850160051c81016020861015611dde5750805b601f850160051c820191505b81811015611dfd57828155600101611dea565b5050505b505050565b815167ffffffffffffffff811115611e2057611e20611aed565b611e3481611e2e8454611d7d565b84611db7565b602080601f831160018114611e695760008415611e515750858301515b600019600386901b1c1916600185901b178555611dfd565b600085815260208120601f198616915b82811015611e9857888601518255948401946001909101908401611e79565b5085821015611eb65787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b600052603260045260246000fd5b60208082526028908201527f4163636573732064656e696564206f6e6c79206c616e646f72642063616e206160408201526718d8d95cdcc81a5d60c21b60608201526080019056fea2646970667358221220f309d03692c5963fa992bee93269a417ef9a1942694b5cfb398b83f4b3f7a0a764736f6c63430008130033";

    public static final String FUNC_AGREEMENTMAPPING = "AgreementMapping";

    public static final String FUNC_CONTRACTCOMPLETED = "ContractCompleted";

    public static final String FUNC_ADDPROPERTY = "addProperty";

    public static final String FUNC_AGREEMENTTERMINATED = "agreementTerminated";

    public static final String FUNC_GETALLAGREEMENTS = "getAllAgreements";

    public static final String FUNC_PAYRENT = "payRent";

    public static final String FUNC_PROPERTY_NO = "property_no";

    public static final String FUNC_PROPERTY_RENT_NO = "property_rent_no";

    public static final String FUNC_SIGNAGREEMENT = "signAgreement";

    @Deprecated
    protected Project_sol_ERealtor_Property(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Project_sol_ERealtor_Property(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Project_sol_ERealtor_Property(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Project_sol_ERealtor_Property(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>> AgreementMapping(BigInteger param0) {
        final Function function = new Function(FUNC_AGREEMENTMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>>(function,
                new Callable<Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue(), 
                                (String) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> ContractCompleted(BigInteger _index, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CONTRACTCOMPLETED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> addProperty(String _pTitle, String _pAddress, BigInteger _rent, BigInteger _advance) {
        final Function function = new Function(
                FUNC_ADDPROPERTY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_pTitle), 
                new org.web3j.abi.datatypes.Utf8String(_pAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_rent), 
                new org.web3j.abi.datatypes.generated.Uint256(_advance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> agreementTerminated(BigInteger _index, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_AGREEMENTTERMINATED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Tuple7<List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<String>>> getAllAgreements() {
        final Function function = new Function(FUNC_GETALLAGREEMENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<Tuple7<List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<String>>>(function,
                new Callable<Tuple7<List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<String>>>() {
                    @Override
                    public Tuple7<List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<String>, List<String>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()), 
                                convertToNative((List<Uint256>) results.get(4).getValue()), 
                                convertToNative((List<Address>) results.get(5).getValue()), 
                                convertToNative((List<Address>) results.get(6).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> payRent(BigInteger _index, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PAYRENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String>> property_no(BigInteger param0) {
        final Function function = new Function(FUNC_PROPERTY_NO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String>>(function,
                new Callable<Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String>>() {
                    @Override
                    public Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<BigInteger, String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue(), 
                                (String) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple9<BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger, String, String>> property_rent_no(BigInteger param0) {
        final Function function = new Function(FUNC_PROPERTY_RENT_NO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple9<BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger, String, String>>(function,
                new Callable<Tuple9<BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple9<BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (String) results.get(7).getValue(), 
                                (String) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> signAgreement(BigInteger _index, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SIGNAGREEMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    @Deprecated
    public static Project_sol_ERealtor_Property load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Project_sol_ERealtor_Property(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Project_sol_ERealtor_Property load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Project_sol_ERealtor_Property(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Project_sol_ERealtor_Property load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Project_sol_ERealtor_Property(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Project_sol_ERealtor_Property load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Project_sol_ERealtor_Property(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Project_sol_ERealtor_Property> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Project_sol_ERealtor_Property.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Project_sol_ERealtor_Property> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Project_sol_ERealtor_Property.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Project_sol_ERealtor_Property> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Project_sol_ERealtor_Property.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Project_sol_ERealtor_Property> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Project_sol_ERealtor_Property.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}