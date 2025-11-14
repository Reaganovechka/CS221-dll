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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addToRear'");
        // ListIterator<T> lit = listIterator(size);
        // lit.add(element);
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        ListIterator<T> lit = listIterator();
        boolean foundIt = false;
        while (lit.hasNext() && lit.next().equals(target)) {
            if (lit.next().equals(target)) {
                lit.add(element);
                foundIt = true;
            } // else, nothing

        }
        if (!foundIt) {
            throw new NoSuchElementException();
        }
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


            //ListIterator<T> lit = listIterator(index);
            //lit.add(element);
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
        // if (head == tail) { // Single element list
        // if (index == 0) {
        // head.setPrevNode(newNode);
        // newNode.setNextNode(head);
        // head = newNode;
        // } else {
        // tail.setNextNode(newNode);
        // newNode.setPrevNode(tail);
        // tail = newNode;
        // }
        // // } else if (priorNode == tail) { // End of the list
        // tail.setNextNode(newNode);
        // newNode.setPrevNode(tail);
        // tail = newNode;
        // }

        // size++;
        // modCount++;
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
        if (size == 1) { // or tail == null
            head = null; // removed only element
        } else {
            tail.setNextNode(null);
        }

        size--;
        modCount++;
        return retVal;
        //ListIterator<T> lit = listIterator(size);
        //if(!lit.hasPrevious()){
        //  throw new NoSuchElementException();
        //}

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
        // if (index < 0 || index >= size) {
        //     throw new IndexOutOfBoundsException();
        // }
        // Node<T> currentNode = head;
        // for (int i = 0; i < index; i++) {
        //     currentNode = currentNode.getNextNode();
        // }
        // return currentNode.getElement();
        if (index == size){
            throw new IndexOutOfBoundsException();
        }
        ListIterator<T> lit = listIterator(index);
        return lit.next();
    }

    @Override
    public int indexOf(T element) {
        // int index = 0;
        // Node<T> currentNode = head; // Start at the beginning, head is the first node we ill look at
        // while (currentNode != null && !element.equals(currentNode.getElement())) { // either I found it or i didnt
        //     currentNode = currentNode.getNextNode();
        //     index++;
        // }
        // if (currentNode == null) { // Did not find it, OR index >= size()
        //     index = -1;
        // }
        int index = -1;
        int currentIndex = 0;
        ListIterator<T> lit = listIterator();
        while(index < 0 && lit.hasNext()){
            if (lit.next().equals(element)) {
                index = currentIndex;
            }
            currentIndex ++;
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
        private Node<T> lastReturnedNode;
        private int iterModCount;

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
            if (startingIndex < 0 || startingIndex >= size) {
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
            iterModCount = modCount;
            lastReturnedNode = null;

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
            return nextNode.getElement();
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
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }

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