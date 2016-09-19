package linker;


import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Module {

    private ArrayList<Variable> listUsed = new ArrayList<>();
    private String[] words = {};
    private int baseAddress = 0;

    public Module(int baseAddress, String[] words){
        this.baseAddress = baseAddress;
        this.words = words;
    }
    public void setListUsed(ArrayList<Variable>listUsed){
        this.listUsed  = listUsed;
    }
    public int getBaseAddress(){
        return this.baseAddress;
    }

    public void computeAddresses(){
        for(int i=0; i<words.length; i++){
            if(words[i].endsWith("1") || words[i].endsWith("2"))
                words[i] = words[i].substring(0,4);
            else if(words[i].endsWith("3")){
                int num = Integer.parseInt(words[i].substring(0,4));
                num = num+baseAddress;
                words[i] = num+"";
            }
            else if(words[i].endsWith("4")){
                int index = Integer.parseInt(words[i].substring(1,4));
                if(index < 0 || index >= listUsed.size()){
                    words[i] = words[i].substring(0,4);
                }
                else{
                    Variable v = listUsed.get(index);
                    int wordAsInt = Integer.parseInt(words[i].substring(0,4))-index;
                    wordAsInt = wordAsInt+v.getValue();
                    words[i] = wordAsInt+"";
                }
            }
            else{
                //do nothing, it must be an error
            }

        }
        this.words = words;
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
