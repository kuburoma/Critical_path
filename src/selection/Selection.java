package selection;

import java.util.Random;

import utils.Generation;

abstract public class Selection {

	protected double[] selection;
	
	abstract public int[] selectionNewPopulation(Generation generation);
	Random random = new Random();
	
	protected int sizeOfTurnament;
	

	
	public void soutSelection(){
		for (int i = 0; i < selection.length; i++) {
			System.out.print("i: "+i+"-"+selection[i]+" - ");
		}
		System.out.println();
	}
	
}
