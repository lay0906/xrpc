package ray.github.xrpc.demo.server;

import ray.github.xrpc.demo.HelloService;
import ray.github.xrpc.demo.HelloServiceImpl;
import ray.github.xrpc.stub.XRpcExportManager;

/**
 * Created by Administrator on 2017/2/4.
 */
public class Server {
    public static void main(String[] args) {
        XRpcExportManager.export(HelloService.class, new HelloServiceImpl());
    }
}
