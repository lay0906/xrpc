package ray.github.xrpc.exception;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcException extends RuntimeException {
    public XRpcException(){
        super();
    }

    public XRpcException(String msg){
        super(msg);
    }

    public XRpcException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
