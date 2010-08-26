package com.beelun.shoppro.pay;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Borrowed from: http://integrationwizard.x.com/ecpaypal/cart.php
 * 
 * @author bali
 * 
 */
public class PaypalAdaptor {
	private transient final Log log = LogFactory.getLog(PaypalAdaptor.class);

	private String gv_APIUserName;
	private String gv_APIPassword;
	private String gv_APISignature;

	private String gv_APIEndpoint;
	private String gv_BNCode;

	private String gv_Version;
//	private String gv_nvpHeader;
//	private String gv_ProxyServer;
//	private String gv_ProxyServerPort;
//	private int gv_Proxy;
//	private boolean gv_UseProxy;
	private String PAYPAL_URL;

	public PaypalAdaptor(String apiUserName, String apiPassword, String apiSignature, boolean useSandbox) {// lhuynh - Actions to be Done on init of this
								// class

		// BN Code is only applicable for partners
		gv_BNCode = "PP-ECWizard";
		// Replace <API_USERNAME> with your API Username
		// Replace <API_PASSWORD> with your API Password
		// Replace <API_SIGNATURE> with your Signature
		gv_APIUserName = apiUserName;
		gv_APIPassword = apiPassword;
		gv_APISignature = apiSignature;

		/*
		 * Servers for NVP API Sandbox: https://api-3t.sandbox.paypal.com/nvp
		 * Live: https://api-3t.paypal.com/nvp
		 */

		/*
		 * Redirect URLs for PayPal Login Screen Sandbox:
		 * https://www.sandbox.paypal
		 * .com/webscr&cmd=_express-checkout&token=XXXX Live:
		 * https://www.paypal.
		 * com/cgi-bin/webscr?cmd=_express-checkout&token=XXXX
		 */

		if (useSandbox == true) {
			gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
			//PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
			PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
		} else {
			gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
			PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		}

//		String HTTPREQUEST_PROXYSETTING_SERVER = "";
//		String HTTPREQUEST_PROXYSETTING_PORT = "";
//		boolean USE_PROXY = false;

		gv_Version = "2.3";

		// WinObjHttp Request proxy settings.
//		gv_ProxyServer = HTTPREQUEST_PROXYSETTING_SERVER;
//		gv_ProxyServerPort = HTTPREQUEST_PROXYSETTING_PORT;
//		gv_Proxy = 2; // 'setting for proxy activation
//		gv_UseProxy = USE_PROXY;

	}

	/*********************************************************************************
	 * CallShortcutExpressCheckout: Function to perform the SetExpressCheckout
	 * API call
	 * 
	 * Inputs: 
	 *   paymentAmount: Total value of the shopping cart 
	 *   currencyCodeType: Currency code value the PayPal API 
	 *   paymentType: paymentType has to be one of the following values: Sale or Order or Authorization 
	 *   returnURL: the page where buyers return to after they are done with the payment review on PayPal 
	 *   cancelURL: the page where buyers return to when they cancel the payment review on PayPal
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 * @throws UnsupportedEncodingException 
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	public HashMap callSetExpressCheckout(String paymentAmount,
			String returnURL, String cancelURL) throws UnsupportedEncodingException {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */

		String currencyCodeType = "USD";
		String paymentType = "Order";

		/*
		 * Construct the parameter string that describes the PayPal payment the
		 * varialbes were set in the web form, and the resulting string is
		 * stored in $nvpstr
		 */
		String nvpstr = "&Amt=" + paymentAmount + "&PAYMENTACTION="
				+ paymentType + "&ReturnUrl=" + URLEncoder.encode(returnURL, "US-ASCII")
				+ "&CANCELURL=" + URLEncoder.encode(cancelURL, "US-ASCII")
				+ "&CURRENCYCODE=" + currencyCodeType;

		/*
		 * Make the call to PayPal to get the Express Checkout token If the API
		 * call succeded, then redirect the buyer to PayPal to begin to
		 * authorize payment. If an error occured, show the resulting errors
		 */

		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);
		return nvp;

		// Bali: below swallow the reason for failures. We should pass it out.
//		String strAck = nvp.get("ACK").toString();
//		if (strAck != null && strAck.equalsIgnoreCase("Success")) {
//			return nvp;
//		}
//
//		return null;
	}

	/*********************************************************************************
	 * CallMarkExpressCheckout: Function to perform the SetExpressCheckout API
	 * call
	 * 
	 * Inputs: paymentAmount: Total value of the shopping cart currencyCodeType:
	 * Currency code value the PayPal API paymentType: paymentType has to be one
	 * of the following values: Sale or Order or Authorization returnURL: the
	 * page where buyers return to after they are done with the payment review
	 * on PayPal cancelURL: the page where buyers return to when they cancel the
	 * payment review on PayPal shipToName: the Ship to name entered on the
	 * merchant's site shipToStreet: the Ship to Street entered on the
	 * merchant's site shipToCity: the Ship to City entered on the merchant's
	 * site shipToState: the Ship to State entered on the merchant's site
	 * shipToCountryCode: the Code for Ship to Country entered on the merchant's
	 * site shipToZip: the Ship to ZipCode entered on the merchant's site
	 * shipToStreet2: the Ship to Street2 entered on the merchant's site
	 * phoneNum: the phoneNum entered on the merchant's site
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 * @throws UnsupportedEncodingException 
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	public HashMap CallMarkExpressCheckout(String paymentAmount,
			String returnURL, String cancelURL, String shipToName,
			String shipToStreet, String shipToCity, String shipToState,
			String shipToCountryCode, String shipToZip, String shipToStreet2,
			String phoneNum) throws UnsupportedEncodingException {
		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */
		String currencyCodeType = "USD";
		String paymentType = "Order";

		/*
		 * Construct the parameter string that describes the PayPal payment the
		 * varialbes were set in the web form, and the resulting string is
		 * stored in $nvpstr
		 */
		String nvpstr = "ADDROVERRIDE=1&Amt=" + paymentAmount
				+ "&PAYMENTACTION=" + paymentType;
		nvpstr = nvpstr.concat("&CURRENCYCODE=" + currencyCodeType
				+ "&ReturnUrl=" + URLEncoder.encode(returnURL, "US-ASCII") + "&CANCELURL="
				+ URLEncoder.encode(cancelURL, "US-ASCII"));

		nvpstr = nvpstr.concat("&SHIPTONAME=" + shipToName + "&SHIPTOSTREET="
				+ shipToStreet + "&SHIPTOSTREET2=" + shipToStreet2);
		nvpstr = nvpstr.concat("&SHIPTOCITY=" + shipToCity + "&SHIPTOSTATE="
				+ shipToState + "&SHIPTOCOUNTRYCODE=" + shipToCountryCode);
		nvpstr = nvpstr.concat("&SHIPTOZIP=" + shipToZip + "&PHONENUM"
				+ phoneNum);

		/*
		 * Make the call to PayPal to set the Express Checkout token If the API
		 * call succeded, then redirect the buyer to PayPal to begin to
		 * authorize payment. If an error occured, show the resulting errors
		 */

		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);
		String strAck = nvp.get("ACK").toString();
		
		// TODO: bali: is this the right logic to check return status? refer to other methods
		if (strAck != null
				&& !(strAck.equalsIgnoreCase("Success") || strAck
						.equalsIgnoreCase("SuccessWithWarning"))) {
			return nvp;
		}

		return null;
	}

	/*********************************************************************************
	 * GetShippingDetails: Function to perform the GetExpressCheckoutDetails API
	 * call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	public HashMap callGetExpressCheckoutDetails(String token) {
		/*
		 * Build a second API request to PayPal, using the token as the ID to
		 * get the details on the payment authorization
		 */

		String nvpstr = "&TOKEN=" + token;

		/*
		 * Make the API call and store the results in an array. If the call was
		 * a success, show the authorization details, and provide an action to
		 * complete the payment. If failed, show the error
		 */

		HashMap nvp = httpcall("GetExpressCheckoutDetails", nvpstr);
		return nvp;
//		String strAck = nvp.get("ACK").toString();
//		if (strAck != null && strAck.equalsIgnoreCase("Success")) {
//			return nvp;
//		}
//		return null;
	}

	/*********************************************************************************
	 * GetShippingDetails: Function to perform the DoExpressCheckoutPayment API
	 * call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	public HashMap ConfirmPayment(String token, String payerID,
			String finalPaymentAmount, String serverName) {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */
		String currencyCodeType = "USD";
		String paymentType = "Order";

		/*
		 * '----------------------------------------------------------------------------
		 * '---- Use the values stored in the session from the previous SetEC
		 * call
		 * '----------------------------------------------------------------------------
		 */
		String nvpstr = "&TOKEN=" + token + "&PAYERID=" + payerID
				+ "&PAYMENTACTION=" + paymentType + "&AMT="
				+ finalPaymentAmount;

		nvpstr = nvpstr + "&CURRENCYCODE=" + currencyCodeType + "&IPADDRESS="
				+ serverName;

		/*
		 * Make the call to PayPal to finalize payment If an error occured, show
		 * the resulting errors
		 */
		HashMap nvp = httpcall("DoExpressCheckoutPayment", nvpstr);
		return nvp;
//		String strAck = nvp.get("ACK").toString();
//		if (strAck != null && strAck.equalsIgnoreCase("Success")) {
//			return nvp;
//		}
//		return null;

	}

	/*********************************************************************************
	 * httpcall: Function to perform the API call to PayPal using API signature @
	 * methodName is name of API method. @ nvpStr is nvp string. returns a NVP
	 * string containing the response from the server.
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	public HashMap httpcall(String methodName, String nvpStr) {

//		String version = "2.3";
		String agent = "Mozilla/4.0";
		String respText = "";
		HashMap nvp = null; // lhuynh not used?

		// deformatNVP( nvpStr );
		String encodedData = "METHOD=" + methodName + "&VERSION=" + gv_Version
				+ "&PWD=" + gv_APIPassword + "&USER=" + gv_APIUserName
				+ "&SIGNATURE=" + gv_APISignature + nvpStr + "&BUTTONSOURCE="
				+ gv_BNCode;

		try {
			URL postURL = new URL(gv_APIEndpoint);
			HttpURLConnection conn = (HttpURLConnection) postURL
					.openConnection();

			// Set connection parameters. We need to perform input and output,
			// so set both as true.
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// Set the content type we are POSTing. We impersonate it as
			// encoded form data
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent", agent);

			// conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty("Content-Length", String
					.valueOf(encodedData.length()));
			conn.setRequestMethod("POST");

			// get the output stream to POST to.
			DataOutputStream output = new DataOutputStream(conn
					.getOutputStream());
			output.writeBytes(encodedData);
			output.flush();
			output.close();

			// Read input from the input stream.
//			DataInputStream in = new DataInputStream(conn.getInputStream());
			int rc = conn.getResponseCode();
			if (rc != -1) {
				BufferedReader is = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String _line = null;
				while (((_line = is.readLine()) != null)) {
					respText = respText + _line;
				}
				nvp = deformatNVP(respText);
			}
			return nvp;
		} catch (IOException e) {
			// handle the error here
			log.error("exp: " + e.getMessage());
			return null;
		}
	}

	/*********************************************************************************
	 * deformatNVP: Function to break the NVP string into a HashMap pPayLoad is
	 * the NVP string. returns a HashMap object containing all the name value
	 * pairs of the string.
	 * @throws UnsupportedEncodingException 
	 *********************************************************************************/
	@SuppressWarnings("unchecked")
	public HashMap deformatNVP(String pPayload) throws UnsupportedEncodingException {
		HashMap nvp = new HashMap();
		StringTokenizer stTok = new StringTokenizer(pPayload, "&");
		while (stTok.hasMoreTokens()) {
			StringTokenizer stInternalTokenizer = new StringTokenizer(stTok
					.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2) {
				String key = URLDecoder.decode(stInternalTokenizer.nextToken(), "US-ASCII");
				String value = URLDecoder.decode(stInternalTokenizer
						.nextToken(), "US-ASCII");
				nvp.put(key.toUpperCase(), value);
			}
		}
		return nvp;
	}

	/*********************************************************************************
	 * RedirectURL: Function to redirect the user to the PayPal site token is
	 * the parameter that was returned by PayPal returns a HashMap object
	 * containing all the name value pairs of the string.
	 *********************************************************************************/
	public void RedirectURL(HttpServletResponse response, String token) {
		String payPalURL = PAYPAL_URL + token;

		// response.sendRedirect( payPalURL );
		response.setStatus(302);
		response.setHeader("Location", payPalURL);
		response.setHeader("Connection", "close");
	}

	// end class
}