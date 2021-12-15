package mancala;


public class Store {
	
	private Player name;
	private int store; //number of marbles in store
	
	public Store(Player name, int store) {
		this.name=name;
		this.store=store;
	}
	
	public void setName(Player name) {
		this.name=name;
	}
	
	public void setStore(int store) {
		this.store=store;
	}
	
	public int getStore() {
		
		return store;
	}
	
	public void add(int marbles) {
		this.store+=marbles;
	}
	public String toString() {
		return "Store " + name.getName() + ": " + store;
	}

}
