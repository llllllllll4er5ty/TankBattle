package com.leicx.tank.impl;

import java.awt.Color;

import com.leicx.bullet.impl.MyBulletL2;
import com.leicx.map.MainMap;
import com.leicx.tank.Tank;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class MyTankL2 extends Tank{
	
	/**
	 * �ڲ�ά��һ��tank����
	 */
	public static MyTankL2 myTankL2 = null;
	
	/**
	 * ˽�л����췽��
	 */
	private MyTankL2() {	//����ʱȷ��tank��λ�ã�Ϊtank���ĵ�λ��
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
