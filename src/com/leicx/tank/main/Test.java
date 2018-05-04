package com.leicx.tank.main;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.leicx.map.MainMap;
import com.leicx.tank.impl.AntiTankL1;
import com.leicx.tank.impl.MyTankL1;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class Test {

	public static void main(String[] args) throws Exception{
		test2();
	}
	public static void test1() {
		final MyTankL1 myTankL1 = MyTankL1.getInstance();
		myTankL1.getTank();
		
		AntiTankL1 antiTankL1 = AntiTankL1.getInstance();
		antiTankL1.getTank();
		
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 540, 333);
		
		JPanel jPanel1 = new JPanel(new FlowLayout());
		jPanel1.add(new JButton("按钮1"));
		jPanel1.add(new JButton("按钮3"));
		jPanel1.add(new JButton("按钮4"));
		jPanel1.setBackground(Color.RED);
		jPanel1.setPreferredSize(new Dimension(200, 200));
		
		JPanel jPanel2 = new JPanel(new BorderLayout());
		jPanel2.add(new JButton("按钮2"));
		jPanel2.setBackground(Color.GREEN);
		
		frame.add(jPanel1,BorderLayout.WEST);
		frame.add(jPanel2,BorderLayout.EAST);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		antiTankL1.attack();
	}
	public static void test2() {
		MainMap.start();
		
	}
	public static void test3() throws AWTException {
		while(true) {
			Random random = new Random();
			int direction = (random.nextInt(2)+1) * (Math.random()>0.5?1:-1);
			System.out.println(direction);
		}
	}
}
