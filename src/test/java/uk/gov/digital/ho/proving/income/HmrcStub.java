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
        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/pass/employmentsPayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/pass/incomePayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/pass/incomeSAResponseEmpty.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/pass/incomeSASelfEmploymentsResponse.json", applicantId))));
    }

    void stubCatAPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);

        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/pass_cat_A/employmentsPayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/pass_cat_A/incomePayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/pass_cat_A/incomeSAResponseEmpty.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/pass_cat_A/incomeSASelfEmploymentsResponse.json", applicantId))));
    }

    void stubCatANotPassUser(Applicant applicant, Applicant partner) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);
        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/not_pass_cat_A/employmentsPayeResponse1.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/not_pass_cat_A/incomePayeResponse1.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/not_pass_cat_A/incomeSAResponseEmpty.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/not_pass_cat_A/incomeSASelfEmploymentsResponse.json", applicantId))));

        String partnerId = partner.nino();
        getMatchedUser(partner);
        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + partnerId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/not_pass_cat_A/employmentsPayeResponse2.json", partnerId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + partnerId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/not_pass_cat_A/incomePayeResponse2.json", partnerId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + partnerId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/not_pass_cat_A/incomeSAResponseEmpty.json", partnerId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + partnerId + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/not_pass_cat_A/incomeSASelfEmploymentsResponse.json", partnerId))));
    }

    void stubCatBPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);
        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/pass_cat_B/employmentsPayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/pass_cat_B/incomePayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/pass_cat_B/incomeSAResponseEmpty.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/pass_cat_B/incomeSASelfEmploymentsResponse.json", applicantId))));

    }

    void stubCatBNotPassUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();
        getMatchedUser(applicant);
        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/not_pass_cat_B/employmentsPayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + applicantId + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/not_pass_cat_B/incomePayeResponse.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/not_pass_cat_B/incomeSAResponseEmpty.json", applicantId))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + applicantId + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/not_pass_cat_B/incomeSASelfEmploymentsResponse.json", applicantId))));
    }


    void createFailedMatchStubs() throws IOException {

        wireMockRule.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildAccessCodeResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        wireMockRule.stubFor(post(urlEqualTo("/individuals/matching/"))
                .willReturn(aResponse().withStatus(HttpStatus.FORBIDDEN.value())
                        .withBody(hmrcResponse.buildFailedMatchResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    private void getMatchedUser(Applicant applicant) throws IOException {

        String applicantId = applicant.nino();

        wireMockRule.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildAccessCodeResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        wireMockRule.stubFor(post(urlEqualTo("/individuals/matching/"))
                .withRequestBody(matchingJsonPath("$[?(@.nino == '" + applicantId + "')]"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildMatchResponse(applicantId))
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        wireMockRule.stubFor(get(urlEqualTo("/individuals/matching/" + applicantId))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildMatchedIndividualResponse(applicant))));

        wireMockRule.stubFor(get(urlEqualTo("/individuals/income/?matchId=" + applicantId))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildIncomeResponse(applicantId))));

        wireMockRule.stubFor(get(urlEqualTo("/individuals/employments/?matchId=" + applicantId))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsResponse(applicantId))));
    }

}
