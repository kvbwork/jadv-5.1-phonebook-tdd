package kvbdev;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PhoneBook {

    protected static class Contact {
        private final String name;
        private final String phone;

        public Contact(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }

    private final Map<String, Contact> contactNameIndexMap = new HashMap<>();
    private final Map<String, Contact> contactPhoneIndexMap = new HashMap<>();

    public int add(String name, String phone) {
        if (name == null || name.isEmpty()) return contactNameIndexMap.size();
        if (phone == null || phone.isEmpty()) return contactNameIndexMap.size();
        if (contactNameIndexMap.containsKey(name)) return contactNameIndexMap.size();

        Contact newContact = new Contact(name, phone);
        contactNameIndexMap.put(name, newContact);
        contactPhoneIndexMap.put(phone, newContact);

        return contactNameIndexMap.size();
    }

    public String findByNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return "";
        return Optional.ofNullable(contactPhoneIndexMap.get(phoneNumber))
                .map(Contact::getName)
                .orElse("");
    }

    public String findByName(String name) {
        if (name == null || name.isEmpty()) return "";
        return Optional.ofNullable(contactNameIndexMap.get(name))
                .map(Contact::getPhone)
                .orElse("");
    }

    public void printAllNames() {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
