package aero.delco;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyril on 22/05/2015.
 */
public class DelCoParser {

    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List codes = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "annuaire");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the code tag
            if (name.equals("code")) {
                codes.add(readCode(parser));
            } else {
                skip(parser);
            }
        }
        return codes;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private DelayCode readCode(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "code");
        String category = null;
        String number = null;
        String content = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("category")) {
                category = readCategory(parser);
            } else if (name.equals("number")) {
                number = readNumber(parser);
            } else if (name.equals("content")) {
                content = readContent(parser);
            } else {
                skip(parser);
            }
        }

        return new DelayCode(category, number, content);
    }

    // Processes category tags in the feed.
    private String readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "category");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "category");
        return title;
    }

    // Processes number tags in the feed.
    private String readNumber(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "number");
        String number = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "number");
        return number;
    }

    // Processes content tags in the feed.
    private String readContent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "content");
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "content");
        return content;
    }

    // For the tags category, number and content, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
