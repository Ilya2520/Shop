package com.shop.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Component
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "cart_product",
//            joinColumns = @JoinColumn(name = "cart_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id"))
//    private List<Product> products = new ArrayList<>();
//
//    @Column(name = "total_price")
//    private int totalPrice;
//
//    public void calculateTotalPrice() {
//        int totalPrice = 0;
//        for (Product product : products) {
//            totalPrice += product.getPrice();
//        }
//        this.totalPrice = totalPrice;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "total_price")
    private int totalPrice;

    public void calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            int quantity = cartItem.getQuantity();
            int price = cartItem.getProduct().getPrice();
            totalPrice += price * quantity;
        }
        this.totalPrice = totalPrice;
    }
}