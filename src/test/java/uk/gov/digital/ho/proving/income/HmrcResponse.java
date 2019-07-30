package uk.gov.digital.ho.proving.income;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import uk.gov.digital.ho.proving.income.domain.AccessCode;
import uk.gov.digital.ho.proving.income.domain.Applicant;

import java.io.IOException;
import java.nio.charset.Charset;

import static uk.gov.digital.ho.proving.income.IpsSearchTest.wireMockRule;


public class HmrcResponse {

    private static final String ACCESS_ID = "ACCESS-ID";
    private ObjectMapper objectMapper = new ObjectMapper();


    String buildAccessCodeResponse() throws IOException {
        return getResponseFile("/template/matchedUser/accessCodeResponse.json");
    }

    String buildMatchResponse(String applicantId) throws IOException {
        return getResponseFile("/template/matchedUser/matchResponse.json")
                .replace("${matchId}", applicantId);
    }

    String buildFailedMatchResponse() throws IOException {
        return getResponseFile("/template/failedMatchResponse.json");
    }

    String buildMatchedIndividualResponse(Applicant applicant) throws IOException {
        return getResponseFile("/template/matchedUser/individualMatchResponse.json")
                .replace("${matchId}", applicant.nino())
                .replace("${firstName}", applicant.forename())
                .replace("${lastName}", applicant.surname())
                .replace("${nino}", applicant.nino())
                .replace("${year}", String.valueOf(applicant.dateOfBirth().getYear()))
                .replace("${month}", String.format("%02d", applicant.dateOfBirth().getMonthValue()))
                .replace("${day}", String.format("%02d", applicant.dateOfBirth().getDayOfMonth()));
    }

    String buildOauthResponse() throws JsonProcessingException {
        return asJson(new AccessCode(ACCESS_ID, null));
    }

    String buildIncomeResponse(String applicantId) throws IOException {
        return getResponseFile("/template/matchedUser/incomeResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildEmploymentsResponse(String applicantId) throws IOException {
        return getResponseFile("/template/matchedUser/employmentsResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildEmploymentsPayeResponse(String responseFile, String applicantId) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildPayeIncomeResponse(String responseFile, String applicantId) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildEmptySaResponse(String responseFile, String applicantId) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildSaMatchIdResponse(String responseFile, String applicantId) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildSaSummaryResponse(String responseFile, String applicantId) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    String buildSaSelfEmploymentResponse(String responseFile, String applicantId) throws IOException {
        return getResponseFile(responseFile)
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", applicantId);
    }

    private String getResponseFile(String fileName) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(fileName), Charset.defaultCharset());
    }

    private String asJson(Object input) throws JsonProcessingException {
        return objectMapper.writeValueAsString(input);
    }

}
