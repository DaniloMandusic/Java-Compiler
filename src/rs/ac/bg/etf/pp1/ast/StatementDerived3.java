// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived3 extends Statement {

    public StatementDerived3 () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDerived3(\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived3]");
        return buffer.toString();
    }
}
