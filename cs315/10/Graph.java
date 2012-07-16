import java.util.*;

interface Heuristic {
	Integer fn(Graph.Vertex x, Graph.Vertex y);
}

public class Graph {
	private TreeMap<String, Vertex> vertices;

	public void add (Vertex v) {
		if (vertices == null) {
			vertices = new TreeMap<String,Vertex>();
		}

		vertices.put(v.contents, v);
	}

	public Vertex vert (String s) {
		Vertex v = new Vertex(s);
		add(v);
		return v;
	}

	public Vertex vert (String s, Double lat, Double longx) {
		Vertex v = new Vertex(s, lat, longx);
		add(v);
		return v;
	}

	public TreeMap<String, Vertex> vertices () {
		return vertices;
	}

	public Vertex vertex (String s) {
		return vertices.get(s);
	}

	public void print () {
		Collection c = vertices.values();
		Iterator itr = c.iterator();
		while(itr.hasNext()) {
			Vertex v = (Vertex) itr.next();
			v.print();
		}
	}

	public Graph (Cons lst) {
		vertices = new TreeMap<String,Vertex>();
		for (Cons ptr = lst; ptr != null; ptr = Cons.rest(ptr)) {
			Cons item = (Cons) Cons.first(ptr);
			Vertex node = vert((String) Cons.first(item), (Double) Cons.second(item), (Double) Cons.third(item));
		}
		for (Cons ptr = lst; ptr != null; ptr = Cons.rest(ptr)) {
			Cons item = (Cons) Cons.first(ptr);
			Cons conns = (Cons) Cons.fourth(item);
			Vertex node = vertices.get((String) Cons.first(item));
			for ( ; conns != null; conns = Cons.rest(conns)) {
				Cons conn = (Cons) Cons.first(conns);
				Vertex target = vertices.get((String) Cons.first(conn));
				node.add(target, (Integer) Cons.second(conn));
				target.add(node, (Integer) Cons.second(conn));
			}
		}
	}

	public class Vertex {
		private String contents;
		private ArrayList<Edge> edges;
		private Integer cost;
		private Integer estimate;
		private Vertex parent;
		private boolean visited;
		private Double latitude;
		private Double longitude;

		public Vertex (String s) {
			contents = s;
			edges = new ArrayList<Edge>();
		}

		public Vertex (String s, Double lat, Double longx) {
			contents = s;
			latitude = lat;
			longitude = longx;
			edges = new ArrayList<Edge>();
		}

		public void add (Edge e) {
			edges.add(e);
		}

		public void add (Vertex v, int c) {
			edges.add(new Edge(v, c));
		}

		public void add (Vertex v, int c, boolean undirected) {
			edges.add(new Edge(v, c));
			if (undirected && (this.contents.compareTo(v.contents) < 0))
			v.edges.add(new Edge(this, c));
		}

		public String str() {
			return contents;
		}

		public int cost() {
			return cost;
		}

		public Vertex parent() {
			return parent;
		}

		public Double latitude() {
			return latitude;
		}

		public Double longitude() {
			return longitude;
		}

		public int compareTo (Vertex other) {
			if (this.cost == other.cost) {
				return 0;
			}
			else if (this.cost < other.cost) {
				return -1;
			}
			else {
				return 1;
			}
		}

		public void print() {
			System.out.print(contents + "\t");
			if ( latitude != 0.0 ) {
				System.out.print("(" + latitude + ", " + longitude + ")\t");
			}
			System.out.print(cost + "\t");
			System.out.print(((parent == null) ? "null" : parent.contents) + "\t");
			for (Edge e : edges) {
				e.print();
			}
			System.out.println();
		}

		public Integer distanceTo(Vertex other) {
			return ((Double) (gcdist(this.latitude, this.longitude, other.latitude, other.longitude) * 0.624)).intValue();
		}
	}

	public class Edge {
		private Vertex target;
		private int cost;

		public Edge (Vertex v, int c) {
			target = v;
			cost = c;
		}

		public void print() {
			System.out.print("(" + target.str() + ", " + cost + ") ");
		}
	}

	// Great-circle distance.  Args in degrees, result in kilometers
	public static double gcdist (double lata, double longa, double latb, double longb) {
		double midlat, psi, dist;
		midlat = 0.5 * (lata + latb);
		psi = 0.0174532925 * Math.sqrt(Math.pow(lata - latb, 2) + Math.pow((longa - longb) * Math.cos(0.0174532925 * midlat), 2));
		dist = 6372.640112 * psi;
		return dist;
	}

	//
	// BEGIN my code
	//

	public int dijkstra (Vertex s) {
		int nodes;
		Collection<Vertex> collection;
		Comparator<Vertex> comparator;
		PriorityQueue<Vertex> fringe;

		nodes = 0;
		collection = vertices.values();
		comparator = new Comparator<Vertex> () {
			public int compare (Vertex a, Vertex b) {
				return a.cost - b.cost;
			}
		};
		fringe = new PriorityQueue<Vertex> (20, comparator);

		for (Vertex vertex : collection) {
			vertex.visited = false;
			vertex.cost = Integer.MAX_VALUE;
		}

		s.cost = 0;
		s.parent = null;

		fringe.add(s);

		while (!fringe.isEmpty()) {
			Vertex vertex = fringe.remove();
			nodes++;

			if (!vertex.visited) {
				vertex.visited = true;

				for (Edge edge : vertex.edges) {
					int newcost = vertex.cost + edge.cost;

					if (newcost < edge.target.cost) {
						edge.target.cost = newcost;
						edge.target.parent = vertex;
						fringe.add(edge.target);
					}
				}
			}
		}

		return nodes;
	}

	public int prim (Vertex s) {
		int cost;
		Collection<Vertex> collection;
		Comparator<Vertex> comparator;
		PriorityQueue<Vertex> fringe;

		cost = 0;
		collection = vertices.values();
		comparator = new Comparator<Vertex> () {
			public int compare (Vertex a, Vertex b) {
				return a.cost - b.cost;
			}
		};
		fringe = new PriorityQueue<Vertex> (20, comparator);

		for (Vertex vertex : collection) {
			vertex.visited = false;
			vertex.parent = null;
			vertex.cost = Integer.MAX_VALUE;
		}

		s.cost = 0;

		fringe.add(s);

		while (!fringe.isEmpty()) {
			Vertex vertex = fringe.remove();

			if (!vertex.visited) {
				vertex.visited = true;

				cost += vertex.cost();

				for (Edge edge : vertex.edges) {
					if ((!edge.target.visited) && (edge.cost < edge.target.cost)) {
						edge.target.cost = edge.cost;
						edge.target.parent = vertex;
						fringe.add(edge.target);
					}
				}
			}
		}

		return cost;
	}

	public int astar (Vertex start, Vertex goal, Heuristic h) {
		int nodes;
		Collection<Vertex> collection;
		Comparator<Vertex> comparator;
		PriorityQueue<Vertex> queue;

		nodes = 0;
		collection = vertices.values();
		comparator = new Comparator<Vertex> () {
			public int compare (Vertex a, Vertex b) {
				return a.cost - b.cost;
			}
		};
		queue = new PriorityQueue<Vertex> (20, comparator);

		for (Vertex vertex : collection) {
			vertex.visited = false;
			vertex.cost = Integer.MAX_VALUE;
		}

		start.cost = 0;
		start.parent = null;

		queue.add(start);

		while (!queue.isEmpty()) {
			Vertex vertex = queue.remove();
			nodes++;

			if (goal.equals(vertex)) {
				break;
			}

			if (!vertex.visited) {
				vertex.visited = true;

				for (Edge edge : vertex.edges) {
					int newcost = vertex.cost + h.fn(vertex, goal);

					if (newcost < edge.target.cost) {
						edge.target.cost = vertex.cost + edge.cost;
						edge.target.parent = vertex;
						queue.add(edge.target);
					}
				}
			}
		}

		return nodes;
	}

	public Cons pathto (String city) {
		Vertex vertex;
		Cons path;

		vertex = vertices.get(city);
		path = null;

		while (vertex != null) {
			path = Cons.cons(vertex.str(), path);
			vertex = vertex.parent();
		}

		return path;
	}

	public int edgecost (Vertex start, Vertex goal) {
		int cost;
		ArrayList<Edge> edges;

		cost = 0;
		edges = start.edges;

		for (Edge edge : edges) {
			if (goal.equals(edge.target)) {
				cost = edge.cost;
				break;
			}
		}

		return cost;
	}

	public int pathcost (Vertex vertex) {
		int cost;

		cost = 0;

		while (vertex.parent() != null) {
			cost += edgecost(vertex, vertex.parent());

			vertex = vertex.parent();
		}

		return cost;
	}

	public int totalcost () {
		int cost;
		Collection<Vertex> collection;

		cost = 0;
		collection = vertices.values();

		for (Vertex vertex : collection) {
			ArrayList<Edge> edges = vertex.edges;

			for (Edge edge : edges) {
				cost += edge.cost;
			}
		}

		return cost / 2;
	}

	static Random rng = new Random();

	public static final Heuristic dist = new Heuristic() {
		public Integer fn (Vertex from, Vertex to) {
			return from.distanceTo(to);
		}
	};

	public static final Heuristic halfass = new Heuristic() {
		public Integer fn (Vertex from, Vertex to) {
			Double result = gcdist(
				from.latitude(),
				from.longitude(),
				to.latitude(),
				to.longitude()
			);
			result /= 2;

			return result.intValue();
		}
	};

	public static final Heuristic zip = new Heuristic() {
		public Integer fn (Vertex from, Vertex to) {
			return 0;
		}
	};

	public static final Heuristic randombs = new Heuristic() {
		public Integer fn (Vertex from, Vertex to) {
			Double result = gcdist(
				from.latitude(),
				from.longitude(),
				to.latitude(),
				to.longitude()
			);
			result /= 2;
			result *= rng.nextDouble();

			return result.intValue();
		}
	};

	public static final Heuristic randomlies = new Heuristic() {
		public Integer fn (Vertex from, Vertex to) {
			Double result = rng.nextDouble();
			result *= 5000;

			return result.intValue();
		}
	};

	//
	// END my code
	//
}