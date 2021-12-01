import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class scoreboard {
	String file = "src/topScores.txt";
	int amountOfTopScores = 5;
	BufferedWriter writer;
	BufferedReader reader;
	int[] topFiveScores;

	scoreboard() {
		try {
			writer = new BufferedWriter(new FileWriter(file));
			reader = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
		}
	}

	public int[] getTopFive() {
		topFiveScores = new int[amountOfTopScores];
		String line = null;
		for (int i = 0; i < amountOfTopScores; i++) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("the score is " + line);
			if (line != null) {
				topFiveScores[i] = Integer.parseInt(line);
			}
		}
		return topFiveScores;
	}

	public boolean addScore(int score) {
		int[] scores = getTopFive();
		System.out.println("top five are: " + scores[0] + " " + scores[1]);
		boolean scoreAdded = true;
		int smallestScore = 100000;
		int scoreNumber = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] < smallestScore) {
				smallestScore = scores[i];
				scoreNumber = i;
			}
		}
		if (smallestScore < score) {
			scoreAdded = true;
			scores[scoreNumber] = score;
			writeScores(scores);
		}
		return scoreAdded;
	}

	private void writeScores(int[] scores) {
		try {
			writer.flush();
			for (int i = 0; i < scores.length; i++) {
				writer.write(scores[i]);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
