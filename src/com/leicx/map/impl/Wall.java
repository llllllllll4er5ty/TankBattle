package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class Wall extends MapObject {
	/**
	 * ˽�л��������
	 */
	private Wall() {
		hardness = 4;	//��Ӳ
		color = Color.WHITE;	//��ɫ
	}
	
	/**
	 * �ڲ�ά��һ��Sand����
	 */
	public static Wall wall = null;
	
	
	public static Wall getInstance() {
		if(wall == null) {
			wall = new Wall();
		}
		return wall;
	}
	
	@Override
	public String toString() {
		return "#";
	}
}
