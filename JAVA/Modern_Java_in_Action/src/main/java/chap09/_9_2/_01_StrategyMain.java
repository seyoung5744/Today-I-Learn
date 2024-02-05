package chap09._9_2;

public class _01_StrategyMain {

    public static void main(String[] args) {
        // old school
        Validator numericValidator = new Validator(new IsNumeric());
        System.out.println(numericValidator.validate("aaa"));// false;
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        System.out.println(lowerCaseValidator.validate("bbbb")); // true

        // with lambda
        Validator numericValidator2 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(numericValidator2.validate("aaaa"));
        Validator lowerCaseValidator2 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(lowerCaseValidator2.validate("bbbbbbbb"));
    }

    public interface ValidationStrategy {

        boolean execute(String s);
    }

    private static class IsAllLowerCase implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    private static class IsNumeric implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    private static class Validator {

        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }
}
