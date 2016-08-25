package com.example.leonardo.pruebagrability;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ProgramasActivity extends SingleFragmentActivity {
	private static final String EXTRA_CATEGORIA =
					"com.example.leonardo.pruebagrability.categoria";

	public static Intent newIntent(Context packageContext, String categoria) {
		Intent intent = new Intent(packageContext, ProgramasActivity.class);
		intent.putExtra(EXTRA_CATEGORIA, categoria);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_programas);
	}

	@Override
	protected Fragment createFragment() {
		String categoria = (String)getIntent().getSerializableExtra(EXTRA_CATEGORIA);
		return ProgramasFragment.newInstance(categoria);
	}
}
