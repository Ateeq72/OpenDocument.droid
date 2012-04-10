package at.tomtasche.reader.widget;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class FileFragment extends ListFragment {

	public static final String TAG = "files";
	
	private static final File ROOT = Environment.getExternalStorageDirectory();

	private static final boolean BREAKOUT = false;

	private static final FileFilter FILTER = new FileFilter() {
		@Override
		public boolean accept(final File f) {
			final String name = f.getName();
			return !f.getName().startsWith(".")
					&& (f.isDirectory() || name.endsWith(".odt") || name.endsWith(".ods")
							|| name.endsWith(".ott") || name.endsWith(".ots"));
		}
	};

	private static final Comparator<File> COMPARATOR = new Comparator<File>() {
		@Override
		public int compare(final File f1, final File f2) {
			final String name1 = f1.getName().toUpperCase();
			final String name2 = f2.getName().toUpperCase();
			if (f1.isDirectory()) {
				if (f2.isDirectory()) {
					return name1.compareTo(name2);
				} else {
					return -1;
				}
			} else {
				if (f2.isDirectory()) {
					return 1;
				} else {
					return name1.compareTo(name2);
				}
			}
		}
	};

	FileAdapter adapter;

	FileFragmentContainer container;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		File root;
		if (savedInstanceState == null || savedInstanceState.getString("directory") == null) {
			root = ROOT;
		} else {
			// TODO: user can't navigate lower than root?
			root = new File(savedInstanceState.getString("directory"));
		}
		
		adapter = new FileAdapter(getActivity(), root, BREAKOUT, FILTER, COMPARATOR);

		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		container = (FileFragmentContainer) activity;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
//		outState.putString("directory", adapter.getCurrentPath());
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onStart() {
		super.onStart();

		ListView list = getListView();

		if (!FileAdapter.checkDir(ROOT)) {
			//		    Toast.makeText(getActivity(), getString(R.string.toast_error_find_file), Toast.LENGTH_LONG)
			//		    .show();
		} else {
			setListAdapter(adapter);

			list.setTextFilterEnabled(true);
			registerForContextMenu(list);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (id == 0) {
			adapter.changeUp();

			return;
		}

		if (adapter.isDirectory(id)) {
			if (!adapter.changeDir(id)) {
				//				Toast.makeText(getActivity(), getString(R.string.toast_error_access_denied),
				//						Toast.LENGTH_SHORT).show();
			} else if (adapter.getCount() == 0) {
				//				Toast.makeText(getActivity(), getString(R.string.toast_error_no_files),
				//						Toast.LENGTH_SHORT).show();
			}
		} else {
			container.openDocument(adapter.getUri(id));
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add("option for FileFragment");

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	public interface FileFragmentContainer {
		public void openDocument(Uri uri);
	}
}
