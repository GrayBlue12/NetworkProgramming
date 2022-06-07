package com.charting.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground {
	private ServerSocket serverSocket;
	private Socket socKet;
	private ServerGui sGui;
	private String msg;
	
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	 
	public final void setGui(ServerGui sGui) {
		this.sGui=sGui;
	}
	
	public void setting() throws IOException{
		Collections.synchronizedMap(clientsMap);
		serverSocket=new ServerSocket(80);
		while(true) {
			System.out.println("서버 대기중...");
			socKet=serverSocket.accept();
			System.out.println(socKet.getInetAddress()+"에서 접속했습니다.");
			Receiver receiver = new Receiver(socKet);
			receiver.start();
		}
	}
	public static void main(String[] arg)throws IOException{
		ServerBackground sBg = new ServerBackground();
		sBg.setting();
	}
	
	public void addClient(String nick, DataOutputStream out) throws IOException{
		sendMessage(nick+"님이 접속하셨습니다.");
		clientsMap.put(nick,out);
	}
	public void removeClient(String nick) {
		sendMessage(nick+"님이 나가셨습니다.");
		clientsMap.remove(nick);
	}
	public void sendMessage(String msg) {
		Iterator<String> it = clientsMap.keySet().iterator();
		String key ="";
		while(it.hasNext()) {
			key = it.next();
			try {
				clientsMap.get(key).writeUTF(msg);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

class Receiver extends Thread{
	private DataInputStream in;
	private DataOutputStream out;
	private String nick;
	
	public Receiver(Socket socket) throws IOException{
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		nick = in.readUTF();
		addClient(nick,out);
	}
	public void run() {
		try {
			while(in!= null) {
				msg=in.readUTF();
				sendMessage(msg);
				sGui.appendMsg(msg);
			}
		}catch(IOException e) {
			removeClient(nick);
			}
		}
	}
}