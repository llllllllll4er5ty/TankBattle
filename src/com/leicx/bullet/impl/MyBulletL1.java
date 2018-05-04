package com.leicx.bullet.impl;

import com.leicx.bullet.Bullet;

/**
 *@author leicx 
 *@time 2018年1月17日
 */
public class MyBulletL1 extends Bullet {
	/**
	 * 维护一个MyBulletL1的引用
	 */
	public static MyBulletL1 myBulletL1 = null;
	/**
	 * private the constructor
	 */
	private MyBulletL1() {
	}
	/**
	 * to get instance
	 */
	public static MyBulletL1 getInstance() {
		if(myBulletL1 == null) {
			myBulletL1 = new MyBulletL1();
		}
		return myBulletL1;
	}
}
