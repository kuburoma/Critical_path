package selection;

import utils.Generation;

public class SelectionRouletteWheel extends Selection {

	@Override
	public int[] selectionNewPopulation(Generation generation) {
		int[] population = new int[generation.getSize()];

		selection = new double[generation.getSize()];
		for (int i = 1; i < generation.getSize(); i++) {
			selection[i] = (selection[i - 1] + generation.fitness[i - 1]
					/ (generation.totalFitness * 1.0));
			System.out.print(selection[i] + " ");
		}

		System.out.println();

		for (int i = 0; i < selection.length; i++) {
			double r = random.nextDouble();
			int a = 0;
			int b = selection.length - 1;
			while (b - a > 1) {
				int mid = (a + b) / 2;
				if (selection[mid] > r)
					b = mid;
				else
					a = mid;
			}
			population[i] = a;
		}

		return population;
	}

}
