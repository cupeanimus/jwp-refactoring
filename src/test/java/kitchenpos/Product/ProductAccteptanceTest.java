package kitchenpos.Product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.AcceptanceTest;
import kitchenpos.menugroup.dto.MenuGroupRequest;
import kitchenpos.menugroup.dto.MenuGroupResponse;
import kitchenpos.product.domain.Product;
import kitchenpos.product.dto.ProductRequest;
import kitchenpos.product.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sun.security.x509.OtherName;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductAccteptanceTest extends AcceptanceTest {
    private ProductRequest productRequest;

    @BeforeEach
    public void setUp() {
        super.setUp();
        productRequest = new ProductRequest("테스트상품", BigDecimal.valueOf(10000));
    }

//    @PostMapping("/api/products")
//    public ResponseEntity<Product> create(@RequestBody final Product product) {
//        final Product created = productService.create(product);
//        final URI uri = URI.create("/api/products/" + created.getId());
//        return ResponseEntity.created(uri)
//                .body(created)
//                ;
//    }
//
//    @GetMapping("/api/products")
//    public ResponseEntity<List<Product>> list() {
//        return ResponseEntity.ok()
//                .body(productService.list())
//                ;
//    }
//

    @DisplayName("DTO와 JPA를 사용하여 상품을 등록한다")
    @Test
    void createTest() {

        //when
        ExtractableResponse<Response> response = 상품_등록_요청(productRequest);

        //then
        정상_등록(response);
        ProductResponse productResponse = response.as(ProductResponse.class);
        assertThat(productRequest.getName()).isEqualTo(productResponse.getName());
        assertThat(productRequest.getPrice()).isEqualTo(productResponse.getPrice());
    }

    @DisplayName("DTO와 JPA를 사용하여 상품을 조회한다")
    @Test
    void listTest() {

        //when
        ExtractableResponse<Response> response = 상품_조회_요청();

        //then
        정상_처리(response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(ProductRequest productRequest) {
        return RestAssured
                .given().log().all()
                .body(productRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/products/temp")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 상품_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/temp")
                .then().log().all()
                .extract();
    }


}
