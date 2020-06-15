package by.gradomski.apartments.entity;

import by.gradomski.apartments.exception.IncorrectRoleException;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN(1),
    USER(2);

    private int value;

    Role(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static Role getRoleByValue(int value) throws IncorrectRoleException{
        Role[] roles = Role.values();
        Optional<Role> optionalRole = Arrays.stream(roles).filter(r -> r.getValue() == value).findFirst();
        if (optionalRole.isEmpty()){
            throw new IncorrectRoleException(value + " type not present in GemType enum");
        }
        return optionalRole.get();
    }
}
