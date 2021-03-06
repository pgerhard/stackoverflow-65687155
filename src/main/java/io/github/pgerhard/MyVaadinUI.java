package io.github.pgerhard;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.Timer;
import java.util.TimerTask;

@Push
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "io.github.pgerhard.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        final Timer timer = new Timer();
        final Button button = new Button("Start Countdown");
        final Label countDownLabel = new Label("Count Down Running ");
        countDownLabel.setVisible(false);

        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                countDownLabel.setVisible(true);
                layout.addComponent(countDownLabel);
                button.setCaption("10");
                button.setEnabled(false);

                timer.scheduleAtFixedRate(new TimerTask() {
                    int secondsLeft = 10;

                    @Override
                    public void run() {
                        System.out.printf("Seconds left: %d%n", secondsLeft);

                        if (secondsLeft < 0) {
                            timer.cancel();
                            UI.getCurrent().access(new Runnable() {
                                @Override
                                public void run() {
                                    button.setCaption("Start Countdown");
                                    countDownLabel.setVisible(false);
                                    button.setEnabled(true);
                                }
                            });
                        } else {
                            UI.getCurrent().access(new Runnable() {
                                @Override
                                public void run() {
                                    button.setCaption(String.valueOf(secondsLeft));
                                }
                            });
                        }

                        secondsLeft--;
                    }
                }, 0, 1000);
            }
        });
        layout.addComponent(button);
        layout.addComponent(countDownLabel);
    }

}
