package Ovinger.Oving1;

import java.util.Random;

public class Oving1 {

    // example from page 19 in the book
    private final static int[][] example = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9}, //days
            {-1, 3, -9, 2, 2, -1, 2, -1, -5} //price change
    };

    // Creates random arrays for testing
    private static int[][] createRandomExample(int days){
        Random rng = new Random();

        int[][] array = new int[2][days];
        for (int i = 0; i < days; i++){
            array[0][i] = i+1;
            array[1][i] = rng.nextInt(10 + 10) - 10; //random number from -10 to 10
        }
        return array;
    }


    // Assuming that the list will have two rows like the book example on page 19
    // Row 1 will have the date, row 2 will have an int representing price differences
    private static String findBuyAndSellDate(int[][] priceDifferences) {
        // variables will change
        int biggestPositiveDifference = -1;
        int buyDate = -1;
        int sellDate = -1;

        for (int i = 0; i < priceDifferences[1].length; i++) {
            int sum = 0;

            for (int j = 1 + i; j < priceDifferences[1].length; j++) {
                if ((priceDifferences[1][j] + sum - priceDifferences[1][i]) > biggestPositiveDifference) {
                    biggestPositiveDifference = (priceDifferences[1][j] + sum - priceDifferences[1][i]);
                    buyDate = priceDifferences[0][i];
                    sellDate = priceDifferences[0][j];
                }
                sum += priceDifferences[1][j];
            }
        }
        return " Buy date: " + buyDate + ", sell date: " + sellDate;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(findBuyAndSellDate(example)); // should return buy date 3 and sell date 7
        long end = System.currentTimeMillis();
        System.out.println("9 day example took " + (end - start) + "ms.");

        int[][] example2 = createRandomExample(1000);
        start = System.currentTimeMillis();
        System.out.println(findBuyAndSellDate(example2));
        end = System.currentTimeMillis();
        System.out.println("1000 day example took " + (end - start) + "ms.");

        int[][] example3 = createRandomExample(10000);
        start = System.currentTimeMillis();
        System.out.println(findBuyAndSellDate(example3));
        end = System.currentTimeMillis();
        System.out.println("10 000 day example took " + (end - start) + "ms.");

        int[][] example4 = createRandomExample(100000);
        start = System.currentTimeMillis();
        System.out.println(findBuyAndSellDate(example4));
        end = System.currentTimeMillis();
        System.out.println("100 000 day example took " + (end - start) + "ms.");

        int[][] example5 = createRandomExample(1000000);
        start = System.currentTimeMillis();
        System.out.println(findBuyAndSellDate(example5));
        end = System.currentTimeMillis();
        System.out.println("1 000 000 day example took " + (end - start) + "ms.");
    }
}
