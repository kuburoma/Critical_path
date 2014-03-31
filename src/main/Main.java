package main;

import utils.Generation;
import utils.Project;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int number_of_tasks = 9;
		
		int[][] precedence_connection = {
				{}, /* 0 */
				{ 0}, /* 1 */
				{ 0}, /* 2 */
				{ 1}, /* 3 */
				{ 1}, /* 4 */
				{ 1,  2}, /* 5 */
				{ 3,  4}, /* 6 */
				{ 5}, /* 7 */
				{ 6,  7}, /* 8 */
		};
		
		int[] number_of_modes_in_task = {
				2, 2, 2, 
				2, 2, 2, 
				2, 2, 2, 
		};
		
		int number_of_renewable_resources= 1;
		
		int number_of_nonrenewable_resources = 1;

		
		/* table[task][mode][resource] */
		int[][][] renewable_resources = {
				{{0}, {0}},
				{{5}, {6}}, 
				{{2}, {1}},
				{{3}, {2}},
				{{1}, {2}},
				{{3}, {5}},
				{{2}, {3}},
				{{3}, {1}},
				{{2}, {4}},
		};
		
		int[] renewable_resources_constrain = {6};
		
		/* table[task][mode][resource] */
		int[][][] nonrenewable_resources = {
				{{0}, {0}},
				{{3}, {0}}, 
				{{2}, {3}},
				{{2}, {2}},
				{{0}, {3}},
				{{3}, {1}},
				{{0}, {2}},
				{{2}, {1}},
				{{2}, {3}},
		};
		
		int[] nonrenewable_resources_constrain = {15};
		
		/* table[task][mode] */
		int[][] duration_in_task_mode = {
				{0, 0}, 
				{4, 2}, 
				{2, 3},
				{3, 6},
				{4, 2},
				{3, 2},
				{4, 2},
				{2, 6},
				{6, 3},
				};
		
		Project pr = new Project(
				number_of_tasks, 
				precedence_connection, 
				number_of_modes_in_task, 
				duration_in_task_mode, 
				renewable_resources, 
				renewable_resources_constrain, 
				number_of_renewable_resources, 
				nonrenewable_resources, 
				nonrenewable_resources_constrain, 
				number_of_nonrenewable_resources);
		

		//int[] order = {0,2,1,4,5,7,3,6,8};
		//int[] modes = {0,1,0,0,1,0,0,0,0};
		
		//int[] order2 = {0,1,3,4,2,6,5,7,8};
		//int[] modes2 = {1,1,0,1,1,0,0,0,1};
		
		//boolean gene =  false;
		//boolean gene2 =  true;
		
		//GenerationGenerator gg = new GenerationGenerator(pr);
		//gg.generateIndividual();
		
		//Individual ind = new Individual(pr, order, modes, gene);
		//Individual ind2 = new Individual(pr, order2, modes2, gene2);
		
		//Crossover co = new Crossover(pr);
		//co.crossBreeding(ind, ind2);
		//Individual son = co.son;
		
		//ind.scheduling();
		//ind.soutRes();
		//rpg.soutTasks();
		//System.out.println(ind.toString());
		Generation g = new Generation(pr, 100);
		g.initialPopulation();
		g.schedule();
		//g.calculateFitness();
		//System.out.println("minSpan: "+g.minSpan);
		//System.out.println("maxSpan: "+g.maxSpan);
		//System.out.println("avarageSpan: "+g.averageFitness);
	}

}
