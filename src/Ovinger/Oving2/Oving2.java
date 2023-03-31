package Ovinger.Oving2;

import java.util.Date;

public class Oving2 {

    // x^n
    private static double pow(double x, int n){
        if (n == 0) return 1;
        return (x * pow(x, n-1));
    }

    private static double pow2(double x, int n){
        if (n == 0) return 1;

        if (n % 2 == 0) return pow2(x*x, n/2); //partall
        return x * pow2(x*x, (n-1)/2); //oddetall
    }


    public static void main(String[] args) {

        System.out.println("Sjekker at alle metoder funker. 2^12 skal gi 4096 og 3^14 skal gi 4782969");
        System.out.println("Metode 1: " + pow(2, 12));
        System.out.println("Metode 1: " + pow(3, 14));
        System.out.println("Metode 2: " + pow(2, 12));
        System.out.println("Metode 2: " + pow(3, 14));
        System.out.println("Math.pow() metode: " + pow(2, 12));
        System.out.println("Math.pow() metode: " + pow(3, 14)+ "\n");


        System.out.println("TEST 1: x = 3, n = 14");
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            pow(3, 14);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW: Resultat: " + pow(3,14) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            pow2(3, 14);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW2: Resultat: " + pow2(3,14) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            Math.pow(3, 14);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("MATH.POW: Resultat: " + Math.pow(3,14) + " Millisekund pr. runde: " + tid);


        System.out.println("TEST 2: x = 1.001, n = 500");
        start = new Date();
        runder = 0;
        do {
            pow(1.001, 500);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW: Resultat: " + pow(1.001, 500) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            pow2(1.001, 500);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW2: Resultat: " + pow2(1.001, 500) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            Math.pow(1.001, 500);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("MATH.POW: Resultat: " + Math.pow(1.001, 500) + " Millisekund pr. runde: " + tid);


        System.out.println("TEST 3: x = 1.001, n = 4000");
        start = new Date();
        runder = 0;
        do {
            pow(1.001, 4000);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW: Resultat: " + pow(1.001, 4000) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            pow2(1.001, 4000);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW2: Resultat: " + pow2(1.001, 4000) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            Math.pow(1.001, 4000);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("MATH.POW: Resultat: " + Math.pow(1.001, 4000) + " Millisekund pr. runde: " + tid);



        System.out.println("TEST 4: x = 1.001, n = 5000");
        start = new Date();
        runder = 0;
        do {
            pow(1.001, 5000);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW: Resultat: " + pow(1.001, 5000) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            pow2(1.001, 5000);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("POW2: Resultat: " + pow2(1.001, 5000) + " Millisekund pr. runde: " + tid);

        start = new Date();
        runder = 0;
        do {
            Math.pow(1.001, 5000);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("MATH.POW: Resultat: " + Math.pow(1.001, 5000) + " Millisekund pr. runde: " + tid);



    }
}
