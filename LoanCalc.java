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
		double g = loan / n; // Initial guess
		double increment = 0.001;
		double balance = endBalance(loan, rate, n, g);
	
		int maxIterations = 1_000_000; // Safety limit
		while (Math.abs(balance) > epsilon && iterationCounter < maxIterations) {
			g += increment;
			balance = endBalance(loan, rate, n, g);
			iterationCounter++;
		}
	
		if (iterationCounter >= maxIterations) {
			System.out.println("BruteForceSolver: Max iterations reached. Exiting...");
		}
	
		return g;
	}
	
	
    
    // Uses bisection search to compute an approximation of the periodical payment 
    // that will bring the ending balance of a loan close to 0.
    // Given: the sum of the loan, the periodical interest rate (as a percentage),
    // the number of periods (n), and epsilon, the approximation's accuracy
    // Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
		double L = loan / n;
		double H = (loan * Math.pow(1 + rate, n)) / n;
		double g = (L + H) / 2;
		double balance = endBalance(loan, rate, n, g);
	
		int maxIterations = 1000; // Smaller limit for bisection
		while (Math.abs(balance) > epsilon && Math.abs(H - L) > epsilon && iterationCounter < maxIterations) {
			if (balance > 0) {
				L = g;
			} else {
				H = g;
			}
			g = (L + H) / 2;
			balance = endBalance(loan, rate, n, g);
			iterationCounter++;
		}
	
		if (iterationCounter >= maxIterations) {
			System.out.println("BisectionSolver: Max iterations reached. Exiting...");
		}
	
		return g;
	}
	
	
}

