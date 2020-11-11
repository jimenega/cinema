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
        //System.out.println(Arrays.deepToString(cinema));
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
        System.out.printf("%n%s%n", "Cinema:");
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
        screenRoom1.sellTicket();
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
    static int soldTicketPrice;

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
    int priceyRowCount = Math.abs(rows/2);
    int budgetRowCountOdd = Math.abs((rows/2) + 1);
    int budgetRowCountEven = Math.abs(rows/2);

    if (!largeRoom) { profit = rows * seats * BASIC_PRICE;} //not large room
    if (largeRoom && isNumRowsOdd()) {
        profit = (BASIC_PRICE * priceyRowCount * seats)
        + (BUDGET_PRICE * budgetRowCountOdd * seats);
    }
    if (largeRoom && !isNumRowsOdd()) {
        profit = (BASIC_PRICE * priceyRowCount * seats)
        + (BUDGET_PRICE * budgetRowCountEven * seats);
    }
    //System.out.println("Total income:");
    //System.out.printf("$%d%n%n",profit);
    }

    public void getFloorPlan() {
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter the number of rows:");
        System.out.printf("%s%n", "Enter the number of rows:");
        rows = scanner.nextInt();
        //System.out.println("Enter the number of seats in each rows:");
        System.out.printf("%s%n", "Enter the number of seats in each rows:");
        seats = scanner.nextInt();
        setRoomSize();
    }

     public void sellTicket() {
         Scanner scanner = new Scanner(System.in);
         System.out.printf("%n%s%n", "Enter a row number:");
         int soldRow = scanner.nextInt();
         System.out.printf("%s%n", "Enter a seat number in that row:");
         int soldSeat = scanner.nextInt();
         //int soldTicketPrice = getTicketPrice(soldRow);
         getTicketPrice(soldRow);
         tagSoldSeat(soldRow, soldSeat);
         System.out.printf("%n%s%d%n", "Ticket price: ", soldTicketPrice);
         //System.out.println();
     }

     public void getTicketPrice(int soldRow) {
         //setRoomSize();
         int budgetRowCountOdd = Math.abs((rows/2) + 1);
         int budgetRowCountEven = Math.abs(rows/2);
         //System.out.printf("largeroom:%b  isNumRowsOdd:%b  ", largeRoom, isNumRowsOdd());
         //System.out.printf("budgetRowCountOdd:%d  budgetRowCountEven:%d",budgetRowCountOdd, budgetRowCountEven);
         //int soldTicketPrice = 0;
        //System.out.println(soldRow);
        //int priceyRowCount = Math.abs(rows/2);

        if (!largeRoom) { soldTicketPrice = BASIC_PRICE; }

        if (largeRoom && isNumRowsOdd() && (soldRow < budgetRowCountOdd)) { soldTicketPrice = BASIC_PRICE; }

        if (largeRoom && isNumRowsOdd() && (soldRow >= budgetRowCountOdd)) { soldTicketPrice = BUDGET_PRICE; }

        if (largeRoom && !isNumRowsOdd() && (soldRow <= budgetRowCountEven)) { soldTicketPrice = BASIC_PRICE; }

        if (largeRoom && !isNumRowsOdd() && (soldRow > budgetRowCountEven)) { soldTicketPrice = BUDGET_PRICE; }
    }

     public void tagSoldSeat(int soldRow, int soldSeat) {
         Cinema.cinema[soldRow - 1][soldSeat - 1] = 'B';
     }
}