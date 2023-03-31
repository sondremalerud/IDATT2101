import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        int startNode = 0;
        Scanner scan = new Scanner(System.in);
        Graph g = new Graph();
        File file = new File("vg1.txt");
        g.newWeightedGraph(new BufferedReader(new FileReader(file)));

        System.out.println("The graph has " + g.N + " nodes. Which node do you want to use as starting node? (number between 0-" + (g.N-1) + ")");
        startNode = scan.nextInt();

        g.dijkstra(g.node[startNode]);
        System.out.println("Dijkstra's algorithm on " + file.getName() + " with start node = " + startNode);
        System.out.println("Node     Predecessor     Distance");
        System.out.println("=================================");
        for (int i = 0; i < g.N; i++) {
            if (g.node[i].index == startNode)
                System.out.println(i + "              start            " + ((Predecessor) g.node[i].d).distance);
            else if (((Predecessor)g.node[i].d).predecessor == null)
                System.out.println(i + "                     not reached");
            else System.out.println(i + "                  " + ((Predecessor)g.node[i].d).predecessor.index + "            " + ((Predecessor) g.node[i].d).distance);
        }
    }
}

class Graph {
    int N, E; //Node, Edge
    Node[] node;

    // PriorityQueue-klassen baserer seg på en heap-basert prioritetskø.
    // Standard rekkefølge er som en min-heap.
    PriorityQueue<Node> prio;

    public void newWeightedGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        node = new Node[N];
        for (int i = 0; i < N; ++i) node[i] = new Node(i);
        E = Integer.parseInt(st.nextToken());
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            WeightedEdge e = new WeightedEdge(node[to], node[from].edge1, weight);
            node[from].edge1 = e;
        }
    }

    // node n er startnoden, som betyr at distance settes til 0.
    public void initPredecessor(Node n) {
        for (int i = N; i-- > 0;) {
            node[i].d = new Predecessor();
        }
        ((Predecessor)n.d).distance = 0;
    }

    // finner korteste avstand
    void shorten(Node n, WeightedEdge e) {
        Predecessor nd = (Predecessor)n.d, md = (Predecessor)e.to.d;
        if (md.distance > nd.distance + e.weight) {
            md.distance = nd.distance + e.weight;
            md.predecessor = n;

            //oppdaterer prioriteringskøen ved å fjerne og legge til noden på nytt, siden den nå har forbedret avstand.
            prio.remove(e.to);
            prio.offer(e.to);
        }
    }

    // s = startnode
    void dijkstra(Node s) {
        initPredecessor(s);
        createPriorityQ();
        for (int i = N; i > 1; --i) {
            Node n = this.prio.poll(); //tar fremste element i min-heap køen og fjerner det fra køen.
            for (WeightedEdge e = n.edge1; e != null; e = e.next) shorten(n, e); //sjekker om det er kortere distanser fra nodens kanter
        }
    }

    void createPriorityQ() {
        this.prio = new PriorityQueue<Node>(N);
        for (int i = 0; i < N; i++) this.prio.offer(node[i]);
    }
}

class Node implements Comparable<Node> {
    WeightedEdge edge1; //første kanten i nodens kantliste
    Object d; //Andre nodedata
    int index;

    public Node(int i) {
        index = i;
    }

    @Override
    public int compareTo(Node o) {
        if (((Predecessor)this.d).distance < ((Predecessor)o.d).distance) return -1;
        if (((Predecessor)this.d).distance > ((Predecessor)o.d).distance) return 1;
        return 0;
    }
}

// alle kanter er med i en hver nodes kantliste
class WeightedEdge {
    WeightedEdge next; // neste kant i kantlista
    Node to; //hvilken node kanten går til
    int weight; //vekt til kanten

    public WeightedEdge(Node n, WeightedEdge next, int weigth){
        this.to = n;
        this.next = next;
        this.weight = weigth;
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

