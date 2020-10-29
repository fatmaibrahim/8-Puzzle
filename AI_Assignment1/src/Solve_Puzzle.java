

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.awt.Point;

import javax.management.openmbean.ArrayType;


public class Solve_Puzzle {
	
 
public LinkedList<State> explored ;
public LinkedList<State> output=new LinkedList() ;
private LinkedList<LinkedList<Integer>> id;
private LinkedList<State> neibours= new LinkedList() ;
private int [][] goalState ={{0,1,2} ,{3,4,5} ,{6,7,8}};
private State goal_state=new State(goalState);
private float bfs_sec;
private float dfs_sec;
private float a_sec;
int goal;
//private int [][] initial_state ;
	
	//get index of element if flag ==0 return x index else return y index
	private int get_index (int x, int array[][],int flag){
		int index1 = -1;
		int index2 =-1;
		for(int i=0;i<array.length;i++){
			for (int j=0;j<array[i].length;j++){
				if(x==array[i][j]){
					index1= i;
					index2=j;
					break;
				}
			}
		}
		if(flag==0){
			return index1;
		}else{
			return index2;
		}
	}
	//swap the empty cell with an adjacent cell and return a neibouring array
	private int[][] swap(int cell1 , int cell2,int array[][]){
		int neibour_array[][] = new int [3][3];
		for(int i=0;i<array.length;i++){
			for( int j=0; j<array[i].length;j++){
				neibour_array[i][j]=array[i][j];
			}
		}
		// x,y members in array not index so first get index
		int x1=get_index(cell1,array,0);
		int y1=get_index(cell1,array,1);
		int x2=get_index(cell2,array,0);
		int y2=get_index(cell2,array,1);
		neibour_array[x1][y1]=cell2;
		neibour_array[x2][y2]=cell1;
		return neibour_array;
		
	}
	private boolean compare (int a1[][], int a2[][]){
		for(int i=0;i< a1.length; i++){
			for(int j=0;j<a1[i].length;j++){
				if(a1[i][j]!=a2[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	private LinkedList<State> get_neibours(State stateObj){
		int[][]state=stateObj.getStateShape();
		LinkedList<int [][]> neib=new LinkedList();
		int x1=get_index(0,state,0);
		int y1=get_index(0,state,1);
		if((y1+1)<state[x1].length){
			neib.add(swap(state[x1][y1],state[x1][y1+1],state));
		}
		if((x1+1)<state.length){
			neib.add(swap(state[x1][y1],state[x1+1][y1],state));
		}
		if((y1-1)>=0){
			neib.add(swap(state[x1][y1],state[x1][y1-1],state));
		}
		if((x1-1)>=0){
			neib.add(swap(state[x1][y1],state[x1-1][y1],state));
		}
		
		
		LinkedList<State> result=new LinkedList();
		for(int i=0;i<neib.size();i++){
			State s = new State(neib.get(i));
			s.setCost(stateObj.getCost()+1);
			result.add(s);
		}

		return result;
		
	}
	private boolean contain(LinkedList<State>list,State state){
		for(int i=0;i<list.size();i++){
			if(compare(list.get(i).getStateShape(),state.getStateShape())){
				return true;
			}
		}
		
		return false;
	}
	
	public String BFS (int [][]initial_state){
		int[][] init = initial_state;
		State state = new State(init);
		long end,start;
		int level=0;
		int id_state=-1;
		Queue<State> frontier = new LinkedList<State>();
		id=new LinkedList();
		start = System.currentTimeMillis();
		
		frontier.add(state);
		id.add(new LinkedList());
		id.get(level).add(0);
		explored = new LinkedList<State>();
		
		while(!(frontier.isEmpty())){
			id_state++;
			state = frontier.remove();
			explored.add(state);
			print_e();
			if(compare(state.getStateShape(),goal_state.getStateShape())){
				goal=id_state;
				end =  System.currentTimeMillis();
				bfs_sec = (end - start) / 1000F;
				return "Success" ;
			}
			neibours=get_neibours(state);
			for(int i=0;i<neibours.size();i++){
				if(!(contain((LinkedList<State>) frontier,neibours.get(i)))){
					if(!(contain(explored,neibours.get(i)))){
						frontier.add(neibours.get(i));
						level++;
						id.add(new LinkedList());
						id.get(level).addAll(id.get(id_state));
						id.get(level).add(level);
					}
				}
			}
			
		}
		end =  System.currentTimeMillis();
		bfs_sec = (end - start) / 1000F;
		return "Failure";
		
	}
	private int getDistance(State state,char heuristic){
		Map<Integer,Point> map=new HashMap();
		map.put(0, new Point(0, 0));
		map.put(1, new Point(0, 1));
		map.put(2, new Point(0, 2));
		map.put(3, new Point(1, 0));
		map.put(4, new Point(1, 1));
		map.put(5, new Point(1, 2));
		map.put(6, new Point(2, 0));
		map.put(7, new Point(2, 1));
		map.put(8, new Point(2, 2));
		int [][]arr=state.getStateShape();
		int sum=0;
		boolean flag=false;
		for(int k=0;k<9;k++){
			flag=false;
			for(int i=0;i<3&&!flag;i++){
				for(int j=0;j<3;j++){
					if(arr[i][j]==k){
						flag=true;
						if(heuristic=='m'){
						sum+=Math.abs(map.get(k).x-i)+Math.abs(map.get(k).y-j);
						}else if(heuristic=='e'){
							sum+=Math.sqrt(Math.pow(map.get(k).x-i, 2)+Math.pow(map.get(k).y-j, 2));
						}
						break;
					}
				}
			}
		}
		return sum;
	}
	private State getMin(LinkedList<State>list){
		int min=Integer.MAX_VALUE;
		State ret=null;
		for(int i=0;i<list.size();i++){
			if(list.get(i).calcFun()<min){
				min=list.get(i).calcFun();
				ret=list.get(i);
			}
		}
		return ret;
	}
	public String AStar(int[][]initial_state,char heuristic){
		State state = new State(initial_state);
		state.setCost(0);
		state.setHeuristic(getDistance(state, heuristic));
		int level=0;
		int id_state=-1;
		LinkedList <State> frontier = new LinkedList<State>();
		long start = System.currentTimeMillis();
		id=new LinkedList();
		frontier.push(state);
		id.add(new LinkedList());
		id.get(level).add(0);
		explored = new LinkedList<State>();
		while(!(frontier.isEmpty())){
			id_state++;
			state=getMin(frontier);
			frontier.remove(state);
			explored.add(state);
			System.out.println(explored.size() );
			if(compare(state.getStateShape(),goal_state.getStateShape())){
				goal=id_state;
				long end = System.currentTimeMillis();
				a_sec = (end - start) / 1000F;
				return "Success";
			}
			neibours=get_neibours(state);
			for(int i=0;i<neibours.size();i++){
				if(!(contain( frontier,neibours.get(i)))){
					if(!(contain(explored,neibours.get(i)))){
						state.setHeuristic(getDistance(state,heuristic));
						frontier.add(neibours.get(i));
						level++;
						id.add(new LinkedList());
						id.get(level).addAll(id.get(id_state));
						id.get(level).add(level);
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		a_sec = (end - start) / 1000F;
		return "Failure";
	}
	public String DFS (int [][]initial_state){
		int[][] init = initial_state;
		State state=new State(init);
		int level=0;
		int id_state=-1;
		LinkedList <State> frontier = new LinkedList<State>();
		long start = System.currentTimeMillis();
		id=new LinkedList();
		frontier.push(state);
		id.add(new LinkedList());
		id.get(level).add(0);
		explored = new LinkedList<State>();
		while(!(frontier.isEmpty())){
			id_state++;
			state=frontier.pop();
			explored.add(state);
			System.out.println(explored.size() );
			if(compare(state.getStateShape(),goal_state.getStateShape())){
				goal=id_state;
				long end = System.currentTimeMillis();
				dfs_sec = (end - start) / 1000F;
				return "Success";
			}
			neibours=get_neibours(state);
			for(int i=0;i<neibours.size();i++){
				if(!(contain( frontier,neibours.get(i)))){
					if(!(contain(explored,neibours.get(i)))){
						frontier.push(neibours.get(i));
						level++;
						id.add(new LinkedList());
						id.get(level).addAll(id.get(id_state));
						id.get(level).add(level);
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		dfs_sec = (end - start) / 1000F;
		return "Failure";
		
	}
	public void path(){
		int index;
		for(int i=0;i<id.get(goal).size();i++){
			index= id.get(goal).get(i);
			output.add(explored.get(index));
		}
	}
	public void print (){
		for(int i=0;i<output.size();i++){
			for(int j=0;j<output.get(i).getStateShape().length;j++){
				for(int k=0;k<output.get(i).getStateShape()[j].length;k++){
					System.out.print(output.get(i).getStateShape()[j][k]);
					System.out.print(" ");
				}
				System.out.println();
			}
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
		public void print_e (){
			for(int i=0;i<explored.size();i++){
				for(int j=0;j<explored.get(i).getStateShape().length;j++){
					for(int k=0;k<explored.get(i).getStateShape()[j].length;k++){
						System.out.print(explored.get(i).getStateShape()[j][k]);
						System.out.print(" ");
					}
					System.out.println();
				}
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}

	}

	
	
	public int get_cost(){
		return output.size();
	}
	//running time of bfs algorithm
	public float bfs_time(){
		return bfs_sec;
		
	}
	//running time of dfs algorithm
	public float dfs_time(){
		return dfs_sec;
		
	}
	public float a_time(){
		return a_sec;
		
	}
	public int get_depth(){
		return id.get(goal).size();
	}

}
