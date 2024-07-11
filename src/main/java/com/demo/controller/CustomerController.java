package com.demo.controller;

import com.demo.model.Account;
import com.demo.model.Order;
import com.demo.model.OrderDetail;
import com.demo.model.Product;
import com.demo.repo.AccountRepo;
import com.demo.repo.CategoryRepo;
import com.demo.repo.ProductRepo;
import com.demo.service.CartService;
import com.demo.service.OrderDetailService;
import com.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
	@Autowired
	HttpSession session;

	@Autowired
	CategoryRepo categoryService;

	@Autowired
	ProductRepo productService;

	@Autowired
	CartService cartService;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@ModelAttribute("cart")
	CartService getCartService() {
		return cartService;
	}

	@Data
	@AllArgsConstructor
	public static class PriceRange {
		int id;
		int minValue;
		int maxValue;
		String display;
	}

	List<PriceRange> priceRangeList = Arrays.asList(
			new PriceRange(0, 0, Integer.MAX_VALUE, "Tất cả"),
			new PriceRange(1, 0, 10000000, "Dưới 10 triệu"),
			new PriceRange(2, 10000000, 20000000, "Từ 10-20 triệu"),
			new PriceRange(3, 20000000, Integer.MAX_VALUE, "Trên 20 triệu")
	);

	@RequestMapping("/")
	public String index(
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="") String categoryId,
			@RequestParam(defaultValue="0") int priceRangeId,
			@RequestParam(defaultValue="0") int page,
			Model model) {

		model.addAttribute("priceRangeList", priceRangeList);
		model.addAttribute("categoryList", categoryService.findAll());

		int minPrice = priceRangeList.get(priceRangeId).getMinValue();
		int maxPrice = priceRangeList.get(priceRangeId).getMaxValue();

		Pageable pageable = PageRequest.of(page, 5);
		Page<Product> productPage = productService.findProducts(keyword, categoryId, minPrice, maxPrice, pageable);

		model.addAttribute("productList", productPage.getContent());
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("currentPage", page);

		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("priceRangeId", priceRangeId);

		return "customer/index";
	}

	@GetMapping("/detail/{id}")
	public String viewProduct(@PathVariable Integer id, Model model) {
		Optional<Product> prdOptional = productService.findById(id);
		if (prdOptional.isPresent()) {
			Product prd = prdOptional.get();
			model.addAttribute("prd", prd);
			return "customer/detail";
		} else {
			model.addAttribute("errorMessage", "Product not found");
			return "error/404";
		}
	}

	@GetMapping("/page")
	public String page(@RequestParam(defaultValue = "0") int d, Model model) {
		if (d < 0) {
			return "error/404";
		}

		int pageSize = 5;
		Pageable pageable = PageRequest.of(d, pageSize);
		Page<Product> productPage = productService.findAll(pageable);

		model.addAttribute("productList", productPage.getContent());
		model.addAttribute("currentPage", d);

		return "customer/index";
	}

	@RequestMapping("/add-to-cart/{id}")
	public String addToCart(@PathVariable int id) {
		// Lấy thông tin đơn hàng hiện tại từ session
		Order currentOrder = (Order) session.getAttribute("currentOrder");

		// Kiểm tra nếu không có đơn hàng hiện tại trong session, bạn có thể thực hiện các bước để tạo một đơn hàng mới
		if (currentOrder == null) {
			// Tạo một đơn hàng mới và lưu vào session
			// currentOrder = orderService.createOrder(address, account);
			// session.setAttribute("currentOrder", currentOrder);

			// Hoặc, nếu bạn đã có cách khác để lấy thông tin đơn hàng, hãy sử dụng nó ở đây
		}

		// Tiếp tục thêm sản phẩm vào giỏ hàng và liên kết nó với đơn hàng hiện tại
		Product product = productService.findById(id).orElse(null);
		if (product != null) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(product);
			orderDetail.setOrder(currentOrder); // Thiết lập đơn hàng cho chi tiết đơn hàng

			// Lưu chi tiết đơn hàng vào giỏ hàng
			cartService.add(orderDetail);

			return "redirect:/cart";
		} else {
			return "redirect:/";
		}
	}



	@RequestMapping("/remove-cart/{id}")
	public String removeCart(@PathVariable int id) {
		cartService.remove(id);
		if (cartService.getTotal() == 0) {
			return "redirect:/";
		}
		return "redirect:/cart";
	}

	@RequestMapping("/update-cart/{id}")
	public String updateCart(@PathVariable int id, @RequestParam int quantity) {
		Optional<Product> productOptional = productService.findById(id); // Lấy thông tin sản phẩm từ cơ sở dữ liệu
		if (productOptional.isPresent()) { // Kiểm tra xem sản phẩm có tồn tại không
			Product product = productOptional.get(); // Lấy sản phẩm từ Optional
			cartService.update(product.getId(), quantity); // Cập nhật số lượng của sản phẩm trong giỏ hàng
		}
		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String showCart(Model model) {
		double totalAmount = cartService.getTotalAmount();
		model.addAttribute("totalAmount", totalAmount);
		return "customer/cart";
	}

	@GetMapping("/checkout")
	public String confirm() {
		if (session.getAttribute("account") == null) {
			return "redirect:/login";
		}
		return "customer/checkout";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		return "customer/about";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		Optional<Account> accountOptional = Optional.ofNullable(accountRepo.findByUsername(username));

		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			if (password.equals(account.getPassword())) {
				session.setAttribute("account", account);
				return "redirect:/";
			}
		}
		model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng");
		return "login";
	}

	@PostMapping("/purchase")
	public String purchase(@RequestParam String address) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null) {
			// Tạo đơn hàng
			Order order = orderService.createOrder(address, acc);

			// Lưu các chi tiết đơn hàng
			for (OrderDetail item : cartService.getItems()) {
				// Thiết lập Orderid cho chi tiết đơn hàng
				item.setOrder(order);
				// Lưu chi tiết đơn hàng
				orderDetailService.save(item);
			}

			// Xóa giỏ hàng sau khi đơn hàng đã được xử lý
			cartService.clear();
		}
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout() {
		session.removeAttribute("account");
		return "redirect:/login";
	}
}
