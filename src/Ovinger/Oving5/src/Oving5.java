import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Oving5 {
    public static void main(String[] args) throws IOException {
        File textFile = new File("navn.txt");

        BufferedReader file = new BufferedReader(new FileReader(textFile));
        String line;

        int lines = (int)file.lines().count();
        int nearest2Power = 1;
        while (nearest2Power < lines) nearest2Power = nearest2Power << 1;

        HashTable h = new HashTable(nearest2Power);

        file = new BufferedReader(new FileReader(textFile));
        while((line = file.readLine()) != null) {
            h.insert(line);
        }
        file.close();
        System.out.println("OPPGAVE 1");
        System.out.println("------");
        System.out.println("Testing lookup function on Sondre Malerud. Return value: " + h.lookUp("Sondre Malerud"));
        System.out.println("Total collisions: " + HashTable.collisionCount);
        System.out.println("Load factor: " + (double) lines / nearest2Power);
        System.out.println("Average collisions per person: " + (double) HashTable.collisionCount / lines);


        int size = 13000027;
        int aSize = 10000000;
        int a[] = new int[aSize];
        long start;
        long time = 0;

        for (int i = 0; i < aSize; i++) a[i] = (int) (Math.random() * aSize * 100);
        HashTable2 h2 = new HashTable2(size);

        for (int j = 0; j < a.length; j++){
            start = System.nanoTime();
            h2.insert(a[j]);
            time += System.nanoTime() - start;
        }

        long hashmapStart;
        long hashmapTime = 0;

        HashMap<Integer, Integer> hashmap = new HashMap<>();

        for (int k = 0; k < aSize; k++){
            hashmapStart = System.nanoTime();
            hashmap.put(a[k], a[k]);
            hashmapTime += System.nanoTime() - hashmapStart;
        }

        System.out.println("------");
        System.out.println("------");
        System.out.println("------");
        System.out.println("OPPGAVE 2");
        System.out.println("------");

        System.out.println("Total collisions: " + HashTable2.collisionCount);
        System.out.println("Load factor: " + (double) aSize / size);
        System.out.println("Total insert time into my hashtable: " + time/1000000 + "ms");
        System.out.println("Total insert time into java's HashMap: " + hashmapTime/1000000 + "ms");
    }
}

class HashTable {
    static LinkedList[] array;
    static int A = 1327217885;
    static int collisionCount = 0;
    static int amountOfPositions = 0;

    public HashTable(int amountOfPositions){
        array = new LinkedList[amountOfPositions];
        collisionCount = 0;
        HashTable.amountOfPositions = amountOfPositions;
    }

    public void insert(String s) {
        if (array[hashFunction(s)] != null) {
            collisionCount++;
            System.out.println("Insert collision between " + array[hashFunction(s)].getFirst() + " and insert string: " + s);
            ListIterator iterator = array[hashFunction(s)].listIterator();
            while (!iterator.hasNext()) {
                if (iterator.next().equals(s)) return; // the name already exists
                collisionCount++;
                System.out.println("Insert collision between " + iterator.next() + " and insert string: " + s);
            }
            array[hashFunction(s)].addFirst(s);
        }
        else {
            LinkedList<String> list = new LinkedList<>();
            list.addFirst(s);
            array[(hashFunction(s))] = list;
        }
    }

    public String lookUp(String s) {
        String currentString;

        if (array[hashFunction(s)] == null) return null;
        if (array[hashFunction(s)].getFirst().equals(s)) return (String) array[hashFunction(s)].getFirst();

        ListIterator iterator = array[hashFunction(s)].listIterator();

        while (iterator.hasNext()) {
            currentString = (String) iterator.next();
            if (currentString.equals(s)) return currentString;
            collisionCount++;
            System.out.println("Lookup collision between " + currentString + " and lookup string: " + s);
        }
        return null;
    }

    public int hashFunction(String s) {
        int number = 0;
        for (int i = 0; i < s.length(); i++) {
            number += ((int)s.charAt(i) * i+1);
        }
        //return divHash(number, amountOfPositions);
        return multHash(number);
    }
    public int multHash(int k) {
        int x = Integer.bitCount(k);
        return (k*A>>>(31-x)) & (amountOfPositions -1);
    }

    public int divHash(int k, int m) {
        if (m == 0) return 0;
        return k % m;

    }

}

class HashTable2 {

    static int[] array;
    static int collisionCount = 0;
    static int amountOfPositions = 1;

    public HashTable2(int amountOfPositions){
        array = new int[amountOfPositions];
        collisionCount = 0;
        HashTable2.amountOfPositions = amountOfPositions;
    }

    public void insert(int i) {
        int h1 = h1(i);

        if (array[h1] == 0) {
            array[h1] = h1;
            return;
        }

        int h2 = h2(i);
        int j = 1;
        collisionCount++;

        while (array[probe(h1, h2, j)] != 0) {
            collisionCount++;
            j++;
        }
        array[probe(h1, h2, j)] = i;
    }

    public int lookUp(int i) {
        int h1 = h1(i);

        if (array[h1] == i) return array[h1];

        int h2 = h2(i);
        int j = 1;
        collisionCount++;

        while (array[probe(h1, h2, j)] != i) {
            collisionCount++;
            j++;
        }
        return array[probe(h1, h2, j)];
    }

    public int h1(int k){
        return k % amountOfPositions;
    }

    public int h2(int k) {
        return k % (amountOfPositions - 1) + 1;
    }

    public int probe(int h1, int h2, int i) {
        return (h1 + i*h2) % amountOfPositions;
    }
}
