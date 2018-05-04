package com.leicx.bullet.impl;

import com.leicx.bullet.Bullet;

/**
 *@author leicx 
 *@time 2018年1月17日
 */
public class AntiBulletL1 extends Bullet {
	/**
	 * 维护一个AntiBulletL1的引用
	 */
	public static AntiBulletL1 antiBulletL1 = null;
	/**
	 * private the constructor
	 */
	private AntiBulletL1() {
		this.flag = false;
	}
	/**
	 * to get instance
	 */
	public static AntiBulletL1 getInstance() {
		if(antiBulletL1 == null) {
			antiBulletL1 = new AntiBulletL1();
		}
		return antiBulletL1;
	}
}
