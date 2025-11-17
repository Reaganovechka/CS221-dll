****************
* Double Linked List (DLL)
* CS221
* November 17 2025
* Reagan Ovechka
**************** 

OVERVIEW:

 DLL is a double linked list implementation of the IndexedUnsortedList interface. In this program I fully implemented the usage of a double linked list through the IUDoubleLinkedList class, also creating a fully functioning iterator that implements the ListIterator interface.
 Utilizing a test suite ensures the correct funtionality of the IUDoubleLinkedList class.


INCLUDED FILES:

 List the files required for the project with a brief
 explanation of why each is included.

 e.g.
 * ListTester.java - source file
 * IUDoubleLinkedList.java - source file
 * IndexedUnsortedList.java
 * GoodList.java
 * BadList.java
 * IUSingleLinkedList.java
 * README - this file


COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac ListTester.java

 Run the compiled class file with the command:
 $ java ListTester

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 This is the sort of information someone who really wants to
 understand your program - possibly to make future enhancements -
 would want to know.

 Explain the main concepts and organization of your program so that
 the reader can understand how your program works. This is not a repeat
 of javadoc comments or an exhaustive listing of all methods, but an
 explanation of the critical algorithms and object interactions that make
 up the program.

 Explain the main responsibilities of the classes and interfaces that make
 up the program. Explain how the classes work together to achieve the program
 goals. If there are critical algorithms that a user should understand, 
 explain them as well.
 
 If you were responsible for designing the program's classes and choosing
 how they work together, why did you design the program this way? What, if 
 anything, could be improved? 

TESTING:

 Utilizing the test suit, ListTester.java, helps to ensure the funtionality of each method quickly and efficiently. The main source of testing stemmed from this class by running the test suite and reading the output to decipher which aspects of the IUDoubleLinkedList class executed successfully or not. Tests were ran for lists containing 0-3 elements, by testing 0-3 elements we can be confident that the program will work for lists of any length. Testing iterator fucntionality is necessary as well, and there is specific testing for each iterator method. In the iterator testing, it is important to test methods after multiple previous calls, ensuring mutliple calls does not break the program for the future. 
 In the case that a test for a specific method was failing, it was clear where to start the debugging process. From there, it is easy to find which test was failing, and if needed, utilizing the Debugger to further investigate the bug.


DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
