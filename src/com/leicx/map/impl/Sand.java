package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月9日
 */
public class Sand extends MapObject {
	/**
	 * 私有化构造参数
	 */
	private Sand() {
		hardness = 1;	//硬度
		color = Color.ORANGE;	//颜色
	}
	
	/**
	 * 内部维护一个Sand的类
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
