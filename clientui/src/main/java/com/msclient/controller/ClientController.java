package com.msclient.controller;

import com.msclient.beans.CommandeBean;
import com.msclient.beans.PaiementBean;
import com.msclient.beans.ProductBean;
import com.msclient.proxies.CommandeProxy;
import com.msclient.proxies.PaiementProxy;
import com.msclient.proxies.ProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ClientController {

    @Autowired
    private ProduitsProxy produitsProxy;

    @Autowired
    private CommandeProxy commandeProxy;

    @Autowired
    private PaiementProxy paiementProxy;

    /*
     * Étape (1)
     * Opération qui récupère la liste des produits et on les affichent dans la page d'accueil.
     * Les produits sont récupérés grâce à ProduitsProxy
     * On fini par rentourner la page Accueil.html à laquelle on passe la liste d'objets "produits" récupérés.
     * */
    @RequestMapping(value = "/")
    public String accueil(Model model){
        List<ProductBean> productBean = produitsProxy.listeDesProduits();
        model.addAttribute("produits", productBean);
        return "accueil";
    }

    /*
     * Étape (2)
     * Opération qui récupère les détails d'un produit
     * On passe l'objet "produit" récupéré et qui contient les détails en question à  FicheProduit.html
     * */
    @RequestMapping(value="/details-produit/{id}")
    public String ficheProduit(@PathVariable int id, Model model){

        ProductBean produit = produitsProxy.recupererUnProduit(id);
        model.addAttribute("produit", produit);
        return "detail-produit";
    }

    /*
     * Étape (3) et (4)
     * Opération qui fait appel au microservice de commande pour passer une commande et récupérer les détails de la commande créée
     * */
    @RequestMapping(value = "/details-produit/commander-produit/{idProduit}/{montant}")
    public String passerCommande(@PathVariable int idProduit, @PathVariable Double montant, Model model){

        CommandeBean commande = new CommandeBean();

        //On renseigne les propriétés de l'objet de type CommandeBean que nous avons crée
        commande.setProductId(idProduit);
        commande.setQuantite(1);
        commande.setDateCommande(new Date());

        //appel du microservice commandes grâce à Feign et on récupère en retour les détails de la commande créée, notamment son ID (étape 4).
        CommandeBean commandeAjoutee = commandeProxy.ajouterCommande(commande);

        //on passe à la vue l'objet commande et le montant de celle-ci afin d'avoir les informations nécessaire pour le paiement
        model.addAttribute("commande", commandeAjoutee);
        model.addAttribute("montant", montant);

        return "paiement";
    }

    /*
     * Étape (5)
     * Opération qui fait appel au microservice de paiement pour traiter un paiement
     * */
    @RequestMapping(value = "/payer-commande/{idCommande}/{montantCommande}")
    public String payerCommande(@PathVariable int idCommande, @PathVariable Double montantCommande, Model model){

        PaiementBean paiementAExcecuter = new PaiementBean();

        //on reseigne les détails du produit
        paiementAExcecuter.setIdCommande(idCommande);
        paiementAExcecuter.setMontant(montantCommande);
        paiementAExcecuter.setNumeroCarte(numcarte()); // on génère un numéro au hasard pour simuler une CB

        // On appel le microservice et (étape 7) on récupère le résultat qui est sous forme ResponseEntity<PaiementBean> ce qui va nous permettre de vérifier le code retour.
        ResponseEntity<PaiementBean> paiement = paiementProxy.payerUneCommande(paiementAExcecuter);

        Boolean paiementAccepte = false;
        //si le code est autre que 201 CREATED, c'est que le paiement n'a pas pu aboutir.
        if(paiement.getStatusCode() == HttpStatus.CREATED)
            paiementAccepte = true;

        model.addAttribute("paiementOk", paiementAccepte); // on envoi un Boolean paiementOk à la vue

        return "confirmation";
    }

    //Génére une serie de 16 chiffres au hasard pour simuler vaguement une CB
    private Long numcarte() {

        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }
}
