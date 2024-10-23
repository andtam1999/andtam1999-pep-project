package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.*;
import Service.*;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService smService;

    public SocialMediaController() {
        smService = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messagePostHandler);
        app.get("/messages", this::messageGetHandler);
        app.get("/messages/{message_id}", this::messageGetByIdHandler);
        app.delete("/messages/{message_id}", this::messageDeleteHandler);
        app.patch("/messages/{message_id}", this::messagePatchHandler);
        app.get("/accounts/{account_id}/messages", this::messageGetByAccountIdHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        Account accToCreate = objMapper.readValue(context.body(), Account.class);
        Account createdAcc = smService.createNewAccount(accToCreate);
        if (createdAcc != null) {
            context.json(objMapper.writeValueAsString(createdAcc));
        }
        else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        Account tryLoginAcccount = objMapper.readValue(context.body(), Account.class);
        Account loggedInAccount = smService.login(tryLoginAcccount);
        if (loggedInAccount != null) {
            context.json(objMapper.writeValueAsString(loggedInAccount));
        }
        else {
            context.status(401);
        }
    }

    private void messagePostHandler(Context context) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        Message msgToCreate = objMapper.readValue(context.body(), Message.class);
        Message createdMsg = smService.createNewMessage(msgToCreate);
        if (createdMsg != null) {
            context.json(objMapper.writeValueAsString(createdMsg));
        }
        else {
            context.status(400);
        }
    }

    private void messageGetHandler(Context context) {
        context.json(smService.getAllMessages());
    }


    private void messageGetByIdHandler(Context context) {
        Message retrievedMessage = smService.getMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (retrievedMessage != null) {
            context.json(retrievedMessage);
        }
        else {
            context.status(200);
        }
    }

    private void messageDeleteHandler(Context context) {
        Message deletedMessage = smService.deleteMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (deletedMessage != null) {
            context.json(deletedMessage);
        }
        else {
            context.status(200);
        }
    }

    private void messagePatchHandler(Context context) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        String msgText = objMapper.readValue(context.body(), Message.class).getMessage_text();
        Message updatedMsg = smService.updatMessageById(Integer.parseInt(context.pathParam("message_id")), msgText);
        if (updatedMsg != null) {
            context.json(objMapper.writeValueAsString(updatedMsg));
        }
        else {
            context.status(400);
        }
    }

    private void messageGetByAccountIdHandler(Context context) {
        context.json(smService.getallMessagesPostedByAccountId(Integer.parseInt(context.pathParam("account_id"))));
    }
}