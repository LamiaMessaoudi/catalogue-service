package dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.Category;
@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, String> {

}
