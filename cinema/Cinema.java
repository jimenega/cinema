package cinema;

import java.util.Arrays;

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
        populateCinema();
        showSeating();
    }
}