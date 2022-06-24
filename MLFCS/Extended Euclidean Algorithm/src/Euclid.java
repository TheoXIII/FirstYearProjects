import java.util.Scanner;

public class Euclid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();
        int u_x = 1, u_y = 0, v_x = 0, v_y = 1, r, k, u, v;
        while (y != 0) {
            r = x%y;
            k = x/y;
            u = u_x;
            v = v_x;
            u_x = u_y;
            v_x = v_y;
            u_y = u-k*u_y;
            v_y = v-k*v_y;
            x = y;
            y = r;
        }
        System.out.printf("x=%d, u_x=%d, v_x=%d%n",x,u_x,v_x);
    }
}
