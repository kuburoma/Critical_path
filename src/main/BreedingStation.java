package main;

import java.util.ArrayList;
import java.util.List;

import crossover.Crossover;
import fitness.Fitness;
import selection.Selection;
import utils.Generation;
import utils.Individual;
import utils.Project;

public class BreedingStation {

	public Generation oldGeneration;
	public Generation newGeneration;
	public Individual best;

	public Crossover crossoverType;
	public Fitness fitnessType;
	public Selection selection;
	private int[] selected;
	private List<Double> avgFitness;

	Project pr;
	int size;
	int minSpan;

	List<Integer> a = new ArrayList<Integer>();

	public BreedingStation(Project pr, Crossover crossoverType, Fitness fitnessType, Selection selection, int size) {
		this.pr = pr;
		this.size = size;
		this.crossoverType = crossoverType;
		this.fitnessType = fitnessType;
		this.selection = selection;
		avgFitness = new ArrayList<Double>();
		oldGeneration = new Generation(pr, size, 0, 0);
		newGeneration = new Generation(pr, size, 0, 0);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
		avgFitness.add(oldGeneration.averageFitness);
		minSpan = Integer.MAX_VALUE;
		crossoverType.setProject(pr);
		//soutResult();
		//oldGeneration.soutFitness();
		
	}
	
	public List<Double> runSchedules(int numberOfSchedules) {
		for (int i = 1; i < numberOfSchedules / size; i++) {
			nextGeneration();
			avgFitness.add(oldGeneration.averageFitness);
			if(oldGeneration.minSpan < minSpan){
				minSpan = oldGeneration.minSpan;
			}
		}
		return avgFitness;
	}

	public List<Double> run(int numberOfTimes) {
		for (int i = 1; i < numberOfTimes; i++) {
			nextGeneration();
			avgFitness.add(oldGeneration.averageFitness);
			
		}
		return avgFitness;
	}

	
	
	private void nextGeneration() {
		selection();
		crossover();
		switchGenerations();
		schedule();
		evaluateFitness();
		//soutResult();
		
		//oldGeneration.sortIndividuals();
		//oldGeneration.soutFitness();
	}

	private void schedule() {
		oldGeneration.schedule();
	}

	private void selection() {
		selected = selection.selectionNewPopulation(oldGeneration);
	}

	private void crossover() {
		for (int i = 1; i < size; i += 2) {
			crossoverType.crossBreeding(oldGeneration.individuals.get(selected[i]),
					oldGeneration.individuals.get(selected[i - 1]));
			
			newGeneration.addIndividual(crossoverType.getSon());
			newGeneration.addIndividual(crossoverType.getDaughter());
		}
	}

	private void switchGenerations() {
		oldGeneration = newGeneration.clone();
		newGeneration = new Generation(pr, size, 0, 0);
	}

	private void evaluateFitness() {
		fitnessType.calculateFitness(oldGeneration);
	}

}
