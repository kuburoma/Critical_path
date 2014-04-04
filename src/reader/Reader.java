package reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import utils.Project;

public class Reader {

	StringTokenizer st;
	BufferedReader input;

	public Project read(String project) throws IOException {

		int number_of_tasks;
		
		int optimumSpan;

		int[][] descendante_connection;

		int[] number_of_modes_in_task;

		int number_of_renewable_resources;

		int number_of_nonrenewable_resources;

		/* table[task][mode][resource] */
		int[][][] renewable_resources;

		int[] renewable_resources_constrain;

		/* table[task][mode][resource] */
		int[][][] nonrenewable_resources;
		int[] nonrenewable_resources_constrain;

		/* table[task][mode] */
		int[][] duration_in_task_mode;

		InputStream in = getClass().getResourceAsStream("/"+project+".mm");
		//InputStream in = new FileInputStream(project);
		input = new BufferedReader(new InputStreamReader(in));

		String line;

		skip(5);

		line = input.readLine();
		number_of_tasks = Integer.parseInt(line.replaceAll("[\\D]", ""));

		skip(2);

		line = input.readLine();
		number_of_renewable_resources = Integer.parseInt(line.replaceAll(
				"[\\D]", ""));
		line = input.readLine();
		number_of_nonrenewable_resources = Integer.parseInt(line.replaceAll(
				"[\\D]", ""));

		skip(4);
		line = input.readLine();
		st = new StringTokenizer(line);
		st.nextToken();
		st.nextToken();
		st.nextToken();
		optimumSpan = Integer.valueOf(st.nextToken());
		
		skip(3);

		number_of_modes_in_task = new int[number_of_tasks];
		descendante_connection = new int[number_of_tasks][];

		for (int i = 0; i < number_of_tasks; i++) {
			line = input.readLine();
			st = new StringTokenizer(line);
			st.nextToken();
			number_of_modes_in_task[i] = Integer.valueOf(st.nextToken());
			int suc = Integer.valueOf(st.nextToken());
			descendante_connection[i] = new int[suc];
			for (int j = 0; j < suc; j++) {
				descendante_connection[i][j] = Integer.valueOf(st.nextToken()) - 1;
			}
		}

		skip(4);

		duration_in_task_mode = new int[number_of_tasks][];
		renewable_resources = new int[number_of_tasks][][];
		nonrenewable_resources = new int[number_of_tasks][][];

		for (int i = 0; i < number_of_tasks; i++) {
			line = input.readLine();
			st = new StringTokenizer(line);
			st.nextToken();
			st.nextToken();
			duration_in_task_mode[i] = new int[number_of_modes_in_task[i]];
			duration_in_task_mode[i][0] = Integer.valueOf(st.nextToken());
			renewable_resources[i] = new int[number_of_modes_in_task[i]][number_of_renewable_resources];

			nonrenewable_resources[i] = new int[number_of_modes_in_task[i]][number_of_nonrenewable_resources];

			for (int j = 0; j < number_of_renewable_resources; j++) {
				renewable_resources[i][0][j] = Integer.valueOf(st.nextToken());
			}

			for (int j = 0; j < number_of_nonrenewable_resources; j++) {
				nonrenewable_resources[i][0][j] = Integer.valueOf(st.nextToken());
			}

			for (int j = 1; j < number_of_modes_in_task[i]; j++) {
				line = input.readLine();
				st = new StringTokenizer(line);
				st.nextToken();
				duration_in_task_mode[i][j] = Integer.valueOf(st.nextToken());

				for (int k = 0; k < number_of_renewable_resources; k++) {
					renewable_resources[i][j][k] = Integer.valueOf(st
							.nextToken());
				}

				for (int k = 0; k < number_of_nonrenewable_resources; k++) {
					nonrenewable_resources[i][j][k] = Integer.valueOf(st
							.nextToken());
				}
			}
		}
		
		skip(3);
		
		line = input.readLine();
		st = new StringTokenizer(line);
		renewable_resources_constrain = new int[number_of_renewable_resources];
		for (int i = 0; i < number_of_renewable_resources; i++) {
			renewable_resources_constrain[i] = Integer.valueOf(st.nextToken());
		}
		
		nonrenewable_resources_constrain = new int[number_of_nonrenewable_resources];
		for (int i = 0; i < number_of_nonrenewable_resources; i++) {
			nonrenewable_resources_constrain[i] = Integer.valueOf(st.nextToken());
		}

		/*
		System.out.println("Jobs: " + number_of_tasks);
		System.out.println("OptimumSpan: "+optimumSpan);
		System.out.println("rr: " + number_of_renewable_resources);
		System.out.println("ne: " + number_of_nonrenewable_resources);
		System.out.println("rr con: ");
		for (int j = 0; j < renewable_resources_constrain.length; j++) {
			System.out.print(renewable_resources_constrain[j]+" ");
		}
		System.out.println();
		System.out.println("nr con: ");
		for (int j = 0; j < nonrenewable_resources_constrain.length; j++) {
			System.out.print(nonrenewable_resources_constrain[j]+" ");
		}
		System.out.println();
		
		*/
		/*
		
		for (int i = 0; i < descendante_connection.length; i++) {
			System.out.print(i+": ");
			for (int j = 0; j < descendante_connection[i].length; j++) {
				System.out.print(descendante_connection[i][j] + " ");
			}
			System.out.println();
		}
		
		*/
		/*
		System.out.println("Renewable resources");
		for (int i = 0; i < renewable_resources.length; i++) {
			System.out.println("Task "+i);
			for (int j = 0; j < renewable_resources[i].length; j++) {
				System.out.print("Mode: "+j+" - rr: ");
				for (int j2 = 0; j2 < number_of_renewable_resources; j2++) {
					System.out.print(renewable_resources[i][j][j2]);
				}
				System.out.print(" - nr: ");
				for (int j2 = 0; j2 < number_of_nonrenewable_resources; j2++) {
					System.out.print(nonrenewable_resources[i][j][j2]+" ");
				}
				System.out.println();
			}
		}
		
		*/
		
		return new Project(
				number_of_tasks, 
				descendante_connection, 
				number_of_modes_in_task, 
				duration_in_task_mode, 
				renewable_resources, 
				renewable_resources_constrain, 
				number_of_renewable_resources, 
				nonrenewable_resources, 
				nonrenewable_resources_constrain, 
				number_of_nonrenewable_resources, optimumSpan);
		
	}

	public void skip(int i) throws IOException {
		for (int j = 0; j < i; j++) {
			input.readLine();
		}
	}
}
