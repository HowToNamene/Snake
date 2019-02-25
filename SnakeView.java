package snake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JPanel;

import snake.util.FrameUtil;

class Snake extends JPanel
{
//	JFrame frame = new JFrame("贪吃蛇");

	// 地图的宽(列数)
	public static final int WIDTH = 40;

	// 地图高（行数）
	public static final int HEIGHT = 30;

	// 格子宽
	public static final int CELLWIDTH = 20;

	// 格子高
	public static final int CELLHEIGHT = 20;
	
	// 地图
	private boolean[][] background = new boolean[HEIGHT][WIDTH];

	//使用四个常量表示四个方向
	public static final int UP_DIRECTION = 1; // 上

	public static final int DOWN_DIRECTION = -1; // 下

	public static final int LEFT_DIRECTION = 2; // 左

	public static final int RIGHT_DIRECTION = -2; // 右
	
	//当前方向，默认向右
	private int currentDrection=-2;
	
	//游戏结束状态
	private boolean isGameOver=false;
	
	// 使用集合保存蛇节点的所有信息
	LinkedList<Point> snake = new LinkedList<Point>();
	
	// 食物
	Point food;

	private Thread run;


	// 初始化蛇
	public void initSnake()
	{
		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));
	}

	//产生食物
	public void createFood()
	{
		// 创建一个随机数对象
		Random random = new Random();
		while (true)
		{
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			if (!background[y][x])
			{
				food = new Point(x, y);
				break;
			}
		}
	}

	// 吃食物
	public boolean eat()
	{
		// 获取到原来的蛇头
		Point head = snake.getFirst();
		if (head.equals(food))
		{
			return true;
		}
		return false;
	}

	public void changeDirection(int direction)
	{
		switch (direction)
		{
		case KeyEvent.VK_UP:
			isOpositeDirection(UP_DIRECTION);
			break;
		case KeyEvent.VK_DOWN:
			isOpositeDirection(DOWN_DIRECTION);
			break;
		case KeyEvent.VK_LEFT:
			isOpositeDirection(LEFT_DIRECTION);
			break;
		case KeyEvent.VK_RIGHT:
			isOpositeDirection(RIGHT_DIRECTION);
			break;
		default:
			break;
		}
	}
	
	//改变当前方向的方法
	public void isOpositeDirection(int newDirection)
	{
		// 判断新方向是否与当前方向是否是相反方向，才允许其改变
		if (newDirection + currentDrection != 0)
		{
			this.currentDrection = newDirection;
		}
	}
	
	// 向上移动
	public void moveUp()
	{
		// 获取原来蛇头
		Point head = snake.getFirst();
		// 添加新的蛇头
		snake.addFirst(new Point(head.x, head.y - 1));
		// 删除蛇尾
		snake.removeLast();
	}

	public void move()
	{
		Point head = snake.getFirst();
		// 蛇是根据当前的方向移动的
		switch (currentDrection)
		{
		case UP_DIRECTION:
			// 添加新的蛇头
			snake.addFirst(new Point(head.x, head.y - 1));
//			repaint();
			break;
		case DOWN_DIRECTION:
			// 添加新的蛇头
			snake.addFirst(new Point(head.x, head.y + 1));
//			System.out.println(head.y);
//			repaint(100);
			break;
		case LEFT_DIRECTION:
			if (head.x == 0)
			{
				snake.addFirst(new Point(WIDTH - 1, head.y));
//				repaint();
			} else
			{
				// 添加新的蛇头
				snake.addFirst(new Point(head.x - 1, head.y));
//				repaint();
			}
			break;
		case RIGHT_DIRECTION:
			if (head.x == WIDTH - 1)
			{
				snake.addFirst(new Point(0, head.y));
//				repaint();
			} else
			{
				// 添加新的蛇头
				snake.addFirst(new Point(head.x + 1, head.y));
//				repaint();
			}
			break;
		default:
			break;
		}

		if (eat())
		{
			// 吃到了食物
			createFood();
		} else
		{
			// 删除蛇尾
			snake.removeLast();
		}
	}

	//游戏结束的方法
	public boolean isGameOver()
	{
		// 撞墙死亡
		Point head = snake.getFirst();
		if (background[head.y][head.x])
		{
			isGameOver = true;
		}

		// 咬到自己 蛇身
		for (int i = 1; i < snake.size(); i++)
		{
			Point body = snake.get(i);
			if (head.equals(body))
			{
				isGameOver = true;
			}
		}
		return isGameOver;
	}
	

	
	// 初始化地图
	public void initBackground()
	{
		for (int rows = 0; rows < background.length; rows++)
		{
			for (int cols = 0; cols < background[rows].length; cols++)
			{
				if (rows == 0 || rows == (HEIGHT - 1))
				{ // 第一行、最后一行、 第一列与最后一列
					background[rows][cols] = true;
				}
			}
		}
	}

	@Override
	public void paint(Graphics g)
	{
//		super.paint(g);
		// 画地图
		for (int rows = 0; rows < background.length; rows++)
		{ // rows =0
			for (int cols = 0; cols < background[rows].length; cols++)
			{
				if (background[rows][cols])
				{
					// 石头
					g.setColor(Color.GRAY);
				} else
				{
					// 空地
					g.setColor(Color.WHITE);
				}
				// 画矩形
				g.fill3DRect(cols * CELLWIDTH, rows * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
			}
		}

		// 画蛇

		// 取出蛇头
		Point head = snake.getFirst();
		g.setColor(Color.RED);
		g.fill3DRect(head.x * CELLWIDTH, head.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		// 画蛇身
		g.setColor(Color.GREEN);
		for (int i = 1; i < snake.size(); i++)
		{
			Point body = snake.get(i);
			g.fill3DRect(body.x * CELLWIDTH, body.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		}

		// 画食物

		g.setColor(Color.RED);
		g.fill3DRect(food.x * CELLHEIGHT, food.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
	}

	public void init()
	{
		initBackground();
		initSnake();
		createFood();
	}
	
	public void Thread()
	{
		final long millis = 300;// 每隔300毫秒刷新一次
		run = new Thread()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(millis);
					} catch (InterruptedException ex)
					{
						ex.printStackTrace();
					}

					move();
					repaint();
					if (isGameOver())
					{
						System.out.println("game over");
						System.exit(0);
					}
				}
			}
		};
		run.start();
	}
	
	
}

class SnakeStart
{
	public void start()
	{
		final JFrame frame = new JFrame("贪吃蛇");

		final Snake snake = new Snake();

		snake.init();
		frame.add(snake);
		FrameUtil.initFrame(frame, snake.WIDTH * snake.CELLWIDTH + 20, snake.HEIGHT * snake.CELLHEIGHT + 35);
		snake.Thread();
		frame.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				int code = e.getKeyCode();				
				snake.changeDirection(code);				
			}
		});
	}
}

public class SnakeView
{
	public static void main (String[] args)
	{
		SnakeStart snakeStart=new SnakeStart();
		snakeStart.start();
		}
}

///*笔记

//* 我好像明白了之前发生的错误：新建一个线程，将move(),repaint()放入线程，然后在获取键盘录入后调用该线程start()会出现改变方向或报错的原因。
//* 其实不用在public void keyPressed(KeyEvent e)中调用这个函数，只需要在前面调用一次，因为调用后这个线程就会一直在间隔时间后根据Snake类中的
//* 各种参数来刷新地图，记住：是一直在刷新，
//* 而main函数中的时间监控就是回了获取键盘录入，进而改变Snak类中的蛇和食物的参数（位置、等），只要参数一改变，那么等上述新建的线程刷新地图时，蛇就已经改变方向了		
//*/
