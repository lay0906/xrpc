package ray.github.xrpc.rpc;

import ray.github.xrpc.exception.XRpcException;

/**
 * Created by Administrator on 2017/2/3.
 */
public interface Invoker<T> {
    Class<T> getInterfaces();

    Result invoke(Invocation invocation) throws XRpcException;
}
