import java.util.*;

public class HillClimbing {
    final static double TI = 10000;

    public static double eval(int val, double[] coefficients) {
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i]*Math.pow(val,i);
        }
        return sum;
    }

    public static void climbHill(int start, double[] coefficients) {
        int val = start;
        Scanner sc = new Scanner(System.in);
        while (!sc.nextLine().equals("done")) {
            System.out.println(val);
            double neighbour1 = eval(val - 1, coefficients);
            double neighbour2 = eval(val + 1, coefficients);
            double current = eval(val, coefficients);
            System.out.println(current);
            double greatest_neighbour;
            int new_val;
            if (neighbour1 > neighbour2) {
                greatest_neighbour = neighbour1;
                new_val = val-1;
            } else {
                greatest_neighbour = neighbour2;
                new_val = val+1;
            }
            if (greatest_neighbour < current) {
                System.out.println(val);
                break;
            }
            val = new_val;

        }
    }

    public static void simulatedAnnealing(int start, double[] coefficients) {
        int val = start;
        Scanner sc = new Scanner(System.in);
        double t = TI;
        int iteration = 0;
        while (!sc.nextLine().equals("done")) {
            System.out.println(val);
            double current = eval(val, coefficients);
            int new_val;
            if (Math.random() < 0.5)
                new_val = val-1;
            else
                new_val = val+1;
            double neighbour = eval(new_val, coefficients);
            System.out.println(current);
            if (neighbour <= current) {
                if (Math.random() < Math.pow(Math.E,(neighbour-current)/t))
                    val = new_val;
            } else {
                val = new_val;
            }
            t = scheduleT(t, iteration++);

        }
    }

    private static double scheduleT(double t, int iteration) {
        return t/(iteration+1);
    }

    static void reverse(double a[])
    {
        int i, k, n = a.length;
        double t;
        for (i = 0; i < n / 2; i++) {
            t = a[i];
            a[i] = a[n - i - 1];
            a[n - i - 1] = t;
        }
    }

    public static void main(String[] args) {
        double[] coefficients = new double[]{-1,20,678,-5000,-50000,0,10000000};
        reverse(coefficients);
        System.out.println(Arrays.toString(coefficients));
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = coefficients[i] / 1000000;
        }
        simulatedAnnealing(-30,coefficients);
    }
}
