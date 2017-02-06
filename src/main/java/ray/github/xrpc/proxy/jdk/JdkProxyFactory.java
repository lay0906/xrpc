package ray.github.xrpc.proxy.jdk;

import ray.github.xrpc.rpc.Invoker;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/2/3.
 */
public class JdkProxyFactory {
    public static  <T> T getProxy(Invoker<T> invoker){
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{invoker.getInterfaces()}, new InvokerInvocationHandler(invoker));
    }
}
