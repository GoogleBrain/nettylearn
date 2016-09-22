package com.sun.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class TimeServer {

	private static final String DEFAULT_URL = "/";

	public void server(final int port, final String url) throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap boots = new ServerBootstrap();
			boots.group(boss, worker).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							arg0.pipeline().addLast("http-decoder", new HttpRequestDecoder());
							arg0.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
							arg0.pipeline().addLast("http-encoder", new HttpResponseEncoder());
							arg0.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
							arg0.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
						}
					});
			ChannelFuture cf = boots.bind("127.0.0.1", port).sync();
			System.out.println("文件服务器地址:http://127.0.0.1:" + port + url);
			cf.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new TimeServer().server(8080, DEFAULT_URL);
	}
}
