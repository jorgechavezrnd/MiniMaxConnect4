package ai.minimax;

import java.util.Scanner;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		Connect4 game = new Connect4();
		scanner = new Scanner(System.in);
		
		while (true) {
			System.out.print("Human, your movement: ");
			int moveHuman = scanner.nextInt();
			game.insertChip(moveHuman - 1, -1, game.getBoard());
			
			game.showBoard();
			
			if (game.wonPlayer(-1, game.getBoard())) {
				System.out.println("Human won");
				break;
			}
			
			long startTime = System.nanoTime();
			
			int[] tuple = game.minimax(0, 7, game.getBoard(), -1, 1, 1);
			
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			System.out.println("Duracion: " + (duration / 1000000) + " milisegundos");
			
			System.out.println("Computer: " + (tuple[0] + 1) + ", value: " + tuple[1]);
			game.insertChip(tuple[0], 1, game.getBoard());
			
			game.showBoard();
			
			if (game.wonPlayer(1, game.getBoard())) {
				System.out.println("Coputer won");
				break;
			}
		}
	}

}
