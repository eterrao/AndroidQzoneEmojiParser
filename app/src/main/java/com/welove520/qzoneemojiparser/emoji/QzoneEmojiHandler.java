package com.welove520.qzoneemojiparser.emoji;

import android.content.Context;
import android.text.Spannable;

import java.util.ArrayList;
import java.util.List;

import static com.welove520.qzoneemojiparser.emoji.EmojiHandler.SpanRangeList.SPAN_NOT_FOUND;


final class QzoneEmojiHandler {
    private QzoneEmojiHandler() {
        throw new AssertionError("No instances.");
    }

    static void addEmojis(final Context context, final Spannable text, final int emojiSize) {
        final SpanRangeList existingSpanRanges = new SpanRangeList(text);
        final EmojiManager emojiManager = EmojiManager.getInstance();
        int index = 0;

        while (index < text.length()) {
            final int existingSpanEnd = existingSpanRanges.spanEnd(index);

            if (existingSpanEnd == SPAN_NOT_FOUND) {
                final int nextSpanStart = existingSpanRanges.nextSpanStart(index);
                final int searchRange = nextSpanStart == SPAN_NOT_FOUND ? text.length() : nextSpanStart;
                final Emoji found = emojiManager.findEmoji(text.subSequence(index, searchRange));

                if (found != null) {
                    text.setSpan(new QzoneEmojiSpan(context, found.getEmojiId(), emojiSize), index, index + found.getLength(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    index += found.getLength();
                } else {
                    index++;
                }
            } else {
                index += existingSpanEnd - index;
            }
        }
    }

    static final class SpanRangeList {
        static final int SPAN_NOT_FOUND = -1;

        private final List<Range> spanRanges = new ArrayList<>();

        SpanRangeList(final Spannable text) {
            for (final QzoneEmojiSpan span : text.getSpans(0, text.length(), QzoneEmojiSpan.class)) {
                spanRanges.add(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        int spanEnd(final int index) {
            for (final Range spanRange : spanRanges) {
                if (spanRange.start == index) {
                    return spanRange.end;
                }
            }

            return SPAN_NOT_FOUND;
        }

        int nextSpanStart(final int index) {
            for (final Range spanRange : spanRanges) {
                if (spanRange.start > index) {
                    return spanRange.start;
                }
            }

            return SPAN_NOT_FOUND;
        }
    }

    static final class Range {
        final int start;
        final int end;

        Range(final int start, final int end) {
            this.start = start;
            this.end = end;
        }
    }
}
