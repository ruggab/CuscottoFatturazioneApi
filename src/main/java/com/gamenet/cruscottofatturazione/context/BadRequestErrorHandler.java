package com.gamenet.cruscottofatturazione.context;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Component("badReqErrorHandler")
public class BadRequestErrorHandler extends DefaultResponseErrorHandler {

	public BadRequestErrorHandler() {
	}

	@Override
	public boolean hasError(final ClientHttpResponse response) throws IOException {
		switch (response.getStatusCode()) {
		case BAD_REQUEST:
		case OK:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {

		switch (response.getStatusCode()) {
		case UNAUTHORIZED:
			throw new IOException(response.getStatusText());
		case NOT_FOUND:
			throw new IOException(response.getStatusText());
		case METHOD_NOT_ALLOWED:
			throw new IOException(response.getStatusText());
		case NOT_ACCEPTABLE:
			throw new IOException(response.getStatusText());
		case UNSUPPORTED_MEDIA_TYPE:
			throw new IOException(response.getStatusText());
		default:
			throw new IOException(response.getStatusText());
		}
	}
}
