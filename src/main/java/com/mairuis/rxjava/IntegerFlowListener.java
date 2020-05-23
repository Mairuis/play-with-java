package com.mairuis.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Mairuis
 * @since 2020/5/23
 */
public class IntegerFlowListener {

    public static void main(String[] args) throws InterruptedException {
        final Observable<Integer> dataStream = Observable.range(0, Integer.MAX_VALUE)
                .concatMap(tick -> Observable
                        .just(tick)
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .map(tickV -> ThreadLocalRandom.current().nextInt(233))
                )
                .publish()
                .refCount();
        final Disposable subscribe = dataStream.subscribe(System.out::println);

        Thread.sleep(5000);
        subscribe.dispose();
    }

}
