package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class Award extends MapObject {
	public Award() {
		hardness = 0;	//Ӳ��
		color = Color.LIGHT_GRAY;	//��ɫ
	}
	
	@Override
	public String toString() {
		return "&";
	}
}
