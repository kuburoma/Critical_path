package main;

import java.util.ArrayList;
import java.util.List;

import crossover.Crossover;
import fitness.Fitness;
import utils.Generation;
import utils.Individual;
import utils.Project;

public class BreedingStation {
	
	Generation oldGeneration;
	Generation newGeneration;
	Individual best;
	
	Crossover crossoverType;
	Fitness fitnessType;
	
	int[][] selection;
	
	Project pr;
	int size;
	
	List<Integer> a = new ArrayList<Integer>();
	
	public BreedingStation(Project pr, Crossover crossoverType, Fitness fitnessType, int size){
		this.pr = pr;
		this.size = size;
		this.crossoverType = crossoverType;
		this.fitnessType = fitnessType;
		oldGeneration = new Generation(pr, size);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
	}
			
	public void nextGeneration(){
		schedule();
		selection();
		crossover();
		evaluateFitness();
	}
	
	
	private void schedule(){
		oldGeneration.schedule();		
	}
	
	private void selection(){
		
	}
	
	private void crossover(){
		selection = new int[size/2][2]; 
		for (int i = 0; i < (size / 2); i++) {
			//crossoverType.crossBreeding(father, mother)
			
		}
		
		
	}
	
	private void evaluateFitness(){
		fitnessType.calculateFitness(oldGeneration);
	}
	

}
