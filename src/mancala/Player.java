package mancala;

public class Player {

	private String name;
	private Store store;
	private Pit[] pits;

	public Player(String name, Pit[] pits) {
		this.name = name;
		this.pits = pits;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Store getStore() {
		return store;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return true if all pits of the player are empty
	 */
	public boolean isEmpty() {
		for (int i = 0; i < pits.length; i++) {
			if (pits[i].getMarbles() != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return all marbles from pits from his side. pits are now empty
	 */
	public int empty() {
		int marbles = 0;
		for (int i = 0; i < pits.length; i++) {
			marbles += pits[i].remove();

		}

		return marbles;
	}
}
