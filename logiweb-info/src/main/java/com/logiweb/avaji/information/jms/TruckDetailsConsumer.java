package com.logiweb.avaji.information.jms;

import com.logiweb.avaji.model.Details;
import com.logiweb.avaji.model.TruckDetails;
import com.logiweb.avaji.observer.Observer;
import com.logiweb.avaji.observer.Subject;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;

@MessageDriven(name = "truckDetailsConsumer",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/truck")
        })
public class TruckDetailsConsumer implements MessageListener, Subject {

    private final int ID = 3;
    private List<Observer> observers = new ArrayList<>();
    private Details details;

    @Override
    public void onMessage(Message message) {
        try {
            details = message.getBody(TruckDetails.class);
            if(details != null) {
                notifyObservers();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(this, details);
        }
    }

    @Override
    public int getSubjectIdentity() {
        return ID;
    }
}
