import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.*;

public class SomeStuff {

    public static void main(String[] args) {
        var orders = List.<Order>of();

        Map<String, Set<LineItem>> itemsByCustomerName
            = orders.stream().collect(
            groupingBy(o -> o.name, flatMapping(order -> order.lineItems.stream(), toSet())));

        var nn= orders.stream().collect(groupingBy(o -> o.name, mapping(o-> o.lineItems, toList())));


//        Predicate
//        Predicate.not()
        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> System.out.println("hello"));

        Scanner scanner = new Scanner(" a b c b n mmm  mm f  w ");
//        scanner.tokens().forEach(System.out::println);
        
//        System.out.println(nn);
//        orders.stream().collect(gro)
        Scanner scanner1 = new Scanner(System.in);
        scanner1.nextLine();

    }

    private static class LineItem {
    }

    private static class Order {
        public String name;
        public List<LineItem> lineItems;

        public Order(String name, List<LineItem> lineItems) {
            this.name = name;
            this.lineItems = lineItems;
        }
    }
}
