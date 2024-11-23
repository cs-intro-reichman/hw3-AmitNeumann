// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {
    
    static double epsilon = 0.01;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations 
    
    // Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
    public static void main(String[] args) {        
        // Gets the loan data
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1])/100.0;
        int n = Integer.parseInt(args[2]);
        iterationCounter = 0;
        System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

        // Computes the periodical payment using brute force search
		iterationCounter=0;
        System.out.print("\nPeriodical payment, using brute force: ");
        System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));  
        System.out.println("number of iterations: " + iterationCounter);

        // Computes the periodical payment using bisection search
		iterationCounter=0;
        System.out.print("\nPeriodical payment, using bi-section search: ");
        System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
        System.out.println("number of iterations: " + iterationCounter);
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a percentage), the number of periods (n), and the periodical payment.
    private static double endBalance(double loan, double rate, int n, double payment) { 
        double balance=loan;
        for(int i=0;i<n;i++) {
            balance=(balance-payment)*(1+rate);
        }
        return balance;
    }
    
    // Uses sequential search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    // Given: the sum of the loan, the periodical interest rate (as a percentage),
    // the number of periods (n), and epsilon, the approximation's accuracy
    // Side effect: modifies the class variable iterationCounter.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		double g = loan / n; // Initial guess for payment
		double increment = 0.001; // Increment size
		double balance = endBalance(loan, rate, n, g); // Initial balance
	
		while (Math.abs(balance) > epsilon) { // Stop when balance is close to zero
			g += increment; // Increment the payment
			balance = endBalance(loan, rate, n, g); // Update balance
			iterationCounter++;
		}
	
		return g;
	}
	
    
    // Uses bisection search to compute an approximation of the periodical payment 
    // that will bring the ending balance of a loan close to 0.
    // Given: the sum of the loan, the periodical interest rate (as a percentage),
    // the number of periods (n), and epsilon, the approximation's accuracy
    // Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
		double L = loan / n; // Lower bound for payment
		double H = (loan * (1 + rate)) / n; // Upper bound for payment
		double g = (L + H) / 2; // Midpoint for bisection
		double balance = endBalance(loan, rate, n, g);
	
		// Loop until balance is close to zero or bounds converge
		while (Math.abs(balance) > epsilon && Math.abs(H - L) > epsilon) {
			if (balance > 0) {
				L = g; // Payment is too low; adjust lower bound
			} else {
				H = g; // Payment is too high; adjust upper bound
			}
	
			g = (L + H) / 2; // Update midpoint
			balance = endBalance(loan, rate, n, g); // Recalculate balance
			iterationCounter++;
		}
	
		return g;
	}
	
}

