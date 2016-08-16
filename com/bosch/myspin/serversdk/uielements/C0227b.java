package com.bosch.myspin.serversdk.uielements;

import java.util.HashMap;
import java.util.Locale;

/* renamed from: com.bosch.myspin.serversdk.uielements.b */
public final class C0227b {

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.a */
    public static final class C0220a implements C0219a {
        private static final String[] f366a;
        private static final String[] f367b;
        private static final String[] f368c;
        private static final String[] f369d;
        private static HashMap<String, String> f370e;

        static {
            f366a = new String[]{"q w e\u00e9\u00e8\u00ea\u00eb r t y\u00ff u\u00fc\u00fa\u00f9\u00fb i\u00ec\u00ed\u00ee\u00ef o\u00f6\u00f3\u00f2\u00f4\u00f5\u00f8\u0153 p", "a\u00e4\u00e1\u00e0\u00e2\u00e5\u00e2\u00e3\u00e5\u00e6 s\u00df\u0161\u015f d f g h j k l", "*shift z x c\u00e7 v b n\u00f1 m *del", "*123 *lang - *space .:;,?! *enter"};
            f367b = new String[]{"Q W E\u00c9\u00c8\u00ca\u00cb R T Y\u0178 U\u00dc\u00da\u00d9\u00db I O P", "A\u00c4\u00c1\u00c0\u00c2\u00c5\u00c2\u00c3\u00c5\u00c6 S\u0160\u015e D F G H J K L", "*shift Z X C\u00c7 V B N\u00d1 M *del", "*abc *lang - *space .:;,?! *enter"};
            f368c = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f369d = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return new Locale("nl");
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f366a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f368c;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f369d;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f367b;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f370e == null) {
                f370e = new HashMap();
                f370e.put("keyboard_space", "spatie");
                f370e.put("keyboard_done", "Gereed");
                f370e.put("keyboard_go", "Gereed");
                f370e.put("keyboard_next", "verder");
                f370e.put("keyboard_prev", "terug");
                f370e.put("keyboard_search", "Zoek");
                f370e.put("keyboard_ok", "OK");
                f370e.put("keyboard_abc", "ABC");
            }
            return f370e;
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.b */
    public static final class C0221b implements C0219a {
        private static final String[] f371a;
        private static final String[] f372b;
        private static final String[] f373c;
        private static final String[] f374d;
        private static HashMap<String, String> f375e;

        static {
            f371a = new String[]{"q w e\u00e9\u00e8\u00ea\u00eb r t y\u00ff u\u00fc\u00fa\u00f9\u00fb i\u00ec\u00ed\u00ee\u00ef o\u00f6\u00f3\u00f2\u00f4\u00f5\u00f8\u0153 p", "a\u00e4\u00e1\u00e0\u00e2\u00e5\u00e2\u00e3\u00e5\u00e6 s\u00df\u0161\u015f d f g h j k l", "*shift z x c\u00e7 v b n\u00f1 m *del", "*123 *lang - *space .:;,?! *enter"};
            f372b = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f373c = new String[]{"Q W E\u00c9\u00c8\u00ca\u00cb R T Y\u0178 U\u00dc\u00da\u00d9\u00db I\u00cc\u00cd\u00ce\u00cf O\u00d6\u00d3\u00d2\u00d4\u00d5\u00d8\u0152 P", "A\u00c4\u00c1\u00c0\u00c2\u00c5\u00c2\u00c3\u00c5\u00c6 S\u0160\u015e D F G H J K L", "*shift Z X C\u00c7 V B N\u00d1 M *del", "*123 *lang - *space .:;,?! *enter"};
            f374d = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return Locale.ENGLISH;
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f371a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f374d;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f372b;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f373c;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f375e == null) {
                f375e = new HashMap();
                f375e.put("keyboard_space", "space");
                f375e.put("keyboard_done", "done");
                f375e.put("keyboard_go", "go");
                f375e.put("keyboard_next", "next");
                f375e.put("keyboard_prev", "prev");
                f375e.put("keyboard_search", "search");
                f375e.put("keyboard_ok", "OK");
                f375e.put("keyboard_abc", "ABC");
            }
            return f375e;
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.c */
    public static final class C0222c implements C0219a {
        private static final String[] f376a;
        private static final String[] f377b;
        private static final String[] f378c;
        private static final String[] f379d;
        private static HashMap<String, String> f380e;

        static {
            f376a = new String[]{"a\u00e4\u00e1\u00e0\u00e2\u00e5\u00e2\u00e3\u00e5\u00e6 z e\u00e9\u00e8\u00ea\u00eb r t y\u00ff u\u00fc\u00fa\u00f9\u00fb i\u00ec\u00ed\u00ee\u00ef o\u00f6\u00f3\u00f2\u00f4\u00f5\u00f8\u0153 p", "q s\u00df\u0161\u015f d f g h j k l m", "*shift w x c\u00e7 v b n\u00f1 *del", "*123 *lang - *space .:;,?! *enter"};
            f377b = new String[]{"A\u00c4\u00c1\u00c0\u00c2\u00c5\u00c2\u00c3\u00c5\u00c6 Z E\u00c9\u00c8\u00ca\u00cb R T Y\u0178 U\u00dc\u00da\u00d9\u00db I\u00cc\u00cd\u00ce\u00cf O\u00d6\u00d3\u00d2\u00d4\u00d5\u00d8\u0152 P", "Q S\u0160\u015e D F G H J K L M", "*shift W X C\u00c7 V B N\u00d1 *del", "*123 *lang - *space .:;,?! *enter"};
            f378c = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f379d = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return Locale.FRENCH;
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f376a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f378c;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f379d;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f377b;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f380e == null) {
                f380e = new HashMap();
                f380e.put("keyboard_space", "Espace");
                f380e.put("keyboard_done", "OK");
                f380e.put("keyboard_go", "OK");
                f380e.put("keyboard_next", "Suivant");
                f380e.put("keyboard_prev", "Pr\u00e9c");
                f380e.put("keyboard_search", "Rechercher");
                f380e.put("keyboard_ok", "OK");
                f380e.put("keyboard_abc", "ABC");
            }
            return f380e;
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.d */
    public static final class C0223d implements C0219a {
        private static final String[] f381a;
        private static final String[] f382b;
        private static final String[] f383c;
        private static final String[] f384d;
        private static HashMap<String, String> f385e;

        static {
            f381a = new String[]{"q w e\u00e9\u00e8\u00ea\u00eb r t z u\u00fc\u00fa\u00f9\u00fb i\u00ec\u00ed\u00ee\u00ef o\u00f6\u00f3\u00f2\u00f4\u00f5\u00f8\u0153 p", "a\u00e4\u00e1\u00e0\u00e2\u00e5\u00e2\u00e3\u00e5\u00e6 s\u00df\u0161\u015f d f g h j k l", "*shift y\u00ff x c\u00e7 v b n\u00f1 m *del", "*123 *lang - *space .:;,?! *enter"};
            f382b = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f383c = new String[]{"Q W E\u00c9\u00c8\u00ca\u00cb R T Z U\u00dc\u00da\u00d9\u00db I\u00cc\u00cd\u00ce\u00cf O\u00d6\u00d3\u00d2\u00d4\u00d5\u00d8\u0152 P", "A\u00c4\u00c1\u00c0\u00c2\u00c5\u00c2\u00c3\u00c5\u00c6 S\u0160\u015e D F G H J K L", "*shift Y\u0178 X C\u00c7 V B N\u00d1 M *del", "*123 *lang - *space .:;,?! *enter"};
            f384d = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return Locale.GERMAN;
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f381a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f384d;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f382b;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f383c;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f385e == null) {
                f385e = new HashMap();
                f385e.put("keyboard_space", "Leerzeichen");
                f385e.put("keyboard_done", "Fertig");
                f385e.put("keyboard_go", "Los");
                f385e.put("keyboard_next", "Weiter");
                f385e.put("keyboard_prev", "Zur\u00fcck");
                f385e.put("keyboard_search", "Suchen");
                f385e.put("keyboard_ok", "OK");
                f385e.put("keyboard_abc", "ABC");
            }
            return f385e;
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.e */
    public static final class C0224e implements C0219a {
        private static final String[] f386a;
        private static final String[] f387b;
        private static final String[] f388c;
        private static final String[] f389d;
        private static HashMap<String, String> f390e;

        static {
            f386a = new String[]{"q w e\u00e9\u00e8\u00ea\u00eb r t y\u00ff u\u00fc\u00fa\u00f9\u00fb i\u00ec\u00ed\u00ee\u00ef o\u00f6\u00f3\u00f2\u00f4\u00f5\u00f8\u0153 p", "a\u00e4\u00e1\u00e0\u00e2\u00e5\u00e2\u00e3\u00e5\u00e6 s\u00df\u0161\u015f d f g h j k l", "*shift z x c\u00e7 v b n\u00f1 m *del", "*123 *lang - *space .:;,?! *enter"};
            f387b = new String[]{"Q W E\u00c9\u00c8\u00ca\u00cb R T Y\u0178 U\u00dc\u00da\u00d9\u00db I O P", "A\u00c4\u00c1\u00c0\u00c2\u00c5\u00c2\u00c3\u00c5\u00c6 S\u0160\u015e D F G H J K L", "*shift Z X C\u00c7 V B N\u00d1 M *del", "*abc *lang - *space .:;,?! *enter"};
            f388c = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f389d = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return new Locale("pt");
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f386a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f388c;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f389d;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f387b;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f390e == null) {
                f390e = new HashMap();
                f390e.put("keyboard_space", "espa\u00e7o");
                f390e.put("keyboard_done", "enter");
                f390e.put("keyboard_go", "enter");
                f390e.put("keyboard_next", "enter");
                f390e.put("keyboard_prev", "enter");
                f390e.put("keyboard_search", "pesquisar");
                f390e.put("keyboard_ok", "enter");
                f390e.put("keyboard_abc", "ABC");
            }
            return f390e;
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.f */
    public static final class C0225f implements C0219a {
        private static final String[] f391a;
        private static final String[] f392b;
        private static final String[] f393c;
        private static final String[] f394d;
        private static HashMap<String, String> f395e;

        static {
            f391a = new String[]{"\u0439 \u0446 \u0443 \u043a \u0435\u0451 \u043d \u0433 \u0448 \u0449 \u0437 \u0445", "\u0444 \u044b \u0432 \u0430 \u043f \u0440 \u043e \u043b \u0434 \u0436 \u044d", "*shift \u044f \u0447 \u0441 \u043c \u0438 \u0442 \u044c\u044a \u0431 \u044e *del", "*123 *lang - *space .:;,?! *enter"};
            f392b = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f393c = new String[]{"\u0419 \u0426 \u0423 \u041a \u0415\u0401 \u041d \u0413 \u0428 \u0429 \u0417 \u0425", "\u0424 \u042b \u0412 \u0410 \u041f \u0420 \u041e \u041b \u0414 \u0416 \u042d", "*shift \u042f \u0427 \u0421 \u041c \u0418 \u0422 \u042c\u042a \u0411 \u042e *del", "*123 *lang - *space .:;,?! *enter"};
            f394d = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return new Locale("ru");
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f391a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f394d;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f392b;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f393c;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f395e == null) {
                f395e = new HashMap();
                f395e.put("keyboard_space", "\u041f\u0440\u043e\u0431\u0435\u043b");
                f395e.put("keyboard_done", "\u0412\u0432\u043e\u0434");
                f395e.put("keyboard_go", "\u0412\u0432\u043e\u0434");
                f395e.put("keyboard_next", "\u0412\u0432\u043e\u0434");
                f395e.put("keyboard_prev", "\u041d\u0430\u0437\u0430\u0434");
                f395e.put("keyboard_search", "\u041d\u0430\u0439\u0442\u0438");
                f395e.put("keyboard_ok", "\u0412\u0432\u043e\u0434");
                f395e.put("keyboard_abc", "\u0410\u0411\u0412");
            }
            return f395e;
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.uielements.b.g */
    public static final class C0226g implements C0219a {
        private static final String[] f396a;
        private static final String[] f397b;
        private static final String[] f398c;
        private static final String[] f399d;
        private static HashMap<String, String> f400e;

        static {
            f396a = new String[]{"q w e\u00e9\u00e8\u00ea\u00eb r t y\u00ff u\u00fc\u00fa\u00f9\u00fb i\u00ec\u00ed\u00ee\u00ef o\u00f6\u00f3\u00f2\u00f4\u00f5\u00f8\u0153 p", "a\u00e4\u00e1\u00e0\u00e2\u00e5\u00e2\u00e3\u00e5\u00e6 s\u00df\u0161\u015f d f g h j k l", "*shift z x c\u00e7 v b n\u00f1 m *del", "*123 *lang - *space .:;,?! *enter"};
            f397b = new String[]{"Q W E\u00c9\u00c8\u00ca\u00cb R T Y\u0178 U\u00dc\u00da\u00d9\u00db I\u00cc\u00cd\u00ce\u00cf O\u00d6\u00d3\u00d2\u00d4\u00d5\u00d8\u0152 P", "A\u00c4\u00c1\u00c0\u00c2\u00c5\u00c2\u00c3\u00c5\u00c6 S\u0160\u015e D F G H J K L", "*shift Z X C\u00c7 V B N\u00d1 M *del", "*abc *lang - *space .:;,?! *enter"};
            f398c = new String[]{"~ # \\ | ^ [ ] { } %", "_ * / + = < > $ \u00a3 \u00a5", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
            f399d = new String[]{"1 2 3 4 5 6 7 8 9 0", "_ * / + = ( ) \u20ac & @", "*123alt : ; , ? ! ' \" *del", "*abc *lang - *space . *enter"};
        }

        public Locale getLocale() {
            return new Locale("es");
        }

        public String[] getStringArrayKeyboardLayoutMain() {
            return f396a;
        }

        public String[] getStringArrayKeyboardLayoutAlt() {
            return f398c;
        }

        public String[] getStringArrayKeyboardLayoutDigits() {
            return f399d;
        }

        public String[] getStringArrayKeyboardLayoutShift() {
            return f397b;
        }

        public HashMap<String, String> getSpecialKeysDictionary() {
            if (f400e == null) {
                f400e = new HashMap();
                f400e.put("keyboard_space", "Espacio");
                f400e.put("keyboard_done", "Ir");
                f400e.put("keyboard_go", "Listo");
                f400e.put("keyboard_next", "Siguiente");
                f400e.put("keyboard_prev", "Anterior");
                f400e.put("keyboard_search", "Buscar");
                f400e.put("keyboard_ok", "Aceptar");
                f400e.put("keyboard_abc", "ABC");
            }
            return f400e;
        }
    }
}
