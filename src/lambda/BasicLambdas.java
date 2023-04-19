package lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

interface Printable<T> {
	void print(T t);
}

interface Retrievable<T> {
	T retrieve();
}

interface Evaluate<T> {
	boolean isNegative(T t);
}

interface Functionable<T,R> {
	R applyThis(T t);
}

class Person {
	private int age;
	private String name;
	private double height;

	public Person(String name, int age, double height) {
		this.age = age;
		this.name = name;
		this.height = height;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "lambda.Person{" +
				"age=" + age +
				", name='" + name + '\'' +
				", height=" + height +
				'}';
	}
}

public class BasicLambdas {
	static void consumer() {
		Printable<String> lambda = s -> System.out.println(s);
		Printable<String> mr = System.out::println;
		lambda.print("lambda.Printable lambda");
		mr.print("lambda.Printable lambda");
	}

	static void supplier() {
		Retrievable<Integer> lambda = () -> 77;
		Supplier<Integer> s = () -> 77;

		System.out.println(lambda.retrieve());
		System.out.println(s.get());
	}

	static void predicate() {
		Evaluate<Integer> lambda = i -> i < 0;
		Predicate<Integer> predicate = i -> i < 0;

		System.out.println(lambda.isNegative(-1));
		System.out.println(predicate.test(1));

		Predicate<Integer> isEven = i -> i % 2 == 0;
		System.out.println(check(4, isEven));
		System.out.println(check(7, isEven));

		Predicate<String> beginsWithMr = s -> s.startsWith("Mr.");
		System.out.println(check("Mr. Joe Bloggs", beginsWithMr));
		System.out.println(check("“Ms. Ann Bloggs", beginsWithMr));

		Predicate<Person> isAdult = p -> p.getAge() >= 18;
		Person mike = new Person("Mike", 33, 1.8);
		Person ann = new Person("Ann", 13, 1.4);

		System.out.println(check(mike, isAdult));
		System.out.println(check(ann, isAdult));
	}

	static void function() {
		Functionable<Integer, String> functionable = i -> "Number is " + i;

		System.out.println(functionable.applyThis(25));

		Function<Integer, String> function = i -> "Number is " + i;

		System.out.println(function.apply(25));
	}

	static <T> boolean check(T t, Predicate<T> p) {
		return p.test(t);
	}

	private static List<Person> getPeople() {
		List<Person> result = new ArrayList<>();

		result.add(new Person("Mike", 33, 1.8));
		result.add(new Person("Mary", 25, 1.4));
		result.add(new Person("Alan", 34, 1.7));
		result.add(new Person("Zoe", 30, 1.5));

		return result;
	}

	private static void sortAge(List<Person> listPeople) {
		Comparator<Person> comparator = (p1, p2) -> p1.getAge() - p2.getAge();
		listPeople.sort(comparator);
		listPeople.forEach(System.out::println);
	}

	private static void sortName(List<Person> listPeople) {
		Comparator<Person> comparator = (p1, p2) -> p1.getName().compareTo(p2.getName());
		listPeople.sort(comparator);
		listPeople.forEach(System.out::println);
	}

	private static void sortHeight(List<Person> listPeople) {
		Comparator<Person> comparator = (p1, p2) -> (int) (p1.getHeight() * 100) - (int) (p2.getHeight() * 100);
		listPeople.sort(comparator);
		listPeople.forEach(System.out::println);
	}


	private static void sortAgeMethodReference(List<Person> listPeople) {
		Comparator<Person> comparator = Comparator.comparingInt(Person::getAge);
		listPeople.sort(comparator);
		listPeople.forEach(System.out::println);
	}

	private static void sortNameMethodReference(List<Person> listPeople) {
		Comparator<Person> comparator = Comparator.comparing(Person::getName);
		listPeople.sort(comparator);
		listPeople.forEach(System.out::println);
	}

	private static void sortHeightMethodReference(List<Person> listPeople) {
		Comparator<Person> comparator = Comparator.comparingInt(p -> (int) (p.getHeight() * 100));
		listPeople.sort(comparator);
		listPeople.forEach(System.out::println);
	}

	public static void main(String[] args) {
		consumer();
		supplier();
		predicate();
		function();

		List<Person> listPeople = getPeople();
		sortAge(listPeople);
		sortName(listPeople);
		sortHeight(listPeople);
	}
}
