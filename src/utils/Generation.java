package utils;

import generator.GenerationGenerator;

public class Generation {

	Individual[] generation;
	int size;
	int numberOfIndividuals;
	public double averageFitness = 0;
	GenerationGenerator gg;
	Project pr;
	public int minSpan = Integer.MAX_VALUE;
	public int maxSpan = 0;
	public int minSpanTask = 0;

	public Generation(Project pr, int size) {
		this.pr = pr;
		this.size = size;
		numberOfIndividuals = size;
	}

	public void initialPopulation() {
		gg = new GenerationGenerator(pr);
		generation = new Individual[size];
		gg.generatePopulation(size).toArray(generation);
	}

	public void addIndividual(Individual ind) {
		if (size == numberOfIndividuals) {
			return;
		}
		generation[numberOfIndividuals] = ind;
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
		for (int i = 0; i < generation.length; i++) {
			generation[i].scheduling();
		}
	}

	public void calculateFitness() {
		int helpSpan;
		for (int i = 0; i < generation.length; i++) {
			if (generation[i].feasible) {
				helpSpan = generation[i].calcualteFitnessFeasible();
				averageFitness += helpSpan;
				if(minSpan > helpSpan){
					minSpan = helpSpan;
					minSpanTask = i;
				}
				if(maxSpan < helpSpan){
					maxSpan = helpSpan;
				}
			}
		}
		
		for (int i = 0; i < generation.length; i++) {
			if (!generation[i].feasible) {
				averageFitness += generation[i].calcualteFitnessInfeasible(maxSpan);
			}
		}
		
		averageFitness = averageFitness / size; 
	}
}
