package Submission;

import java.util.*;

class RunGame {

	public static void main(String args[]) throws Exception
	{
		Board GameBoard; playerSearch ComputerAI; 
		GameBoard				= new Board(7,6);
		ComputerAI				= new playerSearch();
		int userInput 			= -1;
		int wrongInput			= 0;
		
		System.out.println("Welcome to Connect 4!\n\n" +
							"Your aim is to put 4 'O'\n" +
							"in a line. The line can be\n" +
							"horizontal, vertical or diagonal.\n\n" +
							"You will be competing against the\n"+
							"computer ('X') and you play first!\n\n" +
							"To play you enter a number from\n" + 
							"1 to 7 to add an 'O' to the respective column.\n\n" +
							"If at any time you want to quit\n" +
							"close the program.\n\n" +
							"Good Luck!\n\n" + 
							"-------\n-------\n-------\n" +
							"-------\n-------\n-------\n\n1234567\n");

		while((GameBoard.winnerIs()==0) && GameBoard.validMovesLeft())
		{
			
			if(GameBoard.changePlayer == Board.PLAYER_ONE){// if it is Player 1's turn

				userInput = GameBoard.testMove();//take input

				while(wrongInput == 0){
					
					while(userInput == -1){// in case of random character input
						
						userInput = GameBoard.testMove();
					}// end while(userInput == -1)

					wrongInput = 1;
	
				}// end while (wrongInput == 0)

				if (userInput != -1){//if input is correct make move
					GameBoard.makeMove(userInput);
				}else// if input is not correct
					wrongInput = 0;
			}
			else
				GameBoard.makeMove(ComputerAI.getMove(GameBoard));// 
			
			System.out.println(GameBoard);

		}// end while((b.winnerIs()==0) && b.validMovesLeft())
		
		//State the winner when the game ends
		if (GameBoard.winnerIs()==1){
			System.out.println("You Win!\nThank you for playing!");
		}else if(GameBoard.winnerIs()== -1){
			System.out.println("Computer Wins!\nThank you for playing!");
		}
		
	}//end Main
	
}//end RunGame


class Board {

	final static int PLAYER_ONE = 1;
	final static int PLAYER_TWO = -1;
	final static int EMPTY 		= 0;

	Point[][] grid;
	int[] heights;//checks whether a column is full

	int cols;
	int ros;
	
	int initial = 0;
	
	int[] moves;
	int limitMoves;

	int changePlayer;
	Point[][] checkLines;

	public Board(int columns, int rows)
	{
		// TODO Creates a double point array where the 
		// grid is stored. Each location is associated 
		// with a state, stating the Player by which it
		// is occupied
		
		cols		= columns;
		ros			= rows;
		grid		= new Point[cols][ros];
		heights		= new int[cols];
		moves		= new int[cols*ros];
		limitMoves	= -1;
		
		for(int x = 0; x < cols; x++)//for(1)
		{
			heights[x]= 0;
			for(int y = 0; y < ros; y++)//for(2)
				grid[x][y] = new Point(x,y,EMPTY); 

		} // end for(1)
		
		generateCheckLines();
		changePlayer = PLAYER_ONE;
	} // end Board(int columns, int rows)

	

	public int testMove() {
		// TODO Checks whether the appropriate input
		// is entered using the Scanner
		
		Scanner move = new Scanner (System.in);//reads input 
		
		int correctInput		=	0;
		int testMove			=	0;
		
		System.out.println("Enter a number:"); 

		while (correctInput == 0){	
			try{
				
				testMove = move.nextInt();

				if ((testMove >= 1) && (testMove <= 7)){
					// checks if the column entered has space
					if ((testMove == 1) && (validMove(0))){

						correctInput = 1;// exits while loop
						
					} else if ((testMove == 2) && (validMove(1))){

						correctInput = 1;
						
					} else if ((testMove == 3) && (validMove(2))){

						correctInput = 1;
						
					} else if ((testMove == 4) && (validMove(3))){

						correctInput = 1;
						
					} else if ((testMove == 5) && (validMove(4))){

						correctInput = 1;
						
					} else if ((testMove == 6) && (validMove(5))){

						correctInput = 1;
						
					} else if ((testMove == 7) && (validMove(6))){

						correctInput = 1;
						
					} else{
						System.out.println("\nColumn " + testMove + " is full! Try another one.\n");
					}

				}else {// in case of wrong numerical input
					System.out.println("\nThere are only 7 columns!\nEnter a number from 1 to 7.\n");
				}

			}catch(java.util.InputMismatchException e){// in case of random character input

				System.out.println("\nWhat is that?!\nTry again.\n");
				return -1;
			}
		}// end while
		return testMove-1;
	}// end testMove()

	
	void generateCheckLines()
	{
		//TODO Checks whether there are for
		// disks of the same type in a continuous
		// line
		
		checkLines	= new Point[69][4];
		int count	= 0;
		
		// checks for horizontal lines
		for(int rows = 0; rows < ros; rows++)
		{
			for(int columns = 0; columns < cols-3; columns++)
			{
				Point[] temp = new Point[4];
				for(int check = columns; check < columns+4; check++)
					temp[check-columns]=grid[check][rows];
				checkLines[count]=temp;
				count++;
			}
		}

		// checks for vertical lines
		for(int columns = 0; columns < cols; columns++)
		{
			for(int rows = 0; rows < ros-3; rows++)
			{
				Point[] temp = new Point[4];
				for(int check = rows; check < rows+4; check++)
					temp[check-rows]=grid[columns][check];
				checkLines[count]=temp;
				count++;
			}
		}

		// checks for diagonal lines (1)
		for(int columns = 0; columns < cols-3; columns++)
		{
			for(int rows = 0; rows < ros-3; rows++)
			{
				Point[] temp = new Point[4];
				for(int checkC = columns, checkR = rows; checkC < columns+4 && checkR < rows+4; checkC++, checkR++)
					temp[checkR-rows]=grid[checkC][checkR];
				checkLines[count]=temp;
				count++;
			}
		}
		// checks for diagonal lines (2)
		for(int columns = 0; columns < cols-3; columns++)
		{
			for(int rows = ros-1; rows > ros-4; rows--)
			{
				Point[] temp = new Point[4];
				for(int checkC = columns, checkR = rows; checkC < columns+4 && checkR >-1; checkC++, checkR--)
					temp[checkC-columns]=grid[checkC][checkR];
				checkLines[count]=temp;
				count++;
			}
		}
	}// end generateCheckLines()
	
	
	boolean validMove(int column)
	{
		//TODO Checks that there is still space in a column
		
		return heights[column] < ros;
	}// end validMove(int column)

	
	void makeMove(int column)
	{
		// TODO Adds the move to the game and changes player
		
		grid[column][heights[column]].setState(changePlayer);
		heights[column]++;
		limitMoves++;
		moves[limitMoves]	= column;
		changePlayer		= -changePlayer;
	}// end makeMove(int column)

	
	void undoMove()
	{
		// TODO Performs undo to moves done by the AI
		// algorithm that will not be used
		
		grid[moves[limitMoves]][heights[moves[limitMoves]]-1].setState(EMPTY);
		heights[moves[limitMoves]]--;
		limitMoves--;
		changePlayer = -changePlayer;
	} // end undoMove()


	boolean validMovesLeft()
	{
		//TODO Checks whether there are
		// empty slots in the grid
		
		return limitMoves<moves.length-1;
	} // end validMovesLeft()


	int winnerIs()
	{
		//TODO Checks for consecutive lines
		// returns the winner
		
		for(int i=0;i<checkLines.length;i++)
			if(getScore(checkLines[i])==4)
			{
				return PLAYER_ONE;
			}
			else if(getScore(checkLines[i])==-4){

				return PLAYER_TWO;}
		return 0;
	}// end winnerIs()

	
	private int getScore(Point[] points) {
		//TODO Identifies both the progression of
		// the score and the winner (in case there
		// is one)
		
		int playerone = 0;
		int playertwo = 0;
		
		for(int i = 0; i < points.length; i++){
			
			if(points[i].getState() == Board.PLAYER_ONE)
				playerone++;
			
			else if(points[i].getState() == Board.PLAYER_TWO)
				playertwo++;
			}
		// if there is a winner return either +4 of -4 otherwise return 0
		if((playerone + playertwo > 0) && (!(playerone > 0 && playertwo > 0)))
		{
			return (playerone!=0)?playerone:(playertwo*PLAYER_TWO);// added player two to win
		}
		else
			return 0;
	} // end getScore


	int getStrength()
	{
		//TODO Checks the strength of the
		// moves performed by the AI
		
		int sum			= 0;
		int[] weights 	= {0,1,10,50,600};
		
		for(int i = 0; i < checkLines.length; i++)
		{
			int temp = getScore(checkLines[i]);
			sum+=(temp>0)?weights[Math.abs(temp)]:-weights[Math.abs(temp)];
		}
		return sum+(changePlayer==PLAYER_ONE?16:-16);
	} // end getStrength()

	
	public String toString()
	{
		//TODO Prints the game on the Screen
		
		StringBuffer buf = new StringBuffer();
		// makes the grid and fills it in accordingly
		for(int y = ros-1; y > -1; y--){
			for(int x = 0; x < cols; x++)
				if(grid[x][y].getState()==EMPTY)
					buf.append("-");
				else if(grid[x][y].getState()==PLAYER_ONE)
					buf.append("O");
				else
					buf.append("X");
			buf.append("\n");
		}// end for
		buf.append("\n1234567\n");
		return buf.toString();
	}// end String to String()
	
} // end class Board


class playerSearch {
	//TODO Calls a simple Minimax Algorithm
	// with alpha beta pruning
	
	int alpha	= Integer.MAX_VALUE;
	int beta	= Integer.MIN_VALUE;
	
	int getMove(Board checkBoard)
	{
		int[] moves = new int[7];
		int highest = 0;
		
		for(int i = 0; i < 7; i++)
		{
			moves[i] = Integer.MIN_VALUE;
			if(checkBoard.validMove(i))
			{
				checkBoard.makeMove(i);
				moves[i] = minValue(checkBoard, 6, alpha, beta);
				if(moves[i] >= moves[highest])
					highest = i;
				checkBoard.undoMove();
			} 
		}
		return highest;
	}// end getMove
	
	
	int minValue(Board checkBoard, int depth, int alpha, int beta)
	{
		//TODO Searches the game tree of the min Player
		// in minimax. In the case that a move is worse
		// than a previously examined one further moves
		// on said branch are not further evaluated. Best
		// payoff for min Player is - infinity.
		
		int[] moves	= new int[7];
		int lowest	= 0;
		
		for(int i = 0; i < 7; i++)
		{
			moves[i] = Integer.MAX_VALUE;
			if(checkBoard.validMove(i))
			{
				checkBoard.makeMove(i);
				
				// checks winner, search depth, and pruning cut off
				if((checkBoard.winnerIs() == 0) && depth > 0 && moves[i] > alpha)
				{
					moves[i] = maxValue(checkBoard, depth-1, alpha, beta);
				}
				else 
				{
					moves[i] = -checkBoard.getStrength();
				}
				if(moves[i] < alpha && depth == 0)
					lowest = i;
				alpha = moves[lowest];
				checkBoard.undoMove();
			}// end if(checkBoard.validMove(i))
			
		}// end for
		
		return moves[lowest];
	} // end MinValue
	
	
	int maxValue(Board checkBoard, int depth, int alpha, int beta)
	{
		//TODO Searches the game tree of the max Player
		// in minimax. In the case that a move is worse
		// than a previously examined one further moves
		// on said branch are not further evaluated. Best
		// payoff for max player is + infinity.
		
		int[] moves = new int[7];
		int highest = 0;
		
		for(int i = 0; i < 7; i++)
		{
			moves[i] = Integer.MAX_VALUE;
			
			if(checkBoard.validMove(i))
			{
				checkBoard.makeMove(i);
				
				// checks winner, search depth, and pruning cut off
				if((checkBoard.winnerIs() == 0) && depth > 0 && moves[i] <= beta)
				{
					moves[i] = minValue(checkBoard, depth-1, alpha, beta);
				}
				else 
					moves[i] = -checkBoard.getStrength();
				if(moves[i] >= beta && depth == 0)
					highest = i;
				beta = moves[highest];
				checkBoard.undoMove();
			} // end if(checkBoard.validMove(i))
		}// end for
		return moves[highest];
	} // end MaxValue

} // end class playerSearch


class Point
{
	//TODO Assigns a state representing each player
	// in the grid

	int x;
	int y;
	int state;

	Point(int xt, int yt, int statet)
	{
		x=xt;
		y=yt;
		state=statet;
	}

	void setState(int player)
	{
		state=player;
	}

	int getState()
	{
		return state;
	}

}// end class Point


