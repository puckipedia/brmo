/*
 * Copyright (C) 2018 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.b3p.web;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mark Prins
 */
public class DownloadLogIntegrationTest extends WebTestUtil {

    /**
     * Test login/downloadlog/logout sequentie.
     *
     * @throws IOException mag niet optreden
     * @throws URISyntaxException mag niet optreden
     */
    @Test
    public void testLoginDownloadLogsLogoutPage() throws Exception {
        // login
        HttpResponse response = client.execute(new HttpGet(BASE_TEST_URL));
        EntityUtils.consume(response.getEntity());

        HttpUriRequest login = RequestBuilder.post()
                .setUri(new URI(BASE_TEST_URL + "j_security_check"))
                .addParameter("j_username", "brmo")
                .addParameter("j_password", "brmo")
                .build();
        response = client.execute(login);
        EntityUtils.consume(response.getEntity());
        assertThat("Response status is OK.", response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));

        // downloadlog
        response = client.execute(new HttpGet(BASE_TEST_URL + "downloadlog.jsp"));
        assertThat("Response status is OK.", response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        String body = EntityUtils.toString(response.getEntity());
        assertNotNull(body, "Response body mag niet null zijn.");
        // System.out.println("\n\n=============== BODY:=================\n" + body + "\n\n");
        // test eerste regel uit de logfile
        assertTrue(body.contains("Quartz scheduler 'BRMOgeplandeTaken' initialized from an externally provided properties instance."));

        // logout
        response = client.execute(new HttpGet(BASE_TEST_URL + "logout.jsp"));
        body = EntityUtils.toString(response.getEntity());
        assertThat("Response status is OK.", response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        assertNotNull(body, "Response body mag niet null zijn.");
        assertTrue(body.contains("Opnieuw inloggen"), "Response moet 'Opnieuw inloggen' tekst hebben.");
    }

    @Override
    public void setUp() {
        // void implementatie
    }
}
