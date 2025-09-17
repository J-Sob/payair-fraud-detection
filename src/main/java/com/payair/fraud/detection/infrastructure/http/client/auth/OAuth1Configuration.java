package com.payair.fraud.detection.infrastructure.http.client.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.arc.profile.UnlessBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class OAuth1Configuration {

    @Produces
    @ApplicationScoped
    @IfBuildProfile("mastercard")
    public OAuth1HeaderFactory mastercardOauth1HeaderFactory(
            @ConfigProperty(name = "mastercard.consumer.key") String consumerKey,
            @ConfigProperty(name = "mastercard.keystore.path") String keystorePath,
            @ConfigProperty(name = "mastercard.keystore.password") String keystorePassword,
            @ConfigProperty(name = "mastercard.keystore.alias") String keyAlias,
            ObjectMapper objectMapper
    ) {
        return new MastercardAuthHeaderFactory(consumerKey, keystorePath, keystorePassword, keyAlias, objectMapper);
    }

    @Produces
    @ApplicationScoped
    @UnlessBuildProfile("mastercard")
    public OAuth1HeaderFactory mockOauth1HeaderFactory() {
        return requestContext -> "OAuth oauth_body_hash=\"9OYot8rCpnDniYky01FMP0SgJt/g3GXKbLXm5NXMikA=\",oauth_nonce=\"W9jdrtm1aKaoAhMu\",oauth_signature=\"Ul17%2BT6HTBsEdRdTRR551PzL3lbQRWsm2ogfZmOdD2GYPDC%2BC76Gl9nBbouaB462XX22L%2FQsy1shCeJovCB4NOZdWG7W6I1wBiRaKlPdlEoNyCSRdXu5tybl7uynBSxxG%2BUrv%2ByZ8bbvXEiO18CAhpwKBhad%2BQJxUvm3d0GHgXqNJZlI7uFSSKoOUET%2FZDAgZZI4uJjGnEzIgE4ujDNZ%2B20xHT3F9UfmyLWWO7djPXeUzNI8kqK%2FgU6cFbOJZeFk7TPHE2wF511qwHXjUJFqOH4gHzhrXmziVSWYxquwoHmeQoPualiteg4SJjDRueIlUqNREWyIBwClC%2FYZvBoeiA%3D%3D\",oauth_consumer_key=\"OF3NuuSK2fQkq4CMq17rufarYT4NKLvMImmOrHMi5e038267!7afc64398bc548798c7df8de555c54d40000000000000000\",oauth_signature_method=\"RSA-SHA256\",oauth_timestamp=\"1758104877\",oauth_version=\"1.0\"";
    }
}
