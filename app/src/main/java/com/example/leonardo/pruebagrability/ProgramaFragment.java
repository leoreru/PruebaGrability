package com.example.leonardo.pruebagrability;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Leonardo on 24/08/2016.
 */
public class ProgramaFragment extends Fragment {

	private static final String ARG_INFO_PROGRAMA = "infoPrograma";

	private TextView mTxtvOnline;
	private InfoPrograma mInfoPrograma;

	public static ProgramaFragment newInstance(InfoPrograma infoPrograma) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_INFO_PROGRAMA, infoPrograma);
		ProgramaFragment fragment = new ProgramaFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInfoPrograma = (InfoPrograma)getArguments().getSerializable(ARG_INFO_PROGRAMA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		TextView txtvNomPrograma;
		TextView txtvDescripcion;
		TextView txtvEmpresa;

		View v = inflater.inflate(R.layout.fragment_programa, container, false);
		mTxtvOnline = (TextView) v.findViewById(R.id.txtvOnline);
		if(EstadoPrograma.get().isEnLinea())
			mTxtvOnline.setText("");
		else
			mTxtvOnline.setText("Offline");
		new DownloadImageTask((ImageView) v.findViewById(R.id.imvPrograma), getActivity(), mInfoPrograma.getRutasImagenes()[EstadoPrograma.get().getIndResolucion()])
						.execute(mInfoPrograma.getUrlsImagenes()[EstadoPrograma.get().getIndResolucion()]);

		txtvNomPrograma = (TextView)v.findViewById(R.id.txtvNomPrograma);
		txtvNomPrograma.setText(mInfoPrograma.getNombre());
		txtvDescripcion = (TextView)v.findViewById(R.id.txtvDescripcion);
		txtvDescripcion.setText(mInfoPrograma.getDescripcion());
		txtvEmpresa = (TextView)v.findViewById(R.id.txtvEmpresa);
		txtvEmpresa.setText(mInfoPrograma.getEmpresa());
		return v;
	}
}
