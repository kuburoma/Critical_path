package fitness;

import utils.Generation;

abstract public class Fitness {
	
	protected int minSpan = Integer.MAX_VALUE;
	protected int maxSpan = 0;
	protected int minSpanTask = 0;
	protected Generation generation;
	protected double averageFitness = 0;
	protected int size;
	protected int[] fitness;
	
	public Fitness(){

	}
	
	public void calculateFitness(Generation gen){
		this.generation = gen;
		size = gen.getSize();
		calculate();		
	}
	
	public int[] fitness(){
		return fitness;
	}
	
	abstract protected void calculate(); 
	

}
