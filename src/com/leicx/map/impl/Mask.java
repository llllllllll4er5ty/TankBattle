package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class Mask extends MapObject {
	public Mask() {
		hardness = 0;	//硬度
		color = Color.darkGray;	//颜色
		exist = false;	//默认不存在
	}
	
	@Override
	public String toString() {
		return " ";
	}
}
