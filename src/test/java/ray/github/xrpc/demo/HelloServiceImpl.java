package ray.github.xrpc.demo;

/**
 * Created by Administrator on 2017/2/4.
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello(String arg) {
        System.out.println("HelloServiceImpl:" + arg);
        return "HelloServiceImpl:" + arg;
    }
}
