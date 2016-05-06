package de.piet.nettyexample.protocol.util;

import de.piet.nettyexample.protocol.NettyPacket;
import de.piet.nettyexample.protocol.PacketType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Created by Peter on 18.04.2016.
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode ( ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list ) throws Exception {
        int packetID = byteBuf.readInt();
        Class<? extends NettyPacket> nettyPacketClass = PacketType.getPacketByID( packetID );
        if( nettyPacketClass != null ) {
            NettyPacket nettyPacket = nettyPacketClass.newInstance();
            nettyPacket.readPacket( byteBuf );
            list.add( nettyPacket );
        } else {
            byteBuf.release( );
        }
    }
}
