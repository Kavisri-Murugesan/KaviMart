package com.kavi.kavimart.service;

import com.kavi.kavimart.dao.OrderDao;
import com.kavi.kavimart.dao.ProductDao;
import com.kavi.kavimart.exception.AppException;
import com.kavi.kavimart.model.Order;
import com.kavi.kavimart.model.Product;
import com.kavi.kavimart.model.Role;
import com.kavi.kavimart.model.User;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Rule-based shopping assistant: product search, price filters, order status and site help. */
public class ChatService {
    /** Chat answer with optional product suggestions. */
    public record Reply(String text, List<Product> products) {}

    private static final int MAX_LEN = 300, MAX_PRODUCTS = 4;
    private static final Pattern UNDER = Pattern.compile("(?:under|below|less than|within|upto|up to)\\s*(?:rs\\.?|₹)?\\s*(\\d{1,7})");
    private static final Set<String> STOP = Set.of("show","me","find","search","i","want","need","buy","a","an","the","some","for","please",
            "do","you","have","is","there","any","price","of","what","are","available","can","get","looking","to","in","with","and","cheap","best",
            "under","below","rs","upto","up","less","than","within","items","item","products","product");

    private final ProductDao productDao;
    private final OrderDao orderDao;

    /** Creates the assistant. */
    public ChatService(ProductDao productDao, OrderDao orderDao) { this.productDao = productDao; this.orderDao = orderDao; }

    /** Builds a reply for one message; {@code user} is null for guests. */
    public Reply reply(String raw, User user) throws AppException {
        String msg = raw == null ? "" : raw.trim();
        if (msg.isEmpty() || msg.length() > MAX_LEN) return text("Please type a short question (up to 300 characters).");
        String q = msg.toLowerCase(Locale.ROOT);
        Set<String> words = new HashSet<>(Arrays.asList(q.split("[^a-z0-9]+")));

        if (any(words, "hi", "hello", "hey", "vanakkam", "hii")) return text("Hi! I'm the KaviMart assistant. Ask me to find products (e.g. \"mug under 500\"), check your orders, or explain how to buy and sell.");
        if (any(words, "order", "orders", "track", "tracking", "delivery", "delivered", "shipped")) return orderStatus(user);
        if (any(words, "cart", "checkout", "pay", "payment")) return text("Add items with the Add button, open Cart to change quantities, then Checkout and confirm the mock payment. No real money is charged.");
        if (any(words, "register", "signup", "join", "sell", "seller")) return text("Use Join to register as a Buyer or Seller. Sellers can add, edit and delete listings under My listings.");
        if (any(words, "review", "reviews", "rating")) return text("You can review a product after your order for it is marked Delivered. Open the product page and submit a rating and comment.");
        if (any(words, "category", "categories")) {
            List<String> cats = productDao.findCategories();
            return text(cats.isEmpty() ? "No categories yet." : "Categories: " + String.join(", ", cats) + ".");
        }
        if (any(words, "help", "thanks", "thank", "bye")) return text("Try: \"wireless earbuds\", \"home items under 600\", or \"my orders\".");
        return productSearch(q, words);
    }

    private Reply orderStatus(User user) throws AppException {
        if (user == null) return text("Please log in first so I can look up your orders.");
        if (user.getRole() == Role.BUYER) {
            List<Order> orders = orderDao.findByBuyer(user.getId());
            if (orders.isEmpty()) return text("You have no orders yet.");
            String lines = orders.stream().limit(3).map(o -> "Order #" + o.getId() + ": " + o.getStatus() + " (₹" + o.getTotalAmount() + ")").collect(Collectors.joining("; "));
            return text("Your latest orders - " + lines + ". See Orders for the full list.");
        }
        if (user.getRole() == Role.SELLER) return text("You have " + orderDao.findIncomingForSeller(user.getId()).size() + " incoming order(s). Open Incoming orders for details.");
        return text("There are " + orderDao.findAll().size() + " orders in total. Manage them from the Admin dashboard.");
    }

    private Reply productSearch(String q, Set<String> words) throws AppException {
        BigDecimal max = null;
        Matcher m = UNDER.matcher(q);
        if (m.find()) max = new BigDecimal(m.group(1));
        List<String> keys = words.stream().filter(w -> !w.isEmpty() && !STOP.contains(w) && !w.matches("\\d+")).sorted().collect(Collectors.toList());
        if (keys.isEmpty() && max == null) return text("I didn't catch that. Try \"wireless earbuds\", \"home items under 600\" or \"my orders\".");

        LinkedHashMap<Long, Product> found = new LinkedHashMap<>();
        if (keys.isEmpty()) productDao.search(null, null).forEach(p -> found.put(p.getId(), p));
        else for (String k : keys) productDao.search(k, null).forEach(p -> found.putIfAbsent(p.getId(), p));
        final BigDecimal limit = max;
        List<Product> result = found.values().stream().filter(p -> limit == null || p.getPrice().compareTo(limit) <= 0).limit(MAX_PRODUCTS).collect(Collectors.toList());
        if (result.isEmpty()) return text("Sorry, I couldn't find matching products. Try another keyword or ask for \"categories\".");
        return new Reply("Here's what I found:", result);
    }

    private static boolean any(Set<String> words, String... keys) { for (String k : keys) if (words.contains(k)) return true; return false; }
    private static Reply text(String s) { return new Reply(s, List.of()); }
}
