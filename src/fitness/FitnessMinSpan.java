package fitness;

public class FitnessMinSpan extends Fitness {
	
	public void calculate() {
		int helpSpan;
		for (int i = 0; i < generation.getSize(); i++) {
			if (generation.individuals[i].feasible) {
				helpSpan = generation.individuals[i].calcualteFitnessFeasible();
				fitness[i] = helpSpan;
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
		
		for (int i = 0; i < generation.getSize(); i++) {
			if (!generation.individuals[i].feasible) {
				averageFitness += generation.individuals[i].calcualteFitnessInfeasible(maxSpan);
			}
		}
		
		averageFitness = averageFitness / size; 
	}

}
