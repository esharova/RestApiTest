package ru.hh.rest;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.utils.AuthorizationHelper;
import ru.hh.data.HHUser;
import ru.hh.data.Token;
import ru.hh.utils.RetrieveUtil;

public class GetUserInfo {

    private static final Logger log = LoggerFactory.getLogger(GetUserInfo.class);
    private static Token token;
    private static HHUser data;
    private static Properties properties;
    private HttpUriRequest request;



    @BeforeClass
    public static void passAuthentification() throws ClientProtocolException, IOException {
        AuthorizationHelper authHelper = new AuthorizationHelper();
        data = authHelper.getData();
        token = authHelper.getAccessToken();
        loadAdditionalTestData();
    }

    private static void loadAdditionalTestData(){
        properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Before
    public void formHttpGetRequest(){
        request = new HttpGet("https://api.hh.ru/me");
        request.addHeader("User-Agent", "RestApiTest/1.0 (sharovahelene@gmail.com)");
        request.addHeader("Host", "api.hh.ru");
        request.addHeader("Accept", "*/*");
        request.addHeader("Authorization", "Bearer " + token.getAccess_token());
    }


    @Test
    public void failToGetUserInfo() throws ClientProtocolException, IOException {
        // Given
        final HttpUriRequest request = new HttpGet("https://api.hh.ru/me");
        // When
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_FORBIDDEN));
    }

    @Test
    public void checkToken() {
        assertNotNull(token);
    }

    @Test
    public void checkCorrectCallStatus() throws ClientProtocolException, IOException {

        // When
        final HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(response.getStatusLine().getStatusCode(), Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void checkCorrectCallMimeType() throws ClientProtocolException, IOException {
        // Given
        final String jsonMimeType = "application/json";
        // When
        final HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        final String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    public void checkCorrectCallUserFields() throws ClientProtocolException, IOException {
        // Given
        final HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        final HHUser resource = RetrieveUtil.retrieveResourceFromResponse(response, HHUser.class);
        log.info("HHUser: {}", resource);
        checkUserInfo(resource);
    }

    private void checkUserInfo(HHUser resource) {
        assertEquals(data, resource);
        assertThat(properties.getProperty("resumes_url"), Matchers.is(resource.getResumes_url()));
        assertThat(properties.getProperty("negotiations_url"), Matchers.is(resource.getNegotiations_url()));
        assertNotNull(resource.getId());
        assertNull(resource.getMiddle_name());
        assertNotNull(resource.getCounters());
        assertTrue(resource.getCounters().getNew_resume_views().getClass().equals(Integer.class));
        assertTrue(resource.getCounters().getUnread_negotiations().getClass().equals(Integer.class));
    }
}
