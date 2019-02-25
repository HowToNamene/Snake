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
	
	//��ͼ�Ŀ�(����)
	public static final int WIDTH = 35;
	
	//��ͼ�ߣ�������
	public static final int HEIGHT = 9;	
	
	
	//��ͼ
	private char[][] background = new char[HEIGHT][WIDTH];
	
	//ʹ�ü��ϱ����߽ڵ��������Ϣ
	LinkedList<Point>  snake = new LinkedList<Point>(); 
	
	
	//ʳ��
	Point	food;
	
	//ʹ���ĸ�������ʾ�ĸ�����
	public static final int UP_DIRECTION = 1;  //��
	
	public static final int DOWN_DIRECTION = -1;  //��
	
	public static final int LEFT_DIRECTION = 2;  //��
	
	public static final int RIGHT_DIRECTION =-2;  //��


	//�ߵ�ǰ�ķ���
	int currentDrection = -2; // ��Ĭ������������
	
	
	//��¼��Ϸ�Ƿ����
	static	boolean isGameOver = false; //Ĭ����Ϸû�н����ġ�
	
	
	//���ƶ��ķ���
	public void move(){
		Point head = snake.getFirst();
		//���Ǹ��ݵ�ǰ�ķ����ƶ���
		switch (currentDrection) {
			case UP_DIRECTION:
				//����µ���ͷ
				snake.addFirst(new Point(head.x,head.y-1));
				break;
			case DOWN_DIRECTION:
				//����µ���ͷ
				snake.addFirst(new Point(head.x,head.y+1));
				break;
			case LEFT_DIRECTION:
				if(head.x==0){
					snake.addFirst(new Point(WIDTH-1,head.y));
				}else{
					//����µ���ͷ
					snake.addFirst(new Point(head.x-1,head.y));
				}
				break;
			case RIGHT_DIRECTION:
				if(head.x==WIDTH-1){
					snake.addFirst(new Point(0,head.y));
				}else{
					//����µ���ͷ
					snake.addFirst(new Point(head.x+1,head.y));
				}
				break;
			default:
				break;
		}
		
		if(eat()){
			//�Ե���ʳ��
			createFood();
		}else{
			//ɾ����β
			snake.removeLast(); 		
		}
	}
	
	
	//�ı䵱ǰ����ķ���
	public  void changeDirection(int newDirection){
		//�ж��·����Ƿ��뵱ǰ�����Ƿ����෴���򣬲�������ı�
		if(newDirection+currentDrection!=0){
			this.currentDrection = newDirection;
		}
	}
	
	//����ʳ�� 
	public void createFood(){
		//����һ�����������
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
	
	//��ʾʳ��
	public void showFood(){
		background[food.y][food.x] ='@';
	}
	

	
	//��ʼ����
	public void initSnake(){
		int x = WIDTH/2;
		int y = HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
	}
	
	
	//��ʾ��--->ʵ���Ͼ��ǽ��߽ڵ� ��������Ϣ��������ͼ�ϣ��ڵ�ͼ�ϻ��϶�Ӧ���ַ�����
	public void showSnake(){
	
		//������
		for(int i =1; i<snake.size() ; i++ ){
			Point body = snake.get(i);
			background[body.y][body.x] = '#';
			
		}
		
		//ȡ����ͷ
		Point head = snake.getFirst();
		background[head.y][head.x] = '$';
		
	}
		
	//��ʼ����ͼ
	public void initBackground(){
		for(int rows = 0 ; rows<background.length ; rows++){
			for(int cols = 0  ; cols<background[rows].length ; cols++ ){
				if(rows==0||rows==(HEIGHT-1)){  //��һ�С����һ�С� ��һ�������һ��
					background[rows][cols] = '*';
				}else{
					background[rows][cols] = ' ';
				}
			}
		}
	}

	//��ʾ��ͼ��
	public void showBackground() {
		//��ӡ��ά����
		for(int rows = 0 ; rows<background.length ; rows++){ // rows =0 
			for(int cols = 0  ; cols<background[rows].length ; cols++ ){
				System.out.print(background[rows][cols]);
			}
			System.out.println(); //����
		}
	}
	
	//ˢ����Ϸ ��״̬ 
	public void refrash(){
		//�����Ϸ֮ǰ������״̬��Ϣ
		initBackground();
		//�������µ�״̬��������ͼ��
		showSnake();
		//��ʳ�����Ϣ��������ͼ��ɥ��
		showFood();
		//��ʾ��ǰ��ͼ����Ϣ
		showBackground();
	}
	
	//��ʳ��
	public boolean eat(){
		//��ȡ��ԭ������ͷ
		Point head = snake.getFirst();
		if(head.equals(food)){
			return true; 
		}
		return false;
	}
		
	//��Ϸ�����ķ���
	public void isGameOver(){
		//ײǽ����
		Point head = snake.getFirst();
		if(background[head.y][head.x]=='*'){
			isGameOver = true;
		}
		
		//ҧ���Լ�  ����
		for(int i = 1; i<snake.size() ; i++){
			Point body = snake.get(i);
			if(head.equals(body)){
				isGameOver = true;
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		final SnakeGame snake = new SnakeGame();
		//��ʼ����ͼ
		snake.initBackground();
		//��ʼ����
		snake.initSnake(); 
		//���ߵ���Ϣ��������ͼ��
		snake.showSnake();
		
		//��ʼ������
		snake.createFood();
		//��ʾʳ��
		snake.showFood();
		snake.showBackground();
		
		
		JFrame frame = new JFrame("������");
		frame.add(new JButton("��"),BorderLayout.NORTH);
		frame.add(new JButton("��"),BorderLayout.SOUTH);
		frame.add(new JButton("��"),BorderLayout.WEST);
		frame.add(new JButton("��"),BorderLayout.EAST);
		JButton button = new JButton("������Ʒ���");
		frame.add(button);
		FrameUtil.initFrame(frame, 300, 300);
		
		//����ť����¼�������
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
				snake.isGameOver();//�ж��Ƿ���Ϸ����
				snake.refrash();
				if(isGameOver){
					System.out.println("��Ϸ������..");
					System.exit(0);
				}
			}
		});
		
		
		
		
		
	}

}
