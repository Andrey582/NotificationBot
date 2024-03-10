package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import edu.java.controller.StackOverflowController;
import edu.java.dto.response.StackOverflowResponseItem;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@SpringBootTest(classes = ScrapperApplication.class)
@TestPropertySource(locations = "classpath:test")
@WireMockTest(httpPort = 8080)
class StackOverflowSearchClientTest {
    @Autowired
    StackOverflowController stackOverflowController;

    private void startServer() {
        stubFor(
            get(urlPathMatching("/2.3/search/advanced"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(
                        """
                            {
                                "items": [
                                    { "last_activity_date": 1708825321 },
                                    { "last_activity_date": 1708823525 }
                                ]
                            }
                            """
                    )));
    }

    @Test
    void stackOverflowSearchTest() {

        OffsetDateTime first = OffsetDateTime.parse("2024-02-25T01:42:01Z");
        OffsetDateTime second = OffsetDateTime.parse("2024-02-25T01:12:05Z");

        startServer();

        List<StackOverflowResponseItem> body = stackOverflowController.getSearch("test")
            .block().items();

        assertThat(body.get(0).lastActivity()).isEqualTo(first);
        assertThat(body.get(1).lastActivity()).isEqualTo(second);
    }

}
