import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GraphAdjacencyList {
	public boolean Visited[];
	private Map<Integer, List<Integer>> Adjacency_List;
	public HashMap<Integer, List<Integer>> resultmap = new HashMap<Integer, List<Integer>>();
	public int lastveretx = 0;

	public GraphAdjacencyList(int number_of_vertices) {
		Adjacency_List = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < number_of_vertices; i++) {
			Adjacency_List.put(i, new LinkedList<Integer>());
		}
		Visited = new boolean[number_of_vertices];
		for (int j = 0; j < number_of_vertices; j++) {
			Visited[j] = false;
		}

	}

	public void setEdge(int source, int destination) {
		if (source > Adjacency_List.size() || destination > Adjacency_List.size()) {
			System.out.println("the vertex entered in not present ");
			return;
		}
		List<Integer> slist = Adjacency_List.get(source);
		slist.add(destination);

		List<Integer> dlist = Adjacency_List.get(destination);
		dlist.add(source);
	}

	public List<Integer> getEdge(int source) {
		if (source > Adjacency_List.size()) {
			System.out.println("the vertex entered is not present");
			return null;
		}
		return Adjacency_List.get(source);
	}

	public void displayadjacentlist(int number_of_vertices, GraphAdjacencyList adjacencyList) {

		System.out.println("the given Adjacency List for the graph \n");
		for (int i = 0; i <= number_of_vertices; i++) {
			System.out.print(i + "->");
			List<Integer> edgeList = adjacencyList.getEdge(i);
			for (int j = 1;; j++) {
				if (j != edgeList.size()) {
					System.out.print(edgeList.get(j - 1) + "->");
				} else {
					System.out.print(edgeList.get(j - 1));
					break;
				}
			}
		
		}
	}

	void BFS(int s, int component) {

		LinkedList<Integer> queue = new LinkedList<Integer>();

		ArrayList<Integer> templist = new ArrayList<Integer>();
		Visited[s] = true;
		queue.add(s);
		templist.add(s);
		while (queue.size() != 0) {

			s = queue.poll();

			Iterator<Integer> i = Adjacency_List.get(s).listIterator();
			while (i.hasNext()) {
				int n = i.next();
				if (!Visited[n]) {
					Visited[n] = true;
					queue.add(n);
					templist.add(n);

				}
			}

		}
		Collections.sort(templist);

		resultmap.put(component, templist);

	}

	public int firstUnvisitedNode() {
		int startvalue = 0;

		for (int i = 0; i < Visited.length; i++) {

			if (Visited[i] == false) {

				startvalue = i;
				break;

			}

		}

		return startvalue;
	}

	public int[] longestconnectedcomponent(GraphAdjacencyList g) {
		int component = 1;

		while (g.isAllvisited() != true) {

			int start_node = g.firstUnvisitedNode();

			g.BFS(start_node, component);
			component++;

		}
		Map<Integer, Integer> size_of_components = new HashMap<Integer, Integer>();
		for (int key : g.resultmap.keySet()) {
			ArrayList<Integer> value = (ArrayList<Integer>) g.resultmap.get(key);
			size_of_components.put(key, value.size());
		}

		Map<Integer, Integer> size_of_component1 = g.sortByComparator(size_of_components);

		int result = 0;

		for (int key : size_of_component1.keySet()) {
			result = key;

		}
		int[] resultset = new int[2];
		resultset[0] = result;
		resultset[1] = size_of_component1.get(result);
		System.out.println("Number of connected components  "  +size_of_component1.size());
		return resultset;
	}

	public Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortMap) {

		List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public boolean isAllvisited() {

		boolean Status = false;

		for (int i = 0; i < Visited.length; i++) {

			if (Visited[i] == true) {

				Status = true;

			} else {
				Status = false;
			}
		}

		return Status;

	}

	public void findDiameter(GraphAdjacencyList g, ArrayList<Integer> resultlist, int size) {
		
		int Size = size;

		int temp = 0;
		int fromvertex = 0;
		int tovertex = 0;
		int result;
		for (int i = 0; i < resultlist.size(); i++) {
			result = unwieghtedShortestpath(resultlist.get(i), Size);
		
			if (temp < result) {
				fromvertex = resultlist.get(i);
				temp = result;
			
			}

			tovertex = lastveretx;
		}
		System.out.println("Diameter is  :  " + temp);
		
		System.out.println("The two vertices which  are the furthest are from: " + fromvertex + "  to: " + tovertex);

	}

	public int unwieghtedShortestpath(int s, int size) {

		int Diameter = 0;
		int Distance[] = new int[size + 1];
		Queue<Integer> queue = new LinkedList<Integer>();
		List<Integer> verticelist = new ArrayList<Integer>();
		verticelist.add(s);
		int v, w;
		queue.add(s);
		for (int i = 0; i < Distance.length; i++) {

			Distance[i] = -1;

		}
		Distance[s] = 0;
		while (!queue.isEmpty()) {
			v = queue.poll();
			Iterator<Integer> i = Adjacency_List.get(v).listIterator();
			while (i.hasNext()) {
				w = i.next();

				if (Distance[w] == -1) {
					Distance[w] = Distance[v] + 1;
					queue.add(w);
					verticelist.add(w);
				}
			}
		}

		for (int i = 0; i < verticelist.size(); i++)

		{

			if (Distance[i] > Diameter) {
				Diameter = Distance[i];
				lastveretx = verticelist.get(i);
			}
		}
		
		return Diameter;

	}

	public static void main(String... arg) throws IOException {
		// File file = new File("relativity.txt");
		File file = new File("dolphins.txt");

		FileReader fileopen = new FileReader(file);
		BufferedReader in = new BufferedReader(fileopen);
		String vertexcount = in.readLine();

		int _number_of_vertex = Integer.parseInt(vertexcount);
		int counter = 0;

		String line = null;

		GraphAdjacencyList adjacencyList = new GraphAdjacencyList(_number_of_vertex);

		while ((line = in.readLine()) != null) {

			counter++;
			if (counter > 0) {
				String[] Edges = line.split("  ");

				int to = Integer.parseInt(Edges[1]);
				int from = Integer.parseInt(Edges[0]);

				adjacencyList.setEdge(to, from);
			}
		}

		int[] result = adjacencyList.longestconnectedcomponent(adjacencyList);

		System.out.println("The size of the largest connected component of the graphis "+result[1]);

		ArrayList<Integer> resultset = (ArrayList<Integer>) adjacencyList.resultmap.get(result[0]);
		int largest = resultset.get(result[1] - 1);
		adjacencyList.findDiameter(adjacencyList, resultset, largest);

	}

}
