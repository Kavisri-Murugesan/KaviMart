package com.kavi.kavimart.controller;

import com.kavi.kavimart.dao.JdbcSalesDao;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/** Seller sales dashboard (GET /seller/dashboard). Role check is done by AuthFilter for /seller/*. */
public class SellerDashboardServlet extends BaseServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("stats", new JdbcSalesDao(dataSource(req)).forSeller(currentUser(req).getId()));
            req.getRequestDispatcher("/WEB-INF/views/seller-dashboard.jsp").forward(req, resp);
        } catch (Exception e) { fail(req, resp, e); }
    }
}
