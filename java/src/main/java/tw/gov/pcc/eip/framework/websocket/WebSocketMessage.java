package tw.gov.pcc.eip.framework.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebSocketMessage {

    private String message;

    public WebSocketMessage(String message) {
        this.message = message;
    }

}
