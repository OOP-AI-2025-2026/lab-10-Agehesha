package ua.opnu;

import ua.opnu.util.Order;
import ua.opnu.util.Product;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class HardTasks {



    public List<Product> getBooksWithPrice() {
        return getBooksWithPrice(List.of());
    }

    public List<Order> getOrdersWithBabyProducts() {
        return getOrdersWithBabyProducts(List.of());
    }

    public List<Product> applyDiscountToToys() {
        return applyDiscountToToys(List.of());
    }

    public Optional<Product> getCheapestBook() {
        return getCheapestBook(List.of());
    }

    public List<Order> getRecentOrders() {
        return getRecentOrders(List.of());
    }

    public DoubleSummaryStatistics getBooksStats() {
        return getBooksStats(List.of());
    }

    public Map<Integer, Long> getOrdersProductsMap() {
        return getOrdersProductsMap(List.of());
    }

    public Map<String, List<Product>> getProductsByCategory() {
        return getProductsByCategory(List.of());
    }


    public List<Product> getBooksWithPrice(List<Product> products) {
        return products.stream()
                .filter(p -> "Books".equalsIgnoreCase(p.getCategory()))
                .filter(p -> p.getPrice() > 100)
                .collect(Collectors.toList());
    }


    public List<Order> getOrdersWithBabyProducts(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(p -> "Baby".equalsIgnoreCase(p.getCategory())))
                .collect(Collectors.toList());
    }


    public List<Product> applyDiscountToToys(List<Product> products) {
        return products.stream()
                .filter(p -> "Toys".equalsIgnoreCase(p.getCategory()))
                .peek(p -> p.setPrice(p.getPrice() * 0.5))
                .collect(Collectors.toList());
    }


    public Optional<Product> getCheapestBook(List<Product> products) {
        return products.stream()
                .filter(p -> "Books".equalsIgnoreCase(p.getCategory()))
                .min(Comparator.comparingDouble(Product::getPrice));
    }


    public List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }


    public DoubleSummaryStatistics getBooksStats(List<Product> products) {
        return products.stream()
                .filter(p -> "Books".equalsIgnoreCase(p.getCategory()))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }


    public Map<Integer, Long> getOrdersProductsMap(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts() == null
                                ? 0L
                                : (long) order.getProducts().size()
                ));
    }

    public Map<String, List<Product>> getProductsByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }
}

