package com.bookstore;

import com.bookstore.alg.Searching;
import com.bookstore.alg.Sorting;
import com.bookstore.ds.Queue;
import com.bookstore.ds.Stack;
import com.bookstore.model.BookItem;
import com.bookstore.model.Order;

import java.util.*;

/**
 * App demo (console) - Full version with catalog + stock validation.
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Core data structures
        Queue<Order> orderQueue = new Queue<>(8);
        Stack<Order> processedStack = new Stack<>();
        List<Order> createdOrders = new ArrayList<>();
        Map<String, Order> orderMap = new HashMap<>();

        // Predefined catalog with stock (title, author, stock)
        List<BookItem> storeBooks = new ArrayList<>();
        storeBooks.add(new BookItem("Clean Code", "Robert C. Martin", 5));
        storeBooks.add(new BookItem("The Pragmatic Programmer", "Andrew Hunt", 3));
        storeBooks.add(new BookItem("Design Patterns", "Erich Gamma", 2));
        storeBooks.add(new BookItem("Java Concurrency in Practice", "Brian Goetz", 4));
        storeBooks.add(new BookItem("Introduction to Algorithms", "Thomas H. Cormen", 1));

        System.out.println("=== Simple Online Bookstore Demo ===");

        // Print catalog immediately
        printCatalog(storeBooks);

        System.out.println("Enter orders (press Enter at 'Customer Name' to finish):");

        // 1) Input orders
        while (true) {
            System.out.print("\nCustomer Name (Enter to finish): ");
            String customer = sc.nextLine().trim();
            if (customer.isEmpty()) break;

            System.out.print("Shipping Address: ");
            String address = sc.nextLine().trim();

            // items chosen from catalog with validation
            List<BookItem> items = new ArrayList<>();
            while (true) {
                printCatalog(storeBooks);
                System.out.print("Choose book by number (Enter to finish the list): ");
                String sel = sc.nextLine().trim();
                if (sel.isEmpty()) break;

                int idx = -1;
                try {
                    idx = Integer.parseInt(sel) - 1;
                } catch (NumberFormatException ex) {
                    System.out.println(" -> Invalid input. Enter the book number from the list.");
                    continue;
                }

                if (idx < 0 || idx >= storeBooks.size()) {
                    System.out.println(" -> Invalid book number. Try again.");
                    continue;
                }

                BookItem storeItem = storeBooks.get(idx);
                if (storeItem.getQuantity() == 0) {
                    System.out.println(" -> Sorry, this book is out of stock. Choose another book.");
                    continue;
                }

                int qty;
                while (true) {
                    System.out.printf("Quantity (available %d): ", storeItem.getQuantity());
                    String sQty = sc.nextLine().trim();
                    try {
                        qty = Integer.parseInt(sQty);
                        if (qty <= 0) {
                            System.out.println(" -> Enter a positive integer.");
                            continue;
                        }
                        if (qty > storeItem.getQuantity()) {
                            System.out.println(" -> Not enough stock. Enter a quantity <= " + storeItem.getQuantity());
                            continue;
                        }
                        break;
                    } catch (NumberFormatException ex) {
                        System.out.println(" -> Enter a valid integer.");
                    }
                }

                // add a copy to order items (preserve catalog item separately)
                items.add(new BookItem(storeItem.getTitle(), storeItem.getAuthor(), qty));
                // reduce stock in catalog immediately (option: move this to confirmation step if needed)
                storeItem.setQuantity(storeItem.getQuantity() - qty);

                System.out.println(" -> Added to order: " + storeItem.getTitle() + " x" + qty);
            }

            if (items.isEmpty()) {
                System.out.println("No items added. Order cancelled.");
            } else {
                Order order = new Order(customer, address, items);
                createdOrders.add(order);
                orderQueue.enqueue(order);
                orderMap.put(order.getOrderId(), order);

                System.out.printf("Order created: Order ID = %s%n", order.getOrderId());
            }
        }

        // 2) Process queue with FIFO
        System.out.println("\n--- Processing orders from queue (FIFO) ---");
        int processedCount = 0;

        while (!orderQueue.isEmpty()) {
            processedCount++;
            Order o = orderQueue.dequeue();
            o.setConfirmed(true);

            // Sort items in the order for consistent display (Insertion Sort)
            Sorting.insertionSort(o.getItems(), Comparator.comparing(BookItem::getTitle));

            System.out.printf("Processed #%d: %s | Address: %s%n", processedCount, o.getCustomerName(), o.getAddress());
            for (BookItem b : o.getItems()) {
                System.out.printf("   - %s by %s (qty=%d)%n", b.getTitle(), b.getAuthor(), b.getQuantity());
            }

            processedStack.push(o);
        }

        // 3) LIFO display using Stack
        System.out.println("\n--- Processed Orders (LIFO - Stack) ---");
        int idx = 0;
        while (!processedStack.isEmpty()) {
            idx++;
            Order o = processedStack.pop();
            System.out.printf("%d) %s | Address: %s (confirmed=%s)%n", idx, o.getCustomerName(), o.getAddress(), o.isConfirmed());
        }

        // 4) Search by ID or customer name
        System.out.println("\n--- Search Orders ---");
        while (true) {
            System.out.print("Enter Order ID (Enter to skip): ");
            String searchId = sc.nextLine().trim();

            if (!searchId.isEmpty()) {
                Order found = Searching.mapLookup(orderMap, searchId);
                if (found != null) printFullOrder(found);
                else System.out.println(" => Order with this ID not found.");
            }

            System.out.print("Enter Customer Name (Enter to skip): ");
            String searchCustomer = sc.nextLine().trim();

            if (!searchCustomer.isEmpty()) {
                Order found = Searching.linearSearchByCustomer(createdOrders, searchCustomer);
                if (found != null) printFullOrder(found);
                else System.out.println(" => Order for this customer not found.");
            }

            if (searchId.isEmpty() && searchCustomer.isEmpty()) break;
            System.out.println("--- Continue searching or press Enter twice to exit ---");
        }

        // 5) Search by author
        System.out.println("\n--- Search Orders by Author ---");
        while (true) {
            System.out.print("Enter Author Name (Enter to finish): ");
            String authorQuery = sc.nextLine().trim();
            if (authorQuery.isEmpty()) break;

            boolean any = false;
            for (Order o : createdOrders) {
                boolean has = o.getItems().stream().anyMatch(b -> b.getAuthor().equalsIgnoreCase(authorQuery));
                if (has) {
                    printBriefOrder(o);
                    any = true;
                }
            }
            if (!any) {
                System.out.println(" => No orders found for this author.");
            }
        }

        System.out.println("\n=== End of Program ===");
    }

    // helper: print full order details
    private static void printFullOrder(Order o) {
        System.out.println("\n=== Order Information ===");
        System.out.println("Order ID : " + o.getOrderId());
        System.out.println("Customer : " + o.getCustomerName());
        System.out.println("Address  : " + o.getAddress());
        System.out.println("Confirmed: " + o.isConfirmed());

        // Sort again for consistent display
        Sorting.insertionSort(o.getItems(), Comparator.comparing(BookItem::getTitle));

        System.out.println("Items:");
        for (BookItem b : o.getItems()) {
            System.out.printf("  - %s | %s | qty=%d%n", b.getTitle(), b.getAuthor(), b.getQuantity());
        }
        System.out.println("========================");
    }

    // helper: print brief order info
    private static void printBriefOrder(Order o) {
        System.out.printf(" - %s | %s | OrderID: %s%n", o.getCustomerName(), o.getAddress(), o.getOrderId().substring(0, 8));
    }

    // helper: print catalog
    private static void printCatalog(List<BookItem> catalog) {
        System.out.println("\nAvailable books in store:");
        for (int i = 0; i < catalog.size(); i++) {
            BookItem b = catalog.get(i);
            System.out.printf("%d) %s | %s | stock=%d%n", i + 1, b.getTitle(), b.getAuthor(), b.getQuantity());
        }
        System.out.println();
    }
}
