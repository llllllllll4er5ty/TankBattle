package com.leicx.tank.impl;

import java.awt.Color;

import com.leicx.bullet.impl.MyBulletL2;
import com.leicx.map.MainMap;
import com.leicx.tank.Tank;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class MyTankL2 extends Tank{
	
	/**
	 * 内部维护一个tank的类
	 */
	public static MyTankL2 myTankL2 = null;
	
	/**
	 * 私有化构造方法
	 */
	private MyTankL2() {	//构造时确定tank的位置，为tank中心的位置
		this.initTank();
	}
	
	/**
	 * initial the tank, initial paramers
	 */
	@Override
	public void initTank() {
		this.direction = Tank.UP;
		this.isExist = true;
		this.HP = 1;
		this.canAttack = true;
		this.canBeAttack = true;
		this.canMove = true;
		this.yPosition = MainMap.HEIGHT - 4;
		this.xPosition = MainMap.WIDTH / 2 + 12;
		this.headYPosition = this.yPosition - 2;
		this.headXPosition = this.xPosition;
		this.tailYPosition = this.yPosition + 2;
		this.tailXPosition = this.xPosition;
		this.color = Color.GREEN;
		this.bullet.flag = true;
		this.bullet = MyBulletL2.getInstance();
	}
	
	
	public static MyTankL2 getInstance() {
		if(myTankL2 == null) {
			myTankL2 = new MyTankL2();
		}
		return myTankL2;
	}
	
}
