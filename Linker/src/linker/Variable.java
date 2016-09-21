package linker;

/**
 * Created by jeffersonvivanco on 9/17/16.
 */
public class Variable {
    private String name;
    private int value = -111;
    private int modNum;
    private String multiplyDefined="";
    private String defAddExceedsSize = "";
    private boolean isUsed = false;
    private boolean isAlreadyDefined = false;

    public Variable(String name, int value, int modNum){
        this.name = name;
        this.value = value;
        this.modNum = modNum;

    }
    public Variable(String name, int modNum){
        this.name = name;
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
    public void isMultiplyDefined(){
        multiplyDefined = "Error: This variable is multiply defined; first value used.";
        this.isAlreadyDefined = true;
    }
    public void varUsed(){
        this.isUsed = true;
    }
    public boolean isVarUsed(){
        return this.isUsed;
    }
    public void setValue(int i){
        this.value = i;

        this.defAddExceedsSize = "Error: Address appearing in definition exceeds the size of module "+modNum+", zero used at this module.";
    }

    @Override
    public String toString(){
        return this.name + " = " + this.value+" "+this.multiplyDefined+" "+this.defAddExceedsSize;
    }

}
