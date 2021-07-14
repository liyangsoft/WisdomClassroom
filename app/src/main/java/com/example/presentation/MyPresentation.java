package com.example.presentation;

import android.app.Presentation;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.WindowManager;

import com.example.wisdomclassroom.R;

public class MyPresentation extends Presentation {
    public MyPresentation(Context context, Display display) {
        super(context, display);
    }

//        public MyPresentation(Context context, Display display,
//                              DemoPresentationContents contents) {
//            super(context, display);
//            mContents = contents;
//        }

    /**
     * Sets the preferred display mode id for the presentation.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setPreferredDisplayMode(int modeId) {
        //       mContents.displayModeId = modeId;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.preferredDisplayModeId = modeId;
        getWindow().setAttributes(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // Get the resources for the context of the presentation.
        // Notice that we are getting the resources from the context of the presentation.
        Resources r = getContext().getResources();

        // Inflate the layout.
        setContentView(R.layout.fragment_teaching_presentation);
            /*frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
            bt_presentation = (Button) findViewById(R.id.bt_presentation);
            bt_presentation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Dialog")
                            .setMessage("Prsentation Click Test")
                            .setPositiveButton("OK", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    dialog.show();
                }
            });*/

    }
}
