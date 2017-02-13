package salasa.websocket;

import javax.enterprise.context.ApplicationScoped;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PushEndpoint("/{user}")
@ApplicationScoped
public class NewMessageSocket {

	private final Logger logger = LoggerFactory.getLogger(MessageSocket.class);

	@PathParam("user")
	private String username;

	@OnOpen
	public void onOpen(RemoteEndpoint r) {
		logger.info("OnOpen {}", r);	
	}

	@OnClose
	public void onClose(RemoteEndpoint r) {
		logger.info("OnClose {}", r);
	}
	
    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String message) {
        return message;
    }
}
