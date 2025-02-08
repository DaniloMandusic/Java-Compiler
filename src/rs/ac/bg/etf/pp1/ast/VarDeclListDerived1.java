// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListDerived1 extends VarDeclList {

    private VarDeclWithoutSemi VarDeclWithoutSemi;

    public VarDeclListDerived1 (VarDeclWithoutSemi VarDeclWithoutSemi) {
        this.VarDeclWithoutSemi=VarDeclWithoutSemi;
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.setParent(this);
    }

    public VarDeclWithoutSemi getVarDeclWithoutSemi() {
        return VarDeclWithoutSemi;
    }

    public void setVarDeclWithoutSemi(VarDeclWithoutSemi VarDeclWithoutSemi) {
        this.VarDeclWithoutSemi=VarDeclWithoutSemi;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListDerived1(\n");

        if(VarDeclWithoutSemi!=null)
            buffer.append(VarDeclWithoutSemi.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListDerived1]");
        return buffer.toString();
    }
}
