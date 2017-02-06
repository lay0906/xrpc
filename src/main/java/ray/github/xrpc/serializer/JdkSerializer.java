package ray.github.xrpc.serializer;

import java.io.*;

/**
 * Created by Administrator on 2017/2/4.
 */
public abstract class JdkSerializer {
    public static byte[] serialize(Object o){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(o);
            os.flush();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object deserialize(byte[] data){
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
