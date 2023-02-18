// DO NOT CHANGE THE METHOD NAMES

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.*;

public class MyListIteratorCustomTester {

    private MyLinkedList testList;
    private MyLinkedList.MyListIterator testListIter;

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
        testList = new MyLinkedList();
        testList.add("Alpha");
        testList.add("Beta");
        testList.add("Charlie");
        testListIter = testList.new MyListIterator();

    }

    /**
     * Aims to test the next() method when iterator is at end of the list 
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextEnd() {
        testListIter.left = testList.head.getNext().getNext();
        testListIter.right = testListIter.left.getNext().getNext();
        testListIter.idx = 3;
        testListIter.next();
    }

    /**
     * Aims to test the previous() method when iterator is at the start of the 
     * list 
     */
    @Test(expected = NoSuchElementException.class)
    public void testPreviousStart() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        testListIter.idx = 0;
        testListIter.previous();
    }

    /**
     * Aims to test the add(E e) method when an invalid element is added
     */
    @Test(expected = NullPointerException.class)
    public void testAddInvalid() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        testListIter.next();
        testListIter.add(null);
    }

    /**
     * Aims to test the set(E e) method when canRemoveOrSet is false
     */
    @Test(expected = IllegalStateException.class)
    public void testCantSet() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        testListIter.set("Delta");
    }


    /**
     * Aims to test the set(E e) method when an invalid element is set
     */
    @Test(expected = NullPointerException.class)
    public void testSetInvalid() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        testListIter.next();
        testListIter.set(null);
    }

    /**
     * Aims to test the remove() method when canRemoveOrSet is false
     */
    @Test(expected = IllegalStateException.class)
    public void testCantRemove() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        testListIter.remove();
    }

    /**
     * Aims to tests the hasNext() method at the end of a list
     */
    @Test
    public void testHasNextEnd() {
        testListIter.left = testList.head.getNext().getNext();
        testListIter.right = testListIter.left.getNext().getNext();
        testListIter.idx = 3;
        assertEquals(false, testListIter.hasNext());
    }

    /**
     * Aims to test the hasPrevious() method at the start of a list
     */
    @Test
    public void testHasPreviousStart() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        assertEquals(false, testListIter.hasPrevious());
    }

    /**
     * Aims to test the previousIndex() method at the start of a list
     */
    @Test
    public void testPreviousIndexStart() {
        testListIter.left = testList.head;
        testListIter.right = testList.head.getNext();
        assertEquals(-1, testListIter.previousIndex());
    }

    /**
     * Aims to test the nextIndex() method at the end of a list
     */
    @Test
    public void testNextIndexEnd() {
        testListIter.left = testList.head.getNext().getNext();
        testListIter.right = testListIter.left.getNext().getNext();
        testListIter.idx = 3;
        assertEquals(testList.size, testListIter.nextIndex());
    }
}
