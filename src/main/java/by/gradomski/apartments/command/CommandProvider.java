package by.gradomski.apartments.command;

import java.util.Optional;

public class CommandProvider {
    public static Optional<Command> defineCommand(String commandName){
        if(commandName == null || commandName.isBlank()){
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
