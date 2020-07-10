package by.gradomski.apartments.util;

import org.apache.commons.codec.binary.Base64;

public class PasswordEncoder {
    public static String encode(String data){
        byte[] dataByte = data.getBytes();
        byte[] encodedBytes = Base64.encodeBase64(dataByte);
        return new String(encodedBytes);
    }

    public static String decode(String encodedData){
        byte[] encodedBytes = encodedData.getBytes();
        byte[] data = Base64.decodeBase64(encodedBytes);
        return new String(data);
    }
}
