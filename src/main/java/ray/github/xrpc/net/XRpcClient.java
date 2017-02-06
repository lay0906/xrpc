package ray.github.xrpc.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import ray.github.xrpc.exception.XRpcException;
import ray.github.xrpc.net.codec.XRpcDecoder;
import ray.github.xrpc.net.handler.XRpcClientHandler;
import ray.github.xrpc.net.pkg.Request;
import ray.github.xrpc.net.pkg.Response;
import ray.github.xrpc.rpc.Result;
import ray.github.xrpc.rpc.XRpcResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/2/3.
 */
public class XRpcClient implements Client {

    private EventLoopGroup loopGroup;
    private String serverIp;
    private int port;
    private XRpcClientHandler handler;
    private ChannelFuture channelFuture;
    private static final Map<String,Client> clientMap = new ConcurrentHashMap<String,Client>();

    public static Client getClient(String serverIp, int port){
        Client client = clientMap.get(clientKey(serverIp, port));
        if(client == null){
            client = new XRpcClient(serverIp, port);
            client.connect();
            clientMap.put(clientKey(serverIp,port), client);
        }
        return clientMap.get(clientKey(serverIp, port));
    }

    public XRpcClient(String serverIp, int port){
        this.serverIp = serverIp;
        this.port = port;
        this.loopGroup = new NioEventLoopGroup();
        this.handler = new XRpcClientHandler(this);
    }

    private Bootstrap configureBootstrap(Bootstrap b){
        b.group(loopGroup)
                .remoteAddress(serverIp, port)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>(){
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new XRpcDecoder());
                        socketChannel.pipeline().addLast(handler);
                    }
                });
        return b;
    }

    public void connect() {
        channelFuture = configureBootstrap(new Bootstrap()).connect();
        clientMap.put(clientKey(serverIp, port), this);
    }

    public void close() {
        clientMap.remove(clientKey(serverIp, port));
        loopGroup.shutdownGracefully();
    }

    public void reconnect(final ChannelHandlerContext ctx){
        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(new Runnable() {
            public void run() {
                configureBootstrap(new Bootstrap()).connect().addListener(new FutureListener<Void>(){
                    public void operationComplete(Future<Void> future) throws Exception {
                        //log something
                        if(future.isSuccess()){
                            System.out.println("reconnect success");
                        }
                    }
                });
            }
        }, 10L, TimeUnit.SECONDS);
    }

    public Result request(Request request) {
        Future<Response> resultFuture = handler.handleRequest(request);
        try {
            Response response = resultFuture.get(5000, TimeUnit.SECONDS);
            if(response.getHeader().isOk())
                return (XRpcResult)response.getData();
            else{
                return new XRpcResult(null, new XRpcException("error"));
            }
        } catch (Exception e) {
            XRpcException exception = new XRpcException("timeout");
            return new XRpcResult(null, exception);
        }
    }

    private static String clientKey(String serverIp, int port){
        return serverIp + "#" + port;
    }
}
