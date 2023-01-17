package ilyes.de.simpleproductcrud.service;

import ilyes.de.simpleproductcrud.config.exception.ProductNotFoundException;
import ilyes.de.simpleproductcrud.mapper.ProductMapper;
import ilyes.de.simpleproductcrud.mapper.to.ProductCreateOrUpdateTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductTo;
import ilyes.de.simpleproductcrud.repository.ProductRepository;
import ilyes.de.simpleproductcrud.repository.entity.ProductEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ilyes.de.simpleproductcrud.config.log.logtype.LogTypeProductConstants.*;

@Service
public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductService.class);
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductTo createProduct(ProductCreateOrUpdateTo productTo){
        ProductEntity productEntity = ProductMapper.mapToProductEntityFields(productTo);
        productRepository.save(productEntity);
        return ProductMapper.mapToProductTo(productEntity);
    }

    public ProductTo updateProduct(ProductCreateOrUpdateTo productCreateOrUpdateTo, long id){
        ProductEntity productEntity = tryGetProductEntityById(id, HttpStatus.NOT_FOUND,PRODUCT_UPDATE_HANDLE_REQUEST_WARN);
        productEntity.setName(productCreateOrUpdateTo.getName());
        productEntity.setPrice(productCreateOrUpdateTo.getPrice());
        productEntity.setInStock(productCreateOrUpdateTo.inStock());

        productRepository.save(productEntity);

        return  ProductMapper.mapToProductTo(productEntity);
    }

    public ProductTo deleteProduct(long id){
        ProductEntity productEntity = tryGetProductEntityById(id,HttpStatus.NOT_FOUND,PRODUCT_DELETE_HANDLE_REQUEST_WARN);
        productRepository.delete(productEntity);
        return  ProductMapper.mapToProductTo(productEntity);
    }

    public List<ProductTo> getAllProducts(){
       return productRepository.findAll().stream().map(ProductMapper::mapToProductTo).collect(Collectors.toList());
    }

    public ProductEntity tryGetProductEntityById(long id,HttpStatus httpStatus,String logType){
        Optional<ProductEntity> optionalProductEntity =  productRepository.findById(id);
        return optionalProductEntity.orElseThrow(()->new ProductNotFoundException(id, httpStatus,logType));
    }

    public ProductTo tryGetProductById(long id){
       return ProductMapper.mapToProductTo(tryGetProductEntityById(id,HttpStatus.NOT_FOUND,PRODUCT_GET_BY_ID_HANDLE_REQUEST_WARN));
    }

}
