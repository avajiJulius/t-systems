package com.logiweb.avaji.information;

import com.logiweb.avaji.information.jms.DriverDetailsConsumer;
import com.logiweb.avaji.information.jms.InitializeSender;
import com.logiweb.avaji.information.jms.OrderDetailsConsumer;
import com.logiweb.avaji.information.jms.TruckDetailsConsumer;
import com.logiweb.avaji.model.Details;
import com.logiweb.avaji.observer.Observer;
import com.logiweb.avaji.observer.Subject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InformationManager implements Observer {

    private InformationTableView informationTableView;
    private InitializeSender initSender;
    private OrderDetailsConsumer orderConsumer;
    private TruckDetailsConsumer truckConsumer;
    private DriverDetailsConsumer driverConsumer;

    @Inject
    public InformationManager(InformationTableView informationTableView, InitializeSender initSender,
                              OrderDetailsConsumer orderConsumer, TruckDetailsConsumer truckConsumer,
                              DriverDetailsConsumer driverConsumer) {
        this.informationTableView = informationTableView;
        this.initSender = initSender;
        this.orderConsumer = orderConsumer;
        this.truckConsumer = truckConsumer;
        this.driverConsumer = driverConsumer;
    }

    @PostConstruct
    private void initialize() {
        orderConsumer.registerObserver(this);
        truckConsumer.registerObserver(this);
        driverConsumer.registerObserver(this);
        initSender.send();
    }

    @Override
    public void update(Subject subject, Details details) {
        switch (subject.getSubjectIdentity()) {
            case (1) :
                informationTableView.setOrderDetails(details);
                break;
            case (2) :
                informationTableView.setDriverDetails(details);
                break;
            case (3) :
                informationTableView.setTruckDetails(details);
                break;
            default :
                throw new UnsupportedOperationException();
        }
    }

}
