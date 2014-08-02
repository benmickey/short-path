import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Dijkstra {

	public static BinaryHeap<Vertex> myHeap; 
	public static ArrayList<Vertex> myList; 
	public static ArrayList<Vertex> pathList; 
	public static HashMap<String, Vertex> myHash = new HashMap<String, Vertex>(1000);

	public Dijkstra() {

		pathList = new ArrayList<Vertex>(); 
	}

	public static void DijkstraMethod(Vertex s, ArrayList a, HashMap<String,Vertex> hash)
	{
		myHeap = new BinaryHeap<Vertex>(); 

		for(int i=0; i<a.size(); i++)
		{
			Vertex vert1 = (Vertex) a.get(i); 
			vert1.distance = Double.POSITIVE_INFINITY; 
			vert1.known = false; 
			vert1.prev = null; 
		}

		s.distance = 0; 

		myHeap.insert(s); 



		while(!myHeap.isEmpty())
		{

			Vertex v = myHeap.deleteMin(); 

			if (v.distance == Double.POSITIVE_INFINITY)
				break; 

			v.known = true;
			//--count;  

			for(int k=0; k<v.adjEdge.size(); k++)
			{
				Edge y = v.adjEdge.get(k);
				String adjCity = y.city; 
				Double dist = y.distance; //Edge Cost 
				Vertex adjacent = (Vertex) hash.get(adjCity); //Get the adjacent vertex from HashTable 

				if(adjacent.known == false)//if the adjacent vertex is not known 
				{ 
					double adjacentDistance = dist;

					if(adjacentDistance + v.distance < adjacent.distance)
					{	
						//Not in the Queue
						if(adjacent.distance == Double.POSITIVE_INFINITY)
						{
							adjacent.distance = adjacentDistance + v.distance; 
							adjacent.prev = v; 
							myHeap.insert(adjacent); 
						}

						//In the Queue and need to reBuild Heap to maintain structure
						else{																																							
							adjacent.distance = adjacentDistance + v.distance; 
							adjacent.prev = v; 
							myHeap.reBuildHeap(); 
						}
					}
				}


			}
		}

	}


	public static void printPath(Vertex s)
	{
		if(s.prev != null)
		{
			printPath(s.prev); 
			pathList.add(s); 
			myHash.put(s.cityName, s); 
			System.out.print(" to "); 
		}
		System.out.print(s.cityName); 

	}

	public static ArrayList<Vertex> getPath()
	{
		return pathList; 
	}

	public static HashMap<String, Vertex> getHash()
	{
		return myHash; 
	}
}
