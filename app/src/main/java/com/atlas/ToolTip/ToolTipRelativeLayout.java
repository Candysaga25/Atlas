/*
 * Copyright 2013 Niek Haarman
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlas.ToolTip;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.atlas.ToolTip.exception.NoOverflowMenuRuntimeException;
import com.atlas.ToolTip.exception.NoTitleViewRuntimeException;
import com.atlas.ToolTip.exception.ViewNotFoundRuntimeException;


/**
 * Created by sagar.shedge on 7/31/2015.
 */
public class ToolTipRelativeLayout extends RelativeLayout {

    public static final String ACTION_BAR_TITLE = "action_bar_title";
    public static final String ID = "id";
    public static final String ANDROID = "android";
    public static final String ACTION_BAR = "action_bar";
    public static final String ACTION_MENU_VIEW = "ActionMenuView";
    public static final String OVERFLOW_MENU_BUTTON = "OverflowMenuButton";

    public ToolTipRelativeLayout(final Context context) {
        super(context);
    }

    public ToolTipRelativeLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolTipRelativeLayout(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }


    public ToolTipView showToolTipForView(final ToolTip toolTip, final View view) {
        final ToolTipView toolTipView = new ToolTipView(getContext());
        toolTipView.setToolTip(toolTip, view);
        addView(toolTipView);
        return toolTipView;
    }


    public ToolTipView showToolTipForViewResId(final Activity activity, final ToolTip toolTip, final int resId) {
        final ToolTipView toolTipView = new ToolTipView(getContext());
        final View decorView = activity.getWindow().getDecorView();
        final View view = decorView.findViewById(resId);

        if (view == null) {
            throw new ViewNotFoundRuntimeException();
        }

        toolTipView.setToolTip(toolTip, view);
        addView(toolTipView);
        return toolTipView;
    }


    @TargetApi(11)
    public ToolTipView showToolTipForActionBarHome(final Activity activity, final ToolTip toolTip) {
        final int homeResId = android.R.id.home;
        return showToolTipForViewResId(activity, toolTip, homeResId);
    }


    @TargetApi(11)
    public ToolTipView showToolTipForActionBarTitle(final Activity activity, final ToolTip toolTip) {
        final int titleResId = Resources.getSystem().getIdentifier(ACTION_BAR_TITLE, ID, ANDROID);
        if (titleResId == 0) {
            throw new NoTitleViewRuntimeException();
        }
        return showToolTipForViewResId(activity, toolTip, titleResId);
    }


    @TargetApi(11)
    public ToolTipView showToolTipForActionBarOverflowMenu(final Activity activity, final ToolTip toolTip) {
        return showToolTipForView(toolTip, findActionBarOverflowMenuView(activity));
    }

    @TargetApi(11)
    private static View findActionBarOverflowMenuView(final Activity activity) {
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        final int actionBarViewResId = Resources.getSystem().getIdentifier(ACTION_BAR, ID, ANDROID);
        final ViewGroup actionBarView = (ViewGroup) decorView.findViewById(actionBarViewResId);

        ViewGroup actionMenuView = null;
        int actionBarViewChildCount = actionBarView.getChildCount();
        for (int i = 0; i < actionBarViewChildCount; ++i) {
            if (actionBarView.getChildAt(i).getClass().getSimpleName().equals(ACTION_MENU_VIEW)) {
                actionMenuView = (ViewGroup) actionBarView.getChildAt(i);
            }
        }

        if (actionMenuView == null) {
            throw new NoOverflowMenuRuntimeException();
        }

        int actionMenuChildCount = actionMenuView.getChildCount();
        View overflowMenuButton = null;
        for (int i = 0; i < actionMenuChildCount; ++i) {
            if (actionMenuView.getChildAt(i).getClass().getSimpleName().equals(OVERFLOW_MENU_BUTTON)) {
                overflowMenuButton = actionMenuView.getChildAt(i);
            }
        }

        if (overflowMenuButton == null) {
            throw new NoOverflowMenuRuntimeException();
        }

        return overflowMenuButton;
    }
}
