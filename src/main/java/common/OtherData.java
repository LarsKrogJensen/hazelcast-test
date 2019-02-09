package common;

import java.io.*;

public class OtherData implements Serializable {
//public class OtherData implements Externalizable {
    private int x = 10;
    private int y = 10;
    private int z = 10;
    private int k = 10;
    private int l = 10;
    private String xStr = "shshsh";
    private String yStr = "shshsh";
    private String zStr = "shshsh";
    private String vStr = "shshsh";
    private String bStr = "shshsh";
    private String aStr = "shshsh";
    private String rStr = "shshsh";

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getK() {
        return k;
    }

    public int getL() {
        return l;
    }

    public String getxStr() {
        return xStr;
    }

    public String getyStr() {
        return yStr;
    }

    public String getzStr() {
        return zStr;
    }

    public String getvStr() {
        return vStr;
    }

    public String getbStr() {
        return bStr;
    }

    public String getaStr() {
        return aStr;
    }

    public String getrStr() {
        return rStr;
    }
    
//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeUTF(xStr);
//        out.writeUTF(yStr);
//        out.writeUTF(zStr);
//        out.writeUTF(vStr);
//        out.writeUTF(bStr);
//        out.writeUTF(aStr);
//        out.writeUTF(rStr);
//        out.writeInt(x);
//        out.writeInt(y);
//        out.writeInt(z);
//        out.writeInt(k);
//        out.writeInt(l);
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        xStr = in.readUTF();
//        yStr = in.readUTF();
//        zStr = in.readUTF();
//        vStr = in.readUTF();
//        bStr = in.readUTF();
//        aStr = in.readUTF();
//        rStr = in.readUTF();
//        x = in.readInt();
//        y = in.readInt();
//        z = in.readInt();
//        k = in.readInt();
//        l = in.readInt();
//    }
}
