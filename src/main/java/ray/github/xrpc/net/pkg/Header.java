package ray.github.xrpc.net.pkg;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/2/3.
 */
public class Header {
    public static final byte magicHigh = -0x79;
    public static final byte magicLow = 0x79;
    public static final int HEADER_LENGTH = 16;

    private static final AtomicLong ID_GEN = new AtomicLong(0);

    private byte flag;
    private byte status;

    private long id;
    private int size;

    public Header(){
        id = ID_GEN.getAndIncrement();
    }

    public byte getFlag() {
        return flag;
    }

    public byte getStatus() {
        return status;
    }

    public Status getStatusEnum(){
        int t = status & 0x03;
        if(t == 0) return Status.OK;
        else if(t == 1) return Status.FAIL;
        else if(t == 2) return Status.SERVER_TIMEOUT;
        else if(t ==3) return Status.CLINET_TIMEOUT;
        return Status.OK;
    }

    public void setStatusOK(){
        status = (byte)(status & 0xfc);
        status = (byte)(status | 0x00);
    }

    public void setStatusFail(){
        status = (byte)(status & 0xfc);
        status = (byte)(status | 0x01);
    }

    public void setStatusServerTimeout(){
        status = (byte)(status & 0xfc);
        status = (byte)(status | 0x02);
    }

    public void setStatusClientTimeout(){
        status = (byte)(status & 0xfc);
        status = (byte)(status | 0x03);
    }

    public boolean isOk(){
        return getStatusEnum() == Status.OK;
    }

    public Way getWay(){
        int t = flag & 0x01;
        return t == 0 ? Way.ONE : Way.TWO;
    }

    public void setOneWay(){
        flag = (byte)(flag & 0xfe);
    }

    public void setTwoWay(){
        flag = (byte)(flag | 0x01);
    }

    public void setReq(){
        flag = (byte)(flag & 0xfd);
    }

    public void setRes(){
        flag = (byte)(flag | 0x02);
    }

    public void setEventNone(){
        flag = (byte)(flag & 0xfb);
    }

    public void setEventHeat(){
        flag = (byte)(flag | 0x04);
    }

    public void setSync(){
        flag = (byte)(flag & 0xf7);
    }

    public void setAsync(){
        flag = (byte)(flag | 0x08);
    }

    public ReqType getReqType(){
        int t = flag & 0x02;
        return t == 0 ? ReqType.REQ : ReqType.RES;
    }

    public Event getEvent(){
        int t = flag & 0x04;
        return t == 0 ? Event.NONE : Event.HEAT;
    }

    public Sync getSync(){
        int t = flag & 0x08;
        return t == 0 ? Sync.SYNC : Sync.ASYNC;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
