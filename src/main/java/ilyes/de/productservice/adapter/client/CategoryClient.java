package ilyes.de.productservice.adapter.client;


import ilyes.de.productservice.adapter.mapper.to.CategoryCreateOrUpdateTo;
import ilyes.de.productservice.adapter.mapper.to.CategoryTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "categoryClient",url="${ilyes.clients.categoryClientUrl}")
public interface CategoryClient {

    @RequestMapping(method = RequestMethod.GET, value = "")
    List<CategoryTo> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/{categoryId}", produces = "application/json")
    CategoryTo getById(@PathVariable("categoryId") Long categoryId);

    @PostMapping("")
    CategoryTo create(@RequestBody CategoryCreateOrUpdateTo product);

    @PutMapping("/{categoryId}")
    CategoryTo update(@RequestBody CategoryCreateOrUpdateTo product, @PathVariable("categoryId") Long categoryId);

    @DeleteMapping("/{categoryId}")
    CategoryTo delete(@PathVariable("categoryId") Long categoryId);

}
