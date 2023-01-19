package ilyes.de.productservice.adapter;

import ilyes.de.productservice.adapter.client.CategoryClient;
import ilyes.de.productservice.adapter.mapper.to.CategoryCreateOrUpdateTo;
import ilyes.de.productservice.adapter.mapper.to.CategoryTo;
import ilyes.de.productservice.config.utils.annotation.Adapter;

import java.util.List;

@Adapter
public class CategoryAdapter {

    private CategoryClient categoryClient;

    public CategoryAdapter(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

    public CategoryTo getById(long id){
        return categoryClient.getById(id);
    }

    public List<CategoryTo> getAll(){
        return categoryClient.getAll();
    }

    public CategoryTo create(CategoryCreateOrUpdateTo categoryCreateOrUpdateTo){
        return categoryClient.create(categoryCreateOrUpdateTo);
    }

    public CategoryTo update(CategoryCreateOrUpdateTo categoryCreateOrUpdateTo, long id){
        return categoryClient.update(categoryCreateOrUpdateTo,id);
    }

    public CategoryTo delete(long id){
        return categoryClient.delete(id);
    }
}
