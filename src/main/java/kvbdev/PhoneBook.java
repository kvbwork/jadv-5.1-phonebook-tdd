package kvbdev;

import java.util.HashMap;
import java.util.Map;

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

    public int add(String name, String phone) {
        if (name == null || name.isEmpty()) return contactNameIndexMap.size();
        if (phone == null || phone.isEmpty()) return contactNameIndexMap.size();

        Contact newContact = new Contact(name, phone);
        contactNameIndexMap.putIfAbsent(name, newContact);

        return contactNameIndexMap.size();
    }
}
