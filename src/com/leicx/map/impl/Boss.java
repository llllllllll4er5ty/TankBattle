package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��9��
 */
public class Boss extends MapObject {
	public static boolean isExist = false;
	public Boss() {
		hardness = 1;	//Ӳ��
		color = Color.MAGENTA;	//��ɫ
		isExist = true;
	}
	@Override
	public String toString() {
		return "B";
	}
}
