package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import reader.Reader;
import selection.SelectionTournament;
import utils.Individual;
import utils.Project;
import crossover.Crossover;
import crossover.Crossover2PFBC;
import crossover.CrossoverBox;
import fitness.FitnessMinSpan;
import generator.GenerationGenerator;
import graphic.GraphPanel;

public class Main extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//crossTest();
		
		//testing();

		//singleBenchmark();
		
		//benchmarkDueDate();
		
		benchmark();
	}
	
	public static void benchmarkDueDate(){
		int size = 100;
		int turnament = 4;
		int elitism = 2;
		int delete = 10;
		int iteration = 1000;
		int again = 1;
		
		Benchmark bm = new Benchmark(new CrossoverBox(),
				new FitnessMinSpan(), new SelectionTournament(turnament), size, elitism, delete,GenerationGenerator.RANDOM_SCHEDULING);

		try {
			System.out.println("sm_j120: "+bm.run("sm", "j120", iteration, again));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void singleBenchmark(){
		int size = 100;
		int turnament = 4;
		int elitism = 2;
		int delete = 10;
		int iteration = 1000;
		int again = 1;
		
		String mode = "mm";
		String instances = "j30";
		int i = 33;
		int j = 8;
		
		
		SingleBenchmark bm = new SingleBenchmark(new Crossover2PFBC(),
				new FitnessMinSpan(), new SelectionTournament(turnament), size, elitism, delete, GenerationGenerator.BACKWARD_SCHEDULING);

		try {
			System.out.println(bm.run(mode, instances, iteration, again, i , j));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void benchmark(){
		int size = 100;
		int turnament = 4;
		int elitism = 2;
		int delete = 10;
		int iteration = 1000;
		int again = 5;
		
		Benchmark bm = new Benchmark(new Crossover2PFBC(),
				new FitnessMinSpan(), new SelectionTournament(turnament), size, elitism, delete,GenerationGenerator.RANDOM_SCHEDULING);

		try {

			System.out.println("sm_j30: "+bm.run("sm", "j30", iteration, again));
			System.out.println("sm_j60: "+bm.run("sm", "j60", iteration, again));
			System.out.println("sm_j120: "+bm.run("sm", "j120", iteration, again));
			System.out.println("mm_j10: "+bm.run("mm", "j10", iteration, again));
			System.out.println("mm_j12: "+bm.run("mm", "j12", iteration, again));
			System.out.println("mm_j14: "+bm.run("mm", "j14", iteration, again));
			System.out.println("mm_j16: "+bm.run("mm", "j16", iteration, again));
			System.out.println("mm_j18: "+bm.run("mm", "j18", iteration, again));
			System.out.println("mm_j20: "+bm.run("mm", "j20", iteration, again));
			System.out.println("mm_j30: "+bm.run("mm", "j30", iteration, again));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void crossTest(){
		Crossover crossoverType = new CrossoverBox();
		
		
		int number_of_tasks = 9;

		int[][] precedence_connection = { {1, 2}, /* 0 */
		{ 3, 4, 5 }, /* 1 */
		{ 5 }, /* 2 */
		{ 6 }, /* 3 */
		{ 6 }, /* 4 */
		{ 7 }, /* 5 */
		{ 8 }, /* 6 */
		{ 8 }, /* 7 */
		{  }, /* 8 */
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
				number_of_nonrenewable_resources, 10, 0);
		
		crossoverType.setProject(pr);

	    int[] order = {0,2,1,4,5,7,3,6,8};
		int[] modes = {0,1,0,0,1,0,0,0,0};

		int[] order2 = {0,1,3,4,2,6,5,7,8};
		int[] modes2 = {1,1,0,1,1,0,0,0,1};
		
		int[] order3 = {0,2,1,5,7,4,3,6,8};
		int[] modes3 = {1,1,0,1,1,0,0,0,1};

		boolean gene = false;
		boolean gene2 = true;
		boolean gene3 = true;

		Individual ind = new Individual(pr, order, modes, gene);
		Individual ind2 = new Individual(pr, order2, modes2, gene2);
		Individual ind3 = new Individual(pr, order3, modes3, gene3);
		
		crossoverType.crossBreeding(ind, ind2, ind3);
	}

	public static void testing() {

		int size = 100;
		int maxElitism = 40;
		int maxDelete = 40;
		int maxTurnament = 1;
		int pokusu = 10;
		double help;
		double min_v = Integer.MAX_VALUE;
		int min_i = 0;
		int min_j = 0;
		int min_t = 0;

		for (int i = 0; i < maxElitism; i += 2) {
			for (int j = 0; j < maxDelete; j += 2) {
				System.out.println(i + " - " + j);
				if (i + j > size) {
					System.out.println("vetsi");
					break;
				}
				// for (int t = 1; t < maxTurnament; t++) {
				// System.out.println(i+" - "+j +" - "+t);
				Benchmark bm = new Benchmark(new Crossover2PFBC(),
						new FitnessMinSpan(), new SelectionTournament(5), size,
						i, j,GenerationGenerator.BACKWARD_SCHEDULING);

				try {
					help = bm.run("mm", "j10", 10000, 5);
					if (help < min_v) {
						min_v = help;
						min_i = i;
						min_j = j;
						// min_t = t;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }
			}
		}

		System.out.println("min_v: " + min_v);
		System.out.println("min_i: " + min_i);
		System.out.println("min_j: " + min_j);
		System.out.println("min_t: " + min_t);

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
				number_of_nonrenewable_resources, 10, 0);

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
