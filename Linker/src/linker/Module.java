package linker;


import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Module {

    private ArrayList<Variable> listUsed = new ArrayList<>();
    private String[] words = {};
    private int baseAddress = 0;
    private String defLine = "";
    private int modNum = 0;
    private int sizeOfMachine;

    public Module(int baseAddress, String[] words, int modNum, int sizeOfMachine){
        this.baseAddress = baseAddress;
        this.words = words;
        this.modNum = modNum;
        this.sizeOfMachine = sizeOfMachine;
    }
    public void setListUsed(ArrayList<Variable>listUsed){
        this.listUsed  = listUsed;
    }
    public int getBaseAddress(){
        return this.baseAddress;
    }

    public void computeAddresses(){
        for(int i=0; i<words.length; i++){
            if(words[i].endsWith("1")){
                words[i] = words[i].substring(0,4);
            }
            else if(words[i].endsWith("2")){
                int value = Integer.parseInt(words[i].substring(1,4));
                if(value < sizeOfMachine)
                    words[i] = words[i].substring(0,4);
                else
                    words[i] = words[i].charAt(0)+"000 Error: Absolute address exceeds machine size; zero used.";
            }
            else if(words[i].endsWith("3")){
                int num = Integer.parseInt(words[i].substring(0,4));
                num = num+baseAddress;
                words[i] = num+"";
            }
            else if(words[i].endsWith("4")){
                int index = Integer.parseInt(words[i].substring(1,4));
                if(index < 0 || index >= listUsed.size()){
                    words[i] = words[i].substring(0,4) + " Error: External address exceeds length of use list; treated as immediate.";
                }
                else{
                    Variable v = listUsed.get(index);
                    int wordAsInt = Integer.parseInt(words[i].substring(0,4))-index;
                    if(v.getValue() == -111){
                        v.varUsed();
                        words[i] = wordAsInt+" Error: "+v.getName()+" is not defined; zero used.";
                    }
                    else{
                        v.varUsed();
                        wordAsInt = wordAsInt+v.getValue();
                        words[i] = wordAsInt+"";
                    }

                }
            }
            else{
                //do nothing, it must be an error
            }

        }
        this.words = words;
    }
    public void checkIfVarUsed(){
        for(int x=0; x<listUsed.size(); x++){
            if(!listUsed.get(x).isVarUsed()){
                System.out.println("Warning: In module "+this.modNum+" "+listUsed.get(x).getName()+
                " is on use list but isn't used.");
            }
        }
    }
    public void setDefLine(String l){
        String[] array = l.split(" ");
        String line = "";
        for(int i=0; i<array.length; i++){
            if(i%2 == 0)
                line = line + array[i]+" ";
        }
        this.defLine = line;

    }


    @Override
    public String toString(){
        String string = "";
        int tempBase = this.baseAddress;
        for(int c=0; c<words.length; c++){
            string = string+tempBase+": "+words[c]+"\n";
            tempBase++;
        }

        return string;
    }




}
