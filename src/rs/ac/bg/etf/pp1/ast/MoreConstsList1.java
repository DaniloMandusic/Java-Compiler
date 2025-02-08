// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class MoreConstsList1 extends MoreConstsList {

    private String constName;
    private ConstType ConstType;
    private MoreConstsList MoreConstsList;

    public MoreConstsList1 (String constName, ConstType ConstType, MoreConstsList MoreConstsList) {
        this.constName=constName;
        this.ConstType=ConstType;
        if(ConstType!=null) ConstType.setParent(this);
        this.MoreConstsList=MoreConstsList;
        if(MoreConstsList!=null) MoreConstsList.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public ConstType getConstType() {
        return ConstType;
    }

    public void setConstType(ConstType ConstType) {
        this.ConstType=ConstType;
    }

    public MoreConstsList getMoreConstsList() {
        return MoreConstsList;
    }

    public void setMoreConstsList(MoreConstsList MoreConstsList) {
        this.MoreConstsList=MoreConstsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstType!=null) ConstType.accept(visitor);
        if(MoreConstsList!=null) MoreConstsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstType!=null) ConstType.traverseTopDown(visitor);
        if(MoreConstsList!=null) MoreConstsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstType!=null) ConstType.traverseBottomUp(visitor);
        if(MoreConstsList!=null) MoreConstsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreConstsList1(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(ConstType!=null)
            buffer.append(ConstType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreConstsList!=null)
            buffer.append(MoreConstsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstsList1]");
        return buffer.toString();
    }
}
