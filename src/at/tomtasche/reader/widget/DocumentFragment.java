package at.tomtasche.reader.widget;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DocumentFragment extends Fragment {

	public static final String TAG = "document";
	public static final String EXTRA_URI = "uri";

	private Uri uri;

	public DocumentFragment() {
		super();
	}
	
	public DocumentFragment(Uri uri) {
		this();
		
		setUriArgument(uri);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_URI)) {
			getUriArgument(savedInstanceState);
		} else if (getArguments() != null && getArguments().containsKey(EXTRA_URI)) {
			getUriArgument(getArguments());
		}
		
		TextView view = new TextView(getActivity());
		if (uri != null) {
			view.setText(uri.toString());
		} else {
			view.setText("document...");
		}

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(EXTRA_URI, uri);

		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add("option for DocumentFragment");

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	private void getUriArgument(Bundle bundle) {
		this.uri = bundle.getParcelable(EXTRA_URI);
	}

	private void setUriArgument(Uri uri) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(EXTRA_URI, uri);

		setArguments(bundle);
	}

	public void showDocument(Uri uri) {
		if (getView() == null || !(getView() instanceof TextView)) {
			// onCreateView not called yet, to be called soon, so we pass it the URI as an argument
			
			return;
		} else {
			// onCreateView already called
			this.uri = uri;
			
			TextView view = (TextView) getView();
			view.setText(uri.toString());
		}
	}
}
