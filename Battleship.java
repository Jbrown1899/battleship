import java.util.*;
import java.io.*;

public class Battleship {
	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //1)
        System.out.println("Welcome to Battleship!\n\n");
        //get 5 inputs from user to place in a 5x5 array
        char[][] player1c = new char[5][5];
        char[][] player2c = new char[5][5];
        char[][] player1score = new char[5][5];
        char[][] player2score = new char[5][5];
        for (int row = 0; row < 5; row++) { // initialize the char arrays to '-'
            for (int column = 0; column < 5; column++){
                player1c[row][column] = '-';
                player2c[row][column] = '-';
                player1score[row][column] = '-';
                player2score[row][column] = '-';
                
            }
        }

        //gets coordinates for initial ship placement for player 1 and player 2
        for(int player = 1; player <= 2; player++){
            System.out.println("PLAYER " + player +", ENTER YOUR SHIPS' COORDINATES.");
            for (int ship = 1; ship <= player1c.length; ship++) {
                if (player == 1){
                    int[] array = coordinates(input, ship, player1c);
                    player1c[array[0]][array[1]] = '@';// assign values for ship locations
                }
                else{
                    int[] array = coordinates(input, ship, player2c);
                    player2c[array[0]][array[1]] = '@';
                }
            }
            char[][] print = player == 1 ? player1c: player2c; //ternary operator. If player is 1 print player1c array else print player2c array
            printBattleShip(print);

            for (int space = 0; space < 100; space++) {
                System.out.println("\n");
            }
        }

        //Attack coordinates checked vs enemy ship placement
        int score1 = 0;
        int score2 = 0;
        int i = 2;
        do {
            if (i % 2 == 0) { //mod allows us to alternate turns
                int[] array = attack(input, player2c, player1score, player2score, 1);
                if (player2c[array[0]][array[1]] == '@') {
                    System.out.println("PLAYER 1 HIT PLAYER 2's SHIP!");
                    player1score[array[0]][array[1]] = 'X';
                    player2c[array[0]][array[1]] = 'X';
                    score1 = score1 + 1;
                }
                else {
                    System.out.println("PLAYER 1 MISSED!");
                    player1score[array[0]][array[1]] = 'O';
                    player2c[array[0]][array[1]] = 'O';

                }
                i = i + 1;
                printBattleShip(player1score);
                System.out.println("");
            }
            else {
                int[] array = attack(input, player1c, player2score, player1score, 2);
                if (player1c[array[0]][array[1]] == '@') {
                    System.out.println("PLAYER 2 HIT PLAYER 1's SHIP!");
                    player2score[array[0]][array[1]] = 'X';
                    player1c[array[0]][array[1]] = 'X';
                    score2 = score2 + 1;
                }
                else {
                    System.out.println("PLAYER 2 MISSED!");
                    player2score[array[0]][array[1]] = 'O';
                    player1c[array[0]][array[1]] = 'O';
                }
                i = i + 1;
                printBattleShip(player2score);
                System.out.println("");
            }
        }
        while (score1 < 5 && score2 < 5);
        if (score1 > score2) {
            System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!\n");
        }
        else if (score2 > score1) {
            System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!\n");
        }

        System.out.println("Final boards:\n");
        printBattleShip(player1c);
        System.out.println("");
        printBattleShip(player2c);
    }
    

    private static int[] attack(Scanner input, char[][] opponent, char[][] attackerScore, char[][] opponentScore, int attackerNum) {
        int[] array = new int[2];
        boolean complete = false;

        //Attack coordinates attained
        do {
            System.out.println("Player " + attackerNum + ", enter hit row/column: ");
            if(input.hasNextInt() != true) {
                System.out.println("Invalid coordinates. Choose different coordinates.");
            }
            else {
                int row = input.nextInt();
                array[0] = row;
                if(input.hasNextInt() != true) {
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                }
                else {
                    int column = input.nextInt();
                    array[1] = column;
                    if( 0 <= column && column <= 4 && 0 <= row && row <= 4) { //tests to see if the coordinates are in bounds
                        if(attackerScore[array[0]][array[1]] != 'X' && attackerScore[array[0]][array[1]] != 'O' ) {
                            complete = true;
                        }
                        else {
                            System.out.println("You already fired on this spot. Choose different coordinates.");
                        }
                    }
                    else{
                        System.out.println("Invalid coordinates. Choose different coordinates.");
                    }
                }
            }
            input.nextLine(); // consumes the scanner allowing us to enter a new value and avoid infinite loop
        }
        while (complete == false);

        return array;
    }
    
    //Use this method to get initial coordinates
    private static int[] coordinates(Scanner input, int ship, char[][] player) { 
        int[] array = new int[2];
        boolean complete = false;

        do {
            System.out.println("Enter ship " + ship + " location: ");
            if(input.hasNextInt() != true) {
                System.out.println("Invalid coordinates. Choose different coordinates.");
            }
            else {
                int row = input.nextInt();
                array[0] = row;
                if(input.hasNextInt() != true) {
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                }
                else {
                    int column = input.nextInt();
                    array[1] = column;
                    if( 0 <= column && column <= 4 && 0 <= row && row <= 4){ //tests to see if the coordinates are in bounds
                        if (player[array[0]][array[1]] != '@') {
                            complete = true;
                        }
                        else{
                            System.out.println("You already have a ship there. Choose different coordinates.");
                        }
                    }
                    else{
                        System.out.println("Invalid coordinates. Choose different coordinates.");
                    }
                }
            }
            input.nextLine(); // consumes the scanner allowing us to enter a new value and avoid infinite loop
        }
        while (complete == false);
        
        return array;
    }

    // Use this method to print game boards to the console.
	private static void printBattleShip(char[][] player) {
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					System.out.print(player[row][column] + " ");
				}
			}
			System.out.println("");
		}
	}
}

