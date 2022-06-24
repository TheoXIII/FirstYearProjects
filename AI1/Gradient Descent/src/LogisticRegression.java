import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LogisticRegression {
    public static class WrongDimensionsException extends Throwable{
    }

    private final int DIMENSIONS;

    public LogisticRegression(int dimensions) {
        DIMENSIONS = dimensions;
    }

    private final List<Coord> coordinates = new LinkedList<>();

    private class Coord {
        private final double[] x;
        private final double y;

        public double getX(int i) {
            return x[i];
        }

        public double getY() {
            return y;
        }

        public Coord(double[] x, double y) throws WrongDimensionsException {
            if (x.length != DIMENSIONS) {
                throw new WrongDimensionsException();
            }
                this.x = x;
            this.y = y;
        }
    }

    public void addCoord(double[] x, double y) throws WrongDimensionsException {
        coordinates.add(new Coord(x,y));
    }

    private double sigmoid(double[] weights, Coord p) {
        double w = weights[0];
        for (int i = 0; i < DIMENSIONS; i++) {
            w += weights[i+1]*p.getX(i);
        }
        return 1/(1+Math.pow(Math.E,-1*w)); //sigmoid function

    }

    public void createLine(double alpha) {
        double[] weights = new double[DIMENSIONS+1];
        Scanner sc = new Scanner(System.in);
        while(!sc.nextLine().equals("done")) {
            for (Coord p : coordinates) {
                double f = sigmoid(weights, p);
                weights[0] += alpha * (p.getY() - f);
                for (int i = 0; i < DIMENSIONS; i++) {
                    weights[i + 1] += alpha * (p.getY() - f) * p.getX(i);
                }
            }
            double cost = 0;
            for (Coord p: coordinates) {
                cost -= p.getY() * Math.log(sigmoid(weights, p)) + (1 - p.getY()) * Math.log(1 - sigmoid(weights, p));
            }
            for (double w : weights) {
                System.out.println(w);
            }
            System.out.println("Cost: "+cost);
        }
    }

    public static void main(String[] args) throws WrongDimensionsException {
        LogisticRegression lr = new LogisticRegression(2);
        lr.addCoord(new double[]{2,3},1);
        lr.addCoord(new double[]{5,4},1);
        lr.addCoord(new double[]{6,2},1);
        lr.addCoord(new double[]{1,1},0);
        lr.addCoord(new double[]{2,2},0);
        lr.addCoord(new double[]{4,0},0);
        lr.createLine(0.01);
    }


}
