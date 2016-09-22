package com.sun.connlong;

public class PingMsg extends BaseMsg {
	public PingMsg() {
		super();
		setType(MsgType.PING);
	}
}