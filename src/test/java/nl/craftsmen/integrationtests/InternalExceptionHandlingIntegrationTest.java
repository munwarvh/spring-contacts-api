package nl.craftsmen.integrationtests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import nl.craftsmen.contacts.ContactsReadController;
import nl.craftsmen.contacts.ContactsTestdataSupplier;
import nl.craftsmen.exceptionhandling.ApiError;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import nl.craftsmen.contacts.ContactReadService;
import nl.craftsmen.exceptionhandling.UnexpectedTestException;

import reactor.test.StepVerifier;

/**
 * In this test class we only test the exception (InternalServerError) situations. All other cases are tested in
 * ContactsReadControllerTest, ContactsCreateControllerTest using mocks and in the ContactsReadIntegrationTest and
 * ContactsCreateIntegrationTest.
 * <p>
 * We disable security, since we don't want to add complexity regarding security in the controller test.
 * Security is tested in the integration test.
 * <p>
 * For more information on WebFluxTest, see https://jstobigdata.com/spring/getting-started-with-spring-webflux
 */
@WebFluxTest(controllers = {ContactsReadController.class}, excludeAutoConfiguration =
        {ReactiveSecurityAutoConfiguration.class})
class InternalExceptionHandlingIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ContactReadService contactReadService;

    @Test
    @DisplayName("When an unexpected error occurs, I expect a response 500 and an ApiError with an error message and "
            + "without validation messages")
    void findByCriteriaExpectInternalServerErrorAndApiErrorResponse() {
        // GIVEN
        given(contactReadService.findByCriteria(any())).willThrow(new UnexpectedTestException());

        // WHEN / THEN
        final var requestParams = ContactsTestdataSupplier.getRequestParams("socialsecuritynumber",
                ContactsTestdataSupplier.SOCIAL_SECURITY_NUMBER);
        final var result = webTestClient
                .get()
                .uri(ContactsTestdataSupplier.getUriWithRequestParams(requestParams))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .returnResult(ApiError.class);
        expectApiError(result);
    }

    private void expectApiError(FluxExchangeResult<ApiError> result) {
        StepVerifier.create(result.getResponseBody())
                .expectNextMatches(a -> StringUtils.isNotEmpty(a.getErrorMessage()))
                .verifyComplete();
    }
}