package com.leicx.tank.impl;

import java.awt.Color;

import com.leicx.bullet.impl.MyBulletL1;
import com.leicx.map.MainMap;
import com.leicx.tank.Tank;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class MyTankL1 extends Tank{
	
	/**
	 * �ڲ�ά��һ��tank����
	 */
	public static MyTankL1 myTankL1 = null;
	
	/**
	 * ˽�л����췽��
	 */
	private MyTankL1() {	//����ʱȷ��tank��λ�ã�Ϊtank���ĵ�λ��
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
		this.bullet.flag = true;	//�ӵ����Լ��˵��ӵ�
		this.bullet = MyBulletL1.getInstance();
	}
	
	
	public static MyTankL1 getInstance() {
		if(myTankL1 == null) {
			myTankL1 = new MyTankL1();
		}
		return myTankL1;
	}
	
}
