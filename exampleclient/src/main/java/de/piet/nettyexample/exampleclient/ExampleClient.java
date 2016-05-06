package de.piet.nettyexample.exampleclient;

import de.piet.nettyexample.protocol.bootstrap.NettyClient;
import de.piet.nettyexample.protocol.packets.MessagePacket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Peter on 18.04.2016.
 */
public class ExampleClient {
    private static NettyClient nettyClient;
    public static void main( String[] args ) {
        System.out.println( ExampleClient.class.getPackage().getImplementationVersion() );
        new Thread( ( ) -> {
            nettyClient = new NettyClient( "127.0.0.1", 1234 );
            nettyClient.addPacketReceiver( new PacketListener() );
            nettyClient.run();
        } ).start();
        new Thread( ( ) -> {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );
            String line;
            try {
                while( ( line = bufferedReader.readLine() ) != null ) {
                    nettyClient.sendPacket( new MessagePacket( line ) );
                }
            } catch ( IOException e ) {
                e.printStackTrace( );
            }
        } ).start();
    }
}
