package jp.goka.favos.helper;

import android.text.Spannable;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

/**
 * Created by katsuyagoto on 2014/06/20.
 */
public class TextViewHelper {

	public static void makeUnderLine(TextView textView){
		UnderlineSpan underlineSpan = new UnderlineSpan();
		Spannable.Factory factory = Spannable.Factory.getInstance();
		Spannable spannable = factory.newSpannable(textView.getText());
		spannable.setSpan(
				underlineSpan,
				0,
				textView.getText().length(),
				spannable.getSpanFlags(underlineSpan)
		);
		textView.setText(spannable, TextView.BufferType.SPANNABLE);
	}

}
