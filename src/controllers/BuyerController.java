package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.Buyer;
import db.Cart;
import db.Item;
import db.Product;
import db.helpers.BuyerDB;
import db.helpers.CartDB;
import db.helpers.ItemDB;
import db.helpers.OrderDB;
import db.helpers.ProductDB;
import db.helpers.SellerDB;
import helpers.EmailUtil;
import helpers.Utils;

/**
 * Servlet implementation class BuyerController
 */
@WebServlet("/buyer/*")
public class BuyerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> parts = Utils.getPathParts("/buyer", request.getRequestURI());
		String url = null;
		if(session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());
		
		if(parts == null) {
			List<Product> products = null;
			List<Object> types = null;
			List<Object> sellers = null;
			List<List<Object>> filters = null;
			String[] type_filter_param = request.getParameterValues("type");
			String[] seller_filter_param = request.getParameterValues("seller");
			List<String> type_filter = new ArrayList<>();
			List<String> seller_filter = new ArrayList<>();
			
			if(type_filter_param != null)
				for(String type: type_filter_param)
					type_filter.add(type);
			if(seller_filter_param != null)
				for(String seller: seller_filter_param)
					seller_filter.add(seller);
			
			try {
				products = ProductDB.applyFilters(type_filter, seller_filter);
				filters = ProductDB.getFilters();
				types = filters.get(0);
				sellers = filters.get(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("products", products);
			request.setAttribute("types", types);
			request.setAttribute("sellers", sellers);
			request.setAttribute("seller_filter", seller_filter);
			request.setAttribute("type_filter", type_filter);
			session.setAttribute("b_return_path", "/buyer");
			url = "/buyers/home.jsp";
		} else if(parts.get(0).equals("product")) {
			Product product;
			if(parts.size() == 1) { response.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
			try {
				product = ProductDB.getProduct(null, Long.valueOf(parts.get(1)));
				if(product == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
				request.setAttribute("product", product);
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("b_return_path", "/buyer/product/" + parts.get(1));
			url = "/buyers/description.jsp";
		} else if(parts.get(0).equals("cart")) {
			url = "/buyers/cart.jsp";
			session.setAttribute("b_return_path", "/buyer/cart");
		} else if(parts.get(0).equals("checkout")) {
			if(session.getAttribute("bid") == null) {
				url = "/buyer/login";
				session.setAttribute("b_return_path", "/buyer/checkout");
				response.sendRedirect(request.getContextPath() + url);
				return;
			}
			url = "/buyers/checkout.jsp";
				
		} else if(parts.get(0).equals("login")) {
			if(session.getAttribute("bid") != null) {
				response.sendRedirect(request.getContextPath() + "/buyer");
				return;
			}
			url = "/buyers/login.jsp";
		} else if(parts.get(0).equals("register")) {
			url = "/buyers/register.jsp";
		} else if(parts.get(0).equals("logout") && parts.size() == 1) {
			if(session.getAttribute("userId") == null && session.getAttribute("userName") == null)
				session.invalidate();
			else {
				session.removeAttribute("bid");
				session.removeAttribute("bname");
				session.removeAttribute("cart");
				session.removeAttribute("b_return_path");
			}
			response.sendRedirect(request.getContextPath() + "/buyer");
			return;
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		if(url == null) {
			return;
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		List<String> parts = Utils.getPathParts("/buyer", request.getRequestURI());
		String url = null;
		if(parts.size() >= 2) {
			if(parts.get(0).equals("cart")) {
				if(parts.get(1).equals("edit")) {
					int count = Integer.parseInt(request.getParameter("count"));
					Cart cart = (Cart) session.getAttribute("cart");
					int id = Integer.parseInt(parts.get(2));
					Item item = null;
					try {
						if(count == 0) {
							item = cart.removeItem(id);
							if(session.getAttribute("bid") != null) { ItemDB.remove(item); CartDB.update(null, cart); }
						}
						else {
							item = cart.updateItem(id, count);
							if(session.getAttribute("bid") != null) ItemDB.update(null, item);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					url = "/buyer/cart";
					response.sendRedirect(request.getContextPath() + url);
					return;
				}
			} else if(parts.get(0).equals("addCart")) {
				Cart cart = (Cart) session.getAttribute("cart");
				Item item;
				try {
					Product product = ProductDB.getProduct(null, Long.valueOf(parts.get(1)));
					item = new Item(product);
					if(session.getAttribute("bid") != null) ItemDB.insert(null, item);
					cart.addItem(item);
					if(session.getAttribute("bid") != null) CartDB.update(null, cart);
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				url = "/buyer/product/" + parts.get(1);
				response.sendRedirect(request.getContextPath() + url);
				return;
			}
		} else if(parts.get(0).equals("login")) {
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			try {
				boolean flag = BuyerDB.check(email);
				if(!flag) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.getWriter().print("Email doesn't exists.");
					return;
				}
				Buyer buyer = BuyerDB.get(email, Utils.hashPassword(passwd));
				flag = flag && buyer != null;
				if(!flag) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().print("Email and Password do not match.");
					return;
				}
				session.setAttribute("bid", buyer.getId());
				session.setAttribute("bname", buyer.getFname() + " " + buyer.getLname());
				Cart cart = buyer.getCartObj();
				if(session.getAttribute("cart") != null && ((Cart) session.getAttribute("cart")).getItems().size() != 0)
					CartDB.mergeCarts(cart, (Cart) session.getAttribute("cart"));
				
				List<Item> items = new ArrayList<>(cart.getItems());
				flag = false;
				for(Item item: items) {
					if(item.getProduct().getCount() < item.getCount())  {
						ItemDB.remove(item);
						cart.removeItem(item.getId());
						flag = true;
					}
				}
				if(flag) CartDB.update(null, cart);
				
				session.setAttribute("cart", cart);
				response.setStatus(HttpServletResponse.SC_OK);
				url = session.getAttribute("b_return_path") == null ? "/buyer" : session.getAttribute("b_return_path").toString();
				response.getWriter().print(url);
			} catch(Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				e.printStackTrace(response.getWriter());
			}
			
		} else if(parts.get(0).equals("register")) {
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			String addr = request.getParameter("addr");
			int pincode = Integer.parseInt(request.getParameter("pincode"));
			String mobile = request.getParameter("mobile");
			Cart cart = (Cart) session.getAttribute("cart");
			boolean flag = false;
			try {
				flag = BuyerDB.check(email);
				if(flag) {
					throw new Exception();
				}
				CartDB.insert(cart);
				Buyer buyer = new Buyer(fname, lname, email, Utils.hashPassword(passwd), addr, pincode, mobile, cart);
				BuyerDB.insert(buyer);
				StringBuffer text = new StringBuffer();
				text.append("Hi " + buyer.getFname() + " " + buyer.getLname());
				text.append(",\n\tYour account is created successfully. Hope you find best time with us.");
				text.append("\nBest Regards,\nEShoppers.com");
				EmailUtil.sendEmail(buyer.getEmail(), text.toString());
				session.removeAttribute("cart");
			} catch(Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(flag ? "Email already exists!" : e.getMessage());
			}
		} else if(parts.get(0).equals("checkout")) {
			try {
				Cart cart = (Cart) session.getAttribute("cart");
				OrderDB.insert(cart, (long) session.getAttribute("bid"));
				response.sendRedirect(request.getContextPath() + "/buyer");
				return;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
