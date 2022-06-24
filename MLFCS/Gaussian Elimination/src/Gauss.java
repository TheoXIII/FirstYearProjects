public class Gauss {
    public static int euclid(int x, int y) {
        int r;
        while (y != 0) {
            r = x%y;
            x = y;
            y = r;
        }
        return x;
    }

    private static void printGrid(int[][] eqs) {
        for (int[] eq: eqs) {
            System.out.print("(");
            for (int i=0; i<eq.length-1; i++) {
                System.out.printf("%4d",eq[i]);
            }
            System.out.printf("|%4d)%n",eq[eq.length-1]);
        }
        System.out.printf("%n");
    }

    private static void swap(int i, int j, int[][] a) {
        int[] temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static double[] solve(int[][] eqs) throws NoUniqueSolutionsException {
        for (int i=0; i < eqs.length-1; i++) {
            printGrid(eqs);
            if (eqs[i][i] == 0) {
                boolean notFound = true;
                for (int j=i+1; j < eqs.length; j++) {
                    if (eqs[j][i] != 0) {
                        swap(i,j,eqs);
                        notFound = false;
                        break;
                    }
                }
                if(notFound) {
                    throw new NoUniqueSolutionsException("A variable is not used.");
                }
                printGrid(eqs);
            }
            for (int j=i+1; j< eqs.length; j++) {
                int hcf = euclid(eqs[i][i],eqs[j][i]);
                int mult1 = eqs[j][i]/hcf;
                int mult2 = eqs[i][i]/hcf;
                for (int k=i; k<eqs[j].length; k++) {
                    eqs[j][k] = eqs[j][k]*mult2-eqs[i][k]*mult1;
                }
            }
        }
        for (int i=eqs.length-1; i > 0; i--) {
            printGrid(eqs);
            for (int j=0; j < i; j++) {
                int hcf = euclid(eqs[i][i],eqs[j][i]);
                int mult1 = eqs[j][i]/hcf;
                int mult2 = eqs[i][i]/hcf;
                for (int k=0; k<eqs[j].length; k++) {
                    eqs[j][k] = eqs[j][k]*mult2-eqs[i][k]*mult1;
                }
            }
        }
        printGrid(eqs);
        double[] solutions = new double[eqs.length];
        for (int i=0; i<eqs.length; i++)
            solutions[i] = (double) eqs[i][eqs[i].length-1]/(double) eqs[i][i];
        return solutions;
    }
}
