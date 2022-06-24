import java.util.*;

public class DPLL {
    static String[] symbols;

    public static void outputStatement(List<byte[]> expr, int depth) {
        StringBuilder output = new StringBuilder();
        output.append(" ".repeat(depth * 4));
        for(byte[] clause: expr) {
            output.append("(");
            for(int i=0; i<clause.length; i++) {
                if (clause[i] == 1)
                    output.append(String.format("%s∨",symbols[i]));
                else if (clause[i] == -1)
                    output.append(String.format("¬%s∨",symbols[i]));
                else if (clause[i] == 2)
                    output.append(String.format("%s∨¬%s∨",symbols[i],symbols[i]));
            }
            if(output.charAt(output.length()-1) == '∨')
                output.deleteCharAt(output.length()-1);
            output.append(")^");
        }
        output.deleteCharAt(output.length()-1);
        System.out.println(output);
    }

    public static byte[] tryVal(byte newVal,List<byte[]> oldExpr,byte[] terms,int i,int depth) {
        System.out.print(" ".repeat(depth * 4));
        System.out.printf("%s: %d%n",symbols[i],newVal);
        terms[i] = newVal;
        List<byte[]> expr = new LinkedList<>();
        for (byte[] clause: oldExpr) {
            if (clause[i] != newVal && clause[i] != 2) {
                byte[] newClause = Arrays.copyOf(clause, clause.length);
                newClause[i] = 0;
                expr.add(newClause);
            }
        }
        return solve(expr, terms,++depth);

    }

    public static byte[] solve(List<byte[]> oldExpr, byte[] oldTerms, int depth) {
        if(oldExpr.isEmpty()) {
            System.out.print(" ".repeat(depth * 4));
            System.out.println("SAT");
            return oldTerms;
        }
        outputStatement(oldExpr,depth);
        byte[] terms = Arrays.copyOf(oldTerms,oldTerms.length);
        for (int i=0; i < terms.length; i++) {
            if(terms[i] == 0) {
                byte[] correctTerms = tryVal((byte) 1,oldExpr,terms,i,depth);
                if (correctTerms != null)
                    return correctTerms;

                correctTerms = tryVal((byte) -1,oldExpr,terms,i,depth);
                if (correctTerms != null)
                    return correctTerms;
                terms[i] = 0;

            }
        }
        System.out.print(" ".repeat(depth * 4));
        System.out.println("UNSAT");
        return null;
    }

    public static byte[] solve(List<byte[]> expr, int length) {
        return solve(expr,new byte[length],1);
    }

    public static void readClause(int clauseNo,boolean val,List<byte[]> expr,int symbolNo,Map<String,Integer> symbolMap,Scanner sc) {
        System.out.printf("Enter %b symbols in clause %d",val,clauseNo);
        String[] symbols = sc.nextLine().split(" ");
        for (String symbol: symbols) {
            try {
                if (val)
                    expr.get(clauseNo)[symbolMap.get(symbol)] = 1;
                else {
                    if (expr.get(clauseNo)[symbolMap.get(symbol)] == 0)
                        expr.get(clauseNo)[symbolMap.get(symbol)] = -1;
                    else //It will be 1
                        expr.get(clauseNo)[symbolMap.get(symbol)] = 2; //Accept both true and untrue.
                }
            } catch (NullPointerException e) {
                System.out.printf("WARNING: %s is not a valid symbol.%n",symbol);
            }
        }

    }

    public static String[] read(List<byte[]> expr) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of clauses:");
        int clauseNum = Integer.parseInt(sc.nextLine());
        System.out.printf("%d clauses%n",clauseNum);
        System.out.println("Enter symbols (separate by space):");
        Map<String, Integer> symbolMap = new TreeMap<>();
        int symbolNo=0;
        for (String symbol: sc.nextLine().split(" ")) {
            if (symbolMap.get(symbol) == null) {
                symbolMap.put(symbol, symbolNo);
                symbolNo++;
            }
        }
        for (int i=0; i<clauseNum; i++) {
            expr.add(new byte[symbolNo]);
            readClause(i,true,expr,symbolNo,symbolMap,sc);
            readClause(i,false,expr,symbolNo,symbolMap,sc);
        }
        return symbolMap.keySet().toArray(new String[symbolNo]);
    }

    /*public static void truthTable(String[] ) {

    }*/

    public static void main(String[] args) {
        List<byte[]> expr = new LinkedList<>();
        String[] expressions = read(expr);
        symbols = expressions;
        outputStatement(expr,0);
        byte[] solution = solve(expr,expressions.length);
        if(solution == null) {
            System.out.println("No solution");
        } else {
            for (int i=0; i<solution.length; i++) {
                System.out.printf("%s: %d%n",expressions[i],solution[i]);
            }
        }
    }
}
