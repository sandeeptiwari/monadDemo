package org.example.supports;


import com.google.gson.Gson;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class HttpHelper {

    private static final Gson gson = new Gson();
    public Person callApi(String host) {
        return getPerson();
    }

    private Person getPerson() {
        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpget = new HttpGet("http://httpbin.org/get");
            System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getUri().toString());


            HttpClientResponseHandler<Optional<Person>> responseHandler = response -> {
                final int status = response.getCode();
                if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                    final HttpEntity entity = response.getEntity();
                    try {
                        return entity != null ? parsePerson(EntityUtils.toString(entity)) : null;
                    } catch (final ParseException ex) {
                        throw new ClientProtocolException(ex);
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            Optional<Person> personOptional = httpclient.execute(httpget, responseHandler);
            return personOptional.orElse(new Person());

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Person> parsePerson(String json) {
        Person person = gson.fromJson(json, Person.class);
        return Optional.ofNullable(person);
    }
}
