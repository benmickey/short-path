
// in this case we are displaying a Binary Search tree  
// reference problem 4.38 of Weiss to compute tree node x,y positions

// input is a text file name that will form the Binary Search Tree

//     java DisplaySimpleTree textfile


import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import javax.swing.JPopupMenu;

public class DisplayTree extends JFrame {
	JScrollPane scrollpane;
	DisplayPanel panel;

	JMenuBar menuBar;
	JMenu menu; 

	public DisplayTree(ArrayList<Vertex> x, HashMap<String,Vertex> y) {
		panel = new DisplayPanel(x, y);
		panel.setPreferredSize(new Dimension(300, 300));
		scrollpane = new JScrollPane(panel); 

		getContentPane().add(scrollpane, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		pack();  // cleans up the window panel

	}


}

class DisplayPanel extends JPanel {


	public ArrayList<Vertex> x; 
	public  HashMap y; 
	int xs;
	int ys;
	public static String source; 
	public static String dest; 
	public static Graphics gg; 
	public static ArrayList<Vertex> path; 
	public boolean regraph = false; 
	public static Vertex mySource; 
	public static Vertex destin; 

	public DisplayPanel(ArrayList<Vertex> a, HashMap<String,Vertex> b) {
		this.x = a; // allows dispay routines to access the tree
		this.y = b; 
		setBackground(Color.white);
		setForeground(Color.black);
		JMenuBar menu = new JMenuBar(); 
	
		String [] xcombo = new String[x.size()]; 
		for(int i = 0; i <x.size(); i++)
		{
			Vertex vv = (Vertex) x.get(i); 
			xcombo[i] = vv.cityName;  
		}

		JComboBox menu1 = new JComboBox(xcombo); 
		JComboBox menu2 = new JComboBox(xcombo); 
		menu1.setName("Source"); 
		menu2.setName("Destination");
		menu1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JComboBox cb = (JComboBox)e.getSource(); 
				source = (String)cb.getSelectedItem(); 
			}
		});

		menu2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JComboBox cb = (JComboBox)e.getSource(); 
				dest = (String)cb.getSelectedItem(); 

			}
		});

		JButton button = new JButton("Run Dijkstra");
		String s = (String) menu1.getSelectedItem(); 
		String d = (String) menu2.getSelectedItem(); 
		//int source1 = menu1.getSelectedIndex();
		//int dest1 = menu2.getSelectedIndex(); 
		final DisplayPanel pan = this; 
		button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				System.out.println(" "); 
				System.out.println("----Dijkstra Shortest Path----"); 
				Vertex start = (Vertex) y.get(source); 
				Dijkstra d = new Dijkstra(); 
				d.DijkstraMethod(start, x, y); 
				Vertex end = (Vertex) y.get(dest); 
				d.printPath(end);
				//path.add(start); 
				mySource = start; 
				destin = end; 
				path = d.getPath();
				
				//Graphics g = null; 
				regraph = true; 
				//pan.repaint(); 
				
				//pan.drawPath(gg, path); 
				//revalidate(); 
				pan.repaint(); 

			}
		});



		menu2.setVisible(true); 
		menu1.setVisible(true); 
		button.setVisible(true); 
		menu.add(menu1); 
		menu.add(menu2); 
		menu.add(button); 
		menu.setVisible(true); 
		this.add(menu); 
		//this.add(menu2);
		//this.add(menu1);
	}


	public void paintComponent(Graphics g) { 	
		
		g.setColor(getBackground()); //colors the window
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground()); //set color and fonts
		Font MyFont = new Font("SansSerif",Font.PLAIN,10);
		g.setFont(MyFont);
		xs=20;   //where to start printing on the panel
		ys=20;
		g.drawString("Representation of US Cities & Dijkstra's Algorithm \n",xs,ys);
		ys=ys+10;;
		int start=0;


		MyFont = new Font("SansSerif",Font.BOLD,15); //bigger font for tree
		g.setFont(MyFont);

		this.drawTree(g, this.x, this.y); // draw the tree  
		
		if(regraph)
		{
			this.drawPath(g, path);
			
		}
	
		

	}

	public void drawPath(Graphics g, ArrayList<Vertex> c)
	{
		
		int xpos = mySource.xpos / 3; 
		int ypos = mySource.ypos / 3; 
		ypos = 500-ypos; 
		Vertex v = (Vertex) c.get(0); 
		
		int xpos2 = v.xpos/3; 
		int ypos2 = v.ypos/3;
		ypos2 = 500-ypos2; 
		g.setColor(Color.RED);
		g.drawLine(xpos,ypos,xpos2,ypos2);
		
		
		for(int i=0; i<c.size()-1; i++)
		{
			Vertex v1 = (Vertex) c.get(i); 
			//System.out.println(v1.cityName); 
			int x1 = v1.xpos/3; 
			int y1 = v1.ypos/3;
			y1 = 500-y1; 
			//g.drawString(v1.cityName, 10, 10); 
			Vertex v2 = (Vertex) c.get(i+1); 
			int x2 = v2.xpos/3;
			int y2 = v2.ypos/3;
			y2 = 500-y2; 
			//g.setPaintMode();
			g.setColor(Color.RED);
			g.drawLine(x1,y1,x2,y2);
			

		}
		g.setColor(Color.BLACK); 
		g.drawString("Distance: ", 50, 55); 
		String doub = Double.toString(destin.distance); 
		g.drawString(doub, 200, 55); 

	}

	public void drawTree(Graphics g, ArrayList<Vertex> x, HashMap<String,Vertex> hash) {//actually draws the tree


		int dx, dy, dx2, dy2;
		int SCREEN_WIDTH=300; //screen size for panel
		int SCREEN_HEIGHT=500;
		int XSCALE, YSCALE;  
		XSCALE=SCREEN_WIDTH;  //scale x by total nodes in tree
		YSCALE=SCREEN_HEIGHT; //scale y by tree height

		for(int i=0; i<x.size(); i++) { // inorder traversal to draw each node

			//System.out.println("here"); 
			Vertex p = x.get(i); 
			String s = x.get(i).cityName; 
			//System.out.println(p.xpos); 

			dx = x.get(i).xpos / 3; // get x,y coords., and scale them 
			dy = x.get(i).ypos /3 ;
			dy = 500-dy;  
			//g.drawString(x.get(i).cityName, dx, dy); // draws the word
			g.drawString(s, dx, dy); 
			g.fillRect(dx, dy, 5, 5); 



			if(p.adjEdge.size()>0)
			{
				for(int j=0; j<p.adjEdge.size(); j++)
				{
					Edge ed = p.adjEdge.get(j); 
					String y = ed.city; 
					Vertex c = hash.get(y); 
					dx2 = c.xpos / 3;//get right child x,y scaled position
					dy2 = c.ypos / 3;
					dy2 = 500 -dy2; 
					//g.drawLine(dx,dy,dx2,dy2);

				}
			}
		}

		gg = g; 
	}
}


class MovingButtonListener implements ActionListener{

	ArrayList<Vertex> arr  = new ArrayList<Vertex>(); 
	HashMap<String, Vertex> myHash = new HashMap<String, Vertex>(1000);
	String source; 
	String dest; 


	public MovingButtonListener(ArrayList<Vertex> x, HashMap<String,Vertex> hash, String s, String d) {
		System.out.println(s); 
		System.out.println(s); 
		this.arr = x; 
		this.myHash = hash; 
		this.source = s; 
		this.dest = d; 

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Vertex start = (Vertex) myHash.get(source); 
		Dijkstra d = new Dijkstra(); 
		d.DijkstraMethod(start, arr, myHash); 
		Vertex end = (Vertex) myHash.get(dest); 
		Dijkstra.printPath(end);

	}
}


