package com.jying.eth_test.Base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    private final Subject<Object> mBus;
    private static volatile RxBus rxBus;
    private Map<Class<?>, Object> mStickyEventMap;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void post(Object event) {
        mBus.onNext(event);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    public <T> Observable<T> tObservable(LifecycleOwner owner, Class<T> eventType) {
        LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(owner);
        return mBus.ofType(eventType).compose(provider.bindToLifecycle());
    }

    public <T> Observable<T> tObservableSticky(LifecycleOwner owner, final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(owner);
            Observable<T> observable = mBus.ofType(eventType).compose(provider.bindToLifecycle());
            final Object event = mStickyEventMap.get(eventType);
            if (event != null) {
                return observable.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                        emitter.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }

    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }


    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    public void removeAllStickyEvent() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }

    public boolean hasObservable() {
        return mBus.hasObservers();
    }

    public void reset() {
        rxBus = null;
    }
}
