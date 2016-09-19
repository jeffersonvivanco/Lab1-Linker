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
    public Module findModule(int baseAddress){
        Module m = null;
        for(int x=0; x<modules.size(); x++){
            if(modules.get(x).getBaseAddress() == baseAddress){
                m = modules.get(x);
            }
        }
        return m;
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
