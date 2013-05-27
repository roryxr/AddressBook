package com.roryxr.addressbook;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class AppTest {
    private static String myPath = "TestFile.json"; //file path
    private static JSONParser parser = new JSONParser(); //file parser
    private static JSONObject init = null; //initial position of json
    private static JSONObject curr = null; //current position of json
    private static AddressBook testCase = null;
    
    public AppTest(){
    	try {
            JSONObject jo = (JSONObject) parser.parse(new FileReader(myPath));
            JSONObject initPos = jo;
            JSONObject currPos = jo; // init and curr point to the same json at the beginning
        } catch (Exception e){
        	e.printStackTrace();
        }
    	testCase = new AddressBook(init, curr, myPath, false);
    }
    public static void main(String [] args)
    {
        String [] cmd1 = {"cd"};
        String [] cmd2 = {"cd non-existence-key"};
        String result1 = "cd error1";
        String result2 = "cd error2";
        if(!result1.equals(testCase.doCommand(cmd1))){
            System.out.println("Wrong Test Result: no cd target was given!");
        }
        if(!result2.equals(testCase.doCommand(cmd2))){
        	System.out.println("Wrong Test Result: cd target does not exist");
        }
        String [] cmd3 = {"cat"};
        String result3 = "cat error";
        if(!result3.equals(testCase.doCommand(cmd3)))
        {
            System.out.println("Wrong Test Result: no cat target was given");
        }
    }
}
