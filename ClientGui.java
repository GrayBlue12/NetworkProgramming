package com.charting.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea(40,25);
	private JTextField jtf = new JTextField(25);
	private ClientBackground cBg = new ClientBackground();
	private static String nickName;
	
	public ClientGui() {
		add(jta,BorderLayout.CENTER);
		add(jtf,BorderLayout.SOUTH);
		jtf.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(800,100,400,600);
		setTitle("클라이언트");
		
		cBg.setGui(this);
		cBg.setNickname(nickName);
		cBg.connet();
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("닉네임 : ");
		nickName = sc.nextLine();
		sc.close();
		new ClientGui();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		String msg = nickName+" : "+jtf.getText()+"\n";
		cBg.sendMessage(msg);
		jtf.setText("");
	}
	public void appendMsg(String msg) {
		jta.append(msg);
	}

}