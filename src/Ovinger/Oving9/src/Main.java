import java.io.*;
import java.nio.Buffer;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Program started...");

        Graph g = new Graph();
        g.newWeightedGraph("noder.txt", "kanter.txt", "interessepkt.txt");
        g.preProcess(new int[]{3007953, 3643361, 4117313, 3722663});
        g.readPreprocessed("fromLandmark.txt", "toLandmark.txt", 4);

        // ALT
        long programTime = System.currentTimeMillis();

        //g.alt(g.node[3292784], g.node[7352330]); //kårvåg-gjemnes
        g.alt(g.node[232073], g.node[2518780]); //tampere-ålesund

        programTime = System.currentTimeMillis() - programTime;
        System.out.println("ALT nodes checked: " + g.nodeCount);
        System.out.println("ALT algorithm runtime: " + programTime + "ms");

        //int time = ((Predecessor)(g.node[7352330]).d).distance / 100; //kårvåg-gjemnes
        int time = ((Predecessor)(g.node[2518780]).d).distance / 100; //tampere-ålesund

        int hrs = time/3600;
        int mins = (time % 3600) / 60;
        int seconds = (time % 3600) % 60;
        System.out.println("ALT Time to drive (HH:MM:SS): " + hrs + ":" + mins + ":" + seconds);
        System.out.println("- - - - - - - - - - - - - - - - -");

        // DIJKSTRA
        programTime = System.currentTimeMillis();

        //g.dijkstra(g.node[3292784], g.node[7352330]); //kårvåg-gjemnes
        g.dijkstra(g.node[232073], g.node[2518780]); //tampere-ålesund

        programTime = System.currentTimeMillis() - programTime;
        System.out.println("Dijkstra nodes checked: " + g.nodeCount);
        System.out.println("Dijkstra algorithm runtime: " + programTime + "ms");

        //time = ((Predecessor)(g.node[7352330]).d).distance / 100; //kårvåg-gjemnes
        time = ((Predecessor)(g.node[2518780]).d).distance / 100; //tampere-ålesund

        hrs = time/3600;
        mins = (time % 3600) / 60;
        seconds = (time % 3600) % 60;
        System.out.println("Dijkstra Time to drive (HH:MM:SS): " + hrs + ":" + mins + ":" + seconds);

        //8 ladestasjonær nær Trondheim lufthavn, Værnes
        //g.dijkstraPointsOfInterest(g.node[7172108], 4,8);

        //8 drikkestedene nærmest Trondheim torg
        //g.dijkstraPointsOfInterest(g.node[4546048], 16,8);

        //8 spisesteder nærmest Hemsedal
        //g.dijkstraPointsOfInterest(g.node[3509663], 8,8);
    }
}


class Graph {
    int N, E, P; //Nodes, Edges, Points of interest
    Node[] node;
    Node[] transposed;
    int[][] toLandmarks, fromLandmarks;
    int time; //kjøretid
    int nodeCount; // antall noder sjekket i en gitt algoritme
    // PriorityQueue-klassen baserer seg på en heap-basert prioritetskø.
    // Standard rekkefølge er som en min-heap.
    PriorityQueue<Node> prio;


    public void newWeightedGraph(String node, String edge, String pointsOfInterest) throws IOException {
        BufferedReader n = new BufferedReader(new FileReader(node));
        BufferedReader e = new BufferedReader(new FileReader(edge));
        BufferedReader p = new BufferedReader(new FileReader(pointsOfInterest));

        readNodes(n);
        readEdges(e);
        readPointsOfInterest(p);
    }

    public void readNodes(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //antall
        node = new Node[N];
        transposed = new Node[N];
        int index;
        double latitude, longitude;
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            index = Integer.parseInt(st.nextToken());
            latitude = Double.parseDouble(st.nextToken());
            longitude = Double.parseDouble(st.nextToken());
            node[i] = new Node(index, latitude, longitude);
            transposed[i] = new Node(index, latitude, longitude);
        }
    }

    public void readEdges(BufferedReader br) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        E = Integer.parseInt(st.nextToken()); //antall
        int from, to, weight, length, speedLimit;
        for (int i = 0; i < E; ++i) {
            st = new StringTokenizer(br.readLine());
            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());
            weight = Integer.parseInt(st.nextToken());
            length = Integer.parseInt(st.nextToken());
            speedLimit = Integer.parseInt(st.nextToken());
            Edge e = new Edge(node[to], node[from].edge1, weight, length, speedLimit);
            node[from].edge1 = e;

            Edge eT = new Edge(transposed[from], transposed[to].edge1, weight, length, speedLimit);
            transposed[to].edge1 = eT;

        }
    }

    public void readPointsOfInterest(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        P = Integer.parseInt(st.nextToken());
        int index, code;
        for (int i = 0; i < P; ++i) {
            String name = "";

            st = new StringTokenizer(br.readLine());
            index = Integer.parseInt(st.nextToken());
            code = Integer.parseInt(st.nextToken());
            while (st.hasMoreTokens()) name += st.nextToken();
            node[index].pointOfInterestCode = code;
            node[index].pointOfInterestName = name;

            transposed[index].pointOfInterestCode = code;
            transposed[index].pointOfInterestName = name;
        }
    }


    // s = startnode, finner korteste avstand til alle andre noder i grafen
    void dijkstra(Node s) {
        initPredecessor(s);
        Node n = s;
        this.prio = new PriorityQueue<>();
        this.prio.offer(s);

        while (!this.prio.isEmpty()){
            n = this.prio.poll(); //tar fremste element i min-heap køen og fjerner det fra køen.

            for (Edge e = n.edge1; e != null; e = e.next) shorten(n, e); //sjekker om det er kortere distanser fra nodens kanter

        }
    }

    // brukes til å finne korteste vei fra startnode til målnode, og legger ved kommaseparert koordinatfil
    // start = startnode, goal = mål
    void dijkstra(Node start, Node goal) throws IOException {
        nodeCount = 0;
        initPredecessor(start);
        Node n = start;
        this.prio = new PriorityQueue<>();
        this.prio.offer(start);

        while (!this.prio.isEmpty()){
            n = this.prio.poll(); //tar fremste element i min-heap køen og fjerner det fra køen.
            nodeCount++;
            if (n == goal){
                FileWriter w = new FileWriter("dijkstra.txt");
                while (n != null) {
                    w.write(n.latitude + ", " + n.longitude + "\n");
                    n = ((Predecessor)n.d).predecessor;
                }
                w.close();
                return;
            }
            for (Edge e = n.edge1; e != null; e = e.next) shorten(n, e); //sjekker om det er kortere distanser fra nodens kanter
        }
    }

    // bruker dijkstra til å finne de nærmeste interessepunktne av en gitt type (code)
    // start = startnode, code = koden som angir type interessepunkt, amount = antall interessepunkt som skal finnes
    void dijkstraPointsOfInterest(Node start, int code, int amount) throws IOException {
        int foundCount = 0;
        initPredecessor(start);
        Node n;
        this.prio = new PriorityQueue<>();
        this.prio.offer(start);
        FileWriter w = new FileWriter("dijkstraPOI.txt");

        while (!this.prio.isEmpty()){
            n = this.prio.poll();
            if ((n.pointOfInterestCode & code) == code){
                w.write(n.latitude + ", " + n.longitude + "\n");
                System.out.println(n.pointOfInterestName); //skriver ut navna på interessepunktene for oversiktens skyld
                foundCount++;
                if (foundCount == amount){
                    w.close();
                    return;
                }
            }
            for (Edge e = n.edge1; e != null; e = e.next) shorten(n, e); //sjekker om det er kortere distanser fra nodens kanter
        }
    }

    void createPriorityQ() {
        this.prio = new PriorityQueue<Node>(N);
        for (int i = 0; i < N; i++) this.prio.offer(node[i]);
    }

    // finner korteste avstand
    void shorten(Node n, Edge e) {
        Predecessor nd = (Predecessor)n.d, md = (Predecessor)e.to.d;
        if (md.distance > nd.distance + e.weight) {
            md.distance = nd.distance + e.weight;
            md.predecessor = n;

            //oppdaterer prioriteringskøen ved å fjerne og legge til noden på nytt, siden den nå har forbedret avstand.
            prio.remove(e.to);
            prio.offer(e.to);
        }
    }

    // node n er startnoden, som betyr at distance settes til 0.
    public void initPredecessor(Node n) {
        for (int i = N; i-- > 0;) {
            node[i].d = new Predecessor();
            transposed[i].d = new Predecessor();
        }
        ((Predecessor)n.d).distance = 0;
    }


    //tar inn array med indekser til nodene som skal brukes som landemerker, og lager tekstfiler med avstander til og fra disse
    public void preProcess(int[] landmarkIndexes) throws IOException {
        File toLandmark = new File("toLandmark.txt");
        int[][] to = new int[landmarkIndexes.length][N];
        File fromLandmark = new File("fromLandmark.txt");
        int[][] from = new int[landmarkIndexes.length][N];

        for (int i = 0; i < landmarkIndexes.length; i++) {
            System.out.println("dijkstra on index " + i);
            dijkstra(node[landmarkIndexes[i]]);

            for (int j = 0; j < N; j++) from[i][j] = ((Predecessor)(node[j].d)).distance;

        }
        BufferedWriter fromWriter = new BufferedWriter(new FileWriter(fromLandmark));

        for (int i = 0; i < N; i++) {
            String fromLine = "";
            for (int j = 0; j < landmarkIndexes.length; j++) {
                fromLine += from[j][i] + " ";
            }
            fromWriter.write(fromLine + "\n");
        }

        for (int i = 0; i < landmarkIndexes.length; i++) {
            System.out.println("dijkstra on transposed, index " + i);
            dijkstra(transposed[landmarkIndexes[i]]);
            for (int j = 0; j < N; j++) to[i][j] = ((Predecessor)(transposed[j].d)).distance;

        }
        BufferedWriter toWriter = new BufferedWriter(new FileWriter(toLandmark));

        for (int i = 0; i < N; i++) {
            String toLine = "";
            for (int j = 0; j < landmarkIndexes.length; j++) {
                toLine += to[j][i] + " ";
            }
            toWriter.write(toLine + "\n");
        }
    }

    //leser inn preproseserte kart og legger de i toLandmarks og fromLandmarks arrayene
    // from og to = filnavnene, l = antall landemerker
    public void readPreprocessed(String from, String to, int l) throws IOException {
        File toLandmark = new File(to);
        toLandmarks = new int[l][N];
        File fromLandmark = new File(from);
        fromLandmarks = new int[l][N];

        BufferedReader brTo = new BufferedReader(new FileReader(toLandmark));
        BufferedReader brFrom = new BufferedReader(new FileReader(fromLandmark));

        int i = 0;
        String read = brTo.readLine();
        while (read != null) {
            String[] line = read.split(" ");
            for (int j = 0; j < line.length; j++) toLandmarks[j][i] = Integer.parseInt(line[j]);
            i++;
            read = brTo.readLine();
        }

        i = 0;
        read = brFrom.readLine();
        while (read != null) {
            String[] line = read.split(" ");
            for (int j = 0; j < line.length; j++) fromLandmarks[j][i] = Integer.parseInt(line[j]);
            i++;
            read = brFrom.readLine();
        }
    }

    //kjører alt-algoritmen for å finne korteste vei fra startnode til målnode, og skriver resultat til fil
    public void alt(Node start, Node goal) throws IOException {
        Node n = null;
        nodeCount = 0;
        initPredecessor(start);
        prio = new PriorityQueue<Node>();

        //estimat
        ((Predecessor)start.d).distanceToGoal = getEstimate(start, goal);
        prio.offer(start);

        while (!prio.isEmpty()) {
            n = prio.poll();
            n.visited = true;
            nodeCount++;
            if (n == goal){
                FileWriter w = new FileWriter("alt.txt");
                while (n != null) {
                    w.write(n.latitude + ", " + n.longitude + "\n");
                    n = ((Predecessor)n.d).predecessor;
                }
                w.close();
                return;
            }

            // sjekker alle kantene fra Node n
            for (Edge e = n.edge1; e != null; e = e.next){
                ((Predecessor)e.to.d).distanceToGoal = getEstimate(e.to, goal);
                int check = ((Predecessor)n.d).distance + ((Predecessor)e.to.d).distanceToGoal + e.weight;

                // sjekker om vi har funnet en forbedret vei
                if (check < ((Predecessor)e.to.d).distance + ((Predecessor)e.to.d).distanceToGoal) {

                    ((Predecessor)e.to.d).distance = ((Predecessor)n.d).distance + e.weight;
                    if (e.to.visited) prio.remove(e.to);
                    prio.add(e.to);
                    e.to.visited = true;
                    ((Predecessor)e.to.d).predecessor = n;
                }
            }
        }

    }

    //gir et estimat på avstand til målnoden fra node n
    public int getEstimate(Node n, Node goal) {
        int index = n.index;
        int goalIndex = goal.index;
        int num1 = 0;
        int num2 = 0;

        for (int i = 0; i < fromLandmarks.length; i++) {
            if (fromLandmarks[i][goalIndex] - fromLandmarks[i][index] > num1) num1 = fromLandmarks[i][goalIndex] - fromLandmarks[i][index];
            if (toLandmarks[i][index] - toLandmarks[i][goalIndex] > num2) num2 = toLandmarks[i][index] - toLandmarks[i][goalIndex];
        }

        return Math.max(num1, num2);
    }
}

class Node implements Comparable<Node> {
    Object d; //andre nodedata
    Edge edge1; //første kanten i nodens kantliste
    double latitude; // breddegrad
    double longitude; // lengdegrad
    int index; // nodenr
    int pointOfInterestCode;
    String pointOfInterestName;
    boolean visited;

    public Node(int i, double latitude, double longitude) {
        this.index = i;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visited = false;
    }

    @Override
    public int compareTo(Node o) {
        if (((Predecessor)this.d).distance + ((Predecessor)this.d).distanceToGoal < ((Predecessor)o.d).distance + ((Predecessor)o.d).distanceToGoal) return -1;
        if (((Predecessor)this.d).distance + ((Predecessor)this.d).distanceToGoal > ((Predecessor)o.d).distance + ((Predecessor)o.d).distanceToGoal) return 1;
        return 0;
    }
}

class Predecessor {
    int distance; //avstand fra denne til startnoden
    int distanceToGoal; //estimat som regnes før den legges inn i køen
    Node predecessor;
    static int inf = 1000000000;
    public int getDistance() {return distance;}
    public Node getPredecessor() {return predecessor;}
    public Predecessor(){
        distance = inf;
    }
}

class Edge {
    Edge next; // neste kant i kantlista
    Node to; //hvilken node kanten går til
    int weight; //vekt til kanten (kjøretid)
    int length; //lengde
    int speedLimit; //fartsgrense

    public Edge(Node n, Edge next, int weigth, int length, int speedLimit){
        this.to = n;
        this.next = next;
        this.weight = weigth;
        this.length = length;
        this.speedLimit = speedLimit;
    }
}

