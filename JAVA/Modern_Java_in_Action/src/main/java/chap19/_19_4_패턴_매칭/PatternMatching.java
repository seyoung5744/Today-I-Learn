package chap19._19_4_패턴_매칭;

import java.util.function.Function;
import java.util.function.Supplier;

public class PatternMatching {

    public static void main(String[] args) {
        simplify();

        Expr e = new BinOp("+", new Number(5), new BinOp("*", new Number(3), new Number(4)));
        Integer result = evaluate(e);
        System.out.println(e + " = " + result);
    }

    static <T> T myIf(boolean b, Supplier<T> truecase, Supplier<T> falsecase) {
        return b ? truecase.get() : falsecase.get();
    }

    static <T> T patternMatchExpr(Expr e, TriFunction<String, Expr, Expr, T> binopcase, Function<Integer, T> numcase, Supplier<T> defaultcase) {
//        return (e instanceof BinOp) ? binopcase.apply(((BinOp) e).opname, ((BinOp) e).left, ((BinOp) e).right) :
//            (e instanceof Number) ? numcase.apply(((Number) e).val) : defaultcase.get();
        if (e instanceof BinOp) {
            return binopcase.apply(((BinOp) e).opname, ((BinOp) e).left, ((BinOp) e).right);
        } else if (e instanceof Number) {
            return numcase.apply(((Number) e).val);
        } else {
            return defaultcase.get();
        }
    }

    public static void simplify() {
        TriFunction<String, Expr, Expr, Expr> binopcase = (opname, left, right) -> { // BinOp 표현식 처리
            if ("+".equals(opname)) { // 더하기 처리
                if (left instanceof Number && ((Number) left).val == 0) {
                    return right;
                }
                if (right instanceof Number && ((Number) right).val == 0) {
                    return left;
                }
            }

            if ("*".equals(opname)) { // 곱셈 처리
                if (left instanceof Number && ((Number) left).val == 1) {
                    return right;
                }
                if (right instanceof Number && ((Number) right).val == 1) {
                    return left;
                }
            }

            return new BinOp(opname, left, right);
        };

        Function<Integer, Expr> numcase = val -> new Number(val); // 숫자 처리
        Supplier<Expr> defaultcase = () -> new Number(0); // 수식을 인식할 수 없을 때 기본 처리

        Expr e = new BinOp("+", new Number(5), new Number(0));
        Expr match = patternMatchExpr(e, binopcase, numcase, defaultcase);
        if (match instanceof Number) {
            System.out.println("Number: " + match);
        } else if (match instanceof BinOp) {
            System.out.println("BinOp: " + match);
        }
    }

    private static Integer evaluate(Expr e) {
        Function<Integer, Integer> numcase = val -> val;
        Supplier<Integer> defaultcase = () -> 0;

        TriFunction<String, Expr, Expr, Integer> binopcase = (opname, left, right) -> {
            if ("+".equals(opname)) {
                if (left instanceof Number && right instanceof Number) {
                    return ((Number) left).val + ((Number) right).val;
                }
                if (right instanceof Number && left instanceof BinOp) {
                    return ((Number) right).val + evaluate(left);
                }
                if (left instanceof Number && right instanceof BinOp) {
                    return ((Number) left).val + evaluate(right);
                }
                if (left instanceof BinOp && right instanceof BinOp) {
                    return evaluate(left) + evaluate(right);
                }
            }
            if ("*".equals(opname)) {
                if (left instanceof Number && right instanceof Number) {
                    return ((Number) left).val * ((Number) right).val;
                }
                if (right instanceof Number && left instanceof BinOp) {
                    return ((Number) right).val * evaluate(left);
                }
                if (left instanceof Number && right instanceof BinOp) {
                    return ((Number) left).val * evaluate(right);
                }
                if (left instanceof BinOp && right instanceof BinOp) {
                    return evaluate(left) * evaluate(right);
                }
            }
            return defaultcase.get();
        };

        return patternMatchExpr(e, binopcase, numcase, defaultcase);
    }
}
