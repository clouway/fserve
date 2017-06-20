package com.clouway.friendlyserve.testing;

import com.clouway.friendlyserve.Cookie;
import com.clouway.friendlyserve.Request;
import com.clouway.friendlyserve.RequestExt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FakeRequest implements RequestExt {

  public static Builder aNewRequest() {
    return new Builder();
  }


  public static class Builder {
    private String method = "GET";
    private String path = "/";
    private String url = "http://localhost:8080";
    private String queryString = null;
    private String contentType = "text/html";
    private String remoteAddress = "10.10.10.10";
    private Map<String, String> params = new LinkedHashMap<>();
    private Map<String, String> headers = new LinkedHashMap<>();
    private List<Cookie> cookies = new LinkedList<>();
    private byte[] body = new byte[]{};

    public Builder get(String path) {
      this.method = "GET";
      this.path = path;
      return this;
    }

    public Builder post(String path) {
      this.method = "POST";
      this.path = path;
      return this;
    }

    public Builder put(String path) {
      this.method = "PUT";
      this.path = path;
      return this;
    }

    public Builder delete(String path) {
      this.method = "DELETE";
      this.path = path;
      return this;
    }

    public Builder query(String queryString) {
      this.queryString = queryString;
      return this;
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public Builder remoteAddress(String remoteAddress) {
      this.remoteAddress = remoteAddress;
      return this;
    }

    public Builder param(String key, String value) {
      params.put(key, value);
      return this;
    }


    public Builder params(Map<String, String> params) {
      this.params = params;
      return this;
    }

    public Builder header(String name, String value) {
      headers.put(name, value);
      return this;
    }

    public Builder headers(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public Builder cookie(Cookie cookie) {
      cookies.add(cookie);
      return this;
    }

    public Builder cookies(List<Cookie> cookies) {
      this.cookies = cookies;
      return this;
    }

    public Builder body(byte[] body) {
      this.body = body;
      return this;
    }

    public Request build() {
      return new FakeRequest(this);
    }
  }

  private final String method;
  private final String url;
  private final String path;
  private final String queryString;
  private final String contentType;
  private final String remoteAddress;
  private final Map<String, String> params;
  private final Map<String, String> headers;
  private final List<Cookie> cookies;
  private final byte[] body;


  public FakeRequest(String path, Map<String, String> params, Map<String, String> headers, List<Cookie> cookies, byte[] content) {
    this(aNewRequest().path(path).params(params).headers(headers).cookies(cookies).body(content));
  }

  public FakeRequest(String path, Map<String, String> params, Map<String, String> headers, byte[] content) {
    this(aNewRequest().path(path).params(params).headers(headers).body(content));
  }

  public FakeRequest(Map<String, String> params, Map<String, String> headers) {
    this(aNewRequest().path("/").params(params).headers(headers).body(new byte[]{}));
  }

  public FakeRequest(String path, Map<String, String> params, byte[] content) {
    this(aNewRequest().path(path).params(params).body(content));
  }

  FakeRequest(Builder builder) {
    this.method = builder.method;
    this.url = builder.url;
    this.path = builder.path;
    this.params = builder.params;
    this.headers = builder.headers;
    this.cookies = builder.cookies;
    this.body = builder.body;
    this.queryString = builder.queryString;
    this.contentType = builder.contentType;
    this.remoteAddress = builder.remoteAddress;
  }


  @Override
  public String path() {
    return path;
  }

  @Override
  public String param(String name) {
    return params.get(name);
  }

  @Override
  public Iterable<String> names() {
    return params.keySet();
  }

  @Override
  public Iterable<String> cookie(String name) {
    List<String> values = new LinkedList<>();
    for (Cookie each : cookies) {
      if (each.name().equalsIgnoreCase(name)) {
        values.add(each.value());
      }
    }
    return values;
  }

  @Override
  public String header(String name) {
    return headers.get(name);
  }

  @Override
  public InputStream body() {
    return new ByteArrayInputStream(body);
  }

  @Override
  public String method() {
    return method;
  }

  @Override
  public StringBuffer url() {
    return new StringBuffer(url);
  }

  @Override
  public String queryString() {
    return queryString;
  }

  @Override
  public String contentType() {
    return contentType;
  }

  @Override
  public Iterable<String> headerNames() {
    return headers.keySet();
  }

  @Override
  public Map<String, String[]> params() {
    Map<String, String[]> result = new LinkedHashMap<>();
    for (String each : params.keySet()) {
      result.put(each, new String[]{params.get(each)});
    }
    return result;
  }

  @Override
  public String remoteAddress() {
    return remoteAddress;
  }

}