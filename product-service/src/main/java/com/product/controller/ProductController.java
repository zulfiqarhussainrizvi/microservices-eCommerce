package com.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.dto.ProductDto;
import com.product.entity.Product;
import com.product.entity.UserLog;
import com.product.service.ProductService;
import com.product.utils.LocalServerString;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController extends CommonController {

    static Logger log = Logger.getLogger(ProductController.class.getName());

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest context;

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        String apiName = "PRODUCT_API_SAVE";
        log.info(LocalServerString.REQUEST_RECEIVED + apiName);

        UserLog userLog = new UserLog(context, apiName, product);

        if (product.getName() == null || product.getName().isEmpty()) {
            log.warning("Product name is missing");
            return sendHttpBadReqResponse(userLog, "Product name is required");
        }

        Product savedProduct = productService.saveProduct(product);
        return sendSuccessResponse(userLog, "Product saved successfully", savedProduct);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        String apiName = "PRODUCT_API_GET_ALL";
        log.info(LocalServerString.REQUEST_RECEIVED + apiName);

        UserLog userLog = new UserLog(context, apiName, null);
        List<Product> products = productService.getAllProducts();
        return sendSuccessResponse(userLog, "Fetched all products", products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        String apiName = "PRODUCT_API_GET_BY_ID";
        log.info(LocalServerString.REQUEST_RECEIVED + apiName + " ID: " + id);

        UserLog userLog = new UserLog(context, apiName, id);
        Optional<Product> product = productService.getProductById(id);
        if (product == null) {
            return sendHttpBadReqResponse(userLog, "Product not found with ID: " + id);
        }

        return sendSuccessResponse(userLog, "Product fetched", product);
    }
    @GetMapping("/view/{id}")    
    public ResponseEntity<ProductDto> getProductByIdForFeign(@PathVariable Long id) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Feign handles 404 properly
        }

        Product product = productOpt.get();
        ProductDto dto = new ProductDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getDescription(),
            product.getCategory()
        );

        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        String apiName = "PRODUCT_API_DELETE";
        log.info(LocalServerString.REQUEST_RECEIVED + apiName + " ID: " + id);

        UserLog userLog = new UserLog(context, apiName, id);
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            return sendHttpBadReqResponse(userLog, "Product not found for deletion: " + id);
        }

        return sendSuccessResponse(userLog, "Product deleted", null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        String apiName = "PRODUCT_API_UPDATE";
        log.info(LocalServerString.REQUEST_RECEIVED + apiName);

        UserLog userLog = new UserLog(context, apiName, updatedProduct);

        Product product = productService.updateProduct(id, updatedProduct);
        if (product == null) {
            return sendHttpBadReqResponse(userLog, "Product not found for update: " + id);
        }

        return sendSuccessResponse(userLog, "Product updated", product);
    }
}
