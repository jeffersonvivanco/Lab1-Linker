package linker;



import java.io.*;
import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class linker {


    public static void main(String[] args)throws IOException{

        if(args.length !=1){
            System.err.println("Please include the absolute file path as the first and only command line "+
            "argument to run this program. Please run it again. Thank You!");
            System.exit(0);
        }

        //Reading the file line by line and storing each line in an array
        FileInputStream finput = null;
        BufferedReader br = null;
        try {
            finput = new FileInputStream("jkdfb");
            br  = new BufferedReader(new InputStreamReader(finput));
        }catch (Exception e){
            System.err.println("File could not be read or could not be found. Please make sure"+
            " you entered the correct abs path name of your file. Please run again.");
            System.exit(0);
        }


        ArrayList<String> lines  = new ArrayList<String>(); //The second need the input, we just read off this array


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

        String[] programText = null;

        ArrayList<String> warsAndErrs = new ArrayList<>();

        SymbolTable symbolTable = new SymbolTable();//Created symbol table

        ModuleList modules = new ModuleList();

        int baseAddress = 0; // Keeps track of base address of each module

        int sizeOfMachine = 600;//Given value of machine in class instructions

        int modNum = 0; //Keeps track of which module is at
        for(int w=0; w<2; w++){
            if(w==0){//1st pass
                //Reading each line from the file

                while((line = br.readLine())!=null){
                    lines.add(line);
                    arrayS = line.split(" ");

                    for(int j=0; j<arrayS.length; j++){
                        if(arrayS[j].matches("[0-9]+") || arrayS[j].matches("[a-zA-Z]+")||arrayS[j].length()>1){

                            if(isNumOfModules){//Get num of modules
                                numOfModules = Integer.parseInt(arrayS[j]);
//                                System.out.println(numOfModules);
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
//                                    System.out.println(potentialLine);
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
                                            value = Integer.parseInt(s[q])+baseAddress;
                                            checkValue = true;
                                        }
                                        if(checkName && checkValue){
                                            if(symbolTable.checkIfExists(name) == -1){
                                                Variable v = new Variable(name, value, modNum);
                                                symbolTable.addVariable(v);
                                            }
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
//                                    System.out.println(potentialLine);
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
//                                    System.out.println(potentialLine);
                                    programText = potentialLine.split(" ");
                                    int newLength = programText.length-1;
                                    String[] newProgramText = new String[newLength];
                                    for(int d=0; d<newLength; d++){
                                        newProgramText[d] = programText[d+1];
                                    }
                                    words = false;
                                    def = true;
                                    index = 1;
                                    potentialLine = "";
                                    Module m = new Module(baseAddress, newProgramText, modNum, sizeOfMachine);
                                    modules.addModule(m);
                                    baseAddress = baseAddress + numOfElements;
                                    programText = null;
                                    modNum++;
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
                isNumOfModules = true;
                def = false;
                used = false;
                words = false;
                baseAddress = 0;
                numOfElements = 0;
                modNum = 0;
            }
            if(w == 1){//Second pass
                //Reading each line from the file
                String symbolsDefined = "";
                int place = 0;
                while(place<lines.size()){
                    line = lines.get(place);
//                    System.out.println("It goes in here");
                    arrayS = line.split(" ");

                    for(int j=0; j<arrayS.length; j++){
                        if(arrayS[j].matches("[0-9]+") || arrayS[j].matches("[a-zA-Z]+")||arrayS[j].length()>1){

                            if(isNumOfModules){//Get num of modules
                                numOfModules = Integer.parseInt(arrayS[j]);

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
                                    Module g = modules.findModule(baseAddress);
                                    String[] arrayOfPotentialL = potentialLine.split(" ");
                                    for(int t=1; t<arrayOfPotentialL.length; t++){
                                        symbolsDefined = symbolsDefined + arrayOfPotentialL[t]+" ";
                                    }
//                                    System.out.println(potentialLine);
//                                    System.out.println(symbolsDefined);

                                    symbolsDefined = "";
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
//                                    System.out.println(potentialLine);
                                    Module temp = modules.findModule(baseAddress);
                                    ArrayList<Variable> variablesUsed = new ArrayList<>();
                                    String[] potentialStrArray = potentialLine.split(" ");
                                    for(int s=1; s<potentialStrArray.length; s++){
                                        Variable v = symbolTable.findVariable(potentialStrArray[s]);
                                        modules.addVarToListUsed(potentialStrArray[s]);
                                        if(v!=null)
                                            variablesUsed.add(v);
                                        else{
                                            v = new Variable(potentialStrArray[s],modNum);
                                            variablesUsed.add(v);
                                        }
                                    }
                                    temp.setListUsed(variablesUsed);
                                    temp.computeAddresses();
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
                                    words = false;
                                    def = true;
                                    index = 1;
                                    potentialLine = "";
                                    baseAddress = baseAddress + numOfElements;
                                    modNum++;
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
                    place++;
                }
            }
        }

        modules.setSymbolTable(symbolTable);
        System.out.println(symbolTable.toString());
        System.out.println(modules);
        modules.checkIfDefinedButNotUsed();
        modules.checkIfAppearsNotUsedInModule();
//        for(int err=0; err<warsAndErrs.size(); err++){
//            System.out.println(warsAndErrs.get(err));
//        }

    }

}
