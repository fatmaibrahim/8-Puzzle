
public class State {
	private int[][] stateShape;
	private int heuristic,cost;
	public State(int[][] state){
		this.stateShape=state;
	}
	
	
	/**
	 * @return the heuristic
	 */
	public int calcFun() {
		return heuristic+cost;
	}
	/**
	 * @return the heuristic
	 */
	public int getHeuristic() {
		return heuristic;
	}
	/**
	 * @param heuristic the heuristic to set
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	/**
	 * @return the stateShape
	 */
	public int[][] getStateShape() {
		return stateShape;
	}
	/**
	 * @param fun the fun to set
	 */
	

}
