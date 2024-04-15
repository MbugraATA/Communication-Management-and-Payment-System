
package communicationmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SimulateSystem {

    private static ArrayList<Subscriber> subscribersList;
    private static ArrayList<ServiceProvider> serviceProvidersList;

    public static void main(String[] args) {

        subscribersList = new ArrayList<>();
        serviceProvidersList = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("-----------------------------");
            printMenu();
            System.out.println("-----------------------------");
            System.out.println("Please select an option: ");
            option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.println("1- Creating a new Service Provider...");
                    createNewServiceProvider();
                    break;
                case 2:
                    System.out.println("2- Creating a new Subscriber...");
                    createNewSubscriber();
                    break;
                case 3:
                    System.out.println("Voice Call...");
                    voiceCall();
                    break;

                case 4:
                    System.out.println("Messaging...");
                    messaging();
                    break;
                case 5:
                    System.out.println("Internet...");

                    connectionToInternet();
                    break;
                case 6:
                    System.out.println("Pay Invoice...");
                    payInvoice();
                    break;
                case 7:
                    System.out.println("Changing Service Provider...");
                    changeServiceProvider();
                    break;
                case 8:
                    System.out.println("Changing Limit...");
                    changeUsageLimit();
                    break;
                case 9:
                    System.out.println("List all Subscribers...");
                    listAllSubscribers();
                    break;
                case 10:
                    System.out.println("List all Service Providers...");
                    listAllServiceProviders();
                    break;
                case 11:
                    System.out.println("Exit...");
                    showIstatistic();
                    break;
                default:
                    System.out.println("Please select an option between 1 and 11.");
                    break;
            }
        } while (option != 11);
    }

    public static void printMenu() {
        System.out.println("1- Creating a new Service Provider\n"
                + "2- Create a new Subscriber\n"
                + "3- Voice Call: A subscriber calls another subscriber\n"
                + "4- Messaging: A subscriber sends a message to another subscriber\n"
                + "5- Internet: A subscriber connects to the Internet\n"
                + "6- Pay Invoice: A subscriber pays his/her invoice\n"
                + "7- Change ServiceProvider: A subscriber changes his/her provider\n"
                + "8- Change Limit: A subscriber changes his/her usage limit for the Invoice\n"
                + "9- List all Subscribers (show s_id, isActive, s_provider, invoice)\n"
                + "10- List all Service Providers (show p_id, p_name, all costs, and discount)\n"
                + "11- Exit");
    }

    public static void createNewServiceProvider() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter voice call cost: ");
        double voiceCallCost = scanner.nextDouble();
        System.out.print("Enter messaging cost: ");
        double messagingCost = scanner.nextDouble();
        System.out.print("Enter internet cost: ");
        double internetCost = scanner.nextDouble();
        System.out.print("Enter discount ratio: ");
        int discountRatio = scanner.nextInt();

        ServiceProvider serviceProvider = new ServiceProvider(name, voiceCallCost, messagingCost, internetCost, discountRatio);
        serviceProvidersList.add(serviceProvider);
        System.out.println("Service Provider created.." + serviceProvider.getP_id());

    }

    public static void createNewSubscriber() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        System.out.print("Enter provider ID: ");
        int providerId = scanner.nextInt();
        System.out.print("Enter usage limit: ");
        double usageLimit = scanner.nextDouble();
        Date lastDayToPay = new Date();

        ServiceProvider selectedProvider = null;
        for (ServiceProvider provider : serviceProvidersList) {
            if (provider.getP_id() == providerId) {
                selectedProvider = provider;
                break;
            }
        }

        if (selectedProvider == null) {
            System.out.println("Invalid provider id.");
            return;
        }

        Subscriber subscriber = new Subscriber(name, age, selectedProvider, usageLimit, lastDayToPay);
        subscribersList.add(subscriber);
        System.out.println("Subscriber created successfully with id " + subscriber.getS_id());
    }

    public static void voiceCall() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter caller's ID:");
        int callerId = scanner.nextInt();
        System.out.println("Enter receiver's ID:");
        int receiverId = scanner.nextInt();
        System.out.println("Enter call duration in minutes:");
        int minute = scanner.nextInt();

        //Girilen caller id'm ile abonemi buluyorum
        //Başta abonemin ne olduğu bilmiyorum boş.
        Subscriber caller = null;
        Subscriber receiver = null;
        for (int i = 0; i < subscribersList.size(); i++) {
            if (callerId == subscribersList.get(i).getS_id()) {
                caller = subscribersList.get(i);
                break;
            }
        }
        //Girilen receiver id'm ile abonemi buluyorum
        for (int i = 0; i < subscribersList.size(); i++) {
            if (receiverId == subscribersList.get(i).getS_id()) {
                receiver = subscribersList.get(i);
                break;
            }
        }

        //Eğer girilen idlerde bir abone yoksa yanlış döndürüyorum
        if (caller == null || receiver == null) {
            System.out.println("Invalid subscriber ID. Please try again...");
        }

        //Bulduğum abonelerle konuşma yapıyorum.
        caller.makeVoiceCall(minute, receiver, caller);
        //Both caller and receiver will be charged based on their service providers' costs.
        receiver.makeVoiceCall(minute, caller, receiver);

    }

    public static void messaging() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter sender's s_id: ");
        int sender_id = scanner.nextInt();
        System.out.print("Enter receiver's s_id: ");
        int receiver_id = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        //Girilen  sender_id'm ile abonemi buluyorum
        //Başta abonemin ne olduğu bilmiyorum boş.
        Subscriber sender = null;
        Subscriber receiver = null;
        for (int i = 0; i < subscribersList.size(); i++) {
            if (sender_id == subscribersList.get(i).getS_id()) {
                sender = subscribersList.get(i);
                break;
            }
        }
        //Girilen receiver id'm ile abonemi buluyorum
        for (int i = 0; i < subscribersList.size(); i++) {
            if (receiver_id == subscribersList.get(i).getS_id()) {
                receiver = subscribersList.get(i);
                break;
            }
        }

        //Eğer girilen idlerde bir abone yoksa yanlış döndürüyorum
        if (sender == null || receiver == null) {
            System.out.println("Invalid subscriber ID. Please try again...");
        }

        //Bulduğum abonelerle mesaj atıyorum.
        sender.sendMessage(quantity, sender, receiver);

    }

    public static void connectionToInternet() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter s_id: ");
        int s_id = scanner.nextInt();
        System.out.print("Enter amount in MBs: ");
        int amountInMBs = scanner.nextInt();

        //Girilen caller id'm ile abonemi buluyorum
        //Başta abonemin ne olduğu bilmiyorum boş.
        Subscriber user = null;
        for (int i = 0; i < subscribersList.size(); i++) {
            if (s_id == subscribersList.get(i).getS_id()) {
                user = subscribersList.get(i);
                break;
            }
        }

        //Eğer girilen idlerde bir abone yoksa yanlış döndürüyorum
        if (user == null) {
            System.out.println("Invalid subscriber ID. Please try again...");
        }

        //Bulduğum abone ile internete bağlanıyorum
        user.connectToInternet(amountInMBs, user);
    }

    public static void payInvoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter s_id: ");
        int s_id = scanner.nextInt();

        //Girilen caller id'm ile abonemi buluyorum
        //Başta abonemin ne olduğu bilmiyorum boş.
        Subscriber user = null;
        for (int i = 0; i < subscribersList.size(); i++) {
            if (s_id == subscribersList.get(i).getS_id()) {
                user = subscribersList.get(i);
                break;
            }
        }

        //Eğer girilen idlerde bir abone yoksa yanlış döndürüyorum
        if (user == null) {
            System.out.println("Invalid subscriber ID. Please try again...");
        }

        System.out.println("invoice payable: " + user.getInvoice().getCurrentSpending());

        //ödeme yapacağım miktarı alıyorum
        System.out.print("Enter amount of money to pay: ");
        double amount = scanner.nextDouble();

        //Bulduğum abone ile ödeme yapıyorum
        user.getInvoice().pay(amount);
        System.out.println(amount + " paid...");
    }

    public static void changeServiceProvider() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter s_id: ");
        int s_id = scanner.nextInt();
        System.out.print("Enter new p_id: ");
        int p_id = scanner.nextInt();

        Subscriber subscriber = null;
        for (int i = 0; i < subscribersList.size(); i++) {
            if (s_id == subscribersList.get(i).getS_id()) {
                subscriber = subscribersList.get(i);
                break;
            }
        }

        ServiceProvider provider = null;
        for (int i = 0; i < serviceProvidersList.size(); i++) {
            if (p_id == serviceProvidersList.get(i).getP_id()) {
                provider = serviceProvidersList.get(i);
                break;
            }
        }

        if (subscriber == null) {
            System.out.println("Invalid subscriber ID.");
            return;
        }
        if (provider == null) {
            System.out.println("Invalid provider ID.");
            return;
        }

        subscriber.changeServiceProvider(provider);

    }

    public static void changeUsageLimit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter s_id: ");
        int s_id = scanner.nextInt();
        System.out.print("Enter new limit: ");
        double newLimit = scanner.nextDouble();
        while (newLimit < 0) {
            System.out.println("Limit cannot be negative enter limit again: ");
            newLimit = scanner.nextDouble();
        }

        Subscriber caller = null;
        for (int i = 0; i < subscribersList.size(); i++) {
            if (s_id == subscribersList.get(i).getS_id()) {
                caller = subscribersList.get(i);
                break;
            }
        }

        if (caller == null) {
            System.out.println("Invalid subscriber ID.");
            return;
        }

        caller.getInvoice().changeUsageLimit(newLimit);
        System.out.println("new limit " + newLimit);
    }

    public static void listAllSubscribers() {
        System.out.println("List of all subscribers:");
        for (int i = 0; i < subscribersList.size(); i++) {
            System.out.println("Id: " + subscribersList.get(i).getS_id()
                    + " Active: " + subscribersList.get(i).isIsActive()
                    + " Provider: " + subscribersList.get(i).getS_provider().getP_id()
                    + " Invoice: " + subscribersList.get(i).getInvoice().getUsageLimit()
            );
        }
    }

    public static void listAllServiceProviders() {
        System.out.println("List of all service providers:");
        for (int i = 0; i < serviceProvidersList.size(); i++) {
            System.out.println("ID: " + serviceProvidersList.get(i).getP_id()
                    + " Name: " + serviceProvidersList.get(i).getP_name()
                    + " Voice Call Cost: " + serviceProvidersList.get(i).getVoiceCallCost()
                    + " Messaging Cost: " + serviceProvidersList.get(i).getMessagingCost()
                    + " Internet Cost: " + serviceProvidersList.get(i).getInternetCost()
                    + " Discount: " + serviceProvidersList.get(i).getDiscountRatio());
        }
    }

    public static void showIstatistic() {
        //her serviceProviderın yaptığı toplam konuşma mesajlaşma ve internet kullanımını bulduk
        for (ServiceProvider sp : serviceProvidersList) {
            double totalVoiceCallTime = sp.getTotalVoiceCallTime();
            int totalNumMessages = sp.getTotalNumMessages();
            double totalInternetUsage = sp.getTotalInternetUsage();

            System.out.println(sp.getP_id() + " : Total Voice Call Time: " + totalVoiceCallTime + " Total Number Of Messages " + totalNumMessages + " Total Internet Usage: " + totalInternetUsage);
        }

        //her kullanıcının toplam ve current harcaması
        for (int i = 0; i < subscribersList.size(); i++) {
            System.out.println("<" + subscribersList.get(i).getS_id() + "> : Total Spending: " + subscribersList.get(i).getTotalSpending() + " Current Spending " + subscribersList.get(i).getInvoice().getCurrentSpending());
        }

        // En çok telefonla konuşma süresi olan kullanıcıyı bulduk
        Subscriber subscriberWithMostVoiceCallTime = null;
        int maxVoiceCallTime = 0;
        for (int i = 0; i < subscribersList.size(); i++) {
            int totalVoiceCallTime = subscribersList.get(i).getTotalVoiceCallTime();
            if (totalVoiceCallTime > maxVoiceCallTime) {
                subscriberWithMostVoiceCallTime = subscribersList.get(i);
                maxVoiceCallTime = totalVoiceCallTime;
            }
        }
        System.out.println("The subscriber had the most voice call time: " + subscriberWithMostVoiceCallTime.getS_id() + " " + subscriberWithMostVoiceCallTime.getName() + " " + maxVoiceCallTime);

        // En çok mesaj atan kullanıcıyı bulduk
        Subscriber subscriberWithMostMessages = null;
        int maxNumMessages = 0;
        for (int i = 0; i < subscribersList.size(); i++) {
            int totalNumMessages = subscribersList.get(i).getTotalNumMessages();
            if (totalNumMessages > maxNumMessages) {
                subscriberWithMostMessages = subscribersList.get(i);
                maxNumMessages = totalNumMessages;
            }
        }
        System.out.println("The subscriber sent the most number of messages: " + subscriberWithMostMessages.getS_id() + " " + subscriberWithMostMessages.getName() + " " + maxNumMessages);

        // En çok internet kullanımı yapan kullanıcıyı bulduk
        Subscriber subscriberWithMostInternetUsage = null;
        double maxInternetUsage = 0;
        for (int i = 0; i < subscribersList.size(); i++) {
            double totalInternetUsage = subscribersList.get(i).getTotalInternetUsage();
            if (totalInternetUsage > maxInternetUsage) {
                subscriberWithMostInternetUsage = subscribersList.get(i);
                maxInternetUsage = totalInternetUsage;
            }
        }
        System.out.println("The subscriber got the connected the internet: " + subscriberWithMostInternetUsage.getS_id() + " " + subscriberWithMostInternetUsage.getName() + " " + maxInternetUsage);
    }
} 