package example.israt.com.testbioscopeserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import example.israt.com.testbioscopeserver.adapter.ErrorAdapter;
import example.israt.com.testbioscopeserver.dbhelper.DatabaseHelper;
import example.israt.com.testbioscopeserver.model.EventReceived;
import example.israt.com.testbioscopeserver.model.ServerStatus;

public class MainActivity extends AppCompatActivity {
    private TextView textView,counttext;
    private   SimpleDateFormat sdf;
    private  Date resultdate ;
    private RecyclerView errors;
    private ServerStatus serverStatus;
    private DatabaseHelper databaseHelper;
    private  LinearLayoutManager layoutManager;
    private   ArrayList<ServerStatus> data ;
    private  ErrorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        serverStatus=new ServerStatus();
        databaseHelper=new DatabaseHelper(this);
      String token=  FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("ServerStatus");
        sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");

        EventBus.getDefault().register(this);
// Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.status);
        counttext=(TextView)findViewById(R.id.textView2);


            String showval=databaseHelper.getLast();
            if(showval==null)
            {
                textView.setText("Bioscope server is running!");
            }
            else
            textView.setText("Bioscope is "+showval);
       // }
        errors = (RecyclerView) findViewById(R.id.errorrecycler);
        errors.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        errors.setLayoutManager(layoutManager);
        errors.setItemAnimator(new DefaultItemAnimator());

        int count= databaseHelper.getCount();
        if(count==0)
        {
            counttext.setText("No Alert");
        }
        else {
            counttext.setText("Bioscope was down for "+ String.valueOf(count)+" times!");
        }

        data=databaseHelper.getAll();


       // removedItems = new ArrayList<Integer>();

        adapter = new ErrorAdapter(this,data);
        errors.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceived(EventReceived eventReceived) {
        String state= eventReceived.getState();
        if(state.equals("No"))
        {
            textView.setText("Bioscope is down!");
        }
        else {
            textView.setText("Bioscope is running!");
        }
        counttext.setText("Bioscope was down for "+ String.valueOf(databaseHelper.getCount())+" times!");

        data=databaseHelper.getAll();


        // removedItems = new ArrayList<Integer>();


        adapter.update(data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MainApp.setChatActivityOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainApp.setChatActivityOpen(false);
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();


        finish();
    }
}
