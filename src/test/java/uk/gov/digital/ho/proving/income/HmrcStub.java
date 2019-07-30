package uk.gov.digital.ho.proving.income;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import uk.gov.digital.ho.proving.income.domain.Applicant;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static uk.gov.digital.ho.proving.income.IpsSearchTest.wireMockRule;

class HmrcStub {

    private HmrcResponse hmrcResponse = new HmrcResponse();

    void stubPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        stubEmploymentPaye(applicantId, "/template/payments/pass/employmentsPayeResponse.json");
        stubIncomePaye(applicantId, "/template/payments/pass/incomePayeResponse.json");
        stubIncomeSaMatchId(applicantId, "/template/payments/pass/incomeSAEmploymentsResponse.json");
        stubIncomeSa(applicantId, "/template/payments/pass/incomeSAResponse.json");
        stubIncomeSelfAssessment(applicantId, "/template/payments/pass/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(applicantId, "/template/payments/pass/incomeSASummaryResponse.json");
    }

    void stubCatAPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        stubEmploymentPaye(applicantId, "/template/payments/pass_cat_A/employmentsPayeResponse.json");
        stubIncomePaye(applicantId, "/template/payments/pass_cat_A/incomePayeResponse.json");
        stubIncomeSaMatchId(applicantId, "/template/payments/pass_cat_A/incomeSAEmploymentsResponse.json");
        stubIncomeSa(applicantId, "/template/payments/pass_cat_A/incomeSAResponse.json");
        stubIncomeSelfAssessment(applicantId, "/template/payments/pass_cat_A/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(applicantId, "/template/payments/pass_cat_A/incomeSASummaryResponse.json");
    }

    void stubCatANotPassUser(Applicant applicant, Applicant partner) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        stubEmploymentPaye(applicantId, "/template/payments/not_pass_cat_A/employmentsPayeResponse1.json");
        stubIncomePaye(applicantId, "/template/payments/not_pass_cat_A/incomePayeResponse1.json");
        stubIncomeSaMatchId(applicantId, "/template/payments/not_pass_cat_A/incomeSAEmploymentsResponse.json");
        stubIncomeSa(applicantId, "/template/payments/not_pass_cat_A/incomeSAResponse.json");
        stubIncomeSelfAssessment(applicantId, "/template/payments/not_pass_cat_A/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(applicantId, "/template/payments/not_pass_cat_A/incomeSASummaryResponse.json");

        String partnerId = partner.nino();
        getMatchedUser(partner);

        stubEmploymentPaye(partnerId, "/template/payments/not_pass_cat_A/employmentsPayeResponse2.json");
        stubIncomePaye(partnerId, "/template/payments/not_pass_cat_A/incomePayeResponse2.json");
        stubIncomeSaMatchId(partnerId, "/template/payments/not_pass_cat_A/incomeSAEmploymentsResponse.json");
        stubIncomeSa(partnerId, "/template/payments/not_pass_cat_A/incomeSAResponse.json");
        stubIncomeSelfAssessment(partnerId, "/template/payments/not_pass_cat_A/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(partnerId, "/template/payments/not_pass_cat_A/incomeSASummaryResponse.json");
    }

    void stubCatBPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        stubEmploymentPaye(applicantId, "/template/payments/pass_cat_B/employmentsPayeResponse.json");
        stubIncomePaye(applicantId, "/template/payments/pass_cat_B/incomePayeResponse.json");
        stubIncomeSaMatchId(applicantId, "/template/payments/pass_cat_B/incomeSAEmploymentsResponse.json");
        stubIncomeSa(applicantId, "/template/payments/pass_cat_B/incomeSAResponse.json");
        stubIncomeSelfAssessment(applicantId, "/template/payments/pass_cat_B/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(applicantId, "/template/payments/pass_cat_B/incomeSASummaryResponse.json");
    }

    void stubCatBNotPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        stubEmploymentPaye(applicantId, "/template/payments/not_pass_cat_B/employmentsPayeResponse.json");
        stubIncomePaye(applicantId, "/template/payments/not_pass_cat_B/incomePayeResponse.json");
        stubIncomeSaMatchId(applicantId, "/template/payments/not_pass_cat_B/incomeSAEmploymentsResponse.json");
        stubIncomeSa(applicantId, "/template/payments/not_pass_cat_B/incomeSAResponse.json");
        stubIncomeSelfAssessment(applicantId, "/template/payments/not_pass_cat_B/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(applicantId, "/template/payments/not_pass_cat_B/incomeSASummaryResponse.json");
    }

    void stubCatFPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        stubEmploymentPaye(applicantId, "/template/payments/pass_cat_F/employmentsPayeResponse.json");
        stubIncomePaye(applicantId, "/template/payments/pass_cat_F/incomePayeResponse.json");
        stubIncomeSaMatchId(applicantId, "/template/payments/pass_cat_F/incomeSAEmploymentsResponse.json");
        stubIncomeSa(applicantId, "/template/payments/pass_cat_F/incomeSAResponse.json");
        stubIncomeSelfAssessment(applicantId, "/template/payments/pass_cat_F/incomeSASelfEmploymentsResponse.json");
        stubIncomeSaSummary(applicantId, "/template/payments/pass_cat_F/incomeSASummaryResponse.json");
    }

    void createFailedMatchStubs() throws IOException {

        stubOAuthToken("/oauth/token");
        stubNotFoundIndividual();
    }

    private void getMatchedUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        stubOAuthToken("/oauth/token");

        stubMatchedIndividual(applicantId);
        stubIndividualId(applicantId, applicant);
        stubIncome(applicantId);
        stubEmployment(applicantId);
    }

    void stubOAuthToken(String authPath) throws IOException {
        wireMockRule.stubFor(post(urlEqualTo(authPath))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildAccessCodeResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    void stubMatchedIndividual(String applicantId) throws IOException {
        wireMockRule.stubFor(post(urlEqualTo("/individuals/matching/"))
                .withRequestBody(matchingJsonPath("$[?(@.nino == '" + applicantId + "')]"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildMatchResponse(applicantId))
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    void stubNotFoundIndividual() throws IOException {
        wireMockRule.stubFor(post(urlEqualTo("/individuals/matching/"))
                .willReturn(aResponse().withStatus(HttpStatus.FORBIDDEN.value())
                        .withBody(hmrcResponse.buildFailedMatchResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    void stubIndividualId(String applicantId, Applicant applicant) throws IOException {
        wireMockRule.stubFor(get(urlEqualTo("/individuals/matching/" + applicantId))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildMatchedIndividualResponse(applicant))));
    }

    void stubIncome(String applicantId) throws IOException {
        wireMockRule.stubFor(get(urlEqualTo("/individuals/income/?matchId=" + applicantId))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildIncomeResponse(applicantId))));
    }

    void stubEmployment(String applicantId) throws IOException {
        wireMockRule.stubFor(get(urlEqualTo("/individuals/employments/?matchId=" + applicantId))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsResponse(applicantId))));
    }

    void stubEmploymentPaye(String applicantId, String responseFile) throws IOException {
        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse(responseFile, applicantId))));
    }

    void stubIncomeSaMatchId(String applicantId, String responseFile) throws IOException {
        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaMatchIdResponse(responseFile, applicantId))));
    }

    void stubIncomePaye(String applicantId, String responseFile) throws IOException {
        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse(responseFile, applicantId))));
    }

    void stubIncomeSa(String applicantId, String responseFile) throws IOException {
        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse(responseFile, applicantId))));
    }

    void stubIncomeSelfAssessment(String applicantId, String responseFile) throws IOException {
        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse(responseFile, applicantId))));
    }

    void stubIncomeSaSummary(String applicantId, String responseFile) throws IOException {
        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/summary.*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSummaryResponse(responseFile, applicantId))));
    }

}
