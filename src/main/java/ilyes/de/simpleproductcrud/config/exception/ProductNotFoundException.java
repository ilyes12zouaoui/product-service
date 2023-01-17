package ilyes.de.simpleproductcrud.config.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends TechnicalException{

    public ProductNotFoundException(long id, HttpStatus errorHttpStatus, String logType) {
        super(String.format("No product with id %s was found!",id),errorHttpStatus,logType);
    }
}
