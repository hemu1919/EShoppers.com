<header id="pageHeader">
	<table>
		<tr>
			<td align="left"onclick="window.open('/buyer', '_self')"><strong>EShoppers</strong>.com</td>
			<td align="right">
				<span onclick="window.open('/buyer', '_self')">Home</span>
				<span onclick="window.open('/buyer/cart', '_self')">Cart</span>
				<span onclick="window.open('/buyer/checkout', '_self')">Checkout</span>
				<%
					if(session.getAttribute("bid") != null) {
				%>
						<span onclick="window.open('/buyer/logout', '_self')">LogOut</span>
				<%	} else { %>
					<span onclick="window.open('/buyer/login', '_self')">Login</span>
				<% 	} %>
			</td>
		</tr>
	</table>
</header>