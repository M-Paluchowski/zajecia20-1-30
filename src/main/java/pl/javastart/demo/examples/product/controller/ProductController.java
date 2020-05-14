package pl.javastart.demo.examples.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.javastart.demo.examples.product.model.Product;
import pl.javastart.demo.examples.product.model.ProductCategory;
import pl.javastart.demo.examples.product.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
public class ProductController {

    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    @ResponseBody
    void home() {
//        productRepository.findAll()
//                .forEach(System.out::println);
//
//        productRepository.findAllByNameStartsWith("Ml")
//                .forEach(System.out::println);

        productRepository.findByAvailable(Boolean.TRUE)
                .forEach(System.out::println);

        System.out.println();

        productRepository.findByProductCategory(ProductCategory.PREMIUM)
                .forEach(System.out::println);

        System.out.println();

        productRepository.findAllByOrderByPriceDesc()
                .forEach(System.out::println);

        System.out.println();

        productRepository.findByProductCategoryOrderByNameAscPriceDesc(ProductCategory.GOLD)
                .forEach(System.out::println);

        System.out.println();

        productRepository.findByProductCategoryAndPrice(ProductCategory.PREMIUM, 2.99)
                .forEach(System.out::println);

        System.out.println();

        productRepository.findByPriceBetween(2, 4)
                .forEach(System.out::println);

        print(productRepository.findByPriceLessThanEqual(1.99));
        print(productRepository.findByNameIn(List.of("Mleko", "Owoc", "Twaróg")));

//        productRepository.deleteByNameContaining("le");

        print(productRepository.getMyAwesomeProduct(2.99, "Ml"));
        print(productRepository.getMyAwesomeProductWithoutParameters(2.99, "Ml"));
        print(productRepository.getMyAwesomeProductWithNativeQuery("Ml"));

        productRepository.deleteUsingNativeQuery("Ml");
    }

    @RequestMapping("/test")
    @Transactional(readOnly = true)
    public void findWithoutTransactional() {
        Product product = entityManager.find(Product.class, 1L);
    }

    public void print(List<Product> products) {
        System.out.println();
        products.forEach(System.out::println);
    }
}
