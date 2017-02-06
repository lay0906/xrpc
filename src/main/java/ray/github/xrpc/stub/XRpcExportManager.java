package ray.github.xrpc.stub;

import ray.github.xrpc.rpc.Invoker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2017/2/4.
 */
public class XRpcExportManager {
    private static final ConcurrentMap<Class<?>, Object> exportMap = new ConcurrentHashMap<Class<?>, Object>();

    public static  <T> Invoker<T> export(Class<T> interfaces, T ref){
        if(exportMap.containsKey(interfaces))
            return (Invoker<T>)exportMap.get(interfaces);
        ServiceExport<T> serviceExport = new XRpcExport<T>(ref, interfaces);
        exportMap.putIfAbsent(interfaces, serviceExport.export());
        return (Invoker<T>)exportMap.get(interfaces);
    }

    public static  <T> Invoker<T> getInvoker(Class<T> interfaces){
        return (Invoker<T>)exportMap.get(interfaces);
    }
}
