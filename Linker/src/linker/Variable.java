package linker;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Variable {
    private String name;
    private int value = -111;
    private int modNum;
    private String multiplyDefined="";

    public Variable(String name, int value, int modNum){
        this.name = name;
        this.value = value;
        this.modNum = modNum;

    }
    public Variable(String name){
        this.name = name;
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
    public void isMultiplyDefined(){
        multiplyDefined = "Error: This variable is multiply defined; first value used.";
    }
    @Override
    public String toString(){
        return this.name + " = " + this.value+" "+this.multiplyDefined;
    }

}
