package fitness;

public class FitnessMinSpan extends Fitness {
	
	public void calculate() {
		int helpSpan;
		for (int i = 0; i < generation.length; i++) {
			if (generation[i].feasible) {
				helpSpan = generation[i].calcualteFitnessFeasible();
				averageFitness += helpSpan;
				if(minSpan > helpSpan){
					minSpan = helpSpan;
					minSpanTask = i;
				}
				if(maxSpan < helpSpan){
					maxSpan = helpSpan;
				}
			}
		}
		
		for (int i = 0; i < generation.length; i++) {
			if (!generation[i].feasible) {
				averageFitness += generation[i].calcualteFitnessInfeasible(maxSpan);
			}
		}
		
		averageFitness = averageFitness / size; 
	}

}
