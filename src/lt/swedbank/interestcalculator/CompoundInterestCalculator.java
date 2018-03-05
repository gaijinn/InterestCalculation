package lt.swedbank.interestcalculator;

import java.util.Arrays;
import java.util.Scanner;

public class CompoundInterestCalculator {

   static double[] intermediateInterest;

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       double amount;
       double interestRate;
       int periodLength;
       String compoundFrequency;

       System.out.print("Amount: ");
       amount = scanner.nextDouble();

       System.out.print("Interest rate: ");
       interestRate = scanner.nextInt();

       System.out.print("Period length (years): ");
       periodLength = scanner.nextInt();

       System.out.print("Compound frequency: ");
       compoundFrequency = scanner.next();

       evaluateCompoundFrequency(compoundFrequency, amount,interestRate,periodLength);

   }

   private static double calculateCompoundInterest(double amount, double rate, int year, int frequency){
       intermediateInterest = new double[frequency*year];
       double intermediateAmount = 0d;
       double temp = 0d;
       for ( int i = 0; i < year*frequency; i++){
           intermediateAmount = (amount * Math.pow((1 + ((rate / 100) / frequency)),(i+1)*frequency)) - amount;
           System.out.printf("Interest amount after year " + (i+1) + ": %.2f\n",  intermediateAmount);

            if (i == 0) {
                intermediateInterest[i] = intermediateAmount;
                temp = intermediateAmount;
            }
            else {
                intermediateInterest[i] = intermediateAmount - temp;
                temp = intermediateAmount;
            }
       }
        String arrayString = Arrays.toString(intermediateInterest);
        System.out.println("Intermediate interest amounts: " + arrayString);
       return intermediateAmount + amount;
   }

   private static void evaluateCompoundFrequency(String choice, double amount, double rate, int year){
       switch (choice){
           case "D":
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 365));
               break;

           case "W":
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 52));
               break;

           case "M":
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 12));
               break;

           case "Q":
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 4));
               break;

           case "H":
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 2));
               break;

           case "Y":
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 1));
               break;

           default:
               System.out.printf("Total amount: %.2f", calculateCompoundInterest(amount,rate,year, 1));
               break;
       }
   }
}