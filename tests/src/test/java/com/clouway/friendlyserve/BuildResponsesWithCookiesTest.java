package com.clouway.friendlyserve;

import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class BuildResponsesWithCookiesTest {

  @Test
  public void withSingleCookie() throws Exception {
    RsWithCookie response = new RsWithCookie(
            new RsEmpty(),
            new Cookie("/", "SID", "value1", true, false, 10)
    );

    assertThat(response.cookies(), contains(new Cookie("/", "SID", "value1", true, false, 10)));
    assertThat(response.body().available(), is(0));
    assertThat(response.status().code, is(HttpURLConnection.HTTP_OK));
  }

  @Test
  public void withMultipleCookies() throws Exception {
    RsWithCookie response = new RsWithCookie(
            new RsEmpty(),
            new Cookie("/", "SID1", "value1", false, true, 10),
            new Cookie("/path1", "SID2", "value2", false, true, 11),
            new Cookie("/path2", "SID3", "value3", true, false, 12)
    );

    assertThat(response.cookies(), containsInAnyOrder(
            new Cookie("/", "SID1", "value1", false, true, 10),
            new Cookie("/path1", "SID2", "value2", false, true, 11),
            new Cookie("/path2", "SID3", "value3", true, false, 12)
    ));
  }
}
