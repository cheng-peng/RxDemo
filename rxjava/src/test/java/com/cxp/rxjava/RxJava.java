package com.cxp.rxjava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * 文 件 名: RxJava
 * 创 建 人: CXP
 * 创建日期: 2017-05-26 12:04
 * 描    述: RxJava 操作符   因为测试  都没有取消订阅
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class RxJava {

    @Test
    public void map() {
        //map操作符，用来操作数据 （例如将Int 转成String ）
        Observable.just(1, 2, 3, 4, 5, 6)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer num) {
                        return "map" + num;
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("map-- " + s);
            }
        });
    }

    @Test
    public void flatMap() {
        //flatMap操作符，用来操作数据 功能类似map 只不过map返回的结果集  flatMap返回的Observable
        //concatMap操作符  和flatMap操作符 用法一致  这个是有序的  flatMap  是无序的
        Observable.just(1, 2, 3, 4, 5, 6)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer num) {
                        return Observable.just(""+num);
                    }
                }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return "哈哈"+s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("map-- " + s);
            }
        });
    }

    @Test
    public void merge() {
        //1、merge操作符，合并观察对象
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list1.add("1");
        list1.add("2");
        list1.add("3");

        list2.add("a");
        list2.add("b");
        list2.add("c");

        Observable observable1 = Observable.from(list1);
        Observable observable2 = Observable.from(list2);

        //合并数据  先发送observable2的全部数据，然后发送 observable1的全部数据
        Observable observable = Observable.merge(observable2, observable1);

        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("merge-- " + o);
            }
        });
    }

    @Test
    public void zip() {
        //2、zip  操作符，合并多个观察对象的数据。并且允许 Func2（）函数重新发送合并后的数据   (用于多网络请求)
        //合并两个的观察对象数据项应该是相等的；如果出现了数据项不等的情况，合并的数据项以最小数据队列为准
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list1.add("1");
        list1.add("2");
        list1.add("3");

        list2.add("a");
        list2.add("b");
        list2.add("c");
        list2.add("d");

        Observable observable1 = Observable.from(list1);
        Observable observable2 = Observable.from(list2);

        Observable observable3 = Observable.zip(observable1, observable2, new Func2<String, String, String>() {
            @Override
            public String call(String s1, String s2) {
                return s1 + s2;
            }
        });

        observable3.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("zip-- " + o);
            }
        });
    }

    @Test
    public void scan() {
        //3、scan累加器操作符的使用
        //第一次发射得到1，作为结果与2相加；发射得到3，作为结果与3相加，以此类推
        Observable observable = Observable.just(1, 2, 3, 4, 5);
        observable.scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer o, Integer o2) {
                return o + o2;
            }
        }).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("scan-- " + o);
            }
        });
    }

    @Test
    public void filter() {
        //4、filter 过滤操作符的使用
        Observable observable = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer o) {
                //数据大于4的时候才会被发送
                return o > 4;
            }
        })
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("filter-- " + o);
                    }
                });
    }

    @Test
    public void listFilter() {
        /**
         * 5、 消息数量过滤操作符的使用
         * take ：取前n个数据
         * takeLast：取后n个数据
         * first 只发送第一个数据
         * last 只发送最后一个数据
         * skip() 跳过前n个数据发送后面的数据
         * skipLast() 跳过最后n个数据，发送前面的数据
         */
        //take 发送前3个数据
        Observable observable = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable.take(3)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("take-- " + o);
                    }
                });

        //takeLast 发送最后三个数据
        Observable observable2 = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable2.takeLast(3)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("takeLast-- " + o);
                    }
                });

        //first 只发送第一个数据
        Observable observable3 = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable3.first()
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("first-- " + o);
                    }
                });

        //last 只发送最后一个数据
        Observable observable4 = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable4.last()
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("last-- " + o);
                    }
                });
        //skip() 跳过前2个数据发送后面的数据
        Observable observable5 = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable5.skip(2)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("skip-- " + o);
                    }
                });

        //skipLast() 跳过最后两个数据，发送前面的数据
        Observable observable6 = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable5.skipLast(2)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("skipLast-- " + o);
                    }
                });
    }

    @Test
    public void elementAt() {
        // 6、elementAt 、elementAtOrDefault
        //elementAt() 发送数据序列中第n个数据 ，序列号从0开始
        //如果该序号大于数据序列中的最大序列号，则会抛出异常，程序崩溃
        //所以在用elementAt操作符的时候，要注意判断发送的数据序列号是否越界
        Observable observable7 = Observable.just(1, 2, 3, 4, 5, 6, 7);
        observable7.elementAt(3)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("elementAt-- " + o);
                    }
                });

        //elementAtOrDefault( int n , Object default ) 发送数据序列中第n个数据 ，序列号从0开始。
        //如果序列中没有该序列号，则发送默认值
        Observable observable9 = Observable.just(1, 2, 3, 4, 5);
        observable9.elementAtOrDefault(8, 666)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("elementAtOrDefault-- " + o);
                    }
                });
    }

    @Test
    public void startWith() {
        // 7、startWith() 插入数据
        //插入普通数据
        //startWith 数据序列的开头插入一条指定的项 , 最多插入9条数据
        Observable observable = Observable.just("aa", "bb", "cc");
        observable
                .startWith("11", "22")
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("startWith-- " + o);
                    }
                });

        //插入Observable对象
        List<String> list = new ArrayList<>();
        list.add("ww");
        list.add("tt");
        observable.startWith(Observable.from(list))
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("startWith2 -- " + o);
                    }
                });
    }

    /**
     * 跟时间有关系的操作符 都需要在Activity 里面操作才有效
     */

    //不同点：delay  延时一次，延时完成后，可以连续发射多个数据。timer延时一次，延时完成后，只发射一次数据。
    @Test
    public void delay() {
        // 8、delay操作符，延迟数据发送  在Activity里好使
        Observable<String> observable = Observable.just("1", "2", "3", "4", "5", "6", "7", "8");
        //延迟数据发射的时间，仅仅延时一次，也就是发射第一个数据前延时。发射后面的数据不延时
        observable.delay(3, TimeUnit.SECONDS)  //延迟3秒钟
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        System.out.println("delay-- " + o);
                    }
                });
    }

    @Test
    public void timer() {
        //5秒后输出 hello world , 然后显示一张图片  在Activity里好使
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("timer--hello world " + aLong);
                    }
                });
    }

    @Test
    public void interval() {
        // 10、interval 轮询操作符，循环发送数据，数据从0开始递增
        //参数一：延迟时间  参数二：间隔时间  参数三：时间颗粒度
        Observable observable = Observable.interval(3000, 3000, TimeUnit.MILLISECONDS);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("interval-  " + o);
            }
        });
    }

    @Test
    public void doOnNext() {
        // 11、doOnNext() 操作符，在每次 OnNext() 方法被调用前执行
        // 使用场景：从网络请求数据，在数据被展示前，缓存到本地
        Observable observable = Observable.just("1", "2", "3", "4");
        observable.doOnNext(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("doOnNext--缓存数据" + o);
            }
        }).subscribe(new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext--" + o);
            }
        });
    }

    @Test
    public void buffer() {
        /**
         *  12、Buffer 操作符
         *  Buffer( int n )      把n个数据打成一个list包，然后再次发送。
         *  Buffer( int n , int skip)   把n个数据打成一个list包，然后跳过第skip个数据。
         */
        //使用场景：一个按钮每点击3次，弹出一个toast
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add("" + i);
        }
        Observable<String> observable = Observable.from(list);
//        observable
//                .buffer(2)   //把每两个数据为一组打成一个包，然后发送
//                .subscribe(new Action1<List<String>>() {
//                    @Override
//                    public void call(List<String> strings) {
//                        System.out.println( "buffer---------------" );
//                        Observable.from( strings ).subscribe(new Action1<String>() {
//                            @Override
//                            public void call(String s) {
//                                System.out.println( "buffer data --" + s  );
//                            }
//                        }) ;
//                    }
//                });

        //第1、2 个数据打成一个数据包，跳过第三个数据 ； 第4、5个数据打成一个包，跳过第6个数据
        observable.buffer(2, 3)  //把每两个数据为一组打成一个包，然后发送。第三个数据跳过去
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {

                        System.out.println("buffer2---------------");
                        Observable.from(strings).subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                System.out.println("buffer2 data --" + s);
                            }
                        });
                    }
                });
    }

    @Test
    public void throttleFirst() {
        // 13、throttleFirst 操作符
        // 在一段时间内，只取第一个事件，然后其他事件都丢弃。
        //使用场景：1、button按钮防抖操作，防连续点击   2、百度关键词联想，在一段时间内只联想一次，防止频繁请求服务器
        //这段代码，是循环发送数据，每秒发送一个。throttleFirst( 3 , TimeUnit.SECONDS )   在3秒内只取第一个事件，其他的事件丢弃。
        Observable.interval(1, TimeUnit.SECONDS)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("throttleFirst--" + aLong);
                    }
                });
    }

    @Test
    public void distinct() {
        //14、distinct    过滤重复的数据
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("3");
        list.add("4");
        list.add("2");
        list.add("1");
        list.add("1");

        Observable.from(list)
                .distinct()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("distinct--" + s);
                    }
                });

        //distinctUntilChanged()  过滤连续重复的数据
        Observable.from(list)
                .distinctUntilChanged()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("distinctUntilChanged--" + s);
                    }
                });
    }


    @Test
    public void debounce() {
        //15、debounce() 操作符
        // 一段时间内没有变化，就会发送一个数据。
        // 使用场景：百度关键词联想提示。在输入的过程中是不会从服务器拉数据的。当输入结束后，在400毫秒没有输入就会去获取数据。避免了，多次请求给服务器带来的压力。
//        RxTextView.textChanges(editText)
//                .debounce(5000,TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CharSequence>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "onCompleted: onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: onError");
//                    }
//
//                    @Override
//                    public void onNext(CharSequence charSequence) {
//                        Log.d(TAG, "onNext: "+charSequence.toString());
//                    }
//                });
    }

    @Test
    public void doOnSubscribe() {
        // 16、doOnSubscribe()
        /**
         *    使用场景： 可以在事件发出之前做一些初始化的工作，比如弹出进度条等等
         *    注意：
         *    1、doOnSubscribe() 默认运行在事件产生的线程里面，然而事件产生的线程一般都会运行在 io 线程里。那么这个时候做一些，更新UI的操作，是线程不安全的。
         *    所以如果事件产生的线程是io线程，但是我们又要在doOnSubscribe() 更新UI ， 这时候就需要线程切换。
         *    2、如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
         *    3、 subscribeOn() 事件产生的线程 ； observeOn() : 事件消费的线程
         */
//        Observable.create(onSubscribe)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        progressBar.setVisibility(View.VISIBLE); // 需要在主线程执行
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
    }

    @Test
    public void range() {
        //17、range 操作符的使用
        /**
         * Range操作符发射一个范围内的有序整数序列，你可以指定范围的起始和长度。
         * RxJava将这个操作符实现为range函数，它接受两个参数，一个是范围的起始值，一个是范围的数据的数目。如果你将第二个参数设为0，将导致Observable不发射任何数据（如果设置为负数，会抛异常）。
         * range默认不在任何特定的调度器上执行。有一个变体可以通过可选参数指定Scheduler。
         */
//        Observable.range(10, 3)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        Log.v("range  ", "" + integer);
//                    }
//                });

    }


}
