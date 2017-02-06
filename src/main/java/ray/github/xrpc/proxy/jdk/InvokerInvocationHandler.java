package ray.github.xrpc.proxy.jdk;

import ray.github.xrpc.rpc.Invocation;
import ray.github.xrpc.rpc.Invoker;
import ray.github.xrpc.rpc.XRpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/2/3.
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> invoker){
        this.invoker = invoker;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new XRpcInvocation(method, args, invoker.getInterfaces());
         return invoker.invoke(invocation).getValue();
    }
}
