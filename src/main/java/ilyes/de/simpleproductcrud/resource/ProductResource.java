package ilyes.de.simpleproductcrud.resource;

import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductTo;
import ilyes.de.simpleproductcrud.mapper.to.ProductCreateOrUpdateTo;
import ilyes.de.simpleproductcrud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

    private ProductService productService;

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
    public List<ProductTo> getAllProducts(HttpServletRequest httpServletRequest) {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductTo tryGetProductById(@PathVariable long id) {
        return productService.tryGetProductById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductTo createProduct(@Valid @RequestBody ProductCreateOrUpdateTo productTo) {
        return productService.createProduct(productTo);
    }

    @PutMapping("/{id}")
    public ProductTo updateProduct(@Valid @RequestBody ProductCreateOrUpdateTo productCreateOrUpdateTo, @PathVariable long id) {
        return productService.updateProduct(productCreateOrUpdateTo, id);
    }

    @DeleteMapping("/{id}")
    public ProductTo deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

}
