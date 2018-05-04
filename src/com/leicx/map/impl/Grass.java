package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��9��
 */
public class Grass extends MapObject {
	/**
	 * ˽�л��������
	 */
	private Grass() {
		hardness = 1;	//Ӳ��
		color = Color.GREEN;	//��ɫ
		canBeTankThrough = true;	//�ܱ�tank����
		canBeBulletThrough = true;	//�ܱ��ӵ�����
	}
	
	/**
	 * �ڲ�ά��һ��Grass����
	 */
	public static Grass grass = null;
	
	
	public static Grass getInstance() {
		if(grass == null) {
			grass = new Grass();
		}
		return grass;
	}
	
	@Override
	public String toString() {
		return "%";
	}
}
