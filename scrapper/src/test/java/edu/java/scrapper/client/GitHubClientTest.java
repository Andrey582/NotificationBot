package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import edu.java.controller.GitHubController;
import edu.java.dto.response.GitHubResponseDto;
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
class GitHubClientTest {
    @Autowired
    GitHubController gitHubController;

    private void startServer() {
        stubFor(
            get(urlPathMatching("/repos/test/test/events"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(
                        """
                            [
                                {
                                    "created_at": "2024-02-24T13:33:49Z"
                                },
                                {
                                    "created_at": "2024-02-24T11:27:49Z"
                                }
                            ]
                            """
                    )));
    }

    @Test
    void gitHubClientTest() {

        var first = OffsetDateTime.parse("2024-02-24T13:33:49Z");
        var second = OffsetDateTime.parse("2024-02-24T11:27:49Z");

        startServer();

        List<GitHubResponseDto> body =
            gitHubController.getEvents("test", "test")
                .block().getBody();

        assertThat(body.get(0).create()).isEqualTo(first);
        assertThat(body.get(1).create()).isEqualTo(second);
    }


}
