package chavevalor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ChaveValorServer {
    public static ChaveValorHandler keyValueHandler;
    public static ChaveValor.Processor processor;

    public static void main(String []args){
        try{
            keyValueHandler = new ChaveValorHandler();
            processor = new ChaveValor.Processor(keyValueHandler);
            startSimpleServer(processor);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public static void startSimpleServer(ChaveValor.Processor processor){
        try{
            TServerTransport serverTransport = new TServerSocket(8080);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            System.out.println("Starting the simple server");
            server.serve();
        }catch (Exception exception){
            System.out.println("Error: " + exception.getMessage());
            System.out.println("Cause: " + exception.getCause());
        }
    }
}
