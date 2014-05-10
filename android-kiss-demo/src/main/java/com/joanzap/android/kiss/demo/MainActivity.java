/**
 * Copyright 2014 Joan Zapata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joanzap.android.kiss.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import com.joanzap.android.kiss.api.annotation.InjectService;
import com.joanzap.android.kiss.api.annotation.Result;
import com.joanzap.android.kiss.api.internal.Kiss;
import com.joanzap.android.kiss.demo.event.UserEvent;

import static com.joanzap.android.kiss.api.annotation.Result.Sender.ALL;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";

    @InjectService
    public DemoService service;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        Kiss.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        service.getUserAsyncWithCache(3L);
    }

    /*
        The return type of getUserAsyncWithCache() is UserEvent, so the result will come here.

        As we used @Cached on getUserAsyncWithCache(), the result will probably come twice :
        the first result will be the cached value, the second one will be the up-to-date result.

        We will never receive a cached result AFTER a real result, even if we call getUserAsyncWithCache()
        multiple times.
     */
    @Result
    public void onUserFetched(UserEvent e) {
        text.setText(e.name + " " + e.age);
    }

    /*
        Sometimes we just need to know when the result arrives,
        but don't actually use it. So just use a no-arg method
        with the event type in the annotation.
     */
    @Result(UserEvent.class)
    public void onUserFetched() {
        Log.i(TAG, "User fetched !");
    }

    /*
        By default we only receive the results of requests made
         on the injected service of this class. It means that if
         you have two fragments using a different service, and
         calling the same method on the service, they won't receive
         others response.

         That fits most case, but if for some reason you want
         to listen to ALL events of a certain type, no matter
         which service the method was called on, you can use
         from = ALL.
     */
    @Result(from = ALL)
    public void onUserFetchedFromAnywhere(UserEvent e) {
        Log.i(TAG, "User fetched from anywhere !");
    }

}
