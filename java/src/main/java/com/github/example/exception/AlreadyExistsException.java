package com.github.example.exception;

public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = 4657890884820953371L;

	public AlreadyExistsException(String message) {
		super(message);
	}

}
