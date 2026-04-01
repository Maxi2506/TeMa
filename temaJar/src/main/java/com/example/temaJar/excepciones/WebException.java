package com.example.temaJar.excepciones;

public class WebException extends RuntimeException {

  private static final long serialVersionUID = 7883636384872015753L;

  public WebException(String msn) {
    super(msn);
  }

}
