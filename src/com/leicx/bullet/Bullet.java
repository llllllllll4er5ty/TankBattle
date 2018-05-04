package com.leicx.bullet;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import com.leicx.map.MainMap;
import com.leicx.map.MapObject;
import com.leicx.map.impl.Boss;
import com.leicx.map.impl.Empty;
import com.leicx.tank.Tank;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public class Bullet extends MapObject{
	public Bullet() {
		color = Color.WHITE;
	}
	/**
	 * ����
	 */
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = -1;
	public static final int LEFT = -2;
	
	//�ӵ�����
	private int count;
	//����
	private int speed;
	//����
	private int direction = 2;
	//λ�ã���������
	public int xPosition = 0;
	public int yPosition = 0;
	//״̬��Ĭ�ϲ�����
	private boolean exist = false;
	/**
	 * �ӵ������ԣ��ж��ǵз��Ļ����ҷ����ӵ���Ĭ��Ϊtrue���ҷ���tank������ӵ�
	 */
	public boolean flag = true; 
	
	//����
	public void generate() {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				synchronized ("��") {
					if(!isExist()) {
						timer.cancel();
						return;
					}
					move();
				}
			}
		}, 0,20);
	}
	//����
	public void destroy() {
		setExist(false);
		setCount(0);
		clearBullet();
	}
	/**
	 * tank move
	 * @param direction
	 */
	public void move() {
		int delX = xPosition;
		int delY = yPosition;
		if(direction == Bullet.UP || direction == Bullet.DOWN) {
			this.yPosition = this.yPosition - this.direction;
		}else {
			this.xPosition = this.xPosition + this.direction/2;
		}
		//�ӵ���ʧ����
		//1.ײǽ
		if("Wall".equals(MainMap.mainMap[yPosition][xPosition].getName())) {
			destroy();
			MainMap.mainMap[delY][delX] = Empty.getInstance();
		}
		//2.��tank
		else if (MainMap.mainMap[yPosition][xPosition].getName().contains("Tank")){
			Tank tank = MainMap.tankMap.get(MainMap.mainMap[yPosition][xPosition].getName());
			if(tank.bullet.flag != this.flag) {
				tank.destroy();
				destroy();
			}
			MainMap.mainMap[delY][delX] = Empty.getInstance();
			
		}
		//3.��ɳ��
		else if("Sand".equals(MainMap.mainMap[yPosition][xPosition].getName())){
			destroy();
			int less;
			int more;
			if(this.direction==Bullet.UP||this.direction==Bullet.DOWN) {
				less = this.xPosition-2;
				more = this.xPosition+2;
				for(int x=less;x<=more;x++) {
					if("Sand".equals(MainMap.mainMap[yPosition][x].getName())) {
						MainMap.mainMap[yPosition][x] = Empty.getInstance();
					}
				}
			}else {
				less = this.yPosition -2 ;
				more = this.yPosition + 2;
				for(int x=less;x<=more;x++) {
					if("Sand".equals(MainMap.mainMap[x][xPosition].getName())) {
						MainMap.mainMap[x][xPosition] = Empty.getInstance();
					}
				}
			}
			MainMap.mainMap[delY][delX] = Empty.getInstance();
		}
		//4.��Boss
		else if("Boss".equals(MainMap.mainMap[yPosition][xPosition].getName())) {
			destroy();
			Boss.isExist = false;
			MainMap.mainMap[delY][delX] = Empty.getInstance();
		}
		//5.�ӵ���ײ
		else if(MainMap.mainMap[yPosition][xPosition].getName().contains("Bullet")) {
			MainMap.bulletMap.get(MainMap.mainMap[yPosition][xPosition].getName()).destroy();
			destroy();
			MainMap.mainMap[delY][delX] = Empty.getInstance();
		}
		else {
			MainMap.mainMap[yPosition][xPosition] = this;
			if(MainMap.mainMap[delY][delX].getName().contains("Bullet")) {
				MainMap.mainMap[delY][delX] = Empty.getInstance();
			}
		}
	}
	
	/**
	 * ����ָ��MainMap.mainMap[yPosition][xPosition]������
	 */
	public void getBullet() {
		if(isExist()) {
			MainMap.mainMap[yPosition][xPosition] = this;
		}
	}
	
	/**
	 * clear the self-tank on the map judged by name
	 */
	public void clearBullet() {
		MainMap.cleanMap(this.getName());
	};
	
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return ".";
	}
}
