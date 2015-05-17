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
	// ˵���ж���Block�����Ǽ����Լ���
	final int NUM_OF_BLOCK = 10;
	// ���÷���(Block)����
	Block[][] block = new Block[NUM_OF_BLOCK][NUM_OF_BLOCK];
	// ��������ť�¼��Ķ���
	Deal deal = new Deal();
	Deal1 deal1 = new Deal1();
	final String BOMB = "B";
	public static int jishu = 0;
	boolean die =false;
	
	public GameMian() {
		init();
		showUI();
	}

	// ��ʼ��---�ȴ���������İ�ť����Ȼ�������һЩ��ť�������óɵ��ף�Ȼ��ÿ����ť������Ӧ����Χ��������������ŵ���ť�����isBomb��
	private void init() {
		// �����е�Block�������
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				block[i][j] = new Block();
				block[i][j].x = i;
				block[i][j].y = j;
			}
		}
		
		// �������������Block[�����]��isBomb����Ϊ���ס�
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
		
		// ��ÿ��Block��isBomb����ΪӦ�е�ֵ
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				if(!(BOMB.equals(block[i][j].isBomb)))
					block[i][j].setIsBomb(findBomb(block, i, j));
			}
		}
	}

	// ���ƽ���Ԫ��
	private void showUI() {
		// ���ò˵�
		MenuBar menuBar = new MenuBar();
		Menu game = new Menu("��Ϸ");
		Menu help = new Menu("����");
		MenuItem go = new MenuItem("��ʼ");
		go.addActionListener(deal1);
		MenuItem exit = new MenuItem("�˳�");
		exit.addActionListener(deal1);
		MenuItem rule = new MenuItem("����");
		rule.addActionListener(deal1);
		game.add(go);
		game.addSeparator();
		game.add(exit);
		help.add(rule);
		menuBar.add(game);
		menuBar.add(help);
		setMenuBar(menuBar);
		// ���Block
		setLayout(new GridLayout(NUM_OF_BLOCK, NUM_OF_BLOCK));
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				this.add(block[i][j]);
				block[i][j].addActionListener(deal);
			}
		}
		// ���һ��״̬������ʾ����ʱ��
//		 JPanel stats = new JPanel();
//		 add(stats);

		setVisible(true);
		setSize(NUM_OF_BLOCK * 40, NUM_OF_BLOCK * 40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// ����һ�������һ��Ԫ�صĺ������꣬���ڸ�Ԫ����Χ��������
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

	// ����һ�������isBombΪ���������꣬��������Ϊ���Blockȫ������
	private void openShould(Block[][] block, int i, int j) {
		Queue<Block> queue = new LinkedList<Block>();//���������
		ArrayList<Block> a = new ArrayList<Block>();//�Ѽ������
		String count = null;
		block[i][j].cheked = true;
		queue.offer(block[i][j]);
		
		while(queue.size()>0){
			Block r = queue.poll();
			count = r.isBomb;
			a.add(r);
			if("".equals(count)){
				//��point��Χ�İ˸�point�Ž���������飬ע������Ѽ�����������Ѿ����Ǹ�point�ˣ��Ͳ�Ҫ�����ӵ�������������棬��Ȼ����ѭ��
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

	// ����ť��Ӧ���¼������°�ť�󣬵��÷���Block�ķ�����������Ӧ��Block
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
			if("��ʼ".equals(e.getActionCommand())){
				remo();
				init();
				showUI();
			}else if("�˳�".equals(e.getActionCommand())){
				System.exit(0);
			}else{
				JOptionPane.showMessageDialog(null, "����Ϊ9*9������10���ף��м�Ϊ16*16������40���ף��߼�Ϊ16*30������99����");
			}
		}}

	public static void main(String[] args) {
		GameMian gm = new GameMian();
	}
}
