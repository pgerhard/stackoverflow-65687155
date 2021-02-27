# Example Vaadin 7.3.6 Application for Stackoverflow Question 65687155

This is an example Vaadin 7.3.6 Application providing a solution to the issue described in the [Stackoverflow Question 65687155 - Not update button title when start countdown](https://stackoverflow.com/questions/65687155/not-update-button-title-when-start-countdown)
The project was generated using the Vaadin Maven Archetype using the below command

```shell
mvn archetype:generate \
   -DarchetypeGroupId=com.vaadin \
   -DarchetypeArtifactId=vaadin-archetype-application \
   -DarchetypeVersion=7.3.6 \
   -DgroupId=io.github.pgerhard \
   -DartifactId=stackoverflow-65687155 \
   -Dversion=0.0.1-SNAPSHOT \
   -Dpackaging=war
```

The only changes made were

1. Added annotation `@Push` to `MyVaadinUI`
2. Implemented the timer logic and customized UI slightly
```java
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
```
3. Built project using `mvn clean install`
4. Started Jetty server using `mvn jetty:run`

Information on local environment
* Java Version: `AdoptOpenJDK 1.8 282 HotSpot`
* OS: Mac OS 10.15.7
* Servlet Container: Jetty provided in Maven pom.xml (version 9.2.3.v20140905 according to log output)