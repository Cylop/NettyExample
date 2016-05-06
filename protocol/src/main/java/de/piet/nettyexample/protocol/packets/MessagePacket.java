package de.piet.nettyexample.protocol.packets;

import de.piet.nettyexample.protocol.NettyPacket;
import de.piet.nettyexample.protocol.util.StringPacketUtil;
import io.netty.buffer.ByteBuf;

/**
 * Created by Peter on 11.04.2016.
 */
public class MessagePacket extends NettyPacket {
    private String message;
    public MessagePacket() { }
    public MessagePacket( String message ) {
        this.message = message;
    }
    @Override
    public void readPacket ( ByteBuf byteBuf ) {
        byte[] messageBytes = new byte[ byteBuf.readInt() ];
        byteBuf.readBytes( messageBytes );
        message = StringPacketUtil.getStringFromBytes( messageBytes );
    }
    @Override
    public void writePacket ( ByteBuf byteBuf ) {
        byte[] messageBytes = StringPacketUtil.getStringBytes( message );
        System.out.println( messageBytes.length );
        byteBuf.writeInt( messageBytes.length );
        byteBuf.writeBytes( messageBytes );
    }
    public String getMessage ( ) {
        return message;
    }
}
