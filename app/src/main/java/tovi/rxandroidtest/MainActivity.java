package tovi.rxandroidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editMassage;
    private EditText editAuthor;
    private EditText editTakeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMassage = (EditText) findViewById(R.id.massage);
        editAuthor = (EditText) findViewById(R.id.author);
        editTakeNumber = (EditText) findViewById(R.id.take_number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rx_android_send:
//                rxBase1();
//                rxBase2();
                rxBase3();
                break;
            case R.id.rx_android_map:
                rxMap2();
                break;
            case R.id.rx_android_flatmap:
                rxListToEach3();
                break;
            case R.id.rx_android_filter:
                rxFilter();
                break;
            case R.id.rx_android_take:
                rxTake(TextUtils.isEmpty(editTakeNumber.getText().toString()) ? 5 : Integer.parseInt(editTakeNumber.getText().toString()));
                break;

        }
    }

    //===========================================================================================

    /**
     * 限制
     */
    private void rxTake(int num) {
        query(null).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                strings.add("<flatMap1>");
                return Observable.from(strings);
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                //过滤掉为空的数据
                return !TextUtils.isEmpty(s);
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s + "<flatMap2>");
            }
        })
                //限制数量
                .take(num).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                toast(s);
            }
        });
    }

    //===========================================================================================

    /**
     * 过滤
     */
    private void rxFilter() {
        query(null).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                strings.add("<flatMap1>");
                return Observable.from(strings);
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                //过滤掉为空的数据
                return !TextUtils.isEmpty(s);
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s + "<flatMap2>");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                toast(s);
            }
        });
    }

    //===========================================================================================

    /**
     * 获取到的数据是个集合，用该方法，让他们单个输出
     */
    private void rxListToEach3() {
        query(null).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                strings.add("<flatMap1>");
                return Observable.from(strings);
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s + "<flatMap2>");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                toast(s);
            }
        });

//        List list = new ArrayList();
//        Observable.from(list).flatMap(new Func1<List<String>, Observable<String>>() {
//
//            @Override
//            public Observable<String> call(List<String> strings) {
//                return null;
//            }
//        }).flatMap(new Func1<List<String>, Observable<String>>() {
//
//            @Override
//            public Observable<String> call(List<String> strings) {
//                return null;
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String o) {
//
//            }
//        });
    }

    /**
     * 获取到的数据是个集合，用该方法，让他们单个输出
     */
    private void rx_ListToEach2() {
        query(null).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return null;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    /**
     * 获取到的数据是个集合，用该方法，让他们单个输出
     */
    private void rx_ListToEach1() {
        // Returns a List of website URLs based on a text search
        query(null).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                Observable.from(strings).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        toast(s);
                    }
                });
            }
        });


//        List<String> lists = new ArrayList<>();
//        lists.add("ZhangSan");
//        lists.add("LiSi");
//        lists.add("WangWu");
//        Observable.from(lists).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//            }
//        });
    }

    private Observable<List<String>> query(String text) {
        List<List<String>> allList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<String> lists = new ArrayList<>();
            lists.add(i + "-ZhangSan");
            lists.add(i + "-LiSi");
            lists.add("");
            lists.add(i + "-WangWu");
            allList.add(lists);
        }
        Observable<List<String>> ob = Observable.from(allList);
        return ob;
    }

    //===========================================================================================

    /**
     * 多重Map \n
     * 输出数据前，进行数据处理
     */
    private void rxMap2() {
        Observable.just(getMassage()).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + "-" + getAuthor();
            }
        }).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.length();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer l) {
                toast("多重Map。收到的信息的长度:" + l);
            }
        });
    }

    /**
     * 单个Map \n
     * 输出数据前，进行数据处理
     */
    private void rx_map1() {
        Observable.just(getMassage()).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + "-" + getAuthor();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                toast("单个Map。收到的信息:" + s);
            }
        });
    }

    //===========================================================================================

    /**
     * 简化版3
     */
    private void rxBase3() {
//        if (!isJava8) {
        Observable.just(getMassage())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        toast(s);
                    }
                });

//        } else
//            Observable.just(getMassage())
//                    .subscribe(s -> toast(s));
    }

    /**
     * 简化版2
     */
    private void rxBase2() {
        Observable<String> observable = Observable.just(getMassage());
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                toast(s);
            }
        };
        observable.subscribe(action1);
    }

    private void rxBase1() {
        //定义发送器
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        toast("Start");
                        sub.onNext(getMassage());
                        sub.onCompleted();
                        toast("Finish");
                    }
                }
        );
        myObservable.doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
            }
        });
        //接收器
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                toast("onNext:" + s);
            }

            @Override
            public void onCompleted() {
                toast("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
            }
        };
        //注册
        myObservable.subscribe(mySubscriber);
        // Outputs getMassage()
    }

    //===========================================================================================

    private String getMassage() {
        return editMassage.getText().toString();
    }

    private String getAuthor() {
        return editAuthor.getText().toString();
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
