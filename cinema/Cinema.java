package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    static char [][] cinema;

    public static void populateCinema() {
        cinema = new char[ScreenRooms.rows][ScreenRooms.seats];
        for (int i = 0; i < ScreenRooms.rows; i++) {
            for ( int j = 0; j < ScreenRooms.seats; j++) {
                cinema[i][j] = 'S';
            }
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
        System.out.printf("%n%s%n", "Cinema:");
        System.out.printf("%s", "  ");

        for (int columnHeading = 1; columnHeading <= ScreenRooms.seats; columnHeading++ ) {
            System.out.printf("%d ", columnHeading);
        }
        System.out.println();

        for (int rowHeading = 1; rowHeading <= ScreenRooms.rows; rowHeading++ ) {
            System.out.printf("%d %s%n", rowHeading, formatString(cinemaRow));
            cinemaRow++;
        }
    }

    public static void main(String[] args) {
        ScreenRooms screenRoom1 = new ScreenRooms();
        screenRoom1.getFloorPlan();
        populateCinema();
        screenRoom1.getProfit();
        screenRoom1.runMenu();
    }
}

 class ScreenRooms {
    static final int BASIC_PRICE = 10;
    static final int BUDGET_PRICE = 8;
    static int rows;
    static int seats;
    static int totalSeats;
    static boolean largeRoom = false;
    static int profit;
    static int soldTicketPrice;
    static int soldTicketCount;
    static int income;

    public int showMenu() {
        Scanner scanner = new Scanner(System.in);
        int m1 = 1, m2 = 2, m3 = 3, m0 = 0;
        System.out.printf("%n%d%c%s%n",m1,'.'," Show the seats");
        System.out.printf("%d%c%s%n",m2,'.'," Buy a ticket");
        System.out.printf("%d%c%s%n",m3,'.'," Statistics");
        System.out.printf("%d%c%s%n",m0,'.'," Exit");
        return scanner.nextInt();
    }

    public void runMenu() {
        boolean openCinema = true;
        int menuItem;
        do {
            menuItem = showMenu();
            switch (menuItem) {
                case 1:
                    Cinema.showSeating();
                    break;
                case 2:
                    sellTicket();
                    break;
                case 3:
                    showStats();
                    break;
                case 0:
                    openCinema = false;
                    break;
            }
        } while (openCinema);
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
    }

    public void getFloorPlan() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s%n", "Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.printf("%s%n", "Enter the number of seats in each rows:");
        seats = scanner.nextInt();
        setRoomSize();
        totalSeats = rows * seats;
    }

     public void sellTicket() {
        boolean ticketSaleCompleted = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.printf("%n%s%n", "Enter a row number:");
            int soldRow = scanner.nextInt();
            System.out.printf("%s%n", "Enter a seat number in that row:");
            int soldSeat = scanner.nextInt();
            if (isSeatInScope(soldRow, soldSeat)) {
                if (isSeatEmpty(soldRow, soldSeat)) {
                    getTicketPrice(soldRow);
                    tagSoldSeat(soldRow, soldSeat);
                    income += soldTicketPrice;
                    soldTicketCount++;
                    System.out.printf("%n%s%c%d%n", "Ticket price: ", '$', soldTicketPrice);
                    ticketSaleCompleted = true;
                } else {
                    System.out.printf("%n%s%n", "That ticket has already been purchased!");
                }
            } else {
                System.out.printf("%n%s%n", "Wrong input!");
            }
        } while (!ticketSaleCompleted);
     }

     public void getTicketPrice(int soldRow) {
        int budgetRowCountOdd = Math.abs((rows/2) + 1);
        int budgetRowCountEven = Math.abs(rows/2);

        if (!largeRoom) { soldTicketPrice = BASIC_PRICE; }

        if (largeRoom && isNumRowsOdd() && (soldRow < budgetRowCountOdd)) { soldTicketPrice = BASIC_PRICE; }

        if (largeRoom && isNumRowsOdd() && (soldRow >= budgetRowCountOdd)) { soldTicketPrice = BUDGET_PRICE; }

        if (largeRoom && !isNumRowsOdd() && (soldRow <= budgetRowCountEven)) { soldTicketPrice = BASIC_PRICE; }

        if (largeRoom && !isNumRowsOdd() && (soldRow > budgetRowCountEven)) { soldTicketPrice = BUDGET_PRICE; }
    }

     public void tagSoldSeat(int soldRow, int soldSeat) {
        Cinema.cinema[soldRow - 1][soldSeat - 1] = 'B';
     }

     public boolean isSeatEmpty(int soldRow, int soldSeat) {
        return Cinema.cinema[soldRow - 1][soldSeat - 1] == 'S';
     }

     public boolean isSeatInScope(int soldRow, int soldSeat) {
         return soldRow <= rows && soldSeat <= seats;
     }

     public void showStats() {
        double percentSeatsSold = ((double)soldTicketCount/totalSeats) * 100;
        System.out.printf("%n%s%d%n", "Number of purchased tickets: ", soldTicketCount);
        System.out.printf("%s%2.2f%c%n", "Percentage: ", percentSeatsSold, '%');
        System.out.printf("%s%c%d%n", "Current income: ", '$', income);
        System.out.printf("%s%c%d%n", "Total income: ", '$', profit);
     }
}