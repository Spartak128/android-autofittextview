package me.grantland.widget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
public class AutofitTextViewTest {

    private static final float PRECISION = 0.5f;

    @Rule
    public final ActivityTestRule<AutofitTestActivity> rule =
            new ActivityTestRule<AutofitTestActivity>(AutofitTestActivity.class);

    @Test
    public void autofitSingleLineWithPadding() throws Throwable {
        final AutofitTestActivity activity = rule.getActivity();
        final AtomicReference<AutofitTextView> viewRef = new AtomicReference<AutofitTextView>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewRef.set(activity.createAutofitTextView());
            }
        });

        final AutofitTextView view = viewRef.get();
        assertNotNull(view);

        final AtomicReference<Float> expectedSizeRef = new AtomicReference<Float>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("AutoFit single line padding test");
                view.setMaxLines(1);
                view.setPrecision(PRECISION);
                view.setPadding(16, 8, 16, 8);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 80f);
                view.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, 80f);
                view.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 12f);
                layoutView(view, 200, 100);
                expectedSizeRef.set(referenceAutofitTextSize(view, 12f, 80f, 1, PRECISION));
            }
        });

        float expectedSize = expectedSizeRef.get();
        float actualSize = view.getTextSize();
        assertEquals(expectedSize, actualSize, 0f);

        Layout layout = view.getLayout();
        assertNotNull(layout);
        assertEquals(1, layout.getLineCount());
        assertEquals(0, layout.getEllipsisCount(0));
    }

    @Test
    public void autofitMultiLineWithLineSpacingAndPadding() throws Throwable {
        final AutofitTestActivity activity = rule.getActivity();
        final AtomicReference<AutofitTextView> viewRef = new AtomicReference<AutofitTextView>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewRef.set(activity.createAutofitTextView());
            }
        });

        final AutofitTextView view = viewRef.get();
        assertNotNull(view);

        final AtomicReference<Float> expectedSizeRef = new AtomicReference<Float>();
        final AtomicReference<Integer> expectedLinesRef = new AtomicReference<Integer>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("AutoFit multi-line text with extra spacing.");
                view.setMaxLines(3);
                view.setPrecision(PRECISION);
                view.setLineSpacing(6f, 1.1f);
                view.setIncludeFontPadding(false);
                view.setPadding(12, 12, 12, 12);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 64f);
                view.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, 64f);
                view.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 14f);
                layoutView(view, 180, 220);
                expectedSizeRef.set(referenceAutofitTextSize(view, 14f, 64f, 3, PRECISION));
                expectedLinesRef.set(referenceLineCount(view, expectedSizeRef.get()));
            }
        });

        float expectedSize = expectedSizeRef.get();
        int expectedLines = expectedLinesRef.get();
        float actualSize = view.getTextSize();
        assertEquals(expectedSize, actualSize, 0f);

        Layout layout = view.getLayout();
        assertNotNull(layout);
        assertEquals(expectedLines, layout.getLineCount());
        for (int i = 0; i < layout.getLineCount(); i++) {
            assertEquals(0, layout.getEllipsisCount(i));
        }
    }

    @Test
    public void autofitRtlWithTypeface() throws Throwable {
        final AutofitTestActivity activity = rule.getActivity();
        final AtomicReference<AutofitTextView> viewRef = new AtomicReference<AutofitTextView>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewRef.set(activity.createAutofitTextView());
            }
        });

        final AutofitTextView view = viewRef.get();
        assertNotNull(view);

        final AtomicReference<Float> expectedSizeRef = new AtomicReference<Float>();
        final AtomicReference<Integer> expectedLinesRef = new AtomicReference<Integer>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("مرحبا بالعالم");
                view.setTypeface(Typeface.SERIF, Typeface.BOLD);
                view.setTextDirection(View.TEXT_DIRECTION_RTL);
                view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                view.setMaxLines(2);
                view.setPrecision(PRECISION);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 72f);
                view.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, 72f);
                view.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 16f);
                layoutView(view, 160, 160);
                expectedSizeRef.set(referenceAutofitTextSize(view, 16f, 72f, 2, PRECISION));
                expectedLinesRef.set(referenceLineCount(view, expectedSizeRef.get()));
            }
        });

        float expectedSize = expectedSizeRef.get();
        int expectedLines = expectedLinesRef.get();
        float actualSize = view.getTextSize();
        assertEquals(expectedSize, actualSize, 0f);

        Layout layout = view.getLayout();
        assertNotNull(layout);
        assertEquals(expectedLines, layout.getLineCount());
        for (int i = 0; i < layout.getLineCount(); i++) {
            assertEquals(0, layout.getEllipsisCount(i));
        }
    }

    @Test
    public void autofitEmojiAndNewlines() throws Throwable {
        final AutofitTestActivity activity = rule.getActivity();
        final AtomicReference<AutofitTextView> viewRef = new AtomicReference<AutofitTextView>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewRef.set(activity.createAutofitTextView());
            }
        });

        final AutofitTextView view = viewRef.get();
        assertNotNull(view);

        final AtomicReference<Float> expectedSizeRef = new AtomicReference<Float>();
        final AtomicReference<Integer> expectedLinesRef = new AtomicReference<Integer>();
        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText("Autofit\nemoji 😀😃😄 test");
                view.setMaxLines(3);
                view.setPrecision(PRECISION);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 70f);
                view.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, 70f);
                view.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 18f);
                layoutView(view, 150, 240);
                expectedSizeRef.set(referenceAutofitTextSize(view, 18f, 70f, 3, PRECISION));
                expectedLinesRef.set(referenceLineCount(view, expectedSizeRef.get()));
            }
        });

        float expectedSize = expectedSizeRef.get();
        int expectedLines = expectedLinesRef.get();
        float actualSize = view.getTextSize();
        assertEquals(expectedSize, actualSize, 0f);

        Layout layout = view.getLayout();
        assertNotNull(layout);
        assertEquals(expectedLines, layout.getLineCount());
        for (int i = 0; i < layout.getLineCount(); i++) {
            assertEquals(0, layout.getEllipsisCount(i));
        }
    }

    private static void layoutView(TextView view, int widthPx, int heightPx) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(widthPx, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(heightPx, View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, widthPx, heightPx);
    }

    private static float referenceAutofitTextSize(TextView view, float minTextSize,
            float maxTextSize, int maxLines, float precision) {
        if (maxLines <= 0 || maxLines == Integer.MAX_VALUE) {
            return view.getTextSize();
        }

        int targetWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        if (targetWidth <= 0) {
            return view.getTextSize();
        }

        CharSequence text = view.getText();
        if (view.getTransformationMethod() != null) {
            text = view.getTransformationMethod().getTransformation(text, view);
        }
        if (text == null) {
            text = "";
        }

        TextPaint paint = new TextPaint();
        paint.set(view.getPaint());
        paint.setTextSize(maxTextSize);

        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
        float size = maxTextSize;
        if ((maxLines == 1 && paint.measureText(text, 0, text.length()) > targetWidth)
                || getLineCount(text, paint, size, targetWidth, metrics) > maxLines) {
            size = binarySearchTextSize(text, paint, targetWidth, maxLines, 0f, maxTextSize,
                    precision, metrics, 0);
        }

        if (size < minTextSize) {
            size = minTextSize;
        }

        return size;
    }

    private static int referenceLineCount(TextView view, float textSize) {
        TextPaint paint = new TextPaint();
        paint.set(view.getPaint());
        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
        CharSequence text = view.getText();
        if (view.getTransformationMethod() != null) {
            text = view.getTransformationMethod().getTransformation(text, view);
        }
        if (text == null) {
            text = "";
        }
        int targetWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        return getLineCount(text, paint, textSize, targetWidth, metrics);
    }

    private static float binarySearchTextSize(CharSequence text, TextPaint paint, float targetWidth,
            int maxLines, float low, float high, float precision, DisplayMetrics metrics,
            int iterations) {
        if (iterations > 64) {
            return low;
        }
        float mid = (low + high) / 2.0f;
        int lineCount = 1;
        StaticLayout layout = null;

        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, metrics));
        if (maxLines != 1) {
            layout = new StaticLayout(text, paint, (int) targetWidth,
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            lineCount = layout.getLineCount();
        }

        if (lineCount > maxLines) {
            if ((high - low) < precision) {
                return low;
            }
            return binarySearchTextSize(text, paint, targetWidth, maxLines, low, mid, precision,
                    metrics, iterations + 1);
        } else if (lineCount < maxLines) {
            return binarySearchTextSize(text, paint, targetWidth, maxLines, mid, high, precision,
                    metrics, iterations + 1);
        } else {
            float maxLineWidth = 0f;
            if (maxLines == 1) {
                maxLineWidth = paint.measureText(text, 0, text.length());
            } else {
                for (int i = 0; i < lineCount; i++) {
                    if (layout.getLineWidth(i) > maxLineWidth) {
                        maxLineWidth = layout.getLineWidth(i);
                    }
                }
            }

            if ((high - low) < precision) {
                return low;
            } else if (maxLineWidth > targetWidth) {
                return binarySearchTextSize(text, paint, targetWidth, maxLines, low, mid, precision,
                        metrics, iterations + 1);
            } else if (maxLineWidth < targetWidth) {
                return binarySearchTextSize(text, paint, targetWidth, maxLines, mid, high, precision,
                        metrics, iterations + 1);
            } else {
                return mid;
            }
        }
    }

    private static int getLineCount(CharSequence text, TextPaint paint, float size, float width,
            DisplayMetrics metrics) {
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, metrics));
        StaticLayout layout = new StaticLayout(text, paint, (int) width,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return layout.getLineCount();
    }
}
