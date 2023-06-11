package com.shop.demo.controllers;

import com.shop.demo.models.Cart;
import com.shop.demo.models.CartItem;
import com.shop.demo.models.Product;
import com.shop.demo.repositories.CartRepository;
import com.shop.demo.repositories.ProductRepository;
import com.shop.demo.models.AuthenticationFacade;
import com.shop.demo.models.User;
import com.shop.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

//    private final AuthenticationFacade authenticationFacade;
//    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;
//
//    @GetMapping("/get")
//    public ResponseEntity<List<Product>> getProductsInCart() {
//        User user = authenticationFacade.getCurrentUser();
//        Cart cart = cartRepository.findById(user.getCart().getId()).orElse(null);
//        if (cart == null) {
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//        List<Product> products = cart.getProducts();
//        return ResponseEntity.ok(products);
//    }
//
//
//
//    @PostMapping("/add/{productId}")
//    public ResponseEntity<String> addToCart(@PathVariable Long productId) {
//        User user = authenticationFacade.getCurrentUser();
//        Optional<Product> productOptional = productRepository.findById(productId);
//        if (productOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        Product product = productOptional.get();
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseGet(() -> createCartForUser(user));
//
//        if (cart.getProducts().contains(product)) {
//            return ResponseEntity.badRequest().body("Product already in cart");
//        }
//
//        cart.getProducts().add(product);
//        cart.calculateTotalPrice(); // Calculate total price
//        cartRepository.save(cart);
//
//        return ResponseEntity.ok("Product added to cart successfully");
//    }
//
//
//    private Cart createCartForUser(User user) {
//        Cart cart = new Cart();
//        cart.setUser(user);
//        return cartRepository.save(cart);
//    }
//
//    @DeleteMapping("/delete/{productId}")
//    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long productId) {
//        User user = authenticationFacade.getCurrentUser();
//        Cart cart = user.getCart();
//
//        // Проверяем, содержит ли корзина указанный товар
//        Optional<Product> productOptional = productRepository.findById(productId);
//        if (productOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        Product product = productOptional.get();
//        System.out.println(product.getId());
//
//        // Удаляем товар из списка продуктов корзины
//        Iterator<Product> iterator = cart.getProducts().iterator();
//        while (iterator.hasNext()) {
//            Product obj = iterator.next();
//            if (Objects.equals(obj.getId(), productId)) {
//                System.out.println("Найдено");
//                iterator.remove();
//            }
//        }
//        cart.calculateTotalPrice();
//        cartRepository.save(cart);
//
//        return ResponseEntity.ok("Product removed from cart successfully");
//    }
//


    private final AuthenticationFacade authenticationFacade;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping("/get")
    public ResponseEntity<List<CartItem>> getProductsInCart() {
        User user = authenticationFacade.getCurrentUser();
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CartItem> cartItems = cart.getCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long productId) {
        User user = authenticationFacade.getCurrentUser();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> createCartForUser(user));

        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(product)) {
                // Increment quantity if the product already exists in the cart
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cart.calculateTotalPrice();
                cartRepository.save(cart);
                return ResponseEntity.ok("Product quantity incremented in cart");
            }
        }

        // Create a new cart item if the product is not already in the cart
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return ResponseEntity.ok("Product added to cart successfully");
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long productId) {
        User user = authenticationFacade.getCurrentUser();
        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        List<CartItem> cartItems = cart.getCartItems();
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getProduct().getId().equals(productId)) {
                iterator.remove();
                cart.calculateTotalPrice();
                cartRepository.save(cart);
                return ResponseEntity.ok("Product removed from cart successfully");
            }
        }

        return ResponseEntity.notFound().build();


    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }


}