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

import com.forrestguice.suntimeswidget.widgets.layouts.DayLengthLayout_2x1_0;
import com.forrestguice.suntimeswidget.widgets.layouts.SunLayout;

/**
 * Day length widget (2x1): day length and its change compared to yesterday.
 */
public class DayLengthWidget0_2x1 extends SuntimesWidget0_2x1
{
    @Override
    protected Class<?> getConfigClass() {
        return DayLengthConfigActivity0_2x1.class;
    }

    @Override
    protected void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        updateDayLengthWidget(context, new AppWidgetManagerWrapper(appWidgetManager), appWidgetId, DayLengthWidget0_2x1.class, getMinSize(context), new DayLengthLayout_2x1_0());
    }

    /**
     * Like SuntimesWidget0.updateAppWidget, but always uses the given layout (ignores the "allow resize" setting
     * that would otherwise swap in one of the standard sunrise/sunset layouts).
     */
    protected static void updateDayLengthWidget(Context context, WidgetManagerInterface appWidgetManager, int appWidgetId, Class<?> widgetClass, int[] defSize, SunLayout layout)
    {
        layout.setMaxDimensionsDp(widgetSizeDp(context, appWidgetManager, appWidgetId, defSize));
        layout.setCategory(widgetCategory(appWidgetManager, appWidgetId));
        SuntimesWidget0.updateAppWidget(context, appWidgetManager, appWidgetId, layout, widgetClass);
    }
}
