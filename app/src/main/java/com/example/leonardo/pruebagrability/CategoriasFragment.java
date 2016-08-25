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

import java.util.List;

/**
 * Created by Leonardo on 23/08/2016.
 */
public class CategoriasFragment extends Fragment {
	private RecyclerView mRcvCategorias;
	private TextView mTxtvOnline;
	private adpCategoria mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_categorias, container, false);
		mRcvCategorias = (RecyclerView) v.findViewById(R.id.rcv_categorias);
		mTxtvOnline = (TextView) v.findViewById(R.id.txtvOnline);
		if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			mRcvCategorias.setLayoutManager(new GridLayoutManager(getActivity(),2));
		else
			mRcvCategorias.setLayoutManager(new GridLayoutManager(getActivity(),1));
		actualizarUI();
		return v;
	}

	private void actualizarUI() {
		if(EstadoPrograma.get().isEnLinea())
			mTxtvOnline.setText("");
		else
			mTxtvOnline.setText("Offline");
		InfoProgramas infoProg = InfoProgramas.get();
		List<String> categorias = infoProg.getCategorias();
		mAdapter = new adpCategoria(categorias);
		mRcvCategorias.setAdapter(mAdapter);
	}
//--------------------------------------------------------------------------------------------------
	private class vwhCategoria extends RecyclerView.ViewHolder
					implements View.OnClickListener{
		public TextView mTxtvTitulo;
		//public String mCategoria;

		public vwhCategoria(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			mTxtvTitulo = (TextView) itemView;
		}

		@Override
		public void onClick(View v) {
			String categoria;
//			Toast.makeText(getActivity(),
//							mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
//							.show();
			categoria = mTxtvTitulo.getText().toString();
			Intent in = ProgramasActivity.newIntent(
							getActivity(), categoria.equalsIgnoreCase("all") ? null:categoria);
			//Intent in = new Intent(getActivity(),ProgramasActivity.class);
			startActivity(in);
		}
	}
//--------------------------------------------------------------------------------------------------
	private class adpCategoria extends RecyclerView.Adapter<vwhCategoria> {
		private List<String> mCategorias;

		public adpCategoria(List<String> categorias) {
			mCategorias = categorias;
		}

		@Override
		public vwhCategoria onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater
							.inflate(android.R.layout.simple_list_item_1, parent, false);
			return new vwhCategoria(view);
		}

		@Override
		public void onBindViewHolder(vwhCategoria holder, int position) {
			String categoria = mCategorias.get(position);
			//holder.mCategoria = categoria;
			holder.mTxtvTitulo.setText(categoria);
		}

		@Override
		public int getItemCount() {
			return mCategorias.size();
		}
	}
}
