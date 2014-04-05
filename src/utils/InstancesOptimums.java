package utils;

public class InstancesOptimums {

	public int numberOfSets;
	public int numberOfInstances;
	public int[][] instance;

	public InstancesOptimums(
			int numberOfSets, 
			int numberOfInstances,
			int[][] instance) {
		this.numberOfSets = numberOfSets;
		this.numberOfInstances = numberOfInstances;
		this.instance = instance;		
	}

	public boolean contains(int i, int j){
		if(instance[i][j] == 0){
			return false;
		}
		return true;
	}
	
	public int getOptimum(int i, int j){
		return instance[i][j];
	}
}
