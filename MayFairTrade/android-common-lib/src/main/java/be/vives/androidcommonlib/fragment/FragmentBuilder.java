package be.vives.androidcommonlib.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Iben on 23/04/2016.
 */
public class FragmentBuilder {
	private AppCompatActivity activity;
	private Class<? extends Fragment> fragmentClass;
	private Fragment fragment;
	private String customTag;
	private boolean addToBackStack = false;
	private Bundle bundle;

	@IdRes
	private int layoutContainer;

	public static FragmentBuilder with(AppCompatActivity activity) {
		FragmentBuilder builder = new FragmentBuilder();
		builder.activity = activity;
		return builder;
	}

	public FragmentBuilder setFragment(final Fragment fragment) {
		this.fragment = fragment;
		return this;
	}

	public FragmentBuilder setFragmentClass(final Class<? extends Fragment> fragmentClass) {
		this.fragmentClass = fragmentClass;
		return this;
	}

	public FragmentBuilder setLayoutContainer(final @IdRes int layoutContainer) {
		this.layoutContainer = layoutContainer;
		return this;
	}

	public FragmentBuilder setAddToBackStack(final boolean addToBackStack) {
		this.addToBackStack = addToBackStack;
		return this;
	}

	public FragmentBuilder setCustomTag(final String customTag) {
		this.customTag = customTag;
		return this;
	}

	public FragmentBuilder setBundle(final Bundle bundle) {
		this.bundle = bundle;
		return this;
	}

	public void commit() {
		this.setupFragment();
		final String tag = this.createTag();

		if (this.fragment == null) {
			return;
		}
		final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		ft.replace(this.layoutContainer, fragment, tag);

		if (this.addToBackStack) {
			ft.addToBackStack(tag);
		}
		ft.commit();

		this.activity = null;
		this.fragmentClass = null;
		this.fragment = null;
		this.customTag = null;
		this.addToBackStack = false;
		this.bundle = null;
	}

	// Private
	private String createTag() {
		if (this.customTag == null) {
			return this.fragment.getClass().getSimpleName();
		} else {
			return this.customTag;
		}
	}

	private Fragment setupFragment() {
		if (this.fragment == null && this.fragmentClass != null) {
			this.fragment = this.createFragmentFromClass();
		}
		// Set the Bundle on the Fragment instance
		if (this.fragment != null && this.bundle != null) {
			this.fragment.setArguments(bundle);
		}

		return this.fragment;
	}

	private Fragment createFragmentFromClass() {
		try {
			Constructor<? extends Fragment> ctor = this.fragmentClass.getConstructor();
			return ctor.newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
