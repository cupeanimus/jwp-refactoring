package kitchenpos.product.application;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.dto.ProductRequest;
import kitchenpos.product.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(final ProductRequest productRequest) {

        Product product =  productRepository.save(productRequest.toEntity());
        return ProductResponse.of(product);
    }

    public List<ProductResponse> list() {
        List<Product> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(productEntity -> ProductResponse.of(productEntity))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 상품입니다."));
    }

}
