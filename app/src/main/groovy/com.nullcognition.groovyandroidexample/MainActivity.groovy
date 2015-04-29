package com.nullcognition.groovyandroidexample

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import groovyx.gpars.GParsPool
import groovyx.gpars.MessagingRunnable
import groovyx.gpars.ParallelEnhancer
import groovyx.gpars.actor.DynamicDispatchActor
import jsr166y.ForkJoinPool

import java.util.concurrent.atomic.AtomicInteger

public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      statelessActor()
        gparsPool()


    }

    private void gparsPool() {

        // sum concurrencly
        GParsPool.withPool {
            final AtomicInteger sum = new AtomicInteger(0)
            [1, 2, 3, 4, 5].eachParallel { sum.addAndGet(it) }
            Log.e("tag", sum.toString())
        }

        // multiply asynchronously
        GParsPool.withPool {
            final List mult = [1, 2, 3, 4, 5].collectParallel { it * 2 }
            Log.e("tag", mult.toString()) // mul and sum variables defined in inner scope
        }

        // check all elements in collection for criteria
        GParsPool.withPool(5) { ForkJoinPool pool -> // withPool may take an exception handler too
            boolean isMet = false;
            if ([1, 2, 3, 4, 5].everyParallel { it > 1 }) isMet = true;
            Log.e("tag", isMet.toString()) // should be false since all are not above 1
            if ([1, 2, 3, 4, 5].everyParallel { it > 0 }) isMet = true; // should switch to true
            Log.e("tag", isMet.toString())
        }

        // .withExistingPool() to resue instance
        // withPool{} if import static groovyx.gpars.GParsPool.* is used

        // using parallelEnhancer to enhance meta-classes of any classes or individual intances with the parallel methods
        def list = [1, 2, 3, 4, 5, 6, 7, 8, 9]
        ParallelEnhancer.enhanceInstance(list)
        Log.e('tag', list.collectParallel { it * 2 }.toString())

        def animals = ['dog', 'ant', 'cat', 'whale']
        ParallelEnhancer.enhanceInstance animals
        Log.e("tag", animals.anyParallel {it ==~ /ant/} ? 'found ant' : 'not found')
        Log.e("tag", animals.anyParallel {it.contains('a')} ? 'all have the letter a' : 'not all have the letter a')

        // for exceptions for passed in closures, the first exception gets rethrown from the xxxParallel method and the algo stops

        // transparently parallel collections
        // continue from
        // http://gpars.org/1.2.1/guide/guide/single.html#dataParallelism_parallelCollections_GParsPool



    }

    private void statelessActor() {
        final MyStatelessActor actor = new MyStatelessActor();
        actor.start();
        actor.send("Hello");
        actor.sendAndWait(10);
        actor.sendAndContinue(10.0, new MessagingRunnable<String>() {
            @Override
            protected void doRun(final String s) {
                Log.e("TAG", "Received a reply " + s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class MyStatelessActor extends DynamicDispatchActor {
    public void onMessage(final String msg) {
        System.out.println("Received " + msg);
        replyIfExists("Thank you");
    }

    public void onMessage(final Integer msg) {
        System.out.println("Received a number " + msg);
        replyIfExists("Thank you");
    }

    public void onMessage(final Object msg) {
        System.out.println("Received an object " + msg);
        replyIfExists("Thank you");
    }
}
