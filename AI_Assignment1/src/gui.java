import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class gui {

	Solve_Puzzle solve;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
    private String grid1="1",grid2="2",grid3="3",grid4="4",grid5="5",grid6="6",grid7="7",grid8="8",grid9="";
    private Color cgrid1=Color.PINK,cgrid2=Color.PINK,cgrid3=Color.PINK,cgrid4=Color.PINK,cgrid5=Color.PINK,cgrid6=Color.PINK,cgrid7=Color.PINK,cgrid8=Color.PINK,cgrid9=Color.WHITE;
    private JButton btnNewButton_1,button,button_1,button_2,button_3,button_4,button_5,button_6,button_7;
    private JTextField textField_5;
    private JButton btnNewButton_2;
    private LinkedList<State> output=new LinkedList<State>();
    int next=0;
    
    Timer tm;
    
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 718, 491);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Initial State");
		lblNewLabel.setLabelFor(frame);
		lblNewLabel.setBounds(10, 28, 83, 27);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(88, 31, 249, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Method");
		lblNewLabel_1.setBounds(10, 77, 55, 27);
		frame.getContentPane().add(lblNewLabel_1);
		
		String [] s1= {"BFS Search","DFS Search","A* Search"};
		JComboBox comboBox = new JComboBox(s1);
		comboBox.setBounds(103, 79, 146, 22);
		frame.getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("Run");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solve = new Solve_Puzzle();
				String initial_string=textField.getText();
				String[] splited=initial_string.split(",");
				int init [][]=new int[3][3];
				int index=0;
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						init[i][j]=Integer.parseInt(splited[index]);
						index++;
					}
				}
				String method=(String) comboBox.getItemAt(comboBox.getSelectedIndex());
				String complete="";
				if(method=="BFS Search") {
					 complete =solve.BFS(init);
					    solve.path();
						solve.print();
						textField_2.setText(Double.toString(solve.bfs_time()));

				}else if(method=="DFS Search") {
					 complete =solve.DFS(init);
					 solve.path();
						solve.print();
						textField_2.setText(Double.toString(solve.dfs_time()));

				}else {
					 complete =solve.AStar(init, textField_5.getText().charAt(0));
					 solve.path();
						solve.print();
						textField_2.setText(Double.toString(solve.a_time()));					 
				}
				
				
				textField_1.setText(Integer.toString(solve.get_cost()));
				textField_3.setText(Integer.toString(solve.get_depth()));
				textField_4.setText(complete);
				
				output=solve.output;
				if(next<output.size()) {
				int [][] state=output.get(next).getStateShape();
				
				grid1=getoutputstring(state[0][0]);
				grid2=getoutputstring(state[0][1]);
				grid3=getoutputstring(state[0][2]);
				grid4=getoutputstring(state[1][0]);
				grid5=getoutputstring(state[1][1]);
				grid6=getoutputstring(state[1][2]);
				grid7=getoutputstring(state[2][0]);
				grid8=getoutputstring(state[2][1]);
				grid9=getoutputstring(state[2][2]);
				
				cgrid1=getoutputcolor(state[0][0]);
				cgrid2=getoutputcolor(state[0][1]);
				cgrid3=getoutputcolor(state[0][2]);
				cgrid4=getoutputcolor(state[1][0]);
				cgrid5=getoutputcolor(state[1][1]);
				cgrid6=getoutputcolor(state[1][2]);
				cgrid7=getoutputcolor(state[2][0]);
				cgrid8=getoutputcolor(state[2][1]);
				cgrid9=getoutputcolor(state[2][2]);
				System.out.println(grid1+" "+grid2+" "+grid3+" "+grid4+" "+grid5+" "+grid6+" "+grid7+" "+grid8+" "+grid9);
				
				printfun();
				next++;
				tm.start();
				}
			}
		});
		btnNewButton.setBounds(284, 79, 73, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblCost = new JLabel("Cost :");
		lblCost.setBounds(466, 83, 46, 14);
		frame.getContentPane().add(lblCost);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(466, 120, 46, 14);
		frame.getContentPane().add(lblTime);
		
		textField_1 = new JTextField();
		textField_1.setBounds(502, 80, 177, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(502, 117, 177, 20);
		frame.getContentPane().add(textField_2);
		
		JLabel lblDepth = new JLabel("Depth:");
		lblDepth.setBounds(466, 163, 46, 14);
		frame.getContentPane().add(lblDepth);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(502, 160, 177, 20);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(484, 31, 115, 27);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Output Path :");
		lblNewLabel_2.setBounds(29, 172, 132, 27);
		frame.getContentPane().add(lblNewLabel_2);
		
		
	    btnNewButton_1 = new JButton(grid1);
			btnNewButton_1.setBackground(cgrid1);
			btnNewButton_1.setBounds(56, 222, 55, 45);
			frame.getContentPane().add(btnNewButton_1);
			
		    button = new JButton(grid2);
			button.setBackground(cgrid2);
			button.setBounds(106, 222, 55, 45);
			frame.getContentPane().add(button);
			
			 button_1 = new JButton(grid3);
			button_1.setBackground(cgrid3);
			button_1.setBounds(159, 222, 55, 45);
			frame.getContentPane().add(button_1);
			
		    button_2 = new JButton(grid4);
			button_2.setBackground(cgrid4);
			button_2.setBounds(56, 265, 55, 45);
			frame.getContentPane().add(button_2);
			
			button_3 = new JButton(grid5);
			button_3.setBackground(cgrid5);
			button_3.setBounds(106, 265, 55, 45);
			frame.getContentPane().add(button_3);
			
			 button_4 = new JButton(grid6);
			button_4.setBackground(cgrid6);
			button_4.setBounds(159, 265, 55, 45);
			frame.getContentPane().add(button_4);
			
			 button_5 = new JButton(grid7);
			button_5.setBackground(cgrid7);
			button_5.setBounds(56, 308, 55, 45);
			frame.getContentPane().add(button_5);
			
			button_6 = new JButton(grid8);
			button_6.setBackground(cgrid8);
			button_6.setBounds(106, 308, 55, 45);
			frame.getContentPane().add(button_6);
			
		    button_7 = new JButton(grid9);
			button_7.setBackground(cgrid9);
			button_7.setBounds(159, 308, 55, 45);
			frame.getContentPane().add(button_7);
			
			textField_5 = new JTextField();
			textField_5.setBounds(347, 31, 33, 20);
			frame.getContentPane().add(textField_5);
			textField_5.setColumns(10);
			
			btnNewButton_2 = new JButton("Next");
			btnNewButton_2 .setVisible(false);
			
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(next<output.size()) {
						int [][] state=output.get(next).getStateShape();
						
						grid1=getoutputstring(state[0][0]);
						grid2=getoutputstring(state[0][1]);
						grid3=getoutputstring(state[0][2]);
						grid4=getoutputstring(state[1][0]);
						grid5=getoutputstring(state[1][1]);
						grid6=getoutputstring(state[1][2]);
						grid7=getoutputstring(state[2][0]);
						grid8=getoutputstring(state[2][1]);
						grid9=getoutputstring(state[2][2]);
						
						cgrid1=getoutputcolor(state[0][0]);
						cgrid2=getoutputcolor(state[0][1]);
						cgrid3=getoutputcolor(state[0][2]);
						cgrid4=getoutputcolor(state[1][0]);
						cgrid5=getoutputcolor(state[1][1]);
						cgrid6=getoutputcolor(state[1][2]);
						cgrid7=getoutputcolor(state[2][0]);
						cgrid8=getoutputcolor(state[2][1]);
						cgrid9=getoutputcolor(state[2][2]);
						System.out.println(grid1+" "+grid2+" "+grid3+" "+grid4+" "+grid5+" "+grid6+" "+grid7+" "+grid8+" "+grid9);
						printfun();
						next++;
					}else {
						JOptionPane.showConfirmDialog(null, "this is the final state","confirm", JOptionPane.DEFAULT_OPTION);
						next=0;
						
					}
				}
			});
			btnNewButton_2.setBounds(271, 306, 73, 35);
			frame.getContentPane().add(btnNewButton_2);
		
			tm=new Timer(1000,new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(next<output.size()) {
						int [][] state=output.get(next).getStateShape();
						
						grid1=getoutputstring(state[0][0]);
						grid2=getoutputstring(state[0][1]);
						grid3=getoutputstring(state[0][2]);
						grid4=getoutputstring(state[1][0]);
						grid5=getoutputstring(state[1][1]);
						grid6=getoutputstring(state[1][2]);
						grid7=getoutputstring(state[2][0]);
						grid8=getoutputstring(state[2][1]);
						grid9=getoutputstring(state[2][2]);
						
						cgrid1=getoutputcolor(state[0][0]);
						cgrid2=getoutputcolor(state[0][1]);
						cgrid3=getoutputcolor(state[0][2]);
						cgrid4=getoutputcolor(state[1][0]);
						cgrid5=getoutputcolor(state[1][1]);
						cgrid6=getoutputcolor(state[1][2]);
						cgrid7=getoutputcolor(state[2][0]);
						cgrid8=getoutputcolor(state[2][1]);
						cgrid9=getoutputcolor(state[2][2]);
						System.out.println(grid1+" "+grid2+" "+grid3+" "+grid4+" "+grid5+" "+grid6+" "+grid7+" "+grid8+" "+grid9);
						printfun();
						next++;
					}else {
						//JOptionPane.showConfirmDialog(null, "this is the final state","confirm", JOptionPane.DEFAULT_OPTION);
						next=0;
						tm.stop();
						
					}
				}
				});
		
		}
	
	void printfun() {
	    btnNewButton_1.setText(grid1);
		btnNewButton_1.setBackground(cgrid1);
	
	    button.setText(grid2);
		button.setBackground(cgrid2);
		
		 button_1.setText(grid3);
		button_1.setBackground(cgrid3);
		
	    button_2.setText(grid4);
		button_2.setBackground(cgrid4);
		
		button_3.setText(grid5);
		button_3.setBackground(cgrid5);
		
		 button_4.setText(grid6);
		button_4.setBackground(cgrid6);
		
		 button_5.setText(grid7);
		button_5.setBackground(cgrid7);
		
		button_6.setText(grid8);
		button_6.setBackground(cgrid8);
		
		
		button_7.setBackground(cgrid9);
		button_7.setText(grid9);
	
	}
	
	String getoutputstring(int x) {
		if(x==0) {
			return "";
		}else {
			return Integer.toString(x);
		}
	}
	Color getoutputcolor(int x) {
		if(x==0) {
			return Color.WHITE;
		}else {
			return Color.PINK;
		}
	}
}
