package com.company.utils.telegram_appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class TelegramAppender extends AppenderBase<LoggingEvent> {
    private static final String botToken = "5687797236:AAGBbO9ZdZ5eApH11J_6zUknG_qIEK7478s";
    private static final String chatID = "5174610361";
    private final TelegramBot telegramBot = new TelegramBot(botToken);

    public TelegramAppender() {
        addFilter(new Filter<>() {
            @Override
            public FilterReply decide(LoggingEvent loggingEvent) {
                return loggingEvent.getLevel()
                        .isGreaterOrEqual(Level.WARN) ? FilterReply.ACCEPT : FilterReply.DENY;

            }
        });
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        Level logLevel = loggingEvent.getLevel();
        String logMessage = loggingEvent.toString();
//        if(logLevel.equals(Level.INFO)) {
//            logMessage="ℹ"+logMessage;
//        }
        if(logLevel.equals(Level.WARN)) {
            logMessage="⚠"+logMessage;
        }
        if(logLevel.equals(Level.ERROR)) {
            logMessage="❌"+logMessage;
        }
        SendMessage sendMessage = new SendMessage(chatID, logMessage);
        telegramBot.execute(sendMessage);
    }
}
