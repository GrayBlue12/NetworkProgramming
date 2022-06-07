package com.charting.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground {
	private Socket Cs;
	private DataInputStream in;
	private DataOutputStream out;
	private ClientGui cGui;
	private String msg;
	private String nickName;
	
	public final void setGui(ClientGui cGui) {
		this.cGui = cGui;
	}
	
	public void connet() {
		try {
			Cs = new Socket("192.168.1.56", 80);
			System.out.println("�ý��� �����.");
			out = new DataOutputStream(Cs.getOutputStream());
			in = new DataInputStream(Cs.getInputStream());
			
			out.writeUTF(nickName);
			System.out.println("Ŭ���̾�Ʈ : �޽��� ���ۿϷ�");
			while(in!=null) {
				msg = in.readUTF();
				cGui.appendMsg(msg);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ClientBackground cBg = new ClientBackground();
		cBg.connet();
	}
	public void sendMessage(String msg2) {
		try {
			out.writeUTF(msg2);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setNickname(String nickName) {
		this.nickName = nickName;
	}
}