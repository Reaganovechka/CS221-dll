import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Double-linked node-based implementation of IndexedUnsortedList.
 * Supports a fully-functional ListIterator in additon to basic Iterator.
 * 
 * @author Reagan Ovechka
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int modCount;

    /**
     * Initialize a new empty list
     */
    public IUDoubleLinkedList() {
        head = tail = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<T>(element);
        newNode.setNextNode(head);

        if (tail == null) {
            tail = newNode;
        } else {
            head.setPrevNode(newNode);
        }
        head = newNode;
        size++;
        modCount++;

    }

    @Override
    public void addToRear(T element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addToRear'");
    }

    @Override
    public void add(T element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void addAfter(T element, T target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAfter'");
    }

    @Override
    public void add(int index, T element) { // TODO
        Node<T> newNode = new Node<T>(element);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (isEmpty()) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.setNextNode(head);
            head.setPrevNode(newNode);
            head = newNode;
        } else { // General case- long list
            Node<T> priorNode = head;
            for (int i = 0; i < index - 1; i++) {
                priorNode = priorNode.getNextNode();
            }
            newNode.setPrevNode(priorNode);
            newNode.setNextNode(priorNode.getNextNode());
            priorNode.setNextNode(newNode);
            // Node<T> afterNode = priorNode.getNextNode();
            // priorNode.setNextNode(newNode);
            // newNode.setPrevNode(priorNode);
            // afterNode.setPrevNode(newNode);
            // newNode.setNextNode(afterNode);
            
        }
        // if (index == 0) { // Beginning of the list, long OR empty
        // newNode.setNextNode(head);
        // if (head != null){
        // head.setPrevNode(newNode);
        // } else {
        // tail = newNode;
        // }
        // head = newNode;
        // }
    //     if (head == tail) { // Single element list
    //         if (index == 0) {
    //             head.setPrevNode(newNode);
    //             newNode.setNextNode(head);
    //             head = newNode;
    //         } else {
    //             tail.setNextNode(newNode);
    //             newNode.setPrevNode(tail);
    //             tail = newNode;
    //         }
    //    // } else if (priorNode == tail) { // End of the list
    //         tail.setNextNode(newNode);
    //         newNode.setPrevNode(tail);
    //         tail = newNode;
    //     }

    //     size++;
    //     modCount++;
    }

    @Override
    public T removeFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();
        tail = tail.getPrevNode();
        if (size == 1) { //or tail == null
            head = null; // removed only element
        } else {
            tail.setNextNode(null);
        }

        size--;
        modCount++;
        return retVal;
    }

    @Override
    public T remove(T element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public T remove(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void set(int index, T element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'set'");
    }

    @Override
    public T get(int index) { // Single-linked impl. can be improved from n/2 to n/4 avg
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode.getElement();
    }

    @Override
    public int indexOf(T element) {
        int index = 0;
        Node<T> currentNode = head; // Start at the beginning, head is the first node we ill look at
        while (currentNode != null && !element.equals(currentNode.getElement())) { // either I found it or i didnt
            currentNode = currentNode.getNextNode();
            index++;
        }
        if (currentNode == null) { // Did not find it, OR index >= size()
            index = -1;
        }
        return index;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.getElement();
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return indexOf(target) > -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public ListIterator<T> listIterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    /**
     * List iterator for IUDoubleLinkedList
     */
    private class DLLIterator implements ListIterator<T> {
        private Node<T> nextNode;
        private int nextIndex;
        private Node<T> lastReturnedNode;
        private int iterModCount;

        /**
         * Initialize iterator before first element.
         */
        public DLLIterator(){
            nextNode = head;
            nextIndex = 0;
            iterModCount = modCount;
            lastReturnedNode = null;
        }

        /**
         * Initialize iterator before the specified index.
         * @param startingIndex index that would be next after constructor
         */
        public DLLIterator(int startingIndex) {
            //TODO - shoul def be optimized to start from the closest end
        }


        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasNext'");
        }

        @Override
        public T next() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'next'");
        }

        @Override
        public boolean hasPrevious() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasPrevious'");
        }

        @Override
        public T previous() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'previous'");
        }

        @Override
        public int nextIndex() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'nextIndex'");
        }

        @Override
        public int previousIndex() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'previousIndex'");
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public void set(T e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'set'");
        }

        @Override
        public void add(T e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'add'");
        }

    }

}