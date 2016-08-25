package com.example.leonardo.pruebagrability;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ProgramaActivity extends SingleFragmentActivity {
	private static final String EXTRA_INFO_PROGRAMA =
					"com.example.leonardo.pruebagrability.infoPrograma";

	public static Intent newIntent(Context packageContext, InfoPrograma infoPrograma) {
		Intent intent = new Intent(packageContext, ProgramaActivity.class);
		intent.putExtra(EXTRA_INFO_PROGRAMA, infoPrograma);
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
		InfoPrograma infoPrograma = (InfoPrograma)getIntent().getSerializableExtra(EXTRA_INFO_PROGRAMA);
		return ProgramaFragment.newInstance(infoPrograma);
	}
}
