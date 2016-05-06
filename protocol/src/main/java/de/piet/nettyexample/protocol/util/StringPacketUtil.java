package de.piet.nettyexample.protocol.util;

import java.nio.charset.Charset;

/**
 * Created by Peter on 11.04.2016.
 */
public class StringPacketUtil {
    private static Charset charset = Charset.forName( "UTF-8" );
    public static byte[] getStringBytes( String string ) {
        return string.getBytes( charset );
    }
    public static String getStringFromBytes( byte[] bytes ) {
        return new String( bytes, charset );
    }
}
