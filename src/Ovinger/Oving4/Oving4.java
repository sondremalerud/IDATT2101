package Ovinger.Oving4;

import java.util.LinkedList;
import java.util.Scanner;

public class Oving4 {
}



class Node {
    int element;
    Node neste;
    Node forrige;

    public Node(int e, Node n, Node f) {
        element = e;
        neste = n;
        forrige = f;
    }

    public int finnElement() {
        return element;
    }

    public Node finnNeste() {
        return neste;
    }

    public Node finnForrige() {
        return forrige;
    }
}

class DobbelLenke {
    private Node hode = null;
    private Node hale = null;
    private int antElementer = 0;

    public int finnAntall() {
        return antElementer;
    }

    public Node finnHode() {
        return hode;
    }

    public Node finnHale() {
        return hale;
    }

    public void skrivUtListe() {
        for (Node n = hode; n != null; n = n.neste) {
            System.out.print(n.element);
        }
    }

    public void settInnFremst(int verdi) {
        hode = new Node(verdi, hode, null);
        if (hale == null) hale = hode;
        else hode.neste.forrige = hode;
        ++antElementer;
    }

    public void settInnEtter(Node forrige, int verdi) {
        Node ny = new Node(verdi, forrige.neste, forrige);
        forrige.neste = ny;

        if (ny.neste != null) ny.neste.forrige = ny;
        else hale = ny;
        antElementer++;
    }

    public void settInnFør(Node neste, int verdi) {
        Node ny = new Node(verdi, neste, neste.forrige);
        neste.forrige = ny;

        if (ny.forrige != null) ny.forrige.neste = ny;
        else hode = ny;
        antElementer++;
    }

    public void settInnBakerst(int verdi) {
        Node ny = new Node(verdi, null, hale);
        if (hale != null) hale.neste = ny;
        else hode = ny;
        hale = ny;
        ++antElementer;
    }

    public Node fjern(Node n) {
        if (n.forrige != null) n.forrige.neste = n.neste;
        else hode = n.neste;
        if (n.neste != null) n.neste.forrige = n.forrige;
        else hale = n.forrige;
        n.neste = null;
        n.forrige = null;
        --antElementer;
        return n;
    }

    public Node finnNr(int nr) {
        Node denne = hode;
        if (nr < antElementer) {
            for (int i = 0; i < nr; i++) denne = denne.neste;
            return denne;
        } else return null;
    }


    public void slettAlle() {
        hode = null;
        hale = null;
        antElementer = 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Node n = hode; n != null; n = n.neste) {
            s.append(n.element);
        }
        return s.toString();
    }
}

class HeltallKalkulator {

    private DobbelLenke tall1 = new DobbelLenke();
    private DobbelLenke tall2 = new DobbelLenke();
    private DobbelLenke resultatTall = new DobbelLenke();

    public DobbelLenke calculate(String input) {
        resultatTall.slettAlle();
        tall1.slettAlle();
        tall2.slettAlle();
        if (input.split(" ").length != 3) {
            throw new IllegalArgumentException("Må være på formatet \"tall operasjon tall\" med mellomrom.");
        }
        String[] inputTabell = input.split(" ");

        // Gir en tabbell som deler tallet opp slik [1, 2, 3] ved å subtrahere ascii-verdien til tallene med ascii-verdien til 0.
        int[] tall1Tabell = inputTabell[0].chars().map(e -> e - '0').toArray();
        int[] tall2Tabell = inputTabell[2].chars().map(e -> e - '0').toArray();
        String operasjon = inputTabell[1];

        for (int tall : tall1Tabell) tall1.settInnBakerst(tall);
        for (int tall : tall2Tabell) tall2.settInnBakerst(tall);

        switch (operasjon) {
            case "+" -> addere(tall1, tall2);
            case "-" -> subtrahere(tall1, tall2);
            default -> throw new IllegalArgumentException("Du har ikke oppgitt en gyldig operasjon, bruk '+' eller '-'.");
        }

        return resultatTall;
    }

    public void addere(DobbelLenke tall1, DobbelLenke tall2) {
        int lengde = Math.max(tall1.finnAntall(), tall2.finnAntall()); //lengden blir den lengste lista

        int rest = 0;
        int tall = 0;

        Node node1 = tall1.finnHale();
        Node node2 = tall2.finnHale();
        int node1tall = node1.finnElement();
        int node2tall = node2.finnElement();

        int i = 0;

        while (i != lengde) {
            tall = rest + node1tall + node2tall;
            if (tall < 10) {
                resultatTall.settInnFremst(tall);
                rest = 0;
            } else {
                resultatTall.settInnFremst(tall % 10);
                rest = 1;
            }

            if (node1 != null) {
                node1 = node1.finnForrige();
                node1tall = (node1 == null) ? 0 : node1.finnElement();
            }
            if (node2 != null) {
                node2 = node2.finnForrige();
                node2tall = (node2 == null) ? 0 : node2.finnElement();
            }
            i++;
        }
        if (rest != 0) resultatTall.settInnFremst(rest);
    }

    public void subtrahere(DobbelLenke tall1, DobbelLenke tall2) {
        int lengde = Math.max(tall1.finnAntall(), tall2.finnAntall()); //lengden blir den lengste lista

        int tall = 0;
        int rest = 0;

        Node node1 = tall1.finnHale();
        Node node2 = tall2.finnHale();
        int node1tall = node1.finnElement();
        int node2tall = node2.finnElement();

        int i = 0;

        while (i != lengde) {

            tall = rest + node1tall - node2tall;
            if (tall < 0) {
                resultatTall.settInnFremst(10 + tall);
                rest = -1;
            } else {
                resultatTall.settInnFremst(tall);
                rest = 0;
            }

            if (node1 != null) {
                node1 = node1.finnForrige();
                node1tall = (node1 == null) ? 0 : node1.finnElement();
            }
            if (node2 != null) {
                node2 = node2.finnForrige();
                node2tall = (node2 == null) ? 0 : node2.finnElement();
            }
            i++;
        }
        // passer på at f.eks 100 - 99 ikke returnerer 001, men heller 1.
        for (int j = 0; j < resultatTall.finnAntall(); j++)
            if (resultatTall.finnHode().element == 0) resultatTall.fjern(resultatTall.finnHode());
    }


    public static void main(String[] args) {
        HeltallKalkulator h = new HeltallKalkulator();
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("Skriv inn en kalkulasjon på måten: \"tall operasjon tall\", der tall er heltall og operasjon er + eller -. Et tomt argument lukker programmet.");
            String argumenter = scan.nextLine();
            if (argumenter.isBlank()) break;
            System.out.println("= " + h.calculate(argumenter));
        }
        System.out.println("Avslutter programmet ...");
        System.exit(0);
    }
}

class Tre {
    TreNode rot;

    public Tre() {
        rot = null;
    }

    public boolean tomt() {
        return rot == null;
    }

    private int finnDybde(TreNode n) {
        int d = -1;
        while (n != null) {
            d++;
            n = n.forelder;
        }
        return d;
    }

    private int finnHøyde(TreNode n) {
        if (n == null) return -1;
        else {
            int vh = finnHøyde(n.venstre);
            int hh = finnHøyde(n.høyre);
            if (vh >= hh) return vh + 1;
            else return hh + 1;
        }
    }

    public int finnHøyde() {
        return finnHøyde(rot);
    }

    public void settInn(String element) {
        TreNode n = rot;
        if (rot == null) {
            rot = new TreNode(element, null, null, null);
            return;
        }

        TreNode f = null; //forelder
        String nElement = null;
        while (n != null) {
            f = n;
            nElement = n.element;
            if (element.compareTo(nElement) < 0) n = n.venstre;
            else n = n.høyre;
        }
        if (element.compareTo(nElement) < 0) f.venstre = new TreNode(element, null, null, f);
        else f.høyre = new TreNode(element, null, null, f);
    }

    public boolean køErNull(LinkedList<TreNode> q) {
        for (TreNode n : q) if (n != null) return false;
        return true;
    }

    public void skrivUtNivåOrden() {
        if (rot == null) return;

        // kø
        LinkedList<TreNode> q = new LinkedList<>();
        q.add(rot);

        // passer på at første rad får nok mellomrom, selv om høyden er kort
        int mellomrom = Math.max(64 , (int)(Math.pow(2, finnHøyde())));

        while (!q.isEmpty()) {
            if (!køErNull(q)) {
                int antallNoder = q.size();

                while (antallNoder > 0) {
                    if (!køErNull(q)) {
                        TreNode node = q.pollFirst();
                        if (node != null) {
                            for (int i = 0; i < mellomrom / 2; i++) System.out.print(" ");
                            System.out.print(node.element);
                            for (int i = 0; i < mellomrom / 2; i++) System.out.print(" ");
                            q.add(node.venstre);
                            q.add(node.høyre);
                        } else {
                            for (int i = 0; i < mellomrom; i++) System.out.print(" ");
                            q.add(null);
                            q.add(null);
                        }
                        antallNoder--;
                    } else antallNoder = 0;
                }
                mellomrom /= 2;
                System.out.println();
            } else q.clear();
        }
    }
}

class TreNode {
    String element;
    TreNode venstre;
    TreNode høyre;
    TreNode forelder;

    public TreNode(String element, TreNode venstre, TreNode høyre, TreNode forelder) {
        this.element = element;
        this.venstre = venstre;
        this.høyre = høyre;
        this.forelder = forelder;
    }
}

class Søketre {
    public static void main(String[] args) {
        while (true){
            Scanner scan = new Scanner(System.in);
            Tre tre = new Tre();
            System.out.println("Skriv inn et vilkårlig antall ord:");
            String argumenter = scan.nextLine();
            if (argumenter.isBlank()) break;
            String[] ordtabell = argumenter.split(" ");
            for (String s : ordtabell) tre.settInn(s);
            tre.skrivUtNivåOrden();
        }
        System.out.println("Avslutter programmet ...");
        System.exit(0);

    }
}





