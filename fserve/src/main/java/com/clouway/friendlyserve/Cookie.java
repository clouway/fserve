package com.clouway.friendlyserve;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class Cookie {

  public static class Builder {
    private String path = "";
    private String name = "";
    private String value = "";
    private Boolean secured = false;
    private Boolean httpOnly = false;
    private int expirationTimeInSeconds = -1;

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder value(String value) {
      this.value = value;
      return this;
    }

    public Builder setSecure(Boolean secured) {
      this.secured = secured;
      return this;
    }

    public Builder setHttpOnly(Boolean httpOnly) {
      this.httpOnly = httpOnly;
      return this;
    }

    public Builder expiresAfter(int timeInSeconds) {
      this.expirationTimeInSeconds = timeInSeconds;
      return this;
    }

    public Cookie build() {
      return new Cookie(path, name, value, secured, httpOnly, expirationTimeInSeconds);
    }
  }

  private String path;
  private String name;
  private String value;
  private Boolean secured;
  private Boolean httpOnly;
  private int expirationTimeInSeconds;

  public Cookie(String path, String name, String value, Boolean secured, Boolean httpOnly, int expirationTimeInSeconds) {
    this.path = path;
    this.name = name;
    this.value = value;
    this.secured = secured;
    this.httpOnly = httpOnly;
    this.expirationTimeInSeconds = expirationTimeInSeconds;
  }

  public String path() {
    return path;
  }

  public String name() {
    return name;
  }

  public String value() {
    return value;
  }

  public Boolean isSecured() {
    return secured;
  }

  public Boolean isHttpOnly() {
    return httpOnly;
  }

  public int expirationTimeInSeconds() {
    return expirationTimeInSeconds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cookie cookie = (Cookie) o;
    return expirationTimeInSeconds == cookie.expirationTimeInSeconds &&
            Objects.equals(path, cookie.path) &&
            Objects.equals(name, cookie.name) &&
            Objects.equals(value, cookie.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, name, value, expirationTimeInSeconds);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("path", path)
            .add("name", name)
            .add("value", value)
            .add("expirationTimeInSeconds", expirationTimeInSeconds)
            .toString();
  }
}
