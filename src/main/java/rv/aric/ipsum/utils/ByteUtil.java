package rv.aric.ipsum.utils;

public class ByteUtil {

    public static boolean isTrue(byte integer) {
        return integer == 1;
    }

    public static boolean byteToBoolean(byte integer) {
        return isTrue(integer);
    }

    public static byte booleanToByte(boolean bool) {
        return (byte) (bool ? 1 : 0);
    }
}
