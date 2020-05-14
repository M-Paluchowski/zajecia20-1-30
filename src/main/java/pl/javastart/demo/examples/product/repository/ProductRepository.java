package pl.javastart.demo.examples.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.javastart.demo.examples.product.model.Product;
import pl.javastart.demo.examples.product.model.ProductCategory;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameStartsWith(String startLetter);

    List<Product> findByAvailable(boolean isAvailable);

    List<Product> findByProductCategory(ProductCategory productCategory);

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findByProductCategoryOrderByNameAscPriceDesc(ProductCategory productCategory);

    List<Product> findByProductCategoryAndPrice(ProductCategory productCategory, double price);

    List<Product> findByPriceBetween(double start, double end);

    List<Product> findByPriceLessThanEqual(double price);

    List<Product> findByProductCategoryIsNull();

    List<Product> findByNameContaining(String name);

    List<Product> findByAvailableTrue();

    //SELECT * FROM product WHERE name IN ('Mleko', 'Mleczko');
    List<Product> findByNameIn(List<String> name);

    List<Product> findByNameIgnoreCase(String name);

    @Transactional
    void deleteByNameContaining(String name);

    @Query("SELECT p FROM Product p " +
            "WHERE p.name LIKE CONCAT(:name, '%') " +
            "AND p.price <= :price")
    List<Product> getMyAwesomeProduct(@Param("price") double price, @Param("name") String containing);

    @Query("SELECT p FROM Product p " +
            "WHERE p.name LIKE CONCAT(?2, '%') " +
            "AND p.price <= ?1" )
    List<Product> getMyAwesomeProductWithoutParameters(double price, String containing);

    @Query(value = "SELECT * FROM product WHERE name like :name%", nativeQuery = true)
    List<Product> getMyAwesomeProductWithNativeQuery(@Param("name") String containing);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product WHERE name LIKE :name%", nativeQuery = true)
    void deleteUsingNativeQuery(@Param("name") String contains);
}
