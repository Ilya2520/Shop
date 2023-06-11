package com.shop.demo.controllers;

import com.shop.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import com.shop.demo.models.Product;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService product;

    public List<Product> products(Model model) {
        return product.getAllProducts();
    }

//    private final ProductService productService;
//
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping
//    public String getAllProducts(Model model) {
//        List<Product> products = productService.getAllProducts();
//        model.addAttribute("products", products);
//        return "products";
//    }
//
//    @GetMapping("/{id}")
//    public String getProduct(@PathVariable Long id, Model model) {
//        Product product = productService.getProductById(id);
//        model.addAttribute("product", product);
//        return "product";
//    }
//
//    @PostMapping
//    public String addProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
//        String imageUrl = saveImage(imageFile);
//        product.setImageUrl(imageUrl);
//        productService.addProduct(product);
//        return "redirect:/products";
//    }
//
//    private String saveImage(MultipartFile imageFile) throws IOException {
//        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
//        Path uploadPath = Paths.get("images");
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        try (InputStream inputStream = imageFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//            return filePath.toString();
//        }
//    }


}
