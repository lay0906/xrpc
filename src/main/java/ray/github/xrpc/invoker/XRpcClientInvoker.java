package ray.github.xrpc.invoker;

import ray.github.xrpc.exception.XRpcException;
import ray.github.xrpc.net.Client;
import ray.github.xrpc.net.XRpcClient;
import ray.github.xrpc.net.pkg.Header;
import ray.github.xrpc.net.pkg.Request;
import ray.github.xrpc.rpc.Invocation;
import ray.github.xrpc.rpc.Invoker;
import ray.github.xrpc.rpc.Result;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcClientInvoker<T> implements Invoker<T> {

    private Class<T> interfaces;
    private Client client;
    private String ip;
    private int port;

    public XRpcClientInvoker(){
    }

    public XRpcClientInvoker(Class<T> interfaces, String ip, int port){
        this.interfaces = interfaces;
        this.ip = ip;
        this.port = port;
        client = XRpcClient.getClient(ip, port);
    }

    public Class<T> getInterfaces() {
        return interfaces;
    }

    public Result invoke(Invocation invocation) throws XRpcException {
        Request request = buildRequest(invocation);
        return client.request(request);
    }

    private Request buildRequest(Invocation invocation){
        Header header = new Header();
        header.setReq();
        header.setTwoWay();
        header.setEventNone();
        header.setSync();
        Request request = new Request();
        request.setHeader(header);
        request.setData(invocation);
        return request;
    }
}
