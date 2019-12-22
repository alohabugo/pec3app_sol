package edu.uoc.android.models;

import com.google.android.gms.common.util.IOUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    MockWebServer mockServer;

    @Before
    public void setUp() throws Exception {
        // Create a MockWebServer for every unit test.
        this.mockServer = new MockWebServer();

        // Start the server.
        mockServer.start();

         MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(Utils.BODY);


        mockServer.enqueue(response);

        RecordedRequest request = mockServer.takeRequest();
        //assertEquals("POST /v1/chat/send HTTP/1.1", request.getRequestLine());
        //assertEquals("application/json; charset=utf-8", request.getHeader("Content-Type"));
        //assertEquals("{}", request.getBody().readUtf8());


        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

            if (request.getPath().equals("/api/dataset/museus/")) {
                return response;
            }
            return new MockResponse().setResponseCode(404);
        }
    }

    @Test
    public void testMuseums() throws Exception{
        URL url = this.mockServer.url("https://do.diba.cat/api/dataset/museus/json/format/pag-ini/32/pag-fi/32").url();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();

        String response = org.apache.commons.io.IOUtils.toString(inputStream, "utf-8");
        Assert.assertEquals(Utils.BODY, response);

    }

}