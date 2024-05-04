package Sample_Testing;
import java.util.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
 
public class tictactoe_game extends JFrame implements ActionListener
{

	//Here global variables are declared w.r.t the game
	private JLabel title, score_title;
	private JButton newBtn;
	private JRadioButton oButton, xButton, compButton, humanButton, easyBtn, medBtn, hardBtn;
	private boolean humanFirst = true;
	private JPanel BoardPanel, TextPanel, ButtonPanel;
	private int DiffLvl = 2;//The variable that alters the level of lookahead and the sophistication of the strategy employed when the computer plays as the first mover.
	private int Lookahead = 2;//A variable that stores the depth of the lookahead the minimax algorithm will perform.
	private int count = 0;//Variable responsible for monitoring the recursive lookahead iterations conducted.
	private int xscore  = 0;//Human score
	private int yscore = 0;//Computer score
	int[] win = new int[4];
	public boolean won = false;		
	char computerSelect = 'O';//Default selection for computer case.			
	char humanSelect = 'X';//Default selection for human case.
	Btn_tictactoe[] finalwin = new Btn_tictactoe[4];
	private char Board_Panel[][][];				
	private Btn_tictactoe[][][] accessBtn;
	
	
	/*The provided code defines a private class named `Btn_tictactoe`, which extends the JButton class. 
	These variables are used to store and represent values related to rows, columns, and the board panel for a Tic-Tac-Toe game.*/
	private class Btn_tictactoe extends JButton
	{
		public int boxRow;
		public int bocCols;
		public int boxPanelBoard;
	}

	/*The below code represents a public class named `Dimensions_of_Board`. 
	These variables likely store information related to the dimensions, rows, and columns of a game board, possibly for a game like Tic-Tac-Toe game.*/
	public class Dimensions_of_Board
	{
		int boardDim;
		int rowBoard;
		int colBoard;
	}

	/*The below code is a constructor which provides us the board settings.*/
	public tictactoe_game()
	{
		super("Tic-Tac-Toe Game!");
		setSize(700, 1000);
		Board_Panelting();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

    /*The provided code defines a class named designBoard that extends JPanel. 
	This class overrides the paintComponent method to draw a Tic-Tac-Toe board and visual indicators for potential winning positions.
	The drawing of the lines defines the layout of the game boards and displays the potential winning positions when the game is won.*/
	public class designBoard extends JPanel
	{
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));

			//Board 0
			g2.drawLine(50, 95, 250, 95);
			g2.drawLine(25, 137, 225, 137);
			g2.drawLine(10, 179, 210, 179);
			g2.drawLine(110, 60, 35, 210);
			g2.drawLine(160, 60, 85, 210);
		    g2.drawLine(210, 60, 135, 210);
			
			//Board 1
			g2.drawLine(50, 270, 250, 270);
			g2.drawLine(25, 312, 225, 312);
			g2.drawLine(10, 354, 210, 354);
			g2.drawLine(120, 240, 45, 384);
			g2.drawLine(170, 240, 95, 384);
			g2.drawLine(220, 240, 145, 384);

			//Board 2
			g2.drawLine(50, 445, 250, 445);
			g2.drawLine(25, 487, 225, 487);
			g2.drawLine(10, 529, 210, 529);
			g2.drawLine(120, 410, 45, 562);
			g2.drawLine(170, 410, 95, 562);
			g2.drawLine(220, 410, 145, 562);

			//Board 3
			g2.drawLine(50, 622, 250, 622);
			g2.drawLine(25, 664, 225, 664);
			g2.drawLine(10, 706, 210, 706);
			g2.drawLine(120, 590, 45, 740);
			g2.drawLine(170, 590, 95, 740);
			g2.drawLine(220, 590, 145,740);

			
			if(won)
			{ //Draws a green line between the initial and final winning positions, marking the location of the potential win.
				g2.setColor(Color.GREEN);
				g2.drawLine(finalwin[0].getBounds().x + 27, finalwin[0].getBounds().y + 20,
						finalwin[3].getBounds().x + 27, finalwin[3].getBounds().y + 20);
			}
		}
	}

	/*The method public void Board_Panelting() likely creates or configures the game board panel for the Tic-Tac-Toe game.  
	Typically, methods prefixed with "Board_Panelting()" might handle tasks related to setting up the visual representation of the game board, initializing the board's layout, adding components or panels, setting sizes or positions, or performing other necessary configurations specific to the game board interface.
	*/
	public void Board_Panelting()
	{
		//Arrays creation to represent the game.
		Board_Panel = new char[4][4][4];
		accessBtn = new Btn_tictactoe[4][4][4];

		BoardPanel = new designBoard();
		ButtonPanel = new JPanel();
		TextPanel = new JPanel();

		newBtn = new JButton("New Game");
		newBtn.setFont(new Font("Arial", Font.PLAIN, 15));
		newBtn.setFocusPainted(false);
		newBtn.setForeground(Color.BLACK);
		newBtn.setBounds(350, 600, 130, 30);
		newBtn.addActionListener(new ListenerClass());
		newBtn.setName("newBtn");
		newBtn.setBackground(Color.lightGray);

		xButton = new JRadioButton("X", true);
		oButton = new JRadioButton("O");
		oButton.setBackground(Color.lightGray);
		xButton.setBackground(Color.lightGray);
		oButton.setForeground(Color.BLACK);
		xButton.setForeground(Color.BLACK);
		oButton.setFocusPainted(false);
		xButton.setFocusPainted(false);
		xButton.setBounds(350, 420, 50, 50);
		oButton.setBounds(400, 420, 50, 50);

		ButtonGroup xoSelect = new ButtonGroup();
		xoSelect.add(xButton);
		xoSelect.add(oButton);

		Listener_for_Movements list_1 = new Listener_for_Movements();
		xButton.addActionListener(list_1);
		oButton.addActionListener(list_1);

		//Who gonna play first either computer or human.
		humanButton = new JRadioButton("Human First", true);
		compButton = new JRadioButton("Computer First");
		compButton.setBackground(Color.lightGray);
		humanButton.setBackground(Color.lightGray);
		compButton.setForeground(Color.BLACK);
		humanButton.setForeground(Color.BLACK);
		compButton.setFocusPainted(false);
		humanButton.setFocusPainted(false);
		humanButton.setBounds(350, 210, 150, 40);
		compButton.setBounds(350, 180, 150, 40);

		ButtonGroup firstSelect = new ButtonGroup();
		firstSelect.add(compButton);
		firstSelect.add(humanButton);

		Listener_For_BoardPanel list_2 = new Listener_For_BoardPanel();
		compButton.addActionListener(list_2);
		humanButton.addActionListener(list_2);

		//Level of difficulty of the game. (By default, it is set to easy)
		easyBtn = new JRadioButton("Easy",true);
		medBtn = new JRadioButton("Medium");
		hardBtn = new JRadioButton("Hard");
		easyBtn.setBackground(Color.lightGray);
		medBtn.setBackground(Color.lightGray);
		hardBtn.setBackground(Color.lightGray);
		easyBtn.setBounds(350, 290, 150, 40);
		medBtn.setBounds(350, 320, 150, 40);
		hardBtn.setBounds(350, 350, 150, 40);

		ButtonGroup grp_btn = new ButtonGroup();
		grp_btn.add(easyBtn);
		grp_btn.add(medBtn);
		grp_btn.add(hardBtn);

		Listener_for_Levels list_3 = new Listener_for_Levels();
		easyBtn.addActionListener(list_3);
		medBtn.addActionListener(list_3);
		hardBtn.addActionListener(list_3);

		title = new JLabel("Welcome to 3D Tic Tac Toe Game!!");
		title.setFont(new Font("Helvetica", Font.PLAIN, 19));
		title.setBackground(Color.WHITE);
		title.setForeground(Color.BLUE);
		title.setOpaque(true);

		score_title = new JLabel("You: " + xscore + "   Computer: " + yscore);
		score_title.setFont(new Font("Helvetica", Font.PLAIN, 12));
		score_title.setBackground(Color.WHITE);
		score_title.setForeground(Color.black);
		score_title.setOpaque(true);

		//Variables used for monitoring the present button being positioned.
		int b_num = 0;
		int rowCount = 0;
		int colsCount = 0;
		int boardPanelCount = 0;

		//Variables used to define the positions of the TicTacToeButtons when they are arranged using loops.
		int rowMoves = 25;
		int rowStart = 50;
		int xloc = 49;
		int yloc = 43;
		int width = 60;
		int height = 50;

		//BoardPanel loop
		int i=0;
		while(i<=3)
		{
			//Row loop
			int j=0;
			while(j<=3)
			{
				//Column loop
				int k=0;
				while(k<=3)
				{
					//Creating the new button, setting it to be empty in both arrays
					Board_Panel[i][j][k] = '-';
					accessBtn[i][j][k] = new Btn_tictactoe();
					accessBtn[i][j][k].setFont(new Font("Arial Bold", Font.ITALIC, 20));
					accessBtn[i][j][k].setText("");

					//Making it transparent and add
					accessBtn[i][j][k].setContentAreaFilled(false);
					accessBtn[i][j][k].setBorderPainted(false);
					accessBtn[i][j][k].setFocusPainted(false);

					//Placing the button
					accessBtn[i][j][k].setBounds(xloc, yloc, width, height);

					//Setting information variables
					accessBtn[i][j][k].setName(Integer.toString(boardPanelCount));
					accessBtn[i][j][k].boxPanelBoard = b_num;
					accessBtn[i][j][k].boxRow = rowCount;
					accessBtn[i][j][k].bocCols = colsCount;

					//Adding action listener
					accessBtn[i][j][k].addActionListener(this);

					//Bump the column number 1, move the position that the next button will be placed to the right, and add the current button to the panel
					colsCount++;
					boardPanelCount++;
					xloc += 49;
					getContentPane().add(accessBtn[i][j][k]);
					k++;
				}

				//Reseting column number
				colsCount = 0;
				rowCount++;
				xloc = rowStart -= rowMoves;
				yloc += 44;
				j++;
			}

			//Reseting row numbers and row shifts
			rowCount = 0;
			rowMoves = 26;
			rowStart = 58;
			b_num++;
			xloc = rowStart;
			yloc += 2;
			i++;
		}

		//Panel setup
		BoardPanel.setVisible(true);
		BoardPanel.setBackground(Color.WHITE);
		TextPanel.setVisible(true);
		ButtonPanel.setVisible(true);
		title.setVisible(true);

		TextPanel.setLayout(new GridLayout(2,1));
		TextPanel.add(title);
		TextPanel.add(score_title);
		TextPanel.setBounds(80, 0, 400, 30);

		add(xButton);
		add(oButton);
		add(humanButton);
		add(compButton);
		add(easyBtn);
		add(medBtn);
		add(hardBtn);
		add(newBtn);
		add(TextPanel);
		add(BoardPanel);


		setVisible(true);
	}

	/*The below code handles user interactions, such as button clicks, and performs actions accordingly,
	including setting up the game environment and determining the first mover (human or computer) based on the user's choice.*/
	class Listener_For_BoardPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("Good luck!");

			if(compButton.isSelected())
			{
				humanFirst = false;

				if(!hardBtn.isSelected())
					random_movement_by_cpu();
				else
					movement_by_cpu();
			}
			else
			{
				humanFirst = true;
			}
		}
	}

	/*This code snippet appears to handle actions triggered by an event (likely a button click or user interaction).
	It erases the board, updates the display message, and initiates the computer's move based on certain conditions
	(such as the value of humanFirst and DiffLvl).*/
	class ListenerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("Good luck!");

			if(!humanFirst)
			{
				if(DiffLvl == 3)
					movement_by_cpu();
				else
					random_movement_by_cpu();
			}
		}
	}

	/*This code segment seems to handle user actions, likely associated with selecting a game piece ('X' or 'O') and initiating the game based on certain conditions,
	including the selected piece and the level of difficulty (DiffLvl).*/
	class Listener_for_Movements implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("Good luck!");

			if(xButton.isSelected())
			{
				humanSelect = 'X';
				computerSelect = 'O';
			}
			else
			{
				humanSelect = 'O';
				computerSelect = 'X';
			}

			if(!humanFirst)
			{
				if(DiffLvl == 3)
					movement_by_cpu();
				else
					random_movement_by_cpu();
			}
		}
	}

	/*This code appears to handle user interaction related to selecting difficulty levels for the game. 
	It sets the difficulty level and lookahead values accordingly and triggers specific actions based on these selections and the value of humanFirst.*/
	class Listener_for_Levels implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("Good luck!");

			if(easyBtn.isSelected())
			{
				DiffLvl = 1;
				Lookahead = 1;
			}
			else if(medBtn.isSelected())
			{
				DiffLvl = 2;
				Lookahead = 2;
			}
			else
			{
				DiffLvl = 3;
				Lookahead = 6;
			}
			if(!humanFirst)
			{
				if(DiffLvl == 3)
					movement_by_cpu();
				else
					random_movement_by_cpu();
			}
		}
	}

	/*The provided code defines a method named erase_BoardPanel() 
	responsible for clearing the game board and resetting certain variables associated with the game state.*/
	public void erase_BoardPanel()
	{
		repaint();
		won = false;
		count = 0;

		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 3; j++)
			{
				for(int k = 0; k <= 3; k++)
				{
					Board_Panel[i][j][k] = '-';
					accessBtn[i][j][k].setText("");
					accessBtn[i][j][k].setEnabled(true);
				}
			}
		}
		win = new int[4];
	}

	/*This method appears to identify specific buttons based on win conditions, disables certain buttons that do not meet these conditions, 
	changes the color of some buttons, and stores the qualifying buttons in the finalwin array. 
	This is likely done to indicate winning positions or to highlight elements related to a win in the game.*/
	public void BoardPanel_game_stop() {
		int pointer = 0;
		int i = 0, j = 0, k = 0;
	
		while (i <= 3) {
			j = 0;
			while (j <= 3) {
				k = 0;
				while (k <= 3) {
					if (checks_for_win_content(win, Integer.parseInt(accessBtn[i][j][k].getName()))) {
						accessBtn[i][j][k].setEnabled(true);
						accessBtn[i][j][k].setForeground(Color.GRAY);
						finalwin[pointer] = accessBtn[i][j][k];
						pointer++;
					} else {
						accessBtn[i][j][k].setEnabled(false);
					}
					k++;
				}
				j++;
			}
			i++;
		}
		repaint();
	}
	
	/*This code snippet handles the processing of a user's move in the game, checks for a win condition, updates the interface accordingly, 
	and initiates the computer's move if no win occurs.*/
	public void actionPerformed(ActionEvent e)
	{
		Btn_tictactoe button = (Btn_tictactoe)e.getSource();
		Board_Panel[button.boxPanelBoard][button.boxRow][button.bocCols] = humanSelect;
		accessBtn[button.boxPanelBoard][button.boxRow][button.bocCols].setText(Character.toString(humanSelect));
		accessBtn[button.boxPanelBoard][button.boxRow][button.bocCols].setEnabled(false);

		Dimensions_of_Board newMove = new Dimensions_of_Board();
		newMove.boardDim = button.boxPanelBoard;
		newMove.rowBoard = button.boxRow;
		newMove.colBoard = button.bocCols;

		if(Win_Meth(humanSelect, newMove))
		{
			title.setText("You beat me! Click on new game.");
			title.setForeground(Color.GRAY);
			xscore++;
			won = true;
			BoardPanel_game_stop();
			score_BoardPanel();
		}
		else
		{
			movement_by_cpu();
		}
	}

	public void score_BoardPanel()
	{
		score_title.setText("You: " + xscore + "    Computer: " + yscore);
	}

	private boolean checks_for_win_content(int[] a, int k)
	{
		for (int index = 0; index < a.length; index++) {
			int i = a[index];
			if (k == i)
				return true;
		}		
		return false;
	}

	/*This method randomly selects a position on the game board and makes a move for the computer by updating 
	the game board state and corresponding graphical representation.*/
	private void random_movement_by_cpu()
	{
		Random random = new Random();
		int r = random.nextInt(4);
		int c = random.nextInt(4);
		int b = random.nextInt(4);
		Board_Panel[b][r][c] = computerSelect;
		accessBtn[b][r][c].setText(Character.toString(computerSelect));
		accessBtn[b][r][c].setEnabled(false);
	}

	/*This method essentially implements a strategy for the computer player to make its move based on different conditions, 
	such as available board positions, potential wins, and the opponent's moves, to determine the best possible move. */
	private void movement_by_cpu() {
		int winning_points;
		int human_points;
		Dimensions_of_Board next_pointer;
		int win_BoardPanel = -1;
		int win_row = -1;
		int win_column = -1;
	
		winning_points = -1000;
		int i = 0, j = 0, k = 0;
		boolean breakCheck = false;
	
		while (i <= 3 && !breakCheck) {
			j = 0;
			while (j <= 3 && !breakCheck) {
				k = 0;
				while (k <= 3) {
					if (Board_Panel[i][j][k] == '-') {
						next_pointer = new Dimensions_of_Board();
						next_pointer.boardDim = i;
						next_pointer.rowBoard = j;
						next_pointer.colBoard = k;
	
						if (Win_Meth(computerSelect, next_pointer)) {
							Board_Panel[i][j][k] = computerSelect;
							accessBtn[i][j][k].setText(Character.toString(computerSelect));
							title.setText("I win! Click on New Game to play");
							title.setForeground(Color.GRAY);
							won = true;
							yscore++;
	
							BoardPanel_game_stop();
							score_BoardPanel();
							breakCheck = true;
							break;
						} else {
							if (DiffLvl != 1) {
								human_points = possible_movements(humanSelect, -1000, 1000);
							} else {
								human_points = compatible();
							}
	
							count = 0;
	
							if (human_points >= winning_points) {
								winning_points = human_points;
								win_BoardPanel = i;
								win_row = j;
								win_column = k;
								Board_Panel[i][j][k] = '-';
							} else {
								Board_Panel[i][j][k] = '-';
							}
						}
					}
					k++;
				}
				j++;
			}
			i++;
		}
	
		if (!won) {
			Board_Panel[win_BoardPanel][win_row][win_column] = computerSelect;
			accessBtn[win_BoardPanel][win_row][win_column].setText(Character.toString(computerSelect));
			accessBtn[win_BoardPanel][win_row][win_column].setEnabled(false);
		}
	}
	

	/*The below possible_movements method performs an exhaustive search and evaluates potential moves to determine the best possible move for a given player, 
	while using alpha-beta pruning to improve efficiency by avoiding unnecessary explorations of the game tree.*/
	private int possible_movements(char c, int a, int b) {
		int alpha = a;
		int beta = b;
	
		int i = 0, j = 0, k = 0;
	
		while (count <= Lookahead) {
			count++;
	
			if (c == computerSelect) {
				while (i <= 3) {
					j = 0;
					while (j <= 3) {
						k = 0;
						while (k <= 3) {
							if (Board_Panel[i][j][k] == '-') {
								Dimensions_of_Board next_pointer_1 = new Dimensions_of_Board();
								next_pointer_1.boardDim = i;
								next_pointer_1.rowBoard = j;
								next_pointer_1.colBoard = k;
	
								if (Win_Meth(computerSelect, next_pointer_1)) {
									Board_Panel[i][j][k] = '-';
									return 1000;
								} else {
									int human_point_1 = possible_movements(humanSelect, alpha, beta);
									if (human_point_1 > alpha) {
										alpha = human_point_1;
										Board_Panel[i][j][k] = '-';
									} else {
										Board_Panel[i][j][k] = '-';
									}
								}
								if (alpha >= beta)
									break;
							}
							k++;
						}
						j++;
					}
					i++;
				}
				return alpha;
			} else {
				while (i <= 3) {
					j = 0;
					while (j <= 3) {
						k = 0;
						while (k <= 3) {
							if (Board_Panel[i][j][k] == '-') {
								Dimensions_of_Board next_pointer = new Dimensions_of_Board();
								next_pointer.boardDim = i;
								next_pointer.rowBoard = j;
								next_pointer.colBoard = k;
	
								if (Win_Meth(humanSelect, next_pointer)) {
									Board_Panel[i][j][k] = '-';
									return -1000;
								} else {
									int human_point = possible_movements(computerSelect, alpha, beta);
									if (human_point < beta) {
										beta = human_point;
										Board_Panel[i][j][k] = '-';
									} else {
										Board_Panel[i][j][k] = '-';
									}
								}
								if (alpha >= beta)
									break;
							}
							k++;
						}
						j++;
					}
					i++;
				}
				return beta;
			}
		}
		return compatible();
	}
	

	private int compatible()
	{
		return (Positions_for_BoardPanel(computerSelect) - Positions_for_BoardPanel(humanSelect));
	}

	/*The code essentially evaluates all possible winning combinations on the board for the specified character c ('X' or 'O')
	and keeps track of how many of these winning combinations are currently possible.*/
	private int Positions_for_BoardPanel(char c) {
		int wincount = 0;
	
		// Win table
		int[][] wins = {
			// Rows on single BoardPanel
			{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15},
			{16, 17, 18, 19}, {20, 21, 22, 23}, {24, 25, 26, 27}, {28, 29, 30, 31},
			{32, 33, 34, 35}, {36, 37, 38, 39}, {40, 41, 42, 43}, {44, 45, 46, 47},
			{48, 49, 50, 51}, {52, 53, 54, 55}, {56, 57, 58, 59}, {60, 61, 62, 63},

			//Columns on single BoardPanel
			{0,4,8,12}, {1, 5,9,13}, {2,6,10,14}, {3, 7, 11,15}, 
			{16, 20,24,28},{17,21, 25, 29}, {18, 22, 26,30},{19, 23, 27,31},
			{32, 36, 40,44},{33, 37, 41,45},{34, 38, 42,46},{35, 39, 43,47},
			{48, 52, 56,60},{49, 53, 57,61},{50, 54, 58,62},{51, 55, 59,63},	

			//Diagonals on single BoardPanel
			{0, 5, 10,15}, {3, 6,9,12}, 
			{16,21,26,31}, {19, 22, 25,28}, 
			{32, 37,42,47},{35,38, 41, 44}, 
			{48, 53, 58,63},{51, 54, 57,60},	

			//Straight down through BoardPanels
			{0, 16, 32,48}, {1, 17,33,49}, {2,18,34,50}, {3, 19, 35,51}, 
			{4, 20,36,52},{5,21, 37, 53}, {6, 22, 38,54},{7, 23, 39,55},
			{8, 24, 40,56},{9, 25, 41,57},{10, 26, 42,58},{11, 27, 43,59},
			{12, 28, 44,60},{13, 29, 45,61},{14, 30, 46,62},{15, 31, 47,63},
			
			//Diagonals through BoardPanels
			{0, 20, 40,60}, {1, 21,41,61}, {2,22,42,62}, {3, 23, 43,63}, 
			{12, 24,36,48},{13,25, 37, 49}, {14, 26, 38,50},{15, 27, 39,51},
			{4, 21, 38,55},{8, 25, 42,59},{7, 22, 37,52},{11, 26, 41,56},
			{0, 17, 34,51},{3, 18, 33,48},{12, 29, 46,63},{15, 30, 45,60},
			{0, 21, 42,63},{3, 22, 41,60},{12, 25, 38,51},{15, 26, 37,48},
	
		};
	
		// Array indicating all the spaces on the game BoardPanel
		int[] BoardPanel_size = new int[64];
	
		int count_1 = 0;
		int i = 0;
	
		// Fill the BoardPanel_size array with 1s and 0s based on the Board_Panel values
		while (i <= 3) {
			int j = 0;
			while (j <= 3) {
				int k = 0;
				while (k <= 3) {
					if (Board_Panel[i][j][k] == c || Board_Panel[i][j][k] == '-') {
						BoardPanel_size[count_1] = 1;
					} else {
						BoardPanel_size[count_1] = 0;
					}
					count_1++;
					k++;
				}
				j++;
			}
			i++;
		}
	
		// Check for winning positions using the BoardPanel_size array
		int winIndex = 0;
		while (winIndex <= 75) {
			count_1 = 0;
			int j = 0;
			while (j <= 3) {
				if (BoardPanel_size[wins[winIndex][j]] == 1) {
					count_1++;
					// Assuming 'win' is an array to store winning positions
					win[j] = wins[winIndex][j];
					if (count_1 == 4) {
						wincount++;
					}
				}
				j++;
			}
			winIndex++;
		}
	
		return wincount;
	}

	/*This method validates whether the player has won by checking the game board against various possible win configurations, 
	covering rows, columns, diagonals, and multi-dimensional combinations within a 4x4x4 Tic-Tac-Toe setup.*/ 
	private boolean Win_Meth(char c, Dimensions_of_Board pos) {  ///// put this code
    	Board_Panel[pos.boardDim][pos.rowBoard][pos.colBoard] = c;

    	int[][] wins_pos_set = {
        		{0, 1, 2,3}, {4, 5,6,7}, {8,9,10,11}, {12, 13, 14,15},
				{16, 17,18,19},{20,21, 22, 23}, {24, 25, 26,27},{28, 29, 30,31},
				{32, 33, 34,35},{36, 37, 38,39},{40, 41, 42,43},{44, 45, 46,47},
				{48, 49, 50,51},{52, 53, 54,55},{56, 57, 58,59},{60, 61, 62,63},

				//Columns on single BoardPanel
				{0,4,8,12}, {1, 5,9,13}, {2,6,10,14}, {3, 7, 11,15}, 
				{16, 20,24,28},{17,21, 25, 29}, {18, 22, 26,30},{19, 23, 27,31},
				{32, 36, 40,44},{33, 37, 41,45},{34, 38, 42,46},{35, 39, 43,47},
				{48, 52, 56,60},{49, 53, 57,61},{50, 54, 58,62},{51, 55, 59,63},
				
				//Diagonals on single BoardPanel
				{0, 5, 10,15}, {3, 6,9,12}, 
				{16,21,26,31}, {19, 22, 25,28}, 
				{32, 37,42,47},{35,38, 41, 44}, 
				{48, 53, 58,63},{51, 54, 57,60},
			
				//Straight down through BoardPanels
				{0, 16, 32,48}, {1, 17,33,49}, {2,18,34,50}, {3, 19, 35,51}, 
				{4, 20,36,52},{5,21, 37, 53}, {6, 22, 38,54},{7, 23, 39,55},
				{8, 24, 40,56},{9, 25, 41,57},{10, 26, 42,58},{11, 27, 43,59},
				{12, 28, 44,60},{13, 29, 45,61},{14, 30, 46,62},{15, 31, 47,63},
		
				//Diagonals through BoardPanels
				{0, 20, 40,60}, {1, 21,41,61}, {2,22,42,62}, {3, 23, 43,63}, 
				{12, 24,36,48},{13,25, 37, 49}, {14, 26, 38,50},{15, 27, 39,51},
				{4, 21, 38,55},{8, 25, 42,59},{7, 22, 37,52},{11, 26, 41,56},
				{0, 17, 34,51},{3, 18, 33,48},{12, 29, 46,63},{15, 30, 45,60},
				{0, 21, 42,63},{3, 22, 41,60},{12, 25, 38,51},{15, 26, 37,48},
    	};

    	int[] gameBoardPanel = new int[64];
    	int count = 0;

    	int i = 0;
    	while (i <= 3) {
        	int j = 0;
        	while (j <= 3) {
            	int k = 0;
            	while (k <= 3) {
                	if (Board_Panel[i][j][k] == c) {
                    	gameBoardPanel[count] = 1;
                	} else {
                    	gameBoardPanel[count] = 0;
                	}
                	count++;
                	k++;
            	}
            	j++;
        	}
        	i++;
    	}

    	int iLoop = 0;
    	while (iLoop <= 75) {
        	count = 0;
        	int j = 0;
        	while (j <= 3) {
            	if (gameBoardPanel[wins_pos_set[iLoop][j]] == 1) {
                	count++;
                	win[j] = wins_pos_set[iLoop][j];
                	if (count == 4) {
                    	return true;
                	}
            	}
            	j++;
        	}
        	iLoop++;
    	}

    	return false;
	} 

	/*The provided code contains a main method that serves as the entry point of the program.*/
	public static void main(String a[])
	{
		new tictactoe_game();
	}
}

/*Authors: Mani Kanth Dhotre, Ram Sirusanagandla, Deepthi Chinthanippu, Jaideep Gurrapu
This software is a method for playing Tic-Tac-Toe that utilizes a minimax algorithm incorporating alpha-beta pruning to make decisions within the game's move tree.*/