package linker;


import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Module {

    private ArrayList<Variable> listUsed;
    private String[] words;
    private int baseAddress;

    public Module(int baseAddress){
        this.baseAddress = baseAddress;
    }
    public void setListUsed(ArrayList<Variable>listUsed){
        this.listUsed  = listUsed;
    }
    public int getBaseAddress(){
        return this.baseAddress;
    }
    public void setWords(String[] words){
        this.words = words;
    }
    @Override
    public String toString(){
        String string = "Words\n";
//        for(int c=0; c<words.length; c++){
//            string = string+words[c]+" ";
//        }
        return string;
    }



}
