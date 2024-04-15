
package communicationmanagement;

import java.util.ArrayList;
import java.util.Random;

class ServiceProvider {

    private int p_id;
    private String p_name;
    private double voiceCallCost;
    private double messagingCost;
    private double internetCost;
    private int discountRatio;
    private ArrayList<Subscriber> subscribersList;
    private int totalVoiceCall;
    private int totalNumMessages;
    private int totalInternetUsage;

    public ServiceProvider(String p_name, double voiceCallCost, double messagingCost, double internetCost, int discountRatio) {
        Random rand = new Random();
        this.p_id = rand.nextInt(101) + 500; // generate a random number between 500 and 600

        this.p_name = p_name;
        this.voiceCallCost = voiceCallCost;
        this.messagingCost = messagingCost;
        this.internetCost = internetCost;
        this.discountRatio = discountRatio;
        this.subscribersList = new ArrayList<Subscriber>();
        this.totalVoiceCall = 0;
        this.totalNumMessages = 0;
        this.totalInternetUsage = 0;
    }

    // arayan tarafından başlatılan bir sesli arama için ödenecek toplam tutarı hesaplamak için .
    public double calculateVoiceCallCost(int minute, Subscriber caller) {
        this.totalVoiceCall += minute;
        if (caller.isTeenagers(caller)) {
            double totalCost = ((minute - 5) * voiceCallCost); //first 5 min free
            double result = totalCost - ((totalCost * discountRatio) / 100);
            return result;
        } else if (caller.isElderly(caller)) {
            double totalCost = (minute * voiceCallCost);
            double result = totalCost - ((totalCost * discountRatio) / 100);
            return result;
        } else {
            return (minute * voiceCallCost);
        }
    }

    //gönderici aboneden alıcı aboneye mesaj göndermek için ödenecek toplam tutarı hesaplamak için
    public double calculateMessagingCost(int quantity, Subscriber sender, Subscriber receiver) {
        this.totalNumMessages += quantity;
        if (sender.getS_provider() == receiver.getS_provider()) {
            if (sender.isTeenagers(sender)) {
                double totalCost = ((quantity - 10) * messagingCost);
                double result = totalCost - ((totalCost * discountRatio) / 100);
                return result;
            } else {
                double totalCost = (quantity * messagingCost);
                double result = totalCost - ((totalCost * discountRatio) / 100);
                return result;
            }
        } else {
            double totalCost = (quantity * messagingCost);
            return totalCost;
        }

    }

    //İnternet kullanımı için ödenecek toplam tutarı hesaplamak için  
    public double calculateInternetCost(double amount, Subscriber s) {
        this.totalInternetUsage += amount;
        if (s.isTeenagers(s)) {
            double totalCost = ((amount - 5) * internetCost);
            return totalCost;
        } else {
            return (amount * internetCost);
        }

    }

    public boolean addSubscriber(Subscriber s) {
        if (!subscribersList.contains(s)) {
            subscribersList.add(s);
            s.addServiceProvider(this);
            return true;
        }
        return false;
    }

    public boolean removeSubscriber(Subscriber s) {
        if (subscribersList.remove(s)) {
            s.dropServiceProvider(this);
            return true;
        }
        return false;
    }

    public int getP_id() {
        return p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public double getVoiceCallCost() {
        return voiceCallCost;
    }

    public double getMessagingCost() {
        return messagingCost;
    }

    public double getInternetCost() {
        return internetCost;
    }

    public int getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(int discountRatio) {
        this.discountRatio = discountRatio;
    }

    public double getTotalVoiceCallTime() {
        return totalVoiceCall;
    }

    public int getTotalNumMessages() {
        return totalNumMessages;
    }

    public double getTotalInternetUsage() {
        return totalInternetUsage;
    }
}
