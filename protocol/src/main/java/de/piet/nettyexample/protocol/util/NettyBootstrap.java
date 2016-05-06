package de.piet.nettyexample.protocol.util;

import de.piet.nettyexample.protocol.NettyPacket;
import io.netty.channel.Channel;

/**
 * Created by Peter on 07.04.2016.
 */
public interface NettyBootstrap {
    void receivePacket( NettyPacket nettyPacket );
    void addPacketReceiver( PacketReceiver packetReceiver );
    void channelConnected( Channel channel );
}
