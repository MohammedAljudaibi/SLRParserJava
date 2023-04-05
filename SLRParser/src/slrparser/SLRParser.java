//Mohammed Aljudaibi 2035778 mabdulraheemaljudaibi@stu.kau.edu.sa
//Motasem Bamashmous 2045661 mmohammedbamashmous@stu.kau.edu.sa
//Group Project Part 2 SLR Parser
package slrparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Stack;

public class SLRParser {

    public static void main(String[] args) throws FileNotFoundException {

        //Making input file
        File in = new File("input.txt");

        //checks if input file exists
        if (!in.exists()) {
            System.out.println("input.txt file does not exist!");
            System.exit(0);

        }
        
        //we have two Scanners, one to read the whole expressiona and print it, and another to read each token of the expression
        Scanner input = new Scanner(in);
        Scanner line = new Scanner(in);
        
        //we are making a hashtable to store the parser table
        Hashtable<String, String> table = new Hashtable<>();
        
        //fuction to input parser table into hashtable
        parseTable(table);
        
        //fuction to parse the statments in input file
        parsing(table, input, line);

    }
    
    //fuction to input parser table into our hashtable using the operator grammer
    public static void parseTable(Hashtable table) {
        table.put("0id", "s5");
        table.put("0(", "s4");
        table.put("0E", "1");
        table.put("0T", "2");
        table.put("0F", "3");

        table.put("1+", "s6");
        table.put("1$", "acc");

        table.put("2+", "r2");
        table.put("2*", "s7");
        table.put("2)", "r2");
        table.put("2$", "r2");

        table.put("3+", "r4");
        table.put("3*", "r4");
        table.put("3)", "r4");
        table.put("3$", "r4");

        table.put("4id", "s5");
        table.put("4(", "s4");
        table.put("4E", "8");
        table.put("4T", "2");
        table.put("4F", "3");

        table.put("5+", "r6");
        table.put("5*", "r6");
        table.put("5)", "r6");
        table.put("5$", "r6");

        table.put("6id", "s5");
        table.put("6(", "s4");
        table.put("6T", "9");
        table.put("6F", "3");

        table.put("7id", "s5");
        table.put("7(", "s4");
        table.put("7F", "10");

        table.put("8+", "s6");
        table.put("8)", "s11");

        table.put("9+", "r1");
        table.put("9*", "s7");
        table.put("9)", "r1");
        table.put("9$", "r1");

        table.put("10+", "r3");
        table.put("10*", "r3");
        table.put("10)", "r3");
        table.put("10$", "r3");

        table.put("11+", "r5");
        table.put("11*", "r5");
        table.put("11)", "r5");
        table.put("11$", "r5");
    }
    
    //fuction to do the parsing
    public static void parsing(Hashtable h, Scanner in, Scanner line) {
        
        //arrays with all the production rules in order
        String[] rules = {"E ---> E + T", "E ---> T", "T ---> T * F", "T ---> F", "F ---> ( E )", "F ---> id"};
        
        //array with the amount to pop(2β) for each production rule in order
        int[] poppers = {6, 2, 6, 2, 6, 2};
        
        //stack to push tokens in
        Stack<String> s = new Stack<String>();
      
        //while loop to go through the expressions in input file
        while (line.hasNext()) {
            //start at state 0
            int state = 0;
            
            //reads expressions from input file and then cuts the $ and prints it
            String expression = line.nextLine();
            expression = expression.substring(0,expression.length() - 2);
            System.out.println("Right most derivation for the arithmetic expression " + expression + ":");
            
            //lookahead is the current string we are reading from the line
            String lookahead = in.next();
            String action = "";
            
            //clearing the stack at the begginning of each expression and pushing $0
            s.clear();
            s.push("$");
            s.push("0");
            
            //loop to keep going until we either accept the expression or give error
            while (!action.equals("acc")) {
                //key is the the current state plus the lookahead, it is used with the hashtable to find what action to take
                String key = state + "" + lookahead;

                //action is the what is stored in the hashtable when given the key
                action = (String) h.get(key);
                
                //if the key does not have a value in the hashtable, then that means it is a Syntax Error and the program stops running
                if(action == null){
                    System.out.println("Syntax Error");
                    System.exit(0);
                }
                
                String nextState = action.substring(1);
                
                //if action is shift
                if (action.charAt(0) == 's') {
                    //shift into the stack the string being read
                    s.push(lookahead);

                    //get the action and remove the "s" at the beginning so we have just the number, and push that onto the stack
                    s.push(nextState);

                    //change the state into the new state
                    state = Integer.parseInt(nextState);
                    
                    //print action
                    System.out.println("Shift " + nextState);
                    
                    //read next token
                    lookahead = in.next();
                }
                
                //if action is reduce
                //TODO
                
                //if it is accept
                //TODO
            }
        }
    }
}