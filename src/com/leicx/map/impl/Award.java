package com.leicx.map.impl;

import java.awt.Color;
import com.leicx.map.MapObject;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class Award extends MapObject {
	public Award() {
		hardness = 0;	//硬度
		color = Color.LIGHT_GRAY;	//颜色
	}
	
	@Override
	public String toString() {
		return "&";
	}
}
