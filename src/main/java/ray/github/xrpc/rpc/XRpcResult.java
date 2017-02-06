package ray.github.xrpc.rpc;

import ray.github.xrpc.proxy.XRpcImport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcResult implements Result, Serializable {
    private static final long serialVersionUID = -2010645044879081650L;

    private Object value;
    private Throwable exception;

    public XRpcResult(){
    }

    public XRpcResult(Object result){
        this.value = result;
    }

    public XRpcResult(Object result, Throwable exception){
        this.value = result;
        this.exception = exception;
    }



    public Object getValue() {
        return value;
    }

    public boolean hasException() {
        return exception != null;
    }

    public Throwable getException() {
        return exception;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
