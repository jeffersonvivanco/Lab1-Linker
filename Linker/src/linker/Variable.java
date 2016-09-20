package linker;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Variable {
    private String name;
    private int value;
    private int modNum;

    public Variable(String name, int value, int modNum){
        this.name = name;
        this.value = value;
        this.modNum = modNum;

    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    public int getModNum(){
        return this.modNum;
    }
    @Override
    public String toString(){
        return this.name + " = " + this.value;
    }

}
