package reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import utils.Project;

public class ReaderSolutions {

	StringTokenizer st;
	BufferedReader input;

	public int[][] read(String project) throws IOException {

		int[][] optimal_solutions = new int[65][11];

		InputStream in = getClass().getResourceAsStream("/"+project+".txt");
		//InputStream in = new FileInputStream(project);
		input = new BufferedReader(new InputStreamReader(in));

		String line;

		
		while((line = input.readLine()) != null){
			st = new StringTokenizer(line);
			optimal_solutions[Integer.valueOf(st.nextToken())][Integer.valueOf(st.nextToken())] = Integer.valueOf(st.nextToken());
		}
		
		for (int i = 0; i < optimal_solutions.length; i++) {

			//for (int j = 0; j < optimal_solutions[i].length; j++) {
			//	System.out.println(i+"_"+j+" = "+optimal_solutions[i][j]);
			//}
		}
		
		return optimal_solutions;
	}

}
