package com.msclient.proxies;

import com.msclient.beans.CommandeBean;
import com.msclient.beans.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-commandes", url = "localhost:9002")
public interface CommandeProxy {

        @PostMapping(value = "/commandes")
        CommandeBean ajouterCommande(@RequestBody CommandeBean commande);
}
