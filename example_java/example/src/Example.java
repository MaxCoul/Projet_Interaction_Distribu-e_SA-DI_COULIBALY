import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ch.qos.logback.classic.*;
//import ch.qos.logback.core.util.StatusPrinter;

import com.ingescape.*;
import agents.cb.*;


public class Example implements AgentEventListener, WebSocketEventListener {

	private static Logger _logger = LoggerFactory.getLogger(Example.class);

	public Example() {

		//LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		//StatusPrinter.print(lc);
	}

	@Override
	public void handleAgentEvent(Agent agent, AgentEvent event, String uuid, String name, Object eventData) {
		_logger.debug("**received agent event for {} ({}): {} with data {}", name, uuid, event, eventData);
	}

	@Override
	public void handleWebSocketEvent(WebSocketEvent event, Throwable t) {
		if (t != null) { // (event == WebSocketEvent.IGS_WEB_SOCKET_FAILED)
			_logger.error("**received web socket event {} with exception {}", event, t.toString());
		}
		else {
			_logger.debug("**received web socket event {}", event);
		}
	}


	public static void main(String[] args) throws InterruptedException {

        _logger.info("Start Java app 'IngeScape agent test'");
        _logger.info("is DEBUG enabled ? {}", _logger.isDebugEnabled());

		Global globalContext = new Global("ws://localhost:9009");
		//Global globalContext = new Global("ws://10.0.0.35:5003");
        //Global globalContext = new Global("ws://192.168.1.18:5000");

		Example example = new Example();
		globalContext.observeWebSocketEvents(example);

		JavaTest1CB javaTest1CB = new JavaTest1CB();

		Agent a = globalContext.agentCreate("javaTest1");
		a.observeAgentEvents(example);

		a.definition.setName("javaTest1");
		a.definition.setDescription("java test agent");
		a.definition.setVersion("1.0");

		a.definition.inputCreate("myInt1", IopType.IGS_INTEGER_T);
		a.definition.inputCreate("myDouble1", IopType.IGS_DOUBLE_T);
		a.definition.inputCreate("myString1", IopType.IGS_STRING_T);
		a.definition.inputCreate("myBool1", IopType.IGS_BOOL_T);
		a.definition.inputCreate("myImpulsion1", IopType.IGS_IMPULSION_T);
		a.definition.inputCreate("myData1", IopType.IGS_DATA_T);
		a.definition.outputCreate("myInt1", IopType.IGS_INTEGER_T);
		a.definition.outputCreate("myDouble1", IopType.IGS_DOUBLE_T);
		a.definition.outputCreate("myString1", IopType.IGS_STRING_T);
		a.definition.outputCreate("myBool1", IopType.IGS_BOOL_T);
		a.definition.outputCreate("myImpulsion1", IopType.IGS_IMPULSION_T);
		a.definition.outputCreate("myData1", IopType.IGS_DATA_T);
		a.definition.parameterCreate("myInt1", IopType.IGS_INTEGER_T);
		a.definition.parameterCreate("myDouble1", IopType.IGS_DOUBLE_T);
		a.definition.parameterCreate("myString1", IopType.IGS_STRING_T);
		a.definition.parameterCreate("myBool1", IopType.IGS_BOOL_T);
		a.definition.parameterCreate("myImpulsion1", IopType.IGS_IMPULSION_T);
		a.definition.parameterCreate("myData1", IopType.IGS_DATA_T);
		a.observeInput("myInt1", javaTest1CB);
		a.observeInput("myDouble1", javaTest1CB);
		a.observeInput("myString1", javaTest1CB);
		a.observeInput("myBool1", javaTest1CB);
		a.observeInput("myImpulsion1", javaTest1CB);
		a.observeInput("myData1", javaTest1CB);

		a.serviceInit("javaCall1", javaTest1CB);
		a.serviceArgAdd("javaCall1", "myInt", IopType.IGS_INTEGER_T);
		a.serviceArgAdd("javaCall1", "myDouble", IopType.IGS_DOUBLE_T);
		a.serviceArgAdd("javaCall1", "myBool", IopType.IGS_BOOL_T);
		a.serviceArgAdd("javaCall1", "myString", IopType.IGS_STRING_T);
		a.serviceArgAdd("javaCall1", "myData", IopType.IGS_DATA_T);


		JavaTest2CB javaTest2CB = new JavaTest2CB();
		Agent a2 = globalContext.agentCreate("javaTest2");

		a2.definition.setName("javaTest2");
		a2.definition.setDescription("java test agent 2");
		a2.definition.setVersion("1.0");

		a2.definition.inputCreate("myInt2", IopType.IGS_INTEGER_T);
		a2.definition.inputCreate("myDouble2", IopType.IGS_DOUBLE_T);
		a2.definition.inputCreate("myString2", IopType.IGS_STRING_T);
		a2.definition.inputCreate("myBool2", IopType.IGS_BOOL_T);
		a2.definition.inputCreate("myImpulsion2", IopType.IGS_IMPULSION_T);
		a2.definition.inputCreate("myData2", IopType.IGS_DATA_T);
		a2.definition.outputCreate("myInt2", IopType.IGS_INTEGER_T);
		a2.definition.outputCreate("myDouble2", IopType.IGS_DOUBLE_T);
		a2.definition.outputCreate("myString2", IopType.IGS_STRING_T);
		a2.definition.outputCreate("myBool2", IopType.IGS_BOOL_T);
		a2.definition.outputCreate("myImpulsion2", IopType.IGS_IMPULSION_T);
		a2.definition.outputCreate("myData2", IopType.IGS_DATA_T);
		a2.definition.parameterCreate("myInt2", IopType.IGS_INTEGER_T);
		a2.definition.parameterCreate("myString2", IopType.IGS_STRING_T);
		a2.definition.parameterCreate("myBool2", IopType.IGS_BOOL_T);
		a2.definition.parameterCreate("myImpulsion2", IopType.IGS_IMPULSION_T);
		a2.definition.parameterCreate("myData2", IopType.IGS_DATA_T);
		a2.observeInput("myInt2", javaTest2CB);
		a2.observeInput("myDouble2", javaTest2CB);
		a2.observeInput("myString2", javaTest2CB);
		a2.observeInput("myBool2", javaTest2CB);
		a2.observeInput("myImpulsion2", javaTest2CB);
		a2.observeInput("myData2", javaTest2CB);

		a2.serviceInit("javaCall2", javaTest2CB);
		a2.serviceArgAdd("javaCall2", "myInt", IopType.IGS_INTEGER_T);
		a2.serviceArgAdd("javaCall2", "myDouble", IopType.IGS_DOUBLE_T);
		a2.serviceArgAdd("javaCall2", "myBool", IopType.IGS_BOOL_T);
		a2.serviceArgAdd("javaCall2", "myString", IopType.IGS_STRING_T);
		a2.serviceArgAdd("javaCall2", "myData", IopType.IGS_DATA_T);

		a2.start();

		//a.mapping.add("myInt", "iosAgent", "myInt");
		a.mapping.add("myDouble1", "macosAgent", "value1");
		//a.mapping.add("myString", "iosAgent", "myString");
		//a.mapping.add("myBool", "iosAgent", "myBool");
		//a.mapping.add("myImpulsion", "iosAgent", "myImpulsion");
		//a.mapping.add("myData", "iosAgent", "myData");

		a.start();

		//outputs
		Thread.sleep(3000);
		a.outputSetInt("myInt1", 5);
		a.outputSetDouble("myDouble1", 6.6);
		a.outputSetString("myString1", "test string");
		a.outputSetBool("myBool1", true);
		a.outputSetImpulsion("myImpulsion1");
		byte[] myBytes = "java test for byte array".getBytes();
		a.outputSetData("myData1", myBytes);

		a2.outputSetInt("myInt2", 5);
		a2.outputSetDouble("myDouble2", 6.6);
		a2.outputSetString("myString2", "test string");
		a2.outputSetBool("myBool2", true);
		a2.outputSetImpulsion("myImpulsion2");
		a2.outputSetData("myData2", myBytes);

		//services
		Thread.sleep(3000);
		List<Object> arguments1 = Arrays.asList(9, 7.8, true, "java call string test", "java byte array".getBytes());
		a.serviceCall("macosAgent", "MAC_CALL", arguments1, "token");

		List<Object> arguments2 = Arrays.asList();
		a.serviceCall("macosAgent", "PONG_CALL", arguments2, "token");

		try {
			byte[] fileContent = Files.readAllBytes(Paths.get("/Users/steph/Desktop/Ingescape_API_map.pdf"));
			a.outputSetData("myData1", fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Thread.sleep(20000);
		//a.stop();
		//a = null;
	}
}
