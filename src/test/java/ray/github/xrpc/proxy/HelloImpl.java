package ray.github.xrpc.proxy;

/**
 * Created by Administrator on 2017/2/1.
 */
public class HelloImpl implements Hello {

    public String sayHello(String args) {
        return "hello" + args;
    }
}
