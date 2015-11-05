package tovi.rxandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just("Hello, world! -Dan")
                .subscribe(s -> System.out.println(s));
    }
}
