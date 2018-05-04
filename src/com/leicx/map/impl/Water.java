package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月9日
 */
public class Water extends MapObject {
	/**
	 * 私有化构造方法
	 */
	private Water() {
		hardness = 1;	//硬度
		color = Color.CYAN;	//颜色
		canBeTankThrough = true;	//能被tank穿过
		canBeBulletThrough = true;	//能被子弹穿过
	}
	
	/**
	 * 内部维护一个Water的类
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
