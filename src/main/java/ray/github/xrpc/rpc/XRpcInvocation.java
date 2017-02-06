package ray.github.xrpc.rpc;

import ray.github.xrpc.exception.XRpcException;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcInvocation implements Invocation, Serializable {

    private static final long serialVersionUID = 3422433376458449281L;

    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;
    private Class<?> interfaces;

    public XRpcInvocation(){
    }

    public XRpcInvocation(Method method, Object[] arguments, Class<?> interfaces){
        this.methodName = method.getName();
        this.arguments = arguments == null ? new Object[0] : arguments;
        this.parameterTypes = method.getParameterTypes() == null ? new Class<?>[0] : method.getParameterTypes();
        this.interfaces = interfaces;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Class<?> getInterface() {
        return interfaces;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Class<?> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class<?> interfaces) {
        this.interfaces = interfaces;
    }
}
