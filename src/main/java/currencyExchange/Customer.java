package currencyExchange;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    int id;
    String name;
    String surname;
    String email;
    String password;
    String address;
    int phoneNumber;
}
