package ray.github.xrpc.rpc;

/**
 * Created by Administrator on 2017/2/3.
 */
public interface Result {
    Object getValue();

    boolean hasException();

    Throwable getException();
}
