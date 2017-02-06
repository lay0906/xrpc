package ray.github.xrpc.proxy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcImportTest {
    @Test
    public void serviceImport() throws Exception {
        ServiceImport<Hello> serviceImport = new XRpcImport<Hello>(Hello.class, null,0);
        Hello hellService = serviceImport.serviceImport();
        String text = hellService.sayHello("world");
        System.out.println(text);
    }

}