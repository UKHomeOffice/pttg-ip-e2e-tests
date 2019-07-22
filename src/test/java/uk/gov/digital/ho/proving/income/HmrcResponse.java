package uk.gov.digital.ho.proving.income;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import uk.gov.digital.ho.proving.income.domain.AccessCode;

import java.io.IOException;
import java.nio.charset.Charset;

import static uk.gov.digital.ho.proving.income.IpsSearchTest.wireMockRule;


public class HmrcResponse {

    static final String MATCH_ID = "MATCH-ID";
    private static final String ACCESS_ID = "ACCESS-ID";
    private ObjectMapper objectMapper = new ObjectMapper();


    String buildAccessCodeResponse() throws IOException {
        return getResponseFile("/template/matchedUser/accessCodeResponse.json");
    }

    String buildMatchResponse() throws IOException {
        return getResponseFile("/template/matchedUser/matchResponse.json")
                .replace("${matchId}", MATCH_ID);
    }

    String buildFailedMatchResponse() throws IOException {
        return getResponseFile("/template/failedMatchResponse.json")
                .replace("${matchId}", MATCH_ID);
    }

    String buildMatchedIndividualResponse() throws IOException {
        return getResponseFile("/template/matchedUser/individualMatchResponse.json")
                .replace("${matchId}", MATCH_ID);
    }

    String buildOauthResponse() throws JsonProcessingException {
        return asJson(new AccessCode(ACCESS_ID, null));
    }

    String buildIncomeResponse() throws IOException {
        return getResponseFile("/template/matchedUser/incomeResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    String buildEmploymentsResponse() throws IOException {
        return getResponseFile("/template/matchedUser/employmentsResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    String buildEmploymentsPayeResponse(String responseFile) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    String buildPayeIncomeResponse(String responseFile) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    String buildEmptySaResponse(String responseFile) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    String buildSaSelfEmploymentResponse(String responseFile) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    private String getResponseFile(String fileName) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(fileName), Charset.defaultCharset());
    }

    private String asJson(Object input) throws JsonProcessingException {
        return objectMapper.writeValueAsString(input);
    }

}
