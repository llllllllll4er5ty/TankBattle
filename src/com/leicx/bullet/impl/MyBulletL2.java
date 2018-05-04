package com.leicx.bullet.impl;

import com.leicx.bullet.Bullet;

/**
 *@author leicx 
 *@time 2018��1��17��
 */
public class MyBulletL2 extends Bullet {
	/**
	 * ά��һ��MyBulletL2������
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
