package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    static char [][] cinema = new char [7][8];

    public static void populateCinema() {
        for (int i = 0; i < cinema.length; i++) {
            for ( int j = 0; j < cinema.length + 1; j++)
                cinema[i][j] = 'S';
        }
    }

    public static String formatString(int row) {
        return Arrays.toString(cinema[row])
                .replace(",", "")  //remove commas
                .replace("[", "")  //remove right bracket
                .replace("]", "")  //remove left bracket
                .trim(); //remove trailing spaces
    }

    public static void showSeating() {
        int cinemaRow = 0;
        //String columnHeading = "  1 2 3 4 5 6 7 8";
        System.out.println("Cinema:");
        System.out.println("  1 2 3 4 5 6 7 8"); //column heading
        for (int rowHeading = 1; rowHeading <= 7; rowHeading++ )
            System.out.printf("%d %s%n", rowHeading, formatString(cinemaRow));
    }

    public static void main(String[] args) {
        //populateCinema();
        //showSeating();
        //ScreenRooms.getFloorPlan();
        ScreenRooms SR1 = new ScreenRooms();
        //SR1.getFloorPlan();
        SR1.calculateProfit();
    }
}

 class ScreenRooms {
    static final int BASIC_PRICE = 10;
    static final int BUDGET_PRICE = 8;
    static int rows;
    static int seats;
    static boolean largeRoom = false;
    static int totalSeats;
    static int profit;

     public void calculateProfit() {
         getFloorPlan();
         getProfit();
     }

    public void setRoomSize() {
        totalSeats = rows * seats;
        if (totalSeats > 60) largeRoom = true;
    }

     public boolean isNumRowsOdd() {
        return rows % 2 != 0;
     }

     public void getProfit() {
        int pricyRowCount = 0, budgetRowCount = 0;
        int pricyTicketPrice = 0, budgetTicketPrice = 0;
        //boolean numRowsOdd = isNumRowsOdd();
        if (!largeRoom) { profit = rows * seats * BASIC_PRICE;} //not large room
        if (largeRoom && isNumRowsOdd()) {
            pricyRowCount = Math.abs(rows/2);
            budgetRowCount = Math.abs((rows/2) + 1);
            pricyTicketPrice = BASIC_PRICE;
            budgetTicketPrice = BUDGET_PRICE;
            profit = (pricyTicketPrice * pricyRowCount * seats) +(budgetTicketPrice * budgetRowCount * seats);
        }
        if (largeRoom && !isNumRowsOdd()) {
            pricyRowCount = rows/2;
            budgetRowCount = rows/2;
            pricyTicketPrice = BASIC_PRICE;
            budgetTicketPrice = BUDGET_PRICE;
            //profit = pricyTicketPrice + budgetTicketPrice;
            profit = (pricyTicketPrice * pricyRowCount * seats) +(budgetTicketPrice * budgetRowCount * seats);
        }
        System.out.println("Total income:");
        System.out.printf("$%d",profit);
     }

     public void getFloorPlan() {
         Scanner scanner = new Scanner(System.in);
         System.out.println("Enter the number of rows:");
         rows = scanner.nextInt();
         System.out.println("Enter the number of seats in each rows:");
         seats = scanner.nextInt();
         setRoomSize();
         //System.out.printf("%d %d %d %b", rows, seats, totalSeats, largeRoom);
         System.out.printf("totalseats: %d  largeroom: %b", totalSeats, largeRoom);
         System.out.println();
     }
}