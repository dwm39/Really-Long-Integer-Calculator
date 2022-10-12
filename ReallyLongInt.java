// This is a subclass of ArrayDS that only takes integers
// Purpose is to add subtract and compare really long integers

public class ReallyLongInt extends ArrayDS<Integer> implements Comparable<ReallyLongInt> {
	// Instance variables are inherited. You may not add any new instance variables
	private static final Integer[] DIGITS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	private ReallyLongInt(int size) {

		super(DIGITS);
		for (int i = 0; i < size; i++) {
			append(0);
		}
	}

	/**
	 * @param s a string representing the integer (e.g., "123456") with no leading
	 *          zeros except for the special case "0".
	 *          Note that we are adding the digits here to the END. This results in
	 *          the
	 *          MOST significant digit first. It is assumed that String s is a valid
	 *          representation of an
	 *          unsigned integer with no leading zeros.
	 * @throws NumberFormatException if s contains a non-digit
	 */
	public ReallyLongInt(String s) {
		super(DIGITS);
		char c;
		Integer digit;
		// Iterate through the String, getting each character and converting it into
		// an int. Then add at the end. Note that
		// the append() method (from ArrayDS) adds at the end.
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9')) {
				digit = c - '0';
				append(digit);
			} else
				throw new NumberFormatException("Illegal digit " + c);
		}
	}

	/**
	 * Simple call to super to copy the items from the argument ReallyLongInt into a
	 * new one.
	 * 
	 * @param rightOp the object to copy
	 */
	public ReallyLongInt(ReallyLongInt rightOp) {
		super(rightOp);
	}

	
	/**
	 *  Adding a number from a ReallyLongInt
	 * 
	 * @param rightOp the number that is being added from ReallyLongInt
	 * @return Returning a new ReallyLongInt that is the sum of ReallyLongInt and rightOp
	 **/
	public ReallyLongInt add(ReallyLongInt rightOp) {
		Integer holderCarry = 0; // The number that will carry over to the next addition index
		ReallyLongInt temp = new ReallyLongInt(rightOp); // converting rightOp into temp variable into temp so it does
															// not effect actual variable
		ReallyLongInt finalLongInt = new ReallyLongInt(0);
		if (size() != temp.size()) { // Making the sizes of the array equal to each other by prefixing zeroes
			while (size() > temp.size()) {
				temp.prefix(0);
			}
			while (size() < rightOp.size()) {
				prefix(0);
			}
		}
		Integer adder = 0;
		Integer finalInt = 0;
		/// try to prefix the zero until they are the same size either way
		if (size() == temp.size()) {
			for (int i = size() - 1; i >= 0; i--) { // Looping through the array backwards
				finalInt = itemAt(i) + temp.itemAt(i) + holderCarry; // Adding both items
				String holder = String.valueOf(finalInt); // Converting number into string to access the different
															// values
				char firstIndex = holder.charAt(0);
				char secondIndex = '0';
				if (holder.length() > 1) { // If the number returns a number bigger than 9 then we need to edit
											// holderCarry
					secondIndex = holder.charAt(1);
					firstIndex = holder.charAt(0);
					adder = Integer.parseInt(String.valueOf(secondIndex));
					holderCarry = Integer.parseInt(String.valueOf(firstIndex));
				} else { // In this case we set holderCarry to 0 and only take the first index
					holderCarry = 0;
					adder = Integer.parseInt(String.valueOf(firstIndex));
				}
				finalLongInt.prefix(adder);
				if (i == 0) { // Prefixing the last number incase more holder carries over to the last digit
					finalLongInt.prefix(holderCarry);
				}
			}
		}
		removeZeros();
		finalLongInt.removeZeros();
		return finalLongInt;
	}
	
	/**
	 *  Subtracting a number from a ReallyLongInt
	 * 
	 * @param rightOp the number that is being subtracted from ReallyLongInt
	 * @return Returning a new ReallyLongInt that is the difference of ReallyLongInt and rightOp
	 * @throws ArithmeticException if number will be negtive after subtraction
	 **/
	public ReallyLongInt subtract(ReallyLongInt rightOp) {
		if (compareTo(rightOp) == -1) { // Throwing exception if subtracting bigger number
			throw new ArithmeticException("Invalid Difference -- Negative Number");
		}
		Integer holderCarry = 0; // Becomes -1 if we need to add 10 to a number to be subtractable
		ReallyLongInt temp = new ReallyLongInt(rightOp);
		ReallyLongInt finalLongInt = new ReallyLongInt(0);
		if (size() > temp.size()) { // prefixing 0 so the indexes point correctly
			while (size() > temp.size()) {
				temp.prefix(0);
			}
		}

		Integer adder = 0; // The new number after we borrow a one from the previous number
		Integer prefixer = 0;
		/// try to prefix the zero until they are the same size either way
		if (size() == temp.size()) {
			for (int i = size() - 1; i >= 0; i--) { // Looping through the array
				if (itemAt(i) + holderCarry < temp.itemAt(i)) {
					adder = 10 + itemAt(i) + holderCarry; // Adding 10 to the number and possibly subtracting 1 if 1 was
															// removed in the previous index subtraction
					prefixer = adder - temp.itemAt(i); // The actuall item prefixed
					if (itemAt(i - 1) != null) { // If the item is not at the beginning of the array then set
													// holderCarry to -1
						holderCarry = -1;
					}
				} else {
					prefixer = holderCarry + itemAt(i) - temp.itemAt(i); // We can subtract normally then set
																			// holderCarry to 0 since we did not borrow
					holderCarry = 0;
				}
				finalLongInt.prefix(prefixer);
			}
		}
		finalLongInt.removeZeros();
		return finalLongInt;
	}

	public ReallyLongInt subtract(ReallyLongInt first, ReallyLongInt second) {
		return null;
	}

	/**
	 *  Seeing if the number is greater to, equal, or less than the item printed
	 * 
	 * @param rOp the number that is the ReallyLongInt is being compared to
	 * @return Returning 1 if larger than rOp, 0 if equal, or -1 if smaller
	 **/
	public int compareTo(ReallyLongInt rOp) {
		if (size() < rOp.size()) {
			return -1;
		}
		if (size() > rOp.size()) {
			return 1;
		}
		if (equals(rOp)) {
			return 0;
		} else {
			int i = 0;
			while (i < size()) { // Looping through the array until we find a differnce in the numbers
				if (itemAt(i) == rOp.itemAt(i)) {// If both numbers are the same go to the next index
					i++;
				} else if (itemAt(i) < rOp.itemAt(i)) {
					return -1;
				} else if (itemAt(i) > rOp.itemAt(i)) {
					return 1;
				}
			}
		}
		return 0;
	}
	/**
	 * Tests if the number inputted is equal to the number inputted.
	 * 
	 * @param rightOp the number that the ReallyLongInt is being compared to
	 * @return true or false based based on if the two number are equal
	 **/
	public boolean equals(Object rightOp) {
		ReallyLongInt temp = (ReallyLongInt) rightOp;
		int counter = 0;
		if (size() != temp.size()) { // If sizes do not equal eachother than they cannot equal eachother
			return false;
		} else {
			for (int i = 0; i < size(); i++) { // checking if each item equals the inputted item and if so add 1 to
												// counter
				if (itemAt(i) == temp.itemAt(i)) {
					counter++;
				}
			}
		}
		if (counter == size()) { // The only way this is true is if everysingle item at every position was the
									// same in both
			return true;
		} else {
			return false;
		}
	}

	/**
	 *  Multiplies the ReallyLongInt by 10^num power
	 * 
	 * @param num the number that is the power of 10
	 * @return the result of the number multiplied by 10 to the number inputted
	 **/
	public ReallyLongInt multTenToThe(int num) {
		ReallyLongInt temp = new ReallyLongInt(0); // Copying all of the items into a temp variable
		for (int i = 0; i < size(); i++) {
			temp.append(itemAt(i));
		}
		for (int i = 0; i < num; i++) { // Adding 0 to the end based on the number inputted
			temp.append(0);
		}
		return temp;
	}

	/**
	 * Divides the ReallyLongInt by 10^num power
	 * 
	 * @param num the number that is the power of 10
	 * @return the result of the number divided by 10 to the number inputted
	 **/
	public ReallyLongInt divTenToThe(int num) {
		ReallyLongInt temp = new ReallyLongInt(0);
		for (int i = 0; i < size(); i++) { // Copying all the items
			temp.append(itemAt(i));
		}
		for (int i = 0; i < num; i++) { // Deletailing the tail x number of times based on num
			temp.deleteTail();
		}
		return temp;
	}

	/**
	 * Remove leading zeros.
	 *
	 */
	private void removeZeros() {
		if (getFrequencyOf(0) == size()) { // Incase the number itself is zero I make sure that the zero stays
			if (first() == 0) {
				clear();
				append(0);
			}
		} else { // Deleting the front of the sequence until the first item is not zero
			while (first() == 0) {
				deleteHead();
			}
		}
	}
}
