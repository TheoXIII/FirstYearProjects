/*
int hcf = Gauss.euclid(-4,8);
        int mult1 = -4/hcf;
        int mult2 = 8/hcf;
        System.out.println(mult1);
        System.out.println(mult2);
 */

import java.util.Arrays;

public class GaussRunner {
    public static void main(String[] args) {
        //int[][] eqs = {{1,1,2,1},
        //        {1,0,3,2},
        //        {3,4,4,1}};
        //int[][] eqs = {{2,2,1,-1},
        //        {1,1,3,2},
        //        {-1,1,2,3}};
        /*int[][] eqs = {{-2,1,0,0,0,1},
                {1,-2,1,0,0,0},
                {0,1,-2,1,0,0},
                {0,0,1,-2,1,0},
                {0,0,0,1,-2,0}};*/
        /*int[][] eqs = {{1,1,2,5},
                {1,-1,-1,1},
                {1,0,1,3}};*/

        boolean[][] eqsBool = {{true, true, false, true, false, false},
                {true, false, true, true, false, true},
                {false, true, false, false, true, false},
                {true, true, true, false, true, false},
                {true,false,true,false,false,false}};
        try {
            System.out.println(Arrays.toString(GaussGF2.solve(eqsBool)));
        } catch (NoUniqueSolutionsException e) {
            System.out.println(e.getMessage());
        }
    }
}
