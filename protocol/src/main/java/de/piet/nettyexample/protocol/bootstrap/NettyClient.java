package de.piet.nettyexample.protocol.bootstrap;

import de.piet.nettyexample.protocol.NettyPacket;
import de.piet.nettyexample.protocol.util.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 07.04.2016.
 */
public class NettyClient implements Runnable, NettyBootstrap {
    private static List<PacketReceiver> packetReceivers = Collections.synchronizedList( new ArrayList<>(  ) );
    private NettyBootstrap instance;
    private String host;
    private int port;
    private Channel channel;
    public NettyClient( String host, int port ) {
        this.instance = this;
        this.host = host;
        this.port = port;
    }
    @Override
    public void run ( ) {
        EventLoopGroup workerGroup = new NioEventLoopGroup(  );
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group( workerGroup )
                .channel( NioSocketChannel.class )
                .option( ChannelOption.SO_KEEPALIVE, true )
                .handler( new ChannelInitializer<SocketChannel>( ) {
                    @Override
                    protected void initChannel ( SocketChannel socketChannel ) throws Exception {
                        PipelineUtil.initPipeline( socketChannel, instance );
                    }
                } );
        ChannelFuture channelFuture = bootstrap.connect( host, port ).addListener( new ChannelFutureListener( ) {
            @Override
            public void operationComplete( ChannelFuture channelFuture ) throws Exception {
                if( channelFuture.isSuccess() ) {
                    System.out.println( "Successfully connected to " + host + ":" + port );
                }
            }
        } );
            this.channel = channelFuture.channel();
            channelFuture.channel().closeFuture().addListener( future -> {
                System.out.println( "Netty connection lost or refused! Reconnect..." );
                run();
            } );
            channelFuture.channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace( );
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    public void sendPacket( NettyPacket nettyPacket ) {
        if( channel != null ) {
            new Thread( ( ) -> {
                channel.writeAndFlush( nettyPacket );
            } ).start();
        }
    }

    @Override
    public void receivePacket ( NettyPacket nettyPacket ) {
        synchronized ( packetReceivers ) {
            for( PacketReceiver packetReceiver : packetReceivers ) {
                packetReceiver.receivePacket( nettyPacket );
            }
        }
    }
    @Override
    public void addPacketReceiver ( PacketReceiver packetReceiver ) {
        synchronized ( packetReceivers ) {
            packetReceivers.add( packetReceiver );
        }
    }
    @Override
    public void channelConnected ( Channel channel ) {
        synchronized ( packetReceivers ) {
            for ( PacketReceiver packetReceiver : packetReceivers ) {
                packetReceiver.channelActive( channel );
            }
        }
    }
}
