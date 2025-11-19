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
     * Має повернути замовлення з id:
     * 3, 4, 7, 8, 9, 10, 11, 14, 16, 17, 19, 20, 27, 28, 29, 30, 32, 34,
     * 37, 38, 40, 44, 45, 47, 48, 50.
     */
    public List<Order> getOrdersWithBabyProducts() {
        return orders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(p -> "Baby".equals(p.getCategory())))
                .toList();
    }

    /**
     * Завдання 3.
     * Знаходить товари категорії "Toys", застосовує знижку 50% до їхньої ціни
     * (price = price * 0.5) та повертає список цих товарів.
     *
     * Вірні id та нові ціни:
     * id=2  price=6.33
     * id=4  price=268.4
     * id=6  price=73.26
     * id=11 price=47.75
     * id=13 price=147.685
     * id=21 price=47.73
     * id=26 price=179.635
     * id=27 price=393.495
     * id=28 price=158.045
     * id=29 price=386.39
     * id=30 price=455.73
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
     * Це товар з id = 17.
     */
    public Optional<Product> getCheapestBook() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .min(Comparator.comparingDouble(Product::getPrice));
    }

    /**
     * Завдання 5.
     * Повертає три найбільш "свіжі" замовлення за датою orderDate.
     * Повинні бути замовлення з id 23, 30, 33.
     */
public List<Order> getRecentOrders() {

    // id замовлень 23, 30, 33

    long skip = Math.max(0, orders.size() - 3L);

    return orders.stream()
            // сначала сортируем по дате, а при равной дате — по id
            .sorted(
                    Comparator.comparing(Order::getOrderDate)
                              .thenComparing(Order::getId)
            )
            // оставляем только три последних по дате (и id внутри даты)
            .skip(skip)
            .toList();
}

    /**
     * Завдання 6.
     * Статистика по цінах усіх товарів категорії "Books".
     * Очікувані значення:
     * count = 5, average = 607.88, max = 893.44, min = 240.58, sum = 3039.4
     */
    public DoubleSummaryStatistics getBooksStats() {
        return products.stream()
                .filter(p -> "Books".equals(p.getCategory()))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    /**
     * Завдання 7.
     * Повертає мапу: id замовлення -> кількість товарів у цьому замовленні.
     * Тип: Map<Integer, Integer>.
     */
    public Map<Integer, Integer> getOrdersProductsMap() {
        return orders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts() == null
                                ? 0
                                : order.getProducts().size()
                ));
    }

    /**
     * Завдання 8.
     * Групує товари за категоріями.
     * Повертає Map<String, List<Integer>>, де:
     *   key   – назва категорії,
     *   value – список id товарів цієї категорії.
     *
     * Очікувана структура:
     * Grocery : [3, 12, 14, 23, 25]
     * Toys    : [2, 4, 6, 11, 13, 21, 26, 27, 28, 29, 30]
     * Games   : [1, 5, 19]
     * Baby    : [8, 10, 15, 18, 20, 22]
     * Books   : [7, 9, 16, 17, 24]
     */
    public Map<String, List<Integer>> getProductsByCategory() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getId)) // щоб id всередині списків були по зростанню
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getId, Collectors.toList())
                ));
    }
}
