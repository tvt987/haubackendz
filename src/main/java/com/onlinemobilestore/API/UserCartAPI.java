package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/cart")
public class UserCartAPI {
    @Autowired
    CartRepository cartDAO;
    @Autowired
    CartDetailRepository cartDetailDAO;
    @Autowired
    OrderRepository orderDAO;
    @Autowired
    OrderDetailRepository orderDetailDAO;
    @Autowired
    ProductRepository productDAO;
    @Autowired
    UserRepository userDAO;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/getCartUser/{id}")
    public Cart getCartUser(@PathVariable("id") Integer idUser) {
        Cart cart = cartDAO.findByUserId(idUser);
        return cart;
    }

    @PostMapping("/addProductToCart/{idProduct}/{idUser}")
    public ResponseEntity<CartDetail> addProduct(@PathVariable Integer idProduct, @PathVariable Integer idUser) {
        Cart cart = cartDAO.findByUserId(idUser);
        CartDetail cartDetail = new CartDetail();
        String sessionId = request.getSession().getId();
        try {
            if (cart == null) {
                cart = new Cart();
                cart.setSessionId(sessionId);
                cart.setUser(userDAO.findById(idUser).get());
                cartDAO.save(cart);

                cartDetail.setProduct(productDAO.findById(idProduct).get());
                cartDetail.setCart(cart);
                cartDetail.setQuantity(1);
                cartDetail = cartDetailDAO.save(cartDetail);
            } else {
                cart.setSessionId(sessionId);
                cartDAO.save(cart);
                List<CartDetail> productsInCart = cart.getCartDetails();
                Optional<CartDetail> existingProduct = productsInCart.stream().filter(detail -> detail.getProduct().getId() == idProduct).findFirst();
                if (existingProduct.isPresent()) {
                    existingProduct.get().setQuantity(existingProduct.get().getQuantity() + 1);
                    cartDetail = existingProduct.get();
                } else {
                    cartDetail.setId(null);
                    cartDetail.setProduct(productDAO.findById(idProduct).get());
                    cartDetail.setCart(cart);
                    cartDetail.setQuantity(1);
                    productsInCart.add(cartDetail);
                }
                cartDetailDAO.saveAll(productsInCart);
            }
            return ResponseEntity.ok(cartDetail);
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/addCartSession/{idProduct}/{quantity}/{idUser}")
    public ResponseEntity<CartDetail> addCartSession(@PathVariable Integer idProduct,@PathVariable Integer quantity, @PathVariable Integer idUser) {
        Cart cart = cartDAO.findByUserId(idUser);
        CartDetail cartDetail = new CartDetail();
        String sessionId = request.getSession().getId();
        try {
            if (cart == null) {
                cart = new Cart();
                cart.setSessionId(sessionId);
                cart.setUser(userDAO.findById(idUser).get());
                cartDAO.save(cart);

                cartDetail.setProduct(productDAO.findById(idProduct).get());
                cartDetail.setCart(cart);
                cartDetail.setQuantity(quantity);
                cartDetail = cartDetailDAO.save(cartDetail);
            } else {
                cart.setSessionId(sessionId);
                cartDAO.save(cart);
                List<CartDetail> productsInCart = cart.getCartDetails();
                Optional<CartDetail> existingProduct = productsInCart.stream().filter(detail -> detail.getProduct().getId() == idProduct).findFirst();
                if (existingProduct.isPresent()) {
                    existingProduct.get().setQuantity(existingProduct.get().getQuantity() + quantity);
                    cartDetail = existingProduct.get();
                } else {
                    cartDetail.setId(null);
                    cartDetail.setProduct(productDAO.findById(idProduct).get());
                    cartDetail.setCart(cart);
                    cartDetail.setQuantity(quantity);
                    productsInCart.add(cartDetail);
                }
                cartDetailDAO.saveAll(productsInCart);
            }
            return ResponseEntity.ok(cartDetail);
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/addProductToCart/{idProduct}")
//    public ResponseEntity<CartDetail> addProductSession(@PathVariable Integer idProduct) {
//        String sessionId = request.getSession().getId();
//        Cart cart = cartDAO.findBySessionId(sessionId);
//        CartDetail cartDetail = new CartDetail();
//        try {
//            if (cart == null) {
//                cart = new Cart();
//                cart.setSessionId(sessionId);
//                cartDAO.save(cart);
//
//                cartDetail.setProduct(productDAO.findById(idProduct).get());
//                cartDetail.setCart(cart);
//                cartDetail.setQuantity(1);
//                cartDetail = cartDetailDAO.save(cartDetail);
//            } else {
//                List<CartDetail> productsInCart = cart.getCartDetails();
//                Optional<CartDetail> existingProduct = productsInCart.stream().filter(detail -> detail.getProduct().getId() == idProduct).findFirst();
//                if (existingProduct.isPresent()) {
//                    existingProduct.get().setQuantity(existingProduct.get().getQuantity() + 1);
//                    cartDetail = existingProduct.get();
//                } else {
//                    cartDetail.setId(null);
//                    cartDetail.setProduct(productDAO.findById(idProduct).get());
//                    cartDetail.setCart(cart);
//                    cartDetail.setQuantity(1);
//                    productsInCart.add(cartDetail);
//                }
//                cartDetailDAO.saveAll(productsInCart);
//            }
//            return ResponseEntity.ok(cartDetail);
//        } catch (Exception e) {
//            System.err.println(e);
//            return ResponseEntity.badRequest().build();
//        }
//
//    }

    @PostMapping("/createOrder/{idCartDetails}")
    public ResponseEntity<Integer> createOrder(@PathVariable ArrayList<Integer> idCartDetails) {
        try {
            Order order = new Order();
            order.setState(false);
            order.setUser(orderDetailDAO.findById(idCartDetails.get(0)).get().getOrder().getUser());
            Order orderCreate = orderDAO.save(order);
            for (Integer idCartDetail : idCartDetails) {
                OrderDetail newDetail = new OrderDetail();
                newDetail.setProduct(cartDetailDAO.findById(idCartDetail).get().getProduct());
                newDetail.setQuantity(cartDetailDAO.findById(idCartDetail).get().getQuantity());
                newDetail.setPrice(cartDetailDAO.findById(idCartDetail).get().getProduct().getPrice() * (1 - cartDetailDAO.findById(idCartDetail).get().getProduct().getPercentDiscount() / 100) * cartDetailDAO.findById(idCartDetail).get().getQuantity());
                newDetail.setOrder(orderCreate);
                orderDetailDAO.save(newDetail);
                cartDetailDAO.deleteById(idCartDetail);
            }
            return ResponseEntity.ok(orderCreate.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}