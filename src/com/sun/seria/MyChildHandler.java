package com.sun.seria;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MyChildHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		arg0.pipeline().addLast(new ObjectDecoder(1024 * 1024,
				ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
		arg0.pipeline().addLast(new ObjectEncoder());
		arg0.pipeline().addLast(new SubReqServerHandler());
	}
}
