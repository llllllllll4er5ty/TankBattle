package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class Mask extends MapObject {
	public Mask() {
		hardness = 0;	//Ӳ��
		color = Color.darkGray;	//��ɫ
		exist = false;	//Ĭ�ϲ�����
	}
	
	@Override
	public String toString() {
		return " ";
	}
}
