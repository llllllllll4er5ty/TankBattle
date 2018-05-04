package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class Empty extends MapObject {
	/**
	 * ˽�л��������
	 */
	private Empty() {
		hardness = 0;	
		color = Color.BLACK;	
	}
	
	/**
	 * �ڲ�ά��һ��Empty����
	 */
	public static Empty empty = null;
	
	
	public static Empty getInstance() {
		if(empty == null) {
			empty = new Empty();
		}
		return empty;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ";
	}
	
}
