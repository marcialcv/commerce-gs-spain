package com.liferay.commerce.payment.method.redsys;

import com.liferay.commerce.payment.request.CommercePaymentRequest;

import java.math.BigDecimal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @author Roselaine Marques
 *
 */
public class RedsysCommercePaymentMethodRequest extends CommercePaymentRequest  {

	private static final long serialVersionUID = 1L;

	public RedsysCommercePaymentMethodRequest(
			BigDecimal amount, String cancelUrl, long commerceOrderId, Locale locale,
			HttpServletRequest httpServletRequest,
			String returnUrl, String transactionId) {
		
		super(amount, cancelUrl, commerceOrderId, locale, returnUrl, transactionId);
		
		_httpServletRequest = httpServletRequest;

	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	private final HttpServletRequest _httpServletRequest;


}
