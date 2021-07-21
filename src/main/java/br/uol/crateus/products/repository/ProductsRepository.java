package br.uol.crateus.products.repository;

import br.uol.crateus.products.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Products, Long> {

    Products findById(Integer id);

    Page<Products> findAll(Specification<Products> productsSpecification, Pageable pageable);
}
