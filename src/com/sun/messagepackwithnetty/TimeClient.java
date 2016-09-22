package com.sun.messagepackwithnetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class TimeClient {

	public void conn(String host, int port) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							arg0.pipeline().addLast("frameDecoder",
									new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
							arg0.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
							arg0.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
							arg0.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
							arg0.pipeline().addLast(new ClientHandlerAdapter());
						}
					});
			ChannelFuture cf = boot.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new TimeClient().conn("127.0.0.1", 8080);

	}
}
