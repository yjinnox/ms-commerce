package com.mproduits.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component //Scanne la class ApplicationPropertiesConfig pour aller chercher des configurations
@ConfigurationProperties("mes-configs")// cette annotation précise que la classe de configuration va récupérer des propriétés dans bootstrap.properties dont le préfixe est : mes-configs.
public class ApplicationPropertiesConfig {

    private int limitDeProduits;

    public int getLimitDeProduits() {
        return limitDeProduits;
    }

    public void setLimitDeProduits(int limitDeProduits) {
        this.limitDeProduits = limitDeProduits;
    }
}
