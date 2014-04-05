package main;

import java.io.IOException;

import reader.Reader;
import reader.ReaderSolutions;
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
	private int nBest;
	private int nWorst;
	
	int size;


	public Benchmark(
			Crossover crossoverType,
			Fitness fitnessType, 
			Selection selection, 
			int size, 
			int nBest, 
			int nWorst) {
		this.size = size;
		this.crossoverType = crossoverType;
		this.fitnessType = fitnessType;
		this.selection = selection;
		this.nBest = nBest;
		this.nWorst = nWorst;

		//soutResult();
		//oldGeneration.soutFitness();
		
	}
	
	public void run() throws IOException{
		Reader r = new Reader();
		ReaderSolutions rs = new ReaderSolutions();
		Project pr;
		
		long avarageDeviation = 0;
		double numberOfSchedules = 0;
		int help;
		int[][] optimum;


			optimum = rs.read("resources/solutionsSM");

		
			for (int i = 1; i <= 48; i++) {
				for (int j = 1; j <= 10; j++) {
					if(optimum[i][j] == 0){
						continue;
					}
					
					//pr = r.read("j30/j3061_9");
					//pr = r.read("resources/j102_5");
					pr = r.read("j30sm/j30"+i+"_"+j+".sm");
					//pr = r.read("j30/j30"+i+"_"+j+".mm");
					if(!pr.isFeasible()){
						System.out.println("not");
						continue;
					}

					crossoverType.setProject(pr);
					help = runProject(pr,50000);
									
					System.out.println(i+"_"+j+" - op: "+optimum[i][j]+" my:"+help+" res: "+(help - optimum[i][j]));
					//System.out.println("-----------");
					avarageDeviation += help - optimum[i][j];
					numberOfSchedules++;
					
				}
			}

			double ad = (avarageDeviation * 1.0) / (numberOfSchedules * 1.0);
			System.out.println(ad);
		
	}
	
	public int runProject(Project pr, int numberOfSchedules) {

		int minSpan = Integer.MAX_VALUE;
		oldGeneration = new Generation(pr, size, nBest, nWorst);
		newGeneration = new Generation(pr, size, nBest, nWorst);
		oldGeneration.initialPopulation();
		schedule();
		evaluateFitness();
		
		
		for (int i = 1; i < numberOfSchedules / size; i++) {
			nextGeneration(pr);
			
			if(oldGeneration.minSpan < minSpan){
				minSpan = oldGeneration.minSpan;
			}
			//System.out.print(oldGeneration.numberOfSameIndividuals()+" - ");
			//System.out.println();
			//oldGeneration.soutFitness();
		}
		//System.out.println(oldGeneration.individuals);
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
		//oldGeneration.soutGene();
	}
	
	private void fillPopulation(){
		newGeneration.fillPopulation();
	}

	private void schedule() {
		oldGeneration.schedule();
	}
	
	private void elitismAndDeleteWorst(){
		oldGeneration.elitismAndDeleteWorst();
		newGeneration.addIndividuals(oldGeneration.elitism);
	}

	private void selection() {
		selected = selection.selectionNewPopulation(oldGeneration);

		
	}

	private void crossover() {
		for (int i = 1; i < selected.length; i += 2) {
			crossoverType.crossBreeding(oldGeneration.individuals.get(selected[i]),
					oldGeneration.individuals.get(selected[i - 1]));
			
			newGeneration.addIndividual(crossoverType.getSon());
			newGeneration.addIndividual(crossoverType.getDaughter());
		}
		
	}

	private void switchGenerations(Project pr) {
		oldGeneration = newGeneration.clone();
		newGeneration = new Generation(pr, size, nBest, nWorst);
	}

	private void evaluateFitness() {
		fitnessType.calculateFitness(oldGeneration);
	}

}
