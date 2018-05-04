package com.leicx.tank;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.leicx.bullet.Bullet;
import com.leicx.bullet.impl.AntiBulletL1;
import com.leicx.bullet.impl.MyBulletL1;
import com.leicx.bullet.impl.MyBulletL2;
import com.leicx.map.MainMap;
import com.leicx.map.MapObject;
import com.leicx.map.impl.Boss;

/**
 *@author leicx 
 *@time 2018��1��4��
 */
public abstract class Tank extends MapObject{
	//���tank������
	public Map<String, String> map = new HashMap<>();
	//�����ͼ��
	public String present = "";
	/**
	 * ������
	 */
	public static int UP = 1;  
	public static int RIGHT = 2;  
	public static int DOWN = -1;  
	public static int LEFT = -2;  
	/**
	 * �ƶ��ٶ�
	 */
	public static int speed = 1;
	/**
	 * ��ɫ����
	 */
	public static final Color COLOR_PT = Color.WHITE;
	public static final Color COLOR_FRONT = Color.BLACK;
	//λ�ã�tank���ĵĺ����꣬������
	public int xPosition = 0;
	public int yPosition = 0;
	//λ�ã�tank��Ͳ�ĺ�������
	public int headXPosition = 0;
	public int headYPosition = 0;
	//λ�ã�tankβ����λ��
	public int tailXPosition = 0;
	public int tailYPosition = 0;
	
	//�Ƿ�����ƶ���Ĭ�Ͽ����ƶ�
	public boolean canMove = true;
	//�Ƿ���Թ�����Ĭ�Ͽ��Թ���
	public boolean canAttack = true;
	//�Ƿ���Ա�������Ĭ�Ͽ��Ա�����
	public boolean canBeAttack = true;
	//״̬
	public boolean isExist = false;
	//Ѫ��
	public int HP = 1;
	//����ֵ
	public int life = 5;
	//����Ĭ������
	public int direction = Tank.UP;
	//�ڲ�ά��һ���ӵ�����
	public Bullet bullet = new Bullet();
	/**
	 * tank move
	 * @param direction
	 */
	public void move(int direction) {
		if(this.isExist && Boss.isExist) {	//tank���ڣ�����boss���ڵ�����¿����ƶ� 

			//tank��Ͳλ��
			int ytemp = this.headYPosition;
			int xtemp = this.headXPosition;
			//tankβ��λ��
			int tytemp = this.tailYPosition;
			int txtemp = this.tailXPosition;
			
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				int y = Integer.parseInt(key.split(",")[0]);
				int x = Integer.parseInt(key.split(",")[1]);
				present = entry.getValue();
				if(y==this.yPosition && x==this.xPosition) {	//�ҵ���tank������
					//�ж�Ҫ�ƶ��ķ���
					if(direction == Tank.UP || direction == Tank.DOWN) {	//�����ƶ�
						if(this.direction == direction) {	//Ҫ�ƶ��ķ�����tank������ͬ��ǰ��
							y = y - this.direction;
						}	
						//������Ͳλ��
						ytemp = y - direction * 2;
						xtemp = x;	
						//����β����λ��
						tytemp = y + direction * 2;
						txtemp = x;
						//�ж��ܷ�ǰ��
						for(int i=xtemp-2;i<=xtemp+2;i++) {
							if(judgeMove(MainMap.mainMap[ytemp][i].getName())==1) {
								this.canMove = true;
								continue;
							}else {
								this.canMove = false;
								return;
							}
						}
					}else {	//�����ƶ�
						if(this.direction == direction) {
							x = x + this.direction/2;
						}
						//������Ͳλ��
						ytemp = y;
						xtemp = x + direction;
						//����β����λ��
						tytemp = y;
						txtemp = x - direction;
						//�ж��ܷ�ǰ��
						for(int i=ytemp-2;i<=ytemp+2;i++) {
							if(judgeMove(MainMap.mainMap[i][xtemp].getName())==1) {
								this.canMove = true;
								continue;
							}else {
								this.canMove = false;
								return;
							}
						}
					}
					//tank���ĵ�λ��
					this.yPosition = y;
					this.xPosition = x;
					//tank��Ͳ��λ��
					this.headYPosition = ytemp;
					this.headXPosition = xtemp;
					//tankβ����λ��
					this.tailYPosition = tytemp;
					this.tailXPosition = txtemp;
					break;
				}
			}
			
			//���÷���
			this.direction = direction;
			this.clearTank();
			getTank();
		}
	};
	
	/**
	 * judge tank can or can not move
	 * @return	1: tank can move;	2: tank can not move;	3:destroy
	 */
	public int judgeMove(String name) {
		if(name == null) {
			return 2;
		}
		if(!"Empty".equals(name)) {
			if(this.getName().equals(name)) {	//ת���ʱ�����������Լ�
				return 1;
			}else if(name.contains("Bullet")){
				if("AntiBulletL1".equals(name)) {
					if(AntiBulletL1.getInstance().flag != this.bullet.flag) {
						AntiBulletL1.getInstance().destroy();
						this.destroy();
						return 3;
					}
				}else if("MyBulletL1".equals(name)) {
					if(MyBulletL1.getInstance().flag != this.bullet.flag) {
						MyBulletL1.getInstance().destroy();
						this.destroy();
						return 3;
					}
				}else if("MyBulletL2".equals(name)) {
					if(MyBulletL2.getInstance().flag != this.bullet.flag) {
						MyBulletL2.getInstance().destroy();
						this.destroy();
						return 3;
					}
				}
				return 1;
			}else if("Grass".equals(name)){
				return 1;
			}else {
				return 2;
			}
		}
		return 1;
	}
	
	/**
	 * tank attack
	 */
	public void attack() {
		if(this.canAttack) {

			//�ж��Ƿ���Թ���
			if(bullet.getCount()==0) {	//��ǰ��ͼ��û���Լ�������ӵ������Թ���
				//�����ӵ��ķ���
				bullet.setDirection(this.direction);
				//�����ӵ���λ�ã���ʱ��tank��λ����ͬ
				bullet.yPosition = this.headYPosition;
				bullet.xPosition = this.headXPosition;
				//�����ӵ�����
				// TODO Ӧ�ø���tank����̬����
				bullet.setCount(1);
				//�����ӵ�״̬
				bullet.setExist(true);
				//�����ӵ�
				bullet.generate();
			}
		
		}
	};
	/**
	 * get tank
	 */
	public void getTank() {
		if(this.isExist == false) {	//��������
			this.initTank();
		}

		//���map
		map.clear();
		
		for(int i=this.yPosition-2;i<=this.yPosition+2;i++) {
			for(int j=this.xPosition-2;j<=this.xPosition+2;j++) {
				map.put(i+","+j, "*");
			}
		}
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			int j = Integer.parseInt(key.split(",")[0]);
			int i = Integer.parseInt(key.split(",")[1]);
			present = entry.getValue();
			MainMap.mainMap[j][i] = this;
		}
	};
	/**
	 * destroy the tank
	 */
	public void destroy() {
		map.clear();	//clear���tank��map
		this.clearTank();	//�����ͼ�ϵ�tank
		this.isExist = false;
		this.HP = 0;
		this.canAttack = false;
		this.canBeAttack = false;
		this.canMove = false;
		this.xPosition = -10;
		this.yPosition = -10;
		this.headXPosition = -10;
		this.headYPosition = -10;
		this.tailYPosition = -10;
		this.tailXPosition = -10;
		this.life--;
//		MainMap.printMap();
	};
	
	/**
	 * clear the self-tank on the map judged by name
	 */
	public void clearTank() {
		MainMap.cleanMap(this.getName());
	};
	
	@Override
	public String toString() {
		return present;
	}
	
	//��ʼ��
	public abstract void initTank();
	
	/**
	 * ����̨��tank���ò���
	 */
	public void drawTankConsole() {
		//����̨��tank
		if(this.direction == Tank.UP || this.direction == Tank.DOWN) {
			map.put(this.yPosition+","+this.xPosition, "O");
			map.put((this.yPosition+this.direction)+","+this.xPosition, "*");
			map.put((this.yPosition-this.direction)+","+this.xPosition, "*");
			map.put((this.yPosition-this.direction*2)+","+this.xPosition, "*");
			
			map.put(this.yPosition+","+(this.xPosition-this.direction), "*");
			map.put(this.yPosition+","+(this.xPosition+this.direction), "*");
			map.put(this.yPosition+","+(this.xPosition-this.direction*2), "*");
			map.put(this.yPosition+","+(this.xPosition+this.direction*2), "*");
			
			map.put((this.yPosition+this.direction)+","+(this.xPosition-this.direction), "*");
			map.put((this.yPosition+this.direction)+","+(this.xPosition+this.direction), "*");
			map.put((this.yPosition+this.direction)+","+(this.xPosition-this.direction*2), "*");
			map.put((this.yPosition+this.direction)+","+(this.xPosition+this.direction*2), "*");
			
			map.put((this.yPosition+this.direction*2)+","+(this.xPosition-this.direction), "*");
			map.put((this.yPosition+this.direction*2)+","+(this.xPosition+this.direction), "*");
			map.put((this.yPosition+this.direction*2)+","+(this.xPosition-this.direction*2), "*");
			map.put((this.yPosition+this.direction*2)+","+(this.xPosition+this.direction*2), "*");
			
		}else {
			map.put(yPosition+","+this.xPosition, "O");
			map.put(yPosition+","+(this.xPosition-this.direction/2), "*");
			map.put(yPosition+","+(this.xPosition+this.direction/2), "*");
			map.put(yPosition+","+(this.xPosition+this.direction), "*");
			
			map.put((yPosition+this.direction/2)+","+this.xPosition, "*");
			map.put((yPosition-this.direction/2)+","+this.xPosition, "*");
			map.put((yPosition+this.direction)+","+this.xPosition, "*");
			map.put((yPosition-this.direction)+","+this.xPosition, "*");
			
			map.put((yPosition-this.direction/2)+","+(this.xPosition-this.direction/2), "*");
			map.put((yPosition-this.direction)+","+(this.xPosition-this.direction/2), "*");
			map.put((yPosition+this.direction/2)+","+(this.xPosition-this.direction/2), "*");
			map.put((yPosition+this.direction)+","+(this.xPosition-this.direction/2), "*");
			
			map.put((yPosition-this.direction/2)+","+(this.xPosition-this.direction), "*");
			map.put((yPosition-this.direction)+","+(this.xPosition-this.direction), "*");
			map.put((yPosition+this.direction/2)+","+(this.xPosition-this.direction), "*");
			map.put((yPosition+this.direction)+","+(this.xPosition-this.direction), "*");
		}
	}
	
}
