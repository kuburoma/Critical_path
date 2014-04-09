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
	public List<Individual> elitism;
	public Individual ind;
	public Individual bestOfGeneration;

	public double[] selection;

	public int size;
	public int numberOfIndividuals;
	public GenerationGenerator gg;
	public Project pr;
	public int nBest;
	public int nWorst;

	public Generation(Project pr, int size, int nBest, int nWorst) {
		this.pr = pr;
		this.size = size;
		numberOfIndividuals = 0;
		nthGeneration++;
		individuals = new ArrayList<Individual>(size);
		elitism = new ArrayList<Individual>(size);
		this.nBest = nBest;
		this.nWorst = nWorst;
	}

	public Generation clone() {
		Generation a = new Generation(pr, size, nBest, nWorst);
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

	public void elitismAndDeleteWorst() {
		if(0 > nBest || 0 > nWorst || nBest + nWorst >= this.size){
			return;
		}
		
		Collections.sort(individuals);
		/*
		
		for (int i = 0; i < individuals.size(); i++) {
			System.out.print(individuals.get(i).getFitness()+" ");

		}
		
		System.out.println();
		System.out.println("-----------------");
		*/
		
		bestOfGeneration = individuals.get(0);
		
		for (int i = 0; i < nBest; i++) {
			elitism.add(individuals.get(i));
			//System.out.print(individuals.get(i).getFitness()+" ");

		}
		
		//System.out.println();
		//System.out.println("-----------------");
		
		//System.out.println(size+" "+nWorst);
		
		int j = 0;
		for (Iterator<Individual> iterator = individuals.iterator(); iterator.hasNext();) {
			iterator.next();
			if(j >= size - nWorst){
				iterator.remove();
			}
			j++;
		}
		
		//System.out.println("    elitism: "+elitism.size());
		//System.out.println("individuals: "+individuals.size());
		Collections.shuffle(individuals);
	}

	public int getSize() {
		return size;
	}

	public void initialPopulation() {
		gg = new GenerationGenerator(pr);
		individuals = gg.generatePopulation(size);
	}

	public void fillPopulation(){
		gg = new GenerationGenerator(pr);
		List<Individual> l = gg.generatePopulation(nWorst);
		
		//System.out.println(" ind:"+individuals.size());
		//System.out.println("full:"+l.size());
		
		//individuals.addAll(l);
		addIndividuals(l);
		
		//System.out.println(" ind:"+individuals.size());
		//System.out.println("------");
	}
	
	public void addIndividual(Individual ind) {
		if (size == numberOfIndividuals) {
			System.out.println("full");
			return;
		}
		individuals.add(ind);
		numberOfIndividuals++;
	}
	
	public void addIndividuals(List<Individual> ind) {
		if (size == numberOfIndividuals+ind.size() - 1) {
			System.out.println("full");
			return;
		}
		individuals.addAll(ind);
		numberOfIndividuals += ind.size();
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
			int totalFitness, Individual ind) {
		this.minSpan = minSpan;
		this.maxSpan = maxSpan;
		this.averageFitness = averageFitness;
		this.totalFitness = totalFitness;
		this.ind = ind;
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
	
	public int numberOfSameIndividuals(){
		int sameInd = 0;
		for (int i = 0; i < individuals.size(); i++) {
			int help = 0;
			for (int j = 0; j < individuals.size(); j++) {
				
				if(individuals.get(i).isSame(individuals.get(j))){
					help++;
				}
			}
			help--;
			if(help > sameInd){
				sameInd = help;
			}
		}
		
		return sameInd;
	}

	public void soutFitnessStats() {
		System.out.println("Generation : " + (nthGeneration - 2));
		System.out.println("    minSpan: " + minSpan);
		System.out.println("    maxSpan: " + maxSpan);
		System.out.println("     avgFit: " + averageFitness);
	}
	
	public void soutGene() {
		for (Iterator<Individual> iterator = individuals.iterator(); iterator
				.hasNext();) {
			System.out.print(iterator.next().getGene() + " ");
		}
		System.out.println();
	}

	public void soutFitness() {
		for (Iterator<Individual> iterator = individuals.iterator(); iterator
				.hasNext();) {
			System.out.print(iterator.next().getFitness() + " ");
		}
		System.out.println();
	}

}
