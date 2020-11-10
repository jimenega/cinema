package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    //static char [][] cinema = new char [7][8];
    static char [][] cinema;

    public static void populateCinema() {
        cinema = new char[ScreenRooms.rows][ScreenRooms.seats];
        for (int i = 0; i < ScreenRooms.rows; i++) {
            for ( int j = 0; j < ScreenRooms.seats; j++) {
                cinema[i][j] = 'S';
            }
        }
        System.out.println(Arrays.deepToString(cinema));
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
        //System.out.println("  1 2 3 4 5 6 7 8"); //column heading
        System.out.printf("%s", "  ");
        for (int columnHeading = 1; columnHeading <= ScreenRooms.seats; columnHeading++ ) {
            System.out.printf("%d ", columnHeading);
            //columnHeading++;
        }
        System.out.println();
        for (int rowHeading = 1; rowHeading <= ScreenRooms.rows; rowHeading++ ) {
            System.out.printf("%d %s%n", rowHeading, formatString(cinemaRow));
            cinemaRow++;
        }
    }

    public static void main(String[] args) {

        ScreenRooms screenRoom1 = new ScreenRooms();
        screenRoom1.calculateProfit();
        populateCinema();
        showSeating();
    }
}

 class ScreenRooms {
    static final int BASIC_PRICE = 10;
    static final int BUDGET_PRICE = 8;
    static int rows;
    static int seats;
    static boolean largeRoom = false;
    static int profit;

     public void calculateProfit() {
        getFloorPlan();
        getProfit();
     }

    public void setRoomSize() {
        if ((rows * seats) > 60) largeRoom = true;
    }

     public boolean isNumRowsOdd() {
         return rows % 2 != 0;
     }

     public void getProfit() {
        int pricyRowCount = Math.abs(rows/2);
        int budgetRowCountOdd = Math.abs((rows/2) + 1);
        int budgetRowCountEven = Math.abs(rows/2);

        if (!largeRoom) { profit = rows * seats * BASIC_PRICE;} //not large room
        if (largeRoom && isNumRowsOdd()) {
            profit = (BASIC_PRICE * pricyRowCount * seats)
            + (BUDGET_PRICE * budgetRowCountOdd * seats);
        }
        if (largeRoom && !isNumRowsOdd()) {
            profit = (BASIC_PRICE * pricyRowCount * seats)
            + (BUDGET_PRICE * budgetRowCountEven * seats);
        }
        System.out.println("Total income:");
        System.out.printf("$%d%n%n",profit);
     }

     public void getFloorPlan() {
         Scanner scanner = new Scanner(System.in);
         System.out.println("Enter the number of rows:");
         rows = scanner.nextInt();
         System.out.println("Enter the number of seats in each rows:");
         seats = scanner.nextInt();
         setRoomSize();
     }
}