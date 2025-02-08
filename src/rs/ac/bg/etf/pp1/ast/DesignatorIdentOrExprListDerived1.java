// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentOrExprListDerived1 extends DesignatorIdentOrExprList {

    private DesignatorIdentOrExpr DesignatorIdentOrExpr;
    private DesignatorIdentOrExprList DesignatorIdentOrExprList;

    public DesignatorIdentOrExprListDerived1 (DesignatorIdentOrExpr DesignatorIdentOrExpr, DesignatorIdentOrExprList DesignatorIdentOrExprList) {
        this.DesignatorIdentOrExpr=DesignatorIdentOrExpr;
        if(DesignatorIdentOrExpr!=null) DesignatorIdentOrExpr.setParent(this);
        this.DesignatorIdentOrExprList=DesignatorIdentOrExprList;
        if(DesignatorIdentOrExprList!=null) DesignatorIdentOrExprList.setParent(this);
    }

    public DesignatorIdentOrExpr getDesignatorIdentOrExpr() {
        return DesignatorIdentOrExpr;
    }

    public void setDesignatorIdentOrExpr(DesignatorIdentOrExpr DesignatorIdentOrExpr) {
        this.DesignatorIdentOrExpr=DesignatorIdentOrExpr;
    }

    public DesignatorIdentOrExprList getDesignatorIdentOrExprList() {
        return DesignatorIdentOrExprList;
    }

    public void setDesignatorIdentOrExprList(DesignatorIdentOrExprList DesignatorIdentOrExprList) {
        this.DesignatorIdentOrExprList=DesignatorIdentOrExprList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorIdentOrExpr!=null) DesignatorIdentOrExpr.accept(visitor);
        if(DesignatorIdentOrExprList!=null) DesignatorIdentOrExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorIdentOrExpr!=null) DesignatorIdentOrExpr.traverseTopDown(visitor);
        if(DesignatorIdentOrExprList!=null) DesignatorIdentOrExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorIdentOrExpr!=null) DesignatorIdentOrExpr.traverseBottomUp(visitor);
        if(DesignatorIdentOrExprList!=null) DesignatorIdentOrExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIdentOrExprListDerived1(\n");

        if(DesignatorIdentOrExpr!=null)
            buffer.append(DesignatorIdentOrExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorIdentOrExprList!=null)
            buffer.append(DesignatorIdentOrExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentOrExprListDerived1]");
        return buffer.toString();
    }
}
