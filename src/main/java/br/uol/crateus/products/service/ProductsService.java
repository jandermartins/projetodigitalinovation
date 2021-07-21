package br.uol.crateus.products.service;


import br.uol.crateus.products.model.Products;
import br.uol.crateus.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public Products addProducts(Products products) {
        return productsRepository.save(products);
    }

    public List<Products> getProducts() {
        return productsRepository.findAll();
    }

    public Products editProducts(Products products) {
        return productsRepository.save(products);
    }

    public Products getProductsById(Integer id) {
        return productsRepository.findById(id);
    }

    public Page<Products> searchProduto(Specification<Products> productsSpecification, Pageable pageable) {
        System.out.println(pageable.toString());
        return productsRepository.findAll(productsSpecification, pageable);
    }

    public void deleteProduct(Products products) {
        productsRepository.delete(products);
    }
}
