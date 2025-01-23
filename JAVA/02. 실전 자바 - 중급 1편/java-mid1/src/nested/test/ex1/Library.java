package nested.test.ex1;

public class Library {

    private final Book[] books;
    private int size;

    public Library(int maxSize) {
        books = new Book[maxSize];
    }

    public void addBook(String title, String author) {

        if (size >= books.length) {
            System.out.println("도서관 저장 공간이 부족합니다.");
            return;
        }

        books[size++] = new Book(title, author);
    }

    public void showBooks() {
        System.out.println("== 책 목록 출력 ==");
        for (int i = 0; i < size; i++) {
            System.out.println(books[i]);
        }
    }

    private static class Book {

        private final String title;
        private final String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        @Override
        public String toString() {
            return "도서 제목: " + title + ", 저자: " + author;
        }
    }
}
