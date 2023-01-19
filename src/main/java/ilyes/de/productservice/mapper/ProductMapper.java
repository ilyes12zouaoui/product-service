package ilyes.de.productservice.mapper;

import ilyes.de.productservice.config.aop.annotation.WithMethodLogs;
import ilyes.de.productservice.mapper.to.ProductCreateOrUpdateTo;
import ilyes.de.productservice.mapper.to.ProductTo;
import ilyes.de.productservice.repository.entity.ProductEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

   private static final Logger LOGGER = LogManager.getLogger(ProductMapper.class);

   @WithMethodLogs
   public ProductEntity mapToProductEntityFields(ProductCreateOrUpdateTo productTo) {
       if(productTo == null) {
           return null;
       }
       return new ProductEntity(
               productTo.getName(),
               productTo.getPrice(),
               productTo.inStock()
       );
   }

    public ProductTo mapToProductTo(ProductEntity productEntity) {

        if(productEntity == null) {
            return null;
        }

        return new ProductTo(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.isInStock(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt()
        );
    }
}
