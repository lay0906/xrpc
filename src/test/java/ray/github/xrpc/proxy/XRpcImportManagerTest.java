package ray.github.xrpc.proxy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcImportManagerTest {
    @Test
    public void serviceImport() throws Exception {
        Hello hello = XRpcImportManager.serviceImport(Hello.class, null, 0);
        System.out.println(hello.sayHello("world"));
    }

}