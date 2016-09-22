package com.sun.server.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class MyChildHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
		arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
		arg0.pipeline().addLast(new StringDecoder());
		arg0.pipeline().addLast(new TimeServerHandler());
	}
}
