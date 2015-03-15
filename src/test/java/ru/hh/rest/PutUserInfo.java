package ru.hh.rest;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.data.ErrorMessage;
import ru.hh.data.HHUser;
import ru.hh.data.Token;
import ru.hh.utils.AuthorizationHelper;
import ru.hh.utils.RetrieveUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.ErrorManager;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by HonneyHelen on 15-Mar-15.
 */
public class PutUserInfo {

    private static final Logger log = LoggerFactory.getLogger(GetUserInfo.class);
    private static Token token;
    private static HHUser data;

    private HttpPost request;

    @BeforeClass
    public static void passAuthentification() throws ClientProtocolException, IOException {
        AuthorizationHelper authHelper = new AuthorizationHelper();
        token = authHelper.getAccessToken();
        data = authHelper.getData();
    }
    @Before
    public void formHttpPostRequest(){
        request = new HttpPost("https://api.hh.ru/me");
        request.addHeader("User-Agent", "RestApiTest/1.0 (sharovahelene@gmail.com)");
        request.addHeader("Host", "api.hh.ru");
        request.addHeader("Accept", "*/*");
        request.addHeader("Authorization", "Bearer " + token.getAccess_token());
        request.addHeader("Content-Type", "application/x-www-form-urlencoded ; charset=UTF-8");
    }

    @Test
    public void failToPutUserInfoWithoutParams() throws ClientProtocolException, IOException {
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void failToPutUserInfoWithoutMiddleName() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("last_name",data.getLast_name()));
        nameValuePairs.add(new BasicNameValuePair("first_name", data.getFirst_name()));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }
    @Test
    public void failToPutUserInfoWithoutFirsttName() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("last_name",data.getLast_name()));
        nameValuePairs.add(new BasicNameValuePair("middle_name", ""));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void failToPutUserInfoWithoutLastName() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("first_name",data.getFirst_name()));
        nameValuePairs.add(new BasicNameValuePair("middle_name", ""));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void successToPutUserFIO() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("first_name",data.getFirst_name()));
        nameValuePairs.add(new BasicNameValuePair("middle_name", ""));
        nameValuePairs.add(new BasicNameValuePair("last_name", data.getLast_name()));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_NO_CONTENT));
    }

    @Test
    public void successToPutInSearch() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("is_in_search","true"));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_NO_CONTENT));
    }

    @Test
         public void failToPutBothGroups() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("first_name",data.getFirst_name()));
        nameValuePairs.add(new BasicNameValuePair("middle_name", ""));
        nameValuePairs.add(new BasicNameValuePair("last_name", data.getLast_name()));
        nameValuePairs.add(new BasicNameValuePair("is_in_search","true"));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }
    @Test
    public void failToMixGroups() throws ClientProtocolException, IOException {
        // Given
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("first_name",data.getFirst_name()));
        nameValuePairs.add(new BasicNameValuePair("last_name", data.getLast_name()));
        nameValuePairs.add(new BasicNameValuePair("is_in_search","true"));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    public void checkErrorMessage() throws ClientProtocolException, IOException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("first_name",data.getFirst_name()));
        nameValuePairs.add(new BasicNameValuePair("middle_name", ""));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        final ErrorMessage resource = RetrieveUtil.retrieveResourceFromResponse(httpResponse, ErrorMessage.class);
        log.info("Error Message: {}", resource);

        assertThat("Required parameter",Matchers.is(resource.getDescription()));
        assertThat("last_name",Matchers.is(resource.getBad_argument()));
    }

}
