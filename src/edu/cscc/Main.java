package edu.cscc;

import edu.cscc.framework.MVCContext;
import edu.cscc.framework.Request;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        MVCContext context = new MVCContext();

        try {
            context.processRequest(new Request("Home", "index"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
