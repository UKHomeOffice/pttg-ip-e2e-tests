package uk.gov.digital.ho.proving.income;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static uk.gov.digital.ho.proving.income.HmrcResponse.MATCH_ID;
import static uk.gov.digital.ho.proving.income.IpsSearchTest.wireMockRule;

class HmrcStub {

    private HmrcResponse hmrcResponse = new HmrcResponse();

    void stubPassUser() throws IOException {

        getMatchedUser();

        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/pass/employmentsPayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/pass/incomePayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/pass/incomeSAResponseEmpty.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/pass/incomeSASelfEmploymentsResponse.json"))));

    }

    void stubCatAPassUser() throws IOException {

        getMatchedUser();

        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/pass_cat_A/employmentsPayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/pass_cat_A/incomePayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/pass_cat_A/incomeSAResponseEmpty.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/pass_cat_A/incomeSASelfEmploymentsResponse.json"))));

    }

    void stubCatBPassUser() throws IOException {

        getMatchedUser();

        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/pass_cat_B/employmentsPayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/pass_cat_B/incomePayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/pass_cat_B/incomeSAResponseEmpty.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/pass_cat_B/incomeSASelfEmploymentsResponse.json"))));

    }

    void stubCatBNonPassUser() throws IOException {

        getMatchedUser();

        wireMockRule.stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsPayeResponse("/template/payments/not_pass_cat_B/employmentsPayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildPayeIncomeResponse("/template/payments/not_pass_cat_B/incomePayeResponse.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmptySaResponse("/template/payments/not_pass_cat_B/incomeSAResponseEmpty.json"))));

        wireMockRule.stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildSaSelfEmploymentResponse("/template/payments/not_pass_cat_B/incomeSASelfEmploymentsResponse.json"))));
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

    private void getMatchedUser() throws IOException {

        wireMockRule.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildAccessCodeResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        wireMockRule.stubFor(post(urlEqualTo("/individuals/matching/"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(hmrcResponse.buildMatchResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        wireMockRule.stubFor(get(urlEqualTo("/individuals/matching/" + MATCH_ID))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildMatchedIndividualResponse())));

        wireMockRule.stubFor(get(urlEqualTo("/individuals/income/?matchId=" + MATCH_ID))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildIncomeResponse())));

        wireMockRule.stubFor(get(urlEqualTo("/individuals/employments/?matchId=" + MATCH_ID))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(hmrcResponse.buildEmploymentsResponse())));
    }

}
