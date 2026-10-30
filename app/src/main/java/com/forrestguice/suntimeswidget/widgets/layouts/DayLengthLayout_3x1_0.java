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
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.calculator.SuntimesRiseSetData;
import com.forrestguice.suntimeswidget.settings.WidgetSettings;
import com.forrestguice.suntimeswidget.views.SpanUtils;

/**
 * Same as SunLayout_3x1_0 (sunrise, noon, sunset), but the bottom line shows the day length
 * and its change compared to yesterday, e.g. "Day length: 07:01:01 -3:13".
 */
public class DayLengthLayout_3x1_0 extends SunLayout_3x1_0
{
    @Override
    protected float[] findAdjustedSize(Context context, int appWidgetId)
    {
        boolean showSolarNoon = WidgetSettings.loadShowNoonPref(context, appWidgetId);
        boolean showSeconds = WidgetSettings.loadShowSecondsPref(context, appWidgetId);
        int numRows = 2, numCols = 2;    // the day length row is always shown
        numRows += showSolarNoon ? 1 : 0;
        numCols += showSolarNoon ? 1 : 0;
        int[] maxDp = new int[] {(maxDimensionsDp[0] - (paddingDp[0] + paddingDp[2] + 32)) / numCols,
                                 ((maxDimensionsDp[1] - (paddingDp[1] + paddingDp[3])) / numRows)};
        float maxSp = SuntimesLayout.MAX_SP;
        return adjustTextSize(context, maxDp, paddingDp, "sans-serif", boldTime, (showSeconds ? "00:00:00" : "00:00"), timeSizeSp, maxSp, "MM", suffixSizeSp, iconSizeDp);
    }

    protected String dayLengthValue = "";
    protected String dayLengthText = "";

    @Override
    public void updateViews(Context context, int appWidgetId, RemoteViews views, SuntimesRiseSetData data)
    {
        DayLengthDisplay dayLength = DayLengthDisplay.create(context, appWidgetId);
        dayLengthValue = dayLength.dayLength + " " + dayLength.dayDelta;
        dayLengthText = context.getString(R.string.daylength_widget_label, dayLengthValue);    // before super.updateViews (calls scaleViews)

        super.updateViews(context, appWidgetId, views, data);

        CharSequence span = (boldTime ? SpanUtils.createBoldColorSpan(null, dayLengthText, dayLengthValue, timeColor)
                                      : SpanUtils.createColorSpan(null, dayLengthText, dayLengthValue, timeColor));
        views.setTextViewText(R.id.text_delta_day_value, span);
        views.setViewVisibility(R.id.layout_delta_day, View.VISIBLE);
    }

    /**
     * Scales the day length line by the same factor as the times (limited to the widget's width).
     */
    @TargetApi(16)
    @Override
    public void scaleViews(Context context, int appWidgetId, RemoteViews views, float[] adjustedSizeSp)
    {
        super.scaleViews(context, appWidgetId, views, adjustedSizeSp);
        if (adjustedSizeSp[0] > timeSizeSp)
        {
            float sizeSp = textSizeSp * (adjustedSizeSp[0] / timeSizeSp);

            Paint paint = new Paint();
            paint.setTypeface(Typeface.create("sans-serif", boldTime ? Typeface.BOLD : Typeface.NORMAL));
            Rect bounds = new Rect();
            getTextBounds(context, dayLengthText, sizeSp, paint, bounds);

            float maxWidthDp = maxDimensionsDp[0] - (paddingDp[0] + paddingDp[2]) - 8;
            float maxWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxWidthDp, context.getResources().getDisplayMetrics());
            if (bounds.width() > maxWidthPx) {
                sizeSp *= (maxWidthPx / bounds.width());
            }
            views.setTextViewTextSize(R.id.text_delta_day_value, TypedValue.COMPLEX_UNIT_DIP, Math.max(sizeSp, textSizeSp));
        }
    }
}
