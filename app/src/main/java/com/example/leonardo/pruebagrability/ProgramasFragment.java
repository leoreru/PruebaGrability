package com.example.leonardo.pruebagrability;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 24/08/2016.
 */
public class ProgramasFragment extends Fragment {

	private static final String ARG_CATEGORIA = "categoria";

	private RecyclerView mRcvProgramas;
	private TextView mTxtvOnline;
	private adpPrograma mAdapter;
	private String mCategoria;

	// Si categoria es null se muestran todos los programas.
	public static ProgramasFragment newInstance(String categoria) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_CATEGORIA, categoria);
		ProgramasFragment fragment = new ProgramasFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCategoria = (String)getArguments().getSerializable(ARG_CATEGORIA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_programas, container, false);
		mRcvProgramas = (RecyclerView) v.findViewById(R.id.rcv_programas);
		mTxtvOnline = (TextView) v.findViewById(R.id.txtvOnline);
		if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			mRcvProgramas.setLayoutManager(new GridLayoutManager(getActivity(),2));
		else
			mRcvProgramas.setLayoutManager(new GridLayoutManager(getActivity(),1));
		actualizarUI();
		return v;
	}

	private void actualizarUI() {
		int i;
		String categoria;

		if(EstadoPrograma.get().isEnLinea())
			mTxtvOnline.setText("");
		else
			mTxtvOnline.setText("Offline");
		InfoProgramas infoProg = InfoProgramas.get();
		List<InfoPrograma> infoProgs = infoProg.getProgramas();
		List<InfoPrograma> infoProgsFilt = new ArrayList<>();

		for(i=0;i<infoProgs.size();++i){
			categoria = infoProgs.get(i).getCategoria();
			if(mCategoria == null || mCategoria.equals(categoria))
				infoProgsFilt.add(infoProgs.get(i));
		}
		mAdapter = new adpPrograma(infoProgsFilt);
		mRcvProgramas.setAdapter(mAdapter);
	}

	private class vwhPrograma extends RecyclerView.ViewHolder
					implements View.OnClickListener{
		public TextView mTxtvTitulo;
		public InfoPrograma mInfoPrograma;

		public vwhPrograma(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			mTxtvTitulo = (TextView) itemView;
		}

		@Override
		public void onClick(View v) {
			Intent in = ProgramaActivity.newIntent(
							getActivity(),mInfoPrograma);
			startActivity(in);
		}
	}

	private class adpPrograma extends RecyclerView.Adapter<vwhPrograma> {
		private List<InfoPrograma> mInfoProgramas;

		public adpPrograma(List<InfoPrograma> infoProgramas) {
			mInfoProgramas = infoProgramas;
		}

		@Override
		public vwhPrograma onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater
							.inflate(android.R.layout.simple_list_item_1, parent, false);
			return new vwhPrograma(view);
		}

		@Override
		public void onBindViewHolder(vwhPrograma holder, int position) {
			holder.mInfoPrograma = mInfoProgramas.get(position);
			String programa = holder.mInfoPrograma.getNombre();
			holder.mTxtvTitulo.setText(programa);
		}

		@Override
		public int getItemCount() {
			return mInfoProgramas.size();
		}
	}
}
