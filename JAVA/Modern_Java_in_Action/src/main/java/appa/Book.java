package appa;

import io.reactivex.annotations.NonNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Author(name = "Raoul")
@Author(name = "Mario")
@Author(name = "Alan")
public class Book {

    public static void main(String[] args) {
//        Author annotation = Book.class.getAnnotation(Author.class);
//        System.out.println(annotation.name());

        Author[] authors = Book.class.getAnnotationsByType(Author.class);
        Arrays.asList(authors).stream().forEach(a -> {
            System.out.println(a.name());
        });

        cleanCars(Collections.<Book>emptyList());
    }

    static void cleanCars(List<Book> cars) {}
}
