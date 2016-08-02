import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static Piece[][] board = new Piece[10][10];

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

	static int playerTurn = WHITE;

	static ArrayList<Piece> pieces = new ArrayList<>();
	static ArrayList<String> letters = new ArrayList<>();
	static ArrayList<String> numbers = new ArrayList<>();

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		letters.add("hey look i just need to use up this 0 index");
		letters.add("H");
		letters.add("G");
		letters.add("F");
		letters.add("E");
		letters.add("D");
		letters.add("C");
		letters.add("B");
		letters.add("A");

		numbers.add("i needed to use this 0 index too");
		numbers.add("8");
		numbers.add("7");
		numbers.add("6");
		numbers.add("5");
		numbers.add("4");
		numbers.add("3");
		numbers.add("2");
		numbers.add("1");

		populateBoard();
		displayBoard();

		for (;;) { // game loop

			if (checkWin() != 0) {
				if (checkWin() == -1) {
					System.out.println("Stalemate");
					break;
				}
				System.out.println((checkWin() == WHITE ? "White " : "Black ") + "wins!");
				break;
			}

			String fromx;
			int fromx2 = 0;

			String fromy;
			int fromy2 = 0;

			String tox;
			int tox2 = 0;

			String toy;
			int toy2 = 0;

			for (;;) {
				for (;;) {
					boolean breaker = false;
					System.out.println((playerTurn == 1 ? "White, " : "Black, ") + "input starting x coordinate.");
					fromx = scan.nextLine();
					for (int pqq = 1; pqq < letters.size(); pqq++) {
						if (fromx.equalsIgnoreCase(letters.get(pqq))) {
							fromx2 = pqq;
							breaker = true;
							break;
						}
					}
					if (breaker == true)
						break;
				}
				for (;;) {
					boolean breaker = false;
					System.out.println((playerTurn == 1 ? "White, " : "Black, ") + "input starting y coordinate.");
					fromy = scan.nextLine();
					for (int pqq = 1; pqq < numbers.size(); pqq++) {
						if (fromy.equals(numbers.get(pqq))) {
							fromy2 = pqq;
							breaker = true;
							break;
						}
					}
					if (breaker == true)
						break;
				}

				for (;;) {
					boolean breaker = false;
					System.out.println((playerTurn == 1 ? "White, " : "Black, ") + "input ending x coordinate.");
					tox = scan.nextLine();
					for (int pqq = 1; pqq < letters.size(); pqq++) {
						if (tox.equalsIgnoreCase(letters.get(pqq))) {
							tox2 = pqq;
							breaker = true;
							break;
						}
					}
					if (breaker == true)
						break;
				}
				for (;;) {
					boolean breaker = false;
					System.out.println((playerTurn == 1 ? "White, " : "Black, ") + "input ending y coordinate.");
					toy = scan.nextLine();
					for (int pqq = 1; pqq < numbers.size(); pqq++) {
						if (toy.equals(numbers.get(pqq))) {
							toy2 = pqq;
							breaker = true;
							break;
						}
					}
					if (breaker == true)
						break;
				}
				if (board[fromx2][fromy2] != null) {
					if (board[fromx2][fromy2].getColor() == playerTurn) {
						if (board[fromx2][fromy2].move(tox2, toy2))
							break;
					}
				}
				System.out.println("Improper move, try again");
			}

			displayBoard();
			playerTurn *= -1; // alternate player turn

		}

	}

	static int checkWin() {
		boolean white = true;
		boolean black = true;

		for (Piece piece : pieces) {
			if (piece.getType() == KING && piece.inDanger) {
				for (int i = 1; i < 9; i++) {
					for (int j = 1; j < 9; j++) {
						if (piece.sight[i][j] && board[i][j].getColor() != piece.getType()) {
							if (piece.getColor() == WHITE) {
								black = false;
							} else {
								white = false;
							}
						}
					}
				}
			} else {
				white = false;
				black = false;
			}
		}
		if (!white || !black) {
			if (!white && !black)
				return 0;
			return white ? WHITE : BLACK;
		}
		return -1;
	}

	static void populateBoard() { // creates all of the pieces

		for (int n = 0; n < 10; n++) { // sets edges to BORDER
			for (int p = 0; p < 10; p++) {
				if (n == 0 || n == 9 || p == 0 || p == 9) {
					board[n][p] = new Piece(BORDER, 0, n, p);
				}
			}
		}

		for (int i = 1; i < 9; i++) {
			for (int j = 2; j < 8; j = j + 5) { // pawns
				board[i][j] = new Piece(PAWN, j == 2 ? WHITE : BLACK, i, j);
				pieces.add(board[i][j]);
			}
			for (int k = 1; k < 9; k = k + 7) {
				switch (i) { // complex pieces
				case 1:
					board[i][k] = new Piece(ROOK, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 2:
					board[i][k] = new Piece(KNIGHT, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 3:
					board[i][k] = new Piece(BISHOP, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 4:
					board[i][k] = new Piece(k == 1 ? QUEEN : KING, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 5:
					board[i][k] = new Piece(k == 1 ? KING : QUEEN, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 6:
					board[i][k] = new Piece(BISHOP, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 7:
					board[i][k] = new Piece(KNIGHT, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;

				case 8:
					board[i][k] = new Piece(ROOK, k == 1 ? WHITE : BLACK, i, k);
					pieces.add(board[i][k]);
					break;
				}
			}
		}
	}

	static void displayBoard() {
		System.out.println(" -----------------------------------------");
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				String text = "";
				if (board[j][i] != null) {
					switch (board[j][i].getType()) {
					case KING:
						text = board[j][i].getColor() == WHITE ? "| wK" : "| bK";
						break;
					case QUEEN:
						text = board[j][i].getColor() == WHITE ? "| wQ" : "| bQ";
						break;
					case BISHOP:
						text = board[j][i].getColor() == WHITE ? "| wb" : "| bb";
						break;
					case KNIGHT:
						text = board[j][i].getColor() == WHITE ? "| wk" : "| bk";
						break;
					case ROOK:
						text = board[j][i].getColor() == WHITE ? "| wr" : "| br";
						break;
					case PAWN:
						text = board[j][i].getColor() == WHITE ? "| wp" : "| bp";
						break;
					}
				} else {
					text = "|   ";
				}
				System.out.print(" " + text);
			}
			System.out.println(" | " + (9 - i));
			System.out.println(" -----------------------------------------");
		}
		System.out.println("    H    G    F    E    D    C    B    A  ");
	}
}