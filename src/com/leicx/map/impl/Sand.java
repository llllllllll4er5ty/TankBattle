package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��9��
 */
public class Sand extends MapObject {
	/**
	 * ˽�л��������
	 */
	private Sand() {
		hardness = 1;	//Ӳ��
		color = Color.ORANGE;	//��ɫ
	}
	
	/**
	 * �ڲ�ά��һ��Sand����
	 */
	public static Sand sand = null;
	
	
	public static Sand getInstance() {
		if(sand == null) {
			sand = new Sand();
		}
		return sand;
	}
	
	@Override
	public String toString() {
		return "~";
	}
}
