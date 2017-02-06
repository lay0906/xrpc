package ray.github.xrpc.buffer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by Administrator on 2017/2/4.
 */
public class EchoHandler2 extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(2);
        //can release refcnt auto
        ctx.fireChannelRead(msg);
        //cause exception
//        ReferenceCountUtil.release(msg);
    }
}
