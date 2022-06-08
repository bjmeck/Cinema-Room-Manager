package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        char[][] seatingChart = getSeatingChart();
        int menuChoice;
        do {
            menuChoice = Menu();
            switch (menuChoice) {
                case 1:
                    printSeatingChart(seatingChart);
                    break;
                case 2:
                    getSeat(seatingChart);
                    break;
                case 3:
                    getStatistics(seatingChart);
                    break;
                default:
                    break;
            }
        } while (menuChoice != 0);
    }

    static char[][] getSeatingChart() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        char[][] seatingChart = new char[rows][seats];
        for (int i = 0; i < seatingChart.length; i++) {
            for (int j = 0; j < seatingChart[i].length; j++) {
                seatingChart[i][j] = 'S';
            }
        }
        return seatingChart;
    }

    public static int Menu() {
        Scanner scanner = new Scanner(System.in);
        boolean wrongInput = false;
        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                default:
                    wrongInput = false;
            }
        } while (!wrongInput);
        return 0;
    }

    static void printSeatingChart(char[][] seatingChart) {
        System.out.println();
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= seatingChart[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < seatingChart.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seatingChart[i].length; j++) {
                System.out.print(seatingChart[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void getSeat(char[][] seatingChart) {
        Scanner scanner = new Scanner(System.in);
        boolean seatTaken = false;
        do {
            System.out.println("Enter a row number:");
            int rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatNumber = scanner.nextInt();
            if (rowNumber > seatingChart.length || rowNumber < 0 ||
                    seatNumber > seatingChart[0].length || seatNumber < 0) {
                System.out.println("Wrong input!");
            } else if (seatingChart[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
                seatTaken = true;
            } else {
                seatingChart[rowNumber - 1][seatNumber - 1] = 'B';
                getTicketPrice(seatingChart, rowNumber);
                seatTaken = false;
            }
        } while (seatTaken);
        printSeatingChart(seatingChart);
    }

    static void getTicketPrice(char[][] seatingChart, int rowNumber) {
        int totalSeats = seatingChart.length * seatingChart[0].length; // all rows have same number of seats
        int fullTicketPrice = 10;
        int discountedTicketPrice = 8;
        int userTicketPrice;
        int frontHalf = (seatingChart.length / 2);
        if (totalSeats <= 60 || rowNumber <= frontHalf) {
             userTicketPrice = fullTicketPrice;
        } else {
            userTicketPrice = discountedTicketPrice;
        }
        System.out.println();
        System.out.println("Ticket Price: " + "$" + userTicketPrice);
        System.out.println();
    }

    static void getStatistics(char[][] seatingChart) {
        int totalSeats = seatingChart.length * seatingChart[0].length; // all rows have same number of seats
        int fullTicketPrice = 10;
        int discountedTicketPrice = 8;
        int frontHalf = (seatingChart.length / 2);
        int backHalf = (seatingChart.length / 2) + (seatingChart.length % 2);
        int currentIncome = 0;
        int totalIncome = 0;
        int frontHalfTicketsPurchased = 0;
        int backHalfTicketsPurchased = 0;
        int ticketsPurchased = 0;
        for (int i = 0; i < seatingChart.length; i++) {
            for (int j = 0; j < seatingChart[i].length; j++) {
                if (seatingChart[i][j] == 'B' && i < frontHalf) {
                    frontHalfTicketsPurchased++;
                }
                if (seatingChart[i][j] == 'B' && i >= frontHalf) {
                    backHalfTicketsPurchased++;
                }
            }
        }
        ticketsPurchased = frontHalfTicketsPurchased + backHalfTicketsPurchased;
        if (totalSeats <= 60) {
            currentIncome = ticketsPurchased * fullTicketPrice;
            totalIncome = fullTicketPrice * totalSeats;
        } else {
            currentIncome = (frontHalfTicketsPurchased * fullTicketPrice) + (backHalfTicketsPurchased * discountedTicketPrice);
            totalIncome = (frontHalf * seatingChart[0].length * fullTicketPrice) +
                    (backHalf * seatingChart[0].length * discountedTicketPrice);
        }
        double percentSold = (double) ticketsPurchased / totalSeats * 100;
        System.out.println("Number of purchased tickets: " + ticketsPurchased);
        System.out.println("Percentage: " + String.format("%.2f", percentSold) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}
