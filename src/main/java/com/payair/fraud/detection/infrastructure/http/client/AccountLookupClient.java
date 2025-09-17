package com.payair.fraud.detection.infrastructure.http.client;

import com.payair.fraud.detection.infrastructure.http.client.auth.OAuth1RequestFilter;
import com.payair.fraud.detection.infrastructure.http.dto.AccountLookupRequest;
import com.payair.fraud.detection.infrastructure.http.dto.AccountLookupResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/bin-ranges")
@RegisterRestClient(configKey = "lookup-api")
@RegisterProvider(HttpErrorMapper.class)
@RegisterProvider(OAuth1RequestFilter.class)
public interface AccountLookupClient {

    @POST
    @Path("/account-searches")
    List<AccountLookupResponse> fetchAccounts(AccountLookupRequest accountLookupRequest);
}
