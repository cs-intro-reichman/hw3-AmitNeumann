// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {
    
    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations 
    
    public static void main(String[] args) {        
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1]) / 100.0;
        int n = Integer.parseInt(args[2]);
        iterationCounter = 0;
        System.out.println("Loan = " + loan + ", interest rate = " + (rate * 100.0) + "%, periods = " + n);

        // Brute Force Solver
        iterationCounter = 0;
        System.out.print("\nPeriodical payment, using brute force: ");
        System.out.println((int) Math.round(bruteForceSolver(loan, rate, n, epsilon)));  
        System.out.println("number of iterations: " + iterationCounter);

        // Bisection Solver
        iterationCounter = 0;
        System.out.print("\nPeriodical payment, using bi-section search: ");
        System.out.println((int) Math.round(bisectionSolver(loan, rate, n, epsilon)));  
        System.out.println("number of iterations: " + iterationCounter);
    }

    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = (balance - payment) * (1 + rate); 
        }
        return balance;
    }
    
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        double payment = loan / n;
        double endAmount = endBalance(loan, rate, n, payment);
        while (endAmount > 0) {
            payment += epsilon;
            endAmount = endBalance(loan, rate, n, payment);
            iterationCounter++;
        }
        return Math.round(payment); // Use rounding for consistency
    }

    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        double L = loan / n; 
        double H = (loan * Math.pow(1 + rate, n)) / n; 
        double g = (L + H) / 2; 
        double balance = endBalance(loan, rate, n, g);

        iterationCounter = 0;

        // Adjusted stopping condition for stricter precision
        while (Math.abs(balance) > epsilon / 100 && Math.abs(H - L) > epsilon / 100) {
            if (balance > 0) {
                L = g; 
            } else {
                H = g; 
            }

            g = (L + H) / 2; 
            balance = endBalance(loan, rate, n, g);
            iterationCounter++;
        }

        return Math.round(g); // Use rounding for consistency
    }
}
