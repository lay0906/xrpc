package ray.github.xrpc.net.pkg;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/2/3.
 */
public class HeaderTest {

    @Test
    public void testHeader() throws Exception {
        Header header = new Header();

        header.setAsync();
        Assert.assertEquals(header.getSync(), Sync.ASYNC);

        header.setSync();
        Assert.assertEquals(header.getSync(), Sync.SYNC);

        header.setEventNone();
        Assert.assertEquals(header.getEvent(), Event.NONE);

        header.setEventHeat();
        Assert.assertEquals(header.getEvent(), Event.HEAT);

        header.setOneWay();
        Assert.assertEquals(header.getWay(), Way.ONE);

        header.setTwoWay();
        Assert.assertEquals(header.getWay(), Way.TWO);

        header.setStatusOK();
        Assert.assertEquals(header.getStatusEnum(), Status.OK);

        header.setStatusClientTimeout();
        Assert.assertEquals(header.getStatusEnum(), Status.CLINET_TIMEOUT);

        header.setStatusFail();
        Assert.assertEquals(header.getStatusEnum(), Status.FAIL);

        header.setStatusServerTimeout();
        Assert.assertEquals(header.getStatusEnum(), Status.SERVER_TIMEOUT);
    }

}