package chavevalor;

import org.apache.thrift.TException;
import java.util.HashMap;

public class ChaveValorHandler implements ChaveValor.Iface {
    private HashMap<Integer, String> keyValue = new HashMap<>();

    @Override
    public String getKV(int key) throws TException {
        if(keyValue.containsKey(key))
            return keyValue.get(key);
        else
            throw new KeyNotFound();
    }

    @Override
    public String setKV(int key, String value) throws TException {
        if(!keyValue.containsKey(key))
            keyValue.put(key, value);
        else
            return keyValue.get(key);

        return "";
    }

    @Override
    public void deleteKV(int key) throws TException {
        if(keyValue.containsKey(key))
            keyValue.remove(key);
        else
            throw new KeyNotFound();
    }
}
