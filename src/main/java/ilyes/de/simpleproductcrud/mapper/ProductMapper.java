package ilyes.de.simpleproductcrud.mapper;

import ilyes.de.simpleproductcrud.repository.entity.ProductEntity;
import ilyes.de.simpleproductcrud.mapper.to.ProductTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductCreateOrUpdateTo;

public class ProductMapper {

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
                productEntity.inStock(),
                productEntity.getCreatedAt(),
                productEntity.getModifiedAt()
        );
    }
}
