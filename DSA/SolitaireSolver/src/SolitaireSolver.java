import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



class Counter {
    static int counter = 0;
    public static void increment() {
        counter++;
    }
    public static void output() {
        System.out.println(counter+" solutions attempted.");
    }
}

class LegalMove {
    int start;
    int skip;
    int end;
    public LegalMove(String[] line) {
        start = Integer.parseInt(line[0]);
        skip = Integer.parseInt(line[1]);
        end = Integer.parseInt(line[2]);
    }

    public boolean isValid(boolean[] positions) {
        return (!(positions[end]) && positions[skip] && positions[start]);
    }

    public boolean[] performMove(boolean[] positions) {
        positions[start] = false;
        positions[skip] = false;
        positions[end] = true;
        return positions;
    }

    public String toString() {
        return start+" "+skip+" "+end;
    }
}

public class SolitaireSolver {
    private static boolean isComplete(boolean[] positions) {
        if (!positions[16])
            return false;
        else {
            for (int i = 0; i < 16; i++) {
                if (positions[i])
                    return false;
            }
            for (int i = 17; i < 33; i++) {
                if (positions[i])
                    return false;
            }
        }
        return true;
    }


    private static LegalMove[] setupLegalMoves() {
        LegalMove[] legalMoves = new LegalMove[76];
        try {
            File legalMoveFile = new File("files/legal_moves");
            Scanner sc = new Scanner(legalMoveFile);
            int i = 0;
            while (sc.hasNextLine()) {
                legalMoves[i++] = new LegalMove(sc.nextLine().split(","));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return legalMoves;
    }
    private static boolean[] setupPositions() {
        boolean[] positions = new boolean[33];
        positions[16] = false;
        for (int i = 0; i < 16; i++) {
            positions[i] = true;
        }
        for (int i = 17; i < 33; i++) {
            positions[i] = true;
        }
        return positions;
    }

    private static List<LegalMove> findValidMoves(boolean[] positions, LegalMove[] legalMoves) {
        List<LegalMove> validMoves = new LinkedList();
        for (LegalMove legalMove : legalMoves) {
            if (legalMove.isValid(positions)) {
                validMoves.add(legalMove);
            }
        }
        return validMoves;
    }


    /**
     *
     * @param oldPositions
     * @param legalMoves
     * @param oldMoveSequence
     * @param nextMove
     * @return
     */
    private static boolean solve(boolean[] oldPositions, LegalMove[] legalMoves, List<String> oldMoveSequence, String nextMove) {
        boolean[] positions;
        List<String> moveSequence = new LinkedList(oldMoveSequence);
        moveSequence.add(nextMove);
        List<LegalMove> validMoves = findValidMoves(oldPositions,legalMoves);
        //System.out.println(Arrays.toString(positions));
        if (validMoves.size() == 0) {
            Counter.increment();
            Counter.output();
            /*for (String s : moveSequence) {
                System.out.println(s);
            }*/

            return false;
        }
        for (int i = 0; i < validMoves.size(); i++) {
            LegalMove move = validMoves.get(i);
            positions = Arrays.copyOf(oldPositions,oldPositions.length);
            move.performMove(positions);
            String stringMove = move.toString()+"\n\n"+toBoard(positions)+"\n";
            if (isComplete(positions)) {
                for (String s : moveSequence) {
                    System.out.println(s);
                }
                System.out.println(stringMove);
                Counter.increment();
                Counter.output();
                return true;
            }
            if (!(solve(positions, legalMoves, moveSequence, stringMove))) {
                validMoves.remove(i);
                i--;
            } else {
                return true;
            }

        }
        return false;
    }

    private static char bTC(boolean b) {
        if (b)
            return 'T';
        return 'F';
    }

    private static String toBoard(boolean[] p) {
        String board = "";
        board+="XX"+bTC(p[0])+bTC(p[1])+bTC(p[2])+"XX\n";
        board+="XX"+bTC(p[3])+bTC(p[4])+bTC(p[5])+"XX\n";
        board+=""+bTC(p[6])+bTC(p[7])+bTC(p[8])+bTC(p[9])+bTC(p[10])+bTC(p[11])+bTC(p[12])+"\n";
        board+=""+bTC(p[13])+bTC(p[14])+bTC(p[15])+bTC(p[16])+bTC(p[17])+bTC(p[18])+bTC(p[19])+"\n";
        board+=""+bTC(p[20])+bTC(p[21])+bTC(p[22])+bTC(p[23])+bTC(p[24])+bTC(p[25])+bTC(p[26])+"\n";
        board+="XX"+bTC(p[27])+bTC(p[28])+bTC(p[29])+"XX\n";
        board+="XX"+bTC(p[30])+bTC(p[31])+bTC(p[32])+"XX\n";
        return board;
    }


    public static void main(String[] args) {
        boolean[] positions =  setupPositions();
        System.out.println(Arrays.toString(positions));
        LegalMove[] legalMoves = setupLegalMoves();
        List<String> moveSequence = new LinkedList();
        solve(positions, legalMoves, moveSequence, "\n"+toBoard(positions)+"\n");
    }
}
