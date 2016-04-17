package eu.ccvlab.android.common.holders;

import eu.ccvlab.android.common.enums.FragmentLifecycleState;
import eu.ccvlab.android.common.interfaces.FragmentLifecycleStateObserver;

public class FragmentLifecycleStateHolder extends CCVBaseHolder<FragmentLifecycleStateObserver> {
    private FragmentLifecycleState lifeCycleState;
    private Object lifeCycleStateParameter;

    public void setState(final FragmentLifecycleState lifeCycleState) {
        this.setState(lifeCycleState, null);
    }

    public void setState(final FragmentLifecycleState lifeCycleState, Object lifeCycleStateParameter) {
        this.lifeCycleState = lifeCycleState;
        this.lifeCycleStateParameter = lifeCycleStateParameter;
        this.notifyStateChanged();
    }

    private void notifyStateChanged() {
        for (FragmentLifecycleStateObserver observer : this.observers) {
            observer.onLifecycleStateChanged(this.lifeCycleState, this.lifeCycleStateParameter);
        }

        if (this.lifeCycleState == FragmentLifecycleState.ON_DESTROY) {
            this.observers.clear();
        }
    }
}