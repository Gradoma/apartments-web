package by.gradomski.apartments.command;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command};
}
