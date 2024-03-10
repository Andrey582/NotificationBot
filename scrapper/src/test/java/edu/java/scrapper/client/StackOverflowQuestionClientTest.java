package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import edu.java.controller.StackOverflowController;
import java.time.OffsetDateTime;
import java.util.List;
import edu.java.dto.response.StackOverflowResponseItem;
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
class StackOverflowQuestionClientTest {

    @Autowired
    StackOverflowController stackOverflowController;

    private void startServer() {
        stubFor(
            get(urlPathMatching("/2.3/questions/123456"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(
                        """
                            {
                                "items": [
                                    { "last_activity_date": 1708825321 }
                                ]
                            }
                            """
                    )));
    }

    @Test
    void stackOverflowSearchTest() {

        OffsetDateTime first = OffsetDateTime.parse("2024-02-25T01:42:01Z");

        startServer();

        List<StackOverflowResponseItem> body = stackOverflowController.getQuestionById(123456L)
            .block().items();

        assertThat(body.get(0).lastActivity()).isEqualTo(first);
    }

}
