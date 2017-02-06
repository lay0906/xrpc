package ray.github.xrpc.stub;

import ray.github.xrpc.rpc.Invoker;

/**
 * Created by Administrator on 2017/2/4.
 */
public interface ServiceExport<T> {
    Invoker<T> export();
}
