package de.piet.nettyexample.exampleserver;

import de.piet.nettyexample.protocol.NettyPacket;
import de.piet.nettyexample.protocol.packets.MessagePacket;
import de.piet.nettyexample.protocol.util.PacketReceiver;
import io.netty.channel.Channel;

/**
 * Created by Peter on 18.04.2016.
 */
public class PacketListener implements PacketReceiver {
    public void receivePacket ( NettyPacket nettyPacket ) {
        if( nettyPacket instanceof MessagePacket ) {
            MessagePacket messagePacket = ( MessagePacket ) nettyPacket;
            System.out.println( "MessagePacket: " + messagePacket.getMessage() );
        }
    }
    @Override
    public void channelActive ( Channel channel ) {
        new Thread( ( ) -> {
            channel.writeAndFlush( new MessagePacket( "Hallo neue Verbindung!" ) );
        } ).start();
    }
}
