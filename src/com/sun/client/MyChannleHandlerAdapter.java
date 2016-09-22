package com.sun.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyChannleHandlerAdapter extends ChannelHandlerAdapter {

	private ByteBuf bb;
	public MyChannleHandlerAdapter() {
		byte[] b = "几几年啊".getBytes();
		bb = Unpooled.buffer(b.length);
		bb.writeBytes(b);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(bb);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf bb = (ByteBuf) msg;
		byte[] b = new byte[bb.readableBytes()];
		bb.readBytes(b);
		System.out.println("客户端>>>>" + new String(b, "UTF-8"));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
