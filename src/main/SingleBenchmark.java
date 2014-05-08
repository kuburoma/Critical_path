package main;

import java.io.IOException;

import reader.Reader;
import reader.ReaderSolutions;
import selection.Selection;
import utils.Generation;
import utils.Individual;
import utils.InstancesOptimums;
import utils.Project;
import crossover.Crossover;
import fitness.Fitness;

public class SingleBenchmark {
	public Generation oldGeneration;
	public Generation newGeneration;
	public Individual best;

	public Crossover crossoverType;
	public Fitness fitnessType;
	public Selection selection;
	private int[] selected;
	private int nBest;
	private int nWorst;
	Individual ind;
	int scheduling;
	int size;

	public SingleBenchmark(Crossover crossoverType, Fitness fitnessType,
			Selection selection, int size, int nBest, int nWorst, int scheduling) {
		this.size = size;
		this.crossoverType = crossoverType;
		this.fitnessType = fitnessType;
		this.selection = selection;
		this.nBest = nBest;
		this.nWorst = nWorst;
		this.scheduling = scheduling;
		// soutResult();
		// oldGeneration.soutFitness();

	}

	public String run(String mode, String instances, int schedules,
			int repeate, int i, int j) throws IOException {
		Reader r = new Reader();
		ReaderSolutions rs = new ReaderSolutions();
		Project pr;

		long avarageDeviation = 0;
		double numberOfSchedules = 0;
		int help;
		InstancesOptimums op;

		op = rs.read("resources/" + mode + "/" + instances + "/solution");

		if (!op.contains(i, j)) {
			return "Dont have optimum.";
		}

		pr = r.read("resources/" + mode + "/" + instances + "/" + instances
				+ "" + i + "_" + j + "." + mode);

		// pr = r.read("j30/j30"+i+"_"+j+".mm");
		if (!pr.isFeasible()) {
			// System.out.println("not");
			return "Not feasible.";
		}

		int min = Integer.MAX_VALUE;
		for (int k = 0; k < repeate; k++) {
			// System.out.println(i+" - "+j+" - "+k);
			crossoverType.setProject(pr);
			help = runProject(pr, schedules);
			if (help == Integer.MAX_VALUE) {
				continue;
			}
			if (min > help) {
				min = help;
			}
		}

		return "Feasible optimum: " + op.getOptimum(i, j) + " result: " + min;
	}

	public int runProject(Project pr, int numberOfSchedules) {

		int minSpan = Integer.MAX_VALUE;
		oldGeneration = new Generation(pr, size, nBest, nWorst, scheduling);
		newGeneration = new Generation(pr, size, nBest, nWorst, scheduling);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
		minSpan = oldGeneration.minSpan;
		System.out.println("1: " + minSpan);
		// System.out.println("---------------------");

		for (int i = 1; i < numberOfSchedules / size; i++) {
			nextGeneration(pr);

			if (oldGeneration.minSpan < minSpan) {
				minSpan = oldGeneration.minSpan;
				ind = oldGeneration.ind;
			}

			System.out.println((i + 1) + ": " + minSpan);

			// System.out.print(oldGeneration.numberOfSameIndividuals()+" - ");
			// System.out.println();
			// oldGeneration.soutFitness();
		}

		// System.out.println(oldGeneration.individuals);
		return minSpan;
	}

	private void nextGeneration(Project pr) {
		long startTime;
		long stopTime;
		long elapsedTime;
		
		startTime = System.nanoTime();
		elitismAndDeleteWorst();
		stopTime = System.nanoTime();
		System.out.println("Elitism        : "+(stopTime - startTime));
		 
		startTime = System.nanoTime();
		selection();		
		stopTime = System.nanoTime();
		System.out.println("selection      : "+(stopTime - startTime));
		
		startTime = System.nanoTime();
		crossover();
		stopTime = System.nanoTime();
		System.out.println("crossover      : "+(stopTime - startTime));
		
		startTime = System.nanoTime();
		fillPopulation();
		stopTime = System.nanoTime();
		System.out.println("fillPopulation  : "+(stopTime - startTime));
		
		startTime = System.nanoTime();
		switchGenerations(pr);
		stopTime = System.nanoTime();
		System.out.println("Switch generation: "+(stopTime - startTime));
		
		startTime = System.nanoTime();
		schedule();
		stopTime = System.nanoTime();
		System.out.println("Schedule          : "+(stopTime - startTime));
		
		startTime = System.nanoTime();
		evaluateFitness();
		stopTime = System.nanoTime();
		System.out.println("Fitness           : "+(stopTime - startTime));
		
		System.out.println("--------------------------------");
		// oldGeneration.soutGene();
	}

	private void fillPopulation() {
		newGeneration.fillPopulation();
	}

	private void schedule() {
		oldGeneration.schedule();
	}

	private void elitismAndDeleteWorst() {
		oldGeneration.elitismAndDeleteWorst();
		newGeneration.addIndividuals(oldGeneration.elitism);
	}

	private void selection() {
		selected = selection.selectionNewPopulation(oldGeneration);

	}

	private void crossover() {
		for (int i = 1; i < selected.length; i += 2) {
			crossoverType.crossBreeding(
					oldGeneration.individuals.get(selected[i]),
					oldGeneration.individuals.get(selected[i - 1]),
					oldGeneration.bestOfGeneration);

			newGeneration.addIndividual(crossoverType.getSon());
			newGeneration.addIndividual(crossoverType.getDaughter());
		}

	}

	private void switchGenerations(Project pr) {
		oldGeneration = newGeneration.clone();
		newGeneration = new Generation(pr, size, nBest, nWorst, scheduling);
	}

	private void evaluateFitness() {
		fitnessType.calculateFitness(oldGeneration);
	}

}
