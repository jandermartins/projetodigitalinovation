package br.uol.crateus.products.model;



import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Range(min = 0)
    private String price;

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public Products(){

    }

    public Products(Integer id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    /*Metodo para retornar a busca via parametro*/

    public Specification<Products> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.hasText(name)){
                Path<String> campoNome = root.<String>get("q");
                Predicate predicateNome = builder.like(campoNome, "%"+name+"%");
                predicates.add(predicateNome);
            }if(StringUtils.hasText(price)){
                Path<String> campoPrecoMax = root.<String>get("max_price");
                Predicate predicatePrecoMax = builder.like(campoPrecoMax, "%"+price+"%");
                predicates.add(predicatePrecoMax);
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
