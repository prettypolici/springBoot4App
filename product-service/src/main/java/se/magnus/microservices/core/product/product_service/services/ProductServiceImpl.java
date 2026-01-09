package se.magnus.microservices.core.product.product_service.services;

import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.api.exceptions.InvalidInputException;
import se.magnus.api.exceptions.NotFoundException;
import se.magnus.microservices.core.product.product_service.persistence.ProductEntity;
import se.magnus.microservices.core.product.product_service.persistence.ProductRepository;
import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ServiceUtil serviceUtil;
  private final ProductRepository repository;
  private final ProductMapper productMapper;

  public ProductServiceImpl(ServiceUtil serviceUtil, ProductRepository repository, ProductMapper productMapper) {
    this.serviceUtil = serviceUtil;
    this.repository = repository;
    this.productMapper = productMapper;
  }

  @Override
  public Product getProduct(int productId) {
    if(productId < 1) throw new InvalidInputException("Invalid product id: " + productId);

    ProductEntity entity = repository.findByProductId(productId)
      .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));

    Product response = productMapper.entityToApi(entity);
    response.setServiceAddress(serviceUtil.getServiceAddress());
    return response;
  }

  @Override
  public Product createProduct(Product body) {
    try {
      ProductEntity entity = productMapper.apiToEntity(body);
      ProductEntity newEntity = repository.save(entity);
      return productMapper.entityToApi(newEntity);
    } catch (DuplicateKeyException dke) {
      throw new InvalidInputException("Duplicate key, Product ID: " + body.getProductId());
    }
  }

  @Override
  public void deleteProduct(int productId) {
    repository.findByProductId(productId).ifPresent(repository::delete);
  }
}
