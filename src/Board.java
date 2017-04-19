import java.util.Scanner;
import java.util.Random;

public class Board {

	private char[][] board = new char[8][8];
	private int[][] logicBoard = new int[8][8];
	private final int WATER = -1, HIT_NOTHING = 0, HIT_FRIENDLY_SHIP = 1, HIT_FRIENDLY_NADE = 2, HIT_ENEMY_SHIP = 3,
			HIT_ENEMY_NADE = 4;
	private boolean playerNadeHit = false, cpuNadeHit = false;
	public int allyCount, enemyCount;
	
	/**
	 * Constructor used to create the game board.
	 */
	public Board() {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				board[row][column] = '~';
			}
		}

		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				logicBoard[row][column] = -1;
			}
		}

	}
	/**
	 * Prompts user to enter ship locations for the game board and places them where valid.
	 */
	public void placeShips() {

		Scanner input = new Scanner(System.in);
		int row, column;
		String coordinate;
		boolean invalid = false;

		for (int i = 0; i < 6; i++) {
			do {
				do {
					System.out.println("Please enter coordinate for ship " + (i + 1) + ":");
					invalid = false;
					coordinate = input.nextLine();
					if (coordinate.charAt(0) - 65 < 0 || coordinate.charAt(0) - 65 > 7)
						invalid = true;
					if (coordinate.charAt(1) < '1' || coordinate.charAt(1) > '8')
						invalid = true;
					if (invalid)
						System.out.println("Coordinate out of bounds, try again");

				} while (invalid);

				column = coordinate.charAt(0) - 65;
				row = coordinate.charAt(1) - '1';
				if (isOccupied(row, column))
					System.out.println("Space already taken. Try again.");
			} while (isOccupied(row, column));
			board[row][column] = 's';

		}
	}
	/**
	 * Prompts user to enter grenade locations for the game board and places them where valid.
	 */
	public void placeGrenades() {
		Scanner input = new Scanner(System.in);
		int row, column;
		String coordinate;
		boolean invalid = false;

		for (int i = 0; i < 4; i++) {
			do {
				do {
					System.out.println("Please enter coordinate for grenade " + (i + 1) + ":");
					invalid = false;
					coordinate = input.nextLine();
					if (coordinate.charAt(0) - 65 < 0 || coordinate.charAt(0) - 65 > 7)
						invalid = true;
					if (coordinate.charAt(1) < '1' || coordinate.charAt(1) > '8')
						invalid = true;
					if (invalid)
						System.out.println("Coordinate out of bounds, try again");

				} while (invalid);

				column = coordinate.charAt(0) - 65;
				row = coordinate.charAt(1) - '1';
				if (isOccupied(row, column))
					System.out.println("Space already taken. Try again.");
			} while (isOccupied(row, column));
			board[row][column] = 'g';

		}
	}
	/**
	 * Returns true if coordinate already contains a ship/grenade when placing either of them.
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isOccupied(int row, int column) {
		if (board[row][column] == '~')
			return false;
		else
			return true;
	}
	/**
	 * Prints and displays all of the ships and grenades on the map uncovered.
	 */
	public void showBoard() {
		System.out.println("---------------------------------------------------------------");
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				System.out.print(board[row][column] + "\t");
			}
			System.out.println();
		}
	}
	
	
	
	/**
	 * Randomly selects coordinates and places the CPU ships onto the game board.
	 */
	public void placeEnemyShips() {
		Random rand = new Random();
		int row, column;
		for (int i = 0; i < 6; i++) {
			do {
				row = rand.nextInt(8);
				column = rand.nextInt(8);
			} while (isOccupied(row, column));

			board[row][column] = 'S';
		}
	}
	/**
	 * Randomly selects coordinates and places the CPU grenades onto the game board.
	 */
	public void placeEnemyGrenades() {
		Random rand = new Random();
		int row, column;
		for (int i = 0; i < 4; i++) {
			do {
				row = rand.nextInt(8);
				column = rand.nextInt(8);
			} while (isOccupied(row, column));

			board[row][column] = 'G';
		}
	}
	/**
	 * Prints the current state of the board based on the shots taken. 
	 */
	public void updateBoard() {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				switch (logicBoard[row][column]) {
				case WATER:
					System.out.print("~\t");
					break;
				case HIT_NOTHING:
					System.out.print("*\t");
					break;
				case HIT_FRIENDLY_SHIP:
					System.out.print("s\t");
					break;
				case HIT_FRIENDLY_NADE:
					System.out.print("g\t");
					break;
				case HIT_ENEMY_SHIP:
					System.out.print("S\t");
					break;
				case HIT_ENEMY_NADE:
					System.out.print("G\t");
					break;
				}
			}
			System.out.println();
		}
	}
	/**
	 * Prompts the user to select a coordinate for shooting.
	 */
	public void shootRocket() {
		Scanner input = new Scanner(System.in);
		boolean invalid = false;
		String coordinate;
		int row, column;
		System.out.println("Turn " + Main.turnCount + "     Player's move.");
		System.out.println("Choose tile to shoot:");
		do {
			invalid = false;
			coordinate = input.nextLine();
			if (coordinate.charAt(0) - 65 < 0 || coordinate.charAt(0) - 65 > 7)
				invalid = true;
			if (coordinate.charAt(1) < '1' || coordinate.charAt(1) > '8')
				invalid = true;
			if (invalid)
				System.out.println("Coordinate out of bounds, try again");

		} while (invalid);

		column = coordinate.charAt(0) - 65;
		row = coordinate.charAt(1) - '1';

		if (board[row][column] == 's' && logicBoard[row][column] == WATER) {
			System.out.println("Ship hit!");
			logicBoard[row][column] = HIT_FRIENDLY_SHIP;
			enemyCount++;
		} else if (board[row][column] == 'g' && logicBoard[row][column] == WATER) {
			System.out.println("Grenade hit! Your next turn is skipped!");
			logicBoard[row][column] = HIT_FRIENDLY_NADE;
			updateBoard();
			Main.turnCount++;
			playerNadeHit = true;
			enemyShootRocket();
		} else if (board[row][column] == '~' && logicBoard[row][column] == WATER) {
			System.out.println("Nothing hit!");
			logicBoard[row][column] = HIT_NOTHING;
		} else if (board[row][column] == 'S' && logicBoard[row][column] == WATER) {
			System.out.println("Ship hit!");
			logicBoard[row][column] = HIT_ENEMY_SHIP;
			allyCount++;
		} else if (board[row][column] == 'G' && logicBoard[row][column] == WATER) {
			System.out.println("Grenade hit! Your next turn is skipped!");
			logicBoard[row][column] = HIT_ENEMY_NADE;
			updateBoard();
			Main.turnCount++;
			playerNadeHit = true;
			enemyShootRocket();
		} else {
			System.out.println("Oops, nothing happened.");
		}
		System.out.println("---------------------------------------------------------------");
		if (playerNadeHit == false) {
			Main.turnCount++;
			updateBoard();
		}
		playerNadeHit = false;

	}
	/**
	 * Randomly chooses a coordinate on the game board to shoot to.
	 */
	public void enemyShootRocket() {
		Random rand = new Random();
		int row, column;

		row = rand.nextInt(8);
		column = rand.nextInt(8);

		System.out.println("Turn " + Main.turnCount + "   CPU's turn");

		if (board[row][column] == 's' && logicBoard[row][column] == WATER) {
			System.out.println("Ship hit!");
			logicBoard[row][column] = HIT_FRIENDLY_SHIP;
			enemyCount++;
		} else if (board[row][column] == 'g' && logicBoard[row][column] == WATER) {
			System.out.println("Grenade hit! CPU's next turn is skipped!");
			logicBoard[row][column] = HIT_FRIENDLY_NADE;
			updateBoard();
			Main.turnCount++;
			cpuNadeHit = true;
			shootRocket();
		} else if (board[row][column] == '~' && logicBoard[row][column] == WATER) {
			System.out.println("Nothing hit!");
			logicBoard[row][column] = HIT_NOTHING;
		} else if (board[row][column] == 'S' && logicBoard[row][column] == WATER) {
			System.out.println("Ship hit!");
			logicBoard[row][column] = HIT_ENEMY_SHIP;
			allyCount++;
		} else if (board[row][column] == 'G' && logicBoard[row][column] == WATER) {
			System.out.println("Grenade hit! CPU's next turn is skipped!");
			logicBoard[row][column] = HIT_ENEMY_NADE;
			updateBoard();
			Main.turnCount++;
			cpuNadeHit = true;
			shootRocket();

		} else {
			System.out.println("Oops, nothing happened.");
		}
		System.out.println("---------------------------------------------------------------");
		if (cpuNadeHit == false) {
			Main.turnCount++;
			updateBoard();
		}
		cpuNadeHit = false;
	}

}
