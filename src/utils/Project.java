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

	public int optimumSpan;

	int maxLength;
	
	
	public Project(int numberOfTasks, int[][] descendant_connection,
			int[] number_of_modes_in_task, int[][] duration_in_task_mode,
			int[][][] renewable_resources,
			int[] renewable_resources_constrain,
			int number_of_renewable_resources,
			int[][][] nonrenewable_resources,
			int[] nonrenewable_resources_constrain,
			int number_of_nonrenewable_resources, int optimumSpan) {
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
		this.optimumSpan = optimumSpan;
		this.maxLength = calculateMaxLenght();
		
		precedence_connection = new int[numberOfTasks][];
		
		settingConnection(descendant_connection);
		
		

	}
	
	public boolean isFeasible(){
		for (int i = 0; i < renewable_resources.length; i++) {
			for (int j = 0; j < renewable_resources[i].length; j++) {
				for (int j2 = 0; j2 < renewable_resources[i][j].length; j2++) {
					if(renewable_resources_constrain[j2] < renewable_resources[i][j][j2]){
						return false;
					}
				}
			}
		}	
		
		return true;
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
		
		int[] precedenceSize = new int[numberOfTasks];
		int[] precedencePlaced = new int[numberOfTasks];
		
		for (int i = 0; i < descendant.length; i++) {
			for (int j = 0; j < descendant[i].length; j++) {
				precedenceSize[descendant[i][j]] ++;
			}
		}
		
		for (int i = 0; i < precedenceSize.length; i++) {
			precedence_connection[i] = new int[precedenceSize[i]];
		}
		
		for (int i = 0; i < descendant.length; i++) {
			for (int j = 0; j < descendant[i].length; j++) {
				precedence_connection[descendant[i][j]][precedencePlaced[descendant[i][j]]] = i;
				precedencePlaced[descendant[i][j]]++;
			}
		}
		
		
		
	}
	
	public void soutPrecedence(){
		for (int i = 0; i < precedence_connection.length; i++) {
			System.out.println("Des: "+i);
			for (int j = 0; j < precedence_connection[i].length; j++) {
				System.out.print(precedence_connection[i][j]+ " ");
			}
			System.out.println();
		}
	}
}
