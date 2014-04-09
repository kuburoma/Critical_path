package crossover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import utils.Individual;

public class CrossoverBox extends Crossover {

	private Individual orderBy;

	public void crossBreeding(Individual father, Individual mother,
			Individual best) {
		this.father = father;
		this.mother = mother;
		this.best = best;

		son = crossBreedingOne(father, mother, best);
		daughter = crossBreedingOne(mother, father, best);

	}

	private Individual crossBreedingOne(Individual father, Individual mother,
			Individual best) {
		this.father = father;
		this.mother = mother;
		this.best = best;
		
		
		/*
		System.out.println(" ----- otec -----");
		for (int i = 0; i < father.taskOrder.length; i++) {
			System.out.print(father.taskOrder[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < father.modeOrdered.length; i++) {
			System.out.print(father.modeOrdered[i] + " ");
		}
		System.out.println();

		System.out.println(" ----- matka -----");
		for (int i = 0; i < mother.taskOrder.length; i++) {
			System.out.print(mother.taskOrder[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < mother.modeOrdered.length; i++) {
			System.out.print(mother.modeOrdered[i] + " ");
		}
		System.out.println();

		System.out.println(" ----- best -----");
		for (int i = 0; i < best.taskOrder.length; i++) {
			System.out.print(best.taskOrder[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < best.modeOrdered.length; i++) {
			System.out.print(best.modeOrdered[i] + " ");
		}
		System.out.println();
		*/
		

		boolean[] task_used = new boolean[pr.numberOfTasks];
		int[] order = new int[pr.numberOfTasks];
		int[] modes = new int[pr.numberOfTasks];
		List<Integer> inOrder = new ArrayList<Integer>();

		PriorityQueue<Integer> orderCutting = new PriorityQueue<Integer>();

		int cutting = randomNumber(pr.numberOfTasks);

		cutting = 1;

		for (int i = 0; i < cutting; i++) {
			int rn = randomNumber(pr.numberOfTasks);
			if (!orderCutting.contains(rn)) {
				//System.out.println(rn);
				orderCutting.add(rn);
			}
		}

		orderCutting.add(pr.numberOfTasks);

		int start = 0;
		int next = 0;
		int decide;
		int placed = 0;

		while (!orderCutting.isEmpty()) {

			next = orderCutting.poll();

			if (start == next) {
				next = start;
				continue;
			}

			decide = randomNumber(3);

			/*
			System.out.println("s: " + start + " n: " + next + " decide: "
					+ decide);
			*/
			if (decide == 0) {
				orderBy = father;
			}
			if (decide == 1) {
				orderBy = mother;
			}
			if (decide == 2) {
				orderBy = best;
			}
			for (int i = start; i < next; i++) {
				inOrder.add(father.taskOrder[i]);
			}

			start = next;

			// System.out.println("size 1 - "+inOrder.size());
			inOrder = orderBy.getInOrder(inOrder);
			/*
			for (int i = 0; i < inOrder.size(); i++) {
				System.out.println(inOrder.get(i));
			}
			*/
			// System.out.println("size 2 - "+inOrder.size());

			while (!inOrder.isEmpty()) {
				for (int i = 0; i < inOrder.size(); i++) {
					int task = inOrder.get(i);
					if (allPredecessorsDone(task, task_used)) {
						order[placed] = task;
						task_used[task] = true;
						placed++;
						inOrder.remove(i);
						break;
					}
				}
			}
			inOrder.clear();
		}

		/*
		for (int i = 0; i < order.length; i++) {
			modes[i] = father.modeOrdered[order[i]];
		}

		System.out.println(" ----- potomek -----");
		for (int i = 0; i < order.length; i++) {
			System.out.print(order[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < modes.length; i++) {
			System.out.print(modes[i] + " ");
		}
		System.out.println();
		
		*/

		return new Individual(pr, order, modes, father.getGene());
	}

	private Individual crossBreedingForward(Individual father,
			Individual mother, int start, int end) {
		boolean[] task_used = new boolean[pr.numberOfTasks];
		int[] order = new int[pr.numberOfTasks];
		int[] modes = new int[pr.numberOfTasks];
		List<Integer> suspects = new ArrayList<Integer>();
		int placed = 0;

		for (int i = 0; i < start; i++) {
			order[placed] = father.taskOrder[i];
			modes[placed] = father.modeOrdered[i];
			task_used[father.taskOrder[i]] = true;
			placed++;
		}

		Iterator<Integer> it;

		boolean br = false;

		for (int i = 0; i < pr.numberOfTasks; i++) {
			if (!task_used[mother.taskOrder[i]]) {
				suspects.add(mother.taskOrder[i]);
			}

			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next();
				if (allPredecessorsDone(type, task_used)) {
					if (placed >= end) {
						br = true;
						break;
					}

					order[placed] = mother.taskOrder[i];
					modes[placed] = mother.modeOrdered[i];
					task_used[mother.taskOrder[i]] = true;
					placed++;

				}
				it.remove();
			}

			if (br == true) {
				break;
			}
		}

		suspects = new ArrayList<Integer>();

		for (int i = 0; i < pr.numberOfTasks; i++) {

			if (!task_used[father.taskOrder[i]]) {
				suspects.add(father.taskOrder[i]);
			}

			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next();
				if (allPredecessorsDone(type, task_used)) {

					order[placed] = father.taskOrder[i];
					modes[placed] = father.modeOrdered[i];
					task_used[father.taskOrder[i]] = true;
					placed++;
					it.remove();
				}
			}
		}

		/*
		 * 
		 * System.out.println("start: "+start+" end: "+end);
		 * 
		 * for (int i = 0; i < order.length; i++) { System.out.print(order[i]+
		 * " "); } System.out.println();
		 * 
		 * for (int i = 0; i < modes.length; i++) { System.out.print(modes[i]+
		 * " "); } System.out.println();
		 * 
		 * System.out.println("+++++++++++++++++++++++");
		 */

		return new Individual(pr, order, modes, father.getGene());
	}

	private Individual crossBreedingBackward(Individual father,
			Individual mother, int start, int end) {
		boolean[] task_used = new boolean[pr.numberOfTasks];
		int[] order = new int[pr.numberOfTasks];
		int[] modes = new int[pr.numberOfTasks];
		List<Integer> suspects = new ArrayList<Integer>();
		int placed = pr.numberOfTasks - 1;

		for (int i = pr.numberOfTasks - 1; i >= end; i--) {
			order[placed] = father.taskOrder[i];
			modes[placed] = father.modeOrdered[i];
			task_used[father.taskOrder[i]] = true;
			placed--;
		}

		Iterator<Integer> it;

		boolean br = false;

		for (int i = pr.numberOfTasks - 1; i >= 0; i--) {
			if (!task_used[mother.taskOrder[i]]) {
				suspects.add(mother.taskOrder[i]);
			}

			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next();
				if (allDescendantsDone(type, task_used)) {
					if (placed < start) {
						br = true;
						break;
					}

					order[placed] = mother.taskOrder[i];
					modes[placed] = mother.modeOrdered[i];
					task_used[mother.taskOrder[i]] = true;
					placed--;

				}
				it.remove();
			}

			if (br == true) {
				break;
			}
		}

		suspects = new ArrayList<Integer>();

		for (int i = pr.numberOfTasks - 1; i >= 0; i--) {

			if (!task_used[father.taskOrder[i]]) {
				suspects.add(father.taskOrder[i]);
			}

			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next();
				if (allDescendantsDone(type, task_used)) {

					order[placed] = father.taskOrder[i];
					modes[placed] = father.modeOrdered[i];
					task_used[father.taskOrder[i]] = true;
					placed--;
					it.remove();
				}
			}
		}

		/*
		 * 
		 * System.out.println("start: "+start+" end: "+end);
		 * 
		 * for (int i = 0; i < order.length; i++) { System.out.print(order[i]+
		 * " "); } System.out.println();
		 * 
		 * for (int i = 0; i < modes.length; i++) { System.out.print(modes[i]+
		 * " "); } System.out.println();
		 * 
		 * System.out.println("+++++++++++++++++++++++");
		 */

		return new Individual(pr, order, modes, father.getGene());
	}

}
