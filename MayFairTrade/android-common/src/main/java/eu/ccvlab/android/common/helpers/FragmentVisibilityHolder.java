package eu.ccvlab.android.common.helpers;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;

import eu.ccvlab.android.common.enums.FragmentLifecycleState;
import eu.ccvlab.android.common.holders.CCVBaseHolder;
import eu.ccvlab.android.common.holders.FragmentLifecycleStateHolder;
import eu.ccvlab.android.common.interfaces.FragmentLifecycleStateObserver;
import eu.ccvlab.android.common.interfaces.FragmentVisibilityChangedListener;

//TODO: this class only support onVisible();
// onHidden() isn't implemented yet
public class FragmentVisibilityHolder extends CCVBaseHolder<FragmentVisibilityChangedListener> implements FragmentLifecycleStateObserver {
    private boolean isVisible = false;
    private boolean callBecomeVisibleInOnResume = false;
    private boolean isChildOfViewPager = false;

    public FragmentVisibilityHolder(@NonNull FragmentLifecycleStateHolder lifecycleStateHolder, @NonNull View view) {
        lifecycleStateHolder.addObserver(this);

        final ViewParent parentView = view.getParent();
        if (parentView != null && parentView instanceof ViewPager) {
            this.isChildOfViewPager = true;
        }
    }

    @Override
    public void onLifecycleStateChanged(FragmentLifecycleState lifeCycleState, Object parameter) {
        switch (lifeCycleState) {
            case ON_RESUME:
                this.handleOnResume();
                break;
            case ON_PAUSE:
                this.handleOnPause();
                break;
            case SET_USER_VISIBLE_HINT:
                this.handleSetUserVisibleHint((boolean) parameter);
                break;
        }
    }

    public void handleOnResume() {
        if (isChildOfViewPager) {
            if (this.callBecomeVisibleInOnResume) {
                this.setVisible(true);
            }
        } else {
            this.setVisible(true);
        }
        this.callBecomeVisibleInOnResume = true;
    }

    public void handleOnPause() {
        this.callBecomeVisibleInOnResume = this.isVisible;
        this.isVisible = false;
    }

    public void handleSetUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            this.setVisible(true);
        }
        this.isVisible = isVisibleToUser;
    }

    private void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
        for (FragmentVisibilityChangedListener listener : this.observers) {
            listener.onVisible();
        }
    }
}
