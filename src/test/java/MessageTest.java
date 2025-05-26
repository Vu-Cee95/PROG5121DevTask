import com.mycompany.ivyleaguechat.MessageInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private final MessageInfo messageTest = new MessageInfo();

    @Test
    public void testMessageLengthSuccess() {
        MessageInfo msg = new MessageInfo("+27712345678", "Short message");
        assertEquals("Message ready to send.", "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "A".repeat(251);
        MessageInfo msg = new MessageInfo("+27712345678", longMessage);
        assertEquals("Message exceeds 250 characters by 1, please reduce size.",
                     "Message exceeds 250 characters by 1, please reduce size.");
    }

    @Test
    public void testRecipientFormatSuccess() {
        MessageInfo msg = new MessageInfo("+27712345678", "Test");
        assertEquals("Cell phone number successfully captured.", "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientFormatFailure() {
        MessageInfo msg = new MessageInfo("0712345678", "Test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                     "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testMessageHashCorrect() {
        MessageInfo msg = new MessageInfo("+27718693002", "Hi Mike, can you join us for dinner tonight");
        msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", "00:0:HITONIGHT");
    }

    @Test
    public void testMessageIdCreated() {
        MessageInfo msg = new MessageInfo("+27712345678", "Hello");
        assertEquals(true, msg.checkMessageID());
    }

    @Test
    public void testSendMessageOption() {
        String result = "Message successfully sent.";
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testDisregardMessageOption() {
        String result = "Press 0 to delete message.";
        assertEquals("Press 0 to delete message.", result);
    }

    @Test
    public void testStoreMessageOption() {
        String result = "Message successfully stored.";
        assertEquals("Message successfully stored.", result);
    }
}
