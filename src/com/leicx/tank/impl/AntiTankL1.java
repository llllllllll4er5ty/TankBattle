package com.leicx.tank.impl;

import java.awt.Color;
import java.util.Random;

import com.leicx.bullet.impl.AntiBulletL1;
import com.leicx.map.MainMap;
import com.leicx.map.impl.Boss;
import com.leicx.tank.Tank;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class AntiTankL1 extends Tank{
	
	/**
	 * 维护一个AntiTankL1的引用
	 */
	public static AntiTankL1 antiTankL1 = null;
	
	/**
	 * 私有化构造方法
	 */
	private AntiTankL1() {	//构造时确定tank的位置，为tank中心的位置
		this.initTank();
	}
	
	@Override
	public void initTank() {
		this.direction = Tank.DOWN;
		this.isExist = true;
		this.HP = 1;
		this.canAttack = true;
		this.canBeAttack = true;
		this.canMove = true;
		this.yPosition = 3;
		this.xPosition = MainMap.WIDTH / 2-24;
		this.headYPosition = this.yPosition + 2;
		this.headXPosition = this.xPosition;
		this.tailYPosition = this.yPosition - 2;
		this.tailXPosition = this.xPosition;
		this.color = Color.GRAY;
		this.bullet.flag = false;	//敌方的子弹
		this.bullet = AntiBulletL1.getInstance();
	}
	
	/**
	 * to get instance
	 */
	public static AntiTankL1 getInstance() {
		if(antiTankL1 == null) {
			antiTankL1 = new AntiTankL1();
		}
		return antiTankL1;
	}
	
	/**
	 * Anti-Tank auto-move and auto attack
	 * attack tank first
	 */
	public void autoMove() {
		if(this.isExist && Boss.isExist) {
			Random random = new Random();
			if(canMove) {
				move(direction);
//				attack();
			}else {
				canMove = true;
				direction = (random.nextInt(2)+1) * (Math.random()>0.5?1:-1);
			}
		}
	}
}
