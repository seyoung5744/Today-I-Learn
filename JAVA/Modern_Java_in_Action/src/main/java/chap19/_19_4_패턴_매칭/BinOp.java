package chap19._19_4_패턴_매칭;

public class BinOp extends Expr {

    String opname;
    Expr left, right;

    public BinOp(String opname, Expr left, Expr right) {
        this.opname = opname;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + " " + opname + " " + right + ")";
    }
}
