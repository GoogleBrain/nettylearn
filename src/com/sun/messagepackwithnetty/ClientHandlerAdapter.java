package com.sun.messagepackwithnetty;

import java.util.List;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandlerAdapter extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 20; i++) {
			UserInfo ui = new UserInfo();
			ui.setId(i);
			ui.setName("abcdef");
			ctx.writeAndFlush(ui);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		@SuppressWarnings("unchecked")
		List<UserInfo> s = (List<UserInfo>) msg;
		System.out.println("客户端>>>>" + s);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
