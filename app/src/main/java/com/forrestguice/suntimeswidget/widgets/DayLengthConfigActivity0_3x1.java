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

import android.content.Context;
import android.view.View;

import com.forrestguice.suntimeswidget.widgets.layouts.DayLengthLayout_3x1_0;
import com.forrestguice.suntimeswidget.widgets.layouts.SunLayout;

public class DayLengthConfigActivity0_3x1 extends SuntimesConfigActivity0_3x1
{
    @Override
    protected Class<?> getWidgetClass() {
        return DayLengthWidget0_3x1.class;
    }

    @Override
    protected SunLayout defaultSunLayout(Context context, int appWidgetId) {
        return new DayLengthLayout_3x1_0();
    }

    @Override
    public boolean getDefaultShowSolarNoon() {
        return false;
    }

    @Override
    protected View createPreview(final Context context, int appWidgetId, SuntimesWidget0.AppWidgetManagerView appWidgetManager)
    {
        int[] defaultSizePx = getWidgetSizeConstraints(context, getPrimaryWidgetModeSize());
        DayLengthWidget0_2x1.updateDayLengthWidget(context, appWidgetManager, appWidgetId, getWidgetClass(), defaultSizePx, defaultSunLayout(context, appWidgetId));
        return appWidgetManager.getView();
    }
}
