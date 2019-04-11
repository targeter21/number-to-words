public class Interview {
    public static void main(String[] args) {
        NumberConverter numberConverter = new NumberConverterImpl();

        System.out.println(numberConverter.convertNumberToWords("100004000"));
    }
}
