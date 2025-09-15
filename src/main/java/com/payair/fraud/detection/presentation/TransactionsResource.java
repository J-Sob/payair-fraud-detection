package com.payair.fraud.detection.presentation;

import com.payair.fraud.detection.application.data.TransactionResult;
import com.payair.fraud.detection.domain.risk.RiskAssessment;
import com.payair.fraud.detection.application.FraudDetectionService;
import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.presentation.dto.TransactionsRequest;
import com.payair.fraud.detection.presentation.dto.TransactionsResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import static com.payair.fraud.detection.presentation.dto.DtoMappings.mapFromRequest;
import static com.payair.fraud.detection.presentation.dto.DtoMappings.mapToResponse;


@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionsResource {

    private static final Logger LOG = Logger.getLogger(TransactionsResource.class);

    @Inject
    FraudDetectionService fraudDetectionService;

    @POST
    public Response verifyTransaction(TransactionsRequest transactionsRequest) {
        LOG.info("Received transactions request: " + transactionsRequest);

        TransactionData transactionData = mapFromRequest(transactionsRequest);
        TransactionResult transactionResult = fraudDetectionService.verifyTransaction(transactionData);
        TransactionsResponse transactionsResponse = mapToResponse(transactionResult);

        return Response.ok()
                .entity(transactionsResponse)
                .build();
    }
}
