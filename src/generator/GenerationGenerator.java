package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.Individual;
import utils.Project;

public class GenerationGenerator {

	Project pr;
	int numberOfTasks;

	private Random rand = new Random();

	public GenerationGenerator(Project pr) {
		this.pr = pr;
		numberOfTasks = pr.numberOfTasks;
	}
	
	public List<Individual> generatePopulation(int sizeOfGeneration){
		List<Individual> li = new ArrayList<Individual>();
		for (int i = 0; i < sizeOfGeneration; i++) {
			li.add(generateIndividual());
		}
		return li;
	}

	private Individual generateIndividual() {

		int current = 0;
		int mode;
		int random_number;
		boolean gene = false;

		boolean[] task_used = new boolean[numberOfTasks];
		List<Integer> taskReady = new ArrayList<Integer>();
		boolean[] in_work = new boolean[numberOfTasks];

		int[] order = new int[numberOfTasks];
		int[] modes = new int[numberOfTasks];
		taskReady.add(0);

		for (int i = 0; i < numberOfTasks; i++) {

			random_number = randomNumber(taskReady.size());
			
			if(randomNumber(2) == 0){
				gene = false;
			}else{
				gene = true;
			}
			
		

			current = taskReady.get(random_number);

			taskReady.remove(random_number);

			mode = randomNumber(pr.number_of_modes_in_task[current]);

			task_used[current] = true;

			order[i] = current;
			modes[i] = mode;

			for (int k = 0; k < pr.descendant_connection[current].length; k++) {
				in_work[pr.descendant_connection[current][k]] = true;
			}

			for (int j = 0; j < pr.descendant_connection.length; j++) {
				if (in_work[j] == true) {
					if (allPredecessorsDone(j, task_used)) {
						in_work[j] = false;
						taskReady.add(j);
					}
				}
			}
		}
		return new Individual(pr, order, modes, gene);
	}

	private boolean allPredecessorsDone(int current, boolean[] task_used) { //
		int[] array = pr.precedence_connection[current];
		for (int i = 0; i < array.length; i++) {
			if (!task_used[array[i]]) {
				return false;
			}
		}
		return true;
	}

	private int randomNumber(int i) {
		return rand.nextInt(i);
	}

}
