package nl.craftsmen.integrationtests;

import nl.craftsmen.contacts.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        // Usually users, passwords and apikeys are read from the environment.
        // For the integrationtest we specify dummy values.
        "security.apikey=dummyContactsApikey"
})
@AutoConfigureWebTestClient(timeout = "36000")
@ActiveProfiles("integrationtests")
@Testcontainers
@SuppressWarnings("rawtypes")
abstract class BaseIntegrationTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    protected WebTestClient webTestClient;

    static final PostgreSQLContainer POSTGRESQL;

    static {
        POSTGRESQL = new PostgreSQLContainer("postgres:14.3")
                .withUsername("postgres")
                .withPassword("postgres")
                .withDatabaseName("contacts");
        POSTGRESQL.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL::getPassword);
    }

    @BeforeEach
    void setup() {
        contactRepository.deleteAll();
    }
}
