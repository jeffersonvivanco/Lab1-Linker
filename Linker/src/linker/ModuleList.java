package linker;



import java.util.ArrayList;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class ModuleList {
    private ArrayList<Module> modules;

    public ModuleList(){
        modules = new ArrayList<>();
    }

    public void addModule(Module m){
        modules.add(m);
    }
    @Override
    public String toString(){
        String string = "Modules\n";
        for(int v=0; v<modules.size();v++){
            string = string + modules.get(v).toString() + "\n";
        }
        return string;
    }
}
