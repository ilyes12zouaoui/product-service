package ilyes.de.productservice.service;

import ilyes.de.productservice.config.exception.ProductNotFoundException;
import ilyes.de.productservice.mapper.ProductMapper;
import ilyes.de.productservice.mapper.to.ProductCreateOrUpdateTo;
import ilyes.de.productservice.mapper.to.ProductTo;
import ilyes.de.productservice.repository.ProductRepository;
import ilyes.de.productservice.repository.entity.ProductEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductService.class);
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductTo createProduct(ProductCreateOrUpdateTo productTo){
        ProductEntity productEntity = productMapper.mapToProductEntityFields(productTo);
        productRepository.save(productEntity);
        return productMapper.mapToProductTo(productEntity);
    }

    public ProductTo updateProduct(ProductCreateOrUpdateTo productCreateOrUpdateTo, long id){
        ProductEntity productEntity = tryGetProductEntityById(id);

        productEntity.setName(productCreateOrUpdateTo.getName());
        productEntity.setPrice(productCreateOrUpdateTo.getPrice());
        productEntity.setInStock(productCreateOrUpdateTo.inStock());

        productRepository.save(productEntity);

        return  productMapper.mapToProductTo(productEntity);
    }

    public ProductTo deleteProduct(long id){
        ProductEntity productEntity = tryGetProductEntityById(id);
        productRepository.delete(productEntity);
        return  productMapper.mapToProductTo(productEntity);
    }

    public List<ProductTo> getAllProducts(){
       return productRepository.findAll().stream().map(productMapper::mapToProductTo).collect(Collectors.toList());
    }

    public ProductTo tryGetProductTOById(long id){
        return productMapper.mapToProductTo(tryGetProductEntityById(id));
    }

    public ProductEntity tryGetProductEntityById(long id,HttpStatus httpStatus,String logType){
        Optional<ProductEntity> optionalProductEntity =  productRepository.findById(id);
        return optionalProductEntity.orElseThrow(()-> {
            if(logType==null && httpStatus==null) {
                return new ProductNotFoundException(id, HttpStatus.NOT_FOUND);
            }else if(logType == null){
                return new ProductNotFoundException(id, httpStatus);
            }else{
                return new ProductNotFoundException(id, httpStatus, logType);
            }
        });
    }

    public ProductEntity tryGetProductEntityById(long id,HttpStatus httpStatus){
        return this.tryGetProductEntityById(id, httpStatus,null);
    }

    public ProductEntity tryGetProductEntityById(long id){
        return this.tryGetProductEntityById(id,null,null);
    }
}
