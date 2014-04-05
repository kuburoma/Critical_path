package selection;

import utils.Generation;

public class SelectionTournament extends Selection {

	public SelectionTournament(int sizeOfTurnament){
		this.sizeOfTurnament = sizeOfTurnament;
	}
	
	@Override
	public int[] selectionNewPopulation(Generation generation) {
		int[] selection = new int[generation.size-generation.nBest-generation.nWorst];
		int best = Integer.MAX_VALUE;
		int bestPosition = 0;
		int r;
		
		for (int i = 0; i < selection.length; i++) {						
			best = Integer.MAX_VALUE;
			bestPosition = 0;
			for (int j = 0; j < sizeOfTurnament; j++) {
				r = random.nextInt(generation.size-generation.nWorst);
				//System.out.println(r+" "+(generation.size-generation.nWorst));
				if(best > generation.getFitnessFrom(r) ){
					best = generation.getFitnessFrom(r);
					bestPosition = r;
				}
			}
			selection[i] = bestPosition; 			
		}
		
		/*
		for (int i = 0; i < selection.length; i++) {
			System.out.print(selection[i]+" ");
		}
		System.out.println();
		*/
		
		return selection;
	}
	

}
