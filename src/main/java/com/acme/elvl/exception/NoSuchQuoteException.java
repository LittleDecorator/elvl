package com.acme.elvl.exception;

import java.util.NoSuchElementException;

public class NoSuchQuoteException extends NoSuchElementException {

  public NoSuchQuoteException(String message) {
    super(message);
  }

}
