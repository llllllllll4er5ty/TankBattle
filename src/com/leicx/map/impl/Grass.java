package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月9日
 */
public class Grass extends MapObject {
	/**
	 * 私有化构造参数
	 */
	private Grass() {
		hardness = 1;	//硬度
		color = Color.GREEN;	//颜色
		canBeTankThrough = true;	//能被tank穿过
		canBeBulletThrough = true;	//能被子弹穿过
	}
	
	/**
	 * 内部维护一个Grass的类
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
