package linker;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Variable {
    private String name;
    private int value;

    public Variable(String name, int value){
        this.name = name;
        this.value = value;

    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    @Override
    public String toString(){
        return this.name + " = " + this.value; 
    }

}
