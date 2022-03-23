

//CSC 540-01 Assignment #2

 //Imports
import java.io.*;
import java.util.*;

public class A2 {
	
	//Constants
	final static int INT = 10;
	final static int IDENT = 11;
	final static int ASSIGN_OP = 20;
	final static int ADD_OP = 21;
	final static int SUB_OP = 22;
	final static int MULT_OP = 23;
	final static int DIV_OP = 24;
	final static int LEFT_PAREN = 25;
	final static int RIGHT_PAREN = 26;
	
	public static void main(String[] args) {
		try {
			File file = new File(args[0]);		
			Scanner sc = new Scanner(file);
			String input = "";
			while(sc.hasNext()){ //Loops through file concatenating all strings while ignoring whitespaces
				input += sc.next();
			}
			char[] ch = input.toCharArray(); //Turn input into a character array
			Queue<String> list = build(ch); //Creates a Queue by passing the character array to the build method
			print(list); //Prints final output by running the list through the print method
			sc.close(); //Closes Scanner
		} catch (Exception e) {
			System.out.println("ERROR - cannot open front.in \n");
		}
	}

	public static Queue<String> build(char[] ch){ //Function that splits all corresponding lexems into Queue
        Queue<String> list = new LinkedList<String>();
        String temp = ""; //Used to capture individual characters while parsing through char array
        Boolean numFlag = false; //Used to determine whether a Digit or Letter came first in String
        for(int i = 0; i < ch.length; i++){
            //Check for different symbols and add to Queue
			if((ch[i] == '(') || (ch[i] == ')') || (ch[i] == '+') || (ch[i] == '-') || (ch[i] == '*') || (ch[i] == '/')){
                //Used to symbolize that the Identifier is finished and should be added to Queue
				if(temp != ""){
                    list.add(temp);
                    temp = "";
                }
                list.add(Character.toString(ch[i]));
            }
			//Used to capture last char
            else if(i == ch.length-1){
                temp += Character.toString(ch[i]);
                list.add(temp);
            }
            else{
				//Check to see if Number or Letter is first in String so they can be split accordingly 
                if(isNumber(Character.toString(ch[i])) && !numFlag){
                    numFlag = true;
                    temp += Character.toString(ch[i]);
                }
                else if(isNumber(Character.toString(ch[i])) && numFlag){
                    temp += Character.toString(ch[i]);
                }
                else if(!isNumber(Character.toString(ch[i])) && numFlag){
                    numFlag = false;
                    list.add(temp);
                    temp ="";
                    temp += Character.toString(ch[i]);
                }
                else
                    temp += Character.toString(ch[i]);
            }
        }
        return list;
    }

    public static void print(Queue<String> list){ //Used to loop through Queue and print corresponding tokens and lexems
        int n = list.size();
        for(int i = 0; i < n; i++){
            if(list.peek()!= ""){
                if(list.peek().equals("("))
                    System.out.println("Next token is: "+LEFT_PAREN+", Next lexeme is "+list.poll());
                else if(list.peek().equals(")"))
                    System.out.println("Next token is: "+RIGHT_PAREN+", Next lexeme is "+list.poll());
                else if(list.peek().equals("+"))
                    System.out.println("Next token is: "+ADD_OP+", Next lexeme is "+list.poll());
                else if(list.peek().equals("-"))
                    System.out.println("Next token is: "+SUB_OP+", Next lexeme is "+list.poll());
                else if(list.peek().equals("*"))
                    System.out.println("Next token is: "+MULT_OP+", Next lexeme is "+list.poll());
                else if(list.peek().equals("/"))
                    System.out.println("Next token is: "+DIV_OP+", Next lexeme is "+list.poll());
                else if(isNumber(list.peek()))
                    System.out.println("Next token is: "+INT+", Next lexeme is "+list.poll());
                else
                    System.out.println("Next token is: "+IDENT+", Next lexeme is "+list.poll());
            }
            else
                list.remove();
        }
        System.out.println("Next token is: -1, Next lexeme is EOF");
    }

    public static boolean isNumber(String str){ //Used to verify if a String is a digit
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e){  
            return false;
        }
    }
}
