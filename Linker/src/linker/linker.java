package linker;



import java.io.*;
import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class linker {

//    public static void main(String[] args) throws IOException{
//
//        //Reading the file line by line and storing each line in an array
//        FileInputStream finput = new FileInputStream("/Users/jeffersonvivanco/Desktop/OSCS202/Lab1Linker-Files/input1.txt");
//        BufferedReader br  = new BufferedReader(new InputStreamReader(finput));
//        String line = null;
//        ArrayList<String> lines = new ArrayList<>();
//        while((line = br.readLine())!=null){
//            lines.add(line);
//        }
//
//        //Getting symbol table and parsing each line to get final output, going through array of lines twice
//
//        int numOfModules = 0;//once for getting symbol and the next to get the absolute addresses of words
//        int length = 0; //Keeps track of the place the words are at
//        SymbolTable symbolTable = new SymbolTable();//Creating symbol table
//        ModuleList modules = new ModuleList();//Creating a list to store modules
//        for(int i=0; i<2; i++){
//            if(i==0){
//                for(int x=0; x<lines.size(); x++){
//                    //First pass, getting symbol table
//                    if(x==0){
//                        numOfModules = Integer.parseInt(lines.get(x));//Recording 1st line of file for num of modules
//                    }
//                    if(x>0){
//                        //Getting definitions
//                        if(x%3==1){
//                            int numOfSymbols = Integer.parseInt(lines.get(x).charAt(0)+"");
//                            //if there are symbols defined
//                            if(numOfSymbols > 0){
//                                //parsing the string to get symbols and their definitions
//                                String[] lineEles = lines.get(x).split(" ");
//                                String varName = "";
//                                int varValue = 0;
//                                for(int l=1; l<lineEles.length; l++){
//                                    if(l%2==1){
//
//                                        varName = lineEles[l];
//                                    }
//                                    if(l%2==0){
//                                        varValue = Integer.parseInt(lineEles[l]) + length;
//                                    }
//                                }
//                                Variable v = new Variable(varName,varValue);
//                                symbolTable.addVariable(v);
//                            }
//                        }
//                        //Getting the length from the line where words are defined, to help us compute value of symbol
//                        if(x%3==0){
//                            Module module = new Module(length);
//                            modules.addModule(module);
//                            length = Integer.parseInt(lines.get(x).charAt(0)+"") + length;
//                        }
//                    }
//                }
//            }
//            //Second pass, getting absolute addresses of words
//            if(i==1){
//                ArrayList<Variable> listUsed = null;
//                for(int j=1; j<lines.size(); j++){
//
//                    if(j%3 == 2){
//                        int usedSyms = Integer.parseInt(lines.get(j).charAt(0)+"");
//                        if(usedSyms > 0){
//                            String[] symbols = lines.get(j).split(" ");
//                            listUsed = new ArrayList<>();
//                            for(int k=1; k<symbols.length; k++){
//                                Variable sym  = symbolTable.findVariable(symbols[k]);
//
//                                listUsed.add(sym);
//                            }
//                        }
//                    }
//                    if(j%3 ==0){
//                        int numWords = Integer.parseInt(lines.get(j).charAt(0)+"");
//
//
//                        String[] words = null;
//                        if(numWords>0){
//                            words = lines.get(j).substring(1).split(" ");
//                        }
//                        Module temp = modules.findModule(length);
//                        temp.setListUsed(listUsed);
//                        temp.setWords(words);
//                        length = length+numWords;
//
//                    }
//
//                }
//            }
//            length = 0;
//        }
//        System.out.println(symbolTable.toString());
//        System.out.println(modules.toString());
//
//
//    }

    public static void main(String[] args)throws IOException{

        //Reading the file line by line and storing each line in an array
        FileInputStream finput = new FileInputStream("/Users/jeffersonvivanco/Desktop/OSCS202/Lab1Linker-Files/input1.txt");
        BufferedReader br  = new BufferedReader(new InputStreamReader(finput));

        String line = null;//Where the line from the reader will be stored
        String[] arrayS;//Used to store the line as an array, helps with parsing
        String potentialLine = "";//This is where we store the lines that we make from a module ex:deflist, uselist, textlist

        int index = 0;//We use this to control when a new line of a module is starting, for example when a new def list or use list starts

        int numOfModules = 0;//Here we store the first number from each file which represents the number of modules
        //These booleans are used to notify the program when to start making a def list, use list etc.
        boolean isNumOfModules = true;
        Boolean def = false;
        Boolean used = false;
        Boolean words = false;

        int numOfElements = 0;//Keeps track of the number of elements in each list

        SymbolTable symbolTable = new SymbolTable();

        //Reading each line from the file
        while((line = br.readLine())!=null){


            arrayS = line.split(" ");

            for(int j=0; j<arrayS.length; j++){
                if(arrayS[j].matches("[0-9]+") || arrayS[j].matches("[a-zA-Z]+")||arrayS[j].length()>1){

                    if(isNumOfModules){//Get num of modules
                        numOfModules = Integer.parseInt(arrayS[j]);
                        System.out.println(numOfModules);
                        isNumOfModules = false;
                        def = true;
                        index = 1;
                    }
                    else if(def){//Get definition line
                        if(index == 1){
                            numOfElements = Integer.parseInt(arrayS[j]);
                            index = 0;
                        }
                        potentialLine = potentialLine + arrayS[j]+" ";
                        int length = potentialLine.split(" ").length;
                        if(length == numOfElements*2+1){
                            System.out.println(potentialLine);
                            String[] s = potentialLine.split(" ");
                            String name="";
                            boolean checkName = false;
                            boolean checkValue = false;
                            int value=0;
                            for(int q=1; q<s.length; q++){
                                if(q%2 == 1){
                                    name = s[q];
                                    checkName = true;
                                }
                                if(q%2==0){
                                    value = Integer.parseInt(s[q]);
                                    checkValue = true;
                                }
                                if(checkName && checkValue){
                                    Variable v = new Variable(name, value);
                                    symbolTable.addVariable(v);
                                    checkName = false;
                                    checkValue = false;
                                }
                            }

                            def = false;
                            used = true;
                            index = 1;
                            potentialLine = "";
                        }
                    }
                    else if(used){//Get used list
                        if(index == 1){
                            numOfElements = Integer.parseInt(arrayS[j]);
                            index = 0;
                        }
                        potentialLine = potentialLine + arrayS[j]+" ";
                        int length  = potentialLine.split(" ").length;
                        if(length == numOfElements+1){
                            System.out.println(potentialLine);
                            used = false;
                            words = true;
                            index = 1;
                            potentialLine = "";
                        }
                    }
                    else if(words){//Get program text
                        if(index == 1){
                            numOfElements = Integer.parseInt(arrayS[j]);
                            index = 0;
                        }
                        potentialLine = potentialLine + arrayS[j]+" ";
                        int length = potentialLine.split(" ").length;
                        if(length==numOfElements+1){
                            System.out.println(potentialLine);
                            words = false;
                            def = true;
                            index = 1;
                            potentialLine = "";
                        }
                    }
                    else{
                        //Do nothing
                    }
                }
                else{
                    //do nothing
                }
            }
        }
        System.out.println(symbolTable.toString());

    }

}
