import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class scoreboard {
	String file = "topScores.txt";
	int amountOfTopScores = 5;
	;
	int[] topFiveScores;

	scoreboard() {
		File topScores = new File(file);
		if(topScores.exists()) {
		}
	}

	public int[] getTopFive() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		topFiveScores = new int[amountOfTopScores];
		String line = null;
		for (int i = 0; i < amountOfTopScores; i++) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line != null) {
				topFiveScores[i] = Integer.parseInt(line);
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
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
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			for (int i = 0; i < scores.length; i++) {
				writer.write(scores[i]+"");
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
