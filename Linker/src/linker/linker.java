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

        //Getting symbol table and parsing each line to get final output, going through array of lines twice

        int numOfModules = 0;//once for getting symbol and the next to get the absolute addresses of words
        int length = 0; //Keeps track of the place the words are at
        SymbolTable symbolTable = new SymbolTable();//Creating symbol table
        ModuleList modules = new ModuleList();//Creating a list to store modules
        for(int i=0; i<2; i++){
            if(i==0){
                for(int x=0; x<lines.size(); x++){
                    //First pass, getting symbol table
                    if(x==0){
                        numOfModules = Integer.parseInt(lines.get(x));//Recording 1st line of file for num of modules
                    }
                    if(x>0){
                        //Getting definitions
                        if(x%3==1){
                            int numOfSymbols = Integer.parseInt(lines.get(x).charAt(0)+"");
                            //if there are symbols defined
                            if(numOfSymbols > 0){
                                //parsing the string to get symbols and their definitions
                                String[] lineEles = lines.get(x).split(" ");
                                String varName = "";
                                int varValue = 0;
                                for(int l=1; l<lineEles.length; l++){
                                    if(l%2==1){

                                        varName = lineEles[l];
                                    }
                                    if(l%2==0){
                                        varValue = Integer.parseInt(lineEles[l]) + length;
                                    }
                                }
                                Variable v = new Variable(varName,varValue);
                                symbolTable.addVariable(v);
                            }
                        }
                        //Getting the length from the line where words are defined, to help us compute value of symbol
                        if(x%3==0){
                            length = length + Integer.parseInt(lines.get(x).charAt(0)+"");
                        }
                    }
                }
            }
            //Second pass, getting absolute addresses of words
            if(i==1){
                ArrayList<Variable> listUsed = new ArrayList<>();
                for(int j=1; j<lines.size(); j++){
                    if(j%3 == 2){
                        int usedSyms = Integer.parseInt(lines.get(j).charAt(0)+"");
                        if(usedSyms > 0){
                            String[] symbols = lines.get(j).substring(1).split(" ");
                            for(int k=0; k<symbols.length; k++){
                                Variable sym  = symbolTable.findVariable(symbols[k]);
                                listUsed.add(sym);
                            }
                        }
                    }
                    if(j%3 ==0){
                        int numWords = Integer.parseInt(lines.get(j).charAt(0)+"");
                        length = length+numWords;
                        String[] words = null;
                        if(numWords>0){
                            words = lines.get(j).substring(1).split(" ");
                        }
                        Module module = new Module(listUsed,words,length);
                        modules.addModule(module);
                    }

                }
            }
            length = 0;
        }
        System.out.println(symbolTable.toString());
        System.out.println(modules.toString());


    }
}
