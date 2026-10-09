<%@ include file="header.jsp" %>
<h1>Sales dashboard</h1>
<div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:12px">
  <div class="panel"><p class="muted">Revenue</p><h2>₹<c:out value="${stats.revenue}"/></h2></div>
  <div class="panel"><p class="muted">Orders</p><h2><c:out value="${stats.orders}"/></h2></div>
  <div class="panel"><p class="muted">Units sold</p><h2><c:out value="${stats.units}"/></h2></div>
  <div class="panel"><p class="muted">Listings</p><h2><c:out value="${stats.listings}"/></h2></div>
</div>
<div class="panel"><h2>Orders by status</h2>
  <c:if test="${empty stats.byStatus}"><p class="muted">No orders yet.</p></c:if>
  <c:forEach var="e" items="${stats.byStatus}"><span class="btn small secondary"><c:out value="${e.key}"/>: <c:out value="${e.value}"/></span> </c:forEach>
</div>
<div class="panel"><h2>Top products</h2>
  <c:if test="${empty stats.topProducts}"><p class="muted">No sales yet.</p></c:if>
  <ol><c:forEach var="t" items="${stats.topProducts}"><li><c:out value="${t.name}"/> · <c:out value="${t.units}"/> sold · ₹<c:out value="${t.revenue}"/></li></c:forEach></ol>
</div>
<div class="panel"><h2>Low stock (5 or fewer)</h2>
  <c:if test="${empty stats.lowStock}"><p class="muted">All products are well stocked.</p></c:if>
  <ul><c:forEach var="l" items="${stats.lowStock}"><li><c:out value="${l.name}"/> · <c:out value="${l.stockQty}"/> left</li></c:forEach></ul>
</div>
<p class="muted">Cancelled orders are not counted in revenue, orders or units.</p>
<%@ include file="footer.jsp" %>
