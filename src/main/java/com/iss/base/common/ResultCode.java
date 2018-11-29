package com.iss.base.common;

/**
 * The response code enumeration, refer to the semantics of the HTTP status code
 */
public enum ResultCode {
	SUCCESS(200), // success
	FAIL(400), // failure
	UNAUTHORIZED(401), // Uncertified (signature error)
	NOT_FOUND(404), // Interface does not exist
	INTERNAL_SERVER_ERROR(500);// Server internal error

	public int code;

	ResultCode(int code) {
		this.code = code;
	}
}
