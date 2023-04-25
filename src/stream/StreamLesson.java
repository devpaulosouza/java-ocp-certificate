package stream;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamLesson {

    private static void question1() {
        double average = IntStream.range(0, 5)
                .average()
                .orElseThrow();

        System.out.println(average);
    }

    private static Item buildItem(int id, String name) {
        return new Item(id, name);
    }

    private static void question2() {
        List<Item> items = List.of(buildItem(1, "Screw"), buildItem(2, "Nail"), buildItem(3, "Bold"));

        items.stream()
                .sorted(Comparator.comparing(Item::getName))
                .forEach(System.out::print);
    }

    private static void question3() {
        Stream<List<String>> stream = Stream.of(Arrays.asList("a", "b"), Arrays.asList("a", "c"));

        stream.filter(list -> list.contains("c"))
                .flatMap(Collection::stream)
                .forEach(System.out::println);
    }

    private static Person buildPerson(String name, String lastname, int age) {
        return new Person(name, lastname, age);
    }

    private static void question4() {
        int sum = Stream.of(1, 2, 3)
                .mapToInt(Integer::intValue)
                .sum();

        int max = Stream.of(1, 2, 3)
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();

        System.out.printf("Sum = %d, Max = %d\n", sum, max);

        Person personMaxAge = Stream.of(
                        buildPerson("Alan", "Burke", 22),
                        buildPerson("Zoe", "Peters", 20),
                        buildPerson("Peter", "Castle", 29)
                )
                .max(Comparator.comparing(Person::getAge))
                .orElseThrow();

        System.out.println(personMaxAge);

        Integer maxStreamWithoutIdentity = Stream.of(10, 47, 33, 23)
                .reduce((acc, value) -> acc < value ? value : acc)
                .orElseThrow();

        System.out.println(maxStreamWithoutIdentity);

        Integer maxStreamWithIdentity = Stream.of(10, 47, 33, 23)
                .reduce(Integer.MIN_VALUE, (acc, value) -> acc < value ? value : acc);

        System.out.println(maxStreamWithIdentity);
    }

    private static void question5() {
        Optional<String> grade1 = getGrade(50);
        Optional<String> grade2 = getGrade(55);

        System.out.println(grade1.orElse("UNKNOWN"));

        if (grade2.isPresent()) {
            grade2.ifPresent(System.out::println);
        } else {
            System.out.println(grade2.orElse("Empty"));
        }
    }

    private static Optional<String> getGrade(int marks) {
        Optional<String> grade;

        if (marks > 50) {
            grade = Optional.of("PASS");
        } else {
            grade = Optional.of("FAIL");
        }

        return grade;
    }

    private static Book buildBook(String title, double price) {
        return new Book(title, price);
    }

    private static void question6() {
        List<Book> books = List.of(
                buildBook("Thinking in Java", 30),
                buildBook("Java in 24 hrs", 20),
                buildBook("Java recipes", 10)
        );
        double averageGT10 = books
                .stream()
                .filter(book -> book.getPrice() > 10)
                .mapToDouble(Book::getPrice)
                .average()
                .orElseThrow();

        System.out.println(averageGT10);

        double averageGT90 = books
                .stream()
                .filter(book -> book.getPrice() > 90)
                .mapToDouble(Book::getPrice)
                .average()
                .orElse(0);

        System.out.println(averageGT90);
    }

    private static void printPrice(String title, Double value) {
        if (title.startsWith("A")) {
            System.out.println(value);
        }
    }

    private static void question7() {
        List<Book> books = List.of(
                buildBook("Atlas Shrugged", 10),
                buildBook("Freedom at Midnight", 5),
                buildBook("Gone with the wind", 5)
        );

        books
                .stream()
                .collect(Collectors.toMap(Book::getTitle, Book::getPrice))
                .forEach(StreamLesson::printPrice);

    }

    private static void question8() {
        List<Book> books = List.of(
                buildBook("Gone with the wind", 5),
                buildBook("Gone with the wind", 10),
                buildBook("Atlas shrugged", 15)
        );

        books.stream()
                .collect(Collectors.toMap(Book::getTitle, Book::getPrice, Double::sum))
                .forEach((title, value) -> System.out.println(title + " " + value));

    }

    private static void question9() {
        List<Person> people = List.of(
                buildPerson("Bob", null, 31),
                buildPerson("Paulo", null, 32),
                buildPerson("John", null, 33)
        );

        double average = people.stream()
                .filter(person -> person.getAge() < 30)
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);

        System.out.println(average);
    }

    private static void question10() {
        Optional<Double> price = Optional.ofNullable(20.0);

        price.ifPresent(System.out::println);
        System.out.println(price.orElse(0.0));
        System.out.println(price.orElseGet(() -> 0.0));

        Optional<Double> price2 = Optional.ofNullable(null);

        System.out.println(price2);

        if (price2.isEmpty()) {
            System.out.println("empty");
        }

        price2.ifPresent(System.out::println);

        Double x = price2.orElse(44.0);
        System.out.println(x);

        Optional<Double> price3 = Optional.ofNullable(null);
        Double z = price3.orElseThrow(() -> new RuntimeException("Bad Code"));
    }

    private static AnotherBook buildAnotherBook(String title, String genre) {
        return new AnotherBook(title, genre);
    }

    private static void question11() {

        List<AnotherBook> books = List.of(
                buildAnotherBook("Gone with the wind", "Fiction"),
                buildAnotherBook("Bourne Ultimatum", "Thriller"),
                buildAnotherBook("The Client", "Thriller")
        );

        List<String> genreList = books.stream().map(AnotherBook::getGenre).collect(Collectors.toList());

        genreList.forEach(System.out::println);
    }

    private static void question12() {
        DoubleStream doubles = DoubleStream.of(0, 2, 4);

        double sum = doubles.filter(value -> value % 2 != 0)
                .sum();

        System.out.println(sum);

        Stream<Double> evens = Stream.of(1.0, 3.0);

        double average = evens.filter(value -> value % 2 == 1)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        System.out.println(average);
    }

    private static void question13() {
        List<Integer> ls = Arrays.asList(11, 11, 22, 33, 33, 55, 66);

        boolean anyMatch = ls.stream()
                .distinct()
                .anyMatch(value -> value == 11);

        System.out.println(anyMatch);

        boolean noneMatch = ls.stream()
                .noneMatch(value -> value % 11 > 0);

        System.out.println(noneMatch);
    }

    private static void question14a() {
        AtomicInteger ai = new AtomicInteger();
        Stream<Integer> stream = Stream.of(11, 11, 22, 33)
                .parallel()
                .filter(e -> {
                    ai.incrementAndGet();
                    return e%2 == 0;
                });

        System.out.println(ai);
    }
    private static void question14() {
        AtomicInteger ai = new AtomicInteger();
        Stream<Integer> stream = Stream.of(11, 11, 22, 33).parallel();

        stream
                .filter(e -> {
                    ai.incrementAndGet();
                    return e%2 == 0;
                })
                .forEach(System.out::println);

        System.out.println(ai);
    }

    public static void main(String[] args) {
        question1();
        question2();
        question3();
        question4();
        question5();
        question6();
        question7();
        question8();
        question9();
        question10();
        question11();
        question12();
        question13();
        question14a();
        question14();
    }

}
