package currencyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    Integer id;
    String name;
    String surname;
    String email;
    String password;
    String address;
    Integer phoneNumber;
}
