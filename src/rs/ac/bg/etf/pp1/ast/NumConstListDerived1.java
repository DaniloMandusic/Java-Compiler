// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class NumConstListDerived1 extends NumConstList {

    private Integer N1;
    private NumConstList NumConstList;

    public NumConstListDerived1 (Integer N1, NumConstList NumConstList) {
        this.N1=N1;
        this.NumConstList=NumConstList;
        if(NumConstList!=null) NumConstList.setParent(this);
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public NumConstList getNumConstList() {
        return NumConstList;
    }

    public void setNumConstList(NumConstList NumConstList) {
        this.NumConstList=NumConstList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NumConstList!=null) NumConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NumConstList!=null) NumConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NumConstList!=null) NumConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumConstListDerived1(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        if(NumConstList!=null)
            buffer.append(NumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstListDerived1]");
        return buffer.toString();
    }
}
