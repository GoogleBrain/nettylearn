package com.sun.client.ban;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyChannleHandlerAdapter extends ChannelHandlerAdapter {

	private byte[] bb;
	private int counter;

	public MyChannleHandlerAdapter() {
		bb = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(bb.length);
			message.writeBytes(bb);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// ByteBuf bb = (ByteBuf) msg;
		// byte[] b = new byte[bb.readableBytes()];
		// bb.readBytes(b);
		// System.out.println("客户端>>>>" + new String(b, "UTF-8") + " ," +
		// (++counter));

		String body = (String) msg;
		System.out.println("客户端>>>>" + body + " ," + (++counter));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
