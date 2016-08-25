package com.example.leonardo.pruebagrability;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Leonardo on 22/08/2016.
 */
public class WebRequest {
	static String response = null;
	public final static int GETRequest = 1;
	public final static int POSTRequest = 2;

	//Constructor with no parameter
	public WebRequest() {
	}
	/**
	 * Making web service call
	 *
	 * @url – url to make web request
	 * @requestmethod – http request method
	 */
	public static String makeWebServiceCall(String url, int requestmethod) {
		return makeWebServiceCall(url, requestmethod, null);
	}
	/**
	 * Making web service call
	 *
	 * @url – url to make web request
	 * @requestmethod – http request method
	 * @params – http request params
	 */
	public static String makeWebServiceCall(String urladdress, int requestmethod,
																	 HashMap<String, String> params) {
		URL url;
		String response ="";
		HttpURLConnection conn=null;
		OutputStream ostream=null;
		BufferedWriter writer=null;
		BufferedReader br=null;

		try {
			url = new URL(urladdress);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15001);
			conn.setConnectTimeout(15001);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if (requestmethod == POSTRequest) {
				conn.setRequestMethod("POST");
			} else if (requestmethod == GETRequest) {
				conn.setRequestMethod("GET");
			}

			if (params != null) {
				ostream = conn.getOutputStream();
				writer = new BufferedWriter(
								new OutputStreamWriter(ostream, "UTF-8"));
				StringBuilder requestresult = new StringBuilder();
				boolean first = true;
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (first)
						first = false;
					else
						requestresult.append("&");
					requestresult.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
					requestresult.append("=");
					requestresult.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				}
				writer.write(requestresult.toString());

				writer.flush();
			}

			//InputStream in = new BufferedInputStream(conn.getInputStream());
			int reqResponseCode = conn.getResponseCode();

			if (reqResponseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}
		} catch (Exception e) {
			EstadoPrograma.get().setEnLinea(false);// Revisar posición.
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.disconnect();
				if (writer != null)
					writer.close();
				if (ostream != null)
					ostream.close();
				if (br != null)
					br.close();
			} catch(Exception e){}
		}

		return response;
	}
}
