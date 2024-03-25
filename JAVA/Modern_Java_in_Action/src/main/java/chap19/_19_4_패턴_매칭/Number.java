package chap19._19_4_패턴_매칭;

public class Number extends Expr {

    int val;

    public Number(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
