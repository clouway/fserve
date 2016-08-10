package com.clouway.friendlyserve.servlets;

import com.clouway.friendlyserve.HttpException;
import com.clouway.friendlyserve.Response;
import com.clouway.friendlyserve.Status;
import com.clouway.friendlyserve.Take;
import com.clouway.friendlyserve.TkRequestWrap;
import com.google.common.io.ByteStreams;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * ServletApiSupport is an adapter class which adapts the Servlet API into fserve to provide seamless integration.
 *
 * <p>Hooking it in the Servlet class:</p>
 * <pre>
 *  {@literal @}Override
 *  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 *     servletApiSupport.serve(req, resp);
 *  }
 *
 * </pre>
 *
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public final class ServletApiSupport {
  private final Take take;

  public ServletApiSupport(Take take) {
    this.take = take;
  }

  /**
   * Serves the provided Servlet Request and writes the response back to the provided {@link HttpServletResponse}.
   *
   * @param req the servlet request which need to be served
   * @param resp the response which to be written
   * @throws IOException is thrown in case of IO error or some internal error.
   */
  public void serve(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      Response response = take.ack(new TkRequestWrap(req));

      Status status = response.status();
      resp.setStatus(status.code);

      // Handle redirects
      if (status.code == HttpURLConnection.HTTP_MOVED_TEMP) {
        resp.sendRedirect(status.redirectUrl);

        return;
      }

      Map<String, String> header = response.header();
      for (String key : header.keySet()) {
        resp.setHeader(key, header.get(key));
      }

      ServletOutputStream out = resp.getOutputStream();

      try (InputStream inputStream = response.body()) {
        ByteStreams.copy(inputStream, out);
      }

      out.flush();

    } catch (HttpException e) {
      resp.setStatus(e.code());
    }


  }
}
