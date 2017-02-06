package ray.github.xrpc.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import ray.github.xrpc.net.Client;
import ray.github.xrpc.net.XRpcClient;
import ray.github.xrpc.net.pkg.Header;
import ray.github.xrpc.net.pkg.Request;
import ray.github.xrpc.net.pkg.Response;
import ray.github.xrpc.serializer.JdkSerializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcClientHandler extends SimpleChannelInboundHandler<Response> {

    private Channel channel;
    private Client client;

    private Map<Long, Promise<Response>> idPromiseMap = new ConcurrentHashMap<Long, Promise<Response>>();

    public XRpcClientHandler(Client client){
        this.client = client;
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        XRpcClient xRpcClient = (XRpcClient)client;
        xRpcClient.reconnect(ctx);
        this.channel = null;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        Promise<Response> promise = idPromiseMap.get(response.getHeader().getId());
        promise.setSuccess(response);
    }

    public Future<Response> handleRequest(Request request){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeByte(Header.magicHigh)
                .writeByte(Header.magicLow)
                .writeByte(request.getHeader().getFlag())
                .writeByte(request.getHeader().getStatus())
                .writeLong(request.getHeader().getId());
        byte[] data = JdkSerializer.serialize(request.getData());
        byteBuf.writeInt(data.length)
                .writeBytes(data);
        channel.writeAndFlush(byteBuf);

        Promise<Response> promise = createRequestPromise(channel, request);
        return promise;
    }

    public Promise<Response> createRequestPromise(Channel channel, final Request request){
        Promise<Response> promise = new DefaultPromise<Response>(channel.eventLoop());
        idPromiseMap.put(request.getHeader().getId(), promise);

        promise.addListener(new FutureListener<Object>(){
            public void operationComplete(Future<Object> objectFuture) throws Exception {
                idPromiseMap.remove(request.getHeader().getId());
            }
        });
        //handler timeout
        return promise;
    }
}
