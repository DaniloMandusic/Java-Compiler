// generated with ast extension for cup
// version 0.8
// 6/1/2025 17:42:5


package rs.ac.bg.etf.pp1.ast;

public class VarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private VarDeclWithoutSemi VarDeclWithoutSemi;

    public VarDecl (Type Type, VarDeclWithoutSemi VarDeclWithoutSemi) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclWithoutSemi=VarDeclWithoutSemi;
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclWithoutSemi getVarDeclWithoutSemi() {
        return VarDeclWithoutSemi;
    }

    public void setVarDeclWithoutSemi(VarDeclWithoutSemi VarDeclWithoutSemi) {
        this.VarDeclWithoutSemi=VarDeclWithoutSemi;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclWithoutSemi!=null) VarDeclWithoutSemi.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclWithoutSemi!=null)
            buffer.append(VarDeclWithoutSemi.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl]");
        return buffer.toString();
    }
}
