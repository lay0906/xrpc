package ray.github.xrpc.net.pkg;

/**
 * Created by Administrator on 2017/2/3.
 */
public class Request {
    private Header header;
    private Object data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
