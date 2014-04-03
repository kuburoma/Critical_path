package crossover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Individual;
import utils.Project;

public class Crossover2PFBC extends Crossover {

	public void crossBreeding(Individual father, Individual mother) {
		this.father = father;
		this.mother = mother;

		int start = randomNumber(pr.numberOfTasks);
		int end = randomNumber(pr.numberOfTasks);
		if (start > end) {
			int help = start;
			start = end;
			end = help;
		}

		if (father.getGene()== false) {
			son = crossBreedingForward(father, mother, start, end);
		} else {
			son = crossBreedingBackward(father, mother, start, end);
		}

		if (mother.getGene() == false) {
			daughter = crossBreedingForward(mother, father, start, end);
		} else {
			daughter = crossBreedingBackward(mother, father, start, end);
		}
	}

	private Individual crossBreedingForward(Individual father, Individual mother, int start, int end) {
		boolean[] task_used = new boolean[pr.numberOfTasks];
		int[] order = new int[pr.numberOfTasks];
		int[] modes = new int[pr.numberOfTasks];
		List<Integer> suspects = new ArrayList<Integer>();
		int placed = 0;
		
		for (int i = 0; i < start; i++) {
			order[placed] = father.taskOrder[i];
			modes[placed] = father.modes[i];
			task_used[father.taskOrder[i]] = true;
			placed++;
		}
		

		Iterator<Integer> it;
		
		boolean br = false;
		
		for (int i = 0; i < pr.numberOfTasks; i++) {
			if(!task_used[mother.taskOrder[i]]){
				suspects.add(mother.taskOrder[i]);				
			}
			
			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next(); 
				if(allPredecessorsDone(type, task_used)){
					if(placed >= end){
						br = true;
						break;
					}	
					
					order[placed] = mother.taskOrder[i];
					modes[placed] = mother.modes[i];
					task_used[mother.taskOrder[i]] = true;
					placed++;
					
				}
				it.remove();
			}
			
			if(br == true){
				break;
			}
		}
		
		suspects = new ArrayList<Integer>();
		
		for (int i = 0; i < pr.numberOfTasks; i++) {
			
			if(!task_used[father.taskOrder[i]]){
				suspects.add(father.taskOrder[i]);				
			}
			
			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next(); 
				if(allPredecessorsDone(type, task_used)){
					
					order[placed] = father.taskOrder[i];
					modes[placed] = father.modes[i];
					task_used[father.taskOrder[i]] = true;
					placed++;
					it.remove();
				}
			}
		}
		
		/*
		
		System.out.println("start: "+start+" end: "+end);
		
		for (int i = 0; i < order.length; i++) {
			System.out.print(order[i]+ " ");
		}
		System.out.println();
		
		for (int i = 0; i < modes.length; i++) {
			System.out.print(modes[i]+ " ");
		}
		System.out.println();
		
		System.out.println("+++++++++++++++++++++++");
		
		*/
		
		return new Individual(pr, order, modes, father.getGene());
	}

	private Individual crossBreedingBackward(Individual father, Individual mother, int start, int end) {
		boolean[] task_used = new boolean[pr.numberOfTasks];
		int[] order = new int[pr.numberOfTasks];
		int[] modes = new int[pr.numberOfTasks];
		List<Integer> suspects = new ArrayList<Integer>();
		int placed = pr.numberOfTasks - 1;
		
		for (int i = pr.numberOfTasks - 1; i >= end; i--) {
			order[placed] = father.taskOrder[i];
			modes[placed] = father.modes[i];
			task_used[father.taskOrder[i]] = true;
			placed--;
		}

		Iterator<Integer> it;
		
		boolean br = false;
		
		for (int i = pr.numberOfTasks - 1; i >= 0; i--) {
			if(!task_used[mother.taskOrder[i]]){
				suspects.add(mother.taskOrder[i]);				
			}
			
			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next(); 
				if(allDescendantsDone(type, task_used)){
					if(placed < start){
						br = true;
						break;
					}	
					
					order[placed] = mother.taskOrder[i];
					modes[placed] = mother.modes[i];
					task_used[mother.taskOrder[i]] = true;
					placed--;
					
				}
				it.remove();
			}
			
			if(br == true){
				break;
			}
		}
		
		suspects = new ArrayList<Integer>();
		
		for (int i = pr.numberOfTasks - 1; i >= 0  ; i--) {
			
			if(!task_used[father.taskOrder[i]]){
				suspects.add(father.taskOrder[i]);				
			}
			
			it = suspects.iterator();
			while (it.hasNext()) {
				int type = it.next(); 
				if(allDescendantsDone(type, task_used)){
					
					order[placed] = father.taskOrder[i];
					modes[placed] = father.modes[i];
					task_used[father.taskOrder[i]] = true;
					placed--;
					it.remove();
				}
			}
		}
		
		/*
		
		System.out.println("start: "+start+" end: "+end);
		
		for (int i = 0; i < order.length; i++) {
			System.out.print(order[i]+ " ");
		}
		System.out.println();
		
		for (int i = 0; i < modes.length; i++) {
			System.out.print(modes[i]+ " ");
		}
		System.out.println();
		
		System.out.println("+++++++++++++++++++++++");
		
		*/
		
		return new Individual(pr, order, modes, father.getGene());
	}
}
