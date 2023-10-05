import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Emile Marois
 * @author William Valentine
 */
public class Doublets {
	private LinksInterface links;
	
	public Doublets(LinksInterface links) {
		this.links = links;
	}

	public static void main(String[] args) {
		Links link;
		try {
			link = new Links("./data/english.cleaned.all.35.txt");
		} catch (FileNotFoundException e){
			return;
		}
		Doublets doub = new Doublets(link);
		Scanner scanner = new Scanner(System.in);
		ChainManager manager;

		String starting_word = "";
		String ending_word = "";
		String input;

		System.out.println("Welcome to Doublets, a game of \"verbal torture.\"");

		while (true) {
			System.out.print("Enter starting word: ");
			starting_word = scanner.nextLine();
			while (starting_word.length() == 0 || !link.exists(starting_word)) {
				if (starting_word.length() == 0) {
					System.out.print("You must enter a starting word: ");
					starting_word = scanner.nextLine();
				}
				else if (!link.exists(starting_word)) {
					System.out.print("The word \"" + starting_word + "\" is not valid. Please try again: ");
					starting_word = scanner.nextLine();
				}
			}

			System.out.print("Enter ending word: ");
			ending_word = scanner.nextLine();
			while (ending_word.length() == 0 || !link.exists(ending_word) || starting_word.length() != ending_word.length()) {
				if (ending_word.length() == 0) {
					System.out.print("You must enter a ending word: ");
					ending_word = scanner.nextLine();
				}
				else if (starting_word.length() != ending_word.length()) {
					System.out.print("Ending word must have the same length (" +  starting_word.length() + ") as the starting word: ");
					ending_word = scanner.nextLine();
				}
				else if (!link.exists(ending_word)) {
					System.out.print("The word \"" + ending_word + "\" is not valid. Please try again: ");
					ending_word = scanner.nextLine();
				}
			}
			while(true){
				System.out.print("Enter chain manager (s: stack, q: queue, p: priority queue, x: exit): ");
				input = scanner.nextLine();
				
				if(input.equals("s")) {
					manager = new StackChainManager();
					break;
				} else if (input.equals("q")) {
					manager = new QueueChainManager();
					break;
				} else if (input.equals("p")) {
					manager = new PriorityQueueChainManager(ending_word);
					break;
				} else if (input.equals("x")){
					System.out.println("Goodbye!");
					scanner.close();
					return;
				}
			}
			
			Chain finalChain = doub.findChain(starting_word, ending_word, manager);

			if (finalChain != null) {
				System.out.println("Solution: " + finalChain.toString());
				System.out.println("Solution length: " + finalChain.length());
				System.out.println("Chains Examined: " + manager.getNumberOfNexts());
				System.out.println("Max Chain Manager size: " + manager.maxSize());
			}
			else {
				System.out.println("No doublet chain exists from " + starting_word + " to " + ending_word + ".");
			}
		}
	}

	public Chain findChain(String start, String end, ChainManager manager) {

		// ensure start and end length are the same and start exists within the dictionary
		if(start.length() != end.length() || !links.exists(start))
			return null;
		
		// begin the chain with starting word
		Chain firstChain = new Chain();
		firstChain = firstChain.addLast(start);
		
		// add the first chain to the chain manager
		manager.add(firstChain);

		// start a loop and iterate through the contents of the tree
		while(!manager.isEmpty()) {
			Chain currentChain = manager.next();
			String currentCandidate = currentChain.getLast();

			// if the current candidate is equal to the end of the doublet, end the loop
			if (currentCandidate.equals(end)) {
				return currentChain;
			}
			
			Set<String> candidates = links.getCandidates(currentCandidate); // returns all potential links for word

			if(candidates != null){
				
				// add all candidates from currentCanidate to manager
				for(String s: candidates) {
					if (!currentChain.contains(s)) {
						Chain newChain = currentChain.addLast(s);
						manager.add(newChain);
					}

				}
			}
		}
		return null;
	}
}