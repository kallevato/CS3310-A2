package hw2_cs3310_Allevato;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.EmptyStackException;

public class Stack<Item extends Comparable<Item>> implements Iterable<Item>{
	private int n;
	public Node first;
	public Item max;
	
	public Long pushTime;
	public Long popTime;
	public Long maxTime;
	
	private static class Node<Item> {
		public Item item;
		public Node next;
	}
	
	public Stack() {
		first = null;
		int n = 0;
	}
	
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size() {
        return n;
    }
    
    public Item getMax() {
    	Long maxStart = System.nanoTime();
    	if(isEmpty()) throw new EmptyStackException();
    	maxTime  = System.nanoTime() - maxStart;
    	return max;
    }
    
    public void newMax(){
    	Stack<Item> maxFinder = new Stack<>();
    	while(!this.isEmpty()) {
    		maxFinder.push(this.pop());
	}
    	while(!maxFinder.isEmpty()) {
			this.push(maxFinder.pop());
		}
    }
	
    public void push(Item item) {
    	Long pushStart = System.nanoTime();
    	if(this.first.item == null) {
    		first = new Node();
    		first.item = item;  
    		max = first.item;
    	}
    	else {
	        Node oldfirst = this.first;
	        first.item = item;
	        if(first.item.compareTo(oldfirst.item) > 0) {
	        	max = first.item;
	        }
	        first.next = oldfirst;
    	}
    	n++;
    	System.out.println("Pushed: " + first.item);
    	System.out.println("Max: " + max);
    	Long pushTime = System.nanoTime() - pushStart;
    }
	
    public Item pop() {
	Long popStart = System.nanoTime();
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
	    
	if(max != null && item.compareTo(this.first.item) == 0) {
		max = null;
		newMax();
		}
	System.out.println("Popped: " + item);
	System.out.println("Max: " + max);
	popTime = System.nanoTime() - popStart;
        return item;                   // return the saved item
    }
    
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    public Iterator<Item> iterator()  { return new ListIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}
