package com.demo.controller;

import com.demo.model.Account;
import com.demo.model.Category;
import com.demo.model.Product;
import com.demo.repo.AccountRepo;
import com.demo.repo.CategoryRepo;
import com.demo.repo.ProductRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class AdminController {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @ModelAttribute("categoryList")
    public List<Category> ListSP(){
        return categoryRepo.findAll();
    }
    //  Category
    @GetMapping("/admin")
    public String listCategory(Model model){
        List<Category> listcata = categoryRepo.findAll();
        model.addAttribute("listcata",listcata);
        model.addAttribute("cata",new Product());
        return "admin/category/list";
    }

    @GetMapping("/admin/category/create")
    public String createCategory(Model model)
    {
        model.addAttribute("cata",new Category());
        return "admin/category/form";
    }
    @PostMapping("/admin/category/create")
    public String Add(@ModelAttribute("cata") Category category){
        categoryRepo.save(category);
        return "redirect:/admin";
    }


    @GetMapping("/admin/category/update/{id}")
    public String editCategory(@PathVariable String id,Model model){
            Category cata = categoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dia chi id:" + id));
            model.addAttribute("cata", cata);

        return "admin/category/form";
    }
    @PostMapping("/admin/category/update/{id}")
    public String Update(@PathVariable("id") String id, @ModelAttribute("cata") Category category) {
        category.setId(id); // Set lại id cho đối tượng
       categoryRepo.save(category);
        return "redirect:/admin";
    }

    //  Product
    @GetMapping("/admin/product")
    public String listProduct(Model model){
        List<Product> listSP = productRepo.findAll();
        model.addAttribute("listSP",listSP);
        model.addAttribute("prd",new Product());
        return "admin/product/list";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(Model model)
    {
        model.addAttribute("prd",new Product());
        return "admin/product/form";
    }
    @PostMapping("/admin/product/create")
    public String Add(@ModelAttribute("ac") Product product){
        productRepo.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String editProduct(@PathVariable Integer id,Model model){
        Product prd = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dia chi id:" + id));
        model.addAttribute("prd", prd);
        return "admin/product/form";
    }
    @PostMapping("/admin/product/update/{id}")
    public String Update(@PathVariable("id") Integer id, @ModelAttribute("prd") Product product) {
        product.setId(id); // Set lại id cho đối tượng
        productRepo.save(product);
        return "redirect:/admin/product";
    }

    //  Account
    @GetMapping("/admin/account")
    public String listAccount(Model model){
            List<Account> list = accountRepo.findAll();
            model.addAttribute("list",list);
            model.addAttribute("ac",new Account());
        return "admin/account/list";
    }

    @GetMapping("/admin/account/create")
    public String createAccount(Model model){
        model.addAttribute("ac",new Account());
        return "admin/account/form";
    }
    @PostMapping("/admin/account/create")
    public String Add(@Valid @ModelAttribute("ac") Account account, BindingResult rs,Model model){
        accountRepo.save(account);
        return "redirect:/admin/account";
    }

    @GetMapping("/admin/account/update/{username}")
    public String editAccount(@PathVariable String username,Model model){
        Account ac = accountRepo.findById(username).orElseThrow(() -> new IllegalArgumentException("Invalid dia chi username:" + username));
        model.addAttribute("ac", ac);
        return "admin/account/form";
    }
    @PostMapping("/admin/account/update/{username}")
    public String Update(@PathVariable("username") String username, @ModelAttribute("ac") Account account) {
        account.setUsername(username); // Set lại id cho đối tượng
        accountRepo.save(account);
        return "redirect:/admin/account";
    }
}
