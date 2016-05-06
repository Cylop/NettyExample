package de.piet.nettyexample.protocol.util;

import de.piet.nettyexample.protocol.bootstrap.NettyHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;


/**
 * Created by Peter on 05.05.2016.
 */
public class PipelineUtil {
    public static void initPipeline( SocketChannel socketChannel, NettyBootstrap nettyBootstrap ) {
        socketChannel.pipeline().addLast( "timeout", new ReadTimeoutHandler( 30 ) );
        socketChannel.pipeline().addLast( "splitter", new LengthFieldPrepender( 4 ) );
        socketChannel.pipeline().addLast( "prepender", new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ) );
        socketChannel.pipeline().addLast( "decoder", new PacketDecoder() );
        socketChannel.pipeline().addLast( "encoder", new PacketEncoder() );
        socketChannel.pipeline().addLast( "handler", new NettyHandler( nettyBootstrap ) );
    }
}
