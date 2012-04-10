package at.tomtasche.reader;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import at.tomtasche.reader.widget.DocumentFragment;

public class DocumentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            
            return;
        }

        if (savedInstanceState == null) {
        	Uri uri = getIntent().getParcelableExtra(DocumentFragment.EXTRA_URI);
        	
            DocumentFragment fragment = new DocumentFragment(uri);
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
	}
}
