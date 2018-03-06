package lt.swedbank.interestcalculator;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CompoundInterestCalculator {

    private static double[] intermediateInterest;
    private static double[][] intermediateInterestMatrix;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double amount;
        double interestRate;
        int periodLength;
        String compoundFrequency;
        int i = 1;
        double[] interestRates = new double[i];


        do {
            System.out.print("Amount: ");
            if ((amount = scanner.nextDouble()) <= 0)
                System.out.println("Invalid input! Try again!");
        } while (amount <= 0);

        do {
            System.out.print("Interest rate: ");
            interestRate = scanner.nextDouble();
            if (interestRate > 0 && interestRate <= 100) {
                if (interestRate != 0 && i >= 2) {
                    interestRates = Arrays.copyOf(interestRates, i);
                    interestRates[i - 1] = interestRate;
                    i++;
                } else if (interestRate != 0 && i == 1) {
                    interestRates[i - 1] = interestRate;
                    i++;
                }
            } else {
                System.out.println("Invalid input! Try again!");
            }
        } while (interestRate != 0);

        do {
            System.out.print("Period length (years): ");
            try {
                periodLength = scanner.nextInt();
                if (periodLength <= 0) {
                    System.out.println("Invalid input! Try again!");
                }
            } catch (InputMismatchException exc) {
                periodLength = 0;
                System.out.println("Invalid input! Try again!");
                scanner = new Scanner(System.in);
            }

        } while (periodLength <= 0);


        do {
            System.out.print("Compound frequency: ");
            compoundFrequency = scanner.next();
            if (!compoundFrequency.equals("D") && !compoundFrequency.equals("W") && !compoundFrequency.equals("M") && !compoundFrequency.equals("Q") && !compoundFrequency.equals("H") && !compoundFrequency.equals("Y")) {
                System.out.println("Invalid input! Try again!");
            }
        }
        while (!compoundFrequency.equals("D") && !compoundFrequency.equals("W") && !compoundFrequency.equals("M") && !compoundFrequency.equals("Q") && !compoundFrequency.equals("H") && !compoundFrequency.equals("Y"));

        evaluateCompoundFrequency(compoundFrequency, amount, interestRate, periodLength, interestRates);

    }

    private static double calculateCompoundInterest(double amount, double rate, int year, int frequency, int matrix) {
        intermediateInterest = new double[frequency * year];
        double intermediateAmount = 0d;
        double temp = 0d;
        for (int i = 0; i < year * frequency; i++) {
            intermediateAmount = (amount * Math.pow((1 + ((rate / 100) / frequency)), (i + 1) * frequency)) - amount;
            //System.out.printf("Interest amount after year " + (i + 1) + ": %.2f\n", intermediateAmount);

            if (i == 0) {
//                intermediateInterest[i] = intermediateAmount;
                intermediateInterestMatrix[matrix][i] = intermediateAmount;
                temp = intermediateAmount;
            } else {
//                intermediateInterest[i] = intermediateAmount - temp;
                intermediateInterestMatrix[matrix][i] = intermediateAmount - temp;
                temp = intermediateAmount;
            }
        }
//        String arrayString = Arrays.toString(intermediateInterest);
//        System.out.println("Intermediate interest amounts: " + arrayString);
        return intermediateAmount + amount;
    }

    private static void evaluateCompoundFrequency(String choice, double amount, double rate, int year, double[] rates) {
        switch (choice) {
            case "D":
                calculateMultipleInterestRates(amount, rate, year, 365, rates);
                printMatrix(rates.length, year * 365);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 365));
                break;

            case "W":
                calculateMultipleInterestRates(amount, rate, year, 52, rates);
                printMatrix(rates.length, year * 52);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 52));
                break;

            case "M":
                calculateMultipleInterestRates(amount, rate, year, 12, rates);
                printMatrix(rates.length, year * 12);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 12));
                break;

            case "Q":
                calculateMultipleInterestRates(amount, rate, year, 4, rates);
                printMatrix(rates.length, year * 4);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 4));
                break;

            case "H":
                calculateMultipleInterestRates(amount, rate, year, 2, rates);
                printMatrix(rates.length, year * 2);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 2));
                break;

            case "Y":
                calculateMultipleInterestRates(amount, rate, year, 1, rates);
                printMatrix(rates.length, year);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 1));
                break;

            default:
                calculateMultipleInterestRates(amount, rate, year, 1, rates);
                printMatrix(rates.length, year);
                //System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount, rate, year, 1));
                break;
        }
    }

    private static void calculateMultipleInterestRates(double amount, double rate, int year, int frequency, double[] rates) {
        intermediateInterestMatrix = new double[rates.length][frequency * year];
        for (int i = 0; i < rates.length; i++) {
            calculateCompoundInterest(amount, rates[i], year, frequency, i);
        }
    }

    private static void printMatrix(int length, int width) {
        for (int i = 0; i < length; i++) {
            for (int k = 0; k < width; k++) {
                System.out.printf("%.2f ", intermediateInterestMatrix[i][k]);
            }
            System.out.println();
        }
    }
}