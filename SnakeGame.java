package snake.view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import snake.util.FrameUtil;

public class SnakeGame {
	
	//地图的宽(列数)
	public static final int WIDTH = 35;
	
	//地图高（行数）
	public static final int HEIGHT = 9;	
	
	
	//地图
	private char[][] background = new char[HEIGHT][WIDTH];
	
	//使用集合保存蛇节点的所有信息
	LinkedList<Point>  snake = new LinkedList<Point>(); 
	
	
	//食物
	Point	food;
	
	//使用四个常量表示四个方向
	public static final int UP_DIRECTION = 1;  //上
	
	public static final int DOWN_DIRECTION = -1;  //下
	
	public static final int LEFT_DIRECTION = 2;  //左
	
	public static final int RIGHT_DIRECTION =-2;  //右


	//蛇当前的方向
	int currentDrection = -2; // 蛇默认是向右行走
	
	
	//记录游戏是否结束
	static	boolean isGameOver = false; //默认游戏没有结束的。
	
	
	//蛇移动的方法
	public void move(){
		Point head = snake.getFirst();
		//蛇是根据当前的方向移动的
		switch (currentDrection) {
			case UP_DIRECTION:
				//添加新的蛇头
				snake.addFirst(new Point(head.x,head.y-1));
				break;
			case DOWN_DIRECTION:
				//添加新的蛇头
				snake.addFirst(new Point(head.x,head.y+1));
				break;
			case LEFT_DIRECTION:
				if(head.x==0){
					snake.addFirst(new Point(WIDTH-1,head.y));
				}else{
					//添加新的蛇头
					snake.addFirst(new Point(head.x-1,head.y));
				}
				break;
			case RIGHT_DIRECTION:
				if(head.x==WIDTH-1){
					snake.addFirst(new Point(0,head.y));
				}else{
					//添加新的蛇头
					snake.addFirst(new Point(head.x+1,head.y));
				}
				break;
			default:
				break;
		}
		
		if(eat()){
			//吃到了食物
			createFood();
		}else{
			//删除蛇尾
			snake.removeLast(); 		
		}
	}
	
	
	//改变当前方向的方法
	public  void changeDirection(int newDirection){
		//判断新方向是否与当前方向是否是相反方向，才允许其改变
		if(newDirection+currentDrection!=0){
			this.currentDrection = newDirection;
		}
	}
	
	//生成食物 
	public void createFood(){
		//创建一个随机数对象
		Random random = new Random();
		while(true){
			int x = random.nextInt(WIDTH); 
			int y = random.nextInt(HEIGHT);
			if(background[y][x]!='*'){
				food = new Point(x,y);
				break;
			}
		}
	}
	
	//显示食物
	public void showFood(){
		background[food.y][food.x] ='@';
	}
	

	
	//初始化蛇
	public void initSnake(){
		int x = WIDTH/2;
		int y = HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
	}
	
	
	//显示蛇--->实际上就是将蛇节点 的坐标信息反馈到地图上，在地图上画上对应的字符而已
	public void showSnake(){
	
		//画蛇身
		for(int i =1; i<snake.size() ; i++ ){
			Point body = snake.get(i);
			background[body.y][body.x] = '#';
			
		}
		
		//取出蛇头
		Point head = snake.getFirst();
		background[head.y][head.x] = '$';
		
	}
		
	//初始化地图
	public void initBackground(){
		for(int rows = 0 ; rows<background.length ; rows++){
			for(int cols = 0  ; cols<background[rows].length ; cols++ ){
				if(rows==0||rows==(HEIGHT-1)){  //第一行、最后一行、 第一列与最后一列
					background[rows][cols] = '*';
				}else{
					background[rows][cols] = ' ';
				}
			}
		}
	}

	//显示地图的
	public void showBackground() {
		//打印二维数组
		for(int rows = 0 ; rows<background.length ; rows++){ // rows =0 
			for(int cols = 0  ; cols<background[rows].length ; cols++ ){
				System.out.print(background[rows][cols]);
			}
			System.out.println(); //换行
		}
	}
	
	//刷新游戏 的状态 
	public void refrash(){
		//清空游戏之前的所有状态信息
		initBackground();
		//把蛇最新的状态反馈到地图上
		showSnake();
		//把食物的信息反馈到地图婚丧。
		showFood();
		//显示当前地图的信息
		showBackground();
	}
	
	//吃食物
	public boolean eat(){
		//获取到原来的蛇头
		Point head = snake.getFirst();
		if(head.equals(food)){
			return true; 
		}
		return false;
	}
		
	//游戏结束的方法
	public void isGameOver(){
		//撞墙死亡
		Point head = snake.getFirst();
		if(background[head.y][head.x]=='*'){
			isGameOver = true;
		}
		
		//咬到自己  蛇身
		for(int i = 1; i<snake.size() ; i++){
			Point body = snake.get(i);
			if(head.equals(body)){
				isGameOver = true;
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		final SnakeGame snake = new SnakeGame();
		//初始化地图
		snake.initBackground();
		//初始化蛇
		snake.initSnake(); 
		//把蛇的信息反馈到地图上
		snake.showSnake();
		
		//初始化事物
		snake.createFood();
		//显示食物
		snake.showFood();
		snake.showBackground();
		
		
		JFrame frame = new JFrame("方向盘");
		frame.add(new JButton("↑"),BorderLayout.NORTH);
		frame.add(new JButton("↓"),BorderLayout.SOUTH);
		frame.add(new JButton("←"),BorderLayout.WEST);
		frame.add(new JButton("→"),BorderLayout.EAST);
		JButton button = new JButton("点击控制方向");
		frame.add(button);
		FrameUtil.initFrame(frame, 300, 300);
		
		//给按钮添加事件监听器
		button.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
					case KeyEvent.VK_UP:
						snake.changeDirection(UP_DIRECTION);
						break;
					case KeyEvent.VK_DOWN:
						snake.changeDirection(DOWN_DIRECTION);
						break;
					case KeyEvent.VK_LEFT:
						snake.changeDirection(LEFT_DIRECTION);
						break;
					case KeyEvent.VK_RIGHT:
						snake.changeDirection(RIGHT_DIRECTION);
						break;
					default:
						break;
				}
				snake.move();
				snake.isGameOver();//判断是否游戏结束
				snake.refrash();
				if(isGameOver){
					System.out.println("游戏结束了..");
					System.exit(0);
				}
			}
		});
		
		
		
		
		
	}

}
