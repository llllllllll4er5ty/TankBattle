package com.leicx.bullet.impl;

import com.leicx.bullet.Bullet;

/**
 *@author leicx 
 *@time 2018年1月17日
 */
public class MyBulletL2 extends Bullet {
	/**
	 * 维护一个MyBulletL2的引用
	 */
	public static MyBulletL2 myBulletL2 = null;
	/**
	 * private the constructor
	 */
	private MyBulletL2() {
	}
	/**
	 * to get instance
	 */
	public static MyBulletL2 getInstance() {
		if(myBulletL2 == null) {
			myBulletL2 = new MyBulletL2();
		}
		return myBulletL2;
	}
}
