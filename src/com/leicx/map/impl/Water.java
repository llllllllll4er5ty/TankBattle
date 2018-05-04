package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��9��
 */
public class Water extends MapObject {
	/**
	 * ˽�л����췽��
	 */
	private Water() {
		hardness = 1;	//Ӳ��
		color = Color.CYAN;	//��ɫ
		canBeTankThrough = true;	//�ܱ�tank����
		canBeBulletThrough = true;	//�ܱ��ӵ�����
	}
	
	/**
	 * �ڲ�ά��һ��Water����
	 */
	public static Water water = null;
	
	
	public static Water getInstance() {
		if(water == null) {
			water = new Water();
		}
		return water;
	}
	
	@Override
	public String toString() {
		return "~";
	}
}
