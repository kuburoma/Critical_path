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

public class Benchmark {
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

	public Benchmark(Crossover crossoverType, Fitness fitnessType,
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

	public double run(String mode, String instances, int schedules, int repeate)
			throws IOException {
		Reader r = new Reader();
		ReaderSolutions rs = new ReaderSolutions();
		Project pr;

		long avarageDeviation = 0;
		double numberOfSchedules = 0;
		int help;
		InstancesOptimums op;

		op = rs.read("resources/" + mode + "/" + instances + "/solution");

		// op = new InstancesOptimums(1, 1, new int[1][1]);

		long startTime = System.currentTimeMillis();
		long readTime = 0;

		for (int i = 1; i <= op.numberOfSets; i++) {
			for (int j = 1; j <= op.numberOfInstances; j++) {
				if (!op.contains(i, j)) {
					continue;
				}

				long startReadTime = System.currentTimeMillis();
				pr = r.read("resources/" + mode + "/" + instances + "/"
						+ instances + "" + i + "_" + j + "." + mode);
				long endReadTime = System.currentTimeMillis();
				readTime += endReadTime - startReadTime;

				if (!pr.isFeasible()) {
					continue;
				}

				int min = Integer.MAX_VALUE;
				for (int k = 0; k < repeate; k++) {
					crossoverType.setProject(pr);
					help = runProject(pr, schedules);
					if (help == Integer.MAX_VALUE) {
						continue;
					}
					if (min > help) {
						min = help;
					}
				}

				if (min == Integer.MAX_VALUE) {
					continue;
				}
				avarageDeviation += min - op.getOptimum(i, j);
				numberOfSchedules++;

			}
		}

		double ad = (avarageDeviation * 1.0) / (numberOfSchedules * 1.0);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime - readTime;
		//System.out.println("read time    : " + readTime);
		//System.out.println("Required time: " + elapsedTime);
		return ad;
	}

	public int runProject(Project pr, int numberOfSchedules) {

		int minSpan = Integer.MAX_VALUE;
		oldGeneration = new Generation(pr, size, nBest, nWorst, scheduling);
		newGeneration = new Generation(pr, size, nBest, nWorst, scheduling);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
		minSpan = oldGeneration.minSpan;

		for (int i = 1; i < numberOfSchedules / size; i++) {
			nextGeneration(pr);

			if (oldGeneration.minSpan < minSpan) {
				minSpan = oldGeneration.minSpan;
				ind = oldGeneration.ind;
			}
		}

		return minSpan;
	}

	private void nextGeneration(Project pr) {

		elitismAndDeleteWorst();
		selection();
		crossover();
		fillPopulation();
		switchGenerations(pr);
		schedule();

		evaluateFitness();
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
