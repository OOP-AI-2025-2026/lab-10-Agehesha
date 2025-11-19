package ua.opnu;

import ua.opnu.util.Customer;
import ua.opnu.util.DataProvider;
import ua.opnu.util.Order;
import ua.opnu.util.Product;

import java.util.*;
import java.util.stream.Collectors;

public class HardTasks {

    private final List<Customer> customers = DataProvider.customers;
    private final List<Order> orders = DataProvider.orders;
    private final List<Product> products = DataProvider.products;

    public static void main(String[] args) {
        HardTasks tasks = new HardTasks();

        // Для того, щоб побачити в консолі результат роботи методу, разкоментуйте відповідний рядок коду

        // Завдання 1
        Objects.requireNonNull(tasks.getBooksWithPrice(), "Method getBooksWithPrice() returns null")
                .forEach(System.out::println);

        // Завдання 2
        Objects.requireNonNull(tasks.getOrdersWithBabyProducts(), "Method getOrdersWithBabyProducts() returns null")
                .forEach(System.out::println);

        // Завдання 3
        // Objects.requireNonNull(tasks.applyDiscountToToys(),"Method applyDiscountToToys() returns null").forEach(System.out::println);

        // Завдання 4
        // System.out.println(Objects.requireNonNull(tasks.getCheapestBook(),"Method getCheapestBook() returns null").get());

        // Завдання 5
        // Objects.requireNonNull(tasks.getRecentOrders(),"Method getRecentOrders() returns null").forEach(System.out::println);

        // Завдання 6
        // DoubleSummaryStatistics statistics = Objects.requireNonNull(tasks.getBooksStats(), "Method getBooksStats() returns null");
        // System.out.printf("count = %1$d, average = %2$f, max = %3$f, min = %4$f, sum = %5$f%n",
        //         statistics.getCount(), statistics.getAverage(), statistics.getMax(), statistics.getMin(), statistics.getSum());

        // Завдання 7
        // Objects.requireNonNull(tasks.getOrdersProductsMap(),"Method getOrdersProductsMap() returns null")
        //         .forEach((id, size) -> System.out.printf("%1$d : %2$d\n", id, size));

        // Завдання 8
        // Objects.requireNonNull(tasks.getProductsByCategory(), "Method getProductsByCategory() returns null")
        //         .forEach((name, list) -> System.out.printf("%1$s : %2$s\n", name, Arrays.toString(list.toArray())));
    }

    /**
     * Завдання 1.
     * Повертає товари з категорії "Books" з ціною > 100.
     * Повинні вийти товари з id 7, 9, 16, 17, 24.
     */
    public List<Product> getBooksWithPrice() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .filter(p -> p.getPrice() > 100)
                .toList();
    }

    /**
     * Завдання 2.
     * Повертає замовлення, які містять хоча б один товар з категорії "Baby".
     */
    public List<Order> getOrdersWithBabyProducts() {
        return orders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(p -> "Baby".equals(p.getCategory())))
                .toList();
    }

    /**
     * Завдання 3.
     * Знижка 50% на товари категорії "Toys".
     */
    public List<Product> applyDiscountToToys() {
        return products.stream()
                .filter(p -> "Toys".equals(p.getCategory()))
                .peek(p -> p.setPrice(p.getPrice() * 0.5))
                .toList();
    }

    /**
     * Завдання 4.
     * Повертає найдешевший товар з категорії "Books".
     */
    public Optional<Product> getCheapestBook() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .min(Comparator.comparingDouble(Product::getPrice));
    }

    /**
     * Завдання 5.
     * Повертає три найбільш "свіжі" замовлення за датою orderDate.
     * Порядок має бути: 23, 30, 33.
     */
    public List<Order> getRecentOrders() {
        return orders.stream()
                .sorted(
                        Comparator.comparing(Order::getOrderDate).reversed()
                                .thenComparing(Order::getId)
                )
                .limit(3)
                .toList();
    }

    /**
     * Завдання 6.
     * Статистика по цінах усіх товарів категорії "Books".
     */
    public DoubleSummaryStatistics getBooksStats() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    /**
     * Завдання 7.
     * Повертає мапу: id замовлення -> кількість товарів.
     */
    public Map<Integer, Integer> getOrdersProductsMap() {
        return orders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts() == null ? 0 : order.getProducts().size()
                ));
    }

    /**
     * Завдання 8.
     * Групує товари за категоріями.
     * Повертає Map<String, List<Integer>>, де List — id товарів.
     */
    public Map<String, List<Integer>> getProductsByCategory() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getId, Collectors.toList())
                ));
    }
}
