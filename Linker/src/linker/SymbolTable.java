package linker;

import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class SymbolTable {
    private ArrayList<Variable> variables;

    public SymbolTable(){
        variables = new ArrayList<>();
    }
    public void addVariable(Variable v){
        variables.add(v);
    }
    public int sizeOfTable(){
        return variables.size();
    }
    public Variable findVariable(String name){
        Variable v = null;
        for(int j=0; j<variables.size(); j++){
            if(variables.get(j).getName().equals(name)){
                v = variables.get(j);
            }
        }
        return v;
    }
    public int checkIfExists(String vName){
        int check = -1;
        for(int h=0; h<variables.size(); h++){
            if(variables.get(h).getName().equals(vName)){
                check = 1;
                variables.get(h).isMultiplyDefined();
            }

        }
        return check;
    }
    public ArrayList<Variable> varsInMod(int modNum){
        ArrayList<Variable> array = new ArrayList<>();
        for(int i=0; i<variables.size(); i++){
            if(variables.get(i).getModNum() == modNum){
                array.add(variables.get(i));
            }
        }
        return array;
    }
    public Variable getVariable(int x){
        return variables.get(x);
    }
    @Override
    public String toString(){
        String string = "Symbol Table\n\n";
        for(int x=0; x<variables.size(); x++){
            string = string + variables.get(x) + "\n";
        }
        return string;
    }

}
