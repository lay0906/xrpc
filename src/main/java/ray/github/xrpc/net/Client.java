package ray.github.xrpc.net;

import ray.github.xrpc.net.pkg.Request;
import ray.github.xrpc.rpc.Result;

/**
 * Created by Administrator on 2017/2/3.
 */
public interface Client {
    void connect();
    void close();
    Result request(Request request);
}
