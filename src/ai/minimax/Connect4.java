package ai.minimax;

import java.util.ArrayList;

public class Connect4 {
	private static int ROWS = 6;
	private static int COLUMNS = 7;
	private int BOARD[][] = new int[ROWS][COLUMNS];
	private int winner = 0;
	
	// 1 -> computer; -1 -> human
	
	public Connect4() {
		startMatch();
	}
	
	public void startMatch() {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				BOARD[i][j] = 0;
			}
		}
		
		winner = 0;
	}
	
	public void showBoard() {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				System.out.print("|");
				if (BOARD[i][j] != 0) {
					if (BOARD[i][j] == 1) {
						System.out.print("X");
					} else {
						System.out.print("O");
					}
				} else {
					System.out.print(" ");
				}
			}
			System.out.println("|");
		}
		
		for (int i = 0; i < COLUMNS; ++i) {
			System.out.print(" " + (i + 1));
		}
		
		System.out.println();
	}
	
	public void insertChip(int column, int player, int[][] BOARD) {
		for (int i = (ROWS - 1); i >= 0; --i) {
			if (BOARD[i][column] == 0) {
				BOARD[i][column] = player;
				return;
			}
		}
	}
	
	public boolean verticalCheck(int[][] BOARD) {
		for (int i = 0; i < (ROWS - 3); ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (BOARD[i][j] != 0) {
					if (BOARD[i][j] == BOARD[i + 1][j] && BOARD[i][j] == BOARD[i + 2][j] &&
							BOARD[i][j] == BOARD[i + 3][j]) {
						winner = BOARD[i][j];
						return true;
					}
				}
			}
		}
		
		winner = 0;
		return false;
	}
	
	public boolean horizontalCheck(int[][] BOARD) {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < (COLUMNS - 3); ++j) {
				if (BOARD[i][j] != 0) {
					if (BOARD[i][j] == BOARD[i][j + 1] && BOARD[i][j] == BOARD[i][j + 2] &&
							BOARD[i][j] == BOARD[i][j + 3]) {
						winner = BOARD[i][j];
						return true;
					}
				}
			}
		}
		
		winner = 0;
		return false;
	}
	
	public boolean diagonalCheck(int[][] BOARD) {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (BOARD[i][j] != 0) {
					if (i < 3) {
						if (j < 4) {
							if (BOARD[i][j] == BOARD[i + 1][j + 1] && BOARD[i][j] == BOARD[i + 2][j + 2] &&
									BOARD[i][j] == BOARD[i + 3][j + 3]) {
								winner = BOARD[i][j];
								return true;
							}
						}
						if (j > 2) {
							if (BOARD[i][j] == BOARD[i + 1][j - 1] && BOARD[i][j] == BOARD[i + 2][j - 2] &&
									BOARD[i][j] == BOARD[i + 3][j - 3]) {
								winner = BOARD[i][j];
								return true;
							}
						}
					}
				}
			}
		}
		
		winner = 0;
		return false;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public boolean wonPlayer(int player, int[][] board) {
		if (verticalCheck(board)) {
			if (winner == player) {
				return true;
			}
		} else if (horizontalCheck(board)) {
			if (winner == player) {
				return true;
			}
		} else if (diagonalCheck(board)) {
			if (winner == player) {
				return true;
			}
		}
		
		return false;
	}
	
	public int[][] getBoard() {
		return BOARD;
	}
	
	// ========================== MINIMAX =============================================
	
	public boolean isFavorablePosition(int pos, int player) {
		return (pos == player || pos == 0);
	}
	
	public int evaluateBoard(int player) {
		int value = 0;
		
		// vertical
		for (int i = 0; i < (ROWS - 3); ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (isFavorablePosition(BOARD[i][j], player) && 
						isFavorablePosition(BOARD[i + 1][j], player) && 
						isFavorablePosition(BOARD[i + 2][j], player) && 
						isFavorablePosition(BOARD[i + 3][j], player)) {
					++value;
				}
			}
		}
		
		// horizontal
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < (COLUMNS - 3); ++j) {
				if (isFavorablePosition(BOARD[i][j], player) && 
						isFavorablePosition(BOARD[i][j + 1], player) && 
						isFavorablePosition(BOARD[i][j + 2], player) && 
						isFavorablePosition(BOARD[i][j + 3], player)) {
					++value;
				}
			}
		}
		
		// diagonal
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (i < 3) {
					if (j < 4) {
						if (isFavorablePosition(BOARD[i][j], player) && 
								isFavorablePosition(BOARD[i + 1][j + 1], player) && 
								isFavorablePosition(BOARD[i + 2][j + 2], player) && 
								isFavorablePosition(BOARD[i + 3][j + 3], player)) {
							++value;
						}
					}
					if (j > 2) {
						if (isFavorablePosition(BOARD[i][j], player) && 
								isFavorablePosition(BOARD[i + 1][j - 1], player) && 
								isFavorablePosition(BOARD[i + 2][j - 2], player) && 
								isFavorablePosition(BOARD[i + 3][j - 3], player)) {
							++value;
						}
					}
				}
			}
		}
		
		return value;
	}
	
	public ArrayList<Integer> getMovesForTheState(int[][] state) {
		ArrayList<Integer> moves = new ArrayList<>();
		
		for (int i = 0; i < COLUMNS; ++i) {
			if (state[0][i] == 0) {
				moves.add(i);
			}
		}
		
		return moves;
	}
	
	public int[] max(ArrayList<int[]> tuples) {
		int[] resp = tuples.get(0);
		
		for (int[] tuple : tuples) {
			if (resp[1] < tuple[1]) {
				resp = tuple;
			}
		}
		
		return resp;
	}
	
	public int[] min(ArrayList<int[]> tuples) {
		int[] resp = tuples.get(0);
		
		for (int[] tuple : tuples) {
			if (resp[1] > tuple[1]) {
				resp = tuple;
			}
		}
		
		return resp;
	}
	
	// 0 -> move, 1 -> eval
	public int[] minimax(int depth, int limit, int[][] actualState, int move, int player, int type) {
		if (wonPlayer(1, actualState)) {
			return new int[] { move, 999999999 };
		}
		
		if (wonPlayer(-1, actualState)) {
			return new int[] { move, -999999999 };
		}
		
		if (depth == limit) {
			int eval = evaluateBoard(1) - evaluateBoard(-1);
			return new int[] {move, eval};
		}
		
		ArrayList<Integer> movements = getMovesForTheState(actualState);
		ArrayList<int[]> tuples = new ArrayList<>();
		
		for (Integer actualMove : movements) {
			int[][] copyActualScate = new int[ROWS][COLUMNS];
			
			for (int i = 0; i < ROWS; ++i) {
				for (int j = 0; j < COLUMNS; ++j) {
					copyActualScate[i][j] = actualState[i][j];
				}
			}
			
			insertChip(actualMove, player, copyActualScate);
			tuples.add(minimax(depth + 1, limit, copyActualScate, actualMove, player * -1, type * -1));
		}
		
		if (movements.isEmpty()) {
			return new int[] { move, 0 };
		}
		
		if (type == 1) {
			int[] maxTuple = max(tuples);
			if (move == -1) {
				return maxTuple;
			} else {
				return new int[] { move, maxTuple[1] };
			}
		} else {
			int[] minTuple = min(tuples);
			if (move == -1) {
				return minTuple;
			} else {
				return new int[] { move, minTuple[1] };
			}
		}
	}
}
