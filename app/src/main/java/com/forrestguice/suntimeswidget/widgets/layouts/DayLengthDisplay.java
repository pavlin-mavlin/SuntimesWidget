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

import com.forrestguice.suntimeswidget.calculator.SuntimesRiseSetData;
import com.forrestguice.suntimeswidget.calculator.settings.CompareMode;

import java.util.Locale;

/**
 * Day length (today) and its change compared to yesterday, formatted as "07:01:01" and "-3:13".
 */
public class DayLengthDisplay
{
    public final String dayLength;
    public final String dayDelta;

    protected DayLengthDisplay(long dayLengthSeconds, long dayDeltaSeconds)
    {
        dayLength = formatDayLength(dayLengthSeconds);
        dayDelta = formatDayDelta(dayDeltaSeconds);
    }

    /**
     * Calculates today's day length using the widget's settings (location, time mode, etc), always
     * comparing to yesterday (regardless of the widget's compare mode setting).
     */
    public static DayLengthDisplay create(Context context, int appWidgetId)
    {
        SuntimesRiseSetData data = new SuntimesRiseSetData(context, appWidgetId);
        data.setCompareMode(CompareMode.YESTERDAY);
        data.calculate(context);

        long today = Math.round(data.dayLengthToday() / 1000d);
        long yesterday = Math.round(data.dayLengthOther() / 1000d);
        return new DayLengthDisplay(today, today - yesterday);
    }

    public static String formatDayLength(long seconds)
    {
        return String.format(Locale.US, "%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    public static String formatDayDelta(long seconds)
    {
        char sign = (seconds < 0 ? '-' : '+');
        long s = Math.abs(seconds);
        return String.format(Locale.US, "%c%d:%02d", sign, s / 60, s % 60);
    }
}
