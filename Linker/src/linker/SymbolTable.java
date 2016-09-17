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

    @Override
    public String toString(){
        String string = "Symbol Table\n\n";
        for(int x=0; x<variables.size(); x++){
            string = string + variables.get(x) + "\n";
        }
        return string;
    }

}
