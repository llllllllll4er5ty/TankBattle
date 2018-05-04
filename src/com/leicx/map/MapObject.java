package com.leicx.map;

import java.awt.Color;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class MapObject{
	//硬度
	public int hardness = 0;
	//颜色
	public Color color = null;
	//是否真实存在
	public boolean exist = true;
	//能否被tank穿过，默认不可以
	public boolean canBeTankThrough = false;
	//能否被子弹穿过，默认不可以
	public boolean canBeBulletThrough = false;
	//得到物体的名称
	public String getName() {
		return this.getClass().getSimpleName();
	}
	/**
	 * 得到完整类名
	 */
	public String getCompleteName() {
		return this.getClass().getName();
	}
}
