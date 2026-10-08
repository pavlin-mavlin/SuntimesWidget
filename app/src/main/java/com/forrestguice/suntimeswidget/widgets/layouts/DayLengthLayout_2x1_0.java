/**
    This file is part of SuntimesWidget.

    SuntimesWidget is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SuntimesWidget is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SuntimesWidget.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.forrestguice.suntimeswidget.widgets.layouts;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.calculator.SuntimesRiseSetData;
import com.forrestguice.suntimeswidget.themes.SuntimesTheme;
import com.forrestguice.suntimeswidget.views.SpanUtils;

/**
 * Day length and its change compared to yesterday, on two lines:
 *   07:01:01
 *   -3:13
 * Text is always scaled to fill the available space.
 */
public class DayLengthLayout_2x1_0 extends SunLayout
{
    protected static final float DELTA_SCALE = 0.7f;    // second line relative to the first
    protected static final float LINE_HEIGHT = 1.2f;    // line height relative to text size

    protected int timeColor = Color.WHITE;

    @Override
    public void initLayoutID() {
        this.layoutID = R.layout.layout_widget_daylength_2x1;
    }

    @Override
    public void themeViews(Context context, RemoteViews views, SuntimesTheme theme)
    {
        super.themeViews(context, views, theme);
        timeColor = theme.getTimeColor();
        views.setTextColor(R.id.text_daylength, timeColor);
        views.setTextColor(R.id.text_daylength_delta, timeColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            views.setTextViewTextSize(R.id.text_daylength, TypedValue.COMPLEX_UNIT_DIP, timeSizeSp);
            views.setTextViewTextSize(R.id.text_daylength_delta, TypedValue.COMPLEX_UNIT_DIP, timeSizeSp * DELTA_SCALE);
        }
    }

    @Override
    public void updateViews(Context context, int appWidgetId, RemoteViews views, SuntimesRiseSetData data)
    {
        super.updateViews(context, appWidgetId, views, data);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            scaleViews(context, views);
        }

        DayLengthDisplay dayLength = DayLengthDisplay.create(context, appWidgetId);
        views.setTextViewText(R.id.text_daylength, (boldTime ? SpanUtils.createBoldSpan(null, dayLength.dayLength, dayLength.dayLength) : dayLength.dayLength));
        views.setTextViewText(R.id.text_daylength_delta, (boldTime ? SpanUtils.createBoldSpan(null, dayLength.dayDelta, dayLength.dayDelta) : dayLength.dayDelta));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void scaleViews(Context context, RemoteViews views)
    {
        // both lines must fit the height; a line (without font padding) is about LINE_HEIGHT x text size
        float maxHeightDp = maxDimensionsDp[1] - (paddingDp[1] + paddingDp[3]) - 4;
        float maxSizeByHeight = maxHeightDp / (LINE_HEIGHT * (1 + DELTA_SCALE));

        // the first line must fit the width (adjustTextSize only scales up, and only by width here)
        int[] maxDp = new int[] { maxDimensionsDp[0] - (paddingDp[0] + paddingDp[2]) - 8, (int)maxHeightDp };
        float[] adjustedSizeSp = adjustTextSize(context, maxDp, paddingDp, "sans-serif", boldTime, "00:00:00", timeSizeSp, SuntimesLayout.MAX_SP, "", suffixSizeSp);

        float sizeSp = Math.max(Math.min(adjustedSizeSp[0], maxSizeByHeight), 6);
        views.setTextViewTextSize(R.id.text_daylength, TypedValue.COMPLEX_UNIT_DIP, sizeSp);
        views.setTextViewTextSize(R.id.text_daylength_delta, TypedValue.COMPLEX_UNIT_DIP, sizeSp * DELTA_SCALE);
    }
}
