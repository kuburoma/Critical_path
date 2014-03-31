package utils;

import generator.GenerationGenerator;

public class Generation {
	
	public static int nthGeneration = 0;
	public int minSpan;
	public int maxSpan;
	public double averageFitness;
	public int totalFitness;
	public Individual[] individuals;
	public int[] fitness;	
	
	public double[] selection;
	
	public int size;
	public int numberOfIndividuals;
	public GenerationGenerator gg;
	public Project pr;
	


	public Generation(Project pr, int size) {
		this.pr = pr;
		this.size = size;
		numberOfIndividuals = 0;
		nthGeneration++;
		individuals = new Individual[size];
	}
	
	public Generation clone(){
		Generation a = new Generation(pr,size);
		a.minSpan = this.minSpan;
		a.maxSpan = this.maxSpan;
		a.averageFitness = this.averageFitness;
		a.totalFitness = this.totalFitness;
		a.individuals = this.individuals;
		a.fitness = this.fitness;	
		
		a.selection = this.selection;
		
		a.numberOfIndividuals = this.numberOfIndividuals;
		a.gg = this.gg;
		return a;
	}
	
	public int getSize(){
		return size;
	}

	public void initialPopulation() {
		gg = new GenerationGenerator(pr);
		gg.generatePopulation(size).toArray(individuals);
	}

	public void addIndividual(Individual ind) {
		if (size == numberOfIndividuals) {
			System.out.println("full");
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
	
	public void addSelection(){}
	
	public void soutFitnessStats(){
		System.out.println("Generation : "+nthGeneration);
		System.out.println("    minSpan: "+minSpan);
		System.out.println("    maxSpan: "+maxSpan);
		System.out.println("     avgFit: " +averageFitness);
	}
	
	public void soutFitness(){
		for (int i = 0; i < fitness.length; i++) {
			System.out.print(fitness[i]+" ");
		}
		System.out.println();
	}
}
