/**
 * @author Dung (Jonny) Le
 * Date: 2/17/2023
 * This file contains my implementation from MyLinkedList and Iterator 
 * This PA was done in small collaboration with Stevie Komon for fixing algorithms
 * 
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
        this.size = 0;
    }

    @Override
    public int size() {
        // need to implement the size method
        return this.size; // TODO
    }

    @Override
    public E get(int index) {
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        Node returnNode = getNth(index);
        return returnNode.getElement();
    }

    /*
     * Append with index (middle of list)
     * Throw index out of bounds exception if index is less than 0 
     * OR if index greater than this.size + 1 (since if index is only larger than this.size + 1, then append to linked list)
     */
    @Override
    public void add(int index, E data) {
        /* Add your implementation here */
        // TODO
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }

        if(data == null){
           throw new NullPointerException(); 
        }

        Node addedNode = new Node(data);

        //Test for edge cases: add at beginning
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
            if(this.size == 0){
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
        
        //Add size at end
        this.size++;
    }

    /*
     * Append at end of linked list
     * Throw null pointer exception only if data is null
     */
    public boolean add(E data) {
        if (data == null){
            throw new NullPointerException();
        } else{
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
        }
        return true; 
    }

    public E set(int index, E data) {
        
        if(index < 0 || index > this.size){
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

    /*
     * Remove function unlinks node at index from linked list
     * Link node before and after current node with each other, hence removing current node
     * Reduce size
     * Return the element removed
     */
    public E remove(int index) {
        if(index < 0 || index > this.size || this.size == 0){
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
     * Set previouis of tail to head
     * Change size to 0
     */
    public void clear() {
        /* Add your implementation here */
        this.head.setNext(tail);
        this.tail.setPrev(head);
        this.size = 0;
    }


    public boolean isEmpty() {
        if(this.size == 0)
            return true;  
        return false;
    }

    protected Node getNth(int index) {
        if(index < 0 || index > this.size || this.size == 0){
            throw new IndexOutOfBoundsException();
        }

        int idx = 0;
        //Set node that needs to be returned to first node
        Node returnNode = head.getNext();
        
        while(idx != index){
            returnNode = returnNode.getNext();
            idx++;
        }

        return returnNode;
    }

    protected class MyListIterator implements ListIterator<E>
    {
        int idx;
        Node left, right;
        boolean forward;
        boolean canRemoveOrSet;

        public MyListIterator() {
            left = head;
            right = head.getNext();
            idx = 0;
            forward = true;
            canRemoveOrSet = false;
        }

        public boolean hasNext()
        {
            return this.idx < MyLinkedList.size;
        }

        public E next()
        {
            if(!hasNext()){
                throw new NoSuchElementException();
            }

            if(size == 0){
                return null;
            }

            E returnElem = right.data;

            this.left = left.next;
            this.right = right.next;
            this.idx++;
            this.forward = true;                        //Make sure forward becomes true
            this.canRemoveOrSet = true;

            return returnElem;
        }

        public boolean hasPrevious()
        {
            return this.left.getPrev() != null;
        }

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

        public int nextIndex()
        {
            if(idx < size){
                return idx;
            }
            return size;
        }

        public int previousIndex()
        {
            if(idx == 0){
                return -1;
            }
            return idx - 1;
        }

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

        public void remove()
        {
            if(!this.canRemoveOrSet){
                throw new IllegalStateException();
            }

            if(forward){
                left.data = null;
                left = left.prev;
                right.setPrev(left);
                left.setNext(right);
                this.idx--;
            } else{
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
