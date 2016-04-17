package eu.ccvlab.android.common.interfaces;

import eu.ccvlab.android.common.enums.FragmentLifecycleState;

public interface FragmentLifecycleStateObserver {
    void onLifecycleStateChanged(FragmentLifecycleState lifeCycleState, Object parameter);
}
