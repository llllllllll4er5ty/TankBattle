package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月9日
 */
public class Boss extends MapObject {
	public static boolean isExist = false;
	public Boss() {
		hardness = 1;	//硬度
		color = Color.MAGENTA;	//颜色
		isExist = true;
	}
	@Override
	public String toString() {
		return "B";
	}
}
