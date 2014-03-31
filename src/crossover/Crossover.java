package crossover;

import java.util.Random;

import utils.Individual;
import utils.Project;

abstract public class Crossover {

	protected Project pr;

	protected Individual father;
	protected Individual mother;

	protected Individual son;
	protected Individual daughter;

	protected Random rand = new Random();

	public Crossover(Project pr) {
		this.pr = pr;
	}

	abstract public void crossBreeding(Individual father, Individual mother);

	public Individual getSon() {
		return son;
	}

	public Individual getDaughter() {
		return daughter;
	}
	
	protected boolean allPredecessorsDone(int current, boolean[] task_used) { //
		int[] array = pr.precedence_connection[current];
		for (int i = 0; i < array.length; i++) {
			if (!task_used[array[i]]) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean allDescendantsDone(int current, boolean[] task_used) { //
		int[] array = pr.descendant_connection[current];
		for (int i = 0; i < array.length; i++) {
			if (!task_used[array[i]]) {
				return false;
			}
		}
		return true;
	}

	protected int randomNumber(int i) {
		return rand.nextInt(i);
	}
}
