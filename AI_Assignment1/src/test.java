import java.util.LinkedList;

public class test {
//1,4,2,6,5,8,7,3,0
	//1,2,5,3,4,0,6,7,8
	//1,2,0,3,4,5,6,7,8
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solve_Puzzle solve = new Solve_Puzzle();
		int init [][]={{1,4,2},{6,5,8},{7,3,0}};
		String complete1 =solve.BFS(init);
		solve.print();
		LinkedList<State> state=solve.output;
		for(int i=0;i<state.size();i++) {
			int [][]s =state.get(i).getStateShape();
             for(int h=0;h<3;h++) {
            	 for(int j=0;j<3;j++) {
            			System.out.println(s[i][j]+" ");
 
            	 }
            	 System.out.println();
             }
		}
		
		System.out.println(complete1);
		int cost = solve.get_cost();
		float time = solve.bfs_time();
		int depth=solve.get_depth();
		System.out.println("Cost:"+cost);
		System.out.println("Time:"+time);
		System.out.println("Depth:"+depth);
		//String complete =solve.DFS(init);
	
		//System.out.println(complete);
		
		String complete2 =solve.AStar(init, 'm');
		
//		int init [][]={{1,0,2},{3,5,6},{7,8,4}};
//		
//		solve.print();
//		solve.path();
//		solve.print();
//		System.out.println(complete2);
//		int cost = solve.get_cost();
//		float time = solve.bfs_time();
//		int depth=solve.get_depth();
//		System.out.println("Cost:"+cost);
//		System.out.println("Time:"+time);
//		System.out.println("Depth:"+depth);
//		//String complete =solve.DFS(init);
	
		//System.out.println(complete);
		
		
	}

}
