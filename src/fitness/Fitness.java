package fitness;

import utils.Generation;

abstract public class Fitness {
	
	protected int minSpan;
	protected int maxSpan;
	protected int totalFitness;
	protected double averageFitness;
	
	protected int minSpanTask;
	protected Generation generation;
	protected int size;
	protected int[] fitness;
	
	public void calculateFitness(Generation gen){
		this.generation = gen;
		size = gen.getSize();
		fitness = new int[size];
		minSpan = Integer.MAX_VALUE;
		maxSpan = 0;
		minSpanTask = 0;
		totalFitness = 0;
		totalFitness = 0;
		calculate();		
	}

	abstract protected void calculate(); 
	

}
