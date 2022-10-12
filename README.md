# CS 0445 – Algorithms and Data Structures 1 – Assignment#1 [^1]

## OVERVIEW

**Purpose:** To refresh your Java programming skills, to emphasize the object-oriented programming
approach used in Java, to practice working with Java single- and two-dimensional arrays, and to use the ADT Bag. Specifically, you will work with control
structures, class-building, interfaces and generics to **create** and **utilize** a two-dimensional-array-based
data structure.

- **Task 1:** To design and implement a generic class `ArrayDS<T>` that will act as a data structure for accessing
sequences of Java Objects. Your `ArrayDS<T>` class will primarily implement 2 interfaces –
`SequenceInterface<T>` and `ReorderInterface`. The details of these interfaces are explained in the files
`SequenceInterface.java` and `ReorderInterface.java`. **Read these files over very carefully before implementing
your `ArrayDS<T>` class.**

- **Task 2:** To utilize your `ArrayDS<T>` class to store and manipulate arbitrary length **integers**. We can think of an integer as a sequence of decimal digits. For example, the number `1234` could be stored as the digit
'1' followed by the digit '2' followed by the digit '3' followed by the digit '4'. We will store these digits in
an `ArrayDS` object. Clearly, to perform operations on a number that is stored in this fashion, we must
access the digits one at a time in some systematic way. More specific details follow below.

