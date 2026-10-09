package com.kavi.kavimart.dao;

import com.kavi.kavimart.exception.AppException;
import com.kavi.kavimart.model.SalesStats;
import javax.sql.DataSource;
import java.sql.*;

/** Read-only JDBC queries that build a seller's sales dashboard. Cancelled orders are excluded from totals. */
public class JdbcSalesDao {
    private static final String FROM = " FROM order_items oi JOIN orders o ON o.id=oi.order_id JOIN products p ON p.id=oi.product_id WHERE p.seller_id=?";
    private final DataSource dataSource;

    /** Creates a DAO backed by the supplied pool. */
    public JdbcSalesDao(DataSource dataSource) { this.dataSource = dataSource; }

    /** Collects totals, status counts, top products and low-stock items for one seller. */
    public SalesStats forSeller(long sellerId) throws AppException {
        SalesStats s = new SalesStats();
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT COALESCE(SUM(oi.quantity*oi.unit_price),0) rev, COALESCE(SUM(oi.quantity),0) units, COUNT(DISTINCT o.id) orders" + FROM + " AND o.status<>'CANCELLED'")) {
                p.setLong(1, sellerId);
                try (ResultSet r = p.executeQuery()) { if (r.next()) { s.setRevenue(r.getBigDecimal("rev")); s.setUnits(r.getLong("units")); s.setOrders(r.getLong("orders")); } }
            }
            try (PreparedStatement p = c.prepareStatement("SELECT o.status st, COUNT(DISTINCT o.id) cnt" + FROM + " GROUP BY o.status ORDER BY o.status")) {
                p.setLong(1, sellerId);
                try (ResultSet r = p.executeQuery()) { while (r.next()) s.getByStatus().put(r.getString("st"), r.getLong("cnt")); }
            }
            try (PreparedStatement p = c.prepareStatement("SELECT p.name pname, SUM(oi.quantity) units, SUM(oi.quantity*oi.unit_price) rev" + FROM + " AND o.status<>'CANCELLED' GROUP BY p.id, p.name ORDER BY units DESC, rev DESC LIMIT 5")) {
                p.setLong(1, sellerId);
                try (ResultSet r = p.executeQuery()) { while (r.next()) s.getTopProducts().add(new SalesStats.TopProduct(r.getString("pname"), r.getLong("units"), r.getBigDecimal("rev"))); }
            }
            try (PreparedStatement p = c.prepareStatement("SELECT name, stock_qty FROM products WHERE seller_id=? AND stock_qty<=5 ORDER BY stock_qty, name")) {
                p.setLong(1, sellerId);
                try (ResultSet r = p.executeQuery()) { while (r.next()) s.getLowStock().add(new SalesStats.LowStock(r.getString("name"), r.getInt("stock_qty"))); }
            }
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM products WHERE seller_id=?")) {
                p.setLong(1, sellerId);
                try (ResultSet r = p.executeQuery()) { if (r.next()) s.setListings(r.getInt(1)); }
            }
            return s;
        } catch (SQLException e) { throw new AppException("Unable to load sales dashboard.", e); }
    }
}
