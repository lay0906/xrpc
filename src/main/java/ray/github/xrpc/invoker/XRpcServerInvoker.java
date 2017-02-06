package ray.github.xrpc.invoker;

import ray.github.xrpc.exception.XRpcException;
import ray.github.xrpc.rpc.Invocation;
import ray.github.xrpc.rpc.Invoker;
import ray.github.xrpc.rpc.Result;
import ray.github.xrpc.rpc.XRpcResult;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcServerInvoker<T> implements Invoker<T> {

    private T ref;
    private Class<T> interfaces;

    public XRpcServerInvoker(T ref, Class<T> tClass){
        this.ref = ref;
        this.interfaces = tClass;
    }

    public Class<T> getInterfaces() {
        return interfaces;
    }

    public Result invoke(Invocation invocation) throws XRpcException {
        try {
            Method method = interfaces.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            Object o = method.invoke(ref, invocation.getArguments());
            return new XRpcResult(o);
        } catch (Exception e) {
            e.printStackTrace();
            return new XRpcResult(null, new XRpcException(e.getMessage()));
        }
    }
}
