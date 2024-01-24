package chap03;

public enum Color {
    RED("빨간색"),
    GREEN("녹색");

    private String message;

    Color(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
