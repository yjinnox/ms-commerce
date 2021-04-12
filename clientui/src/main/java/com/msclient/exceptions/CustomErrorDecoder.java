package com.msclient.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response response)  {
        if(response.status() == 400 ) {
            return new ProductBadRequestException(
                    "Requête incorrecte"
            );
        }
        else if(response.status() > 400 && response.status() <=499 ) {
            return new Product4XXException(
                    "Erreur de au format 4XX "
            );
        }
        return defaultErrorDecoder.decode(invoqueur, response);
    }
}
