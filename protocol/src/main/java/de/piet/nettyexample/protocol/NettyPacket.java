package de.piet.nettyexample.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Created by Peter on 07.04.2016.
 */
public abstract class NettyPacket {
    public abstract void readPacket( ByteBuf byteBuf );
    public abstract void writePacket( ByteBuf byteBuf );
}
