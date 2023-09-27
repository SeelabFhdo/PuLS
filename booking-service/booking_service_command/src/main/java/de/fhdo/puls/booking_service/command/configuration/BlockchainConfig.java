package de.fhdo.puls.booking_service.command.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;

@Configuration
public class BlockchainConfig {

    /** Private key of a blockchain account/wallet
     * This key and the associated account are required to address and interact with smart contracts.
     * !WARNING! This approach of hardcoding private keys in the source code ist not safe! -->  Dynamic key management necessary
     */
    private static final String PRIVATE_KEY = "c46e0c22b2381c1295bd4627526a2996ba5b12aeb559a5eacc89093bd53a2969";


    /** Gas limit for one transaction
     * !INFO! Every transaction needs/consume an specify amount of gas.
     * The gas limit indicates the maximum gas consumption that the smart contract can use.
     */
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4712388L);


    /** Gas price per gas unit
     * !INFO! The gas price defines the gas value per consumed/used gas unit.
     * */
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);


    /** buildConnection() returns a Web3j-Client-Object for the connection with an Ethereum node.
     * To connect to the node, Web3j requires the JSON-RPC API endpoint.
     * !INFO! If running a local Geth, Parity, Pantheon or a Ganache-cli --> default node JSON-RPC API endpoint is http://localhost:8545
     * If running the ganache application --> default node JSON-RPC API endpoint is http://localhost:7545
     * */
    @Bean
    public Web3j buildConnection(){
        return Web3j.build(new HttpService("http://localhost:7545"));
    }


    /** Print the actually used web3 client version. */
    @Bean
    public static void printWeb3jClientVersion(Web3j web3j){
        Web3ClientVersion web3ClientVersion = null;

        try{
            web3ClientVersion = web3j.web3ClientVersion().send();
            System.out.println("Web3 client version: " + web3ClientVersion);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println(ioe.getMessage());
        }
    }


    @Bean
    public Credentials getCredentialsFromPrivateKey(){
        return Credentials.create(PRIVATE_KEY);
    }


    @Bean
    public TransactionManager getRawTransactionManager(Web3j web3j, Credentials credentials){
        return new RawTransactionManager(web3j,credentials);
    }


    @Bean
    public ContractGasProvider getContractGasProvider(){
        return new ContractGasProvider() {
            @Override
            public BigInteger getGasPrice(String s) {
                return GAS_PRICE;
            }

            @Override @Deprecated
            public BigInteger getGasPrice() {
                return GAS_PRICE;
            }

            @Override
            public BigInteger getGasLimit(String s) {
                return GAS_LIMIT;
            }

            @Override @Deprecated
            public BigInteger getGasLimit() {
                return GAS_LIMIT;
            }
        };
    }


    /*---------------------------------------------------------------------*/


    @Bean
    public String deployParkBookingContract(Web3j web3j,
                                      TransactionManager transactionManager,
                                      ContractGasProvider contractGasProvider){
        return null;
    }


    @Bean
    public void loadParkBookingContract(String contractAddress,
                                        Web3j web3j,
                                        TransactionManager transactionManager,
                                        ContractGasProvider contractGasProvider){

    }


    @Bean
    public String deployParkAndChargeBookingContract(Web3j web3j,
                                                     TransactionManager transactionManager,
                                                     ContractGasProvider contractGasProvider){
        return null;
    }


    @Bean
    public void loadParkAndChargeBookingContract(String contractAddress,
                                                 Web3j web3j,
                                                 TransactionManager transactionManager,
                                                 ContractGasProvider contractGasProvider){

    }
}
