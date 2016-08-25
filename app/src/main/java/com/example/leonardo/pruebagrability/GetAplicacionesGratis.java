package com.example.leonardo.pruebagrability;

import android.os.AsyncTask;

/**
 * Created by Leonardo on 22/08/2016.
 */

public class GetAplicacionesGratis extends AsyncTask<String, Void, Void>
{
	private String strJson;
//
//	GetAplicacionesGratis(String Url)
//	{
//		url = Url;
//	}


	public String getStrJson() {
		return strJson;
	}

	@Override
	protected Void doInBackground(String... argo)
	{
		strJson = WebRequest.makeWebServiceCall(argo[0], WebRequest.GETRequest);
		return null;
	}
}
