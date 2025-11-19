****************
* Double Linked List (DLL)
* CS221
* November 17 2025
* Reagan Ovechka
**************** 

OVERVIEW:

 DLL is a doubly linked list implementation of the IndexedUnsortedList interface. In this program I fully implemented the usage of a double linked list through the IUDoubleLinkedList class, also creating a fully functioning iterator located in the same class that implements the ListIterator interface.
 Utilizing a test suite ensures the correct funtionality of the IUDoubleLinkedList class, including scenarios that extensively test each method and iterator functionality of lists length 0-3.


INCLUDED FILES:

 List the files required for the project with a brief
 explanation of why each is included.

 e.g.
 * ListTester.java - source file/driver class for testing
 * IUDoubleLinkedList.java - source file
 * IndexedUnsortedList.java - List interface
 * GoodList.java - source file
 * BadList.java - source file
 * IUSingleLinkedList.java - source file
 * Node.java - Node structure for linked lists
 * README - this file


COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac ListTester.java

 Run the compiled class file with the command:
 $ java ListTester

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 This program utilized a doubly linked list with a fully funtioning iterator to navigate forward and backward in a list. The implementation of IndexedUnsortedList provides methods that allow for navigation and change of a list, including adding, removing, and setting values. The iterator is an implementation of ListIterator that is included in the file of IUDoubleLinkedList.java at the bottom, this class is included in this file which allows IUDoubleLinkedList and DLLIterator to access and call each others methods.

 IUDoubleLinkedList is the class that holds all the methods of navigating and changing the list. It is important to know that lists are only made up of the elements it points to, they are in consecutive order with no empty nodes in between, new nodes can be added anywhere within the list as long as it is within the bounds. For iterator funtionality it is important to know that you can add, remove, set, and get the previous and next node, and the previous and next index. Like a doubly linked list, the DDLIterators can navigate two-ways in a list, making it more efficient. Within the ListTester class, it is important to test a variety of scenarios to increase confidence in the functionality of both IUDoubleLinkedList and the DLLIterator classes. Tests include making sure your program is not broken after invoking a change, as well as being able to invoke said changes using both the iterator and standard methods. Testing 0-3 element lists gives high confidence that the program will successfully run for short and long lists.
 
 The main classes used in this specific assignment are DLLIterator, IUDoubleLinkedList, and ListTester. DLLIterator is included in the IUDoubleLinkedList class, which allows for the iterator to be used in any methods of IUDoubleLinkedList, as well as access its instance variables and communicate with the class. ListTester accesses these classes by invoking the methods to ensure they are working as expected on a variety of different lists and scenarios.
 
 It is important that the DLLIterator class was included in IUDoubleLinkedList so that way the two classes can communicate with each others instance variables and easily envoke the iterator methods, creating a new DLLIterator when ListIterator is called. Additionally, allowing DLLIterator and IUDoubleLinked list to access each others instance variables was vital for ensuring there was no ConcurrentModificationException() by checking if iterModCount and modCount were the same.

TESTING:

 Utilizing the test suit, ListTester.java, helps to ensure the funtionality of each method quickly and efficiently. The main source of testing stemmed from this class by running the test suite and reading the output to decipher which aspects of the IUDoubleLinkedList class executed successfully or not. Tests were ran for lists containing 0-3 elements, by testing 0-3 elements we can be confident that the program will work for lists of any length. Testing iterator fucntionality is necessary as well, and there is specific testing for each iterator method. In the iterator testing, it is important to test methods after multiple previous calls, ensuring mutliple calls does not break the program for the future. 

 In the case that a test for a specific method was failing, it was clear where to start the debugging process. From there, it is easy to find which test was failing, and if needed, utilizing the Debugger to further investigate the bug.

 For example, one issue that I ran into when testing my program was I was failing a single test for one of the scenarios while testing the add(index) method. When adding to the end of the list, I was accidentally linking newNode back to itself instead of the currentNode/tail. It was super quick to identify this by reading the tester output. From there I was able to set a breakpoint right before the scenario and step through and over each line of code to find where the bug was. 


DISCUSSION:
 
 Like any programming process, there were many bugs and issues encountered while testing. I will discuss a few prominant bugs I ran into, and how I fixed them. The first significant issue that I ran into was with my DLLIterator constructor. I was failing tests for the ListIterator on an empty list, and I was not unsure why. To fix this, I walked through my DLLIterator constructor with an empty list with a whiteboard and walking through the contructor step by step. Very quickly I learned that my conditions for checking if the startind index was valid was incorrect. It was not allowing an iterator to be placed at the end of the list, or starting index = size, when that should be a valid index. I quickly changed the condition check to allow for any starting index equal to the size ( if (startingIndex < 0 || startingIndex >= size) => if (startingIndex < 0 || startingIndex > size) ).

 Another bug was getting a NullPointerException in my removeFirst() method because in the return statement I was returning the value of FirstNode.getElement(), which was an issue because firstNode was set to null at some point in this method. I fixed this issue by walking through the debugger and finding out the issue was stemming from the return value, it was returning null instead of the expected value. This was a simple fix and I added a variable for retVal to store the initial value of the first element before setting it to null.

 What really "clicked" for me in this process was the concept of navigating through the list. Utilizing currentNode with the .getNextNode() and .getPrevNode() methods made it super easy to locate the target node and execute any changes needed to be made. I also gained confidence and ease in writing methods, specifically in logically planning them out, I was able to get into a confident flow where I would sketch out a picture on a whiteboard and make sure what I was writing was actually working in practice. This led to less buggy code and more understanding of planning and writing my code.



