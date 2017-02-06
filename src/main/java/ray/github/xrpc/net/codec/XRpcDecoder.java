package ray.github.xrpc.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ray.github.xrpc.net.pkg.Header;
import ray.github.xrpc.net.pkg.ReqType;
import ray.github.xrpc.net.pkg.Request;
import ray.github.xrpc.net.pkg.Response;
import ray.github.xrpc.serializer.JdkSerializer;

import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */
public class XRpcDecoder extends ByteToMessageDecoder {
    private int state = 0;
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        do{
            int readable = byteBuf.readableBytes();
            if(state == 0){
                if(readable < Header.HEADER_LENGTH)
                    break;
                if(byteBuf.getByte(0) != Header.magicHigh && byteBuf.getByte(1) != Header.magicLow){
                    channelHandlerContext.close();
                    break;
                }
                state = 1;
            }
            if(state == 1){
                int dataLength = byteBuf.getInt(12);
                if(readable < dataLength + Header.HEADER_LENGTH)
                    break;
                state = 2;
            }
            if(state == 2){
                //skip magic
                byteBuf.readChar();
                //read header
                Header header = new Header();
                header.setFlag(byteBuf.readByte());
                header.setStatus(byteBuf.readByte());
                header.setId(byteBuf.readLong());
                header.setSize(byteBuf.readInt());

                if(header.getReqType() == ReqType.REQ){
                    Request request = new Request();
                    request.setHeader(header);
                    //read body
                    byte[] datas = new byte[header.getSize()];
                    byteBuf.readBytes(datas);
                    request.setData(JdkSerializer.deserialize(datas));
                    list.add(request);
                }else if(header.getReqType() == ReqType.RES){
                    Response response = new Response();
                    response.setHeader(header);
                    byte[] datas = new byte[header.getSize()];
                    byteBuf.readBytes(datas);
                    response.setData(JdkSerializer.deserialize(datas));
                    list.add(response);
                }
                state = 0;
                byteBuf.discardReadBytes();
            }
        }while (byteBuf.isReadable());
    }
}
