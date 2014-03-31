package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Task {
	
	private static int totalNumber = 0;
	private int number;
	
	//private int[] predecessorArray = new int[0];
	private List<Task> predecessor;
	private List<Task> descendants;	
	private int modes;
	
	public Task(int modes) {
		
		this.number = totalNumber;
		totalNumber++;
		
		predecessor = new ArrayList<Task>();
		descendants = new ArrayList<Task>();
		this.modes = modes;
		
	}
	
	public void addParent(Task parent){
		predecessor.add(parent);
	}
	
	public void addDescendant(Task descendant){
		descendants.add(descendant);
	}
	
	/*
	public void calculatePredecessorArray(){
		predecessorArray = new int[predecessor.size()];
		int i = 0;
		for (Iterator<Task> iterator = predecessor.iterator(); iterator.hasNext();) {
			Task type = iterator.next();
			predecessorArray[i] = type.number;
			i++;
		}
	}
	
	*/

	public int getNumber() {
		return number;
	}

	//public int[] getPredecessorArray() {
	//	return predecessorArray;
	//}

	public List<Task> getPredecessor() {
		return predecessor;
	}

	public List<Task> getDescendants() {
		return descendants;
	}

	public int getModes() {
		return modes;
	}

	@Override
    public String toString() {
    	String vypis = "Number: " + number + "\n";
    	vypis += "Predecessor: ";
    	for (Iterator<Task> iterator = predecessor.iterator(); iterator.hasNext();) {
    		Task type = (Task) iterator.next();
    		vypis+=type.number+" ";			
		}
    	vypis+= "\n";
    	vypis += "Descendants: ";
    	for (Iterator<Task> iterator = descendants.iterator(); iterator.hasNext();) {
    		Task type = (Task) iterator.next();
    		vypis+=type.number+" ";			
		}
    	vypis+= "\n";
    	vypis += "Number of modes: "+number;
    	return vypis;
    }
	
}
