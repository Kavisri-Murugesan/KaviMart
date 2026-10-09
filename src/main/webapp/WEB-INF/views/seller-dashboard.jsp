package com.kavi.kavimart.model;

import java.math.BigDecimal;
import java.util.*;

/** Sales summary for one seller (plain getters so JSP EL can read it). */
public class SalesStats {
    /** Best-selling product row. */
    public static class TopProduct {
        private final String name; private final long units; private final BigDecimal revenue;
        public TopProduct(String name, long units, BigDecimal revenue) { this.name = name; this.units = units; this.revenue = revenue; }
        public String getName() { return name; } public long getUnits() { return units; } public BigDecimal getRevenue() { return revenue; }
    }
    /** Low-stock product row. */
    public static class LowStock {
        private final String name; private final int stockQty;
        public LowStock(String name, int stockQty) { this.name = name; this.stockQty = stockQty; }
        public String getName() { return name; } public int getStockQty() { return stockQty; }
    }
    private BigDecimal revenue = BigDecimal.ZERO; private long units; private long orders; private int listings;
    private final Map<String, Long> byStatus = new LinkedHashMap<>();
    private final List<TopProduct> topProducts = new ArrayList<>();
    private final List<LowStock> lowStock = new ArrayList<>();
    public BigDecimal getRevenue() { return revenue; } public void setRevenue(BigDecimal v) { revenue = v; }
    public long getUnits() { return units; } public void setUnits(long v) { units = v; }
    public long getOrders() { return orders; } public void setOrders(long v) { orders = v; }
    public int getListings() { return listings; } public void setListings(int v) { listings = v; }
    public Map<String, Long> getByStatus() { return byStatus; }
    public List<TopProduct> getTopProducts() { return topProducts; }
    public List<LowStock> getLowStock() { return lowStock; }
}
