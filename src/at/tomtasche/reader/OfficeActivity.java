package at.tomtasche.reader;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import at.tomtasche.reader.widget.DocumentFragment;
import at.tomtasche.reader.widget.FileFragment.FileFragmentContainer;

public class OfficeActivity extends FragmentActivity implements FileFragmentContainer {

	private Uri uri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		if (savedInstanceState != null && findViewById(R.id.document) != null && savedInstanceState.containsKey(DocumentFragment.EXTRA_URI)) {
			uri = savedInstanceState.getParcelable(DocumentFragment.EXTRA_URI);

			openDocument(uri);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("option for OpenDocumentActivity");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(DocumentFragment.EXTRA_URI, uri);

		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	private DocumentFragment getDocumentFragment() {
		return (DocumentFragment) getSupportFragmentManager().findFragmentByTag(DocumentFragment.TAG);
	}

	@Override
	public void openDocument(Uri uri) {
		this.uri = uri;

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			DocumentFragment fragment = getDocumentFragment();
			if (fragment == null) {
				fragment = new DocumentFragment(uri);

				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.replace(R.id.document, fragment, DocumentFragment.TAG);
				transaction.addToBackStack(null);
				transaction.commit();
			}

			fragment.showDocument(uri);
		} else {
			Intent intent = new Intent(this, DocumentActivity.class);
			intent.putExtra(DocumentFragment.EXTRA_URI, uri);

			startActivity(intent);
		}
	}
}