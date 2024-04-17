/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner1
 */
public class AgentSearch {
    
    File codenames = new File("dictionary.txt");
    
    public String[] getNames() {
        ArrayList<String> codeNames = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(codenames);
            while(sc.hasNextLine()) {
            codeNames.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        }
        String[] list = codeNames.toArray(new String[codeNames.size()]);
        return list;
    }
    
    public static int binarySearch(String term, String[] list) {
        int low = 0;
        int high = list.length;
        int mid = 0;
        
        while(low <= high) {
            mid = (low + high)/2;
            if(list[mid].equals(term)) {
                return mid;
            } else if(list[mid].compareTo(term) > 0) {
                high = mid - 1;
            } else {
                low = mid - 1;
            }
        }
        return -1;
    }
    
    public static int seqSearch(String term, String[] list) {
        for(int i = 0; i < list.length; i++) {
            if(list[i].equals(term)) {
                return i;
            }
        }
        return -1;
    }
    
}
