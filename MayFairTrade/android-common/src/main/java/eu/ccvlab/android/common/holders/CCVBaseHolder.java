package eu.ccvlab.android.common.holders;

import java.util.ArrayList;
import java.util.List;

public abstract class CCVBaseHolder<T> {
    protected final List<T> observers = new ArrayList<>();

    public void addObserver(T observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(T observer) {
        if (observers.contains(observer)) {
            observers.add(observer);
        }
    }

    protected void notifiyDataChanged() {
        for (T observer : observers) {

        }
    }
}
