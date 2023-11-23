package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.Discount;
import com.onlinemobilestore.entity.Product;
import com.onlinemobilestore.entity.User;

import com.onlinemobilestore.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class UserAdminRestController {
//    @Autowired
//    private AuthenticationManager authenticationManager;trademarkRepository
@Autowired
private TrademarkRepository trademarkRepository;
@Autowired
private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    UserRepository userDao;
    @Autowired
    CategoryRepository categoryDAO;
    @Autowired
    ProductRepository productDAO;
    @Autowired
    DiscountRepository discountDAO;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private PasswordEncoder passwordEncoder;





    @GetMapping("/account")
    public List<UserData> getAllAccount() {
        System.out.println("In thử trong api khác nè");
        List<User> data = userDao.findAll();
        List<UserData> userDataList = new ArrayList<UserData>();
        for (User user : data) {
//            List<RoleAssignment> roleAssignments = user.getRoleAssignments();
//            List<String> roleNames = new ArrayList<>();
//            for (RoleAssignment roleAssignment : roleAssignments) {
//                roleNames.add(roleAssignment.getRole().getName());
//            }
            userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                    user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
        }
        return userDataList;
    }

    @DeleteMapping("/account/delete/{id}")
    public dataUser deleteUser(@PathVariable Integer id) {
        Optional<User> userOptional = userDao.findById(id);
        if (userOptional.isEmpty()) {
            List<User> data = userDao.findAll();
            List<UserData> userDataList = new ArrayList<>();
            for (User user : data) {

                userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                        user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
            }
            return new dataUser("User not found", userDataList);
        }

        userDao.deleteById(id);

        List<User> data = userDao.findAll();
        List<UserData> userDataList = new ArrayList<>();
        for (User user : data) {

            userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                    user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
        }

        String message = "User with id " + id + " has been deleted";
        return new dataUser(message, userDataList);
    }

    @PutMapping("/account/{id}/activate")
    public dataUser activateUser(@PathVariable Integer id) {
        Optional<User> userOptional = userDao.findById(id);
        if (userOptional.isEmpty()) {
            List<User> data = userDao.findAll();
            List<UserData> userDataList = new ArrayList<>();
            for (User user : data) {

                userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                        user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
            }
            return new dataUser("User not found", userDataList);
        }

        User users = userOptional.get();
        users.setActive(true);
        userDao.save(users);

        List<User> data = userDao.findAll();
        List<UserData> userDataList = new ArrayList<>();
        for (User user : data) {

            userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                    user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
        }

        String message = "User with id " + id + " has been activated";
        return new dataUser(message, userDataList);
    }

    @PutMapping("/account/{id}/deactivate")
    public dataUser deactivateUser(@PathVariable Integer id) {
        Optional<User> userOptional = userDao.findById(id);
        if (userOptional.isEmpty()) {
            List<User> data = userDao.findAll();
            List<UserData> userDataList = new ArrayList<>();
            for (User user : data) {

                userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                        user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
            }
            return new dataUser("User not found", userDataList);
        }

        User users = userOptional.get();
        users.setActive(false);
        userDao.save(users);

        List<User> data = userDao.findAll();
        List<UserData> userDataList = new ArrayList<>();
        for (User user : data) {

            userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
                    user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(),user.isActive(), user.getRoles()));
        }

        String message = "User with id " + id + " has been deactivated";
        return new dataUser(message, userDataList);
    }


//    Begin
    @GetMapping("/getTypePhone")
    public List<String> getTypePhone() {
        return categoryDAO.getAllName();
    }

//    @GetMapping("/getProducts")
//    public List<DataProduct> getAllProduct() {
//        List<Product> data = productDAO.findAll();
//        List<DataProduct> jsonData = new ArrayList<>();
//        for (Product pro : data) {
//            jsonData.add(new DataProduct(pro.getId(), pro.getName(), pro.getPrice(),pro.getPrice()*(100-pro.getPercentDiscount())));
//        }
//        return jsonData;
//    }



    @GetMapping("/getProductsDiscount")
    public List<DataProductHasDiscount> getAllProductDiscount() {
        List<Product> data = productDAO.findAll();
        List<DataProductHasDiscount> jsonData = new ArrayList<>();
        DiscountBrief maxPrecent;
        for (Product pro : data) {
            List<Discount> listDiscount = pro.getDiscounts();
            System.out.println(data.size());
            if (listDiscount.size() > 0){
                List<DiscountBrief> discountBriefs = new ArrayList<>();
                listDiscount.stream().forEach(dis -> discountBriefs.add(new DiscountBrief(dis.getName(), dis.getPercent(), dis.getExpirationDate(), dis.getCreateDate(), dis.isActive())));
                maxPrecent = discountBriefs.get(0);
                for(int i = 0; i < discountBriefs.size(); i++){
                    if(discountBriefs.get(i).getPercent() > maxPrecent.getPercent()){
                        maxPrecent = discountBriefs.get(i);
                    }
                }
                jsonData.add(new DataProductHasDiscount(pro.getId(), pro.getName(), pro.getPrice(),
                        (pro.getPrice() - pro.getPrice()*(maxPrecent.getPercent()/100)), discountBriefs));
            }

        }
        return jsonData;
    }

    @GetMapping("/getDiscounts")
    public List<Discount> getDiscounts() {
        List<Discount> data = discountDAO.findAll();
        return data;
    }

    @GetMapping("/getProducts/{name}")
    public List<DataProductHasDiscount> getProductByName(@PathVariable("name") String name) {
        List<Product> data = productDAO.findByName(name);

        List<DataProductHasDiscount> jsonData = new ArrayList<>();
        for (Product pro : data) {
            List<DiscountBrief> discountBriefs = new ArrayList<>();
            pro.getDiscounts().stream().forEach(dis -> discountBriefs.add(new DiscountBrief(dis.getName(), dis.getPercent(), dis.getExpirationDate(), dis.getCreateDate(), dis.isActive())));
            jsonData.add(new DataProductHasDiscount(pro.getId(), pro.getName(),
                    pro.getPrice(), (pro.getPrice() - pro.getPrice()*(pro.getPercentDiscount()/100)), discountBriefs));
        }
        return jsonData;
    }



    @PostMapping("/signin/{email}/{password}")
    public User authenticateUser(@PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findUserByEmail(email);
            System.out.println(user.getEmail());
            return new User(user.getId(), user.getFullName(), user.getAvatar()
                    ,user.getAddress(), user.getEmail(), user.getPassword()
                    ,user.getRoles(), user.getPhoneNumber(), user.getOrders());
        } catch (AuthenticationException e) {
            return null;
        }
    }

    @GetMapping("/getInformationBrand/{id}")
    public List<InformationBrand> getInformationBrand(@PathVariable("id") int id) {
        List<Product> productList = productRepository.findByTrademarkId(id);
        List<InformationBrand> informationBrandList = new ArrayList<>();
        for (Product data : productList) {
            InformationBrand informationBrand = new InformationBrand(
                    data.getId(),
                    data.getName(),
                    data.getPrice(),
                    data.getQuantity(),
                    data.getDescription(),
                    data.getCreateDate(),
                    data.getModifiedDate(),
                    data.getPercentDiscount()
            );
            informationBrandList.add(informationBrand);
        }
        return informationBrandList; // Trả về danh sách InformationBrand
    }

    @GetMapping("/getAllBrand")
    public List<Object[]> gestAllBrand() {
        return trademarkRepository.getAllIdAndName();
    }
}


@Data
@AllArgsConstructor
class InformationBrand{
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private Date createDate;
    private Date modifiedDate;
    private int percentDiscount;

}


    @Data
    @AllArgsConstructor
    class DataProductHasDiscount {
        private Integer id;
        private String name;
        private Double price;
        private Double priceUpdate;
        private List<DiscountBrief> discount;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class DiscountBrief {
        private String name;
        private float percent;
        private Date expiration_date;
        private Date create_date;
        private boolean active;
    }



//    End









    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class UserData {
        private Integer id;
        private String email;
        private String address;
        private String fullName;
        private String avatar;
        private Date birthday;
        private String phoneNumber;
        private Date createDate;
        private boolean isActive;
        private String roles;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class dataUser {
        private String message;
        private List<UserData> userDataList;
    }

