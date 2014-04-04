package main;

import java.io.IOException;

import reader.Reader;
import selection.Selection;
import utils.Generation;
import utils.Individual;
import utils.Project;
import crossover.Crossover;
import fitness.Fitness;

public class Benchmark {
	public Generation oldGeneration;
	public Generation newGeneration;
	public Individual best;

	public Crossover crossoverType;
	public Fitness fitnessType;
	public Selection selection;
	private int[] selected;

	int size;


	public Benchmark(Crossover crossoverType,
			Fitness fitnessType, Selection selection, int size) {
		this.size = size;
		this.crossoverType = crossoverType;
		this.fitnessType = fitnessType;
		this.selection = selection;


		//soutResult();
		//oldGeneration.soutFitness();
		
	}
	
	public void run(){
		Reader r = new Reader();
		Project pr;
		
		long avarageDeviation = 0;
		double numberOfSchedules = 0;
		int help;

		try {
			for (int i = 1; i <= 64; i++) {
				for (int j = 1; j <= 10; j++) {
					
					pr = r.read("j30/j30"+i+"_"+j);
					if(!pr.isFeasible()){
						continue;
					}

					crossoverType.setProject(pr);
					help = runProject(pr,10000);
					
					//System.out.println(i+" "+j);
					//System.out.println(help);					
					//System.out.println(pr.optimumSpan);
					//System.out.println(help - pr.optimumSpan);
					//System.out.println("-----------");
					avarageDeviation += help - pr.optimumSpan;
					numberOfSchedules++;
					
				}
			}
			double ad = (avarageDeviation * 1.0) / (numberOfSchedules * 1.0);
			System.out.println(ad);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int runProject(Project pr, int numberOfSchedules) {

		int minSpan = Integer.MAX_VALUE;
		oldGeneration = new Generation(pr, size);
		newGeneration = new Generation(pr, size);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
		
		
		for (int i = 1; i < numberOfSchedules / size; i++) {
			nextGeneration(pr);
			if(oldGeneration.minSpan < minSpan){
				minSpan = oldGeneration.minSpan;
			}
		}
		return minSpan;
	}
	
	private void nextGeneration(Project pr) {
		selection();
		crossover();
		switchGenerations(pr);
		schedule();
		evaluateFitness();
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

	private void switchGenerations(Project pr) {
		oldGeneration = newGeneration.clone();
		newGeneration = new Generation(pr, size);
	}

	private void evaluateFitness() {
		fitnessType.calculateFitness(oldGeneration);
	}

}
