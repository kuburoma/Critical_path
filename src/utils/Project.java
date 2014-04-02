package utils;

import java.util.Iterator;

public class Project {

	public int numberOfTasks;
	public int[][] precedence_connection;
	public int[][] descendant_connection;

	
	public int[] number_of_modes_in_task;
	int[][] duration_in_task_mode;

	int[][][] renewable_resources;
	int[][] renewable_resources_current;
	int[] renewable_resources_constrain;
	int number_of_renewable_resources;
	
	int[][][] nonrenewable_resources;
	int[][] nonrenewable_resources_current;
	int[] nonrenewable_resources_constrain;
	int number_of_nonrenewable_resources;



	Task[] tasks;
	int maxLength;
	
	
	public Project(int numberOfTasks, int[][] descendant_connection,
			int[] number_of_modes_in_task, int[][] duration_in_task_mode,
			int[][][] renewable_resources,
			int[] renewable_resources_constrain,
			int number_of_renewable_resources,
			int[][][] nonrenewable_resources,
			int[] nonrenewable_resources_constrain,
			int number_of_nonrenewable_resources) {
		super();
		this.numberOfTasks = numberOfTasks;
		this.descendant_connection = descendant_connection;
		this.number_of_modes_in_task = number_of_modes_in_task;
		this.duration_in_task_mode = duration_in_task_mode;
		this.renewable_resources = renewable_resources;
		this.renewable_resources_constrain = renewable_resources_constrain;
		this.number_of_renewable_resources = number_of_renewable_resources;
		this.nonrenewable_resources = nonrenewable_resources;
		this.nonrenewable_resources_constrain = nonrenewable_resources_constrain;
		this.number_of_nonrenewable_resources = number_of_nonrenewable_resources;
		this.maxLength = calculateMaxLenght();
		tasks = new Task[numberOfTasks];
		precedence_connection = new int[numberOfTasks][];
		
		for (int i = 0; i < numberOfTasks; i++) {
			this.tasks[i] = new Task(number_of_modes_in_task[i]);
		}
		
		settingConnection(descendant_connection);
		

	}
	
	
	
	public int calculateMaxLenght() {
		int maxLength = 0;
		int max = 0;

		for (int i = 0; i < duration_in_task_mode.length; i++) {
			max = 0;
			for (int j = 0; j < duration_in_task_mode[i].length; j++) {
				if (max < duration_in_task_mode[i][j]) {
					max = duration_in_task_mode[i][j];
				}
			}
			maxLength += max;
		}
		return maxLength;
	}
	
	public void settingConnection(int[][] descendant) {
		Task parent;
		Task child = null;

		for (int i = 0; i < descendant.length; i++) {
			for (int j = 0; j < descendant[i].length; j++) {
				parent = tasks[descendant[i][j]];
				child = tasks[i];

				parent.addDescendant(child);
				child.addParent(parent);
				
			}
		}
		Iterator<Task> it;
		
		for (int i = 0; i < numberOfTasks; i++) {
			it = tasks[i].getDescendants().iterator();
			int[] array = new int[tasks[i].getDescendants().size()];
			int j = 0;
			while (it.hasNext()) {
				Task object = it.next();
				array[j] = object.getNumber(); 
				j++;
			}
			precedence_connection[i] = array;
		}
		
		
	}
	
	public void soutDescendants(){
		for (int i = 0; i < descendant_connection.length; i++) {
			System.out.println("Des: "+i);
			for (int j = 0; j < descendant_connection[i].length; j++) {
				System.out.print(descendant_connection[i][j]+ " ");
			}
			System.out.println();
		}
	}
}
