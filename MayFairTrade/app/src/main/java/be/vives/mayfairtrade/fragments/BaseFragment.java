package be.vives.mayfairtrade.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Iben on 17/04/2016.
 */
public abstract class BaseFragment extends Fragment {
	protected View rootView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		this.rootView = inflater.inflate(this.getLayoutResourceId(), container, false);

		ButterKnife.bind(this, this.rootView);
		this.setupViews();

		return this.rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	public abstract int getLayoutResourceId();

	/**
	 * Override to implement extra functionality in method onCreateView
	 */
	protected abstract void setupViews();
}
