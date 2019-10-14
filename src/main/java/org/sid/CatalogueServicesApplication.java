package org.sid;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import dao.CategoryRepository;
import dao.ProductRepository;
import entities.Category;
import entities.Product;

@SpringBootApplication
@EntityScan("entities")
@EnableMongoRepositories("dao")
@ComponentScan({"entities","dao","sec"})

public class CatalogueServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogueServicesApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository)
	{
		return args->{
			categoryRepository.deleteAll();
			Stream.of("C1 ordinateurs","C2 imprimantes").forEach(c->{
				categoryRepository.save(new Category(c.split(" ")[0],c.split(" ")[1],new ArrayList<>()));
			});
			
			categoryRepository.findAll().forEach(System.out::println);
			productRepository.deleteAll();
			
			Category c1=categoryRepository.findById("C1").get();
			Stream.of("p1","p2","p3","p4").forEach(name->{
				Product p=productRepository.save(new Product(null,name,Math.random()*100,c1));
				c1.getProducts().add(p);
				categoryRepository.save(c1);
			});
			
			Category c2=categoryRepository.findById("C2").get();
			Stream.of("p1","p2","p3","p4").forEach(name->{
				Product p=productRepository.save(new Product(null,name,Math.random()*100,c2));
				c2.getProducts().add(p);
				categoryRepository.save(c2);
			});
			
			productRepository.findAll().forEach(p->{
				System.out.println(p.toString());
			});
		};
		
	}

}
