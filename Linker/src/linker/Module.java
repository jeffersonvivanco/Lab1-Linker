package linker;


import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Module {

    private ArrayList<Variable> listUsed;
    private String[] words;
    private int length;

    public Module(ArrayList<Variable> listUsed, String[] words, int length){
        this.listUsed = listUsed;
        this.words = words;
        this.length = length;
    }
    @Override
    public String toString(){
        String string = "Words\n";
        for(int c=0; c<words.length; c++){
            string = string+words[c]+" ";
        }
        return string;
    }


}
