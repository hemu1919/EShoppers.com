package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import db.Product;
import db.Seller;
import db.helpers.ProductDB;
import db.helpers.SellerDB;
import helpers.EmailUtil;
import helpers.Utils;

/**
 * Servlet implementation class SellerController
 */
@WebServlet("/seller/*")
public class SellerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> parts = Utils.getPathParts("/seller", request.getRequestURI());
		String url = null;
		
		if(parts == null) {
			response.sendRedirect(request.getContextPath() + "/seller/login");
			return;
		}
		if(parts.get(0).equals("login") && parts.size() == 1) {
			if(session.getAttribute("userId") != null) {
				response.sendRedirect(request.getContextPath() + "/seller/dashboard");
				return;
			}
			url = "/sellers/login.jsp";
		} else if(parts.get(0).equals("register") && parts.size() == 1) {
			if(session.getAttribute("userId") != null) {
				response.sendRedirect(request.getContextPath() + "/seller/dashboard");
				return;
			}
			url = "/sellers/register.jsp";
		} else if(parts.get(0).equals("logout") && parts.size() == 1) {
			if(session.getAttribute("bid") == null && session.getAttribute("bname") == null)
				session.invalidate();
			else {
				session.removeAttribute("userName");
				session.removeAttribute("userId");
				session.removeAttribute("redirectPath");
			}
			response.sendRedirect(request.getContextPath() + "/seller/login");
			return;
		} else if(parts.get(0).equals("dashboard")) {
			if(session.getAttribute("userId") == null) {
				url = "/seller";
				for(String part: parts) {
					url += "/" + part;
				}
				session.setAttribute("redirectPath", url);
				response.sendRedirect(request.getContextPath() + "/seller/login");
				return;
			} 
			if(parts.size() < 2) {
				url = "/sellers/dashboard.jsp";
				try {
					session.setAttribute("products", ProductDB.get((long) session.getAttribute("userId")));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if(parts.get(1).equals("create")) {
				url = "/sellers/product_form_create.jsp";
			} else if(parts.get(1).equals("manage")) {
				url = "/sellers/product_form_manage.jsp";
				try {
					Product product = ProductDB.getProduct(null, Long.parseLong(parts.get(2)));
					if(product == null) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					request.setAttribute("product", product);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		session.removeAttribute("redirectPage");
		if(url == null) {
			return;
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> parts = Utils.getPathParts("/seller", request.getRequestURI());
		if(parts.get(0).equals("login")) {
			loginSeller(request, response);
		} else if(parts.get(0).equals("register")) {
			registerSeller(request, response);
		} else if(parts.get(0).equals("dashboard")) {
			if(parts.size() >= 2) {
				if(parts.get(1).equals("create")) {
					createProduct(request, response);
				} else if(parts.get(1).equals("remove")) {
					removeProduct(request, response);
				} else if(parts.get(1).equals("store")) {
					saveImage(request, response);
				} else if(parts.get(1).equals("edit")) {
					updateProduct(request, response);
				} else if(parts.get(1).equals("publish")) {
					publishProduct(request, response);
				}
			}
		}
	}

	private void loginSeller(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			boolean flag = SellerDB.check(email);
			if(!flag) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().print("Email doesn't exists.");
				return;
			}
			Seller seller = SellerDB.get(email, Utils.hashPassword(passwd));
			flag = flag && seller != null;
			if(!flag) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print("Email and Password do not match.");
				return;
			}
			HttpSession session = request.getSession();
			session.setAttribute("userId", seller.getId());
			session.setAttribute("userName", seller.getCompany_name());
			response.setStatus(HttpServletResponse.SC_OK);
			String url = session.getAttribute("redirectPath") == null ? "/seller/dashboard" : session.getAttribute("redirectPath").toString();
			response.getWriter().print(url);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(e.getMessage());
		}
	}

	private void registerSeller(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean flag = false;
		try {
			Seller seller = new Seller(request.getParameter("email"), Utils.hashPassword(request.getParameter("passwd")), 
					request.getParameter("company_name"), request.getParameter("brand_name"));
			flag = SellerDB.check(seller.getEmail());
			if(flag) {
				throw new Exception();
			}
			SellerDB.insert(seller);
			StringBuffer text = new StringBuffer();
			text.append("Hi " + seller.getCompany_name());
			text.append(",\n\tYour account is created successfully. Hope to have a good relation with us.");
			text.append("\nBest Regards,\nEShoppers.com");
			EmailUtil.sendEmail(seller.getEmail(), text.toString());
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print("Account Created Successfully.");
		} catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(flag ? "Email already exists!" : e.getMessage());
		}
	}

	private void createProduct(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		String name = request.getParameter("name");
		String type = request.getParameter("type");
		double price = Double.parseDouble(request.getParameter("price"));
		int count = Integer.parseInt(request.getParameter("count"));
		String desc = request.getParameter("desc");
		
		Product product = null;
		try {
			product = new Product(SellerDB.get(null, (long) session.getAttribute("userId")), name, desc, type, price, count, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.setAttribute("product", product);
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	}
	
	private void saveImage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletFileUpload upload = new ServletFileUpload();
		String storage = this.getServletContext().getRealPath("/") + "/storage";
		HttpSession session = request.getSession();
		try {
			InputStream is = null;
			FileItemIterator items = upload.getItemIterator(request);
			OutputStream os = null;
			File file = new File(storage);
			if(!file.exists()) {
				file.mkdir();
			}
			byte[] buffer = null;
			String imagePath = session.getAttribute("userId") + "_" + System.currentTimeMillis() + "_";
			while (items.hasNext()) {
				FileItemStream item = items.next();
				Product product = (Product) session.getAttribute("product");
				session.removeAttribute("product");
				String path = ProductDB.preCheck(product);
				if(item.getName().equals("")) {
					ProductDB.update(null, product);
					response.sendRedirect(request.getContextPath() + "/seller/dashboard");
					return;
				}
				imagePath += item.getName();
				
				os = new FileOutputStream(storage + "/" + imagePath);
				if (!item.isFormField()) {
						product.setImagePath(imagePath);
						if(path == null) ProductDB.insert(product);
						else {
							file = new File(storage + "/" + path);
							file.delete();
							ProductDB.update(null, product); 
						}
					
	    	            is = item.openStream();
	    	            buffer = new byte[8 * 1024];
	    	            int bytesRead;
	    	            while ((bytesRead = is.read(buffer)) != -1) {
	    	                os.write(buffer, 0, bytesRead);
	    	            }
	    	            IOUtils.closeQuietly(is);
	    	            IOUtils.closeQuietly(os);
	    	            response.sendRedirect(request.getContextPath() + "/seller/dashboard");
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void removeProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long id = Long.parseLong(request.getParameter("id"));
		
		try {
			String path = ProductDB.remove(id);
			String storage = this.getServletContext().getRealPath("/") + "/storage";
			File file = new File(storage + "/" + path);
			file.delete();
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void publishProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long id = Long.parseLong(request.getParameter("id"));
		boolean isPublished = Boolean.parseBoolean(request.getParameter("flag"));
		
		try {
			ProductDB.setPublished(id, isPublished);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		double price = Double.parseDouble(request.getParameter("price"));
		int count = Integer.parseInt(request.getParameter("count"));
		String desc = request.getParameter("desc");
		
		Product product = null;
		try {
			product = new Product(SellerDB.get(null, (long) session.getAttribute("userId")), name, desc, type, price, count, false);
			product.setId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.setAttribute("product", product);
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	}
	
}
