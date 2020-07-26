package by.gradomski.apartments.controller;

import by.gradomski.apartments.command.PagePath;

public class Router {
    enum TransitionType {
        FORWARD,
        REDIRECT
    }

    private String page = PagePath.SIGN_IN;
    private TransitionType transitionType = TransitionType.FORWARD;

    public String getPage(){
        return page;
    }

    public void setPage(String page){
        this.page = page;
    }

    public TransitionType getTransitionType(){
        return transitionType;
    }

    public void setRedirect(){
        this.transitionType = TransitionType.REDIRECT;
    }

    public void setForward(){
        this.transitionType = TransitionType.FORWARD;
    }
}
