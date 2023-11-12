package rv.aric.ipsum.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteUtilTest {

    @Test
    void testIsTrue() {
        assertTrue(ByteUtil.isTrue((byte) 1));
        assertFalse(ByteUtil.isTrue((byte) 0));
        assertFalse(ByteUtil.isTrue((byte) 2));
    }

    @Test
    void testByteToBoolean() {
        assertTrue(ByteUtil.byteToBoolean((byte) 1));
        assertFalse(ByteUtil.byteToBoolean((byte) 0));
        assertFalse(ByteUtil.byteToBoolean((byte) 2));
    }

    @Test
    void testBooleanToByte() {
        byte one = 1;
        byte zero = 0;
        assertEquals(one, ByteUtil.booleanToByte(true));
        assertEquals(zero, ByteUtil.booleanToByte(false));
    }

}