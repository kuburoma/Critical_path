package utils;

import generator.GenerationGenerator;

public class Generation {

	Individual[] generation;
	int size;
	int numberOfIndividuals;
	GenerationGenerator gg;
	Project pr;
	
	public Generation(Project pr, int size){
		this.size = size;
		numberOfIndividuals = 0;
	}
	
	public void initialPopulation(){
		gg = new GenerationGenerator(pr);
		generation = new Individual[size];
		gg.generatePopulation(size).toArray(generation);
	}
	
	public void addIndividual(Individual ind){
		if(size == numberOfIndividuals){
			return;
		}
		generation[numberOfIndividuals] = ind;
		numberOfIndividuals++;		
	}
	
	public boolean isFull(){
		if(size == numberOfIndividuals){
			return true;
		}else{
			return false;
		}
	}
}
