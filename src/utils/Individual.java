package utils;

public class Individual {

	private Project pr;

	private boolean feasible;

	private int fitness;

	private int[][] connection;

	private int[] startTime;
	private int[] endTime;

	private int[] modeOrdered;
	public int[] taskOrder;
	public int[] modes;

	private int[][] individualRenewableResources;
	private int[] individualNonrenewableResources;

	private int numberOfTasks;

	private boolean gene;

	public boolean isFeasible() {
		return feasible;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	public boolean getGene(){
		return gene;
	}

	public Individual(Project pr, int[] order, int[] modes, boolean gene) {
		this.startTime = new int[pr.numberOfTasks];
		this.modes = new int[pr.numberOfTasks];
		this.endTime = new int[pr.numberOfTasks];
		this.taskOrder = new int[pr.numberOfTasks];
		this.modeOrdered = new int[pr.numberOfTasks];
		this.numberOfTasks = pr.numberOfTasks;
		this.taskOrder = order;
		this.modes = modes;
		this.gene = gene;
		this.pr = pr;
		this.feasible = true;
		this.individualNonrenewableResources = new int[pr.numberOfTasks];

		if (gene == true) {
			this.connection = pr.descendant_connection;
		} else {
			this.connection = pr.precedence_connection;
		}

		for (int i = 0; i < modeOrdered.length; i++) {
			modeOrdered[i] = modes[taskOrder[i]];
		}

		for (int i = 0; i < numberOfTasks; i++) {
			for (int j = 0; j < pr.number_of_nonrenewable_resources; j++) {
				individualNonrenewableResources[j] += pr.nonrenewable_resources[i][modes[taskOrder[i]]][j];
			}
		}

		for (int i = 0; i < pr.number_of_nonrenewable_resources; i++) {
			individualNonrenewableResources[i] -= pr.nonrenewable_resources_constrain[i];
		}

		if (!checkNonrenewableResources()) {
			feasible = false;
		}
	}

	public void scheduling() {

		individualRenewableResources = new int[pr.maxLength][pr.number_of_renewable_resources];

		if (gene == false) {
			forwardScheduling();
		} else {
			backwardScheduling();
		}
	}

	public int calcualteFitnessFeasible() {
		for (int i = 0; i < numberOfTasks; i++) {
			if (endTime[i] > fitness) {
				fitness = endTime[i];
			}
		}
		return fitness;
	}

	public int calcualteFitnessInfeasible(int max) {
		for (int i = 0; i < numberOfTasks; i++) {
			if (individualNonrenewableResources[i] > 0) {
				max += individualNonrenewableResources[i];
			}
		}
		fitness = max;
		return fitness;
	}

	private void forwardScheduling() {
		int current = 0;
		int mode;
		int start_time;

		for (int i = 0; i < numberOfTasks; i++) {

			current = taskOrder[i];

			// start_time = endTimeOfPredecesors(current,
			// pr.precedence_connection[current]);

			start_time = endTimeOfPredecesors(current);

			mode = modes[current];

			while (!checkResourceConstrain(current, mode, start_time)) {
				start_time++;
			}

			startTime[current] = start_time;

			endTime[current] = start_time
					+ pr.duration_in_task_mode[current][mode];

			/*
			 * System.out.println(current+" "+start_time
			 * +" "+pr.duration_in_task_mode[current][mode]+" "+
			 * endTime[current]);
			 */

			addTask(current, mode, start_time);
		}
	}

	private void backwardScheduling() {

		int current = 0;
		int mode;
		int start_time;

		for (int i = numberOfTasks - 1; i >= 0; i--) {

			current = taskOrder[i];

			start_time = endTimeOfPredecesors(current);

			mode = modes[current];

			while (!checkResourceConstrain(current, mode, start_time)) {
				start_time++;
			}

			startTime[current] = start_time;

			endTime[current] = start_time
					+ pr.duration_in_task_mode[current][mode];

			addTask(current, mode, start_time);
		}

		tableToNormal();

	}

	private boolean checkNonrenewableResources() {
		for (int i = 0; i < pr.number_of_nonrenewable_resources; i++) {
			if (individualNonrenewableResources[i] > pr.nonrenewable_resources_constrain[i]) {
				return false;
			}
		}
		return true;
	}

	private void tableToNormal() {
		int max = 0;
		for (int i = 0; i < endTime.length; i++) {
			if (endTime[i] > max) {
				max = endTime[i];
			}
		}

		for (int i = 0; i < startTime.length; i++) {
			startTime[i] = max - startTime[i];
		}

		for (int i = 0; i < endTime.length; i++) {
			endTime[i] = max - endTime[i];
		}

		int[] help = startTime;
		startTime = endTime;
		endTime = help;

	}

	private void addTask(int current, int mode, int start_time) {
		int size = pr.duration_in_task_mode[current][mode];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < pr.number_of_renewable_resources; j++) {
				individualRenewableResources[i + start_time][j] += pr.renewable_resources[current][mode][j];
			}
		}
	}

	private boolean checkResourceConstrain(int current, int mode, int start_time) {
		int size = pr.duration_in_task_mode[current][mode];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < pr.number_of_renewable_resources; j++) {
				if (individualRenewableResources[i + start_time][j]
						+ pr.renewable_resources[current][mode][j] > pr.renewable_resources_constrain[j]) {
					return false;
				}
			}
		}

		return true;
	}

	private int endTimeOfPredecesors(int current) {
		int[] array = connection[current];

		int endTimeCurr = 0;

		if (array.length == 0) {
			return 0;
		}

		for (int i = 0; i < array.length; i++) {
			if (endTimeCurr < endTime[array[i]]) {
				endTimeCurr = endTime[array[i]];
			}
		}

		return endTimeCurr;
	}

	@Override
	public String toString() {
		String vypis = "";
		for (int i = 0; i < numberOfTasks; i++) {
			vypis += "Task: " + i + " Time: " + startTime[i] + " Dur: "
					+ pr.duration_in_task_mode[i][modes[i]] + " Res: "
					+ pr.renewable_resources[i][modes[i]][0] + " End: "
					+ endTime[i] + " Mode: " + modes[i] + "\n";
		}
		return vypis;
	}

	public void soutRes() {
		for (int i = 0; i < individualRenewableResources.length; i++) {
			for (int j = 0; j < individualRenewableResources[i].length; j++) {
				System.out.print(individualRenewableResources[i][j] + " ");
			}
			System.out.println();
		}
	}
}
