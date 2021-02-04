package com.logiweb.avaji.observer;

import com.logiweb.avaji.model.Details;

public interface Observer {
    void update(Subject subject, Details details);
}
