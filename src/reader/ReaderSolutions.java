package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import utils.InstancesOptimums;
import utils.Project;

public class ReaderSolutions {

	StringTokenizer st;
	BufferedReader input;

	public InstancesOptimums read(String project) throws IOException {

		int[][] optimal_solutions = new int[65][11];
		int dataset = 0;
		int instance = 0;
		int readingMode = 0;

		InputStream in = getClass().getResourceAsStream("/" + project + ".txt");
		// InputStream in = new FileInputStream(project);
		input = new BufferedReader(new InputStreamReader(in));

		String line;
		line = input.readLine();
		st = new StringTokenizer(line);
		dataset = Integer.valueOf(st.nextToken());
		instance = Integer.valueOf(st.nextToken());
		readingMode = Integer.valueOf(st.nextToken());
		int d;
		int i;
		
		while ((line = input.readLine()) != null) {
			st = new StringTokenizer(line);
			d = Integer.valueOf(st.nextToken());
			i = Integer.valueOf(st.nextToken());
			if(readingMode == 4){
				st.nextToken();
			}	
			optimal_solutions[d][i] = Integer.valueOf(st.nextToken());
		}

		//for (int i = 0; i < optimal_solutions.length; i++) {

			// for (int j = 0; j < optimal_solutions[i].length; j++) {
			// System.out.println(i+"_"+j+" = "+optimal_solutions[i][j]);
			// }
		//}

		return new InstancesOptimums(dataset, instance, optimal_solutions);
	}
}
