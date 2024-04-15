
package communicationmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Subscriber {

    private int s_id;
    private String name;
    private int age;
    private boolean isActive;
    private ServiceProvider s_provider;
    private Invoice invoice;
    private static ArrayList<Subscriber> subscribersList;
    private static ArrayList<ServiceProvider> serviceProvidersList = new ArrayList<ServiceProvider>();
    private int totalVoiceCall;
    private int totalNumMessages;
    private int totalInternetUsage;
    private static double totalSpending;

    public Subscriber(String name, int age, ServiceProvider s_provider, double usageLimit, Date lastDayToPay) {
        Random rand = new Random();
        this.name = name;
        this.age = age;
        this.s_provider = s_provider;
        this.isActive = true;
        this.invoice = new Invoice(usageLimit, lastDayToPay);
        subscribersList = new ArrayList<>();
        serviceProvidersList = new ArrayList<>();
        this.s_id = rand.nextInt(8000001) + 1000000;
        this.totalVoiceCall = 0;
        this.totalNumMessages = 0;
        this.totalInternetUsage = 0;
    }

    public void updateStatus() {
        Date currentDate = new Date(); // get current date
        Date lastDayToPay = invoice.getLastDayToPay(); // get last day to pay from invoice
        if (currentDate.getTime() > lastDayToPay.getTime()) {
            isActive = false;
        } else {
            isActive = true; //aboneyi oluşturduğumuz zaman otomatikmen true yaptığımız için aslında buna gerek yok
        }

    }

    public void makeVoiceCall(int minute, Subscriber receiver, Subscriber caller) {
        updateStatus();
        if (!caller.isActive || !receiver.isActive) {
            System.out.println("Cannot make voice call: Subscriber is inactive.");
        } else if (minute < 0) {
            System.out.println("Cannot make voice call: Invalid input.");
        } else {
            double cost = this.s_provider.calculateVoiceCallCost(minute, caller);
            if (caller.getInvoice().isLimitExceeded(cost)) {
                System.out.println("Cannot make voice call current spending exceeds usage limit.");
            } else {
                caller.invoice.addCost(cost);
                totalSpending += cost;
                caller.totalVoiceCall += minute;
            }
        }
    }

    public void sendMessage(int quantity, Subscriber sender, Subscriber receiver) {
        updateStatus();
        if (!sender.isActive || !receiver.isActive) {
            System.out.println("Cannot send message: Subscriber is inactive.");
        } else if (quantity < 0) {
            System.out.println("Cannot send message invalid input ");
        } else {
            double cost = sender.s_provider.calculateMessagingCost(quantity, sender, receiver);
            if (sender.getInvoice().isLimitExceeded(cost)) {
                System.out.println("Cannot send messages current spending exceeds usage limit.");
            } else {
                sender.invoice.addCost(cost);
                totalSpending += cost;
                sender.totalNumMessages += quantity;

            }
        }
    }

    public void connectToInternet(double amount, Subscriber s) {
        updateStatus();
        if (!s.isActive) {
            System.out.println("Cannot connect to internet: Subscriber is inactive.");
        } else if (amount < 0) {
            System.out.println("Cannot connect to internet invalid input ");
        } else {
            double internetCost = s.s_provider.calculateInternetCost(amount, s);

            if (s.getInvoice().isLimitExceeded(internetCost)) {
                System.out.println("Cannot connect to Internet current spending exceeds usage limit.");
            } else {
                s.invoice.addCost(internetCost);
                s.totalSpending += internetCost;
                s.totalInternetUsage += amount;
            }
        }
    }

    public void changeServiceProvider(ServiceProvider provider) {
        if (this.getInvoice().getCurrentSpending() == 0) { // değişim yapabilmek için şart faturanın ödenmiş olmalı
            if (s_provider == null) { // öncesinde provider boşsa direkt atanır eklenir
                s_provider = provider;
                s_provider.addSubscriber(this);
                System.out.println("Service provider changed successfully to " + s_provider.getP_name());
            } else if (s_provider != provider) { // daha öncesinde bir providera sahipse ve alınan değerle birbirinden farklıysa önce eski olan kaldırılır daha sonra yeni olan eklenir
                String old_provider = s_provider.getP_name();
                s_provider.removeSubscriber(this);
                s_provider = provider;
                s_provider.addSubscriber(this);
                System.out.println("Service provider changed successfully from " + old_provider + " to " + s_provider.getP_name());
                this.getInvoice().setLastDayToPay(); //fatura tarihi sıfırlandı
            } else { // daha öncesinde provider varsa ve bu girilen değerle aynı ise zaten aynı provider olduğunu döndürür
                System.out.println("Subscriber is already subscribed to " + s_provider.getP_name());
            }
        } else { // ödeme yapılmamışsa herhangi bi değişiklik olmaz
            System.out.println("Cannot change service provider due to unpaid invoice.");
        }
    }

    public boolean isTeenagers(Subscriber s) {
        if (s.getAge() >= 10 && s.getAge() <= 18) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isElderly(Subscriber s) {
        return age >= 65;
    }

    public int getS_id() {
        return s_id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ServiceProvider getS_provider() {
        return s_provider;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public boolean addServiceProvider(ServiceProvider s) {
        if (!serviceProvidersList.contains(s)) {
            if (serviceProvidersList.add(s)) {
                s.addSubscriber(this);
                return true;
            }
        }
        return false;
    }

    public boolean dropServiceProvider(ServiceProvider s) {
        if (serviceProvidersList.remove(s)) {
            s.removeSubscriber(this);
            return true;
        }
        return false;
    }

    public int getTotalVoiceCallTime() {
        return totalVoiceCall;
    }

    public int getTotalNumMessages() {
        return totalNumMessages;
    }

    public double getTotalInternetUsage() {
        return totalInternetUsage;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

}
