package ray.github.xrpc.proxy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcImportManager {
    private static final ConcurrentMap<String, Object> importMaps = new ConcurrentHashMap<String, Object>();

    public static <T> T serviceImport(Class<T> tClass, String serverIp, int port){
        String key = serviceKey(tClass, serverIp, port);
        if(!importMaps.containsKey(importMaps)){
            ServiceImport<T> serviceImport = new XRpcImport<T>(tClass, serverIp, port);
            importMaps.putIfAbsent(key, serviceImport.serviceImport());
        }
        return (T)importMaps.get(key);
    }

    private static String serviceKey(Class<?> tClass, String serverIp, int port){
        return tClass.getName() + "#" + serverIp + "#" + port;
    }
}
