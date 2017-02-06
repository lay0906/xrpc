package ray.github.xrpc.stub;

import ray.github.xrpc.invoker.XRpcServerInvoker;
import ray.github.xrpc.net.Server;
import ray.github.xrpc.net.XRpcServer;
import ray.github.xrpc.rpc.Invoker;

/**
 * Created by Administrator on 2017/2/4.
 */
public class XRpcExport<T> implements ServiceExport<T> {

    private T ref;
    private Class<T> interfaces;

    public XRpcExport(T ref, Class<T> interfaces){
        this.ref = ref;
        this.interfaces = interfaces;
    }

    public Invoker<T> export() {
        Server server = new XRpcServer();
        server.openServer();
        return new XRpcServerInvoker<T>(ref, interfaces);
    }
}
