package com.leicx.key;
/**
 *@author leicx 
 *@time 2018年1月17日
 */

import com.leicx.tank.Tank;
import com.leicx.tank.impl.AntiTankL1;
import com.leicx.tank.impl.MyTankL1;
import com.leicx.tank.impl.MyTankL2;

public class KeyStatus {
	/**
	 * 定义按键状态，默认为false，没有被按下
	 */
	public static boolean W_STATUS = false;
	public static boolean S_STATUS = false;
	public static boolean D_STATUS = false;
	public static boolean A_STATUS = false;
	public static boolean SPACE_STATUS = false;
	public static boolean R_STATUS = false;
	
	public static boolean UP_STATUS = false;
	public static boolean DOWN_STATUS = false;
	public static boolean RIGHT_STATUS = false;
	public static boolean LEFT_STATUS = false;
	public static boolean J_STATUS = false;
	public static boolean K_STATUS = false;
	
	/**
	 * 内部维护tank的实例
	 */
	public static MyTankL1 myTankL1 = MyTankL1.getInstance();
	public static MyTankL2 myTankL2 = MyTankL2.getInstance();
	public static AntiTankL1 antiTankL1 = AntiTankL1.getInstance();
	
	/**
	 * 根据按键状态来判断tank行为
	 */
	public static void act() {
		if(W_STATUS) {
			myTankL1.move(Tank.UP);
		}
		if(S_STATUS) {
			myTankL1.move(Tank.DOWN);
		}
		if(D_STATUS) {
			myTankL1.move(Tank.RIGHT);
		}
		if(A_STATUS) {
			myTankL1.move(Tank.LEFT);
		}
		if(SPACE_STATUS) {
			myTankL1.attack();
		}
		if(R_STATUS) {
			if(!myTankL1.isExist) {
				myTankL1.getTank();
			}
		}
		if(UP_STATUS) {
			myTankL2.move(Tank.UP);
		}
		if(DOWN_STATUS) {
			myTankL2.move(Tank.DOWN);
		}
		if(RIGHT_STATUS) {
			myTankL2.move(Tank.RIGHT);
		}
		if(LEFT_STATUS) {
			myTankL2.move(Tank.LEFT);
		}
		if(J_STATUS) {
			myTankL2.attack();
		}
		if(K_STATUS) {
			if(!myTankL2.isExist) {
				myTankL2.getTank();
			}
		}
	}
	
}
