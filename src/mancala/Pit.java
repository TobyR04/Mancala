package mancala;

public class Pit {
	
	private int pitIndex;
	private int marbles;
	
	public Pit(int pitIndex, int marbles) {
		this.pitIndex=pitIndex;
		this.marbles=marbles;
	}
	
	public void setPitIndex(int pitIndex) {
		this.pitIndex=pitIndex;
	}
	
	public void setMarbles(int marbles) {
		this.marbles=marbles;
				
	}
	
	
	 public int getPitIndex() {
		 return pitIndex; 
		 }
	 
	  public int getMarbles() { 
		 return marbles; 
		 }
	 
	public void add(int marbles) {
		this.marbles+=marbles;
	}
	
	public int remove() {
		int m = marbles;
		marbles=0;
		return m;
	}
	
	public String toString() {
		
		return String.format("%2d:%2d",pitIndex,marbles);
	}

}
