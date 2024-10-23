package Service;

import java.util.List;
import DAO.*;
import Model.*;

public class SocialMediaService {
    AccountDAO accountDAO;
    MessageDAO messageDAO;

    public SocialMediaService() {
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }

    // 
    public Account createNewAccount(Account acc) {
        String user = acc.getUsername();
        String pass = acc.getPassword();

        if (user.length() < 1 || pass.length() < 4) {
            return null;
        }
        if (accountDAO.checkUsernameExists(user)) {
            return null;
        }
        return accountDAO.insertNewAccount(user, pass);
    }

    public Account login(Account acc) {
        return accountDAO.getAccountByUserPass(acc.getUsername(), acc.getPassword());
    }

    public Message createNewMessage(Message msg) {
        String msgText = msg.getMessage_text();
        if (msgText.length() < 1 || msgText.length() > 255) {
            return null;
        }
        if (!accountDAO.checkAccountIdExists(msg.getPosted_by())) {
            return null;
        }
        return messageDAO.insertNewMessage(msg.getPosted_by(), msgText, msg.getTime_posted_epoch());
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message updatMessageById(int msgId, String msgText) {
        if (msgText.length() < 1 || msgText.length() > 255) {
            return null;
        }
        return messageDAO.updateMessageById(msgId, msgText);
    }

    public List<Message> getallMessagesPostedByAccountId(int id) {
        return messageDAO.getAllMessagesPostedByAccountId(id);
    }
}
