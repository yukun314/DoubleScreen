package com.zyk.doublescreen;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mMessage,mMessage1,mMessage2;
    private DisplayManager mDisplayManager;
    private MediaRouter mMediaRouter;
    private Button button;
    private Presentation m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessage = (TextView) findViewById(R.id.activity_main_message);
        mMessage1 = (TextView) findViewById(R.id.activity_main_message1);
        mMessage2 = (TextView) findViewById(R.id.activity_main_message2);
        button = (Button) findViewById(R.id.activity_main_buttom);

        mMediaRouter = (MediaRouter)getSystemService(Context.MEDIA_ROUTER_SERVICE);
        mDisplayManager = (DisplayManager)getSystemService(Context.DISPLAY_SERVICE);
        //该类别可以用于识别辅助显示器，适合于用作展示显示诸如HDMI或无线显示器。应用程序可以自动地预测这些内容呈现显示，以提供更丰富的第二屏幕体验
//		String displayCategory = DisplayManager.DISPLAY_CATEGORY_PRESENTATION;
        String displayCategory = null;
        final Display[] displays = mDisplayManager.getDisplays(displayCategory);
        String str = "";
        String str1 = "";
        for (Display display : displays) {
            str+=display.toString();
            System.out.println("display:"+display);
            int displayId = display.getDisplayId();
            int state = display.getState();
            String name = display.getName();
            String mstate = "";
            if(state == 0){
                mstate +="STATE_UNKNOWN";
            }else if(state == 1){
                mstate +="STATE_OFF";
            }else if(state == 2){
                mstate +="STATE_ON";
            }else if(state == 3){
                mstate +="STATE_DOZE";
            }else if(state == 4){
                mstate +="STATE_DOZE_SUSPEND";
            }
            str1+="displayId:"+displayId+"   name:"+name+"   mstate："+mstate+"\n";
        }

        mMessage.setText(str);
        mMessage1.setText(str1);

//		MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
//                MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
//        Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;
//
//        mMessage2.setText(presentationDisplay.toString());

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				PresentationContents contents = new PresentationContents(getNextPhoto());
//		        showPresentation(display, contents);
//				mDisplayManager.registerDisplayListener(mDisplayListener, mHandler);

                // If there is more than one suitable presentation display, then we could consider
                // giving the user a choice.  For this example, we simply choose the first display
                // which is the one the system recommends as the preferred presentation display.
                Display display = displays[1];
                Presentation presentation = new MyPresentation(MainActivity.this, display);
                presentation.show();
            }
        });

    }

    private class MyPresentation extends Presentation{

        public MyPresentation(Context outerContext, Display display) {
            super(outerContext, display);
        }

    }
}
