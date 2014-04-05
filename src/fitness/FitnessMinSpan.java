package fitness;

public class FitnessMinSpan extends Fitness {
	
	public void calculate() {
		int helpSpan;
		//System.out.println("generation size: "+generation.individuals.size());
		for (int i = 0; i < generation.getSize(); i++) {
			if (generation.individuals.get(i).isFeasible()) {
				helpSpan = generation.individuals.get(i).calcualteFitnessFeasible();
				generation.setFitnessTo(i, helpSpan);
				totalFitness += helpSpan;
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
			if (!generation.isFeasible(i)) {
				helpSpan = generation.individuals.get(i).calcualteFitnessInfeasible(maxSpan);
				totalFitness += helpSpan;
				generation.setFitnessTo(i, helpSpan);
			}
		}
		
		averageFitness = totalFitness / (size * 1.0);
		generation.addFitness(minSpan, maxSpan, averageFitness, totalFitness);
	}

}
