import java.io.*;
import java.util.*;
public class passwordmanager{
    private static final String PASSWORD_FILE = "passwords.txt";
    private static Map<String, String[]> passwordMap = new HashMap<>(); 
    public static void main(String[] args){
        loadPasswordsFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean running=true;
        while(running){
            System.out.println();
            System.out.println("1. Add Website, Username, and Password");
            System.out.println("2. Retrieve Username and Password for Website");
            System.out.println("3. List All Websites");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            switch(choice){
                case 1:
                    addWebsiteUsernameAndPassword(scanner);
                    break;
                case 2:
                    retrieveUsernameAndPassword(scanner);
                    break;
                case 3:
                    listWebsites();
                    break;
                case 4:
                    running = false;
                    savePasswordsToFile();
                    System.out.println("Exiting Password Manager. Your passwords have been saved.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        // scanner.close();
    }
    private static void addWebsiteUsernameAndPassword(Scanner scanner){
        System.out.print("Enter the website: ");
        String website = scanner.nextLine();
        System.out.print("Enter the username: ");
        String username = scanner.nextLine();
        System.out.print("Enter the password: ");
        String password = scanner.nextLine();
        
        for(String[] credentials:passwordMap.values()){
            if(credentials[1].equals(password)){
                System.out.println("Password already exists, cannot add duplicate passwords.");
                return;
            }
        }

        String[] credentials = {username, password};
        passwordMap.put(website, credentials);
        System.out.println("Credentials added for " + website);
    }
    private static void retrieveUsernameAndPassword(Scanner scanner) {
        System.out.print("Enter the website: ");
        String website = scanner.nextLine();
        if(passwordMap.containsKey(website)){
            String[] credentials = passwordMap.get(website);
            System.out.println("Username for " + website + ": " + credentials[0]);
            System.out.println("Password for " + website + ": " + credentials[1]);
        } 
        else{
            System.out.println("Website not found in password manager.");
        }
    }
    private static void listWebsites(){
        System.out.println("List of Websites:");
        for(String website : passwordMap.keySet()){
            System.out.println("- " + website);
        }
    }
    private static void loadPasswordsFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(":");
                if(parts.length == 3){
                    String website=parts[0];
                    String username=parts[1];
                    String password=parts[2];
                    String[] credentials={username, password};
                    passwordMap.put(website,credentials);
                }
            }
        } 
        catch (IOException e){
            System.out.println("Error loading passwords: " + e.getMessage());
        }
    }
    private static void savePasswordsToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(PASSWORD_FILE))){
            for(Map.Entry<String, String[]> entry : passwordMap.entrySet()){
                String website=entry.getKey();
                String username=entry.getValue()[0];
                String password=entry.getValue()[1];
                writer.write(website + ":" + username + ":" + password);
                writer.newLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error saving passwords: " + e.getMessage());
        }
    }
}
