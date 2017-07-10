package com.clouway.friendlyserve;

import com.clouway.friendlyserve.testing.FakeRequest;
import com.clouway.friendlyserve.testing.RsPrint;
import com.google.common.collect.ImmutableMap;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static com.clouway.friendlyserve.testing.FakeRequest.aNewRequest;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class RequiresHeaderTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  Take origin;

  @Test
  public void headerIsProvided() throws IOException {

    final Request request = new FakeRequest(
            ImmutableMap.<String, String>of(),
            ImmutableMap.of("Authorization", "aaaa"));

    context.checking(new Expectations() {{
      oneOf(origin).ack(request);
      will(returnValue(new RsText("::some_body::")));
    }});

    Response response = new RequiresHeader("Authorization", origin).ack(request);
    assertThat(new RsPrint(response).printBody(), is(equalTo("::some_body::")));
  }

  @Test
  public void headerIsEmpty() throws IOException {
    Response response = new RequiresHeader("Authorization", origin)
            .ack(new FakeRequest(ImmutableMap.<String, String>of(), ImmutableMap.of("Authorization", "")));
    assertThat(response.status().code, is(equalTo(HttpURLConnection.HTTP_BAD_REQUEST)));
  }

  @Test
  public void headerIsNotProvided() throws IOException {
    Response response = new RequiresHeader("Authorization", origin).ack(aNewRequest().build());
    assertThat(response.status().code, is(equalTo(HttpURLConnection.HTTP_BAD_REQUEST)));
  }
}
