public class ArrayDS<T> implements SequenceInterface<T>, ReorderInterface {

    private final BagInterface<Integer>[][] array;
    private int size;
    private T[] alphabet;
    private T firstItem;
    private T lastItem;

    // Constructs a new ArrayDS with an underlying 2d Array with the input size of
    // Alphabet
    // Aloocate each space in the array with an empty bag
    public ArrayDS(T[] alphabet) {
        this.alphabet = alphabet;
        @SuppressWarnings("unchecked")
        BagInterface<Integer>[][] temp = new ResizableArrayBag[alphabet.length][alphabet.length]; // temporary variable
                                                                                                  // to stop the supress
                                                                                                  // warning
        array = temp;
        size = 0;
        firstItem = null;
        lastItem = null;
        for (int row = 0; row < array.length; row++) { // making an empty bag in each section of the array
            for (int col = 0; col < array[row].length; col++) {
                array[row][col] = new ResizableArrayBag<Integer>();
            }
        }
    }

    // Copy contructor of ArrayDS
    // Makes a new ArrayDS identical to the one inputed
    public ArrayDS(ArrayDS<T> other) {
        alphabet = other.alphabet;
        @SuppressWarnings("unchecked")
        BagInterface<Integer>[][] temp = new ResizableArrayBag[alphabet.length][alphabet.length];
        for (int row = 0; row < other.array.length; row++) { // Looping over the array inputed and making every input in
                                                             // the array the same
            for (int col = 0; col < other.array[row].length; col++) {
                temp[row][col] = other.array[row][col];
            }
        }
        array = temp;
        size = other.size;
        firstItem = other.firstItem;
        lastItem = other.lastItem;
    }

    /**
     * @return a String that contains the logical sequence
     */
    @Override
    public String toString() {
        String finalString = "";
        for (int i = 0; i < size(); i++) { // Looping over the size of the Array and finding the item at each position
                                           // in the array.
            finalString += itemAt(i);
        }

        return finalString;
    }

    // Taking the logical sequence and and reversing it
    @Override
    public void reverse() {
        for (int i = size - 1; i >= 0; i--) { // Appending all of the item in reverse order starting at the item before
                                              // the last one
            append(itemAt(i));
        }
        for (int i = (size) / 2; i > 0; i--) { // Deleting helf of the array starting at teh beginning because the array
                                               // should be double the size
            deleteHead();
        }

    }

    // Making the last item first and deleting the last item
    @Override
    public void rotateRight() {
        prefix(lastItem);
        deleteTail();

    }

    // Making the first item last and deleting the first item
    @Override
    public void rotateLeft() {
        append(firstItem);
        deleteHead();

    }

    /**
     * Add a new Object to the logical end of the SequenceInterface<T>
     * 
     * @param item the item to be added.
     */
    @Override
    public void append(T item) {
        if (isEmpty()) { // If the sequence is empty then set the first and last item equal to the
                         // inputed item

            size++;
            firstItem = item;
            lastItem = item;
        } else {
            array[indexInAlphabet(lastItem)][indexInAlphabet(item)].add(size); // Adding the item to the next position
            size++;
            lastItem = item;
        }

    }

    /**
     * Add a new Object to the logical start of the SequenceInterface<T>
     * 
     * @param item the item to be added.
     */
    @Override
    public void prefix(T item) {
        if (isEmpty()) { // If the sequence is empty then set the first and last item equal to the
                         // inputed item
            size++;
            firstItem = item;
            lastItem = item;
        } else {
            // Removing every position from the bag and adding one to shift the sequence
            // over by one
            for (int row = 0; row < array.length; row++) { // Looping through the array
                for (int col = 0; col < array[row].length; col++) {
                    if (array[row][col] != null) {
                        BagInterface<Integer> keep = new ResizableArrayBag<Integer>(); // Making a new bag to hold
                                                                                       // removed items
                        int keeper = 0;
                        while (!array[row][col].isEmpty()) { // removes all of the items from the bag and adds them to
                                                             // the temporary bag
                            keeper = array[row][col].remove();
                            keep.add(keeper);
                        }

                        while (!keep.isEmpty()) { // Removing the items from the temporary bag and adding them back to
                                                  // the actual bag with a +1 value
                            int value = keep.remove();
                            array[row][col].add(value + 1);

                        }
                    }
                }
            }
            array[indexInAlphabet(item)][indexInAlphabet(firstItem)].add(1); // Adding the item at the begging of the
                                                                             // sequence
            size++;
            firstItem = item;
        }

    }

    /**
     * Delete the item at the logical start of the SequenceInterface<T>
     * 
     * @return the deleted item
     * @throws EmptySequenceException if the sequence is empty
     */
    @Override
    public T deleteHead() {
        if (isEmpty()) { // Throwing exception if empty
            throw new EmptySequenceException("ArrayDS is Empty");
        }
        T deletedItem = null;
        if (size == 1) { // If the size is one then completely clearing the array
            deletedItem = firstItem;
            firstItem = null;
            lastItem = null;
            size--;
        }
        for (int row = 0; row < array.length; row++) { // Looping through the array and finding where it contains 1
            for (int col = 0; col < array[row].length; col++) {
                if (array[row][col].contains(1)) {
                    deletedItem = firstItem;
                    firstItem = itemAt(1); // setting the first item equal to the second item
                    array[row][col].remove(1); // deleting the first item
                    size--;

                }
            }
        }
        // looping through the array and subracting all of the values in the array by 1
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (array[row][col] != null) {
                    BagInterface<Integer> keep = new ResizableArrayBag<Integer>();
                    int keeper = 0;
                    while (!array[row][col].isEmpty()) { // removes all of the items from the bag and adds them to the
                                                         // temporary bag
                        keeper = array[row][col].remove();
                        keep.add(keeper);
                    }

                    while (!keep.isEmpty()) { // Removing the items from the temporary bag and adding them back to the
                                              // actual bag with a -1 value
                        int value = keep.remove();
                        array[row][col].add(value - 1);

                    }
                }
            }
        }
        return deletedItem;
    }

    /**
     * Delete the item at the logical end of the SequenceInterface<T>
     * 
     * @return the deleted item
     * @throws EmptySequenceException if the sequence is empty
     */
    @Override
    public T deleteTail() {
        T deletedItem = null;
        int holder = size;
        if (isEmpty()) { // If empty throw an exception
            throw new EmptySequenceException("ArrayDS is Empty");
        }
        if (size == 1) { // if the size is one then clear the array
            deletedItem = firstItem;
            firstItem = null;
            lastItem = null;
            size--;
        } else {
            for (int row = 0; row < array.length; row++) { // Loops through the array and finds the last item
                for (int col = 0; col < array[row].length; col++) {
                    if (array[row][col].contains(holder - 1)) {
                        deletedItem = itemAt(holder - 1);
                        array[row][col].remove(holder - 1); // Removing the last item
                        lastItem = itemAt(holder - 2); // Setting last item equal to the second to last item
                        size--;

                    }
                }
            }
        }

        return deletedItem; // Returning the deleted item
    }

    /**
     * @return true if the SequenceInterface is empty, and false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * @return the number of items currently in the SequenceInterface
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return the the logically first item in the sequence
     */
    @Override
    public T first() {
        return firstItem;
    }

    /**
     * @return the the logically last item in the sequence
     */
    @Override
    public T last() {
        return lastItem;
    }

    /**
     * Checks if a given sequence is a subsequence of this sequence
     * 
     * @param another the sequence to check
     * @return true if another is a subsequence of this sequence or false otherwise
     */
    @Override
    public boolean hasSubsequence(SequenceInterface<T> another) {
        String s1 = toString(); // Turning both ArraysDS's into strings
        String other = another.toString();
        return s1.indexOf(other) != -1; // Seeing if the array ds string is a subset of the original
    }

    /**
     * Check if a given item comes right before another given item in the sequence
     * 
     * @param first  an item
     * @param second another item
     * @return true if first comes right before second in the sequence, or false
     *         otherwise
     */
    @Override
    public boolean predecessor(T first, T second) {
        if (array[indexInAlphabet(first)][indexInAlphabet(second)].isEmpty()) {// Testing if the space is empty
            return false;
        }

        else {
            return true;
        }

    }

    /**
     * Return the number of occurences in the sequence of a given item
     * 
     * @param item an item
     * @return the number of occurences in the sequence of item
     */
    @Override
    public int getFrequencyOf(T item) {
        int count = 0;
        for (int row = 0; row < array.length; row++) { // Looping through the array and seeing if there is the item
                                                       // there
            for (int col = 0; col < array[row].length; col++) {
                if (count == 0 && firstItem == item) {
                    count++;
                }
                if (col == indexInAlphabet(item) && array[row][col] != null) {
                    int adder = array[row][col].getCurrentSize();
                    count += adder;
                }
            }
        }
        return count;
    }

    /**
     * Reset the SequenceInterface to empty status by reinitializing the variables
     * appropriately
     */
    @Override
    public void clear() {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                array[row][col] = new ResizableArrayBag<Integer>(); // Reseting each bag in the Array
            }
        }
        firstItem = null;
        lastItem = null;
        size = 0;
    }

    /**
     * Return the item at a given logical position
     * 
     * @param position the logical position starting from 0
     * @return the item at logical position position
     * @throws IndexOutOfBoundsException if position < 0
     *                                   or position > size()-1
     */
    @Override
    public T itemAt(int position) {
        if (position < 0 || position > size() - 1) {
            throw new IndexOutOfBoundsException("This position does not exist");
        }
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (array[row][col].contains(position)) { // testing the bag contains the position and if so returning
                                                          // the column
                    return alphabet[col];
                }
                if (position == 0) {
                    return firstItem;
                }
            }
        }
        return null;
    }

    /**
     * Return an array containing the items in the sequence in their logical order
     * 
     * @return the an array containing the items in the sequence in their logical
     *         order
     *         or null if the sequence is empty
     */
    @Override
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] finalArray = (T[]) new Object[size];
        for (int i = 0; i < size(); i++) {
            finalArray[i] = itemAt(i); // Setting the array equal to the item at each position
        }

        return finalArray;
    }

    /**
     * Return the logical position in the sequence of the first occurrence of a
     * given item
     * 
     * @param item an item
     * @return the logical position in the sequence of the first occurrence of item
     *         or -1 if the item doesn't exist
     */
    @Override
    public int firstOccurrenceOf(T item) {
        int position = -1;
        if (item == firstItem) { // Exception case
            position = 0;
        } else {
            for (int tester = 0; tester < size; tester++) {
                for (int row = 0; row < array.length; row++) {
                    for (int col = 0; col < array[row].length; col++) { // Looping through each posible position and
                                                                        // seeing
                        // if it is in there
                        if (position != -1) {
                            break;
                        }
                        if (col == indexInAlphabet(item)) { // if the item is in the bag and if it contains the lowest
                                                            // posible value return that value
                            if (array[row][col].contains(tester)) {
                                position = tester;
                                break;
                            }
                        }
                    }

                }

            }
        }
        return position; // Thats my old method that uses a basic array which i find easier but it
                         // specifies that no arrays are allowed in this method

        /**
         * T[] tester = toArray(); // Making an array to test when the item first occurs
         * int position = -1;
         * for (int i = 0; i < tester.length; i++) {
         * if (tester[i] == item) { // Seeing if the item in the array equals the item
         * inputted
         * position = i;
         * break;
         * }
         * }
         * return position;
         */
    }

    /**
     * Return the index of a given item in the alphabet of the sequence
     * 
     * @param item an item
     * @return the index of item in the alphabet of the sequence
     *         or -1 if the item doesn't exist
     */
    @Override
    public int indexInAlphabet(T item) {
        int index = -1;
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].equals(item)) {
                index = i;
            }

        }
        return index;
    }

    /**
     * Return the index in the alphabet of the next item in the sequence
     * after the occurrence of item at position
     * 
     * @param item     an item
     * @param position item's position
     * @return the index in the alphabet of the item after the occurrence of item
     *         at position
     *         or -1 if the next item doesn't exist
     */
    @Override
    public int nextIndex(T item, int position) {
        int finalInt = -1;
        if (position > size || position < 0) {
            return -1;
        }
        if (itemAt(position) != item) { // Testing is there is indeed the item inputed at that position
            return -1;
        } else {
            for (int row = 0; row < array.length; row++) {
                for (int col = 0; col < array[row].length; col++) {
                    if (array[row][col].contains(position + 1)) { // Seeing what the item is at the next position
                        finalInt = indexInAlphabet(itemAt(position + 1));
                    }
                }
            }
        }

        return finalInt;
    }

    /**
     * Return the index in the alphabet of the previous item in the sequence
     * before the occurrence of item at position
     * 
     * @param item     an item
     * @param position item's position
     * @return the index in the alphabet of the item before the occurrence of item
     *         at position
     *         or -1 if the previous item doesn't exist
     */
    @Override
    public int prevIndex(T item, int position) {
        int finalInt = -1;
        if (position > size || position < 0) {
            return -1;
        }
        if (itemAt(position) != item) {
            return -1;
        } else {
            for (int row = 0; row < array.length; row++) {
                for (int col = 0; col < array[row].length; col++) {
                    if (array[row][col].contains(position - 1)) { // Seeing what the item is at the previous position
                        finalInt = indexInAlphabet(itemAt(position - 1));
                    }
                }
            }
        }

        return finalInt;
    }

    // Method to print out the 2d Array
    public void print() {
        for (int row = 0; row < array.length; row++) {
            System.out.print(alphabet[row]);
            for (int col = 0; col < array[row].length; col++) {
                System.out.print(array[row][col] + " ");
            }
            System.out.println(" ");
        }
    }

}
