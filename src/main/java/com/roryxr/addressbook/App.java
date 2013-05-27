package com.roryxr.addressbook;

public class App 
{
    public static void main( String[] args )
    {
        String path = "TestFile.json";
        AddressBook ab = new AddressBook(null, null, path, false);
        ab.run();
    }
}
