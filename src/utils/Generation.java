package utils;

import generator.GenerationGenerator;

public class Generation {
	
	
	public int minSpan;
	public int maxSpan;
	public double averageFitness;
	public int totalFitness;
	public Individual[] individuals;
	public int[] fitness;	
	public double[] selection;
	private int size;
	private int numberOfIndividuals;
	private GenerationGenerator gg;
	private Project pr;
	


	public Generation(Project pr, int size) {
		this.pr = pr;
		this.size = size;
		numberOfIndividuals = size;
	}
	
	public int getSize(){
		return size;
	}

	public void initialPopulation() {
		gg = new GenerationGenerator(pr);
		individuals = new Individual[size];
		gg.generatePopulation(size).toArray(individuals);
	}

	public void addIndividual(Individual ind) {
		if (size == numberOfIndividuals) {
			return;
		}
		individuals[numberOfIndividuals] = ind;
		numberOfIndividuals++;
	}

	public boolean isFull() {
		if (size == numberOfIndividuals) {
			return true;
		} else {
			return false;
		}
	}

	public void schedule() {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i].scheduling();
		}
	}
	
	public void addFitness(int minSpan, int maxSpan, double averageFitness, int totalFitness, int[] fitness){
		this.minSpan = minSpan;
		this.maxSpan = maxSpan;
		this.averageFitness = averageFitness;
		this.totalFitness = totalFitness;
		this.fitness = fitness;
	}
	
	public void soutFitness(){
		for (int i = 0; i < fitness.length; i++) {
			System.out.print(fitness[i]+" ");
		}
		System.out.println();
	}
}
