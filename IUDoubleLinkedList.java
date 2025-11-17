import java.util.ConcurrentModificationException;
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
        // ListIterator<T> lit = listIterator();
        // lit.add(element);
    }

    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<T>(element);
        if (tail != null) {
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
        modCount++;
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> newNode = new Node<T>(element);
        if (isEmpty() || !contains(target)) { // If the list is empty, or it does not contain the target element
            throw new NoSuchElementException();
        }
        int index = indexOf(target);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        if (currentNode == tail) {
            currentNode.setNextNode(newNode);
            newNode.setPrevNode(tail);
            tail = newNode;
        } else {
            Node<T> continueNode = currentNode.getNextNode();
            currentNode.setNextNode(newNode);
            newNode.setPrevNode(currentNode);
            continueNode.setPrevNode(newNode);
            newNode.setNextNode(continueNode);
        }

        size++;
        modCount++;

    }

    @Override
    public void add(int index, T element) { 
        Node<T> newNode = new Node<T>(element);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (isEmpty() && index == 0) { // Add to an empty list at index 0
            head = newNode;
            tail = newNode;
        } else if (size == 1) {
            if (index == 0) {
                head.setPrevNode(newNode);
                newNode.setNextNode(head);
                head = newNode;
            } else {
                tail.setNextNode(newNode);
                newNode.setPrevNode(newNode);
                tail = newNode;
            }
        } else { // List longer than 1 element

            if (index > size / 2) { // Back half
                Node<T> currentNode = tail;
                int moves = (size - 1) - index; // This would locate how many nodes from the tail the node after where
                                                // the node should be added
                for (int i = 0; i < moves; i++) {
                    currentNode = currentNode.getPrevNode();
                }
                // Now we can add the new node
                if (currentNode == tail) {
                    currentNode.setNextNode(newNode);
                    newNode.setPrevNode(currentNode);
                    tail = newNode;
                } else { // middle of the list in back half
                    Node<T> continueNode = currentNode.getPrevNode();
                    currentNode.setPrevNode(newNode);
                    newNode.setNextNode(currentNode);
                    continueNode.setNextNode(newNode);
                    newNode.setPrevNode(continueNode);
                }
            } else { // front half
                Node<T> currentNode = head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.getNextNode();
                }
                if (currentNode == head && index == 0) {
                    newNode.setNextNode(currentNode);
                    currentNode.setPrevNode(newNode);
                    head = newNode;
                } else {
                    Node<T> continueNode = currentNode.getNextNode();
                    currentNode.setNextNode(newNode);
                    newNode.setPrevNode(currentNode);
                    continueNode.setPrevNode(newNode);
                    newNode.setNextNode(continueNode);
                }
            }
        }

        size++;
        modCount++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<T> firstNode = head;
        if (head == tail) { //Only element in the list
            firstNode = null;
        } else {

        
        head = head.getNextNode();
        head.setPrevNode(null);
        if (head == null) {
            tail = null;
        }
    }
        size--;
        modCount++;
        return firstNode.getElement();
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();
        tail = tail.getPrevNode();
        if (size == 1) { // or tail == null
            head = null; // removed only element
        } else {
            tail.setNextNode(null);
        }

        size--;
        modCount++;
        return retVal;
        // ListIterator<T> lit = listIterator(size);
        // if(!lit.hasPrevious()){
        // throw new NoSuchElementException();
        // }

    }

    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // must check the head node before general case search
        T retVal;
        // Beginning of a list
        if (head.getElement().equals(element)) {
            retVal = head.getElement();
            head = head.getNextNode();
            head.setPrevNode(null);
            if (head == null) { // removed first and only element
                tail = null;
            }
        } else {
            Node<T> currentNode = head;
            while (currentNode != tail && !currentNode.getNextNode().getElement().equals(element)) {
                currentNode = currentNode.getNextNode();
            }
            // if it is never found
            if (currentNode == tail) {
                throw new NoSuchElementException();
            }
            retVal = currentNode.getNextNode().getElement();
            if (currentNode.getNextNode() == tail) {
                tail.setNextNode(null);
                tail = currentNode;
                
            } else {
            // 'general case'- middle of long list
            Node<T> continueNode = currentNode.getNextNode().getNextNode();
            currentNode.setNextNode(continueNode);
            continueNode.setPrevNode(currentNode);
            }
            // Was it the only element?
            if (head == tail) {
                currentNode = null;
            }
            // Was it the last node?
            if (currentNode.getNextNode() == null) {
                tail = currentNode;
            }

        }

        size--;
        modCount++;
        return retVal;

    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.getNextNode();
        }
        T retVal = null;
        // If removed only element
        if (currentNode == head && head == tail) { // if removed first and only element
            retVal = currentNode.getElement();
            currentNode = null;
        } else if (currentNode == head && index == 0) { // if removed first element in long list
            retVal = head.getElement();
            head = currentNode.getNextNode();
            head.setPrevNode(null);
        } else if (currentNode.getNextNode() == tail) { // Removing the last element
            retVal = tail.getElement();
            currentNode.setNextNode(null);
            tail = currentNode;
        } else {
            retVal = currentNode.getNextNode().getElement();
            Node<T> continueNode = currentNode.getNextNode().getNextNode();
            currentNode.setNextNode(continueNode);
            continueNode.setPrevNode(currentNode);
        }

        size--;
        modCount++;
        return retVal;
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index > size / 2) { // Back half
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.getPrevNode();
            }
            currentNode.setElement(element);
        } else { // Front half
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNextNode();
            }
            currentNode.setElement(element);
        }
        modCount++;
    }

    @Override
    public T get(int index) { // Single-linked impl. can be improved from n/2 to n/4 avg
        // if (index < 0 || index >= size) {
        // throw new IndexOutOfBoundsException();
        // }
        // Node<T> currentNode = head;
        // for (int i = 0; i < index; i++) {
        // currentNode = currentNode.getNextNode();
        // }
        // return currentNode.getElement();
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<T> lit = listIterator(index);
        return lit.next();
    }

    @Override
    public int indexOf(T element) {
        int index = 0;
        Node<T> currentNode = head; // Start at the beginning, head is the first node we will look at
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
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (T element : this) {
            str.append(element.toString());
            str.append(", ");
        }
        if (!isEmpty()) {
            str.delete(str.length() - 2, str.length());
        }
        str.append("]");
        return str.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        return new DLLIterator(startingIndex);
    }

    /**
     * List iterator for IUDoubleLinkedList
     */
    private class DLLIterator implements ListIterator<T> {
        private Node<T> nextNode;
        private int nextIndex;
        private int prevIndex;
        private Node<T> lastReturnedNode;
        private int iterModCount;
        private boolean canRemove;
        private boolean canSet;

        /**
         * Initialize iterator before first element.
         */
        public DLLIterator() {
            // nextNode = head;
            // nextIndex = 0;
            // iterModCount = modCount;
            // lastReturnedNode = null;
            this(0);
        }

        /**
         * Initialize iterator before the specified index.
         * 
         * @param startingIndex index that would be next after constructor
         */
        public DLLIterator(int startingIndex) {
            if (startingIndex < 0 || startingIndex > size) {
                throw new IndexOutOfBoundsException();
            }
            if (startingIndex > size / 2) {
                if (startingIndex == size) {
                    nextNode = null;
                } else {
                    nextNode = tail;
                    for (int i = size - 1; i > startingIndex; i--) {
                        nextNode = nextNode.getPrevNode();
                    }
                }
            } else { // Front half
                nextNode = head;
                nextIndex = 0;
                for (int i = 0; i < startingIndex; i++) {
                    nextNode = nextNode.getNextNode();
                    nextIndex++;
                }
            }
            nextIndex = startingIndex;
            prevIndex = 0; //TODO change
            iterModCount = modCount;
            lastReturnedNode = null;
            canRemove = false;

        }

        @Override
        public boolean hasNext() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextNode = nextNode.getNextNode();
            canRemove = true;
            canSet = true;
            nextIndex++;
            return nextNode.getPrevNode().getElement();
        }

        @Override
        public boolean hasPrevious() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            // nextNode.getPrevious() != null; or nextIndex != 0; or nextNode != head;
            return nextNode != head;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            nextNode = nextNode.getPrevNode();
            lastReturnedNode = nextNode;
            nextIndex--;
            canRemove = true;
            canSet = true;
            return nextNode.getElement();
        }

        @Override
        public int nextIndex() {
            if (!hasNext()) {
                return size;
            }
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return prevIndex;
        }

        @Override
        public void remove() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }
            if (!canRemove) {
                throw new IllegalStateException();
            }
            canRemove = false;
            canSet = false;
            // Now I can remove lastReturnedNode
            if (lastReturnedNode == tail) {
                tail = lastReturnedNode.getPrevNode();

            } else {
                lastReturnedNode.getNextNode().setPrevNode(lastReturnedNode.getPrevNode());
            }
            if (lastReturnedNode == head) {
                head = lastReturnedNode.getNextNode();
            } else {
                lastReturnedNode.getPrevNode().setNextNode(lastReturnedNode.getNextNode());
            }
            if (lastReturnedNode == nextNode) { // last move was previous and just removed nextNode
                nextNode = nextNode.getNextNode();
            } else { // last move was next

            }
            lastReturnedNode = null;
        }

        @Override
        public void set(T e) {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!canSet) {
                throw new IllegalStateException();
            }
            canSet = false;
            // Now we can set
            lastReturnedNode.setElement(e);
            iterModCount++;
        }

        @Override
        public void add(T e) {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            // Insert new node immediately before nextNode (the element that would be
            // returned
            // by a subsequent call to next()). If nextNode is null, insert at end.
            Node<T> newNode = new Node<T>(e);

            if (head == null) { // empty list
                head = newNode;
                tail = newNode;
            } else if (nextNode == head) { // insert before head
                newNode.setNextNode(head);
                head.setPrevNode(newNode);
                head = newNode;
            } else if (nextNode == null) { // insert at end (after tail)
                tail.setNextNode(newNode);
                newNode.setPrevNode(tail);
                tail = newNode;
            } else { // insert between prev and nextNode
                Node<T> prev = nextNode.getPrevNode();
                prev.setNextNode(newNode);
                newNode.setPrevNode(prev);
                newNode.setNextNode(nextNode);
                nextNode.setPrevNode(newNode);
            }

            size++;
            modCount++;
            iterModCount = modCount;

            nextIndex++;
            lastReturnedNode = null;
            canRemove = false;
            canSet = false;
        }

    }

}