// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFactor extends Factor {

    private Designator Designator;
    private OptionalParens OptionalParens;

    public DesignatorFactor (Designator Designator, OptionalParens OptionalParens) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.OptionalParens=OptionalParens;
        if(OptionalParens!=null) OptionalParens.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public OptionalParens getOptionalParens() {
        return OptionalParens;
    }

    public void setOptionalParens(OptionalParens OptionalParens) {
        this.OptionalParens=OptionalParens;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(OptionalParens!=null) OptionalParens.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(OptionalParens!=null) OptionalParens.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(OptionalParens!=null) OptionalParens.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFactor(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalParens!=null)
            buffer.append(OptionalParens.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFactor]");
        return buffer.toString();
    }
}
