package ray.github.xrpc.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ray.github.xrpc.net.pkg.Header;
import ray.github.xrpc.net.pkg.Request;
import ray.github.xrpc.rpc.Invocation;
import ray.github.xrpc.rpc.Invoker;
import ray.github.xrpc.rpc.Result;
import ray.github.xrpc.serializer.JdkSerializer;
import ray.github.xrpc.stub.XRpcExportManager;

/**
 * Created by Administrator on 2017/2/4.
 */
public class XRpcServerHandler extends SimpleChannelInboundHandler<Request> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        Invocation inv = (Invocation)request.getData();
        Invoker<?> invoker = XRpcExportManager.getInvoker(inv.getInterface());
        if(invoker == null){
            System.out.println("no provider!");
            return;
        }
        Result result = invoker.invoke(inv);

        Header header = new Header();
        header.setSync();
        header.setRes();
        header.setTwoWay();
        header.setEventNone();
        header.setStatusOK();

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeByte(Header.magicHigh);
        byteBuf.writeByte(Header.magicLow);
        byteBuf.writeByte(header.getFlag());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(request.getHeader().getId());

        byte[] data = JdkSerializer.serialize(result);
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);

        channelHandlerContext.writeAndFlush(byteBuf);
    }
}
