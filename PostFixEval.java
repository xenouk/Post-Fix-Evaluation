/* *
 *      Author: Guang Peng Li
 *      Date: 08/05/2013
 *      Project: OS Assignment 2
 *      University of Liverpool
 *      Comment: Post Fix Evaluation
 *  */

import java.io.*;
import java.lang.Math;
import java.text.*;
import java.util.*;

public class PostFixEval {
    /**############################################**/
    /**----------------------------------- Constructor ----------------------------------**/
    /**############################################**/
    public PostFixEval(){
        try{
            boolean stop = false;
            String[] inputText = new String[100];   // Maximum 100 lines of inputs.
            int count = 0;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("\n(End each line with ';' and stop input with '@')\nEnter input: ");
            // stop input by typing "@".
            do{  
                inputText[count] = reader.readLine();
                if(inputText[count].equals("@")){
                    stop = true;
                } else{
                    count++;
                }
            }while(stop == false);
            calc(inputText, count);
        }catch(IOException ioe) {}
    }

    /**############################################**/
    /**----------------------------------- Calculations ----------------------------------**/
    /**############################################**/
    public static void calc(String[] inputText, int count){
        // Declaration Variables
        Stack<Integer> st = new Stack<Integer>();
        String token = "";
        char temp =' ';
        boolean semiColumn = false;
        boolean isCorrect = true;
        System.out.println();
        int valueL, valueR = 0;

        // Strings within the Array
        for(int i = 0; i < count; i++) {
            StringTokenizer tokens = new StringTokenizer(inputText[i]);
            semiColumn = false;
            while(tokens.hasMoreTokens()){  
                token = tokens.nextToken();
                temp = token.charAt(0);

                try{
                    if(isCorrect == true){
                        // When the token is a digit
                        if(Character.isDigit(temp)){
                            try{    
                                valueR = st.push(new Integer(((Number)NumberFormat.getInstance().parse(token)).intValue()));
                            }catch(ParseException pe){}
                            System.out.println("LOAD R1,"+valueR);
                            System.out.println("PUSH R1");
                            if(token.charAt(token.length() - 1) == ';'){
                                System.out.println("POP R1");
                                System.out.println("PRINT "+st.pop());
                                System.out.println("**********************************");
                                semiColumn = true;
                            }
                        } 
                        // Line terminate when the token is a semicolumn and pop out the result
                        else if(temp == ';'){
                            if(st.empty()){
                                throw new EmptyStackException();
                            }
                            System.out.println("POP R1");
                            System.out.println("PRINT "+st.pop());
                            System.out.println("**********************************");
                            semiColumn = true;
                        } 
                        // When the token is a selected operator will apply the following calculations
                        // and stack must not be empty!
                        else if((temp == '+' || temp == '/' || temp == '-' || temp == '*') && !st.empty()){
                            // pop out the first value
                            valueR = st.pop();
                            System.out.println("POP R2");
                            // pop out the second value
                            valueL = st.pop();
                            System.out.println("POP R1");
                            // Case depends on the operator
                            switch (temp){
                                case '+':   // Case addtion
                                    st.push(new Integer(valueL + valueR));
                                    System.out.println("ADD R1,R2");
                                    System.out.println("PUSH R1");
                                    break;
                                case '-':   // Case subtraction
                                    st.push(new Integer(valueL - valueR));
                                    System.out.println("SUB R1,R2");
                                    System.out.println("PUSH R1");
                                    break;
                                case '*':   // Case multiplication
                                    st.push(new Integer(valueL * valueR));
                                    System.out.println("MUL R1,R2");
                                    System.out.println("PUSH R1");
                                    break;
                                case '/':   // Case Division
                                    try{
                                        st.push(new Integer(valueL / valueR));
                                        System.out.println("DIV R1,R2");
                                        System.out.println("PUSH R1");
                                    } catch (ArithmeticException iae){
                                        System.out.println("You have divided by Zero!");
                                    }
                                    break;
                                case '^':   // Case power
                                    st.push(new Integer((int)Math.pow(valueL, valueR)));
                                    System.out.println("POW R1,R2");
                                    System.out.println("PUSH R1");
                                    break;
                                default:   
                            }
                            // Line terminate when the last token is a semicolumn and pop out the result
                            if(token.charAt(token.length() - 1) == ';'){
                                System.out.println("POP R1");
                                System.out.println("PRINT "+st.pop());
                                System.out.println("**********************************");
                                semiColumn = true;
                            } 
                        }
                    }
                } 
                // Catch Stack errors and the line will become in correct input
                catch (EmptyStackException ese){
                    isCorrect = false;
                }
            }
            // Warning of incorrectness input or not in post fix notation
            if(semiColumn == false || (token.charAt(token.length() - 1) == ';' && isCorrect == false)){
                isCorrect = true;
                System.out.println("Syntax error: "+inputText[i]+" is not in Post Fix Notation ");
                System.out.println("**********************************");
            }  
        }
    }

    /**############################################**/
    /**----------------------------------- Main ----------------------------------**/
    /**############################################**/
    public static void main(String[] args) {
        PostFixEval pFixEval = new PostFixEval();     
    }
}