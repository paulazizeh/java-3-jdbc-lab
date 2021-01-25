package edu.cscc;


import edu.cscc.framework.ApplicationController;
import edu.cscc.framework.MVCContext;

public class HomeController extends ApplicationController {
    public HomeController(MVCContext context) {
        super(context);
    }
    public void index() {
        render(new HomeIndex(context));
    }

}