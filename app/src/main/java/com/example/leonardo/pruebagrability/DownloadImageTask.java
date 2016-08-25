package com.example.leonardo.pruebagrability;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Leonardo on 25/08/2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView mBmImage;
	Context mContext;
	String mRutaImagen;

	public DownloadImageTask(ImageView bmImage, Context context, String rutaImagen) {
		this.mBmImage = bmImage;
		mContext = context;
		mRutaImagen = rutaImagen;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			if(EstadoPrograma.get().isEnLinea()){
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
				saveImage(mContext,mIcon11,mRutaImagen);
			} else {
				mIcon11 = getImageBitmap(mContext,mRutaImagen);
			}

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		mBmImage.setImageBitmap(result);
	}

	public void saveImage(Context context, Bitmap b,String name){
		//name=name+"."+extension;
		FileOutputStream out;
		try {
			out = context.openFileOutput(name, Context.MODE_PRIVATE);
			b.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bitmap getImageBitmap(Context context,String name){
		//name=name+"."+extension;
		try{
			FileInputStream fis = context.openFileInput(name);
			Bitmap b = BitmapFactory.decodeStream(fis);
			fis.close();
			return b;
		}
		catch(Exception e){
		}
		return null;
	}
}
