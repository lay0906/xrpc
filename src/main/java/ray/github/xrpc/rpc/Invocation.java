package ray.github.xrpc.rpc;

/**
 * Created by Administrator on 2017/2/3.
 */
public interface Invocation {
    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getArguments();

    Class<?> getInterface();

}
