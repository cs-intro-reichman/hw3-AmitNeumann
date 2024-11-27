// Implements algebraic operations and the square root function without using 
// the Java operations a + b, a - b, a * b, a / b, a % b, and without calling 
// Math.sqrt. All the functions in this class operate on int values and
// return int values.

public class Algebra {
	public static void main(String args[]) {
	    // Tests some of the operations
	    System.out.println(plus(2,3));   // 2 + 3
	    System.out.println(minus(7,2));  // 7 - 2
   		System.out.println(minus(2,7));  // 2 - 7
 		System.out.println(times(3,4));  // 3 * 4
   		System.out.println(plus(2,times(4,2)));  // 2 + 4 * 2
   		System.out.println(pow(5,3));      // 5^3
   		System.out.println(pow(3,5));      // 3^5
   		System.out.println(div(12,3));   // 12 / 3    
   		System.out.println(div(5,5));    // 5 / 5  
   		System.out.println(div(25,7));   // 25 / 7
   		System.out.println(mod(25,7));   // 25 % 7
   		System.out.println(mod(120,6));  // 120 % 6    
   		System.out.println(sqrt(36));
		System.out.println(sqrt(263169));
   		System.out.println(sqrt(76123));
	}  

	// Returns x1 + x2
	public static int plus(int x1, int x2) {
		if(x2>=0) {
			int x3=x1;
			for (int i=0;i<x2;i++) {
				x3++;
			}
			return x3;
		} else { 
			int x3=x1;
			for (int i=0;i>x2;i--) {
				x3--;
			}
			return x3;
		}
	}

	// Returns x1 - x2
	public static int minus(int x1, int x2) {
		int x3;
		if(x1>=0&&x2>=0){ // both are positive
			x3=x1;
			for (int i=0;i<x2;i++) {
				x3--;
			}
		} else { // one of them is <0
			x3=x1;
			if (x2<0) { // -(-x2)== +x2
				for (int i=0;i>x2;i--) {
					x3++;
				}
			} else { // x1<0 && x2>0
				for(int i=0; i<x2;i++) {
					x3--;
				}
			}
			
		}
		return x3;
	}
		
	

	// Returns x1 * x2
	public static int times(int x1, int x2) {
		int n = x1, restartN = x1;

    // Handle cases where either number is zero
    if (x1 == 0 || x2 == 0) {
        return 0;
    } else {
        if (x1 < 0 && x2 < 0) { // Both x1 and x2 are negative
            int absX1 = 0, absX2 = 0;

            // Convert x1 to positive
            while (x1 != 0) {
                absX1++;
                x1++;
            }

            // Convert x2 to positive
            while (x2 != 0) {
                absX2++;
                x2++;
            }

            // Reset x1 and x2 to their absolute values
            x1 = absX1;
            x2 = absX2;
            n = x1;
            restartN = x1;
        }

        if (x1 > 0 && x2 > 0) { // Both x1 and x2 are positive
            for (int i = 1; i < x2; i++) {
                n = restartN;
                while (n != 0) {
                    x1++;
                    n--;
                }
            }
            return x1;
        } else { // At least one of x1 or x2 is negative
            if (x2 > 0) { // x2 is positive and x1 is negative
                for (int i = 1; i < x2; i++) {
                    n = restartN;
                    while (n != 0) {
                        x1--;
                        n++;
                    }
                }
                return x1;
            } else { // x2 is negative
                if (x1 > 0) { // x1 is positive and x2 is negative
                    n = x2;
                    restartN = x2;
                    for (int i = 1; i < x1; i++) {
                        n = restartN;
                        while (n != 0) {
                            x2--;
                            n++;
                        }
                    }
                    return x2;
                }
            }
        }
    }
    return x2;
}

	// Returns x^n (for n >= 0)
	public static int pow(int x, int n) {
	// Handle the case where the base (x) is 0
    if (x == 0) {
        if (n == 0) {
            throw new ArithmeticException("0^0 is undefined");
        }
        return 0; // 0 raised to any positive power is 0
    }

    // Handle the case where the exponent (n) is 0
    if (n == 0) {
        return 1; // Any number raised to the power of 0 is 1
    }

    // Handle negative exponents (returning integer approximation)
    boolean isNegativeExponent = false;
    if (n < 0) {
        n = -n; // Make exponent positive
        isNegativeExponent = true;
    }

    // Calculate power for positive exponents
    int sum = x;
    for (int i = 1; i < n; i++) {
        sum = times(sum, x); // Use the "times" method for multiplication
    }

    // If the exponent was negative, return 1 / (x^n)
    if (isNegativeExponent) {
        // Since we're working with integers, the result of 1 / (x^n) will always truncate to 0 for x^n > 1
        return 0; // Integer division doesn't allow fractional results
    }

    return sum;
}
	

	// Returns the integer part of x1 / x2 
	public static int div(int x1, int x2) {
		if (x2 == 0) {
			throw new ArithmeticException("Division by zero is undefined");
		}

		// Determine if the result should be negative
		boolean negativeResult = false;
		if (x1 < 0) {
			x1 = minus(0, x1); // Make a positive
			negativeResult = true;
		}
		if (x2 < 0) {
			x2 = minus(0, x2); // Make b positive
			if (negativeResult) {
				negativeResult = false; // Negative divided by negative gives positive
			} else {
				negativeResult = true; // Positive divided by negative gives negative
			}
		}
		//  division
		int result = 0;
		while (x1 >= x2) {
			x1 = minus(x1, x2);
			result = plus(result, 1);
		}
	
		// Return result with correct sign
		if (negativeResult) {
			return minus(0, result);
		} else {
			return result;
		}
	}

	// Returns x1 % x2
	public static int mod(int x1, int x2) {
		if (x2 == 0) {
			throw new ArithmeticException("Modulo by zero is undefined");
		}
		// Determine if the remainder should be negative
		boolean negativeResult = false;
		if (x1 < 0) {
			x1 = minus(0, x1); // Make x1 positive
			negativeResult = true;
		}
		if (x2 < 0) {
			x2 = minus(0, x2); // Make x2 positive
		}
		//modulo 
		while (x1 >= x2) {
			x1 = minus(x1, x2);
		}
	
		// Return remainder with correct sign
		if (negativeResult) {
			return minus(0, x1);
		} else {
			return x1;
		}
	}
	// Returns the integer part of sqrt(x) 
	public static int sqrt(int x) {
		if (x < 0) {
			throw new ArithmeticException("Square root of a negative number is undefined");
		}
		// Start finding the square root
		int result = 0;
		while (times(result, result) <= x) {
			result = plus(result, 1);
		}
		// -1 for the extra iteration
		return minus(result, 1);
	}	  	  
}