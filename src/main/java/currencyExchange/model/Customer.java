package currencyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String address;
    private Integer phoneNumber;
}
