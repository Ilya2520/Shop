package com.shop.demo.controllers;

import com.shop.demo.models.Cart;
import com.shop.demo.models.Product;
import com.shop.demo.services.CartService;
import com.shop.demo.services.ProductService;
import com.shop.demo.models.AuthenticationFacade;
import com.shop.demo.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shop.demo.models.User;
import com.shop.demo.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

  private final UserService userService;
  private final ProductService productService;
  private final CartService cartService;
  private final AuthenticationFacade auth;


  @GetMapping("/users")
  public ResponseEntity<List<User>> readAllusers() {
    if (auth.getCurrentUser().getRole().equals(Role.ADMIN)) {
      return ResponseEntity.ok(userService.findAll());
    } else return ResponseEntity.badRequest().build();
  }

  @GetMapping("/products")
  public ResponseEntity<List<Product>> readAllproducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @PostMapping("/products/add")
  public ResponseEntity<String> Addproduct(@RequestBody Product product) {
    if (auth.getCurrentUser().getRole().equals(Role.ADMIN)) {
      productService.save(product);
      return ResponseEntity.ok(product.toString() + "was added");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have no rights");
  }

  @GetMapping("/carts")
  public ResponseEntity<List<Cart>> readAllCarts() {
    if (auth.getCurrentUser().getRole().equals(Role.ADMIN)) {
      return ResponseEntity.ok(cartService.getAllCarts());
    } else return ResponseEntity.badRequest().build();
  }
}