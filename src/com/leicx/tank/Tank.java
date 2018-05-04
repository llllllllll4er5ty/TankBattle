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
 *@time 2018年1月4日
 */
public abstract class Tank extends MapObject{
	//存放tank的容器
	public Map<String, String> map = new HashMap<>();
	//代表的图形
	public String present = "";
	/**
	 * 方向常量
	 */
	public static int UP = 1;  
	public static int RIGHT = 2;  
	public static int DOWN = -1;  
	public static int LEFT = -2;  
	/**
	 * 移动速度
	 */
	public static int speed = 1;
	/**
	 * 颜色常量
	 */
	public static final Color COLOR_PT = Color.WHITE;
	public static final Color COLOR_FRONT = Color.BLACK;
	//位置，tank中心的横坐标，纵坐标
	public int xPosition = 0;
	public int yPosition = 0;
	//位置，tank炮筒的横纵坐标
	public int headXPosition = 0;
	public int headYPosition = 0;
	//位置，tank尾部的位置
	public int tailXPosition = 0;
	public int tailYPosition = 0;
	
	//是否可以移动，默认可以移动
	public boolean canMove = true;
	//是否可以攻击，默认可以攻击
	public boolean canAttack = true;
	//是否可以被攻击，默认可以被攻击
	public boolean canBeAttack = true;
	//状态
	public boolean isExist = false;
	//血量
	public int HP = 1;
	//生命值
	public int life = 5;
	//方向，默认向上
	public int direction = Tank.UP;
	//内部维护一个子弹的类
	public Bullet bullet = new Bullet();
	/**
	 * tank move
	 * @param direction
	 */
	public void move(int direction) {
		if(this.isExist && Boss.isExist) {	//tank存在，并且boss存在的情况下可以移动 

			//tank炮筒位置
			int ytemp = this.headYPosition;
			int xtemp = this.headXPosition;
			//tank尾部位置
			int tytemp = this.tailYPosition;
			int txtemp = this.tailXPosition;
			
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				int y = Integer.parseInt(key.split(",")[0]);
				int x = Integer.parseInt(key.split(",")[1]);
				present = entry.getValue();
				if(y==this.yPosition && x==this.xPosition) {	//找到了tank的中心
					//判断要移动的方向
					if(direction == Tank.UP || direction == Tank.DOWN) {	//上下移动
						if(this.direction == direction) {	//要移动的方向与tank方向相同，前进
							y = y - this.direction;
						}	
						//设置炮筒位置
						ytemp = y - direction * 2;
						xtemp = x;	
						//设置尾部的位置
						tytemp = y + direction * 2;
						txtemp = x;
						//判断能否前进
						for(int i=xtemp-2;i<=xtemp+2;i++) {
							if(judgeMove(MainMap.mainMap[ytemp][i].getName())==1) {
								this.canMove = true;
								continue;
							}else {
								this.canMove = false;
								return;
							}
						}
					}else {	//左右移动
						if(this.direction == direction) {
							x = x + this.direction/2;
						}
						//设置炮筒位置
						ytemp = y;
						xtemp = x + direction;
						//设置尾部的位置
						tytemp = y;
						txtemp = x - direction;
						//判断能否前进
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
					//tank中心的位置
					this.yPosition = y;
					this.xPosition = x;
					//tank炮筒的位置
					this.headYPosition = ytemp;
					this.headXPosition = xtemp;
					//tank尾部的位置
					this.tailYPosition = tytemp;
					this.tailXPosition = txtemp;
					break;
				}
			}
			
			//设置方向
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
			if(this.getName().equals(name)) {	//转弯的时候碰到的是自己
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

			//判断是否可以攻击
			if(bullet.getCount()==0) {	//当前地图上没有自己发射的子弹，可以攻击
				//设置子弹的方向
				bullet.setDirection(this.direction);
				//设置子弹的位置，暂时和tank的位置相同
				bullet.yPosition = this.headYPosition;
				bullet.xPosition = this.headXPosition;
				//设置子弹数量
				// TODO 应该根据tank的形态设置
				bullet.setCount(1);
				//设置子弹状态
				bullet.setExist(true);
				//生成子弹
				bullet.generate();
			}
		
		}
	};
	/**
	 * get tank
	 */
	public void getTank() {
		if(this.isExist == false) {	//重新生成
			this.initTank();
		}

		//清除map
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
		map.clear();	//clear存放tank的map
		this.clearTank();	//清除地图上的tank
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
	
	//初始化
	public abstract void initTank();
	
	/**
	 * 控制台画tank，用不到
	 */
	public void drawTankConsole() {
		//控制台画tank
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
