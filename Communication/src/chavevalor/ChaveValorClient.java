package chavevalor;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.Scanner;

public class ChaveValorClient {
    public static void main(String []args){
        try{
            TTransport transport;
            transport = new TSocket("localhost", 8080);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            ChaveValor.Client client = new ChaveValor.Client(protocol);

            setKeyValue(client);
            getKV(client);
            deleteKeyValue(client);
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            System.out.println("Cause: " + ex.getCause());
        }
    }

    public static void setKeyValue(ChaveValor.Client client) throws TException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a key: ");
        int key = scanner.nextInt();
        System.out.print("Enter a value: ");
        scanner.nextLine();
        String value = scanner.nextLine();

        String result = client.setKV(key, value);

        if(result.length() == 0)
            System.out.println("Inserted");
        else
            System.out.println("The key already exists. " + result);

    }

    public static void getKV(ChaveValor.Client client) throws TException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Search for a key: ");

        try{
            int key = scanner.nextInt();
            System.out.println("Key: " + key);
            System.out.println("Value: " + client.getKV(key));
        }catch (KeyNotFound keyNotFound){
            System.out.println("Key not found");
        }
    }

    public static void deleteKeyValue(ChaveValor.Client client) throws TException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Delete a key: ");

        try{
            int key = scanner.nextInt();
            client.deleteKV(key);
            System.out.println("Deleted");
        }catch(KeyNotFound keyNotFound){
            System.out.println("Key does not exist.");
        }
    }
}
