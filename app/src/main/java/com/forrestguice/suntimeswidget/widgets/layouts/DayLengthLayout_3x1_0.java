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

import android.content.Context;
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

    @Override
    public void updateViews(Context context, int appWidgetId, RemoteViews views, SuntimesRiseSetData data)
    {
        super.updateViews(context, appWidgetId, views, data);

        DayLengthDisplay dayLength = DayLengthDisplay.create(context, appWidgetId);
        String value = dayLength.dayLength + " " + dayLength.dayDelta;
        String text = context.getString(R.string.daylength_widget_label, value);
        CharSequence span = (boldTime ? SpanUtils.createBoldColorSpan(null, text, value, timeColor)
                                      : SpanUtils.createColorSpan(null, text, value, timeColor));
        views.setTextViewText(R.id.text_delta_day_value, span);
        views.setViewVisibility(R.id.layout_delta_day, View.VISIBLE);
    }
}
