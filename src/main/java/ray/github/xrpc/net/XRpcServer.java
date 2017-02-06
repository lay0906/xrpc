package ray.github.xrpc.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import ray.github.xrpc.net.codec.XRpcDecoder;
import ray.github.xrpc.net.handler.XRpcServerHandler;

/**
 * Created by Administrator on 2017/2/4.
 */
public class XRpcServer implements Server {

    private int port = 12345;
    private static volatile boolean isOpen;

    public XRpcServer(){
    }

    public XRpcServer(int port){
        this.port = port;
    }

    public void openServer()  {
        if(!isOpen){
            synchronized (XRpcServer.class){
                if(!isOpen){
                    final EventLoopGroup boss = new NioEventLoopGroup();
                    final EventLoopGroup work = new NioEventLoopGroup();
                    try {
                        ServerBootstrap bootstrap = new ServerBootstrap();
                        bootstrap.group(boss, work)
                                .channel(NioServerSocketChannel.class)
                                .localAddress(port)
                                .childHandler(new ChannelInitializer<SocketChannel>() {
                                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                                        socketChannel.pipeline().addLast(new XRpcDecoder());
                                        socketChannel.pipeline().addLast(new XRpcServerHandler());
                                    }
                                });
                        ChannelFuture f = bootstrap.bind().sync();
                        f.channel().closeFuture().addListener(new FutureListener<Object>() {
                            public void operationComplete(Future<Object> arg0) throws Exception {
                                boss.shutdownGracefully();
                                work.shutdownGracefully();
                            }
                        });
                        isOpen = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
