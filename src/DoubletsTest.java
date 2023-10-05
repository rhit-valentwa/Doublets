import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test class focuses on ensuring StackManager and QueueManager is doing the correct thing.
 * Since Links can have many correct implementation, we decide to use a fake links and fake dictionary to produce predictable results.
 * To help you understand what this links look like:
 * 
 * "00000" -> ["10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000" ]
 * "12800" -> ["12810", "12820", "12830", "12840", "12850", "12860", "12870", "12880", "12890" ]
 * "11111" -> null
 * 
 * Basically, all the strings have length of five, and the replacing the first 0 with 1-9 in this string produces its candidates.
 * This link is one direction and ensures that there is only one correct path.
 * 
 * It assumes that findChain() process the return Set in order.
 * 
 * @author zhangq2, modified by chenett1, hollings, jelen
 *
 */
public class DoubletsTest {
	private static double sPoints = 0;

	private static LinksInterface testGraph;
	Doublets doublets;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		testGraph = new LinksInterface() {
			@Override
			public boolean exists(String start) {
				return Pattern.matches("^[0-9]{5}$", start);
			}

			@Override
			public Set<String> getCandidates(String string) {
				if (!exists(string))
					return null;
				TreeSet<String> set = new TreeSet<>();
				for (int index = 0; index < 5; index++) {
					if (string.charAt(index) == '0') {
						for (int i = 1; i < 10; i++)
							set.add(string.substring(0, index) + i + string.substring(index+1));
						return set;
					}
				}
				return set;
			}
		};
	}

	@Before
	public void setUp() throws Exception {
		doublets = new Doublets(testGraph);
	}

	@Test
	public void StackTest1() {
		ChainManager manager = new StackChainManager();
		Chain chain = doublets.findChain("00000", "99000", manager);
		Iterator<String> itr = chain.iterator();
		assertEquals("00000", itr.next());
		assertEquals("90000", itr.next());
		assertEquals("99000", itr.next());
		assertFalse("Expected: false", itr.hasNext());

		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 17", manager.maxSize() <= 17);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 3", manager.getNumberOfNexts() <= 3);
		sPoints += 3;
	}

	@Test
	public void StackTest2() {
		ChainManager manager = new StackChainManager();
		Chain chain = doublets.findChain("00000", "99991", manager);
		Iterator<String> itr = chain.iterator();
		assertEquals("00000", itr.next());
		assertEquals("90000", itr.next());
		assertEquals("99000", itr.next());
		assertEquals("99900", itr.next());
		assertEquals("99990", itr.next());
		assertEquals("99991", itr.next());
		assertFalse("Expected: false", itr.hasNext());

		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 41", manager.maxSize() <= 41);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 14", manager.getNumberOfNexts() <= 14);
		sPoints += 3;
	}

	@Test
	public void StackTest3() {
		ChainManager manager = new StackChainManager();
		Chain chain = doublets.findChain("00000", "71210", manager);
		Iterator<String> itr = chain.iterator();
		assertEquals("00000", itr.next());
		assertEquals("70000", itr.next());
		assertEquals("71000", itr.next());
		assertEquals("71200", itr.next());
		assertEquals("71210", itr.next());
		assertFalse("Expected: false", itr.hasNext());

		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 41", manager.maxSize() <= 41);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 22044", manager.getNumberOfNexts() <= 22044);
		sPoints += 3;
	}

	@Test
	public void QueueTest1() {
		ChainManager manager = new QueueChainManager();
		Chain chain = doublets.findChain("00000", "11000", manager);
		Iterator<String> itr = chain.iterator();
		assertEquals("00000", itr.next());
		assertEquals("10000", itr.next());
		assertEquals("11000", itr.next());
		assertFalse("Expected: false", itr.hasNext());

		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 81", manager.maxSize() <= 81);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 11", manager.getNumberOfNexts() <= 11);
		sPoints += 3;
	}

	@Test
	public void QueueTest2() {
		ChainManager manager = new QueueChainManager();
		Chain chain = doublets.findChain("00000", "23000", manager);
		Iterator<String> itr = chain.iterator();
		assertEquals("00000", itr.next());
		assertEquals("20000", itr.next());
		assertEquals("23000", itr.next());
		assertFalse("Expected: false", itr.hasNext());

		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 169", manager.maxSize() <= 169);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 22", manager.getNumberOfNexts() <= 22);
		sPoints += 3;
	}

	@Test
	public void QueueTest3() {
		ChainManager manager = new QueueChainManager();
		Chain chain = doublets.findChain("00000", "93821", manager);
		Iterator<String> itr = chain.iterator();
		assertEquals("00000", itr.next());
		assertEquals("90000", itr.next());
		assertEquals("93000", itr.next());
		assertEquals("93800", itr.next());
		assertEquals("93820", itr.next());
		assertEquals("93821", itr.next());
		assertFalse("Expected: false", itr.hasNext());

		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 59049", manager.maxSize() <= 59049);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 61904", manager.getNumberOfNexts() <= 61904);
		sPoints += 3;
	}
	
	@Test(timeout=10)
	public void TestDifferentLength() {
		ChainManager manager = new StackChainManager();
		Chain chain = doublets.findChain("00000", "000000", manager);
		// For different-length inputs, code should immediately return null 
		assertNull("Expected: null", chain);
		assertEquals(0, manager.maxSize());
		assertEquals(0, manager.getNumberOfNexts());
		sPoints += 1;
	}
	
	@Test(timeout=10)
	public void TestStartNotInDict() {
		ChainManager manager = new StackChainManager();
		Chain chain = doublets.findChain("xxxxx", "00000", manager);
		// For start word that isn't in the dictionary, code should immediately return null
		// [Optional: can also add this test for end word]
		assertNull("Expected: null", chain);
		assertEquals(0, manager.maxSize());
		assertEquals(0, manager.getNumberOfNexts());
		sPoints += 1;
	}
	
	@Test(timeout=10)
	public void TestNoPath1() {
		ChainManager manager = new StackChainManager();
		Chain chain = doublets.findChain("14500", "22222", manager);
		assertNull("Expected: null", chain);
		
		assertTrue("Manager.maxSize() [" + manager.maxSize() + "] > 17", manager.maxSize() <= 17);
		assertTrue("Manager.getNumberOfNexts() [" + manager.getNumberOfNexts() + "] > 91", manager.getNumberOfNexts() <= 91);
		sPoints += 2;
	}
	
	@AfterClass
	public static void printScore() {
		System.out.printf("Doublets (stacks and queues) tests %.0f/22\n", sPoints);
	}
}
