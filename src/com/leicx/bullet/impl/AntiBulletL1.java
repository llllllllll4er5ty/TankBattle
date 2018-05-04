package com.leicx.bullet.impl;

import com.leicx.bullet.Bullet;

/**
 *@author leicx 
 *@time 2018��1��17��
 */
public class AntiBulletL1 extends Bullet {
	/**
	 * ά��һ��AntiBulletL1������
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
