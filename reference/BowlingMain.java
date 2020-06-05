import java.util.Scanner;

class Calculator {
	public int thisIndex = 0;
	public int sum = 0;
	
	/*
	 * calculate Score 
	 */
	public void calcScore(Frame [] frame, int [] accumScore, int frameNum) {
		while (thisIndex <= frameNum) {
			// Optimised by jwlee
			int basic = addFirstSecond(frame[thisIndex]);
			int bonus = 0;

			if (frame[thisIndex].isStrike())
				bonus = strikeBonus(frame, thisIndex, frameNum);
			if (frame[thisIndex].isSpare())
				bonus = spareBonus(frame, thisIndex, frameNum);

			if (bonus == -1)
				return;

			sum += basic + bonus;
			accumScore[thisIndex] = sum;
			thisIndex++;
		}
	}
	
	/*
	 * In case "spare", calculate bonus score
	 */
	private int spareBonus(Frame [] frame, int thisIndex, int frameNum) {
		if (thisIndex < 9 && thisIndex + 1 > frameNum)
			return -1;
		
		return frame[thisIndex + 1].first;
	}
	
	/*
	 * In case "strike", calculate bonus score 
	 */
	private int strikeBonus(Frame [] frame, int thisIndex, int frameNum) {
		if (thisIndex < 9 && thisIndex + 1 > frameNum)
			return -1;
		
		if (!frame[thisIndex + 1].isStrike())
			return addFirstSecond(frame[thisIndex + 1]);
		
		if (thisIndex == 9)
			return 10 + frame[thisIndex + 1].second;
		
		if (thisIndex < 8 && thisIndex + 2 > frameNum)
			return -1;

		return 10 + frame[thisIndex + 2].first;
	}
	
	private int addFirstSecond(Frame frame) {
		return frame.first + frame.second;
	}
}
class Frame {
	public int first;
	public int second;
	
	/*
	 * check strike
	 */
	public boolean isStrike() {
		if (first == 10)
			return true;
		else
			return false;
	}
	
	/*
	 * check spare
	 */
	public boolean isSpare() {
		if (first + second == 10 && first != 10) 
			return true;
		else
			return false;
	}
	
	/*
	 * check open
	 */
	public boolean isOpen() {
		if (first + second < 10)
			return true;
		else
			return false;
	}
}

class Game {
	public Player [] player;
	
	/*
	 * 1) determine player number
	 * 2) input frame score (first point, second point)
	 * 3) calculate score
	 * 4) print scoreboard
	 */
	public void play() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("************ Welcome to < B O W L I N G _ G A M E > ************");
		System.out.print("Input Player Number : ");
		int playerCount = scanner.nextInt();
		
		createPlayer(playerCount);
		
		for ( int frameNum = 0; frameNum < 10; frameNum++) {
			System.out.println("Now is frame " + (frameNum + 1) + ".");
			for ( int playerNum = 0; playerNum < playerCount; playerNum++) {
				System.out.println("-----------------------");
				System.out.println("Player " + (playerNum + 1) + " turn.");
				inputScore(scanner, playerNum, frameNum);
				System.out.println("-----------------------");
				
				player[playerNum].calcScore(frameNum);
				
				Print print = new Print();
				print.basicBoard();
				print.scoreBoard(player, playerNum, frameNum);
			}
			
		}
		
		
		scanner.close();
	}

	/*
	 * input score
	 */
	private void inputScore(Scanner scanner, int playerNum, int frameNum) {
		Frame frame = player[playerNum].frame[frameNum];

		do {
			System.out.print("first score : ");
			int first = scanner.nextInt();
			frame.first = first;
		} while (frame.first > 10);	
			
		if (frame.first != 10) {
			do {
				System.out.print("second score : ");
				int second = scanner.nextInt();
				frame.second = second;
			} while (frame.second > 10);
		}

		// Input three score
		if (frameNum == 9){
			if (frame.isSpare()) {
				System.out.print("third score : ");
				int third = scanner.nextInt();
				player[playerNum].frame[10].first = third;
			}
			else if (frame.isStrike()) {
				System.out.print("second score : ");
				int second = scanner.nextInt();
				player[playerNum].frame[10].first = second;
				
				System.out.print("third score : ");
				int third = scanner.nextInt();
				player[playerNum].frame[10].second = third;
			}
		}
	}

	/*
	 * create players
	 */
	private void createPlayer(int playerCount) {
		player = new Player [playerCount];
		for ( int i = 0; i < playerCount; i++ ) {
			player[i] = new Player();
		}
	}
}
class Player {
	public Frame [] frame = new Frame[11];
	public int [] accumScore = new int[11];
	public Calculator calculator = new Calculator();
	
	/*
	 * make player's frame
	 */
	public Player() {
		for (int i = 0; i < 11; i++) {
			frame[i] = new Frame();
			accumScore[i] = -1;
		}
	}

	/*
	 * calculate player's score
	 */
	public void calcScore(int frameNum) {
		calculator.calcScore(frame, accumScore, frameNum);
	}
	
}
class Print {
	
	/*
	 * print header of board
	 */
	public void basicBoard() {
		System.out.println("=====================================================================");
		System.out.println("|     |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |   10  |");	
	}
	
	/*
	 * print score of board
	 */
	public void scoreBoard(Player [] player, int playerNum, int frameNum) {
		for (int i = 0; i < player.length; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append("|");
			sb.append("  " + (i + 1) + "  ");
			sb.append("|");
			
			for ( int j = 0; j < 9; j++) {
				int first = player[i].frame[j].first;
				int second = player[i].frame[j].second;
				
				if (j < frameNum || (j == frameNum && i <= playerNum) ) {
					sb.append(frameScore(first, second) + "|");
				}
				else {
					sb.append("     |");
				}
			}
			
			String first = " ";
			String second = " ";
			String third = " ";
			if (frameNum == 9 && i <= playerNum) {
				if (player[i].frame[9].isOpen()) {
					first = frameScore(player[i].frame[9].first);
					second = frameScore(player[i].frame[9].second);
				}
	
				if (player[i].frame[9].isSpare()) {
					first = frameScore(player[i].frame[9].first);
					second = frameScore(player[i].frame[9].second);
					third = frameScore(player[i].frame[10].first);
				}
						
				if (player[i].frame[9].isStrike()) {
					first = frameScore(player[i].frame[9].first);
					second = frameScore(player[i].frame[10].first);
					third = frameScore(player[i].frame[10].second);
				}
					
				sb.append(" " + first + " " + second + " " + third + " |");
			}
			else 
				sb.append("       |");
			
			System.out.println("---------------------------------------------------------------------");
			System.out.println(sb.toString());
			
			System.out.print("|     |");
			for ( int f = 0; f < 9; f++ ) {
				if ( player[i].accumScore[f] == -1 )
					System.out.print("     |");
				else
					System.out.printf(" %3d |", player[i].accumScore[f]);
			}
			if ( player[i].accumScore[9] == -1 )
				System.out.print("       |");
			else
				System.out.printf("  %3d  |", player[i].accumScore[9]);
		
			System.out.println();
		}
		System.out.println("=====================================================================");
	}
	
	/*
	 * make frame score to string
	 */
	public String frameScore(int first, int second)
	{
		if ( first == 10 )
			return " " + frameScore(first) + "   ";
		
		if ( first + second == 10 )
			return " " + frameScore(first) + " " + "/" + " ";

		return " " + frameScore(first) + " " + frameScore(second) + " ";
	}

	/*
	 * make frame each score to char
	 */
	public String frameScore(int num) {
		if (num == 0)
			return "-";
		else if (num == 10)
			return "X";
		else
			return Integer.toString(num);
	}
}

public class BowlingMain {

	/*
	 * play bowling
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}

}
