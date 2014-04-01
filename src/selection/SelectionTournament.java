package selection;

import utils.Generation;

public class SelectionTournament extends Selection {
	
	private int sizeOfTurnament;
	
	public SelectionTournament(int sizeOfTurnament){
		this.sizeOfTurnament = sizeOfTurnament;
	}

	@Override
	public int[] selectionNewPopulation(Generation generation) {
		int[] selection = new int[generation.size];
		int best = Integer.MAX_VALUE;
		int bestPosition = 0;
		int r;
		
		for (int i = 0; i < selection.length; i++) {						
			best = Integer.MAX_VALUE;
			bestPosition = 0;
			for (int j = 0; j < sizeOfTurnament; j++) {
				r = random.nextInt(selection.length);
				if(best > generation.fitness[r] ){
					best = generation.fitness[r];
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
