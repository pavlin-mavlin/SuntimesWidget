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

package com.forrestguice.suntimeswidget.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.widgets.layouts.DayLengthLayout_3x1_0;

/**
 * Day length widget (3x1): sunrise, noon, sunset, and the day length with its change compared to yesterday.
 */
public class DayLengthWidget0_3x1 extends DayLengthWidget0_2x1
{
    @Override
    protected Class<?> getConfigClass() {
        return DayLengthConfigActivity0_3x1.class;
    }

    @Override
    protected void initMinSize(Context context)
    {
        minSize[0] = context.getResources().getInteger(R.integer.widget_size_minWidthDp3x1);
        minSize[1] = context.getResources().getInteger(R.integer.widget_size_minHeightDp);
    }

    @Override
    protected void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        updateDayLengthWidget(context, new AppWidgetManagerWrapper(appWidgetManager), appWidgetId, DayLengthWidget0_3x1.class, getMinSize(context), new DayLengthLayout_3x1_0());
    }
}
