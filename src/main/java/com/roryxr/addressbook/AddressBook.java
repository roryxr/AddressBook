package com.roryxr.addressbook;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class AddressBook {
  private JSONObject initPos; //initial position of json;
  private JSONObject currPos; //current position of json;
  private String filePath; //file path for json file;
  private boolean modified;
  public AddressBook(JSONObject init, JSONObject curr, String path, boolean isModified){
    initPos = init;
    currPos = curr;
    filePath = path;
    modified = isModified;
  }
	
  public void run(){
    JSONParser parser = new JSONParser();
    JSONObject jo = null;
    try{
      jo = (JSONObject) parser.parse(new FileReader(filePath));		
      initPos = jo;
      currPos = jo;
    } catch(Exception e){
    	e.printStackTrace();
    }
    
    while(true){
      System.out.print("ab> ");
      Scanner input = new Scanner(System.in); // scan input from the user
      String[] content = input.nextLine().split(" ");
      String result = doCommand(content); // process user's command and get result
      if(result.equals("msg quit")) return;
    }
  }
  
  public String doCommand(String[] s){
    if(s[0].equals("ls")){
    	Set<String> keySet = currPos.keySet();
    	for(String key : keySet){
    		System.out.print(key+" ");
    	}
    	System.out.println();
    }
    else if(s[0].equals("cd")){
        if(s.length != 2){
          System.out.println("cd command requires a key, try again!");
          return "cd error1";
        }
        if(!currPos.keySet().contains(s[1])){
          System.out.println("key "+s[1]+" does not exist!");
          return "cd error2";
        }
      currPos = (JSONObject) currPos.get(s[1]);
    }
    else if(s[0].equals("cat")){
      if(s.length != 2){
    	  System.out.println("cat command requires a key, try again!");
    	  return "cat error";
      }
      System.out.println("\""+s[1]+"\" : "+currPos.get(s[1]));
    }
    
    else if(s[0].equals("add")){
      System.out.print("key: ");
      Scanner inputKey = new Scanner(System.in);
      String newKey = inputKey.next();
      if(currPos.containsKey(newKey)){
        System.out.println("Unable to add key. keyname: "+newKey+" already exists!");
        return "add error";
      }
      System.out.print("value: ");
      Scanner inputVal = new Scanner(System.in);
      String newVal = inputVal.nextLine();
      currPos.put(newKey, newVal);
      System.out.println("address entry added");
      modified = true;
    }
    else if(s[0].equals("remove")){
      System.out.print("please give the key: ");
      Scanner inputKey = new Scanner(System.in);
      String newKey = inputKey.nextLine();
      if(!currPos.containsKey(newKey)){
        System.out.println(" Unable to remove key. keyname: "+newKey+" does not exist!");
        return "remove error";
      }
      currPos.remove(newKey);
      System.out.println(newKey+" was deleted from JSON");
      modified = true;
    }
    else if(s[0].equals("!quit")){
      return "msg quit";
    }
    else {
      System.out.println("help information:\n"
        +"Command       : Funtion\n"
        +"ls            : list keys\n"
        +"cd      <key> : enter the key level\n"
        +"cat     <key> : display item data\n"
        +"add     <key> : add key\n"
        +"remove  <key> : remove key\n"
        +"!help         : get help information\n"
        +"!quit         : quit program\n");
    }
    if(modified){
    	try{
    	  FileWriter file = new FileWriter("TestFile.json");
    	  file.write(initPos.toJSONString());
    	  file.flush();
    	  file.close();
    	  modified = false;
    	} catch (IOException e) {
    	  e.printStackTrace();
    	}
    }
    return "success";
  }
}
