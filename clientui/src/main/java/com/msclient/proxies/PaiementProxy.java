package com.msclient.proxies;

import com.msclient.beans.PaiementBean;
import com.msclient.beans.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-paiement", url = "localhost:9003")
public interface PaiementProxy {

        @PostMapping(value = "/paiement")
        ResponseEntity<PaiementBean> payerUneCommande(@RequestBody PaiementBean paiement);
}
