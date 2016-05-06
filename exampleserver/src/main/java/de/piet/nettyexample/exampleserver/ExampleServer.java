package de.piet.nettyexample.exampleserver;

import de.piet.nettyexample.protocol.bootstrap.NettyServer;

/**
 * Created by Peter on 18.04.2016.
 */
public class ExampleServer {
    public static void main( String[] args ) {
        NettyServer nettyServer = new NettyServer( 1234 );
        nettyServer.addPacketReceiver( new PacketListener() );
        nettyServer.run();
    }
}
