package by.gradomski.apartments.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandProvider {
    private static final Logger log = LogManager.getLogger();

    public static Optional<Command> defineCommand(String commandName){
        if(commandName == null || commandName.isBlank()){
            log.error("command is empty or null");
            return Optional.empty();
        }
        Optional<Command> optionalCommand;
        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            optionalCommand = Optional.of(type.getCommand());
        } catch (IllegalArgumentException e){
            optionalCommand = Optional.empty();
        }
        return optionalCommand;
    }
}
