package ilyes.de.simpleproductcrud.mapper;

import ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory;
import ilyes.de.simpleproductcrud.repository.entity.ProductEntity;
import ilyes.de.simpleproductcrud.mapper.to.ProductTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductCreateOrUpdateTo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductMapper {

   private static final Logger LOGGER = LogManager.getLogger(ProductMapper.class);

    public static ProductEntity mapToProductEntityFields(ProductCreateOrUpdateTo productTo) {
        if(productTo == null) {
            return null;
        }
        return new ProductEntity(
                productTo.getName(),
                productTo.getPrice(),
                productTo.inStock()
        );
    }

    public static ProductTo mapToProductTo(ProductEntity productEntity) {

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

    public static ProductTo mapToProductTOWithLog(ProductEntity productEntity,
                                                  String logTypeBegin,
                                                  String logTypeEnd) {
        LOGGER.info(LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType(
                productEntity,
                logTypeBegin
        ));
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
        LOGGER.info(LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType(
                response,
                logTypeEnd
        ));

        return response;
    }
}
