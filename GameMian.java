package saolei;

import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameMian extends JFrame {
	// 说明有多少Block，就是几乘以几。
	final int NUM_OF_BLOCK = 10;
	// 设置方块(Block)数。
	Block[][] block = new Block[NUM_OF_BLOCK][NUM_OF_BLOCK];
	// 创建处理按钮事件的对象
	Deal deal = new Deal();
	Deal1 deal1 = new Deal1();
	final String BOMB = "B";
	public static int jishu = 0;
	boolean die =false;
	
	public GameMian() {
		init();
		showUI();
	}

	// 初始化---先创建出界面的按钮对象，然后对其中一些按钮对象设置成地雷，然后将每个按钮的所对应的周围的雷数计算出来放到按钮对象的isBomb中
	private void init() {
		// 将所有的Block赋予对象
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				block[i][j] = new Block();
				block[i][j].x = i;
				block[i][j].y = j;
			}
		}
		
		// 产生随机数，将Block[随机数]的isBomb设置为“雷”
		block[0][4].setIsBomb(BOMB);
		block[1][0].setIsBomb(BOMB);
		block[5][5].setIsBomb(BOMB);
		block[8][2].setIsBomb(BOMB);
		block[4][4].setIsBomb(BOMB);
		block[6][7].setIsBomb(BOMB);
		block[3][4].setIsBomb(BOMB);
		block[8][1].setIsBomb(BOMB);
		block[8][5].setIsBomb(BOMB);
		block[9][4].setIsBomb(BOMB);
		
		// 将每个Block的isBomb逗设为应有的值
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				if(!(BOMB.equals(block[i][j].isBomb)))
					block[i][j].setIsBomb(findBomb(block, i, j));
			}
		}
	}

	// 绘制界面元素
	private void showUI() {
		// 设置菜单
		MenuBar menuBar = new MenuBar();
		Menu game = new Menu("游戏");
		Menu help = new Menu("帮助");
		MenuItem go = new MenuItem("开始");
		go.addActionListener(deal1);
		MenuItem exit = new MenuItem("退出");
		exit.addActionListener(deal1);
		MenuItem rule = new MenuItem("规则");
		rule.addActionListener(deal1);
		game.add(go);
		game.addSeparator();
		game.add(exit);
		help.add(rule);
		menuBar.add(game);
		menuBar.add(help);
		setMenuBar(menuBar);
		// 添加Block
		setLayout(new GridLayout(NUM_OF_BLOCK, NUM_OF_BLOCK));
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				this.add(block[i][j]);
				block[i][j].addActionListener(deal);
			}
		}
		// 添加一个状态栏，显示所用时间
//		 JPanel stats = new JPanel();
//		 add(stats);

		setVisible(true);
		setSize(NUM_OF_BLOCK * 40, NUM_OF_BLOCK * 40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// 传进一个数组和一个元素的横纵坐标，反悔该元素周围的雷数量
	private String findBomb(Block[][] block, int i, int j) {
		int num = 0;
		try {
			if (BOMB.equals(block[i - 1][j - 1].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i - 1][j].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i - 1][j + 1].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i][j - 1].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i][j + 1].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i + 1][j - 1].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i + 1][j].isBomb))num++;
		} catch (Exception e) {}
		try {
			if (BOMB.equals(block[i + 1][j + 1].isBomb))num++;
		} catch (Exception e) {}
		
		if(num==0){
			return "";
		}
		return num+"";
	}

	// 传进一个数组和isBomb为“”的坐标，将相连的为零的Block全部翻开
	private void openShould(Block[][] block, int i, int j) {
		Queue<Block> queue = new LinkedList<Block>();//待检测数组
		ArrayList<Block> a = new ArrayList<Block>();//已检测数组
		String count = null;
		block[i][j].cheked = true;
		queue.offer(block[i][j]);
		
		while(queue.size()>0){
			Block r = queue.poll();
			count = r.isBomb;
			a.add(r);
			if("".equals(count)){
				//把point周围的八个point放进待检测数组，注意如果已检测数组里面已经有那个point了，就不要把它加到待检测数组里面，不然会死循环
					try {
						if(!block[r.x-1][r.y-1].cheked){
							queue.offer(block[r.x-1][r.y-1]);
							block[r.x-1][r.y-1].cheked = true;
						}
					} catch (Exception e) {
					}
					try {
						if(!block[r.x-1][r.y].cheked){
							queue.offer(block[r.x-1][r.y]);
							block[r.x-1][r.y].cheked = true;
						}
					} catch (Exception e) {
					} 
					try {
						if(!block[r.x-1][r.y+1].cheked){
							queue.offer(block[r.x-1][r.y+1]);
							block[r.x-1][r.y+1].cheked = true;	
						}
					} catch (Exception e) {
					}
					try {
						if(!block[r.x][r.y-1].cheked){
							queue.offer(block[r.x][r.y-1]);
							block[r.x][r.y-1].cheked = true;
						}
					} catch (Exception e) {
					}
					try {
						if(!block[r.x][r.y+1].cheked){
							queue.offer(block[r.x][r.y+1]);
							block[r.x][r.y+1].cheked = true;
						}
					} catch (Exception e) {
					}
					try {
						if(!block[r.x+1][r.y-1].cheked){
							queue.offer(block[r.x+1][r.y-1]);
							block[r.x+1][r.y-1].cheked = true;
						}
					} catch (Exception e) {
					}
					try {
						if(!block[r.x+1][r.y].cheked){
							queue.offer(block[r.x+1][r.y]);
							block[r.x+1][r.y].cheked = true;
						}
					} catch (Exception e) {
					}
					try {
						if(!block[r.x+1][r.y+1].cheked){
							queue.offer(block[r.x+1][r.y+1]);
							block[r.x+1][r.y+1].cheked = true;
						}
					} catch (Exception e) {
					}
			}
		}
		
		for(Block b:a){
			b.changToUp();
		}
		
	}

	// 处理按钮相应的事件，按下按钮后，调用翻开Block的方法，翻开相应的Block
	class Deal implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Block b = (Block)e.getSource();
			if(b.changToUp().equals(BOMB)){
				JOptionPane.showMessageDialog(null, "Lose!");
				die =true;
			}
			else
				openShould(block, b.x, b.y);
			if(jishu==NUM_OF_BLOCK*NUM_OF_BLOCK-5 && die==false){
				JOptionPane.showMessageDialog(null, "Win");
			}
		}
	}
	
	private void remo() {
			for (int i = 0; i < block.length; i++) {
				for (int j = 0; j < block[i].length; j++) {
					this.remove(block[i][j]);
				}
	}}
	
	class Deal1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if("开始".equals(e.getActionCommand())){
				remo();
				init();
				showUI();
			}else if("退出".equals(e.getActionCommand())){
				System.exit(0);
			}else{
				JOptionPane.showMessageDialog(null, "初级为9*9个方块10个雷，中级为16*16个方块40个雷，高级为16*30个方块99个雷");
			}
		}}

	public static void main(String[] args) {
		GameMian gm = new GameMian();
	}
}
