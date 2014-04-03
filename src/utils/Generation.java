package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import generator.GenerationGenerator;

public class Generation {

	public static int nthGeneration = 0;
	public int minSpan;
	public int maxSpan;
	public double averageFitness;
	public int totalFitness;
	public List<Individual> individuals;

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
		individuals = new ArrayList<Individual>(size);
	}

	public Generation clone() {
		Generation a = new Generation(pr, size);
		a.minSpan = this.minSpan;
		a.maxSpan = this.maxSpan;
		a.averageFitness = this.averageFitness;
		a.totalFitness = this.totalFitness;
		a.individuals = this.individuals;
		a.selection = this.selection;
		a.numberOfIndividuals = this.numberOfIndividuals;
		a.nthGeneration--;
		
		a.gg = this.gg;
		return a;
	}

	public int getSize() {
		return size;
	}

	public void initialPopulation() {
		gg = new GenerationGenerator(pr);
		individuals = gg.generatePopulation(size);
	}

	public void addIndividual(Individual ind) {
		if (size == numberOfIndividuals) {
			System.out.println("full");
			return;
		}
		individuals.add(ind);
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
		for (int i = 0; i < individuals.size(); i++) {
			individuals.get(i).scheduling();
		}
	}

	public void addFitness(int minSpan, int maxSpan, double averageFitness,
			int totalFitness) {
		this.minSpan = minSpan;
		this.maxSpan = maxSpan;
		this.averageFitness = averageFitness;
		this.totalFitness = totalFitness;
	}

	public void setFitnessTo(int pos, int fitness) {
		individuals.get(pos).setFitness(fitness);
	}

	public int getFitnessFrom(int pos) {
		return individuals.get(pos).getFitness();
	}

	public boolean isFeasible(int pos) {
		return individuals.get(pos).isFeasible();
	}

	public void addSelection() {
	}
	
	public void sortIndividuals(){
		Collections.sort(individuals);
	}

	public void soutFitnessStats() {
		System.out.println("Generation : " + (nthGeneration-2));
		System.out.println("    minSpan: " + minSpan);
		System.out.println("    maxSpan: " + maxSpan);
		System.out.println("     avgFit: " + averageFitness);
	}

	public void soutFitness() {
		for (Iterator<Individual> iterator = individuals.iterator(); iterator
				.hasNext();) {
			iterator.next().getFitness();
			System.out.print(iterator.next().getFitness() + " ");
		}
		System.out.println();
	}

}
