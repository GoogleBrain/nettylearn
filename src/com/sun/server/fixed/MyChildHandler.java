package com.sun.server.fixed;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class MyChildHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		arg0.pipeline().addLast(new FixedLengthFrameDecoder(20));
		arg0.pipeline().addLast(new StringDecoder());
		arg0.pipeline().addLast(new TimeServerHandler());
	}
}
