import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Test;


/**
 * Tests the Links class.
 *
 * @author Matt Boutell. Created Mar 18, 2011.
 * @author Nate Chenette. Modified Sep 28, 2021.
 * @author JHolly. Modified 3 Jan 2023.
 */
public class LinkTest {

	private static double sPoints = 0;

	/**
	 * Test method for implementation of {@link Links#toString()}.
	 * @throws FileNotFoundException
	 */
	@Test
	public void testForToStringBeingImplemented() throws FileNotFoundException {
		Links linksTiny = new Links("../DoubletsData/tiny.dictionary.txt");
		
		String toStringResult = linksTiny.toString();
		assertTrue("\nRequired: implement Links.toString() method\nHave it return a string of the internal map's contents", 
				!toStringResult.contains("Links@"));	
		sPoints += 1;
	}
	
	/**
	 * Test method for {@link Link#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidatesTinyThreeLetterWords() throws FileNotFoundException {
		Links linksTiny = new Links("../DoubletsData/tiny.dictionary.txt");
		Set<String> fooCandidates = linksTiny.getCandidates("foo");
		assertTrue("Expected: true", fooCandidates.contains("for"));
		assertTrue("Expected: true", fooCandidates.contains("too"));
		assertFalse("Expected: false", fooCandidates.contains("bar"));
		assertEquals(2, fooCandidates.size());
		sPoints += 2;
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidatesTinyFourLetterWords() throws FileNotFoundException {
		Links linksTiny = new Links("../DoubletsData/tiny.dictionary.txt");
		Set<String> candidates = linksTiny.getCandidates("math");
		assertTrue("Expected: true", candidates.contains("path"));
		assertTrue("Expected: true", candidates.contains("moth"));
		assertFalse("Expected: false", candidates.contains("mouth"));
		assertEquals(2, candidates.size());
		sPoints += 2;
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidatesTinyMissingWords() throws FileNotFoundException {
		Links linksTiny = new Links("../DoubletsData/tiny.dictionary.txt");
		Set<String> candidates = linksTiny.getCandidates("fwump");
		assertNull("Expected: null", candidates);
		candidates = linksTiny.getCandidates("bryllyg");
		assertNull("Expected: null", candidates);
		sPoints += 2;
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidatesTinyNoCandidates() throws FileNotFoundException {
		Links linksTiny = new Links("../DoubletsData/tiny.dictionary.txt");
		Set<String> candidates = linksTiny.getCandidates("silk");
		
		assertNotNull(candidates);
		assertEquals(0, candidates.size());
		candidates = linksTiny.getCandidates("mouth");
		assertEquals(0, candidates.size());
		sPoints += 2;
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidates10() throws FileNotFoundException {
		Links links10 = new Links("../DoubletsData/english.cleaned.all.10.txt");
		Set<String> candidates = links10.getCandidates("bar");
		assertTrue("Expected: true", candidates.contains("bad"));
		assertTrue("Expected: true", candidates.contains("ban"));
		assertTrue("Expected: true", candidates.contains("car"));
		assertTrue("Expected: true", candidates.contains("far"));
		assertTrue("Expected: true", candidates.contains("war"));
		assertEquals(5, candidates.size());

		candidates = links10.getCandidates("left");
		assertTrue("Expected: true", candidates.contains("lift"));
		assertEquals(1, candidates.size());
		sPoints += 5;
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidates20() throws FileNotFoundException {
		Links links20 = new Links("../DoubletsData/english.cleaned.all.20.txt");
		Set<String> candidates = links20.getCandidates("row");
		assertTrue("Expected: true", candidates.contains("bow"));
		assertTrue("Expected: true", candidates.contains("cow"));
		assertTrue("Expected: true", candidates.contains("how"));
		assertTrue("Expected: true", candidates.contains("low"));
		assertTrue("Expected: true", candidates.contains("now"));
		assertTrue("Expected: true", candidates.contains("raw"));
		assertTrue("Expected: true", candidates.contains("rod"));
		assertTrue("Expected: true", candidates.contains("rot"));
		assertTrue("Expected: true", candidates.contains("wow"));
		assertEquals(9, candidates.size());

		candidates = links20.getCandidates("parse");
		assertTrue("Expected: true", candidates.contains("pause"));
		assertEquals(1, candidates.size());

		candidates = links20.getCandidates("reduction");
		assertTrue("Expected: true", candidates.contains("deduction"));
		assertEquals(1, candidates.size());

		candidates = links20.getCandidates("love");
		assertTrue("Expected: true", candidates.contains("live"));
		assertTrue("Expected: true", candidates.contains("lose"));
		assertTrue("Expected: true", candidates.contains("move"));
		sPoints += 5;
	}
	
	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testGetCandidatesInvalidString() throws FileNotFoundException {
		Links links20 = new Links("../DoubletsData/english.cleaned.all.20.txt");
		Set<String> candidates = links20.getCandidates("aaaa");
		assertNull("Expected: null", candidates);
		sPoints += 1;
	}
	
	@Test
	public void testGetCandidatesEfficiency() throws FileNotFoundException {		
		// Can take a few seconds to load, no efficiency requirement for this.
		Links links20 = new Links("../DoubletsData/english.cleaned.all.20.txt");

		double elapsedTime;
		double startTime = System.currentTimeMillis();
		int NUM_CALLS = 100000;
	
		for (int i = 0; i < NUM_CALLS; i++) {
			links20.getCandidates("row");
		}

		elapsedTime = (System.currentTimeMillis() - startTime);
		System.out.println(elapsedTime + " milliseconds");
		if (elapsedTime > 15) {
			System.out.println("You should check the efficiency of getCandidates() to see if it is O(1), reardless of the size of the dictionary.");
			System.out.println("If the elapsed time is close to 15ms, it should be OK. ");
			System.out.println("This is just a warning that a slow getCandidates() will cause troubles later.");
		} else {
			System.out.println("Passes a conservative speed test - not a guarantee of O(1) efficiency.");
		}
	}
	
	@AfterClass
	public static void printScore() {
		System.out.printf("Links tests %.0f/20\n", sPoints);
	}
}
