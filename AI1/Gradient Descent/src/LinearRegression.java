import java.util.Scanner;

public class LinearRegression {
    public static class Coord {
        private final double x;
        private final double y;

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public Coord(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void createLine(Coord[] trainingSet, double alpha) {
        double cost;
        double w0 = 0;
        double w1 = 0;
        Scanner sc = new Scanner(System.in);
        while(!sc.nextLine().equals("done")) {
            cost = 0;
            for (Coord point : trainingSet) {
                double prediction = w0 + w1 * point.getX();
                cost = cost + Math.pow((point.getY() - prediction), 2); //Mean square error
                w0 += alpha * (point.getY() - prediction);
                w1 += alpha * (point.getY() - prediction) * point.getX();
            }
            System.out.printf("cost: %f%n", cost);
            System.out.printf("w0: %f%n", w0);
            System.out.printf("w1: %f%n", w1);
        }
    }

    public static void main(String[] args) {
        Coord[] trainingSet = new Coord[]{new Coord(1,1), new Coord(2,5), new Coord(3,11)};
        createLine(trainingSet,1);
    }
}
