package com.phei.netty.codec.protobuf;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class MyChildHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		arg0.pipeline().addLast(new ProtobufVarint32FrameDecoder());
		arg0.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
		arg0.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
		arg0.pipeline().addLast(new ProtobufEncoder());
		arg0.pipeline().addLast(new SubReqServerHandler());
	}
}
