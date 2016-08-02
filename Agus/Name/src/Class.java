import java.util.ArrayList;
import java.util.Random;

public class Class {
	public static void main(String[] args) {
		
		ArrayList<String> sentence = new ArrayList<>();
		
		ArrayList<String> words = new ArrayList<>();
		
		ArrayList<String> nouns = new ArrayList<>();
		ArrayList<String> verbs = new ArrayList<>();
		ArrayList<String> adjectives = new ArrayList<>();
		ArrayList<String> prepositions = new ArrayList<>();
		
		String[] wordss = { "the", "adjective","noun","that","noun","verb","belongs to", "noun", "."};
		
		String[] nounss = {"joke", "industrial grinders", "Terrence Vassar", "Doom House", "Mood House", "mistake", "leaf", "Cliff", "worm", "Reginald P. Linux", "skin", "iD Vassar"};
		String[] verbss = {"went", "crawled", "ate", "moved", "created", "slept", "said", "told", "wept", "sneezed", "crushed", "is", "was", "ran"};
		String[] adjectivess = {"spooky", "slimy", "slippery", "decorated", "creepy", "easy", "green"};
		String[] prepositionss = {"to", "at", "for", "by", "with", "because of", "under", "over", "near", "of", "from"};
		
			for (String wordw : wordss) {
				words.add(wordw);
			}
			for (String wordn : nounss) {
				nouns.add(wordn);
			}
			for (String wordv : verbss) {
				verbs.add(wordv);
			}
			for (String worda : adjectivess) {
				adjectives.add(worda);
			}
			for (String wordp : prepositionss) {
				prepositions.add(wordp);
			}
			
		while (words.size() > 0) {
			Random rand = new Random();
			
			switch (words.get(0)) {
			
				case "noun":
					int nnumber = rand.nextInt(nouns.size());
					sentence.add(nouns.get(nnumber));
					nouns.remove(nnumber);
					break;
					
				case "verb":
					int vnumber = rand.nextInt(verbs.size());
					sentence.add(verbs.get(vnumber));
					verbs.remove(vnumber);
					break;
					
				case "adjective":
					int anumber = rand.nextInt(adjectives.size());
					sentence.add(adjectives.get(anumber));
					adjectives.remove(anumber);
					break;
					
				case "preposition":
					int pnumber = rand.nextInt(prepositions.size());
					sentence.add(prepositions.get(pnumber));
					prepositions.remove(pnumber);
					break;
					
				default:
					sentence.add(words.get(0));
					break;

			}
			words.remove(0);
		}
		
		boolean first = true;
		
		for (String thing : sentence) {
			System.out.print((first == true || thing.equalsIgnoreCase(".") || thing.equalsIgnoreCase(",") ? "" : " ") + thing);
			first = false;
		}
	}
}
