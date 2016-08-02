
public class Piece {
	int type;
	int color;
	int x;
	int y;
	Main main;

	int moves = 0;
	boolean movedTwo = false;
	boolean inDanger = false;

	final static int WHITE = 1;
	final static int BLACK = -1;

	final static int EMPTY = 0;
	final static int BORDER = 7;

	final static int KING = 1;
	final static int QUEEN = 2;
	final static int BISHOP = 3;
	final static int KNIGHT = 4;
	final static int ROOK = 5;
	final static int PAWN = 6;

	boolean[][] sight = new boolean[10][10];

	public Piece(int type, int color, int x, int y) {
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
		see();
	}

	boolean move(int x, int y) {
		Piece mustMove = null;
		see();
		if (Main.board[x][y] != null) {
			if (Main.board[x][y].getColor() != color && sight[x][y]) {
				for (Piece piece : Main.pieces) {
					if (piece.inDanger && piece.getColor() == Main.playerTurn)
						mustMove = piece;
				}
				
				if (mustMove != null) {
					if (this != mustMove) 
						return false;
				}
					
				Main.pieces.remove(Main.pieces.indexOf(Main.board[x][y]));
				Main.board[this.x][this.y] = null;
				Main.board[x][y] = this;
				moves++;
				this.x = x;
				this.y = y;
				return true;
			}
		} else if (sight[x][y]) {
			Main.board[x][y] = this;
			if (Main.board[this.x][this.y].getType() == PAWN && Math.abs(y - this.y) == 2)
				movedTwo = true;
			Main.board[this.x][this.y] = null;
			if (Main.board[x][y - color] != null) {
				if (Main.board[x][y - color].movedTwo && Main.board[x][y - color].moves == 1)
					Main.board[x][y - color] = null;
			}
			moves++;
			this.x = x;
			this.y = y;
			return true;
		}
		return false;
	}

	void see() {
		switch (type) {

		case KING:
			inDanger = false;
			
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i != 0 || j != 0) {
						if (Main.board[x + i][y + j] != null) {
							if (Main.board[x + i][y + j].getType() != BORDER) {
								sight[x + i][y + j] = true;
							}
						}
					}
				}
			}
			for (Piece piece : Main.pieces) {
				if (piece.getColor() != color) {
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							if (piece.sight[x + i][y + j])
								sight[x + i][y + j] = false;
							if (piece.sight[x][y])
								inDanger = true;
						}
					}
				}
			}
			break;

		case QUEEN:
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i != 0 || j != 0) {
						int xdis = 0;
						int ydis = 0;
						while (Main.board[x + i + xdis][y + j + ydis] == null) {
							sight[x + i + xdis][y + j + ydis] = true;
							xdis += i;
							ydis += j;
						}
						if (Main.board[x + i + xdis][y + j + ydis].getType() != BORDER) {
							sight[x + i + xdis][y + j + ydis] = true;
						}
					}
				}
			}
			break;

		case BISHOP:
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i != 0 && j != 0) {
						int xdis = 0;
						int ydis = 0;
						while (Main.board[x + i + xdis][y + j + ydis] == null) {
							sight[x + i + xdis][y + j + ydis] = true;
							xdis += i;
							ydis += j;
						}
						if (Main.board[x + i + xdis][y + j + ydis].getType() != BORDER) {
							sight[x + i + xdis][y + j + ydis] = true;
						}
					}
				}
			}

			break;

		case KNIGHT:
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 || j == 0) {
						if (i != 0 || j != 0) {
							int xdis = 0;
							int ydis = 0;
							while (Math.abs(xdis) < 2 && Math.abs(ydis) < 2) {
								if (Main.board[x + i + xdis][y + j + ydis] != null) {
									if (Main.board[x + i + xdis][y + j + ydis].getType() == BORDER)
										break;
								}
								if (Math.abs(xdis) == 1 || Math.abs(ydis) == 1) {
									sight[x + xdis + i + j][y + ydis + j + i] = true;
									sight[x + xdis + i + -j][y + ydis + j + -i] = true;
								}
								xdis += i;
								ydis += j;
							}
						}
					}
				}
			}
			break;

		case ROOK:
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 || j == 0) {
						if (i != 0 || j != 0) {
							int xdis = 0;
							int ydis = 0;
							while (Main.board[x + i + xdis][y + j + ydis] == null) {
								sight[x + i + xdis][y + j + ydis] = true;
								xdis += i;
								ydis += j;
							}
							if (Main.board[x + i + xdis][y + j + ydis].getType() != BORDER) {
								sight[x + i + xdis][y + j + ydis] = true;
							}
						}
					}
				}
			}
			break;

		case PAWN:
			if (moves == 0) {
				sight[x][y + color + color] = true;
			}

			for (int i = -1; i < 2; i++) {
				if (i != 0) {
					if (Main.board[x + i][y] != null) {
						if (Main.board[x + i][y].getType() == PAWN && Main.board[x + i][y].getColor() != color
								&& Main.board[x + i][y].movedTwo && Main.board[x + i][y].moves == 1) {
							Main.board[x + i][y].sight[x][y - color] = true;
						}
					}
				}
			}

			for (int i = -1; i < 2; i++) {
				if (i == 0) {
					if (Main.board[x + i][y + color] == null)
						sight[x + i][y + color] = true;
				} else if (Main.board[x + i][y + color] != null) {
					if (Main.board[x + i][y + color].getColor() != color)
						sight[x + i][y + color] = true;
				}
			}
			break;

		default:
			break;
		}
	}

	int getType() {
		return type;
	}

	int getColor() {
		return color;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}
}
