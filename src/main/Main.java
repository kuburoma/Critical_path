package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import crossover.Crossover2PFBC;
import fitness.FitnessMinSpan;
import graphic.GraphPanel;
import reader.Reader;
import selection.SelectionTournament;
import utils.Project;

public class Main extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Benchmark bm = new Benchmark(
				new Crossover2PFBC(),
				new FitnessMinSpan(), 
				new SelectionTournament(5),
				100, 
				4,
				10);

		try {
			bm.run("SM","j30",1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printGraph(List<Double> values) {
		GraphPanel mainPanel = new GraphPanel(values);
		mainPanel.setPreferredSize(new Dimension(2000, 1000));
		JFrame frame = new JFrame("Graph of values");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void test() {

		try {

			Main main = new Main();
			final JFileChooser fc = new JFileChooser("C:\\");
			int returnVal = fc.showOpenDialog(main);
			if (returnVal != 0) {
				System.exit(0);
			}
			String project = fc.getSelectedFile().getAbsolutePath();

			Reader r = new Reader();
			Project pr;

			pr = r.read(project);

			BreedingStation br = new BreedingStation(pr, new Crossover2PFBC(),
					new FitnessMinSpan(), new SelectionTournament(10), 100);

			printGraph(br.run(25));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int number_of_tasks = 9;

		int[][] precedence_connection = { {}, /* 0 */
		{ 0 }, /* 1 */
		{ 0 }, /* 2 */
		{ 1 }, /* 3 */
		{ 1 }, /* 4 */
		{ 1, 2 }, /* 5 */
		{ 3, 4 }, /* 6 */
		{ 5 }, /* 7 */
		{ 6, 7 }, /* 8 */
		};

		int[] number_of_modes_in_task = { 2, 2, 2, 2, 2, 2, 2, 2, 2, };

		int number_of_renewable_resources = 1;

		int number_of_nonrenewable_resources = 1;

		/* table[task][mode][resource] */
		int[][][] renewable_resources = { { { 0 }, { 0 } }, { { 5 }, { 6 } },
				{ { 2 }, { 1 } }, { { 3 }, { 2 } }, { { 1 }, { 2 } },
				{ { 3 }, { 5 } }, { { 2 }, { 3 } }, { { 3 }, { 1 } },
				{ { 2 }, { 4 } }, };

		int[] renewable_resources_constrain = { 6 };

		/* table[task][mode][resource] */
		int[][][] nonrenewable_resources = { { { 0 }, { 0 } },
				{ { 3 }, { 0 } }, { { 2 }, { 3 } }, { { 2 }, { 2 } },
				{ { 0 }, { 3 } }, { { 3 }, { 1 } }, { { 0 }, { 2 } },
				{ { 2 }, { 1 } }, { { 2 }, { 3 } }, };

		int[] nonrenewable_resources_constrain = { 15 };

		/* table[task][mode] */
		int[][] duration_in_task_mode = { { 0, 0 }, { 4, 2 }, { 2, 3 },
				{ 3, 6 }, { 4, 2 }, { 3, 2 }, { 4, 2 }, { 2, 6 }, { 6, 3 }, };

		Project pr = new Project(number_of_tasks, precedence_connection,
				number_of_modes_in_task, duration_in_task_mode,
				renewable_resources, renewable_resources_constrain,
				number_of_renewable_resources, nonrenewable_resources,
				nonrenewable_resources_constrain,
				number_of_nonrenewable_resources, 10,0);

		// int[] order = {0,2,1,4,5,7,3,6,8};
		// int[] modes = {0,1,0,0,1,0,0,0,0};

		// int[] order2 = {0,1,3,4,2,6,5,7,8};
		// int[] modes2 = {1,1,0,1,1,0,0,0,1};

		// boolean gene = false;
		// boolean gene2 = true;

		// GenerationGenerator gg = new GenerationGenerator(pr);
		// gg.generateIndividual();

		// Individual ind = new Individual(pr, order, modes, gene);
		// Individual ind2 = new Individual(pr, order2, modes2, gene2);

		// Crossover co = new Crossover(pr);
		// co.crossBreeding(ind, ind2);
		// Individual son = co.son;

		// ind.scheduling();
		// ind.soutRes();
		// rpg.soutTasks();
		// System.out.println(ind.toString());

		// Generation g = new Generation(pr, 100);
		// g.initialPopulation();
		// g.schedule();
		// g.calculateFitness();
		// System.out.println("minSpan: "+g.minSpan);
		// System.out.println("maxSpan: "+g.maxSpan);
		// System.out.println("avarageSpan: "+g.averageFitness);

		BreedingStation br = new BreedingStation(pr, new Crossover2PFBC(),
				new FitnessMinSpan(), new SelectionTournament(10), 100);

		// br.oldGeneration.soutFitness();
		br.run(10);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
