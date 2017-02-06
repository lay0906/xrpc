package ray.github.xrpc.proxy;

import ray.github.xrpc.invoker.XRpcClientInvoker;
import ray.github.xrpc.proxy.jdk.JdkProxyFactory;
import ray.github.xrpc.rpc.Invoker;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcImport<T> implements ServiceImport<T> {

    private Class<T> interfaces;
    private String serverIp;
    private int port;
    private String key;

    public XRpcImport(Class<T> tClass, String serverIp, int port){
        this.interfaces = tClass;
        this.serverIp = serverIp;
        this.port = port;
    }

    public T serviceImport() {
        Invoker invoker = new XRpcClientInvoker(interfaces,serverIp, port);
        return (T)JdkProxyFactory.getProxy(invoker);
    }
}
