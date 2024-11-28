package agents.cb;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ingescape.*;

public class JavaTest2CB implements IopListener, ServiceListener {
	private static Logger _logger = LoggerFactory.getLogger(JavaTest2CB.class);

	public JavaTest2CB() {
	}

	@Override
	public void handleIOP(Agent agent, Iop iop, String name, IopType type, Object value) {
		_logger.debug("**received input {} with type {} and value {}", name, type, value);

		if (iop == Iop.IGS_INPUT_T && type == IopType.IGS_DATA_T) {
			byte[] data = (byte[])value;
			Path pathToFile = Paths.get("/Users/aurelien/Desktop/mydata2.png");
			try {
				Files.write(pathToFile, data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void handleCallToService(Agent agent, String senderAgentName, String senderAgentUUID,
									String serviceName, List<Object> arguments, String token) {
		_logger.debug("**received service call from {} ({}): {} (with token {})", senderAgentName, senderAgentUUID, serviceName, arguments, token);
	}
}