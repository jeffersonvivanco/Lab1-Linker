package linker;



import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class ModuleList {
    private ArrayList<Module> modules;
    private ArrayList<String> allVarsUsed = new ArrayList<>();
    private SymbolTable symbolTable = null;
    public ModuleList(){
        modules = new ArrayList<>();
    }

    public void addModule(Module m){
        modules.add(m);
    }
    public Module findModule(int baseAddress){
        Module m = null;
        for(int x=0; x<modules.size(); x++){
            if(modules.get(x).getBaseAddress() == baseAddress){
                m = modules.get(x);
            }
        }
        return m;
    }
    public void addVarToListUsed(String v){
        boolean isThere = false;
        for(int index=0; index<allVarsUsed.size(); index++){
            if(v.equals(allVarsUsed.get(index)))
                isThere  = true;
        }
        if(isThere == false){
            allVarsUsed.add(v);
        }
    }
    public void setSymbolTable(SymbolTable s){
        this.symbolTable = s;
    }
    public void checkIfDefinedButNotUsed(){
        boolean check = false;
        for(int y=0; y<this.symbolTable.sizeOfTable();y++){
            for(int x=0; x<allVarsUsed.size(); x++){
                if(this.symbolTable.getVariable(y).getName().equals(allVarsUsed.get(x))){
                    check = true;
                }
            }
            if(check == false){
                System.out.println("Warning: "+this.symbolTable.getVariable(y).getName()+" was defined in module " +
                        this.symbolTable.getVariable(y).getModNum()+" but never used.");
            }
            check = false;
        }
    }
    @Override
    public String toString(){
        String string = "Memory Map\n";
        for(int v=0; v<modules.size();v++){
            string = string + modules.get(v).toString();
        }
        return string;
    }
}
