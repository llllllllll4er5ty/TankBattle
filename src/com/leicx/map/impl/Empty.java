package com.leicx.map.impl;

import java.awt.Color;

import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class Empty extends MapObject {
	/**
	 * 私有化构造参数
	 */
	private Empty() {
		hardness = 0;	
		color = Color.BLACK;	
	}
	
	/**
	 * 内部维护一个Empty的类
	 */
	public static Empty empty = null;
	
	
	public static Empty getInstance() {
		if(empty == null) {
			empty = new Empty();
		}
		return empty;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ";
	}
	
}
