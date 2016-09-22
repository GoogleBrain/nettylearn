package com.sun.client.ban;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class MyHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		// 方式一。
		// arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));

		// 方式二。
		arg0.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
		arg0.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));

		arg0.pipeline().addLast(new StringDecoder());
		arg0.pipeline().addLast(new MyChannleHandlerAdapter());
	}
}
