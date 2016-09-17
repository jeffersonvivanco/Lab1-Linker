package linker;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class linker {

    public static void main(String[] args) throws IOException{

        //Reading the file line by line and storing each line in an array
        FileInputStream finput = new FileInputStream("/Users/jeffersonvivanco/Desktop/OSCS202/input1.txt");
        BufferedReader br  = new BufferedReader(new InputStreamReader(finput));
        String line = null;
        ArrayList<String> lines = new ArrayList<>();
        while((line = br.readLine())!=null){
            lines.add(line);
        }

        //Getting symbol table and parsing each line to get final output


    }
}
