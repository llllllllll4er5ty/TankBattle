package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class Wall extends MapObject {
	/**
	 * 私有化构造参数
	 */
	private Wall() {
		hardness = 4;	//最硬
		color = Color.WHITE;	//颜色
	}
	
	/**
	 * 内部维护一个Sand的类
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
