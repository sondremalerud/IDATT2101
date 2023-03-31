import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph();
        File ø6g6 = new File("ø6g6.txt");
        g.newGraph(new BufferedReader(new FileReader(ø6g6)));

        g.DFS(g.node[0]);
        for (int i = 1; i < g.node.length; i++) g.DFSearch(g.node[i]);

        g.sortDescDoneTime();
        System.out.println("Strongly connected components for " + ø6g6.getName() + ":");
        for (Node n : g.transposed) g.DFSTransposed(n);
        System.out.println("Amount of strongly connected components: " + g.component);
    }
}

class Predecessor {
    int distance;
    Node predecessor;
    static int inf = 1000000000;
    public int getDistance() {return distance;}
    public Node getPredecessor() {return predecessor;}
    public Predecessor(){
        distance = inf;
    }
}

class DFSPredecessor extends Predecessor {
    int foundTime, doneTime;
    static int time;
    static void resetTime() {time = 0;}
    static int readTime() {return time++;}
}

// alle kanter er med i en hver nodes kantliste
class Edge {
    Edge next; // neste kant i kantlista
    Node to; //hvilken node kanten går til

    public Edge(Node n, Edge next){
        this.to = n;
        this.next = next;
    }
}

class Node {
    Edge edge1; //første kanten i nodens kantliste
    Object d; //Andre nodedata

    int index;
    boolean visited = false;

    public Node(int i){
        index = i;
    }
}



class Graph {
    int N, E; //Node, Edge
    int component = 0;
    Node[] node;
    Node[] transposed;

    public void newGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // first token should always be = N
        node = new Node[N];
        transposed = new Node[N];
        for (int i = 0; i < N; i++) {
            node[i] = new Node(i);
            transposed[i] = new Node(i);
        }
        E = Integer.parseInt(st.nextToken()); // second token should always be = E
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            Edge e = new Edge(node[to], node[from].edge1);
            node[from].edge1 = e;

            Edge eT = new Edge(transposed[from], transposed[to].edge1);
            transposed[to].edge1 = eT;
        }
    }

    public void DFSInit(Node[] graph) {
        for (int i = N; i-- > 0;){
            graph[i].d = new DFSPredecessor();
        }
        DFSPredecessor.resetTime();
    }

    public void DFSearch(Node n) {
        if (n.visited) return;
        n.visited = true;

        DFSPredecessor nd = (DFSPredecessor) n.d;
        nd.foundTime = DFSPredecessor.readTime();
        for (Edge e = n.edge1; e!= null; e = e.next){
            DFSPredecessor md = (DFSPredecessor) e.to.d;
            if (md.foundTime == 0){
                md.predecessor = n;
                md.distance = nd.distance + 1;
                DFSearch(e.to);
            }
        }
        nd.doneTime = DFSPredecessor.readTime();
    }

    public void DFSearchTransposed(Node n) {
        if (n.visited) return;
        n.visited = true;
        if (N < 100) System.out.print("n" + n.index + " ");
        DFSPredecessor nd = (DFSPredecessor) n.d; //node data
        nd.foundTime = DFSPredecessor.readTime();
        for (Edge e = n.edge1; e!= null; e = e.next){
            DFSPredecessor md = (DFSPredecessor) e.to.d; //node data
            if (md.foundTime == 0){
                md.predecessor = n;
                md.distance = nd.distance + 1;
                DFSearchTransposed(e.to);
            }
        }
        nd.doneTime = DFSPredecessor.readTime();
    }

    public void DFS(Node s) {
        if (s.visited) return;
        DFSInit(node);
        ((DFSPredecessor)s.d).distance = 0;
        DFSearch(s);
    }

    // runs dfs on transposed graph and prints out strongly connected nodes.
    public void DFSTransposed(Node s) {
        if (s.visited) return;
        component++;
        if (N < 100) System.out.print("Component " + component + ":    ");
        DFSInit(transposed);
        ((DFSPredecessor)s.d).distance = 0;
        DFSearchTransposed(s);
        if (N < 100) System.out.println();
    }

    //sorts the graph by descending done time, and applies the sorted structure to the transposed graph as well
    public void sortDescDoneTime() {
        Node temp, tTemp; //temp is for nodes[] tTemp is for transposed[]
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if(((DFSPredecessor)node[i].d).doneTime < ((DFSPredecessor)node[j].d).doneTime) {
                    temp = node[i];
                    tTemp = transposed[i];

                    node[i] = node[j];
                    transposed[i] = transposed[j];

                    node[j] = temp;
                    transposed[j] = tTemp;
                }
            }
        }
    }
}



