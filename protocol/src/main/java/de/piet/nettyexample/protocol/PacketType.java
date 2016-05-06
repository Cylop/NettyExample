package de.piet.nettyexample.protocol;

import de.piet.nettyexample.protocol.packets.MessagePacket;

/**
 * Created by Peter on 11.04.2016.
 */
public enum PacketType {
    MESSAGE_PACKET( 1, MessagePacket.class );
    int packetID;
    Class<? extends NettyPacket> packetClass;
    PacketType( int packetID, Class<? extends NettyPacket> packetClass ) {
        this.packetID = packetID;
        this.packetClass = packetClass;
    }

    public int getPacketID ( ) {
        return packetID;
    }
    public Class<? extends NettyPacket> getPacketClass ( ) {
        return packetClass;
    }

    public static Class<? extends NettyPacket> getPacketByID( int packetID ) {
        for ( PacketType packetType : values( ) ) {
            if( packetType.getPacketID() == packetID ) {
                return packetType.getPacketClass();
            }
        }
        return null;
    }
    public static int getPacketIDByClass( Class<? extends NettyPacket> packetClass ) {
        for ( PacketType packetType : values( ) ) {
            if( packetType.getPacketClass().equals( packetClass ) ) {
                return packetType.getPacketID();
            }
        }
        return 0;
    }
}
