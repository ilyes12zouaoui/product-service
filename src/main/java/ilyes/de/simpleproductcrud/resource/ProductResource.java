package ilyes.de.simpleproductcrud.resource;

import ilyes.de.simpleproductcrud.config.aop.annotation.LogResource;
import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import ilyes.de.simpleproductcrud.config.log.logtype.LogTypeProductConstants;
import ilyes.de.simpleproductcrud.mapper.to.ProductCreateOrUpdateTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductTo;
import ilyes.de.simpleproductcrud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//todo : log - 5ammim fil common fields wolli yitbadlo wol generation mte3 uuid
//todo : zid annotations mte3 openapi.
//todo : zid tests.
//todo : zid docker.
@RestController
@RequestMapping("/api/app/v1/products")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get product by id")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Found product",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductTo.class))
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseTo.class))
                            }
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error!",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseTo.class))
                            }
                    ),
            }
    )
    @GetMapping("")
    @LogResource(logType = LogTypeProductConstants.PRODUCT_GET_ALL_HANDLE_REQUEST)
    public List<ProductTo> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @LogResource(logType = LogTypeProductConstants.PRODUCT_GET_BY_ID_HANDLE_REQUEST)
    public ProductTo tryGetProductById(@PathVariable long id) {
        return productService.tryGetProductTOById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @LogResource(logType = LogTypeProductConstants.PRODUCT_CREATE_HANDLE_REQUEST)
    public ProductTo createProduct(@Valid @RequestBody ProductCreateOrUpdateTo productTo) {
        return productService.createProduct(productTo);
    }

    @PutMapping("/{id}")
    @LogResource(logType = LogTypeProductConstants.PRODUCT_UPDATE_HANDLE_REQUEST)
    public ProductTo updateProduct(@Valid @RequestBody ProductCreateOrUpdateTo productCreateOrUpdateTo, @PathVariable long id) {
        return productService.updateProduct(productCreateOrUpdateTo, id);
    }

    @DeleteMapping("/{id}")
    @LogResource(logType = LogTypeProductConstants.PRODUCT_DELETE_HANDLE_REQUEST)
    public ProductTo deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

}
