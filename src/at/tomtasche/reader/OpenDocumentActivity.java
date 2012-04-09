package at.tomtasche.reader;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import at.tomtasche.reader.widget.DocumentFragment;
import at.tomtasche.reader.widget.FileFragment;
import at.tomtasche.reader.widget.FileFragment.FileFragmentContainer;

public class OpenDocumentActivity extends FragmentActivity implements FileFragmentContainer {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("option for OpenDocumentActivity");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	private DocumentFragment getDocumentFragment() {
		Object fragment = getSupportFragmentManager().findFragmentById(R.id.document);
		return fragment instanceof DocumentFragment ? ((DocumentFragment) fragment) : null;
	}

	private FileFragment getFileFragment() {
		return (FileFragment) getSupportFragmentManager().findFragmentById(R.id.files);
	}

	@Override
	public void openDocument(Uri uri) {
		if (getDocumentFragment() == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.hide(getFileFragment());
			transaction.add(R.id.document, new DocumentFragment());
			transaction.addToBackStack(null);
			transaction.commit();
		} else {
			getDocumentFragment().showDocument(uri);
		}
	}
}