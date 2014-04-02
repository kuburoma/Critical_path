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

	Project pr;
	int size;

	List<Integer> a = new ArrayList<Integer>();

	public BreedingStation(Project pr, Crossover crossoverType,
			Fitness fitnessType, Selection selection, int size) {
		this.pr = pr;
		this.size = size;
		this.crossoverType = crossoverType;
		this.fitnessType = fitnessType;
		this.selection = selection;
		oldGeneration = new Generation(pr, size);
		newGeneration = new Generation(pr, size);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
		//soutResult();
		
	}

	public void run(int numberOfTimes) {
		for (int i = 0; i < numberOfTimes; i++) {
			nextGeneration();
		}

	}

	private void nextGeneration() {
		selection();
		crossover();
		switchGenerations();
		schedule();
		evaluateFitness();
		soutResult();
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
			crossoverType.crossBreeding(oldGeneration.individuals[selected[i]],
					oldGeneration.individuals[selected[i - 1]]);
			
			newGeneration.addIndividual(crossoverType.getSon());
			newGeneration.addIndividual(crossoverType.getDaughter());
		}
	}

	private void switchGenerations() {
		oldGeneration = newGeneration.clone();
		newGeneration = new Generation(pr, size);
	}

	private void evaluateFitness() {
		fitnessType.calculateFitness(oldGeneration);
	}

	private void soutResult() {
		oldGeneration.soutFitnessStats();
	}

}
