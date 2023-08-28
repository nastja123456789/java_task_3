import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarNumberValidator {
    private static final String[] SUPPORTED_REGIONS = {"Moscow", "Saint Petersburg", "Krasnodar"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Введите регистрационный номер автомобиля (для выхода введите &#x27;exit&#x27;): ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            input=input.replaceAll(" ","");
            ValidationResult validationResult = validateCarNumber(input);
            if (validationResult.isValid()) {
                System.out.println("Регистрационный номер автомобиля введен корректно.");
                String region = getRegionFromCarNumber(input);
                if (region != null) {
                    System.out.println("Регион: " + region);
                } else {
                    System.out.println("Информация о регионе отсутствует.");
                }
            } else {
                System.out.println("Ошибка: " + validationResult.getError());
            }

            System.out.println();
        }

        System.out.println("Программа завершена.");
        scanner.close();
    }

    private static ValidationResult validateCarNumber(String carNumber) {
        String regex = "^[A-ZА-Я]\\d{3}[A-ZА-Я]{2}\\d{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(carNumber);

        if (!matcher.matches()) {
            return new ValidationResult(false, "Некорректный формат номерного знака.");
        }

        String regionCode = carNumber.substring(carNumber.length() - 3);
        if (!isValidRegionCode(regionCode)) {
            return new ValidationResult(false, "Недопустимый регион номерного знака.");
        }

        return new ValidationResult(true, null);
    }

    private static boolean isValidRegionCode(String code) {
        // Допустимые регионные коды
        String[] validCodes = {"777", "178", "123"};

        for (String validCode : validCodes) {
            if (validCode.equals(code)) {
                return true;
            }
        }

        return false;
    }

    private static String getRegionFromCarNumber(String carNumber) {
        String regionCode = carNumber.substring(carNumber.length() - 3);

        if (regionCode.equals("777")) {
            return "Moscow";
        } else if (regionCode.equals("178")) {
            return "Saint Petersburg";
        } else if (regionCode.equals("123")) {
            return "Krasnodar";
        }

        return null;
    }

    private static class ValidationResult {
        private final boolean valid;
        private final String error;

        public ValidationResult(boolean valid, String error) {
            this.valid = valid;
            this.error = error;
        }

        public boolean isValid() {
            return valid;
        }

        public String getError() {
            return error;
        }
    }
}
