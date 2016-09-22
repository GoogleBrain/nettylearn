package com.sun.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient {
	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket = new Socket("127.0.0.1", 8080);
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		pw.write("Good Luck....");

//		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		String result = br.readLine();
//		System.out.println("结果>>>>" + result);
//		br.close();

		pw.flush();
		pw.close();
		socket.close();
	}
}
