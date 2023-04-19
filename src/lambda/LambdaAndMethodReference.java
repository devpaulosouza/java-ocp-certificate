package lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.*;

public class LambdaAndMethodReference {

    static void staticMR() {
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 7, 4, 5));
        Consumer<List<Integer>> consumer = i -> Collections.sort(i);
        consumer.accept(integers);

        System.out.println(integers);

        List<Integer> integersMR = new ArrayList<>(List.of(1, 2, 7, 4, 5));
        Consumer<List<Integer>> consumerMR = Collections::sort;
        consumerMR.accept(integersMR);

        System.out.println(integers);
    }

    static void boundMR() {
        String name = "Mr. Joe Bloggs";
        Predicate<String> predicate = prefix -> name.startsWith(prefix);

        System.out.println(predicate.test("Mr."));
        System.out.println(predicate.test("Ms."));

        Predicate<String> predicateMR = name::startsWith;

        System.out.println(predicateMR.test("Mr."));
        System.out.println(predicateMR.test("Ms."));
    }

    static void unbound() {
        Predicate<String> predicate = s -> s.isEmpty();

        System.out.println(predicate.test(""));
        System.out.println(predicate.test("xyz"));

        Predicate<String> predicateMR = String::isEmpty;

        System.out.println(predicateMR.test(""));
        System.out.println(predicateMR.test("xyz"));

        BiPredicate<String, String> lambda2 = (str, prefix) -> str.startsWith(prefix);
        System.out.println(lambda2.test("Mr. Joe Bloggs", "Mr.")); // true
        BiPredicate<String, String> methodRef2 = String::startsWith;
        System.out.println(methodRef2.test("Mr. Joe Bloggs", "Ms.")); // false
    }

    static void constructoMR() {
        Supplier<List<String>> lambda = () -> new ArrayList();
        List<String> list = lambda.get();
        System.out.println(list);

        Supplier<List<String>> methodRef = ArrayList::new;
        list = methodRef.get();
        System.out.println(list);

        Function<Integer, List<String>> lambda2 = n -> new ArrayList(n);
        list = lambda2.apply(20);
        System.out.println(list);

        Function<Integer, List<String>> methodRef2 = ArrayList::new; // context!
        list = methodRef2.apply(20);
        System.out.println(list);
    }

    public static void main(String[] args) {
        staticMR();
        boundMR();
        unbound();
        constructoMR();
    }

}
