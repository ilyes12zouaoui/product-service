package ilyes.de.simpleproductcrud.mapper;

import ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory;
import ilyes.de.simpleproductcrud.repository.entity.ProductEntity;
import ilyes.de.simpleproductcrud.mapper.to.ProductTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductCreateOrUpdateTo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

   private static final Logger LOGGER = LogManager.getLogger(ProductMapper.class);

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

    public ProductTo mapToProductTOWithLogs(ProductEntity productEntity) {

        if(productEntity == null) {
            return null;
        }


        var response = new ProductTo(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.isInStock(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt()
        );

        return response;
    }
}
