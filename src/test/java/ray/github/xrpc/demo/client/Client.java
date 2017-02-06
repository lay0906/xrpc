package ray.github.xrpc.demo.client;

import ray.github.xrpc.demo.HelloService;
import ray.github.xrpc.proxy.XRpcImportManager;

/**
 * Created by Administrator on 2017/2/4.
 */
public class Client {
    public static void main(String[] args) throws NoSuchMethodException {
        HelloService helloService = XRpcImportManager.serviceImport(HelloService.class, "127.0.0.1", 12345);
        String ret = helloService.sayHello("world");
        System.out.println(ret);
    }
}
