
public class GaussGF2 {
    public static int euclid(int x, int y) {
        int r;
        while (y != 0) {
            r = x%y;
            x = y;
            y = r;
        }
        return x;
    }

    private static void xorArrays(boolean[] a, boolean[] b, boolean[] output, int start) { //All same length
        for (int i=start; i<a.length; i++)
            output[i] = a[i]^b[i];
    }

    private static void printGrid(boolean[][] eqs) {
        for (boolean[] eq: eqs) {
            System.out.print("(");
            for (int i=0; i<eq.length-1; i++) {
                System.out.printf("%2d",eq[i] ? 1:0);
            }
            System.out.printf("|%2d)%n",eq[eq.length-1] ? 1:0);
        }
        System.out.printf("%n");
    }

    private static void swap(int i, int j, boolean[][] a) {
        boolean[] temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static byte[] solve(boolean[][] eqs) throws NoUniqueSolutionsException {
        for (int i=0; i < eqs.length-1; i++) {
            printGrid(eqs);
            if (!eqs[i][i]) {
                boolean notFound = true;
                for (int j=i+1; j < eqs.length; j++) {
                    if (eqs[j][i]) {
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
                if(eqs[j][i]) {
                    xorArrays(eqs[i],eqs[j],eqs[j],i);
                }
            }
        }
        for (int i=eqs.length-1; i > 0; i--) {
            printGrid(eqs);
            for (int j=0; j < i; j++) {
                if(eqs[j][i]) {
                    xorArrays(eqs[i],eqs[j],eqs[j],i);
                }
            }
        }
        printGrid(eqs);
        byte[] solutions = new byte[eqs.length];
        for (int i=0; i<eqs.length; i++)
            solutions[i] = (byte) (eqs[i][eqs[i].length-1] ? 1:0);
        return solutions;
    }
}