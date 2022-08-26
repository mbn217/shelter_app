package com.ziola.shelter.security;

import com.ziola.shelter.workers.Worker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;
@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Worker worker;

    public OnRegistrationCompleteEvent(Worker workerExists, Locale locale, String appUrl) {
        super(workerExists);
        this.worker = workerExists;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
