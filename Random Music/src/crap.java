import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class crap {
	static List<Note> music = new ArrayList<>();
	static double tempo = 1600.00;
	static int measureNumber = 32;
	static int beatsPerMeasure = 6;
	static double beatNote = 8.00;
	static int key = 1;
	static List<Double> chromatic = new ArrayList<>();

	public static void main(String[] args) {
		for (int i = 0; i < 12; i++) {
			double exp = ((double) i) / 12.00;
			double f = 440.00 * Math.pow(2d, exp);
			chromatic.add(i, f);
		}
		createMusic(tempo, measureNumber, beatsPerMeasure, beatNote);
		try {
			playMusic();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void createMusic(double tempo, int measureNumber,
			int beatsPerMeasure, double beatNote) {
		List<Double> noteFrequencies = new ArrayList<>();
		List<Integer> mode = new ArrayList<>();
		mode.add(0);
		mode.add(2);
		mode.add(2);
		mode.add(1);
		mode.add(2);
		mode.add(2);
		mode.add(2);
		mode.add(1);
		if (key != 12) {
			int degree = 0;
			for (int i = 0; i < 7; i++) {
				double exp = ((double) degree + mode.get(i)) / 12.00;
				double f = chromatic.get(key) * Math.pow(2d, exp);
				degree += mode.get(i);
				noteFrequencies.add(i, f);
			}
		} else {
			for (int i = 0; i < 12; i++) {
				noteFrequencies.add(i, chromatic.get(i));
			}
		}
		List<Double> noteLengths = new ArrayList<>();
		noteLengths.add(beatNote);
		noteLengths.add((beatNote / 2) + ((beatNote / 2) / 2));
		noteLengths.add(beatNote / 2);
		noteLengths.add((beatNote / 4) + ((beatNote / 4) / 2));
		noteLengths.add(beatNote / 4);
		noteLengths.add((beatNote / 8) + ((beatNote / 8) / 2));
		noteLengths.add(beatNote / 8);
		noteLengths.add(beatNote / 16);
		for (int currentMeasure = 0; currentMeasure < measureNumber; currentMeasure++) {
			for (double beat = 0.00; beat < beatsPerMeasure;) {
				Random rand = new Random();
				int frequencyKey = rand.nextInt(noteFrequencies.size());
				double frequency = noteFrequencies.get(frequencyKey);
				int lengthKey = rand.nextInt(noteLengths.size());
				double length = noteLengths.get(lengthKey);
				if (length > beatsPerMeasure - beat) {
					length = beatsPerMeasure - beat;
				}
				Note note = new Note(length, frequency);
				music.add(note);
				beat += length;
			}
		}
	}

	public static void playMusic() throws LineUnavailableException {
		final AudioFormat af = new AudioFormat(16384, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, 16384);
		line.start();
		for (Note note : music) {
			double hz = note.getFrequency();
			double duration = (60 / tempo) * note.getLength() * 1000;
			System.out.println(hz + "\n" + duration);
			play(line, note, (int) duration);
		}
	}

	public static void play(SourceDataLine line, Note note, int duration) {
		int length = 16384 * duration / 1000;
		int count = line.write(note.data(), 0, length);
		System.out.println("wrote " + count + " bytes");
	}
}
