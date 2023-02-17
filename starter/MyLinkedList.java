/**
 * @author Dung (Jonny) Le
 * Email: dal018@ucsd.edu
 * PID: A16873166
 * Date: 2/17/2023
 * References: Week 3 Course Lecture Powerpoint, LLIterator.java
 * This file contains my implementation from MyLinkedList and Iterator.  
 */

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> extends AbstractList<E> {

    static int size;
    Node head;
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /** 
         * Constructor to create singleton Node 
         * @param element Element to add, can be null	
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /** 
         * Set the parameter prev as the previous node
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;		
        }

        /** 
         * Set the parameter next as the next node
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /** 
         * Set the parameter element as the node's data
         * @param element new element 
         */
        public void setElement(E element) {
            this.data = element;
        }

        /** 
         * Accessor to get the next Node in the list 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }
        /** 
         * Accessor to get the prev Node in the list
         * @return the previous node  
         */
        public Node getPrev() {
            return this.prev;
        } 
        /** 
         * Accessor to get the Nodes Element 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        } 
    }

    //  Implementation of the MyLinkedList Class
    /** Only 0-argument constructor is defined */
    public MyLinkedList() {
        /* Add your implementation here */
        // TODO
        this.head = new Node(null);
        this.tail = new Node(null);
        this.head.setNext(tail);
        this.tail.setPrev(head);
        size = 0;
    }

    @Override
    public int size() {
        // need to implement the size method
        return size; // TODO
    }

    @Override
    public E get(int index) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        Node returnNode = getNth(index);
        return returnNode.getElement();
    }

    /**
     * Append with specified index (middle of list)
     * Throw IndexOutOfBoundsException if index is invalid
     * Throw NullPointerException if data is null
     * Increase size at the end
     * 
     * @param index of type int
     * @param data of generic type E
     * 
     * Instance variables:
     * addedNode - new node with data parameter as its data
     * curNode - current node at index parameter
     */
    @Override
    public void add(int index, E data) {
        /* Add your implementation here */
        // TODO
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        if(data == null){
           throw new NullPointerException(); 
        }

        Node addedNode = new Node(data);

        //Add at beginning
        if(index == 0){
            Node curNode = getNth(0);

            head.setNext(addedNode);
            addedNode.setPrev(head);

            addedNode.setNext(curNode);
            curNode.setPrev(addedNode);
            
        } else{
            //Add node to an empty list
            //Must set previous to head and next to tail
            //Set next and previous as node
            if(size == 0){
                addedNode.setPrev(head);
                head.setNext(addedNode);

                addedNode.setNext(tail);
                tail.setPrev(addedNode);
            } 
            //Else, get current node
            //Set previous of added node as the previous of current node
            //Set next of added node as current node
            //Set previous of current node as added node
            else{
                Node curNode = getNth(index);

                addedNode.setPrev(curNode.getPrev());
                addedNode.setNext(curNode);

                curNode.setPrev(addedNode);
            }
        }    
        size++;
    }

    /**
     * Append at end of linked list
     * Throw NullPointerException if data is null
     * Increases size
     * 
     * @param data of type E to be appended to linked list
     * 
     * Instance variables:
     * addedNode - new node with data parameter as its data
     * 
     * @return true
     */
    public boolean add(E data) {
        if (data == null){
            throw new NullPointerException();
        }

        Node addedNode = new Node(data);

        //Set next of the node before tail dummy as added node
        tail.getPrev().setNext(addedNode);

        //Set previous of added node as node before tail dummy
        //Set next of added node as tail
        addedNode.setPrev(tail.getPrev());
        addedNode.setNext(tail);

        //Set previous of tail as added node
        tail.setPrev(addedNode);

        size++;
        
        return true; 
    }

    /**
     * Sets element at specified index with provided data
     * Throws IndexOutOfBoundsException if index is invalid
     * Throws NullPointerException if data is null
     * 
     * @param index of type int
     * @param data of generic type E
     * 
     * Instance variables:
     * curNode - node and data at specified index
     * returnElem - data of curNode before it is replaced
     * 
     * @return data of generic type E of node at specified index prior to set method
     */
    public E set(int index, E data) {
        
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        if(data == null){
            throw new NullPointerException();
        }

        Node curNode = getNth(index);

        E returnElem = curNode.getElement();
        curNode.setElement(data);

        return returnElem; 
    }

    /**
     * Remove function unlinks node at index from linked list
     * Throws IndexOutOfBoundsException if index is invalid or linked list is empty
     * Link node before and after current node with each other, hence removing current node
     * Reduce size
     * 
     * @param index of type int
     * 
     * Instance variables:
     * curNode - current node at position specified by parameter
     * returnElem - data of curNode
     * 
     * @return data of generic type E of removed element
     */
    public E remove(int index) {
        if(index < 0 || index > size || isEmpty()){
            throw new IndexOutOfBoundsException();
        }

        Node curNode = getNth(index);
        E returnElem = curNode.getElement();

        //current node' previous node "set next" should be the node after current node
        //Current node's next node "set previous" should be the node before current node
        curNode.getPrev().setNext(curNode.getNext());
        curNode.getNext().setPrev(curNode.getPrev());
        size--;
        
        return returnElem; 
    }

    /*
     * Clear linked list
     * Set next of head to tail
     * Set previous of tail to head
     * Change size to 0
     */
    public void clear() {
        /* Add your implementation here */
        this.head.setNext(tail);
        this.tail.setPrev(head);
        size = 0;
    }

    /**
     * This method decides whether if linked list is empty
     * 
     * @return true if size is 0, false if otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * This method gets Node at specified index
     * Throws IndexOutOfBoundsException if index is out of bounds or linked list is empty
     * Starts at beginning of list idx = 0 until idx = index 
     * 
     * @param index of type int
     * 
     * Instance variables:
     * returnNode - set at head (sentinel) node's next, or first element
     * idx - index that is used to compare to index parameter 
     * 
     * @return Node that is returned at the specified index
     */
    protected Node getNth(int index) {
        if(index < 0 || index > size || isEmpty()){
            throw new IndexOutOfBoundsException();
        }

        int idx = 0;
        //Set node that needs to be returned to first node
        Node returnNode = head.getNext();
        
        //While loop runs as long as idx != index
        while(idx != index){
            returnNode = returnNode.getNext();
            idx++;
        }

        return returnNode;
    }

    /**
     * Creates a separate Iterator that traverses a linked list
     * Iterator can also remove, add, and modify a node and its contents
     * Implements ListIterator, imported from libraries 
     */
    protected class MyListIterator implements ListIterator<E>
    {
        /* Instance variables */
        int idx;
        Node left, right;
        boolean forward;
        boolean canRemoveOrSet;

        /*
         * Constructor
         */
        public MyListIterator() {
            left = head;
            right = head.getNext();
            idx = 0;
            forward = true;
            canRemoveOrSet = false;
        }

        /**
         * This method returns true if next() method is possible. 
         * Possible only if index is less than size of linked list
         * 
         * @return: if index is less than size of linked list, return true, false if not.
         */
        public boolean hasNext()
        {
            return this.idx < MyLinkedList.size;
        }

        /**
         * This method iterates the iterator one index forward
         * If hasNext() is false, throws a NoSuchElementException
         * 
         * Variables: 
         * returnElem - Stores necessary data of a generic type E to return
         * 
         * @return data of next element in the list
         */
        public E next()
        {
            if(!hasNext()){
                throw new NoSuchElementException();
            }

            E returnElem = right.data;                  //Set returned element as the current right pointer

            this.left = left.next;
            this.right = right.next;
            this.idx++;
            this.forward = true;                        //Make sure forward becomes true
            this.canRemoveOrSet = true;                 //canRemoveOrSet should also become true

            return returnElem;
        }

        /**
         * This method returns whether previous can be called or not
         * If previous of left pointer is null, then it is not possible
         * 
         * @return whether previous of left is null
         */
        public boolean hasPrevious()
        {
            return this.left.getPrev() != null;
        }

        /**
         * This method iterates the iterator one index backward
         * If data of left is null (left is head), throw NoSuchElementException
         * 
         * Instance variables: 
         * returnElem - Stores data of a generic type E to return
         * 
         * @return data of previous element in the list
         */
        public E previous(){
            
            if(left.data == null){
                throw new NoSuchElementException();
            }

            E returnElem = left.data;

            this.left = left.prev;
            this.right = right.prev;
            this.idx--;
            this.forward = false;                        //Make sure forward is false
            this.canRemoveOrSet = true;                  //Can remove or set is still true

            return returnElem;
        }

        /**
         * Returns the next index if next() is called
         * 
         * @return index 
         */
        public int nextIndex()
        {
            return idx;
        }

        /**
         * Returns the previous index if previous() is called
         * 
         * @return index if index if index is not 0, -1 if it is
         */
        public int previousIndex()
        {
            if(idx == 0){
                return -1;
            }
            return idx - 1;
        }

        /**
         * Adds element to linked list
         * Adds element right before index of return value if next() is called
         * Adds element right after index of return value previous() is called
         * Sets canRemoveOrSet to false, as add or remove cannot be called twice in a row
         * Throws NullPointerException if element is null
         *
         * @param element of generic type E. 
         * 
         * Instance variables:
         * addedNode - a node with parameter as data, used to add to linked list
         */
        public void add(E element)
        {
            //Throws NullPointerException if element is null
            if(element == null){
                throw new NullPointerException();
            }

            Node addedNode = new Node(element);     //Add new node
            //Add left
            if(forward){
                addedNode.setPrev(left.getPrev());      //Set previous of added node to left
                addedNode.setNext(left); 
                left.setPrev(addedNode);
                left.getPrev().setNext(addedNode);
                left = addedNode;
            } 
            //Add right
            else{
                addedNode.setPrev(left);                //Set previous of added node to left
                addedNode.setNext(right);               //Set next of added node to right 
                right.setPrev(addedNode);
                left.setNext(addedNode);
                right = addedNode;
            }

            this.canRemoveOrSet = false;
            MyLinkedList.size++;
            this.idx++;
        }

        /**
         * Sets element of linked list 
         * Throws NullPointerException if element is null
         * Throws IllegalStateException is canRemoveOrSet is false
         * Replaces element that would be returned if next() or previous is called
         * Sets canRemoveOrSet to false
         * 
         * @param element of generic type E
         */
        public void set(E element)
        {
            if(element == null){
                throw new NullPointerException();
            }

            if(!this.canRemoveOrSet){
                throw new IllegalStateException();
            }

            if(forward){
                left.data = element;
            } else{
                right.data = element;
            }

            this.canRemoveOrSet = false;
        }

        /**
         * Removes element of a linked list 
         * Throws IllegalStateException is canRemoveOrSet is false
         * Removes element that would be returned if next() or previous is called
         * If forward is true, remove left pointer element
         * If forward is false, remove right pointer element
         * Sets canRemoveOrSet to false
         */
        public void remove()
        {
            if(!this.canRemoveOrSet){
                throw new IllegalStateException();
            }

            //Remove left
            if(forward){
                left.data = null;
                left = left.prev;
                right.setPrev(left);
                left.setNext(right);
                this.idx--;
            } 
            //Remove right
            else{
                right.data = null;
                right = right.next;
                right.setPrev(left);
                left.setNext(right);
            }

            MyLinkedList.size--;
            this.canRemoveOrSet = false;
        }
    }
}
