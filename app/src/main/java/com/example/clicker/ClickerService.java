package com.example.clicker;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Rect;

import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class ClickerService extends AccessibilityService {
    private Handler mHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
        getServiceInfo().flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;

    }



    @Override
    public void onServiceConnected() {

        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_TOUCH_INTERACTION_START |
                AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START;

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;



        info.notificationTimeout = 100;

        this.setServiceInfo(info);
//        autoClick(7000, 100, 950, 581);
//        Toast.makeText(this, "clicking started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
            // Get the touch coordinates from the event
            AccessibilityNodeInfo source = event.getSource();
            Rect rect = new Rect();
            source.getBoundsInScreen(rect);
            int x = rect.centerX();
            int y = rect.centerY();
            // Log the touch coordinates
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Touch coordinates: x = " + x + ", y = " + y, Toast.LENGTH_SHORT).show();
                }
            });
            source.recycle();

    }

    @Override
    public void onInterrupt() {

    }

    public void autoClick(int startTimeMs, int durationMs, int x, int y) {
        boolean isCalled = dispatchGesture(gestureDescription(startTimeMs, durationMs, x, y), null, null);
        System.out.println(isCalled);
    }

    public GestureDescription gestureDescription(int startTimeMs, int durationMs, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        return createGestureDescription(new GestureDescription.StrokeDescription(path, startTimeMs, durationMs));
    }

    public GestureDescription createGestureDescription(GestureDescription.StrokeDescription... strokes) {
        GestureDescription.Builder builder = new GestureDescription.Builder();
        for (GestureDescription.StrokeDescription stroke : strokes) {
            builder.addStroke(stroke);
        }
        return builder.build();
    }
}