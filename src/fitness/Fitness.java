package fitness;

import utils.Individual;

abstract public class Fitness {
	
	protected int minSpan = Integer.MAX_VALUE;
	protected int maxSpan = 0;
	protected int minSpanTask = 0;
	protected Individual[] generation;
	protected double averageFitness = 0;
	protected int size;
	
	public Fitness(){

	}
	
	public void calculateFitness(Individual[] gen){
		this.generation = gen;
		size = gen.length;
		calculate();		
	}
	
	abstract protected void calculate(); 
	

}
