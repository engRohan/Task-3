import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ContactManagement {

    class Contact {
        private String name;
        private String phoneNumber;
        private String email;

        public Contact(String name, String phoneNumber, String email) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    class ContactManagementSystem {
        private static final String FILE_NAME = "contacts.txt";
        private List<Contact> contacts = new ArrayList<>();

        public void main(String[] args) {
            ContactManagementSystem cms = new ContactManagementSystem();
            cms.loadContacts();
            cms.showMenu();
        }

        private void showMenu() {
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\nContact Management System");
                System.out.println("1. Add Contact");
                System.out.println("2. View Contacts");
                System.out.println("3. Edit Contact");
                System.out.println("4. Delete Contact");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addContact(scanner);
                        break;
                    case 2:
                        viewContacts();
                        break;
                    case 3:
                        editContact(scanner);
                        break;
                    case 4:
                        deleteContact(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);

            saveContacts();
            scanner.close();
        }

        private void addContact(Scanner scanner) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter email address: ");
            String email = scanner.nextLine();

            contacts.add(new Contact(name, phoneNumber, email));
            System.out.println("Contact added successfully!");
        }

        private void viewContacts() {
            if (contacts.isEmpty()) {
                System.out.println("No contacts available.");
                return;
            }

            System.out.println("\nContact List:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }

        private void editContact(Scanner scanner) {
            System.out.print("Enter the name of the contact to edit: ");
            String name = scanner.nextLine();

            for (Contact contact : contacts) {
                if (contact.getName().equalsIgnoreCase(name)) {
                    System.out.print("Enter new phone number: ");
                    contact.setPhoneNumber(scanner.nextLine());
                    System.out.print("Enter new email address: ");
                    contact.setEmail(scanner.nextLine());
                    System.out.println("Contact updated successfully!");
                    return;
                }
            }
            System.out.println("Contact not found.");
        }

        private void deleteContact(Scanner scanner) {
            System.out.print("Enter the name of the contact to delete: ");
            String name = scanner.nextLine();

            Iterator<Contact> iterator = contacts.iterator();
            boolean found = false;

            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getName().equalsIgnoreCase(name)) {
                    iterator.remove();
                    found = true;
                    System.out.println("Contact deleted successfully!");
                    break;
                }
            }

            if (!found) {
                System.out.println("Contact not found.");
            }
        }

        private void loadContacts() {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        contacts.add(new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading contacts: " + e.getMessage());
            }
        }

        private void saveContacts() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Contact contact : contacts) {
                    writer.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving contacts: " + e.getMessage());
            }
        }
    }
}


