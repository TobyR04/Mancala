package mancala;

import java.util.*;

public class Board {

	private Player playerOne;
	private Player playerTwo;
	private Player currPlayer;
	private Pit[] boardOne = new Pit[6];
	private Pit[] boardTwo = new Pit[6];
	private Store storeOne;
	private Store storeTwo;
	private Scanner scan = new Scanner(System.in);

	/**
	 * set up board and players
	 */
	public void initialize() {

		// each pit is given 4 marbles and assigned a pit id number
		for (int i = 0; i < 6; i++) {
			boardOne[i] = new Pit(6 - i, 4);
			boardTwo[i] = new Pit(i + 7, 4);
		}

		// get player name and construct player
		System.out.print("Player 1, what is your name? ");
		String name = scan.nextLine();
		System.out.println();
		playerOne = new Player(name, boardOne);

		System.out.print("Player 2, what is your name? ");
		name = scan.nextLine();

		playerTwo = new Player(name, boardTwo);

		System.out.println();
		// create stores for each player. tells player about store and assigns current
		// player to player one
		storeOne = new Store(playerOne, 0);
		storeTwo = new Store(playerTwo, 0);
		playerOne.setStore(storeOne);
		playerTwo.setStore(storeTwo);
		currPlayer = playerOne;
		print();
		System.out.println();
		System.out.println(currPlayer.getName() + " goes first.");

	}

	/**
	 * 
	 * @return true when one player side is empty. move marbles from other player to
	 *         the his store.
	 */
	public boolean done() {

		if (playerOne.isEmpty()) {
			int marbles = playerTwo.empty();
			storeTwo.add(marbles);
		}

		else if (playerTwo.isEmpty()) {
			int marbles = playerOne.empty();
			storeOne.add(marbles);

		} else {
			return false;
		}

		return true;

	}

	/**
	 * print out the winner
	 */
	public void winner() {
		if (storeOne.getStore() > storeTwo.getStore()) {
			System.out.println("Player " + playerOne.getName() + " is the winner!");
		}

		else if (storeOne.getStore() == storeTwo.getStore()) {
			System.out.println("There was a tie.");

		}

		else {
			System.out.println("Player " + playerTwo.getName() + " is the winner!");
		}
	}

	/**
	 * plays the game
	 */
	public void start() {

		// loop until the game ends
		while (!done()) {

			// current player keeps his turn. boolean is returned if he gets another turn
			boolean keepTurn = turn();


			// change current player if he doesnt get another turn
			if (!keepTurn) {
				if (currPlayer == playerOne) {
					currPlayer = playerTwo;
				} else {
					currPlayer = playerOne;
				}
			}
			print();
			System.out.println();
			if (keepTurn) {
				System.out.println(currPlayer.getName() + " gets another turn.");
			}
		}
		System.out.println("Game over!");
		System.out.println();
		print();
		System.out.println();
		// see who the winner is
		winner();
	}

	/**
	 * prints the board
	 */
	public void print() {

		// prints player one's board
		System.out.print("          ");
		for (int x = 0; x < 6; x++) {
			System.out.print("   " + boardOne[x] + "  ");

		}
		// print the middle line with the two stores
		System.out.println();
		System.out.print(storeOne);
		System.out.print("                                                            ");

		System.out.println(storeTwo);

		// prints player two's board
		System.out.print("          ");
		for (int x = 0; x < 6; x++) {
			System.out.print("   " + boardTwo[x] + "  ");
		}
		System.out.println();

	}

	/**
	 * 
	 * @return which pit number the player wants to choose
	 */
	public int getPitNum() {
		int pit = 0;
		System.out.println();
		System.out.print(currPlayer.getName() + ", Which pit would you like to take from? ");
		pit = scan.nextInt();
		while (pit < 1 || pit > 12) {

			System.out.println("Valid pit numbers are between 1 and 12.");
			System.out.println();
			System.out.print(currPlayer.getName() + ", Which pit would you like to take from? ");
			pit = scan.nextInt();
			System.out.println();
		}
		return pit;
	}

	/**
	 * 
	 * @param pitNum passing in pit number
	 * @return pit associated with this number
	 */
	public Pit getPit(int pitNum) {
		if (pitNum <= 6) {
			return boardOne[6 - pitNum];
		}
		return boardTwo[pitNum - 7];
	}

	/**
	 * asks the player for pit number and then moves marbles around
	 * 
	 * @return index of pit which is the last pit that the marble is placed. if the
	 *         last marble is put in player's own store, -1 is returned
	 */
	public int placeMarble() {

		// gets pit number
		int pitNum = getPitNum();

		// get pit for the pit number
		Pit pit = getPit(pitNum);
		while(pit.getMarbles()==0) {
			System.out.print("There are no marbles in there. Choose another pit. ");
			// gets pit number
			pitNum = getPitNum();
			// get pit for the pit number
			pit = getPit(pitNum);
		}

		// removes marble from the pit
		int marbles = pit.remove();

		// set to true if last marble is put in player store
		boolean lastInStore = false;
		// index is initialized to starting pit number
		int index = pitNum;

		// loop until all marbles are placed
		while (marbles != 0) {
			// increment to next index
			index++;
			// when gets to 13, resets to 1
			if (index == 13) {
				index = 1;
			}
			// if index is 7 and that's the player store, marble is put in store before
			// there's a marble put in pit 7
			if ((index == 7) && (currPlayer.getStore() == storeOne)) {
				storeOne.add(1);
				marbles--;

				// last marble is placed. loop ends
				if (marbles == 0) {
					lastInStore = true;
					break;
				}
			}

			// if index is 1 and thats the player store, marble is put in store before put
			// marble in pit 1
			if ((index == 1) && (currPlayer.getStore() == storeTwo)) {
				storeTwo.add(1);
				marbles--;

				// if last marble is placed, loop ends
				if (marbles == 0) {
					lastInStore = true;
					break;
				}
			}

			// get the pit for current index and add marble to pit
			pit = getPit(index);
			pit.add(1);
			marbles--;
		}

		// returns -1 if last marble placed in store
		if (lastInStore) {
			return -1;
		}
		// returns index of pit where last marble placed
		return index;
	}

	/**
	 * 
	 * @param pitIndex pit number
	 * @return pit index of pit opposite the pit
	 */
	public int getOppositePitIndex(int pitIndex) {
		switch (pitIndex) {
		case 1:
			return 12;
		case 2:
			return 11;
		case 3:
			return 10;
		case 4:
			return 9;
		case 5:
			return 8;
		case 6:
			return 7;
		case 7:
			return 6;
		case 8:
			return 5;
		case 9:
			return 4;
		case 10:
			return 3;
		case 11:
			return 2;
		case 12:
			return 1;
		}
		return 0;
	}

	/**
	 * 
	 * @return true if it's the current players turn
	 */
	public boolean turn() {

		// asked player for pit index and places marbles
		int pitIndex = placeMarble();
		// check to see if last marble placed in players store
		if (pitIndex == -1) {
			return true;
		}
		// get pit number for last marble placed
		Pit finalPit = getPit(pitIndex);
		// if final pit has 1 marble in it, it used to have 0. it captured opposite
		// marbles
		if (finalPit.getMarbles() == 1) {
			int capture = getOppositePitIndex(pitIndex);
			Pit oppositePit = getPit(capture);
			int marbles = oppositePit.remove();
			currPlayer.getStore().add(marbles);
		}

		// return false if its the next players turn
		return false;

	}
}
