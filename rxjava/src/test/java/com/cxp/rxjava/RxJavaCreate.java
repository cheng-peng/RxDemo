package com.cxp.rxjava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 文 件 名: RxJavaCreate
 * 创 建 人: CXP
 * 创建日期: 2017-05-26 11:58
 * 描    述: RxJava 创建方式
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class RxJavaCreate {
    @Test
    public void create() {
        //创建方式  1:create
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("create1");
                subscriber.onNext("create2");
                subscriber.onNext("create3");
                subscriber.onCompleted();
            }
        });
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("=========================创建完毕=========================");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        //订阅
        observable.subscribe(subscriber);
        //取消订阅
        subscriber.unsubscribe();
    }

    @Test
    public void just() {
        //创建方式 just方式  最多支持10个数据
        Observable<String> observable = Observable.just("just1", "just2", "just3");
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("=========================创建完毕=========================");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        //订阅
        observable.subscribe(subscriber);
        //取消订阅
        subscriber.unsubscribe();
    }

    @Test
    public void from1() {
        //创建方式  from方式  1:集合
        List<String> list = new ArrayList<>();
        list.add("from1A");
        list.add("from1B");
        list.add("from1C");
        Observable<String> observable = Observable.from(list);
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("=========================创建完毕=========================");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        //订阅
        observable.subscribe(subscriber);
        //取消订阅
        subscriber.unsubscribe();
    }

    @Test
    public void from2() {
        //创建方式  from方式  2:集合
        String[] words = { "from2A", "from2B", "from2C" };
        Observable<String> observable = Observable.from( words ) ;
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("=========================创建完毕=========================");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        //订阅
        observable.subscribe(subscriber);
        //取消订阅
        subscriber.unsubscribe();
    }
}
