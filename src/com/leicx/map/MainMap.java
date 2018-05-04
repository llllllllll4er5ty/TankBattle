package com.leicx.map;
/**
 * 注意问题：
 * 	1.画好sand之后，当子弹把sand打掉之后，子弹或者tank走到上面会出现sand的图片；
 * 		原因：sand销毁只是把map上坐标对应的sand销毁了，但是图片没有销毁，还一直在paint，只是在画完图片之后，又重新画了一次empty，导致图片看不见，
 * 			当子弹或者tank走上去之后，那个地方不是empty了，所以不会重画，因此会显示出图片。（图片在子弹之后画的，覆盖了子弹）
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.leicx.bullet.Bullet;
import com.leicx.bullet.impl.AntiBulletL1;
import com.leicx.bullet.impl.MyBulletL1;
import com.leicx.bullet.impl.MyBulletL2;
import com.leicx.key.KeyStatus;
import com.leicx.map.impl.Award;
import com.leicx.map.impl.Boss;
import com.leicx.map.impl.Empty;
import com.leicx.map.impl.Grass;
import com.leicx.map.impl.Mask;
import com.leicx.map.impl.Sand;
import com.leicx.map.impl.Wall;
import com.leicx.map.impl.Water;
import com.leicx.tank.Tank;
import com.leicx.tank.impl.AntiTankL1;
import com.leicx.tank.impl.MyTankL1;
import com.leicx.tank.impl.MyTankL2;

/**
 *@author leicx 
 *@time 2018年1月4日
 */
public class MainMap extends JPanel{
	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = 1L;
	//map的宽高
	public static final int HEIGHT = 102;
	public static final int WIDTH = 102;
	//地图上每个小格的宽高
	public static final int CELLHEIGHT = 7;
	public static final int CELLWIDTH = 7;
	public static MapObject[][] mainMap = new MapObject[HEIGHT][WIDTH];
	//Frame组件
	public static final JFrame frame = new JFrame();
	/**
	 * cursorStatus
	 */
	public static String cursorStatus = "";
	/**
	 * 图片对象
	 */
	public static Image img = null;
	public static Image img_boss =  new ImageIcon(MainMap.class.getResource("/images/boss.jpg")).getImage();
	public static Image img_boss_die =  new ImageIcon(MainMap.class.getResource("/images/boss_die.jpg")).getImage();
	public static Image img_MyTankL1_UP =  new ImageIcon(MainMap.class.getResource("/images/MyTankL1_UP.jpg")).getImage();
	public static Image img_MyTankL1_RIGHT =  new ImageIcon(MainMap.class.getResource("/images/MyTankL1_RIGHT.jpg")).getImage();
	public static Image img_MyTankL1_DOWN =  new ImageIcon(MainMap.class.getResource("/images/MyTankL1_DOWN.jpg")).getImage();
	public static Image img_MyTankL1_LEFT =  new ImageIcon(MainMap.class.getResource("/images/MyTankL1_LEFT.jpg")).getImage();
	public static Image img_MyTankL2_UP =  new ImageIcon(MainMap.class.getResource("/images/MyTankL2_UP.jpg")).getImage();
	public static Image img_MyTankL2_RIGHT =  new ImageIcon(MainMap.class.getResource("/images/MyTankL2_RIGHT.jpg")).getImage();
	public static Image img_MyTankL2_DOWN =  new ImageIcon(MainMap.class.getResource("/images/MyTankL2_DOWN.jpg")).getImage();
	public static Image img_MyTankL2_LEFT =  new ImageIcon(MainMap.class.getResource("/images/MyTankL2_LEFT.jpg")).getImage();
	public static Image img_AntiTankL1_UP =  new ImageIcon(MainMap.class.getResource("/images/AntiTankL1_UP.jpg")).getImage();
	public static Image img_AntiTankL1_RIGHT =  new ImageIcon(MainMap.class.getResource("/images/AntiTankL1_RIGHT.jpg")).getImage();
	public static Image img_AntiTankL1_DOWN =  new ImageIcon(MainMap.class.getResource("/images/AntiTankL1_DOWN.jpg")).getImage();
	public static Image img_AntiTankL1_LEFT =  new ImageIcon(MainMap.class.getResource("/images/AntiTankL1_LEFT.jpg")).getImage();
	public static Image img_sand =  new ImageIcon(MainMap.class.getResource("/images/sandUnit.jpg")).getImage();
	public static Image img_water =  new ImageIcon(MainMap.class.getResource("/images/waterUnit.jpg")).getImage();
	public static Image img_grass =  new ImageIcon(MainMap.class.getResource("/images/grassUnit.jpg")).getImage();
	public static Image img_wall =  new ImageIcon(MainMap.class.getResource("/images/wallUnit.jpg")).getImage();
	public static Image img_empty =  new ImageIcon(MainMap.class.getResource("/images/emptyUnit.jpg")).getImage();
	
	/**
	 * map to store tanks
	 */
	public static Map<String, Tank> tankMap = new HashMap<String, Tank>();
	/**
	 * map to store bullets
	 */
	public static Map<String, Bullet> bulletMap = new HashMap<String, Bullet>();
	/**
	 * map to store images
	 */
	public static Map<String, Image> imgMap = new HashMap<String, Image>(); 
	/**
	 * map to store map-thing's coordinate(rect) that can be destroy
	 */
	public static Map<String, String> objectMap = new HashMap<String, String>(); 
	/**
	 * map to store map-thing's coordinate(rect) that can not be destroy
	 */
	public static Map<String, String> objectMapCanThrough = new HashMap<String, String>(); 
	/**
	 * map to store map-thing's coordinate(picture)
	 */
	public static Map<String, String> objectImgMap = new HashMap<String, String>(); 
	/**
	 * 当前游戏模式，true为游戏模式，false为构建地图模式，默认为构建地图模式
	 */
	public static boolean gameMode = false;
	
	static {
		drawMap();
		tankMap.put("MyTankL1", MyTankL1.getInstance());
		tankMap.put("MyTankL2", MyTankL2.getInstance());
		tankMap.put("AntiTankL1", AntiTankL1.getInstance());
		bulletMap.put("MyBulletL1", MyBulletL1.getInstance());
		bulletMap.put("MyBulletL2", MyBulletL2.getInstance());
		bulletMap.put("AntiBulletL1", AntiBulletL1.getInstance());
		imgMap.put("sand", img_sand);
		imgMap.put("wall", img_wall);
		imgMap.put("water", img_water);
		imgMap.put("grass", img_grass);
//		printMap();
	}
	//画地图
	public static void drawMap() {
		Sand sand = Sand.getInstance();
		Wall wall = Wall.getInstance();
		Empty empty = Empty.getInstance();
		Boss boss = new Boss();
		for(int i=0;i<mainMap.length;i++) {	//纵向
			for(int j=0;j<mainMap[i].length;j++) {	//横向
				if(i==0 || i==mainMap.length-1 || j==0 || j==mainMap[i].length-1) {
					mainMap[i][j] = wall;
				}else {
					mainMap[i][j] = empty;
				}
				//老窝
				if(i==(MainMap.HEIGHT-13)||i==(MainMap.HEIGHT-12)) {
					int x=(MainMap.WIDTH/2-8);
					int y=(MainMap.WIDTH/2+7);
					if(x<j && j<y) {
						mainMap[i][j] = sand;
					}
				}
				if(j==(MainMap.WIDTH/2-6)||j==(MainMap.WIDTH/2-7)||j==(MainMap.WIDTH/2+6)||j==(MainMap.WIDTH/2+5)) {
					int x=(MainMap.HEIGHT-12);
					int y=(MainMap.HEIGHT-1);
					if(x<i && i<y) {
						mainMap[i][j] = sand;
					}
				}
				//Boss
				if(i>(MainMap.HEIGHT-12) && i<(MainMap.HEIGHT-1)) {
					if(j>(MainMap.WIDTH/2-6)&&j<(MainMap.WIDTH/2+5)) {
						mainMap[i][j] = boss;
					}
				}
			}
		}
	}
	//产生奖励
	public static void getAward() {
		Random random = new Random();
		int y = random.nextInt(HEIGHT);
		int x = random.nextInt(WIDTH);
		while(!"Empty".equals(mainMap[y][x].getName())) {
			y = random.nextInt(HEIGHT);
			x = random.nextInt(WIDTH);
		}
		mainMap[y][x] = new Award();
	}
	//地图clean
	public static void cleanMap(String name) {
		if(name == null) {
			return;
		}
		for(int i=0;i<mainMap.length;i++) {	//纵向
			for(int j=0;j<mainMap[i].length;j++) {	//横向
				if(name.equals(mainMap[i][j].getName())) {
					mainMap[i][j] = Empty.getInstance();
				}
			}
		}
	}
	
	@Override
	//TODO 可以考虑将所有相同类型的地图物体放入一个map中，一类地图物体只维护一个实例，先画地图物体方块，再画地图物体图片，再画empty
	public void paint(final Graphics g) {
		synchronized("锁") {
			if(gameMode) {
				
				//打印地图，以方块的形式
				for(int i=0;i<mainMap.length;i++) {	//纵向
					for(int j=0;j<mainMap[i].length;j++) {	//横向
						g.setColor(mainMap[i][j].color);
//						g.setColor(judgeColor(tankMap.get("MyTankL1"), i, j));
						g.setColor(judgeColor(tankMap.get("AntiTankL1"), i, j));
						if(mainMap[i][j].getName().contains("Bullet")) {
							g.fillOval(j*CELLWIDTH, i*CELLHEIGHT, CELLWIDTH, CELLHEIGHT);
						}else {
							g.fill3DRect(j*CELLWIDTH, i*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
						}
					}
				}
				
				//draw can-through things
				drawObjectPic(objectMapCanThrough,g);
				
				//画tank图片
				drawTank(tankMap.get("MyTankL1"),g);
				drawTank(tankMap.get("MyTankL2"),g);
				drawTank(tankMap.get("AntiTankL1"),g);
				
				//重新画一遍草
				drawGrassAgain(g);
				
				//tank and bullet所在位置重新引用，避免出现grass over tank/bullet的情况
				reAppoint();
				
				//再画一遍empty和子弹
				for(int i=0;i<mainMap.length;i++) {	//纵向
					for(int j=0;j<mainMap[i].length;j++) {	//横向
						if("Empty".equals(mainMap[i][j].getName()) || mainMap[i][j].getName().contains("Bullet")) {
							g.setColor(mainMap[i][j].color);
							g.fill3DRect(j*CELLWIDTH, i*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
						}
					}
				}
				
				//Boss是不是挂了
				img =  img_boss;
				if(!Boss.isExist) {
					Font font = new Font("Serif", Font.PLAIN, 50);
					g.setColor(Color.RED);
					g.setFont(font);
					g.drawString("Game  Over", HEIGHT/3*CELLHEIGHT, WIDTH/2*CELLWIDTH);
					tankMap.get("MyTankL1").canMove = false;
					tankMap.get("MyTankL1").canAttack = false;
					tankMap.get("MyTankL2").canMove = false;
					tankMap.get("MyTankL2").canAttack = false;
					tankMap.get("AntiTankL1").canMove = false;
					tankMap.get("AntiTankL1").canAttack = false;
					img =  img_boss_die;
				}
				//画Boss,Boss是图片
				g.drawImage(img, (WIDTH/2-5)*CELLWIDTH, (HEIGHT-11)*CELLHEIGHT, 70, 70, this);
			}else {
				//打印地图，以方块的形式
				for(int i=0;i<mainMap.length;i++) {	//纵向
					for(int j=0;j<mainMap[i].length;j++) {	//横向
						//鼠标移动时消除阴影，阴影默认状态是不存在的，只是起指示作用
						if(!mainMap[i][j].exist) {
							mainMap[i][j] = Empty.getInstance();
						}
						g.setColor(mainMap[i][j].color);
						g.fill3DRect(j*CELLWIDTH, i*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
					}
				}
				//画地图物体方块和图片
				drawObjectPic(objectMap,g);
				drawObjectPic(objectMapCanThrough,g);
				//画完之后清除objectMap,防止出现子弹打不掉sand的现象
				objectMap.clear();
				//判断鼠标形状是否是手型，是手型，则map中画阴影
				drawMask(g);
			}
		}
	}
	/**
	 * 重新画一遍草，覆盖tank
	 * @param g
	 */
	private void drawGrassAgain(final Graphics g) {
		for(Map.Entry<String, String> e : objectImgMap.entrySet()) {
			int hLevel = Integer.parseInt(e.getKey().split(",")[0]);
			int vLevel = Integer.parseInt(e.getKey().split(",")[1]);
			String picName = e.getValue();
			if("grass".equals(picName)) {
				Image image = imgMap.get(picName);
				if(image != null) {
					g.drawImage(imgMap.get(picName), (vLevel*5+1)*CELLWIDTH, (hLevel*5+1)*CELLHEIGHT, 35, 35, this);
				}
			}
		}
	}
	
	/**
	 * reAppoint mainMap[y][x]
	 */
	private void reAppoint() {
		//tank and bullet所在位置重新引用，避免出现grass over tank/bullet的情况
		if(tankMap.get("MyTankL1").exist) {
			tankMap.get("MyTankL1").getTank();
		}
		if(tankMap.get("MyTankL2").exist) {
			tankMap.get("MyTankL2").getTank();
		}
		if(tankMap.get("AntiTankL1").exist) {
			tankMap.get("AntiTankL1").getTank();
		}
		
		tankMap.get("MyTankL1").bullet.getBullet();
		tankMap.get("MyTankL2").bullet.getBullet();
		tankMap.get("AntiTankL1").bullet.getBullet();
	}
	
	/**
	 * 根据objectMap和objectImgMap画出对应的地图物体方块和图片
	 * @param g
	 */
	private void drawObjectPic(Map<String, String> map, final Graphics g) {
		//判断objectMap中是否有该坐标，如果有，则画出对应的地图物体(方块)
		for(Map.Entry<String, String> e : map.entrySet()) {
			int hLevel = Integer.parseInt(e.getKey().split(",")[0]);
			int vLevel = Integer.parseInt(e.getKey().split(",")[1]);
			String picName = e.getValue();
			//先判断用户点击的地方有没有Boss存在
			boolean emptyFlag = true;
			for(int i=(hLevel*5+1);i<=(hLevel+1)*5;i++) {
				for(int j=(vLevel*5+1);j<=(vLevel+1)*5;j++) {
					if("Boss".equals(mainMap[i][j].getName())) {
						emptyFlag = false;
						break;
					}
				}
			}
			//用户点击的地方有Boss存在
			if(!emptyFlag) {
				//跳过该循环(该坐标)，同时删除objectImgMap中的坐标，不再画图片，即Boss和老窝周围不能被点击
				objectImgMap.remove(e.getKey());
				continue;
			}
			for(int i=(hLevel*5+1);i<=(hLevel+1)*5;i++) {
				for(int j=(vLevel*5+1);j<=(vLevel+1)*5;j++) {
					if("wall".equals(picName)) {
						mainMap[i][j] = Wall.getInstance();
					}else if("water".equals(picName)) {
						mainMap[i][j] = Water.getInstance();
					}else if("grass".equals(picName)) {
						mainMap[i][j] = Grass.getInstance();
					}else if("sand".equals(picName)) {
						mainMap[i][j] = Sand.getInstance();
					}else if("empty".equals(picName)) {
						mainMap[i][j] = Empty.getInstance();
					}
					g.setColor(mainMap[i][j].color);
					g.fill3DRect(j*CELLWIDTH, i*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
				}
			}
		}
		//判断objectMap中是否有该坐标，如果有，则画出对应的地图物体(图片)
		for(Map.Entry<String, String> e : objectImgMap.entrySet()) {
			int hLevel = Integer.parseInt(e.getKey().split(",")[0]);
			int vLevel = Integer.parseInt(e.getKey().split(",")[1]);
			String picName = e.getValue();
			Image image = imgMap.get(picName);
			if(image != null) {
				g.drawImage(imgMap.get(picName), (vLevel*5+1)*CELLWIDTH, (hLevel*5+1)*CELLHEIGHT, 35, 35, this);
			}
		}
	}
	
	/**
	 * 画出指针跟随的阴影和图片
	 * @param g
	 */
	private void drawMask(final Graphics g) {
		if(!"".equals(cursorStatus)) {
		    Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
		    int tempx = point.x-303;
		    int tempy = point.y-25;
		    //判断鼠标是否在map内
		    if(tempy<(HEIGHT-2)*CELLHEIGHT && tempy>CELLHEIGHT && tempx<(WIDTH-2)*CELLWIDTH && tempx>CELLWIDTH) {
		    	int hLevel = (tempy-CELLHEIGHT) / (CELLHEIGHT*5);
		    	int vLevel = (tempx-CELLWIDTH) / (CELLWIDTH*5);
		    	for(int y=hLevel*5+1;y<=(hLevel+1)*5;y++) {
		    		for(int x=vLevel*5+1;x<=(vLevel+1)*5;x++) {
		    			//如果为空，则画阴影
		    			if(MainMap.mainMap[y][x].getName().equals("Empty")) {
		    				MainMap.mainMap[y][x] = new Mask();
		    				g.setColor(mainMap[y][x].color);
		    				g.fill3DRect(x*CELLWIDTH, y*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		    			}
		    		}
		    	}
		    }
		    //将鼠标指针处跟随对应图片
		    g.drawImage(imgMap.get(cursorStatus), tempx, tempy, 35, 35, this);
		    
		}
	}
	
	/**
	 * 判断画笔颜色
	 * @param tank
	 * @param i	纵向坐标
	 * @param j	横向坐标
	 * @return
	 */
	public Color judgeColor(Tank tank, int i, int j) {
		//设置tank前部颜色
		if(i==tank.headYPosition){	
			if(j==(tank.headXPosition-1)||j==(tank.headXPosition+1)) {
				if(tank.direction==Tank.UP||tank.direction==Tank.DOWN) {
					return Tank.COLOR_FRONT;
				}
			}
		}
		if(j==tank.headXPosition){	
			if(i==(tank.headYPosition-1)||i==(tank.headYPosition+1)) {
				if(tank.direction==Tank.RIGHT||tank.direction==Tank.LEFT) {
					return Tank.COLOR_FRONT;
				}
			}
		}

		//设置tank尾部颜色
		if(i==tank.tailYPosition){	
			if(j==(tank.tailXPosition-1)||j==(tank.tailXPosition+1)||j==tank.tailXPosition) {
				if(tank.direction==Tank.UP||tank.direction==Tank.DOWN) {
					return Tank.COLOR_FRONT;
				}
			}
		}
		if(j==tank.tailXPosition){	
			if(i==(tank.tailYPosition-1)||i==(tank.tailYPosition+1)||i==tank.tailYPosition) {
				if(tank.direction==Tank.RIGHT||tank.direction==Tank.LEFT) {
					return Tank.COLOR_FRONT;
				}
			}
		}
		
		//设置炮筒的颜色
		if(i==tank.yPosition && j==tank.xPosition) {
			return Tank.COLOR_PT;
		}
		if(i==((tank.headYPosition+tank.yPosition)/2) && j==((tank.headXPosition+tank.xPosition)/2)) {
			return Tank.COLOR_PT;
		}
		if(i==tank.headYPosition && j==tank.headXPosition){
			return Tank.COLOR_PT;
		}
		return null;
	}
	
	/**
	 * 根据tank类别和方向画tank
	 * @param tank
	 * @param g
	 */
	public void drawTank(Tank tank, Graphics g) {
		if(tank.isExist) {
			if("MyTankL1".equals(tank.getName())) {
				if(tank.direction==Tank.UP) {
					img = img_MyTankL1_UP;
				}else if(tank.direction==Tank.DOWN){
					img = img_MyTankL1_DOWN;
				}else if(tank.direction==Tank.RIGHT){
					img = img_MyTankL1_RIGHT;
				}else if(tank.direction==Tank.LEFT){
					img = img_MyTankL1_LEFT;
				}
			}else if("MyTankL2".equals(tank.getName())) {
				if(tank.direction==Tank.UP) {
					img = img_MyTankL2_UP;
				}else if(tank.direction==Tank.DOWN){
					img = img_MyTankL2_DOWN;
				}else if(tank.direction==Tank.RIGHT){
					img = img_MyTankL2_RIGHT;
				}else if(tank.direction==Tank.LEFT){
					img = img_MyTankL2_LEFT;
				}
			}
			else  if("AntiTankL1".equals(tank.getName())) {
				if(tank.direction==Tank.UP) {
					img = img_AntiTankL1_UP;
				}else if(tank.direction==Tank.DOWN){
					img = img_AntiTankL1_DOWN;
				}else if(tank.direction==Tank.RIGHT){
					img = img_AntiTankL1_RIGHT;
				}else if(tank.direction==Tank.LEFT){
					img = img_AntiTankL1_LEFT;
				}
			}
			
			g.drawImage(img, (tank.xPosition-2)*CELLWIDTH, (tank.yPosition-2)*CELLHEIGHT, 5*CELLWIDTH, 5*CELLHEIGHT, this);
		}
	}
	
	/**
	 * 给按钮添加特定的图片和点击事件
	 * @param panel		panel where cursor is handStatus
	 * @param picName	picture name
	 * @param height	button's height
	 * @return	button	
	 */
	public static JButton generateButton(final JPanel panel, final String picName, int height) {
		JButton button = new JButton();
		button.setBounds(20, height, 35, 34);
		button.setIcon(new ImageIcon(MainMap.class.getResource("/images/"+picName+"Unit.jpg")));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cursorStatus = picName;
				panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		return button;
	}
	
	public static void start() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300,0,MainMap.CELLWIDTH*MainMap.WIDTH+170, MainMap.CELLHEIGHT*MainMap.HEIGHT+30);
		frame.setResizable(false);
		frame.setTitle("坦克大战");
		frame.setLayout(new BorderLayout());
		
		//地图右边的panel
		final JPanel rightPanel = new JPanel(null);
		rightPanel.setPreferredSize(new Dimension(165, 100));
		//将rightPanel添加到窗体中
		frame.add(rightPanel,BorderLayout.EAST);
		//地图主map
		final MainMap map = new MainMap();
		//将map添加到窗体中
		frame.add(map,BorderLayout.CENTER);
		
		//往地图右边的panel中添加图片按钮
		//sand
		rightPanel.add(generateButton(map, "sand", 20));
		//wall
		rightPanel.add(generateButton(map, "wall", 75));
		//water
		rightPanel.add(generateButton(map, "water", 130));
		//grass
		rightPanel.add(generateButton(map, "grass", 185));
		//empty
		rightPanel.add(generateButton(map, "empty", 240));
		
		//diy Button
		JButton diyButton = new JButton("click");
		diyButton.setBounds(20, 295, 50, 34);
		rightPanel.add(diyButton);
		diyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				cursorStatus = "";
				map.requestFocus();
				gameMode = true;
				tankMap.get("MyTankL1").getTank();
				tankMap.get("MyTankL2").getTank();
				tankMap.get("AntiTankL1").getTank();
				
				//map添加键盘键入事件
				map.addKeyListener(new KeyAdapter() {
					
					@Override
					public void keyReleased(KeyEvent e) {
						int code = e.getKeyCode();
						switch(code) 
						{
						case KeyEvent.VK_W:
							KeyStatus.W_STATUS = false;
							break;
						case KeyEvent.VK_S:
							KeyStatus.S_STATUS = false;
							break;
						case KeyEvent.VK_A:
							KeyStatus.A_STATUS = false;
							break;
						case KeyEvent.VK_D:
							KeyStatus.D_STATUS = false;
							break;
						case KeyEvent.VK_SPACE:
							KeyStatus.SPACE_STATUS = false;
							break;
						case KeyEvent.VK_R:
							KeyStatus.R_STATUS = false;
							break;
							
						case KeyEvent.VK_UP:
							KeyStatus.UP_STATUS = false;
							break;
						case KeyEvent.VK_DOWN:
							KeyStatus.DOWN_STATUS = false;
							break;
						case KeyEvent.VK_LEFT:
							KeyStatus.LEFT_STATUS = false;
							break;
						case KeyEvent.VK_RIGHT:
							KeyStatus.RIGHT_STATUS = false;
							break;
						case KeyEvent.VK_J:
							KeyStatus.J_STATUS = false;
							break;
						case KeyEvent.VK_K:
							KeyStatus.K_STATUS = false;
							break;
						}
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						int code = e.getKeyCode();
						switch(code) 
						{
						case KeyEvent.VK_W:
							KeyStatus.W_STATUS = true;
							break;
						case KeyEvent.VK_S:
							KeyStatus.S_STATUS = true;
							break;
						case KeyEvent.VK_A:
							KeyStatus.A_STATUS = true;
							break;
						case KeyEvent.VK_D:
							KeyStatus.D_STATUS = true;
							break;
						case KeyEvent.VK_SPACE:
							KeyStatus.SPACE_STATUS = true;
							break;
						case KeyEvent.VK_R:
							KeyStatus.R_STATUS = true;
							break;
							
						case KeyEvent.VK_UP:
							KeyStatus.UP_STATUS = true;
							break;
						case KeyEvent.VK_DOWN:
							KeyStatus.DOWN_STATUS = true;
							break;
						case KeyEvent.VK_LEFT:
							KeyStatus.LEFT_STATUS = true;
							break;
						case KeyEvent.VK_RIGHT:
							KeyStatus.RIGHT_STATUS = true;
							break;
						case KeyEvent.VK_J:
							KeyStatus.J_STATUS = true;
							break;
						case KeyEvent.VK_K:
							KeyStatus.K_STATUS = true;
							break;
						}
					}
					
				});
			}
		});
		
		//设置定时器，定时refresh frame
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				KeyStatus.act();
				if(gameMode) {
					((AntiTankL1) tankMap.get("AntiTankL1")).autoMove();
				}
				if(!AntiTankL1.getInstance().isExist && AntiTankL1.getInstance().life>0) {
					tankMap.get("AntiTankL1").getTank();
				}
				frame.repaint();
			}
		}, 0,60);
		
		//用户自定义画地图
		map.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int tempy = (e.getY()-CELLHEIGHT)/(CELLHEIGHT*5);
				int tempx = (e.getX()-CELLWIDTH)/(CELLWIDTH*5);
				objectImgMap.put(tempy+","+tempx, cursorStatus);
				//TODO 方便定位到这里
				if("water".equals(cursorStatus) || "grass".equals(cursorStatus )) {
					objectMapCanThrough.put(tempy+","+tempx, cursorStatus);
				}else {
					objectMap.put(tempy+","+tempx, cursorStatus);
					if(objectMapCanThrough.containsKey(tempy+","+tempx)) {
						objectMapCanThrough.remove(tempy+","+tempx);
					}
				}
			}
			
		});
		
		map.setFocusable(true);	//将需要监听的控件获取屏幕焦点
		frame.setVisible(true);
		
	}
	
}
