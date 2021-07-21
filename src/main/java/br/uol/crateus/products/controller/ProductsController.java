package br.uol.crateus.products.controller;


import br.uol.crateus.products.model.Products;
import br.uol.crateus.products.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    /*
    * Este metodo POST salva o JSON com os dados recebido
    * */

    @RequestMapping(method = RequestMethod.POST, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public Products addProducts(@RequestBody Products products){
        return productsService.addProducts(products);
    }

    /*Este metodo retorna todos os products cadastrado no sistema*/

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Products>> getProducts(@RequestBody Products products){
        return new ResponseEntity<List<Products>>((List<Products>) productsService.getProducts(), HttpStatus.OK);
    }


    /* O metodo HTTP PUT não permite que ele receba parametros na URL, logo não sendo possivel realizar o que esta
    * Descrito na documentação do desafio. Fiz da maneira mais simples e que funcionasse. A forma mais correta e limpa de se usar o metodo PUT seria
    * de antemão ter os dados a partir de uma busca, enviando em sua integrade para a edição.
    * */

    @RequestMapping(method =  RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, value = "{id}")
    @ResponseBody
    public ResponseEntity<Products> editProducts(@PathVariable ("id") Integer id, @RequestBody Products products) {
        if(productsService.getProductsById(id) != null){
            products.setId(id);
            return new ResponseEntity<Products>(productsService.editProducts(products), HttpStatus.OK);
        }
        if(productsService.getProductsById(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return null;
    }


    /*Este metodo retorna o product pelo ID passado via paramentro*/

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Products> getProductsById(@PathVariable("id") Integer id){
        Products products = new Products();
        products = productsService.getProductsById(id);

        if(products == null){
            return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
        }if(productsService.getProductsById(id) != null){
            return new ResponseEntity<Products>(products, HttpStatus.OK);
        }
    return null;
    }


    /*Este metodo busca e filtra os produtos, não esta 100%*/

    @GetMapping
    public String searchProduto(Products products, Pageable pageable){
        return productsService.searchProduto(products.toSpec(), pageable).getContent().toString();
    }



    /*Este metodo deleta o product pelo id passado por parametro */

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Integer id){
        Products products = productsService.getProductsById(id);
        if(products != null){
            productsService.deleteProduct(products);
            return new ResponseEntity(HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
