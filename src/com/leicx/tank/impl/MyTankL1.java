package com.leicx.tank.impl;

import java.awt.Color;

import com.leicx.bullet.impl.MyBulletL1;
import com.leicx.map.MainMap;
import com.leicx.tank.Tank;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class MyTankL1 extends Tank{
	
	/**
	 * 内部维护一个tank的类
	 */
	public static MyTankL1 myTankL1 = null;
	
	/**
	 * 私有化构造方法
	 */
	private MyTankL1() {	//构造时确定tank的位置，为tank中心的位置
		this.initTank();
	}
	
	/**
	 * initial the tank, initial parameters
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
		this.xPosition = MainMap.WIDTH / 2 - 13;
		this.headYPosition = this.yPosition - 2;
		this.headXPosition = this.xPosition;
		this.tailYPosition = this.yPosition + 2;
		this.tailXPosition = this.xPosition;
		this.color = Color.YELLOW;
		this.bullet.flag = true;	//子弹是自己人的子弹
		this.bullet = MyBulletL1.getInstance();
	}
	
	
	public static MyTankL1 getInstance() {
		if(myTankL1 == null) {
			myTankL1 = new MyTankL1();
		}
		return myTankL1;
	}
	
}
