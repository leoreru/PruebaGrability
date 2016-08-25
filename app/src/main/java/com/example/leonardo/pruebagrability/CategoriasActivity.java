package com.example.leonardo.pruebagrability;

import android.content.Context;
import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class CategoriasActivity extends SingleFragmentActivity {
	// Nombres de los nodos del json.
	private static final String TAG_OBJ_FEED = "feed";
	private static final String TAG_ARR_PROG = "entry";
	private static final String TAG_OBJ_PROG_CATEGORIA = "category";
	private static final String TAG_OBJ_PROG_NOMBRE = "im:name";
	private static final String TAG_OBJ_PROG_DESCRIPCION = "summary";
	private static final String TAG_OBJ_PROG_EMPRESA = "im:artist";
	private static final String TAG_ARR_PROG_IMAGEN = "im:image";
	private static final String TAG_OBJ_PROG_ID = "id";
	private static final String TAG_OBJ_ATRIBUTOS = "attributes";
	private static final String TAG_STR_ATRIBUTOS_NAME = "term";
	private static final String TAG_STR_ATRIBUTOS_ID = "im:id";
	private static final String TAG_STR_ETIQUETA = "label";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		obtenerDatos();
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
	}

	private void obtenerDatos(){
		int i,j;
		JSONObject jsonObj;
		InfoProgramas infoProg;
		List<String> categorias;
		TreeSet<String> tsetCategorias = new TreeSet<>();
		String categoria;
		List<InfoPrograma> programas;
		InfoPrograma programa;
		String strJson;

		// Obtener información de la resolución de la pantalla.
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		if(metrics.widthPixels<800)
			EstadoPrograma.get().setIndResolucion((byte)0);
		else if(metrics.widthPixels<1920)
			EstadoPrograma.get().setIndResolucion((byte)1);
		else
			EstadoPrograma.get().setIndResolucion((byte)2);

		GetAplicacionesGratis getAplGratis = new GetAplicacionesGratis();
		getAplGratis.execute("https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json");
		try
		{
			getAplGratis.get(); // Para esperar a que termine.
		}
		catch(Exception e){

		}
//		while(getAplGratis.getStatus() != AsyncTask.Status.FINISHED)
//		{ // Esperar a que acabe.
//		}
//		for(long i=0;i<10000000;++i){}
		if(EstadoPrograma.get().isEnLinea()){
			// Se guarda el json en un archivo:
			strJson = getAplGratis.getStrJson();
			writeToFile(strJson,this);
		} else {
			// Se trata de abrir una versión guardada anteriormente
			strJson = readFromFile(this);
			if(strJson.isEmpty())
				Toast.makeText(this,
								"No hay conexión y además no hay datos guardados",
								Toast.LENGTH_LONG)
								.show();
		}

		infoProg = InfoProgramas.get();
		categorias = infoProg.getCategorias();
		programas = infoProg.getProgramas();
		programas.clear();
		try {
			jsonObj = new JSONObject(strJson);
			JSONObject obj = jsonObj.getJSONObject(TAG_OBJ_FEED);
			JSONArray arrProg =  obj.getJSONArray(TAG_ARR_PROG);
			for (i = 0; i < arrProg.length(); ++i) {
				JSONObject prog = arrProg.getJSONObject(i);
				JSONObject cat = prog.getJSONObject(TAG_OBJ_PROG_CATEGORIA);
				JSONObject atrCat = cat.getJSONObject(TAG_OBJ_ATRIBUTOS);
				categoria = atrCat.getString(TAG_STR_ATRIBUTOS_NAME);
				// El TreeSet no permite repetidos.
				tsetCategorias.add(categoria);

				programa = new InfoPrograma();
				programa.setCategoria(categoria);
				JSONObject nomP = prog.getJSONObject(TAG_OBJ_PROG_NOMBRE);
				programa.setNombre(nomP.getString(TAG_STR_ETIQUETA));
				JSONObject desc = prog.getJSONObject(TAG_OBJ_PROG_DESCRIPCION);
				programa.setDescripcion(desc.getString(TAG_STR_ETIQUETA));
				JSONObject emp = prog.getJSONObject(TAG_OBJ_PROG_EMPRESA);
				programa.setEmpresa(emp.getString(TAG_STR_ETIQUETA));
				JSONArray arrImag = prog.getJSONArray(TAG_ARR_PROG_IMAGEN);
				for(j=0; j<3; ++j) {
					JSONObject img = arrImag.getJSONObject(j);
					programa.getUrlsImagenes()[j] = img.getString(TAG_STR_ETIQUETA);
					JSONObject id = prog.getJSONObject(TAG_OBJ_PROG_ID);
					JSONObject atrId = id.getJSONObject(TAG_OBJ_ATRIBUTOS);
					programa.getRutasImagenes()[j] = atrId.getString(TAG_STR_ATRIBUTOS_ID) + "_0" + j + ".png";
				}
				programas.add(programa);
			}
			categorias.clear();
			categorias.add("All");
			Iterator itr=tsetCategorias.iterator();
			while (itr.hasNext()) {
				categorias.add((String)itr.next());
			}
		} catch(org.json.JSONException e){
			//Toast.
		}
	}

	private void writeToFile(String data, Context context) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("json.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		}
		catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	private String readFromFile(Context context) {

		String ret = "";

		try {
			InputStream inputStream = context.openFileInput("json.txt");

			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		}	catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return ret;
	}

	@Override
	protected Fragment createFragment() {
		return new CategoriasFragment();
	}
}
