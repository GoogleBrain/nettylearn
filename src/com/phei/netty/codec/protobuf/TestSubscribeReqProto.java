package com.phei.netty.codec.protobuf;

import java.util.ArrayList;
import java.util.List;

public class TestSubscribeReqProto {
	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}

	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws Exception {
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}

	private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("Lilinfeng");
		builder.setProductName("Netty Book");
		List<String> address = new ArrayList<String>();
		address.add("Nanjing");
		address.add("beijing");
		address.add("ShenZhen");
		builder.addAllAddress(address);
		return builder.build();
	}

	public static void main(String[] args) throws Exception {

		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("before》》》》" + req.toString());
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("After decode>>>>>" + req.toString());
		System.out.println("Assert decode>>>>>" + req2.equals(req));
	}
}
