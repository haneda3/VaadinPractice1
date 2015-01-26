package com.haneda3;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Created by haneda on 2015/01/26.
 */
public class VaadinPractice1 extends UI {

    Navigator navigator;

    public static String MAINVIEW = "main";

    public class LoginView extends VerticalLayout implements View {
        public LoginView() {
            setSizeFull();

            Button button = new Button("Login",
                    new Button.ClickListener() {
                        @Override
                        public void buttonClick(ClickEvent clickEvent) {
                            navigator.navigateTo(MAINVIEW);
                        }
                    }
            );
            addComponent(button);
            setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        }

        @Override
        public void enter(ViewChangeEvent viewChangeEvent) {
            Notification.show("LoginView enter");
        }
    }

    public class NonePanel extends VerticalLayout {
        public NonePanel() {
        }
    }

    public class BookPanel extends VerticalLayout {
        public BookPanel() {
            this.addComponent(
                    new Label("Nothing to see here, " +
                            "just pass along."));
        }
    }

    public class UserPanel extends VerticalLayout {
        public UserPanel() {
            this.addComponent(
                    new Label("userList"));
        }
    }

    public class MainView extends VerticalLayout implements View {
        Panel panel;

        class ButtonListener implements Button.ClickListener {
//            private static final long serialVersionUID = -4941184695301907995L;

            String menuitem;
            public ButtonListener(String menuitem) {
                this.menuitem = menuitem;
            }

            @Override
            public void buttonClick(ClickEvent event) {
                // Navigate to a specific state
                navigator.navigateTo(MAINVIEW + "/" + menuitem);
            }
        }

        public MainView() {
            setSizeFull();

            HorizontalLayout hLayout = new HorizontalLayout();
            hLayout.setSizeFull();

            Panel menu = new Panel("menu");
            menu.setHeight("100%");
            menu.setWidth(null);

            VerticalLayout menuContent = new VerticalLayout();
            menuContent.addComponent(new Button("書籍", new ButtonListener("book")));
            menuContent.addComponent(new Button("ユーザー", new ButtonListener("user")));

            menuContent.setWidth(null);
            menuContent.setMargin(true);
            menu.setContent(menuContent);
            hLayout.addComponent(menu);

            panel = new Panel("panel");
            panel.setSizeFull();
            hLayout.addComponent(panel);
            hLayout.setExpandRatio(panel, 1.0f);
            addComponent(hLayout);
            setExpandRatio(hLayout, 1.0f);
        }

        @Override
        public void enter(ViewChangeEvent event) {
            String param = event.getParameters();
            if (param == null) param = "";

            VerticalLayout layout = null;
            switch (param) {
                case "book":
                    layout = new BookPanel();
                    break;
                case "user":
                    layout = new UserPanel();
                    break;
                default:
                    layout = new NonePanel();
                    break;
            }
            layout.setSizeFull();
            layout.setMargin(true);
            panel.setContent(layout);
        }
    }

    @Override
    public void init(VaadinRequest request) {
        getPage().setTitle("Page/URI Control Practice");

        navigator = new Navigator(this, this);

        navigator.addView("", new LoginView());
        navigator.addView(MAINVIEW, new MainView());
    }
}
