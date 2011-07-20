package no.uka.findmyapp.ukaprogram.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;

public final class ColorUtils {
 public static void changeButtonColor(Button button, int color){
	 button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
 }
}
