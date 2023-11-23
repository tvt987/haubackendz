package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminProductAPI {
    @Autowired
    ProductRepository proDAO;
    @Autowired
    CategoryRepository cateDAO;
    @Autowired
    ColorRepository colDAO;
    @Autowired
    StorageRepository stoDAO;
    @Autowired
    TrademarkRepository traDAO;

    @Autowired
    ImageRepository imgDAO;

    @GetMapping("/getAllProducts")
    public List<Product> getProducts() {
        return proDAO.findAll();
    }

    @GetMapping("/getSelectForm")
    public DTOSelectFormProduct getSelecForm() {
        DTOSelectFormProduct data = new DTOSelectFormProduct(cateDAO.findAll(), traDAO.findAll(), colDAO.findAll(), stoDAO.findAll());
        return data;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = proDAO.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = proDAO.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        Optional<Product> existingProduct = proDAO.findById(id);
        if (existingProduct.isPresent()) {
            updatedProduct.setId(id);
            Product savedProduct = proDAO.save(updatedProduct);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Optional<Product> product = proDAO.findById(id);
        if (product.isPresent()) {
            proDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
@Data
@AllArgsConstructor
class DTOSelectFormProduct {
    List<Category> categories;
    List<Trademark> trademarks;
    List<Color> colors;
    List<Storage> storages;
}
