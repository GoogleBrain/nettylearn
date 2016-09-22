package com.sun.messagepackwithnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class TimeServer {

	public void server(int port) throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap boots = new ServerBootstrap();
			boots.group(boss, worker).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							arg0.pipeline().addLast("frameDecoder",
									new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
							arg0.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
							arg0.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
							arg0.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
							arg0.pipeline().addLast(new ServerHandler());
						}
					});
			ChannelFuture cf = boots.bind(port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new TimeServer().server(8080);
	}
}
