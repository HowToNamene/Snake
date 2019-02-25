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
//	JFrame frame = new JFrame("̰����");

	// ��ͼ�Ŀ�(����)
	public static final int WIDTH = 40;

	// ��ͼ�ߣ�������
	public static final int HEIGHT = 30;

	// ���ӿ�
	public static final int CELLWIDTH = 20;

	// ���Ӹ�
	public static final int CELLHEIGHT = 20;
	
	// ��ͼ
	private boolean[][] background = new boolean[HEIGHT][WIDTH];

	//ʹ���ĸ�������ʾ�ĸ�����
	public static final int UP_DIRECTION = 1; // ��

	public static final int DOWN_DIRECTION = -1; // ��

	public static final int LEFT_DIRECTION = 2; // ��

	public static final int RIGHT_DIRECTION = -2; // ��
	
	//��ǰ����Ĭ������
	private int currentDrection=-2;
	
	//��Ϸ����״̬
	private boolean isGameOver=false;
	
	// ʹ�ü��ϱ����߽ڵ��������Ϣ
	LinkedList<Point> snake = new LinkedList<Point>();
	
	// ʳ��
	Point food;

	private Thread run;


	// ��ʼ����
	public void initSnake()
	{
		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));
	}

	//����ʳ��
	public void createFood()
	{
		// ����һ�����������
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

	// ��ʳ��
	public boolean eat()
	{
		// ��ȡ��ԭ������ͷ
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
	
	//�ı䵱ǰ����ķ���
	public void isOpositeDirection(int newDirection)
	{
		// �ж��·����Ƿ��뵱ǰ�����Ƿ����෴���򣬲�������ı�
		if (newDirection + currentDrection != 0)
		{
			this.currentDrection = newDirection;
		}
	}
	
	// �����ƶ�
	public void moveUp()
	{
		// ��ȡԭ����ͷ
		Point head = snake.getFirst();
		// ����µ���ͷ
		snake.addFirst(new Point(head.x, head.y - 1));
		// ɾ����β
		snake.removeLast();
	}

	public void move()
	{
		Point head = snake.getFirst();
		// ���Ǹ��ݵ�ǰ�ķ����ƶ���
		switch (currentDrection)
		{
		case UP_DIRECTION:
			// ����µ���ͷ
			snake.addFirst(new Point(head.x, head.y - 1));
//			repaint();
			break;
		case DOWN_DIRECTION:
			// ����µ���ͷ
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
				// ����µ���ͷ
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
				// ����µ���ͷ
				snake.addFirst(new Point(head.x + 1, head.y));
//				repaint();
			}
			break;
		default:
			break;
		}

		if (eat())
		{
			// �Ե���ʳ��
			createFood();
		} else
		{
			// ɾ����β
			snake.removeLast();
		}
	}

	//��Ϸ�����ķ���
	public boolean isGameOver()
	{
		// ײǽ����
		Point head = snake.getFirst();
		if (background[head.y][head.x])
		{
			isGameOver = true;
		}

		// ҧ���Լ� ����
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
	

	
	// ��ʼ����ͼ
	public void initBackground()
	{
		for (int rows = 0; rows < background.length; rows++)
		{
			for (int cols = 0; cols < background[rows].length; cols++)
			{
				if (rows == 0 || rows == (HEIGHT - 1))
				{ // ��һ�С����һ�С� ��һ�������һ��
					background[rows][cols] = true;
				}
			}
		}
	}

	@Override
	public void paint(Graphics g)
	{
//		super.paint(g);
		// ����ͼ
		for (int rows = 0; rows < background.length; rows++)
		{ // rows =0
			for (int cols = 0; cols < background[rows].length; cols++)
			{
				if (background[rows][cols])
				{
					// ʯͷ
					g.setColor(Color.GRAY);
				} else
				{
					// �յ�
					g.setColor(Color.WHITE);
				}
				// ������
				g.fill3DRect(cols * CELLWIDTH, rows * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
			}
		}

		// ����

		// ȡ����ͷ
		Point head = snake.getFirst();
		g.setColor(Color.RED);
		g.fill3DRect(head.x * CELLWIDTH, head.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		// ������
		g.setColor(Color.GREEN);
		for (int i = 1; i < snake.size(); i++)
		{
			Point body = snake.get(i);
			g.fill3DRect(body.x * CELLWIDTH, body.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		}

		// ��ʳ��

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
		final long millis = 300;// ÿ��300����ˢ��һ��
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
		final JFrame frame = new JFrame("̰����");

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

///*�ʼ�

//* �Һ���������֮ǰ�����Ĵ����½�һ���̣߳���move(),repaint()�����̣߳�Ȼ���ڻ�ȡ����¼�����ø��߳�start()����ָı䷽��򱨴��ԭ��
//* ��ʵ������public void keyPressed(KeyEvent e)�е������������ֻ��Ҫ��ǰ�����һ�Σ���Ϊ���ú�����߳̾ͻ�һֱ�ڼ��ʱ������Snake���е�
//* ���ֲ�����ˢ�µ�ͼ����ס����һֱ��ˢ�£�
//* ��main�����е�ʱ���ؾ��ǻ��˻�ȡ����¼�룬�����ı�Snak���е��ߺ�ʳ��Ĳ�����λ�á��ȣ���ֻҪ����һ�ı䣬��ô�������½����߳�ˢ�µ�ͼʱ���߾��Ѿ��ı䷽����		
//*/
