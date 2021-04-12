package com.msclient.proxies;

import com.msclient.beans.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-produits", url = "localhost:9001")
public interface ProduitsProxy {

        @GetMapping(value = "/produits")
        List<ProductBean> listeDesProduits();

        @GetMapping( value = "/produits/{id}")
        ProductBean recupererUnProduit(@PathVariable("id") int id);
}
