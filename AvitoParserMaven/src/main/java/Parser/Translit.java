package Parser;

class Translit {
    static final char[] RU = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '-', ' '};
    static final String[] EN = {"a", "b", "v", "g", "d", "e", "o", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "y", "", "e", "yu", "ya", "-", "_"};

    public static String getTranslit(String str) {
        char[] ch = str.toCharArray();
        StringBuilder trans = new StringBuilder();
        for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < RU.length; j++) {
                if (ch[i] == RU[j]) {
                    trans = trans.append(EN[j]);
                }
            }
        }
        str = trans.toString();
        return str;
    }
}