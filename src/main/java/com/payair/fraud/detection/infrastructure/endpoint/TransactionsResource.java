package com.payair.fraud.detection.infrastructure.endpoint;

import com.payair.fraud.detection.domain.FraudAttestation;
import com.payair.fraud.detection.domain.FraudDetectionService;
import com.payair.fraud.detection.domain.TransactionData;
import com.payair.fraud.detection.infrastructure.endpoint.dto.TransactionsRequest;
import com.payair.fraud.detection.infrastructure.endpoint.dto.TransactionsResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import static com.payair.fraud.detection.infrastructure.endpoint.dto.DtoMappings.mapFromRequest;
import static com.payair.fraud.detection.infrastructure.endpoint.dto.DtoMappings.mapToResponse;


@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionsResource {

    private static final Logger LOG = Logger.getLogger(TransactionsResource.class);

    @Inject
    FraudDetectionService fraudDetectionService;

    @POST
    public Response transactions(TransactionsRequest transactionsRequest) {
        LOG.info("Received transactions request: " + transactionsRequest);

        TransactionData transactionData = mapFromRequest(transactionsRequest);
        FraudAttestation fraudAttestation = fraudDetectionService.detectFraud(transactionData);
        TransactionsResponse transactionsResponse = mapToResponse(fraudAttestation);

        return Response.ok()
                .entity(transactionsResponse)
                .build();
    }
}
