import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tester
{
	public static BinaryHeap myHeap = new BinaryHeap(); 

	public static void main(String[] args) throws IOException
	{ 
		String data = args[0]; //Data Set File
		String coordinates = args[1]; // X,Y Coordinates 
		BufferedReader cityPairs = new BufferedReader(new FileReader(new File(data)));
		BufferedReader xy = new BufferedReader(new FileReader(new File(coordinates)));

		HashMap<String, Vertex> myHash = new HashMap<String, Vertex>(1000);
		ArrayList<Vertex> a = new ArrayList<Vertex>(); 

		//Now Build Up Graph of Adjacencies 
		String line2 = cityPairs.readLine(); 
		while(line2 != null)
		{
			StringTokenizer st = new StringTokenizer(line2);
			while (st.hasMoreTokens())
			{
				String city = st.nextToken(); //1st City
				String adjCity = st.nextToken(); //2nd City
				if(myHash.containsKey(city) == false)
				{
					Vertex x = new Vertex(); 
					x.cityName = city; 
					myHash.put(city, x); 
					a.add(x); 
				}

				if(myHash.containsKey(adjCity) == false)
				{
					Vertex y = new Vertex(); 
					y.cityName = adjCity; 
					myHash.put(adjCity, y); 
					a.add(y); 
				}

				double dist = Double.parseDouble(st.nextToken()); //Distance
				Vertex p = (Vertex) myHash.get(city); 
				Vertex l = (Vertex) myHash.get(adjCity); 
				Edge x = new Edge(adjCity, dist); 
				Edge y = new Edge(city, dist); 
				p.adjEdge.add(x);
				l.adjEdge.add(y); 
			}
			line2 = cityPairs.readLine();  
		}

		String line = xy.readLine(); 
		while(line != null)
		{
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens())
			{ 
				String cityName = st.nextToken(); 
				int xpos = Integer.parseInt(st.nextToken()); 
				int ypos = Integer.parseInt(st.nextToken()); 
				if(myHash.containsKey(cityName))
				{
					Vertex y = myHash.get(cityName); 
					y.xpos = xpos; 
					y.ypos = ypos; 
				}

			}

			line = xy.readLine(); 
		}

		DisplayTree dt = new DisplayTree(a, myHash);//get a display panel
		dt.setVisible(true);
	}
}









