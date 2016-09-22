package com.sun.messagepacklearn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

public class HelloWorld {
	public static void main(String[] args) throws IOException {

		List<String> src = new ArrayList<String>();
		src.add("hu");
		src.add("zai");
		src.add("bin");

		MessagePack mp = new MessagePack();
		byte[] raw = mp.write(src);

		List<String> src3 = mp.read(raw, Templates.tList(Templates.TString));
		System.out.println(src3.get(0));
		System.out.println(src3.get(1));
		System.out.println(src3.get(2));

	}
}
